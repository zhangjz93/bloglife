/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50087
Source Host           : localhost:3306
Source Database       : blog

Target Server Type    : MYSQL
Target Server Version : 50087
File Encoding         : 65001

Date: 2017-07-26 13:29:41
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `article`
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article` (
  `id` int(11) NOT NULL auto_increment,
  `title` varchar(100) NOT NULL,
  `writer_id` int(11) NOT NULL,
  `category_id` int(11) NOT NULL,
  `submittime` datetime NOT NULL,
  `clicknum` int(11) NOT NULL,
  `replynum` int(11) NOT NULL,
  `edittime` datetime NOT NULL,
  `isedit` tinyint(1) NOT NULL,
  `summary` varchar(255) NOT NULL,
  `isgood` tinyint(1) NOT NULL,
  `lastcount` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `writer_id` (`writer_id`),
  KEY `category_id` (`category_id`),
  KEY `article_title_index` (`title`),
  KEY `article_submittime_index` (`submittime`),
  CONSTRAINT `article_ibfk_1` FOREIGN KEY (`writer_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
  CONSTRAINT `article_ibfk_2` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Records of article
-- ----------------------------
INSERT INTO `article` VALUES ('16', '天龙十载，如果重玩天龙你会怎么玩？', '3', '4', '2017-07-03 19:24:16', '138', '6', '2017-07-03 19:24:16', '0', ' 把天龙都玩成了遗憾了吗?如果重玩天龙你会怎么玩?   之所以写这篇稿件，其一是因为L 最近看见群里的小伙伴都在回忆之前玩天龙的快乐时光;另一个就是L前几天和天龙里的师傅一起做师德任务，有一环任务是做对5道选择题，一旦错一题就会从新开始做，一直到5道题连续答对，任务才算完成。以为很容易过的一个环节，...', '1', '9');
INSERT INTO `article` VALUES ('19', 'Egg.js 1.0.0 正式发布  企业级 Node.js', '3', '2', '2017-07-11 11:23:06', '97', '0', '2017-07-11 15:51:16', '1', '   『企业级的 Node.js Web 基础框架』，寓意 - 孕育新生。    业界领先的『微内核 + 插件机制』，专为团队架构师量身打造的『框架定制』能力。    内建的安全机制与多进程管理模型。    高可用，高质量，严格遵循 Semver 规则，测试覆盖率 100%（travis/appve...', '1', '1');
INSERT INTO `article` VALUES ('21', '什么是吐槽？？？？', '4', '3', '2017-07-24 11:24:25', '37', '0', '2017-07-24 11:25:47', '1', ' “吐槽”一词，来源于对日本漫才（日本的一种站台喜剧，类似相声），“ツッコミ”的汉语翻译，是指从对方的语言或行为中找到一个漏洞或关键词作为切入点，发出带有调侃意味的感慨或疑问。普通话里相当于相声的“捧哏(小学馆，三省堂，广词苑均为此意)”，后来延伸成为ACGN次文化常用的词汇之一。而闽南语中原本也有', '1', '0');
INSERT INTO `article` VALUES ('22', '《笑傲江湖OL》5?19新版飞行坐骑独家首曝', '3', '4', '2017-07-24 16:11:55', '28', '0', '2017-07-24 16:11:55', '0', ' 完美世界旗下3D武侠《笑傲江湖OL》(网址：http://xa.wanmei.com/main.htm)全新版本青春嘉年华5月19日将与广大玩家正式见面啦!此次小编为少男少女们独家首曝本次519新版隆重推出的飞行坐骑!绚丽夺目，美艳动人，更能助你一秒入天，去你想去的地方，见你想见的人~!并且，为了...', '1', '0');
INSERT INTO `article` VALUES ('23', '倩女幽魂全民争霸赛，晒试玩截图赢点卡', '4', '4', '2017-07-26 00:27:34', '8', '2', '2017-07-26 00:28:51', '1', '《新倩女幽魂》首届统一起点、公平竞技的全民争霸赛火热报名中！！！ 7月20日?8月1日全服试玩持续开启，50级及以上玩家都可进入试玩服报名参赛。 报名参赛奖励多多： 1、 参赛每赢一场即可获得豪华大礼包，有挺高的机率开出宝匣钥匙； 2、 参赛打满4场（无论输赢）即可获得限定坐骑九尾冰狐； 3、 免费...', '1', '2');

-- ----------------------------
-- Table structure for `attach`
-- ----------------------------
DROP TABLE IF EXISTS `attach`;
CREATE TABLE `attach` (
  `id` int(11) NOT NULL auto_increment,
  `article_id` int(11) NOT NULL,
  `path` varchar(255) NOT NULL,
  `uploadtime` date NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `article_id` (`article_id`),
  CONSTRAINT `attach_ibfk_1` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Records of attach
-- ----------------------------
INSERT INTO `attach` VALUES ('3', '16', 'http://i1.17173cdn.com/2fhnvk/YWxqaGBf/outcms/lgjfhublmeonwaD.png', '2017-07-03');
INSERT INTO `attach` VALUES ('5', '19', '/blog/upload/2017-07-11/20170711155107198.png', '2017-07-11');
INSERT INTO `attach` VALUES ('9', '21', '/blog/upload/2017-07-24/20170724112540101.jpg', '2017-07-24');
INSERT INTO `attach` VALUES ('11', '23', '/blog/upload/2017-07-26/20170726002843340.jpg', '2017-07-26');
INSERT INTO `attach` VALUES ('12', '23', '', '2017-07-26');

-- ----------------------------
-- Table structure for `attention`
-- ----------------------------
DROP TABLE IF EXISTS `attention`;
CREATE TABLE `attention` (
  `focus_id` int(11) NOT NULL,
  `focused_id` int(11) NOT NULL,
  `focustime` datetime NOT NULL,
  PRIMARY KEY  (`focus_id`,`focused_id`),
  KEY `focused_id` (`focused_id`),
  KEY `attention_focustime_index` (`focustime`),
  CONSTRAINT `attention_ibfk_1` FOREIGN KEY (`focus_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
  CONSTRAINT `attention_ibfk_2` FOREIGN KEY (`focused_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Records of attention
-- ----------------------------
INSERT INTO `attention` VALUES ('3', '4', '2017-07-13 16:03:03');
INSERT INTO `attention` VALUES ('4', '3', '2017-07-24 23:52:20');

-- ----------------------------
-- Table structure for `category`
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(10) NOT NULL,
  `intro` varchar(200) NOT NULL,
  `photo` varchar(100) NOT NULL,
  `articlenum` int(11) NOT NULL,
  `sortnum` tinyint(4) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES ('2', 'JAVA', 'JAVA技术交流', '/blog/resources/a.jpg', '1', '1');
INSERT INTO `category` VALUES ('3', '吐槽大会', '人生不吐不快。', '/blog/resources/a.jpg', '1', '4');
INSERT INTO `category` VALUES ('4', '游戏心情', '让我们一起来玩耍吧！', '/blog/resources/a.jpg', '3', '3');

-- ----------------------------
-- Table structure for `collect`
-- ----------------------------
DROP TABLE IF EXISTS `collect`;
CREATE TABLE `collect` (
  `user_id` int(11) NOT NULL,
  `article_id` int(11) NOT NULL,
  `collecttime` datetime NOT NULL,
  PRIMARY KEY  (`user_id`,`article_id`),
  KEY `article_id` (`article_id`),
  KEY `collect_collecttime_index` (`collecttime`),
  CONSTRAINT `collect_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
  CONSTRAINT `collect_ibfk_2` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Records of collect
-- ----------------------------
INSERT INTO `collect` VALUES ('4', '16', '2017-07-24 11:21:02');
INSERT INTO `collect` VALUES ('3', '21', '2017-07-24 16:08:00');
INSERT INTO `collect` VALUES ('3', '22', '2017-07-24 20:10:11');
INSERT INTO `collect` VALUES ('3', '19', '2017-07-24 20:36:50');
INSERT INTO `collect` VALUES ('4', '19', '2017-07-24 23:36:19');
INSERT INTO `collect` VALUES ('4', '22', '2017-07-25 22:06:33');
INSERT INTO `collect` VALUES ('4', '23', '2017-07-26 00:31:51');

-- ----------------------------
-- Table structure for `content`
-- ----------------------------
DROP TABLE IF EXISTS `content`;
CREATE TABLE `content` (
  `id` int(11) NOT NULL auto_increment,
  `article_id` int(11) NOT NULL,
  `words` text NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `article_id` (`article_id`),
  CONSTRAINT `content_ibfk_1` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Records of content
-- ----------------------------
INSERT INTO `content` VALUES ('16', '16', '<p> 把天龙都玩成了遗憾了吗?如果重玩天龙你会怎么玩? </p> \n<p> 之所以写这篇稿件，其一是因为L 最近看见群里的小伙伴都在回忆之前玩天龙的快乐时光;另一个就是L前几天和天龙里的师傅一起做师德任务，有一环任务是做对5道选择题，一旦错一题就会从新开始做，一直到5道题连续答对，任务才算完成。以为很容易过的一个环节，然后做了五六遍才成功过关，感觉曾经对天龙的各方面的了解，现如今蛮多已经经过时间的流逝遗忘了...内心也伴随着深深地失落感，就像是很用心去记住的记忆崩塌了一角... </p> \n<p> 天龙十载，很多东西其实都深入了玩家们的生活和内心，最近L身边的很多小伙伴说：其实我之前已经脱坑了，但是依然每天会打开论坛贴吧看看天龙里发生的事情，感觉依旧还是放不下，所以现在又回归了。 </p> \n<p> 天龙十载，当你在玩的时候，其实并未觉得有什么，甚至可能嫌弃他画质不好，2.5D，但是这么多年过来了，嫌弃归嫌弃，当你丢一段时间不来玩了，你依然会关注他的点点滴滴。 </p> \n<p> 如果可以重玩一次天龙你会选择怎么玩呢? </p> \n<p> <img src=\"http://i1.17173cdn.com/2fhnvk/YWxqaGBf/outcms/lgjfhublmeonwaD.png\">如果重回天龙，你们会选择怎么玩呢?是趁着还能出9星装备，多做一些装备呢?是趁着全三还很厉害的时候，早早打满全三呢?是趁着紫电刚出时候，一定给自己的号配置一个呢?是多收集一些绝版宝宝呢、收集一些值得纪念的收藏品、如07年那会出的安装光盘、曾经充值的游戏点卡的卡片、天龙曾经推出的典藏卡?还是在老三城时，认真打每一次城战呢?又或是多多珍惜身边的友谊呢?等等... </p>');
INSERT INTO `content` VALUES ('19', '19', '<ul>\r\n	<li>\r\n		『企业级的 Node.js Web 基础框架』，寓意 - 孕育新生。\r\n	</li>\r\n	<li>\r\n		业界领先的&nbsp;『微内核 + 插件机制』，专为团队架构师量身打造的&nbsp;『框架定制』&nbsp;能力。\r\n	</li>\r\n	<li>\r\n		内建的安全机制与多进程管理模型。\r\n	</li>\r\n	<li>\r\n		高可用，高质量，严格遵循 Semver 规则，测试覆盖率 100%（travis/appveyor)。\r\n	</li>\r\n	<li>\r\n		同时支持 koa 1.x 和 2.x 生态，支持 ES 2017 Async Await。\r\n	</li>\r\n	<li>\r\n		沉淀自阿里各行各业不同领域最佳实践的插件，涵盖了常见的业务开发场景，稳定支撑了 15 和 16 年天猫双11大促，顶级流量压力。\r\n	</li>\r\n	<li>\r\n		渐进式，极具伸缩性，既适合个人小项目快速开发，也适合企业级的团队开发协作。\r\n	</li>\r\n</ul>\r\n<blockquote>\r\n	<p>\r\n		更详细的特点，以及 Node.js 在阿里的定位，可以参见： <a href=\"https://www.zhihu.com/question/50526101/answer/144952130\" target=\"_blank\">如何评价阿里开源的企业级 Node.js 框架 egg？</a> \r\n	</p>\r\n</blockquote>\r\n<p>\r\n	看来重要的事要说三次： Egg 1.x 版本完全支持 async，完全支持 koa2 的中间件 Egg 1.x 版本完全支持 async，完全支持 koa2 的中间件 Egg 1.x 版本完全支持 async，完全支持 koa2 的中间件\r\n</p>\r\n<p style=\"text-align:center;\">\r\n	<img src=\"/blog/upload/2017-07-11/20170711155107198.png\" alt=\"\" />\r\n</p>\r\n<h2>\r\n	里程碑\r\n</h2>\r\n<ul>\r\n	<li>\r\n		2013 年蚂蚁的 chair 框架，可视为 Egg.js 前身。\r\n	</li>\r\n	<li>\r\n		2015 年 11 月，在苏千的召集下，阿里各 BU 的前端骨干齐聚黄龙，闭门共建一周。\r\n	</li>\r\n	<li>\r\n		2016 年初，各 BU 的基础 Web 框架完成升级，在同一套规范的基础上进行差异化定制。\r\n	</li>\r\n	<li>\r\n		2016 年中，成为阿里 Node.js 基建，广泛使用在绝大部分阿里的前端 Node.js 应用。\r\n	</li>\r\n	<li>\r\n		2016 年 09 月，在 <a href=\"http://2016.jsconf.cn/\" target=\"_blank\">JSConf China 2016</a> 上亮相并宣布开源。\r\n	</li>\r\n	<li>\r\n		2017 年初，经过一周的<a href=\"https://cnodejs.org/topic/5870e9da04dcf9a706a745f0\" target=\"_blank\">闭关直播写文档</a>，期待已久的 <a href=\"https://eggjs.org/\" target=\"_blank\">官方文档</a> 诚意登场，足足近 30 篇。\r\n	</li>\r\n	<li>\r\n		2017年 02 月，知乎问答：<a href=\"https://www.zhihu.com/question/50526101/answer/144952130\" target=\"_blank\">如何评价阿里开源的企业级 Node.js 框架 egg？</a> \r\n	</li>\r\n	<li>\r\n		2017 年 02 月第 2 周，上了&nbsp;GitHub Trending&nbsp;周榜第三，Star 数增加 1k 多。\r\n	</li>\r\n	<li>\r\n		2017 年 03 月 21 日，Egg.js 正式发布 1.0.0 。\r\n	</li>\r\n</ul>');
INSERT INTO `content` VALUES ('21', '21', '<p>\r\n	&nbsp; “吐槽”一词，来源于对日本 <a target=\"_blank\" href=\"https://baike.baidu.com/item/%E6%BC%AB%E6%89%8D\">漫才</a>（日本的一种站台喜剧，类似相声），“ツッコミ”的汉语翻译，是指从对方的语言或行为中找到一个漏洞或关键词作为切入点，发出带有调侃意味的感慨或疑问。普通话里相当于相声的“捧哏(小学馆，三省堂，广词苑均为此意)”，后来延伸成为 <a target=\"_blank\" href=\"https://baike.baidu.com/item/ACGN\">ACGN</a>次文化常用的词汇之一。而闽南语中原本也有近义的“吐槽”一词（但含义与“ツッコミ”不尽相同），台湾将“ツッコミ”翻译作“吐槽”，后来此叫法传至中国大陆。近义词是“抬杠”、“掀老底”、“拆台”、“踢爆”等。而打着吐槽旗号的八卦、抱怨、发泄、喷黑、吐苦水、说三道四都不属于吐槽。\r\n</p>\r\n<p>\r\n	<img src=\"/blog/upload/2017-07-24/20170724112540101.jpg\" alt=\"\" />\r\n</p>');
INSERT INTO `content` VALUES ('22', '22', '<span style=\"color:#2B2B2B;font-family:Arial, 宋体;font-size:14px;line-height:26.6px;background-color:#FFFFFF;\"> 完美世界旗下3D武侠《笑傲江湖OL》(网址：http://xa.wanmei.com/main.htm)全新版本青春嘉年华5月19日将与广大玩家正式见面啦!此次小编为少男少女们独家首曝本次519新版隆重推出的飞行坐骑!绚丽夺目，美艳动人，更能助你一秒入天，去你想去的地方，见你想见的人~!并且，为了大家全新开始一场青春的冒险，5月19日将全新开放一组服务器【卓尔不凡】，只等你来哦!</span>');
INSERT INTO `content` VALUES ('23', '23', '《新倩女幽魂》首届统一起点、公平竞技的全民争霸赛火热报名中！！！ <br />\r\n7月20日?8月1日全服试玩持续开启，50级及以上玩家都可进入试玩服报名参赛。 <br />\r\n报名参赛奖励多多： <br />\r\n1、 参赛每赢一场即可获得豪华大礼包，有挺高的机率开出宝匣钥匙； <br />\r\n2、 参赛打满4场（无论输赢）即可获得限定坐骑九尾冰狐； <br />\r\n3、 免费体验150级40万装备评分的强力标准号； <br />\r\n4、 还会有其它各种各种奖励，参赛都有机会获得。 <br />\r\n所以，各位童鞋们，千！万！记！得！赶！紧！报！名！吧！ <br />\r\n现在起，凡是参与全民争霸的童鞋，只要在赛事服进行试玩，并讲试玩角色的截图上传论坛，即可赢取网易一卡通奖励！ <br />\r\n加17173玩家群：656632850 必中奖！ <br />\r\n<br />\r\n活动时间： <br />\r\n<br />\r\n2017年7月21日~2017年8月1日 <br />\r\n<br />\r\n活动方式： <br />\r\n<br />\r\n参与全民争霸的童鞋，在赛事服进行试玩，并将赛事服试玩的截图上传至论坛即可！ <br />\r\n注：截图需带有赛事服角色昵称 <br />\r\n<br />\r\n回帖格式： <br />\r\n<br />\r\n17173论坛ID：XXX <br />\r\n<br />\r\n试玩截图：XXX <br />\r\n<br />\r\n例如 <br />\r\n<br />\r\n17173论坛ID：谁是我的亲亲 <br />\r\n<br />\r\n试玩截图： <br />\r\n<p>\r\n	带上区名\r\n</p>\r\n<p>\r\n	<img src=\"/blog/upload/2017-07-26/20170726002843340.jpg\" alt=\"\" />\r\n</p>\r\n<p>\r\n	<img alt=\"\" /> \r\n</p>');

-- ----------------------------
-- Table structure for `dynamic`
-- ----------------------------
DROP TABLE IF EXISTS `dynamic`;
CREATE TABLE `dynamic` (
  `id` int(11) NOT NULL auto_increment,
  `user_id` int(11) NOT NULL,
  `message_id` int(11) NOT NULL,
  `type` tinyint(1) NOT NULL,
  `submittime` datetime NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `user_id_index` (`user_id`),
  KEY `dynamic_submittime_index` (`submittime`),
  CONSTRAINT `dynamic_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Records of dynamic
-- ----------------------------
INSERT INTO `dynamic` VALUES ('28', '3', '19', '1', '2017-07-11 11:23:06');
INSERT INTO `dynamic` VALUES ('32', '4', '21', '1', '2017-07-24 11:24:25');
INSERT INTO `dynamic` VALUES ('33', '3', '22', '1', '2017-07-24 16:11:55');
INSERT INTO `dynamic` VALUES ('34', '4', '23', '1', '2017-07-24 22:04:59');
INSERT INTO `dynamic` VALUES ('35', '4', '23', '1', '2017-07-26 00:27:35');

-- ----------------------------
-- Table structure for `permission`
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` int(11) NOT NULL auto_increment,
  `url` varchar(50) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES ('2', '/deleteArticle');
INSERT INTO `permission` VALUES ('4', '/deleteReply');
INSERT INTO `permission` VALUES ('6', '/cancelCollect');
INSERT INTO `permission` VALUES ('8', '/loadScript');
INSERT INTO `permission` VALUES ('10', '/cancelAttention');
INSERT INTO `permission` VALUES ('11', '/addArticle');
INSERT INTO `permission` VALUES ('12', '/addReply');
INSERT INTO `permission` VALUES ('13', '/addAttention');
INSERT INTO `permission` VALUES ('15', '/saveScript');
INSERT INTO `permission` VALUES ('16', '/collectArticle');
INSERT INTO `permission` VALUES ('17', '/upload');
INSERT INTO `permission` VALUES ('18', '/uploadPhoto');
INSERT INTO `permission` VALUES ('19', '/updateUser');
INSERT INTO `permission` VALUES ('20', '/write');
INSERT INTO `permission` VALUES ('21', '/edit');
INSERT INTO `permission` VALUES ('22', '/person/collect');
INSERT INTO `permission` VALUES ('23', '/person/dynamic');
INSERT INTO `permission` VALUES ('25', '/person/notice');
INSERT INTO `permission` VALUES ('26', '/admin/categoryList');
INSERT INTO `permission` VALUES ('27', '/admin/deleteCategory');
INSERT INTO `permission` VALUES ('28', '/admin/categoryAdd');
INSERT INTO `permission` VALUES ('29', '/admin/addCategory');
INSERT INTO `permission` VALUES ('30', '/admin/updateCategory');
INSERT INTO `permission` VALUES ('31', '/admin/userList');
INSERT INTO `permission` VALUES ('32', '/admin/deleteUser');
INSERT INTO `permission` VALUES ('33', '/admin/addRole');
INSERT INTO `permission` VALUES ('34', '/admin/deleteRole');
INSERT INTO `permission` VALUES ('35', '/admin/articleList');
INSERT INTO `permission` VALUES ('36', '/admin/recommendList');
INSERT INTO `permission` VALUES ('37', '/admin/deleteArticle');
INSERT INTO `permission` VALUES ('38', '/admin/addRecommend');
INSERT INTO `permission` VALUES ('39', '/admin/deleteRecommend');
INSERT INTO `permission` VALUES ('40', '/admin/replyList');
INSERT INTO `permission` VALUES ('41', '/admin/deleteReply');
INSERT INTO `permission` VALUES ('42', '/admin/permissionList');
INSERT INTO `permission` VALUES ('43', '/admin/updatePermission');
INSERT INTO `permission` VALUES ('44', '/admin/deletePermission');
INSERT INTO `permission` VALUES ('45', '/admin/addPermission');
INSERT INTO `permission` VALUES ('46', '/admin/index');
INSERT INTO `permission` VALUES ('47', '/admin/uploadIcon');

-- ----------------------------
-- Table structure for `recommend`
-- ----------------------------
DROP TABLE IF EXISTS `recommend`;
CREATE TABLE `recommend` (
  `article_id` int(11) NOT NULL,
  `addtime` datetime NOT NULL,
  PRIMARY KEY  (`article_id`),
  KEY `recommend_addtime_index` (`addtime`),
  CONSTRAINT `recommend_ibfk_1` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Records of recommend
-- ----------------------------
INSERT INTO `recommend` VALUES ('19', '2017-07-11 13:29:57');
INSERT INTO `recommend` VALUES ('16', '2017-07-24 11:53:34');
INSERT INTO `recommend` VALUES ('21', '2017-07-24 12:22:19');
INSERT INTO `recommend` VALUES ('23', '2017-07-26 00:33:10');
INSERT INTO `recommend` VALUES ('22', '2017-07-26 00:33:12');

-- ----------------------------
-- Table structure for `reply`
-- ----------------------------
DROP TABLE IF EXISTS `reply`;
CREATE TABLE `reply` (
  `id` int(11) NOT NULL auto_increment,
  `writer_id` int(11) NOT NULL,
  `article_id` int(11) NOT NULL,
  `parent_id` int(11) NOT NULL,
  `words` varchar(600) NOT NULL,
  `count` int(11) NOT NULL,
  `replytime` datetime NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `writer_id` (`writer_id`),
  KEY `article_id` (`article_id`),
  KEY `reply_count_index` (`count`),
  CONSTRAINT `reply_ibfk_1` FOREIGN KEY (`writer_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
  CONSTRAINT `reply_ibfk_2` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Records of reply
-- ----------------------------
INSERT INTO `reply` VALUES ('65', '4', '16', '0', '我来抢个沙发先&nbsp;蛤蛤', '7', '2017-07-24 11:20:29');
INSERT INTO `reply` VALUES ('67', '4', '16', '66', '哈哈哈哈啊哈哈', '9', '2017-07-24 22:08:28');
INSERT INTO `reply` VALUES ('68', '4', '23', '0', '自己顶啊啊啊啊', '1', '2017-07-26 00:28:01');
INSERT INTO `reply` VALUES ('69', '4', '23', '68', '哈哈哈哈哈哈啊哈哈哈', '2', '2017-07-26 00:29:59');

-- ----------------------------
-- Table structure for `reply_notice`
-- ----------------------------
DROP TABLE IF EXISTS `reply_notice`;
CREATE TABLE `reply_notice` (
  `id` int(11) NOT NULL auto_increment,
  `writer_id` int(11) NOT NULL,
  `reply_id` int(11) NOT NULL,
  `isread` tinyint(1) NOT NULL default '0',
  PRIMARY KEY  (`id`),
  KEY `writer_id` (`writer_id`),
  KEY `reply_id` (`reply_id`),
  CONSTRAINT `reply_notice_ibfk_1` FOREIGN KEY (`writer_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
  CONSTRAINT `reply_notice_ibfk_2` FOREIGN KEY (`reply_id`) REFERENCES `reply` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Records of reply_notice
-- ----------------------------
INSERT INTO `reply_notice` VALUES ('1', '3', '65', '1');
INSERT INTO `reply_notice` VALUES ('3', '3', '67', '0');

-- ----------------------------
-- Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(10) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('3', 'user');
INSERT INTO `role` VALUES ('4', 'admin');

-- ----------------------------
-- Table structure for `role_permission`
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `role_id` int(11) NOT NULL,
  `permission_id` int(11) NOT NULL,
  PRIMARY KEY  (`role_id`,`permission_id`),
  KEY `permission_id` (`permission_id`),
  CONSTRAINT `role_permission_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE,
  CONSTRAINT `role_permission_ibfk_2` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES ('3', '2');
INSERT INTO `role_permission` VALUES ('3', '4');
INSERT INTO `role_permission` VALUES ('3', '6');
INSERT INTO `role_permission` VALUES ('3', '8');
INSERT INTO `role_permission` VALUES ('3', '10');
INSERT INTO `role_permission` VALUES ('3', '11');
INSERT INTO `role_permission` VALUES ('3', '12');
INSERT INTO `role_permission` VALUES ('3', '13');
INSERT INTO `role_permission` VALUES ('3', '15');
INSERT INTO `role_permission` VALUES ('3', '16');
INSERT INTO `role_permission` VALUES ('3', '17');
INSERT INTO `role_permission` VALUES ('3', '18');
INSERT INTO `role_permission` VALUES ('3', '19');
INSERT INTO `role_permission` VALUES ('3', '20');
INSERT INTO `role_permission` VALUES ('3', '21');
INSERT INTO `role_permission` VALUES ('3', '22');
INSERT INTO `role_permission` VALUES ('3', '23');
INSERT INTO `role_permission` VALUES ('3', '25');
INSERT INTO `role_permission` VALUES ('4', '26');
INSERT INTO `role_permission` VALUES ('4', '27');
INSERT INTO `role_permission` VALUES ('4', '28');
INSERT INTO `role_permission` VALUES ('4', '29');
INSERT INTO `role_permission` VALUES ('4', '30');
INSERT INTO `role_permission` VALUES ('4', '31');
INSERT INTO `role_permission` VALUES ('4', '32');
INSERT INTO `role_permission` VALUES ('4', '33');
INSERT INTO `role_permission` VALUES ('3', '34');
INSERT INTO `role_permission` VALUES ('4', '35');
INSERT INTO `role_permission` VALUES ('4', '36');
INSERT INTO `role_permission` VALUES ('4', '37');
INSERT INTO `role_permission` VALUES ('4', '38');
INSERT INTO `role_permission` VALUES ('4', '39');
INSERT INTO `role_permission` VALUES ('4', '40');
INSERT INTO `role_permission` VALUES ('4', '41');
INSERT INTO `role_permission` VALUES ('4', '42');
INSERT INTO `role_permission` VALUES ('4', '43');
INSERT INTO `role_permission` VALUES ('4', '44');
INSERT INTO `role_permission` VALUES ('4', '45');
INSERT INTO `role_permission` VALUES ('4', '46');
INSERT INTO `role_permission` VALUES ('4', '47');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(30) NOT NULL,
  `intro` varchar(100) NOT NULL,
  `photo` varchar(200) NOT NULL,
  `registtime` date NOT NULL,
  `is_master` tinyint(1) NOT NULL,
  `career` varchar(100) NOT NULL,
  `hobby` varchar(100) NOT NULL,
  `skill` varchar(100) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `user_name_index` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('3', 'zhangjz931', '哈哈啊哈哈', '/blog/upload/photo/photo3.png', '2017-06-26', '0', '程序猿、攻城狮、父亲', '未填写', '装逼');
INSERT INTO `user` VALUES ('4', 'zhangjz93', '未填写', '/blog/upload/photo/photo4.jpg', '2017-07-13', '0', '未填写', '未填写', '未填写');

-- ----------------------------
-- Table structure for `user_role`
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY  (`user_id`,`role_id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `user_role_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
  CONSTRAINT `user_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('3', '3');
INSERT INTO `user_role` VALUES ('4', '3');
INSERT INTO `user_role` VALUES ('4', '4');
DROP TRIGGER IF EXISTS `t_article_insert`;
DELIMITER ;;
CREATE TRIGGER `t_article_insert` AFTER INSERT ON `article` FOR EACH ROW begin
update category set articlenum=articlenum+1 where id=new.category_id;
end
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `t_article_delete`;
DELIMITER ;;
CREATE TRIGGER `t_article_delete` AFTER DELETE ON `article` FOR EACH ROW begin
update category set articlenum=articlenum-1 where id=old.category_id;
end
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `t_reply_insert`;
DELIMITER ;;
CREATE TRIGGER `t_reply_insert` AFTER INSERT ON `reply` FOR EACH ROW begin
update article set replynum=replynum+1 where id=new.article_id;
end
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `t_reply_delete`;
DELIMITER ;;
CREATE TRIGGER `t_reply_delete` AFTER DELETE ON `reply` FOR EACH ROW begin
update article set replynum=replynum-1 where id=old.article_id;
end
;;
DELIMITER ;
