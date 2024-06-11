/*
SQLyog Community
MySQL - 10.4.25-MariaDB : Database - smart_farming
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`smart_farming` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `smart_farming`;

/*Table structure for table `booking` */

DROP TABLE IF EXISTS `booking`;

CREATE TABLE `booking` (
  `booking_id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` int(11) NOT NULL,
  `farmer_id` int(11) NOT NULL,
  `amount` varchar(100) NOT NULL,
  `date` varchar(20) NOT NULL,
  `status` varchar(100) NOT NULL,
  PRIMARY KEY (`booking_id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `booking` */

/*Table structure for table `complaints` */

DROP TABLE IF EXISTS `complaints`;

CREATE TABLE `complaints` (
  `complaint_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `complaint` varchar(1000) NOT NULL,
  `reply` varchar(1000) NOT NULL,
  `date` varchar(100) NOT NULL,
  PRIMARY KEY (`complaint_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `complaints` */

insert  into `complaints`(`complaint_id`,`user_id`,`complaint`,`reply`,`date`) values 
(1,1,'i need some products.','pending','2022-11-08');

/*Table structure for table `croptype` */

DROP TABLE IF EXISTS `croptype`;

CREATE TABLE `croptype` (
  `type_id` int(11) NOT NULL AUTO_INCREMENT,
  `type_name` varchar(500) NOT NULL,
  `type_description` varchar(1000) NOT NULL,
  PRIMARY KEY (`type_id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Data for the table `croptype` */

insert  into `croptype`(`type_id`,`type_name`,`type_description`) values 
(1,'paddy','small crops'),
(2,'Rabbie','other crops'),
(3,'wheat','normal crop'),
(4,'rice','local item');

/*Table structure for table `delivery` */

DROP TABLE IF EXISTS `delivery`;

CREATE TABLE `delivery` (
  `delivery_id` int(11) NOT NULL AUTO_INCREMENT,
  `om_id` int(11) DEFAULT NULL,
  `logistic_id` int(11) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`delivery_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `delivery` */

/*Table structure for table `enquiry` */

DROP TABLE IF EXISTS `enquiry`;

CREATE TABLE `enquiry` (
  `enquiry_id` int(11) NOT NULL AUTO_INCREMENT,
  `farmer_id` int(11) NOT NULL,
  `description` varchar(1000) NOT NULL,
  `reply` varchar(1000) NOT NULL,
  `date` varchar(100) NOT NULL,
  PRIMARY KEY (`enquiry_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Data for the table `enquiry` */

/*Table structure for table `farmercrops` */

DROP TABLE IF EXISTS `farmercrops`;

CREATE TABLE `farmercrops` (
  `crop_id` int(11) NOT NULL AUTO_INCREMENT,
  `type_id` int(11) NOT NULL,
  `farmer_id` int(11) DEFAULT NULL,
  `crop_name` varchar(200) NOT NULL,
  `availability` varchar(200) NOT NULL,
  `description` varchar(1000) NOT NULL,
  `image` varchar(20000) NOT NULL,
  `amount` varchar(200) NOT NULL,
  PRIMARY KEY (`crop_id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `farmercrops` */

insert  into `farmercrops`(`crop_id`,`type_id`,`farmer_id`,`crop_name`,`availability`,`description`,`image`,`amount`) values 
(1,2,1,'potato','100','eat and enjoy','D:/Projects/Python/Pyhton - crop_yield/crop_yield/static/images/7a1f8f77-5215-4bd7-9d53-fba580d15049abc.jpg','208'),
(2,3,1,'corn','188','for health','D:/Projects/Python/Pyhton - crop_yield/crop_yield/static/images/7bb392b9-9780-459c-99f3-dc5cedcd4adbabc.jpg','108'),
(3,1,1,'wheat','18','good fot eat','static/images/7b1f1ff0-ef7e-4e5f-ae1c-3179d542f598abc.jpg','24'),
(4,3,1,'nnice','23','ggftygd','static/images/1ba51c7d-a65f-4ce1-8145-eb54ed796b94abc.jpg','250'),
(5,4,1,'*xxxxxxx','26','xxxcccttt','static/images/b7dbfd57-567e-42dd-933f-ce49bc9a2f7aabc.jpg','300');

/*Table structure for table `farmers` */

DROP TABLE IF EXISTS `farmers`;

CREATE TABLE `farmers` (
  `farmer_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) NOT NULL,
  `first_name` varchar(200) NOT NULL,
  `last_name` varchar(200) NOT NULL,
  `place` varchar(200) NOT NULL,
  `pincode` varchar(10) NOT NULL,
  `gender` varchar(20) DEFAULT NULL,
  `dob` varchar(100) DEFAULT NULL,
  `phone` varchar(20) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `latitude` varchar(50) NOT NULL,
  `longitude` varchar(50) NOT NULL,
  PRIMARY KEY (`farmer_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `farmers` */

insert  into `farmers`(`farmer_id`,`login_id`,`first_name`,`last_name`,`place`,`pincode`,`gender`,`dob`,`phone`,`email`,`latitude`,`longitude`) values 
(1,5,'arjun','ar','idukki','688851','male','23/08/2001','6238526459','sankusanku001@gmail.com','9.9763454','76.2862318');

/*Table structure for table `login` */

DROP TABLE IF EXISTS `login`;

CREATE TABLE `login` (
  `login_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `usertype` varchar(100) NOT NULL,
  PRIMARY KEY (`login_id`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

/*Data for the table `login` */

insert  into `login`(`login_id`,`username`,`password`,`usertype`) values 
(1,'admin','admin','admin'),
(2,'athul','athul','agricultureofficers'),
(3,'san','san','users'),
(4,'u','u','users'),
(5,'a','a','farmers'),
(8,'l','l','logistic');

/*Table structure for table `logistic` */

DROP TABLE IF EXISTS `logistic`;

CREATE TABLE `logistic` (
  `logistic_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `l_name` varchar(100) DEFAULT NULL,
  `l_place` varchar(100) DEFAULT NULL,
  `l_phone` varchar(100) DEFAULT NULL,
  `l_email` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`logistic_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `logistic` */

insert  into `logistic`(`logistic_id`,`login_id`,`l_name`,`l_place`,`l_phone`,`l_email`) values 
(1,8,'b','c','9874561230','a@gmail.com');

/*Table structure for table `news` */

DROP TABLE IF EXISTS `news`;

CREATE TABLE `news` (
  `news_id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(200) NOT NULL,
  `description` varchar(1000) NOT NULL,
  `date` varchar(100) NOT NULL,
  PRIMARY KEY (`news_id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `news` */

insert  into `news`(`news_id`,`title`,`description`,`date`) values 
(1,'championship','for normal use','2022-11-08 16:36:17'),
(2,'Hot News','Serials are going...','2022-11-08 16:36:37'),
(3,'Cenima','New movie Vikram.','2022-11-08 16:36:52');

/*Table structure for table `orderdetails` */

DROP TABLE IF EXISTS `orderdetails`;

CREATE TABLE `orderdetails` (
  `od_id` int(11) NOT NULL AUTO_INCREMENT,
  `om_id` int(11) NOT NULL,
  `crop_id` int(11) NOT NULL,
  `quantity` varchar(100) NOT NULL,
  `amount` varchar(100) NOT NULL,
  PRIMARY KEY (`od_id`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

/*Data for the table `orderdetails` */

insert  into `orderdetails`(`od_id`,`om_id`,`crop_id`,`quantity`,`amount`) values 
(8,5,5,'1','300'),
(7,5,2,'2','216'),
(6,4,5,'2','600'),
(5,4,3,'2','48');

/*Table structure for table `ordermaster` */

DROP TABLE IF EXISTS `ordermaster`;

CREATE TABLE `ordermaster` (
  `om_id` int(11) NOT NULL AUTO_INCREMENT,
  `date` varchar(100) NOT NULL,
  `user_id` int(11) NOT NULL,
  `total_amount` varchar(100) NOT NULL,
  `oder_status` varchar(100) NOT NULL,
  PRIMARY KEY (`om_id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `ordermaster` */

insert  into `ordermaster`(`om_id`,`date`,`user_id`,`total_amount`,`oder_status`) values 
(5,'2023-04-11',2,'516','pending'),
(4,'2023-04-11',2,'648','delivered');

/*Table structure for table `payment` */

DROP TABLE IF EXISTS `payment`;

CREATE TABLE `payment` (
  `payment_id` int(11) NOT NULL AUTO_INCREMENT,
  `booking_id` int(11) NOT NULL,
  `type` varchar(100) NOT NULL,
  `amount` varchar(100) NOT NULL,
  `pay_date` varchar(100) NOT NULL,
  PRIMARY KEY (`payment_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `payment` */

/*Table structure for table `products` */

DROP TABLE IF EXISTS `products`;

CREATE TABLE `products` (
  `product_id` int(11) NOT NULL AUTO_INCREMENT,
  `product` varchar(200) NOT NULL,
  `amount` varchar(100) NOT NULL,
  `description` varchar(500) NOT NULL,
  PRIMARY KEY (`product_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `products` */

insert  into `products`(`product_id`,`product`,`amount`,`description`) values 
(1,'Tractor','2000','for working'),
(2,'sickle','200','local use');

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) NOT NULL,
  `first_name` varchar(200) NOT NULL,
  `last_name` varchar(200) NOT NULL,
  `latitude` varchar(100) NOT NULL,
  `longitude` varchar(100) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `email` varchar(100) NOT NULL,
  `house_name` varchar(200) NOT NULL,
  `place` varchar(200) NOT NULL,
  `pincode` varchar(10) NOT NULL,
  `district` varchar(100) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `users` */

insert  into `users`(`user_id`,`login_id`,`first_name`,`last_name`,`latitude`,`longitude`,`phone`,`email`,`house_name`,`place`,`pincode`,`district`) values 
(1,3,'sankar','s','9.9763509','76.2862345','6238526459','sankusanku001@gmail.com','alpy','alpy','688523','alpy'),
(2,4,'john','honai','9.9763419','76.2862358','6238526459','mickumicku00@gmail.com','joo','ernakulam','688543','ernakulam');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
