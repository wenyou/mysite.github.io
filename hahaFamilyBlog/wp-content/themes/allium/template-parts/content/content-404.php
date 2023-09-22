<?php
/**
 * The template used for displaying page content in 404.php
 *
 * @package Allium
 */
?>

<div class="post-wrapper-hentry">
	<section class="error-404 not-found">
		<div class="post-content-wrapper post-content-wrapper-single post-content-wrapper-single-404">

			<div class="page-content">
				<p><?php esc_html_e( 'It looks like nothing was found at this location. Maybe try one of the links below or a search?', 'allium' ); ?></p>

				<?php
				// Search Widget
				the_widget(
					'WP_Widget_Search',
					array(
						'title' => esc_html__( 'Search', 'allium' )
					),
					array(
						'before_title' => '<h2 class="widget-title">',
						'after_title' => '</h2>'
					)
				);
				?>

				<?php
				// Recent Posts Widget
				the_widget(
					'WP_Widget_Recent_Posts',
					array(
						'title' => esc_html__( 'Recent Posts', 'allium' ),
					),
					array(
						'before_title' => '<h2 class="widget-title">',
						'after_title' => '</h2>'
					)
				);
				?>

				<?php
				// Archives Widget
				the_widget(
					'WP_Widget_Archives',
					array(
						'title'    => esc_html__( 'Archives', 'allium' ),
						'dropdown' => 1,
					),
					array(
						'before_title' => '<h2 class="widget-title">',
						'after_title' => '</h2>'
					)
				);
				?>

				<?php
				// Tags Widget
				the_widget(
					'WP_Widget_Tag_Cloud',
					array(
						'title' => esc_html__( 'Tags', 'allium' ),
					),
					array(
						'before_title' => '<h2 class="widget-title">',
						'after_title' => '</h2>'
					)
				);
				?>
			</div><!-- .page-content -->

		</div><!-- .post-content-wrapper -->
	</section><!-- .error-404 -->
</div><!-- .post-wrapper-hentry -->
