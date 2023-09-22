<?php
/**
 * Theme updater class.
 */
class Allium_Theme_Updater {

	/**
	 * Class Properties
	 */
	private $remote_api_url;
	private $item_name;
	private $theme_slug;
	private $version;
	private $author;
	private $home_url;
	private $response_key;
	private $request_data;
	protected $strings = null;

	/**
	 * Constructor
	 */
	public function __construct() {

		// Remote API
		$this->remote_api_url = 'https://templatelens.com';

		// Theme Info
		$theme_info = wp_get_theme();
		if ( is_child_theme() ) {
			$theme_info = wp_get_theme( get_template() );
		}

		// Theme Info
		$this->item_name  = $theme_info->get( 'Name' ); // Theme Name
		$this->theme_slug = $theme_info->get( 'TextDomain' ); // Theme Slug
		$this->version    = $theme_info->get( 'Version' ); // Theme Current Version
		$this->author     = $theme_info->get( 'Author' ); // Theme Author

		// Blog
		$this->home_url = home_url();

		// Response Key
		$this->response_key = $this->theme_slug . '-update-response'; // Response Key

		// Request Data
		$this->request_data = array();

		// Strings
		$this->strings = array(
			'update-notice'    => __( "Updating this theme will lose any customizations you have made. 'Cancel' to stop, 'OK' to update.", 'allium' ),
			'update-available' => __('<strong>%1$s %2$s</strong> is available. <a href="%3$s" class="thickbox" title="%4$s">Check out what\'s new</a> or <a href="%5$s"%6$s>update now</a>.', 'allium' ),
		);

		// Admin Hooks
		if ( current_user_can( 'manage_options' ) ) {
			$this->admin_hooks_init();
		}
	}

	/**
	 * Admin Hooks
	 */
	public function admin_hooks_init() {

		add_filter( 'http_request_args',                   array( $this, 'disable_wporg_request' ), 5, 2 );
		add_filter( 'site_transient_update_themes',        array( $this, 'theme_update_transient' ) );
		add_filter( 'delete_site_transient_update_themes', array( $this, 'delete_theme_update_transient' ) );
		add_action( 'load-update-core.php',                array( $this, 'delete_theme_update_transient' ) );
		add_action( 'load-themes.php',                     array( $this, 'delete_theme_update_transient' ) );
		add_action( 'load-themes.php',                     array( $this, 'load_themes_screen' ) );

	}

	/**
	 * Disable requests to wp.org repository for this theme.
	 *
	 * @since 1.0.0
	 */
	public function disable_wporg_request( $r, $url ) {

		// If it's not a theme update request, bail.
		if ( 0 !== strpos( $url, 'https://api.wordpress.org/themes/update-check/1.1/' ) ) {
 			return $r;
 		}

 		// Decode the JSON response
 		$themes = json_decode( $r['body']['themes'] );

 		// Remove the active parent and child themes from the check
 		$parent = get_option( 'template' );
 		$child = get_option( 'stylesheet' );
 		unset( $themes->themes->$parent );
 		unset( $themes->themes->$child );

 		// Encode the updated JSON response
 		$r['body']['themes'] = json_encode( $themes );

 		return $r;
	}

	/**
	 * Show the update notification when neecessary
	 *
	 * @return void
	 */
	public function load_themes_screen() {
		add_thickbox();
		add_action( 'admin_notices', array( $this, 'update_nag' ) );
	}

	/**
	 * Display the update notifications
	 *
	 * @return void
	 */
	public function update_nag() {

		$strings      = $this->strings;
		$api_response = get_transient( $this->response_key );

		if ( false === $api_response ) {
			return;
		}

		$update_url     = wp_nonce_url( 'update.php?action=upgrade-theme&amp;theme=' . urlencode( $this->theme_slug ), 'upgrade-theme_' . $this->theme_slug );
		$update_onclick = ' onclick="if ( confirm(\'' . esc_js( $strings['update-notice'] ) . '\') ) {return true;}return false;"';

		if ( version_compare( $this->version, $api_response->new_version, '<' ) ) {

			echo '<div id="update-nag">';
			printf(
				$strings['update-available'],
				$this->item_name,
				$api_response->new_version,
				'#TB_inline?width=640&amp;inlineId=' . $this->theme_slug . '_changelog',
				$this->item_name,
				$update_url,
				$update_onclick
			);
			echo '</div>';
			echo '<div id="' . $this->theme_slug . '_' . 'changelog" style="display:none;">';
			echo wpautop( $api_response->sections['changelog'] );
			echo '</div>';
		}
	}

	/**
	 * Update the theme update transient with the response from the version check
	 *
	 * @param  array $value   The default update values.
	 * @return array|boolean  If an update is available, returns the update parameters, if no update is needed returns false, if
	 *                        the request fails returns false.
	 */
	public function theme_update_transient( $value ) {
		$update_data = $this->check_for_update();
		if ( $update_data ) {
			$value->response[ $this->theme_slug ] = $update_data;
		}
		return $value;
	}

	/**
	 * Remove the update data for the theme
	 *
	 * @return void
	 */
	public function delete_theme_update_transient() {
		delete_transient( $this->response_key );
	}

	/**
	 * Call the EDD SL API (using the URL in the construct) to get the latest version information
	 *
	 * @return array|boolean  If an update is available, returns the update parameters, if no update is needed returns false, if
	 *                        the request fails returns false.
	 */
	public function check_for_update() {

		$update_data = get_transient( $this->response_key );

		if ( false === $update_data ) {
			$failed = false;

			$api_params = array(
				'mdd_action'      => 'get_version',
				'item_name'       => urlencode( $this->item_name ),
				'item_slug'       => urlencode( $this->theme_slug ),
				'item_version'    => urlencode( $this->version ),
				'item_home_url'   => urlencode( $this->home_url ),
			);

			$response = wp_remote_post( $this->remote_api_url, array( 'timeout' => 15, 'body' => $api_params ) );

			// Make sure the response was successful
			if ( is_wp_error( $response ) || 200 != wp_remote_retrieve_response_code( $response ) ) {
				$failed = true;
			}

			$update_data = json_decode( wp_remote_retrieve_body( $response ) );

			/*echo '<pre>';
			print_r( $update_data );
			echo '</pre>';
			exit;*/

			// Response is not an object
			if ( ! is_object( $update_data ) ) {
				$failed = true;
			}

			// Response is an object but item is not ready due to some reasons.
			if ( is_object( $update_data ) && isset( $update_data->msg )  ) {
				$failed = true;
			}

			// If the response failed, try again in 15 minutes
			if ( $failed ) {
				$data = new stdClass;
				$data->new_version = $this->version;
				//set_transient( $this->response_key, $data, strtotime( '+30 minutes', current_time( 'timestamp' ) ) );
				set_transient( $this->response_key, $data, 900 );
				return false;
			}

			// If the status is 'ok', return the update arguments
			if ( ! $failed ) {
				$update_data->sections = maybe_unserialize( $update_data->sections );
				//set_transient( $this->response_key, $update_data, strtotime( '+12 hours', current_time( 'timestamp' ) ) );
				set_transient( $this->response_key, $update_data, 900 ); // S3 link will expire in 15 minutes.
			}
		}

		if ( version_compare( $this->version, $update_data->new_version, '>=' ) ) {
			return false;
		}

		return (array) $update_data;
	}

}

// Class Instance
new Allium_Theme_Updater();
