<?php
/**
 * Custom template tags for this theme.
 *
 * Eventually, some of the functionality here could be replaced by core features.
 *
 * @package Allium
 */

if ( ! function_exists( 'allium_the_posts_pagination' ) ) :
/**
 * Display navigation to next/previous set of posts when applicable.
 *
 * @return void
 */
function allium_the_posts_pagination() {

	// Previous/next posts navigation @since 4.1.0
	the_posts_pagination( array(
		'prev_text'          => '<span class="screen-reader-text">' . esc_html__( 'Previous Page', 'allium' ) . '</span>',
		'next_text'          => '<span class="screen-reader-text">' . esc_html__( 'Next Page', 'allium' ) . '</span>',
		'before_page_number' => '<span class="meta-nav screen-reader-text">' . esc_html__( 'Page', 'allium' ) . ' </span>',
	) );

}
endif;

if ( ! function_exists( 'allium_the_post_pagination' ) ) :
/**
 * Previous/next post navigation.
 *
 * @return void
 */
function allium_the_post_pagination() {

	// Previous/next post navigation @since 4.1.0.
	the_post_navigation( array(
		'next_text' => '<span class="meta-nav">' . esc_html__( 'Next', 'allium' ) . '</span> ' . '<span class="post-title">%title</span>',
		'prev_text' => '<span class="meta-nav">' . esc_html__( 'Prev', 'allium' ) . '</span> ' . '<span class="post-title">%title</span>',
	) );

}
endif;

if ( ! function_exists( 'allium_posted_on' ) ) :
/**
 * Prints HTML with meta information for the current post-date/time and author.
 */
function allium_posted_on( $before = '', $after = '' ) {

	// No need to display date for sticky posts
	if ( allium_has_sticky_post() ) {
		return;
	}

	// Time String
	$time_string = '<time class="entry-date published updated" datetime="%1$s">%2$s</time>';
	if ( get_the_time( 'U' ) !== get_the_modified_time( 'U' ) ) {
		$time_string = '<time class="entry-date published" datetime="%1$s">%2$s</time><time class="updated" datetime="%3$s">%4$s</time>';
	}

	$time_string = sprintf( $time_string,
		esc_attr( get_the_date( 'c' ) ),
		esc_html( get_the_date() ),
		esc_attr( get_the_modified_date( 'c' ) ),
		esc_html( get_the_modified_date() )
	);

	// Posted On
	$posted_on = sprintf( '<span class="screen-reader-text">%1$s</span><a href="%2$s" rel="bookmark">%3$s</a>',
		esc_html_x( 'Posted on', 'post date', 'allium' ),
		esc_url( get_permalink() ),
		$time_string
	);

	// Posted On HTML
	$html = '<span class="posted-on entry-meta-icon">' . $posted_on . '</span>'; // // WPCS: XSS OK.

	// Posted On HTML Before After
	$html = $before . $html . $after; // WPCS: XSS OK.

	/**
	 * Filters the Posted On HTML.
	 *
	 * @param string $html Posted On HTML.
	 */
	$html = apply_filters( 'allium_posted_on_html', $html );

	echo $html; // WPCS: XSS OK.
}
endif;

if ( ! function_exists( 'allium_posted_by' ) ) :
/**
 * Prints author.
 */
function allium_posted_by( $before = '', $after = '' ) {

	// Global Post
	global $post;

	// We need to get author meta data from both inside/outside the loop.
	$post_author_id = get_post_field( 'post_author', $post->ID );

	// Post Author
	$post_author = sprintf( '<span class="author vcard"><a class="entry-author-link url fn n" href="%1$s" rel="author"><span class="entry-author-name">%2$s</span></a></span>',
		esc_url( get_author_posts_url( get_the_author_meta( 'ID', $post_author_id ) ) ),
		esc_html( get_the_author_meta( 'display_name', $post_author_id ) )
	);

	// Byline
	$byline = sprintf(
		/* translators: %s: post author */
		esc_html_x( 'by %s', 'post author', 'allium' ),
		$post_author
	);

	// Posted By HTML
	$html = '<span class="byline entry-meta-icon">' . $byline . '</span>'; // WPCS: XSS OK.

	// Posted By HTML Before After
	$html = $before . $html . $after; // WPCS: XSS OK.

	/**
	 * Filters the Posted By HTML.
	 *
	 * @param string $html Posted By HTML.
	 */
	$html = apply_filters( 'allium_posted_by_html', $html );

	echo $html; // WPCS: XSS OK.
}
endif;

if ( ! function_exists( 'allium_sticky_post' ) ) :
/**
 * Prints HTML label for the sticky post.
 */
function allium_sticky_post( $before = '', $after = '' ) {

	// Sticky Post Validation
	if ( ! allium_has_sticky_post() ) {
		return;
	}

	// Sticky Post HTML
	$html = sprintf( '<span class="post-label post-label-sticky entry-meta-icon">%1$s</span>',
		esc_html_x( 'Featured', 'sticky post label', 'allium' )
	);

	// Sticky Post HTML Before After
	$html = $before . $html . $after; // WPCS: XSS OK.

	/**
	 * Filters the Sticky Post HTML.
	 *
	 * @param string $html Sticky Post HTML.
	 */
	$html = apply_filters( 'allium_sticky_post_html', $html );

	echo $html; // WPCS: XSS OK.
}
endif;

if ( ! function_exists( 'allium_post_edit_link' ) ) :
/**
 * Prints post edit link.
 *
 * @return void
*/
function allium_post_edit_link( $before = '', $after = '' ) {

	// Post edit link Validation
	if ( allium_has_post_edit_link() ) {

		// Post Edit Link
		$post_edit_link = sprintf( '<span class="screen-reader-text">%1$s</span><a class="post-edit-link" href="%2$s">%3$s</a>',
		esc_html( the_title_attribute( 'echo=0' ) ),
		esc_url( get_edit_post_link() ),
		esc_html_x( 'Edit', 'post edit link label', 'allium' )
		);

		// Post Edit Link HTML
		$html = '<span class="post-edit-link-meta entry-meta-icon">' . $post_edit_link . '</span>';

		// Post Edit Link HTML Before After
		$html = $before . $html . $after; // WPCS: XSS OK.

		/**
		 * Filters the Post Edit Link HTML.
		 *
		 * @param string $html Post Edit Link HTML.
		 */
		$html = apply_filters( 'allium_post_edit_link_html', $html );

		echo $html; // WPCS: XSS OK.
	}

}
endif;

if ( ! function_exists( 'allium_post_first_category' ) ) :
/**
 * Prints first category for the current post.
 *
 * @return void
*/
function allium_post_first_category( $before = '', $after = '' ) {

	// An array of categories to return for the post.
	$categories = get_the_category();
	if ( $categories[0] ) {

		// Post First Category HTML
		$html = sprintf( '<span class="post-first-category cat-links entry-meta-icon"><a href="%1$s" title="%2$s">%3$s</a></span>',
			esc_attr( esc_url( get_category_link( $categories[0]->term_id ) ) ),
			esc_attr( $categories[0]->cat_name ),
			esc_html( $categories[0]->cat_name )
		);

		// Post First Category HTML Before After
		$html = $before . $html . $after; // WPCS: XSS OK.

		/**
		 * Filters the Post First Category HTML.
		 *
		 * @param string $html Post First Category HTML.
		 * @param array $categories An array of categories to return for the post.
		 */
		$html = apply_filters( 'allium_post_first_category_html', $html, $categories );

		echo $html; // WPCS: XSS OK.
	}

}
endif;

if ( ! function_exists( 'allium_read_more_link' ) ) :
	/**
	 * Prints Read More Link.
	 */
	function allium_read_more_link() {

		// Read More Label
		$read_more_label = allium_mod( 'allium_read_more_label' );

		// Read More Link
		$read_more_link = sprintf( '<a href="%1$s" class="more-link">%2$s</a>',
			esc_url( get_permalink() ),
			esc_html( $read_more_label )
		);

		// Read More HTML
		$html = '<div class="more-link-wrapper">' . $read_more_link . '</div>'; // // WPCS: XSS OK.

		/**
		 * Filters the Read More HTML.
		 *
		 * @param string $html Read More HTML.
		 */
		$html = apply_filters( 'allium_read_more_html', $html );

		echo $html; // WPCS: XSS OK.
	}
endif;

if ( ! function_exists( 'allium_entry_footer' ) ) :
/**
 * Prints HTML with meta information for the categories, tags and comments.
 */
function allium_entry_footer() {

	// Hide category and tag text for pages.
	if ( 'post' === get_post_type() ) {
		/* translators: used between list items, there is a space after the comma */
		$categories_list = get_the_category_list( _x(', ', 'Used between category, there is a space after the comma.', 'allium' ) );
		if ( $categories_list && allium_categorized_blog() ) {
			printf(
				/* translators: 1: posted in label. 2: list of categories. */
				'<span class="cat-links cat-links-single">%1$s %2$s</span>',
				esc_html__( 'Posted in:', 'allium' ),
				$categories_list
			); // WPCS: XSS OK.
		}

		/* translators: used between list items, there is a space after the comma */
		$tags_list = get_the_tag_list( '', _x(', ', 'Used between tag, there is a space after the comma.', 'allium' ) );
		if ( $tags_list ) {
			printf(
				/* translators: 1: posted in label. 2: list of tags. */
				'<span class="tags-links tags-links-single">%1$s %2$s</span>',
				esc_html__( 'Tags:', 'allium' ),
				$tags_list
			); // WPCS: XSS OK.
		}
	}

	// Edit post link.
	edit_post_link(
		sprintf(
			wp_kses(
				/* translators: %s: Name of current post. Only visible to screen readers. */
				__( 'Edit <span class="screen-reader-text">%s</span>', 'allium' ),
				array(
					'span' => array(
						'class' => array(),
					),
				)
			),
			get_the_title()
		),
		'<span class="edit-link">',
		'</span>'
	);

}
endif;

/**
 * Returns true if a blog has more than 1 category.
 *
 * @return bool
 */
function allium_categorized_blog() {
	if ( false === ( $all_the_cool_cats = get_transient( 'allium_categories' ) ) ) {
		// Create an array of all the categories that are attached to posts.
		$all_the_cool_cats = get_categories( array (
			'fields'     => 'ids',
			'hide_empty' => 1,

			// We only need to know if there is more than one category.
			'number'     => 2,
		) );

		// Count the number of categories that are attached to the posts.
		$all_the_cool_cats = count( $all_the_cool_cats );

		set_transient( 'allium_categories', $all_the_cool_cats );
	}

	if ( $all_the_cool_cats > 1 ) {
		// This blog has more than 1 category so allium_categorized_blog should return true.
		return true;
	} else {
		// This blog has only 1 category so allium_categorized_blog should return false.
		return false;
	}
}

/**
 * Flush out the transients used in allium_categorized_blog.
 */
function allium_category_transient_flusher() {
	if ( defined( 'DOING_AUTOSAVE' ) && DOING_AUTOSAVE ) {
		return;
	}
	// Like, beat it. Dig?
	delete_transient( 'allium_categories' );
}
add_action( 'edit_category', 'allium_category_transient_flusher' );
add_action( 'save_post',     'allium_category_transient_flusher' );

if ( ! function_exists( 'allium_post_thumbnail' ) ) :
/**
 * Display an optional post thumbnail.
 *
 * Wraps the post thumbnail in an anchor element on index
 * views, or a div element when on single views.
 *
 * @param array $args
 * @return void
*/
function allium_post_thumbnail( $args = array() ) {

	// Defaults
	$defaults = array (
 		'size'  => 'allium-featured',
 		'class' => 'entry-image-wrapper',
	);

	// Parse incoming $args into an array and merge it with $defaults
	$args = wp_parse_args( $args, $defaults );

	// Post Thumbnail HTML
	$html = '';

	// Post Thumbnail Validation
	if ( allium_has_post_thumbnail() ) {

		// Post Thumbnail HTML
		$html = sprintf( '<div class="%1$s"><figure class="post-thumbnail"><a href="%2$s">%3$s</a></figure></div>',
			esc_attr( $args['class'] ),
			esc_url( get_the_permalink() ),
			get_the_post_thumbnail( null, $args['size'], array( 'class' => 'img-featured img-responsive' ) )
		);

	}

	/**
	 * Filters the Post Thumbnail HTML.
	 *
	 * @param string $html Post Thumbnail HTML.
	 */
	$html = apply_filters( 'allium_post_thumbnail_html', $html );

	// Print HTML
	if ( ! empty ( $html ) ) {
		echo $html; // WPCS: XSS OK.
	}

}
endif;

/**
 * A helper conditional function.
 * Whether there is a post thumbnail and post is not password protected.
 *
 * @return bool
 */
function allium_has_post_thumbnail() {

	/**
	 * Post Thumbnail Filter
	 * @return bool
	 */
	return apply_filters( 'allium_has_post_thumbnail', (bool) ( ! post_password_required() && has_post_thumbnail() ) );

}

/**
 * A helper conditional function.
 * Post is Sticky or Not
 *
 * @return bool
 */
function allium_has_sticky_post() {

	/**
	 * Sticky Post Filter
	 * @return bool
	 */
	return apply_filters( 'allium_has_sticky_post', (bool) ( is_sticky() && is_home() && ! is_paged() ) );

}

/**
 * A helper conditional function.
 * Post has Edit link or Not
 *
 * @return bool
 */
function allium_has_post_edit_link() {

	/**
	 * Post Edit Link Filter
	 * @return bool
	 */
	$post_edit_link = get_edit_post_link();
	return apply_filters( 'allium_has_post_edit_link', (bool) ( ! empty( $post_edit_link ) ) );

}

/**
 * A helper conditional function.
 * Theme has Excerpt or Not
 *
 * @return bool
 */
function allium_has_excerpt() {

	// Post Excerpt
	$post_excerpt = get_the_excerpt();

	/**
	 * Excerpt Filter
	 * @return bool
	 */
	return apply_filters( 'allium_has_excerpt', (bool) ! empty ( $post_excerpt ) );

}

/**
 * A helper conditional function.
 * Theme has Sidebar or Not
 *
 * @return bool
 */
function allium_has_sidebar() {

	/**
	 * Sidebar Filter
	 * @return bool
	 */
	return apply_filters( 'allium_has_sidebar', (bool) is_active_sidebar( 'sidebar-1' ) );

}

/**
 * Display the layout classes.
 *
 * @param string $section - Name of the section to retrieve the classes
 * @return void
 */
function allium_layout_class( $section = 'content' ) {

	// Sidebar Position
	$sidebar_position = allium_mod( 'allium_sidebar_position' );
	if ( ! allium_has_sidebar() ) {
		$sidebar_position = 'no';
	}

	// Layout Skeleton
	$layout_skeleton = array(
		'content' => array(
			'content' => 'col',
		),

		'content-sidebar' => array(
			'content' => 'col-16 col-sm-16 col-md-16 col-lg-11 col-xl-11 col-xxl-11',
			'sidebar' => 'col-16 col-sm-16 col-md-16 col-lg-5 col-xl-5 col-xxl-5',
		),

		'sidebar-content' => array(
			'content' => 'col-16 col-sm-16 col-md-16 col-lg-11 col-xl-11 col-xxl-11 order-lg-2 order-xl-2 order-xxl-2',
			'sidebar' => 'col-16 col-sm-16 col-md-16 col-lg-5 col-xl-5 col-xxl-5 order-lg-1 order-xl-1 order-xxl-1',
		),
	);

	// Layout Classes
	switch( $sidebar_position ) {

		case 'no':
		$layout_classes = $layout_skeleton['content']['content'];
		break;

		case 'left':
		$layout_classes = ( 'sidebar' === $section )? $layout_skeleton['sidebar-content']['sidebar'] : $layout_skeleton['sidebar-content']['content'];
		break;

		case 'right':
		default:
		$layout_classes = ( 'sidebar' === $section )? $layout_skeleton['content-sidebar']['sidebar'] : $layout_skeleton['content-sidebar']['content'];

	}

	echo esc_attr( $layout_classes );

}
