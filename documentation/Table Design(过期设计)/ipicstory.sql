CREATE DATABASE  IF NOT EXISTS `ipicstory` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `ipicstory`;
-- MySQL dump 10.13  Distrib 5.5.24, for osx10.5 (i386)
--
-- Host: localhost    Database: ipicstory
-- ------------------------------------------------------
-- Server version	5.5.29

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `FEED`
--

DROP TABLE IF EXISTS `FEED`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `FEED` (
  `idFEEDS` int(11) NOT NULL AUTO_INCREMENT,
  `UKey` varchar(255) DEFAULT NULL,
  `timeLine` datetime DEFAULT NULL,
  `USER_idUSER` int(11) DEFAULT NULL,
  PRIMARY KEY (`idFEEDS`),
  KEY `FK20DD9EC1DEBC7E` (`USER_idUSER`),
  CONSTRAINT `FK20DD9EC1DEBC7E` FOREIGN KEY (`USER_idUSER`) REFERENCES `STORYFOLLOWER` (`idUSER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `COMMEND`
--

DROP TABLE IF EXISTS `COMMEND`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `COMMEND` (
  `idCOMMEND` int(11) NOT NULL AUTO_INCREMENT,
  `commendCode` varchar(255) DEFAULT NULL,
  `commendText` varchar(255) DEFAULT NULL,
  `PAGE_idPAGE` int(11) DEFAULT NULL,
  `STORY_idSTORY` int(11) DEFAULT NULL,
  PRIMARY KEY (`idCOMMEND`),
  KEY `FK63717A2F8E51CEC2` (`PAGE_idPAGE`),
  KEY `FK63717A2FB8F2ED2C` (`STORY_idSTORY`),
  CONSTRAINT `FK63717A2FB8F2ED2C` FOREIGN KEY (`STORY_idSTORY`) REFERENCES `STORY` (`idSTORY`),
  CONSTRAINT `FK63717A2F8E51CEC2` FOREIGN KEY (`PAGE_idPAGE`) REFERENCES `PAGE` (`idPAGE`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `LBS`
--

DROP TABLE IF EXISTS `LBS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `LBS` (
  `idLBS` int(11) NOT NULL AUTO_INCREMENT,
  `createTime` datetime DEFAULT NULL,
  `latitude` decimal(19,2) DEFAULT NULL,
  `longitude` decimal(19,2) DEFAULT NULL,
  `USER_idUSER` int(11) DEFAULT NULL,
  PRIMARY KEY (`idLBS`),
  KEY `FK1259DC1DEBC7E` (`USER_idUSER`),
  CONSTRAINT `FK1259DC1DEBC7E` FOREIGN KEY (`USER_idUSER`) REFERENCES `STORYFOLLOWER` (`idUSER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `FOLLOWER`
--

DROP TABLE IF EXISTS `FOLLOWER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `FOLLOWER` (
  `idFOLLOWERS` int(11) NOT NULL AUTO_INCREMENT,
  `bidirectional` tinyint(1) DEFAULT NULL,
  `userId` int(11) NOT NULL,
  `USER_idUSER` int(11) DEFAULT NULL,
  PRIMARY KEY (`idFOLLOWERS`),
  KEY `FK40A3101EC1DEBC7E` (`USER_idUSER`),
  CONSTRAINT `FK40A3101EC1DEBC7E` FOREIGN KEY (`USER_idUSER`) REFERENCES `STORYFOLLOWER` (`idUSER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `STORY`
--

DROP TABLE IF EXISTS `STORY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `STORY` (
  `idSTORY` int(11) NOT NULL AUTO_INCREMENT,
  `storyCode` varchar(255) DEFAULT NULL,
  `storyDesc` varchar(255) DEFAULT NULL,
  `storyName` varchar(255) DEFAULT NULL,
  `USER_idUSER` int(11) DEFAULT NULL,
  PRIMARY KEY (`idSTORY`),
  KEY `FK4B900D5C1DEBC7E` (`USER_idUSER`),
  CONSTRAINT `FK4B900D5C1DEBC7E` FOREIGN KEY (`USER_idUSER`) REFERENCES `STORYFOLLOWER` (`idUSER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `STORYFOLLOWER`
--

DROP TABLE IF EXISTS `STORYFOLLOWER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `STORYFOLLOWER` (
  `idSTORYFOLLOWER` int(11) NOT NULL,
  `followTime` datetime DEFAULT NULL,
  `userId` int(11) NOT NULL,
  `idUSER` int(11) NOT NULL AUTO_INCREMENT,
  `userCode` varchar(255) DEFAULT NULL,
  `userName` varchar(255) DEFAULT NULL,
  `userPass` varchar(255) DEFAULT NULL,
  `STORY_idSTORY` int(11) DEFAULT NULL,
  PRIMARY KEY (`idUSER`),
  KEY `FKD94C6BF3B8F2ED2C` (`STORY_idSTORY`),
  CONSTRAINT `FKD94C6BF3B8F2ED2C` FOREIGN KEY (`STORY_idSTORY`) REFERENCES `STORY` (`idSTORY`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `MOMENT`
--

DROP TABLE IF EXISTS `MOMENT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MOMENT` (
  `idMOMENT` int(11) NOT NULL AUTO_INCREMENT,
  `pMonDesc` varchar(255) DEFAULT NULL,
  `pMonIndex` int(11) NOT NULL,
  `STORY_idSTORY` int(11) DEFAULT NULL,
  PRIMARY KEY (`idMOMENT`),
  KEY `FK87E27600B8F2ED2C` (`STORY_idSTORY`),
  CONSTRAINT `FK87E27600B8F2ED2C` FOREIGN KEY (`STORY_idSTORY`) REFERENCES `STORY` (`idSTORY`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `USER`
--

DROP TABLE IF EXISTS `USER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USER` (
  `idUSER` int(11) NOT NULL AUTO_INCREMENT,
  `userCode` varchar(255) DEFAULT NULL,
  `userName` varchar(255) DEFAULT NULL,
  `userPass` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idUSER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `INTEREST`
--

DROP TABLE IF EXISTS `INTEREST`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `INTEREST` (
  `idINTEREST` int(11) NOT NULL AUTO_INCREMENT,
  `bidirectional` tinyint(1) DEFAULT NULL,
  `userId` int(11) NOT NULL,
  `USER_idUSER` int(11) DEFAULT NULL,
  PRIMARY KEY (`idINTEREST`),
  KEY `FK50A5972AC1DEBC7E` (`USER_idUSER`),
  CONSTRAINT `FK50A5972AC1DEBC7E` FOREIGN KEY (`USER_idUSER`) REFERENCES `STORYFOLLOWER` (`idUSER`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `PAGE`
--

DROP TABLE IF EXISTS `PAGE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PAGE` (
  `idPAGE` int(11) NOT NULL AUTO_INCREMENT,
  `mediaType` varchar(255) DEFAULT NULL,
  `mediaUrl` varchar(255) DEFAULT NULL,
  `FEED_idFEED` int(11) DEFAULT NULL,
  `Moment_idMoment` int(11) DEFAULT NULL,
  PRIMARY KEY (`idPAGE`),
  UNIQUE KEY `FEED_idFEED` (`FEED_idFEED`),
  KEY `FK255A8FFE1592F1` (`FEED_idFEED`),
  KEY `FK255A8F1FCEE453` (`Moment_idMoment`),
  CONSTRAINT `FK255A8F1FCEE453` FOREIGN KEY (`Moment_idMoment`) REFERENCES `MOMENT` (`idMOMENT`),
  CONSTRAINT `FK255A8FFE1592F1` FOREIGN KEY (`FEED_idFEED`) REFERENCES `FEED` (`idFEEDS`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping events for database 'ipicstory'
--

--
-- Dumping routines for database 'ipicstory'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-03-03  2:48:39
