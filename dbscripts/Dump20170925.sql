--
-- Table structure for table `files`
--

DROP TABLE IF EXISTS `files`;
CREATE TABLE `files` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `originalname` varchar(450) DEFAULT NULL,
  `uid` varchar(100) DEFAULT NULL,
  `upload_date` datetime DEFAULT NULL,
  `location` varchar(450) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Table structure for table `users_files`
--

DROP TABLE IF EXISTS `users_files`;
CREATE TABLE `users_files` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_user` int(11) DEFAULT NULL,
  `id_file` int(11) DEFAULT NULL,
  `access_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_users_idx` (`id_user`),
  KEY `fk_files_idx` (`id_file`),
  CONSTRAINT `fk_files` FOREIGN KEY (`id_file`) REFERENCES `files` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_users` FOREIGN KEY (`id_user`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
