-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- 主机： 127.0.0.1
-- 生成日期： 2020-08-07 09:39:23
-- 服务器版本： 5.6.34
-- PHP 版本： 7.1.19

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 数据库： `wxldb`
--

-- --------------------------------------------------------

--
-- 表的结构 `blog_access`
--

CREATE TABLE `blog_access` (
  `id` int(10) NOT NULL,
  `access_date` date NOT NULL,
  `access_date_time` int(11) DEFAULT '0',
  `access_count` int(10) NOT NULL DEFAULT '0',
  `access_user_id` int(10) DEFAULT '0',
  `ip` varchar(20) DEFAULT NULL,
  `os` varchar(20) DEFAULT NULL,
  `browser` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `blog_access`
--

INSERT INTO `blog_access` (`id`, `access_date`, `access_date_time`, `access_count`, `access_user_id`, `ip`, `os`, `browser`) VALUES
(1, '2020-07-02', 0, 72, 0, NULL, NULL, NULL),
(2, '2020-07-03', 0, 54, 0, NULL, NULL, NULL),
(3, '2020-07-04', 0, 13, 0, NULL, NULL, NULL),
(4, '2020-07-06', 0, 17, 0, NULL, NULL, NULL),
(5, '2020-07-07', 0, 9, 0, NULL, NULL, NULL),
(6, '2020-07-10', 0, 36, 0, NULL, NULL, NULL),
(7, '2020-07-14', 0, 88, 0, NULL, NULL, NULL),
(8, '2020-07-16', 0, 106, 0, NULL, NULL, NULL),
(9, '2020-07-17', 0, 86, 0, NULL, NULL, NULL),
(10, '2020-07-20', 0, 54, 0, NULL, NULL, NULL),
(11, '2020-07-21', 0, 9, 0, NULL, NULL, NULL),
(12, '2020-07-22', 0, 10, 0, NULL, NULL, NULL),
(13, '2020-07-24', 0, 3, 0, NULL, NULL, NULL),
(14, '2020-07-28', 0, 2, 0, NULL, NULL, NULL),
(15, '2020-07-29', 0, 1, 0, NULL, NULL, NULL),
(16, '2020-08-06', 0, 1, 0, NULL, NULL, NULL),
(17, '2020-08-07', 0, 22, 0, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- 表的结构 `blog_article`
--

CREATE TABLE `blog_article` (
  `article_id` int(10) NOT NULL,
  `article_name` varchar(255) NOT NULL,
  `article_content` text NOT NULL,
  `article_editor` tinyint(1) NOT NULL DEFAULT '0',
  `article_authors` varchar(30) NOT NULL,
  `article_input_date` datetime NOT NULL,
  `article_reading_time` int(10) NOT NULL DEFAULT '0',
  `top` tinyint(1) NOT NULL DEFAULT '0',
  `enabled` tinyint(1) NOT NULL DEFAULT '0',
  `created_time` int(11) NOT NULL,
  `created_user_id` int(10) NOT NULL,
  `updated_time` int(11) NOT NULL DEFAULT '0',
  `updated_user_id` int(10) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `blog_article`
--

INSERT INTO `blog_article` (`article_id`, `article_name`, `article_content`, `article_editor`, `article_authors`, `article_input_date`, `article_reading_time`, `top`, `enabled`, `created_time`, `created_user_id`, `updated_time`, `updated_user_id`) VALUES
(1, '我是谁', '我是一盏灯\n\n我想知道黑夜能否将我淹没\n\n我听见远处的钟声，震的我慌乱\n\n我看见头顶的无数支持者，星星\n\n我要坚持这无人的夜晚，等待黎明\n\n我是一株草\n\n我假装我内心足够强大\n\n我感到鲜花姐姐的冷默\n\n我触摸蝴蝶的翅膀，不到\n\n我担心有一只鞋会从我身上，走过\n\n我哭泣不能再见到我的眼泪，露珠\n\n我是一直蜗牛\n\n我理解猎豹的勇猛\n\n我说那是不属于我的炫耀\n\n我梦想有一双翅膀\n\n我尝试和雄鹰比翼\n\n我希望我也能有一个梦想\n\n我是一个平凡的卑微者\n', 0, '有文章', '2020-07-14 00:00:00', 2, 0, 0, 0, 0, 1594721799, 5),
(3, '陌上桑', '         日出东南隅，照我秦氏楼。秦氏有好女，自名为罗敷。罗敷喜蚕桑，采桑城南隅。青丝为笼系，桂枝为笼钩。头上倭堕髻，耳中明月珠。缃绮为下裙，紫绮为上襦。行者见罗敷，下担捋髭须。少年见罗敷，脱帽著帩头。耕者忘其犁，锄者忘其锄。来归相怨怒，但坐观罗敷。(喜蚕桑 一作：善蚕桑；相怨怒 一作：相怒怨)\n\n　　使君从南来，五马立踟蹰。使君遣吏往，问是谁家姝？“秦氏有好女，自名为罗敷。”“罗敷年几何？”“二十尚不足，十五颇有余”。使君谢罗敷：“宁可共载不？”罗敷前致辞：“使君一何愚！使君自有妇，罗敷自有夫！”\n\n　　“东方千余骑，夫婿居上头。何用识夫婿？白马从骊驹，青丝系马尾，黄金络马头；腰中鹿卢剑，可值千万余。十五府小吏，二十朝大夫，三十侍中郎，四十专城居。为人洁白皙，鬑鬑颇有须。盈盈公府步，冉冉府中趋。坐中数千人，皆言夫婿殊。”(白皙 一作：白晰)', 0, '两汉乐府诗集', '2020-07-14 00:00:00', 4, 0, 1, 1594719655, 5, 0, 0),
(5, '未来五天 安', 'sadfsafasasfasf', 0, '李四', '2020-07-14 00:00:00', 0, 0, 1, 1594722142, 5, 0, 0),
(6, '未来五天 安sdasfasf', 'sadfsafasasfasf', 0, 'asdfasf李四', '2020-07-14 00:00:00', 1, 0, 1, 1594722149, 5, 0, 0),
(8, 'asdfasf', 'asfd', 0, 'asfdsafasf', '2020-07-14 00:00:00', 0, 0, 1, 1594722181, 5, 0, 0),
(9, 'asdfasf', 'asfd', 0, 'asfdsafasf', '2020-07-14 00:00:00', 0, 0, 1, 1594722182, 5, 0, 0),
(10, 'asdfasf', 'asfd', 0, 'asfdsafasf', '2020-07-14 00:00:00', 0, 0, 1, 1594722184, 5, 0, 0),
(11, 'asdfasf', 'asfd', 0, 'asfdsafasf', '2020-07-14 00:00:00', 0, 0, 1, 1594722186, 5, 0, 0),
(13, 'dddddasdfasf', 'asfddddd', 0, 'asfdsafasf', '2020-07-14 00:00:00', 0, 0, 1, 1594722226, 5, 0, 0),
(14, '这个是什么玩意啊', '这个是什么玩意啊', 0, '李四', '2020-07-14 00:00:00', 0, 0, 1, 1594722290, 5, 0, 0),
(15, '这个是什么玩意啊222', '这个是什么玩意啊', 0, '李四', '2020-07-14 00:00:00', 0, 0, 1, 1594722296, 5, 0, 0),
(16, '这个是什么玩意啊findBlogArticleList', 'findBlogArticleListfindBlogArticleListfindBlogArticleListfindBlogArticleList', 0, '李四', '2020-07-14 00:00:00', 0, 0, 1, 1594727487, 5, 0, 0),
(17, 'findBlogfindBlogArticleListfindBlogArticleListArticleList', 'findBlogArticleList', 0, 'findBlogArticleList', '2020-07-14 00:00:00', 4, 0, 1, 1594727510, 5, 0, 0),
(18, '2020大记事', '', 1, '李四', '2020-07-29 00:00:00', 0, 0, 0, 1595994705, 5, 1595995207, 5),
(19, '这个是什么玩意啊', '<p>tinymce</p>', 1, '李四', '2020-07-29 00:00:00', 0, 0, 0, 1596002461, 5, 0, 0),
(20, '这个是什么玩意啊', 'dddddd', 2, '李四', '2020-07-29 00:00:00', 0, 0, 0, 1596002470, 5, 0, 0),
(21, 'test', '<p><img src=\"../../upload/imgs/6PhVtW.jpg\" alt=\"\" width=\"757\" height=\"426\" /></p>', 1, 'test', '2020-08-07 00:00:00', 5, 0, 1, 1596787802, 5, 0, 0),
(22, 'asfdsafas', '<p><img src=\"http://127.0.0.1:8080/upload/imgs/LvYnQl.jpg\" alt=\"\" width=\"195\" height=\"238\" /></p>', 1, 'asdfasfasfasf', '2020-08-07 00:00:00', 13, 0, 0, 1596788433, 5, 1596791994, 5);

-- --------------------------------------------------------

--
-- 表的结构 `blog_article_tag`
--

CREATE TABLE `blog_article_tag` (
  `article_id` int(10) NOT NULL,
  `tag_id` int(10) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `blog_article_tag`
--

INSERT INTO `blog_article_tag` (`article_id`, `tag_id`) VALUES
(5, 3),
(3, 4),
(3, 5),
(3, 6),
(1, 7),
(6, 3),
(17, 3),
(22, 3),
(8, 3),
(22, 9),
(9, 3),
(21, 10),
(10, 3),
(20, 3),
(11, 3),
(16, 3),
(20, 9),
(19, 3),
(13, 3),
(19, 9),
(14, 3),
(18, 8),
(15, 3);

-- --------------------------------------------------------

--
-- 表的结构 `blog_config`
--

CREATE TABLE `blog_config` (
  `id` int(10) NOT NULL,
  `blog_name` varchar(30) NOT NULL,
  `author_name` varchar(20) DEFAULT NULL,
  `about_page_article_id` int(10) NOT NULL DEFAULT '0',
  `record_number` varchar(20) DEFAULT NULL,
  `domain_name` varchar(20) DEFAULT NULL,
  `email_username` varchar(30) DEFAULT NULL,
  `created_time` int(11) NOT NULL,
  `created_user_id` int(10) NOT NULL,
  `updated_time` int(11) NOT NULL DEFAULT '0',
  `updated_user_id` int(10) NOT NULL DEFAULT '0'
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `blog_config`
--

INSERT INTO `blog_config` (`id`, `blog_name`, `author_name`, `about_page_article_id`, `record_number`, `domain_name`, `email_username`, `created_time`, `created_user_id`, `updated_time`, `updated_user_id`) VALUES
(1, '微博客-合肥蔓朵', '芊忆君子', 1, '皖ICP备17010158号-2', 'skit.cn', '80825745@qq.com', 0, 0, 1595237646, 5);

-- --------------------------------------------------------

--
-- 表的结构 `blog_link`
--

CREATE TABLE `blog_link` (
  `link_id` int(10) NOT NULL,
  `link_name` varchar(30) NOT NULL,
  `link_url` varchar(30) NOT NULL,
  `remark` varchar(100) NOT NULL,
  `created_time` int(11) NOT NULL,
  `created_user_id` int(10) NOT NULL,
  `updated_time` int(11) NOT NULL,
  `updated_user_id` int(10) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `blog_link`
--

INSERT INTO `blog_link` (`link_id`, `link_name`, `link_url`, `remark`, `created_time`, `created_user_id`, `updated_time`, `updated_user_id`) VALUES
(1, '哈哈之家', 'http://hahafamily.com/', '', 1594896930, 5, 1594897070, 5);

-- --------------------------------------------------------

--
-- 表的结构 `blog_message`
--

CREATE TABLE `blog_message` (
  `message_id` int(10) NOT NULL,
  `email` varchar(30) NOT NULL,
  `name` varchar(20) NOT NULL,
  `subject` varchar(60) NOT NULL,
  `message_content` text NOT NULL,
  `message_input_date` datetime NOT NULL,
  `created_time` int(11) NOT NULL DEFAULT '0',
  `created_user_id` int(10) NOT NULL DEFAULT '0',
  `read` tinyint(1) NOT NULL DEFAULT '0',
  `replied` tinyint(1) NOT NULL DEFAULT '0',
  `reply_content` text,
  `reply_user_id` int(10) NOT NULL DEFAULT '0',
  `reply_time` int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `blog_message`
--

INSERT INTO `blog_message` (`message_id`, `email`, `name`, `subject`, `message_content`, `message_input_date`, `created_time`, `created_user_id`, `read`, `replied`, `reply_content`, `reply_user_id`, `reply_time`) VALUES
(1, 'wenyouzhang@126.com', '有文章', '真好啊', '请给我打电话', '2020-06-30 11:50:32', 1593517832, 0, 1, 1, '这个真不差', 5, 1595234578),
(3, 'asdfasf@qq.com', 'sadsaf', 'asfdsaf', 'safdasf', '2020-07-20 09:35:03', 1595237703, 0, 1, 0, '', 0, 0),
(4, 'wenyouzhang@126.com', '这是个很长很长的名字~~~~~', 'thymeleaf中th:href携带参数的三种写法thymeleaf中th:href携带参数的三种写法', '在使用thymeleaf模板引擎的时候，我们经常会用到th:href来设置标签的href属性，\n\n如何给URL渲染上参数呢？\n\n私总结了三种在th:href添加参数的写法：\n\n1，变量表达式\n又称OGNL表达式或者EL表达式，在Spring术语中也叫model attributes，差不多一个意思\n\n<a th:href=\"${\'/order.html?pageNum=\'}+${pageInfo.prePage}\"><上一页</a>\n1\n2，URL表达式+拼接字符串\n不建议写这种，很捞，可读性不强\n\n<a th:href=\"@{\'/order.html?pageNum=\'+${pageInfo.prePage}}\"><上一页</a>\n1\n3，URL表达式\n推荐写法，简单好看，可读性高。（如果是多个参数用,分隔\n\n<a th:href=\"@{/order.html(pageNum=${pageInfo.prePage})}\"><上一页</a>\n1\n\n————————————————\n版权声明：本文为CSDN博主「enkidu66」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。\n原文链接：https://blog.csdn.net/enkidu66/java/article/details/107097070', '2020-07-20 09:46:15', 1595238375, 0, 1, 0, '', 0, 0);

-- --------------------------------------------------------

--
-- 表的结构 `blog_tag`
--

CREATE TABLE `blog_tag` (
  `tag_id` int(10) NOT NULL,
  `tag_name` varchar(20) NOT NULL,
  `tag_input_date` date NOT NULL,
  `created_time` int(11) NOT NULL,
  `created_user_id` int(10) NOT NULL,
  `updated_time` int(11) NOT NULL,
  `updated_user_id` int(10) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `blog_tag`
--

INSERT INTO `blog_tag` (`tag_id`, `tag_name`, `tag_input_date`, `created_time`, `created_user_id`, `updated_time`, `updated_user_id`) VALUES
(1, '电影', '2020-06-30', 0, 0, 0, 0),
(8, '2020', '2020-07-29', 1595994705, 5, 0, 0),
(3, '古诗', '2020-07-14', 1594714459, 5, 0, 0),
(4, '写人', '2020-07-14', 1594719655, 5, 0, 0),
(5, '乐歌', '2020-07-14', 1594719655, 5, 0, 0),
(6, '咏颂', '2020-07-14', 1594719655, 5, 0, 0),
(7, '系统', '2020-07-14', 1594720817, 5, 0, 0),
(9, '成语', '2020-07-29', 1596002461, 5, 0, 0),
(10, 'test', '2020-08-07', 1596787802, 5, 0, 0);

-- --------------------------------------------------------

--
-- 表的结构 `feedback`
--

CREATE TABLE `feedback` (
  `id` int(10) NOT NULL,
  `open_id` varchar(30) NOT NULL,
  `user_id` int(10) NOT NULL,
  `content` tinytext NOT NULL,
  `created_time` int(11) NOT NULL COMMENT '反馈时间',
  `replied` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0未回复，1已回复',
  `reply_content` tinytext,
  `reply_user_id` int(10) DEFAULT NULL,
  `reply_time` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `role`
--

CREATE TABLE `role` (
  `role_id` smallint(6) NOT NULL,
  `role_name` varchar(20) NOT NULL,
  `enabled` tinyint(1) NOT NULL DEFAULT '0',
  `created_time` int(11) NOT NULL,
  `created_user_id` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `role`
--

INSERT INTO `role` (`role_id`, `role_name`, `enabled`, `created_time`, `created_user_id`) VALUES
(1, 'ROLE_USER', 1, 1593769874, 0),
(2, 'ROLE_ADMIN', 1, 1593769874, 0),
(3, 'ROLE_SYSTEMADMIN', 1, 1593769874, 0),
(4, 'ROLE_SUPERADMIN', 1, 1593769874, 0);

-- --------------------------------------------------------

--
-- 表的结构 `user`
--

CREATE TABLE `user` (
  `user_id` int(10) UNSIGNED NOT NULL,
  `user_name` char(20) DEFAULT NULL,
  `nick_name` varchar(30) DEFAULT NULL,
  `real_name` varchar(20) DEFAULT NULL,
  `wechat_nickname` varchar(30) DEFAULT NULL,
  `open_id` varchar(32) DEFAULT NULL,
  `sex` tinyint(1) NOT NULL DEFAULT '0',
  `role_id` smallint(6) NOT NULL DEFAULT '0',
  `phone` char(11) NOT NULL,
  `email` varchar(20) DEFAULT NULL,
  `user_avatar` varchar(30) DEFAULT NULL,
  `user_password` char(32) NOT NULL,
  `user_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0未激活，1正常，2冻结，3禁止登录，4黑名单，5删除',
  `created_time` int(11) NOT NULL DEFAULT '0',
  `created_user_id` int(10) NOT NULL DEFAULT '0',
  `updated_time` int(11) NOT NULL DEFAULT '0',
  `updated_user_id` int(10) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `user`
--

INSERT INTO `user` (`user_id`, `user_name`, `nick_name`, `real_name`, `wechat_nickname`, `open_id`, `sex`, `role_id`, `phone`, `email`, `user_avatar`, `user_password`, `user_status`, `created_time`, `created_user_id`, `updated_time`, `updated_user_id`) VALUES
(3, 'youwenzhang', '有文章', '', NULL, NULL, 1, 0, '18919605137', NULL, 'upload/avatar/a_5_3_D577.jpg', '34FC8B6EB5C5CB4694AF86DABD728FF7', 3, 1592641523, 0, 1595409272, 5),
(5, 'admin', '超级管理员', '', NULL, NULL, 0, 0, '17333298805', '80825745@qq.com', 'upload/avatar/a_5_5_mj0b.jpg', 'A188C6DC9ADD507B3611C0FD82F6B625', 1, 0, 0, 1595409218, 5),
(6, 'zhangwenyou', '', '', NULL, NULL, 0, 0, '13339078857', NULL, 'upload/avatar/a_5_0_7uni.jpg', 'F4C1BADB4B61CEDF3C3BA4E0B7FBA5EE', 0, 1595409340, 5, 0, 0),
(7, 'sadfsafs', '', '', NULL, NULL, 0, 0, '15656563457', NULL, '', '866903DF78BF567A0AE066CFE91189D7', 0, 1595409633, 5, 0, 0),
(8, 'abc', '', 'zhang', NULL, NULL, 0, 0, '18919605127', NULL, NULL, '2FCE8F74A67ABE7DCB0C72BB6410C81E', 0, 1595409832, 5, 0, 0);

-- --------------------------------------------------------

--
-- 表的结构 `user_role`
--

CREATE TABLE `user_role` (
  `user_id` int(10) NOT NULL,
  `role_id` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `user_role`
--

INSERT INTO `user_role` (`user_id`, `role_id`) VALUES
(5, 1),
(5, 2),
(5, 3),
(5, 4),
(3, 1),
(6, 4),
(7, 1),
(8, 1);

-- --------------------------------------------------------

--
-- 表的结构 `wechat_user`
--

CREATE TABLE `wechat_user` (
  `open_id` varchar(30) NOT NULL,
  `nickname` varchar(30) DEFAULT NULL,
  `sex` tinyint(1) DEFAULT NULL,
  `province` varchar(30) DEFAULT NULL,
  `city` varchar(30) DEFAULT NULL,
  `country` varchar(30) DEFAULT NULL,
  `headimgurl` varchar(256) DEFAULT NULL,
  `user_id` int(10) NOT NULL,
  `created_time` int(11) DEFAULT NULL,
  `created_user_id` int(10) DEFAULT NULL,
  `updated_time` int(11) DEFAULT NULL,
  `updated_user_id` int(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转储表的索引
--

--
-- 表的索引 `blog_access`
--
ALTER TABLE `blog_access`
  ADD PRIMARY KEY (`id`),
  ADD KEY `access_date` (`access_date`);

--
-- 表的索引 `blog_article`
--
ALTER TABLE `blog_article`
  ADD PRIMARY KEY (`article_id`),
  ADD KEY `article_name` (`article_name`),
  ADD KEY `article_authors` (`article_authors`);

--
-- 表的索引 `blog_config`
--
ALTER TABLE `blog_config`
  ADD PRIMARY KEY (`id`);

--
-- 表的索引 `blog_link`
--
ALTER TABLE `blog_link`
  ADD PRIMARY KEY (`link_id`);

--
-- 表的索引 `blog_message`
--
ALTER TABLE `blog_message`
  ADD PRIMARY KEY (`message_id`),
  ADD KEY `reply_user_id` (`reply_user_id`),
  ADD KEY `is_reply` (`replied`),
  ADD KEY `is_read` (`read`);

--
-- 表的索引 `blog_tag`
--
ALTER TABLE `blog_tag`
  ADD PRIMARY KEY (`tag_id`);

--
-- 表的索引 `feedback`
--
ALTER TABLE `feedback`
  ADD PRIMARY KEY (`id`),
  ADD KEY `open_id` (`open_id`),
  ADD KEY `user_id` (`user_id`);

--
-- 表的索引 `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`role_id`),
  ADD UNIQUE KEY `role_name` (`role_name`);

--
-- 表的索引 `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `phone` (`phone`),
  ADD UNIQUE KEY `user_name` (`user_name`),
  ADD UNIQUE KEY `open_id` (`open_id`),
  ADD KEY `role_id` (`role_id`),
  ADD KEY `user_status` (`user_status`);

--
-- 表的索引 `wechat_user`
--
ALTER TABLE `wechat_user`
  ADD PRIMARY KEY (`open_id`),
  ADD UNIQUE KEY `user_id` (`user_id`);

--
-- 在导出的表使用AUTO_INCREMENT
--

--
-- 使用表AUTO_INCREMENT `blog_access`
--
ALTER TABLE `blog_access`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- 使用表AUTO_INCREMENT `blog_article`
--
ALTER TABLE `blog_article`
  MODIFY `article_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- 使用表AUTO_INCREMENT `blog_config`
--
ALTER TABLE `blog_config`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- 使用表AUTO_INCREMENT `blog_link`
--
ALTER TABLE `blog_link`
  MODIFY `link_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- 使用表AUTO_INCREMENT `blog_message`
--
ALTER TABLE `blog_message`
  MODIFY `message_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- 使用表AUTO_INCREMENT `blog_tag`
--
ALTER TABLE `blog_tag`
  MODIFY `tag_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- 使用表AUTO_INCREMENT `feedback`
--
ALTER TABLE `feedback`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT;

--
-- 使用表AUTO_INCREMENT `role`
--
ALTER TABLE `role`
  MODIFY `role_id` smallint(6) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- 使用表AUTO_INCREMENT `user`
--
ALTER TABLE `user`
  MODIFY `user_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
