<?php
/**
 * The header for our theme.
 *
 * Displays all of the <head> section and everything up till <div id="content">
 *
 * @package Allium
 */
?><!DOCTYPE html>
<html <?php language_attributes(); ?>>
<head>
<meta charset="<?php bloginfo( 'charset' ); ?>">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="profile" href="http://gmpg.org/xfn/11">
<?php wp_head(); ?>
</head>

<body <?php body_class(); ?>>
<div id="page" class="site-wrapper site">

	<header id="masthead" class="site-header" role="banner">
		<div class="container">
			<div class="row">
				<div class="col">

					<div class="site-header-inside-wrapper">
						<?php
						// Site Branding
						get_template_part( 'template-parts/header/site-branding' );
						?>

						<?php
						// Site Navigation
						get_template_part( 'template-parts/header/site-navigation' );
						?>
					</div><!-- .site-header-inside-wrapper -->

				</div><!-- .col -->
			</div><!-- .row -->
		</div><!-- .container -->
	</header><!-- #masthead -->

	<div id="content" class="site-content">
