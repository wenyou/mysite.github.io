<?php
/**
 * The base configuration for WordPress
 *
 * The wp-config.php creation script uses this file during the
 * installation. You don't have to use the web site, you can
 * copy this file to "wp-config.php" and fill in the values.
 *
 * This file contains the following configurations:
 *
 * * MySQL settings
 * * Secret keys
 * * Database table prefix
 * * ABSPATH
 *
 * @link https://codex.wordpress.org/Editing_wp-config.php
 *
 * @package WordPress
 */

// ** MySQL settings - You can get this info from your web host ** //
/** The name of the database for WordPress */
define( 'DB_NAME', 'haFaBlogDb' );

/** MySQL database username */
define( 'DB_USER', 'root' );

/** MySQL database password */
define( 'DB_PASSWORD', '123456' );

/** MySQL hostname */
define( 'DB_HOST', '127.0.0.1' );

/** Database Charset to use in creating database tables. */
define( 'DB_CHARSET', 'utf8mb4' );

/** The Database Collate type. Don't change this if in doubt. */
define( 'DB_COLLATE', '' );

/**#@+
 * Authentication Unique Keys and Salts.
 *
 * Change these to different unique phrases!
 * You can generate these using the {@link https://api.wordpress.org/secret-key/1.1/salt/ WordPress.org secret-key service}
 * You can change these at any point in time to invalidate all existing cookies. This will force all users to have to log in again.
 *
 * @since 2.6.0
 */
define( 'AUTH_KEY',         '1cw|2aL5<){45v HIFVYHDWgO(u;q}?<EHoD`!G].p,(MV8>W37=_T3dQR7pkZvY' );
define( 'SECURE_AUTH_KEY',  'rN$vlH.k;[/nByly{e:}7pE+):9r#vzNT@{vH@[$4;vn#RO9f9P{_Mc7M#.aX8L.' );
define( 'LOGGED_IN_KEY',    ')|iOjr[vy& .J4j?V;E/,}O|h,`{M3)rg6Pq=A<}6l|XvXyAFJ3c$C8y12DR@u7 ' );
define( 'NONCE_KEY',        's8gnGkWi{S SNsN U<siP*vG!ICzSWGos)HS1ezmG4>!1R09HYSX.34x^#)n|Xx>' );
define( 'AUTH_SALT',        '$*_-}`Dn+_@WF&f@R|7r_4_&P9$0YD035|D{P@5YgM+W1qSrfZ^.@rMq+`oNz=fc' );
define( 'SECURE_AUTH_SALT', 'BPg~-misX0$g8^r`jca!RHP?VK,G1`=?3>2^KE?2e4Ko>,/UXipkw_}Y=)jIBvXY' );
define( 'LOGGED_IN_SALT',   'MI|3#REV=J.F?lQ1<CV<Pz@jFh8|0Q teaJRvZAs{}8o6exydxe~2Vrg}`U8DT<k' );
define( 'NONCE_SALT',       ' ?KP0_TP5z5ZSY:WB2:XK%yWRqZW@*e0Aqb@Q8=YDmjtO<B|P6^!@/=vQfUcY]v|' );

/**#@-*/

/**
 * WordPress Database Table prefix.
 *
 * You can have multiple installations in one database if you give each
 * a unique prefix. Only numbers, letters, and underscores please!
 */
$table_prefix = 'wp_hablog_';

/**
 * For developers: WordPress debugging mode.
 *
 * Change this to true to enable the display of notices during development.
 * It is strongly recommended that plugin and theme developers use WP_DEBUG
 * in their development environments.
 *
 * For information on other constants that can be used for debugging,
 * visit the Codex.
 *
 * @link https://codex.wordpress.org/Debugging_in_WordPress
 */
define( 'WP_DEBUG', false );

/* That's all, stop editing! Happy publishing. */

/** Absolute path to the WordPress directory. */
if ( ! defined( 'ABSPATH' ) ) {
	define( 'ABSPATH', dirname( __FILE__ ) . '/' );
}

/** Sets up WordPress vars and included files. */
require_once( ABSPATH . 'wp-settings.php' );
