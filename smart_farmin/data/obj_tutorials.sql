/*
SQLyog Community v13.1.6 (64 bit)
MySQL - 5.7.9 : Database - crop_yield
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`crop_yield` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `crop_yield`;

/*Table structure for table `tutorials` */

DROP TABLE IF EXISTS `tutorials`;

CREATE TABLE `tutorials` (
  `tutorial_id` int(11) NOT NULL AUTO_INCREMENT,
  `officer_id` int(11) NOT NULL,
  `title` varchar(500) NOT NULL,
  `description` varchar(500) DEFAULT NULL,
  `file_path` varchar(500) NOT NULL,
  `file_type` varchar(500) NOT NULL,
  PRIMARY KEY (`tutorial_id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `tutorials` */

insert  into `tutorials`(`tutorial_id`,`officer_id`,`title`,`description`,`file_path`,`file_type`) values 
(1,1,'paddy','new crops are arrived','static/cfb25342-563a-486e-9c7d-f5bda1eadf2amsg1869756866-275.jpg','img'),
(2,1,'tomato','new crops are arrived....','static/175b736d-b2e6-4482-b0bc-7d753bf0afe3','img');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;