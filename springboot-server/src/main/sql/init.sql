DROP TABLE IF EXISTS `s202000300021_course`;
CREATE TABLE `s202000300021_course` (
      `Id` varchar(45) NOT NULL,
      `Name` varchar(45) NOT NULL,
      `Term` varchar(45) DEFAULT NULL,
      `Teacher` varchar(45) NOT NULL,
      `Time` varchar(45) NOT NULL,
      `Place` varchar(45) NOT NULL,
      `Credit` int NOT NULL,
      `Classhour` int NOT NULL,
      `Property` varchar(45) NOT NULL,
      `Major` varchar(45) DEFAULT NULL,
      `Students` text,
      `Capacity` int NOT NULL,
      `CurrentNumber` int NOT NULL,
      `sequence` int NOT NULL DEFAULT '0',
      `remain` int NOT NULL DEFAULT '0',
      `userid` varchar(255) DEFAULT NULL,
      `score` int DEFAULT '0',
      `max_score` int DEFAULT '0',
      `min_score` int DEFAULT '0',
      `rankk` int NOT NULL,
      PRIMARY KEY (`Id`),
      UNIQUE KEY `CourseID_UNIQUE` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;


DROP TABLE IF EXISTS `s202000300021_user_type`;
CREATE TABLE `s202000300021_user_type` (
    `id` int NOT NULL,
    `name` varchar(45) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;

INSERT INTO `s202000300021_user_type` VALUES (1, 'ROLE_ADMIN' );
INSERT INTO `s202000300021_user_type` VALUES (2, 'ROLE_USER' );
INSERT INTO `s202000300021_user_type` VALUES (3, 'ROLE_TEACHER');


DROP TABLE IF EXISTS `s202000300021_person`;
CREATE TABLE `s202000300021_person` (
    `person_id` int NOT NULL,
    `per_name` varchar(45) DEFAULT NULL,
    `per_num` varchar(45) DEFAULT NULL,
    `per_type` varchar(45) DEFAULT NULL,
    `per_card` varchar(20) DEFAULT NULL,
    `phone` varchar(20) DEFAULT NULL,
    `email` varchar(45) DEFAULT NULL,
    PRIMARY KEY (`person_id`),
    UNIQUE KEY `UK9u4cxcmin97t4a7ah5bs32829` (`per_num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;


DROP TABLE IF EXISTS `s202000300021_user`;
CREATE TABLE `s202000300021_user` (
    `user_id` int NOT NULL AUTO_INCREMENT,
    `real_name` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
    `user_name` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
    `gender` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
    `university` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
    `phone` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
    `password` varchar(60) COLLATE utf8_unicode_ci NOT NULL,
    `major` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
    `person_id` int DEFAULT NULL,
    `user_type_id` int DEFAULT NULL,
    `last_login_time` datetime DEFAULT NULL,
    `login_count` int DEFAULT NULL,
    `desc` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
    `score` int DEFAULT NULL,
    PRIMARY KEY (`user_id`),
    UNIQUE KEY `user_name_UNIQUE` (`user_name`),
    UNIQUE KEY `UK4bakctviobmdk6ddh2nwg08c2` (`user_name`),
    UNIQUE KEY `UKl4cdqqysq7s42g1pfv1nu42hq` (`person_id`),
    KEY `132123` (`user_type_id`),
#     CONSTRAINT `FKir5g79` FOREIGN KEY (`person_id`) REFERENCES `s202000300021_person` (`person_id`),
    CONSTRAINT `FKlrk9xrdps0eb6h` FOREIGN KEY (`user_type_id`) REFERENCES `s202000300021_user_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
INSERT INTO `s202000300021_user` VALUES (1, '系统管理员', 'admin', '男', '山东大学', '18832129035', '$10$nPIfJST74k0r0Cvo6uNxT.KcKYqs6dtgibijf8kkndltr/Ldg..d2','软件工程',NULL, 1, NULL,1,'',0);
INSERT INTO `s202000300021_user` VALUES (2, 'S李泽宇', '202000300021', '男', '山东大学', '18832129035', '$2a$10$Z8wl3o/vh7u5WzdEKbg/M.hX.UdcrPT36lWRd0Ju3HqZhvNR2TVy6','软件工程', NULL, 2, NULL, 1, '',0);
INSERT INTO `s202000300021_user` VALUES (3, 'T李泽宇', 'T202000300021', '男', '山东大学', '18832129035', '$2a$10$Z8wl3o/vh7u5WzdEKbg/M.hX.UdcrPT36lWRd0Ju3HqZhvNR2TVy6','软件工程', NULL, 3, NULL, 1, '',0);



DROP TABLE IF EXISTS `s202000300021_sys`;
CREATE TABLE `s202000300021_sys` (
    `term` varchar(255) NOT NULL,
    `state` int NOT NULL,
    PRIMARY KEY (`term`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;
INSERT INTO `s202000300021_sys` VALUE ('2021-2022-1', 0);

DROP TABLE IF EXISTS `s202000300021_stoc`;
CREATE TABLE `s202000300021_stoc` (
    `id` int NOT NULL DEFAULT '0',
    `user_id` varchar(45) DEFAULT NULL,
    `term` varchar(45) NOT NULL,
    `courses` text,
    PRIMARY KEY (`id`),
    UNIQUE KEY `stuAccount_UNIQUE` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;

DROP TABLE IF EXISTS `s202000300021_course`;
CREATE TABLE `s202000300021_course` (
    `Id` varchar(45) NOT NULL,
    `Name` varchar(45) NOT NULL,
    `Term` varchar(45) DEFAULT NULL,
    `Teacher` varchar(45) NOT NULL,
    `Time` varchar(45) NOT NULL,
    `Place` varchar(45) NOT NULL,
    `Credit` int NOT NULL,
    `Classhour` int NOT NULL,
    `Property` varchar(45) NOT NULL,
    `Major` varchar(45) DEFAULT NULL,
    `Students` text,
    `Capacity` int NOT NULL,
    `CurrentNumber` int NOT NULL,
    `sequence` int NOT NULL DEFAULT '0',
    `remain` int NOT NULL DEFAULT '0',
    `userid` varchar(255) DEFAULT NULL,
    `score` int DEFAULT '0',
    `max_score` int DEFAULT '0',
    `min_score` int DEFAULT '0',
    `rankk` int NOT NULL,
    PRIMARY KEY (`Id`),
    UNIQUE KEY `CourseID_UNIQUE` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;
INSERT INTO `s202000300021_course` VALUES ('sd01210950','光与光学','2021-2022-1', '范书振', '1-16;45', '中心公教楼406d', '2', '64','任选','','', 46, 0 ,0, 0, 'T202000300021', 0, 0 ,0,0)