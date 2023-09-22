<?php
/**
 * Displays footer site info
 *
 * @package WordPress
 * @subpackage Twenty_Seventeen
 * @since Twenty Seventeen 1.0
 * @version 1.0
 */

?>
<div class="site-info">
	<?php
	if ( function_exists( 'the_privacy_policy_link' ) ) {
		the_privacy_policy_link( '', '<span role="separator" aria-hidden="true"></span>' );
	}
	?>
	&copy; 2017-2020&nbsp;<a href="http://www.hahafamily.com/" class="imprint">哈哈之家</a> &nbsp;&nbsp;
	<a href="http://www.beian.miit.gov.cn/" class="imprint">皖ICP备17010158号</a>
</div><!-- .site-info -->
