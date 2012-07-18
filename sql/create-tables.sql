-- MySQL dump 10.13  Distrib 5.5.16, for osx10.6 (i386)
--
-- Host: localhost    Database: waisda
-- ------------------------------------------------------
-- Server version	5.5.16

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
-- Table structure for table `MatchingTag`
--

DROP TABLE IF EXISTS `MatchingTag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MatchingTag` (
  `lo` varchar(63) NOT NULL DEFAULT '' COMMENT 'The low side of the matching pair, normalized (lo < hi)',
  `hi` varchar(63) NOT NULL DEFAULT '' COMMENT 'The high side of the matching pair, normalized (lo < hi)',
  PRIMARY KEY (`lo`,`hi`),
  KEY `hi` (`hi`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='Pairs of matching normalized tags';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Video`
--

DROP TABLE IF EXISTS `Video`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Video` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL COMMENT 'Title used in summaries, headers etc',
  `duration` int(11) NOT NULL COMMENT 'Length in ms',
  `imageUrl` varchar(255) DEFAULT NULL COMMENT 'URL of preview image',
  `enabled` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Whether this video is available for new games',
  `playerType` varchar(15) NOT NULL DEFAULT 'JW' COMMENT 'Either ''JW'' or ''NPO''',
  `sourceUrl` varchar(255) DEFAULT NULL COMMENT 'For playerType JW',
  `fragmentID` varchar(255) DEFAULT NULL COMMENT 'For playerType NPO',
  `sectionNid` int(11) DEFAULT NULL COMMENT 'For playerType NPO',
  `startTime` int(11) DEFAULT NULL COMMENT 'For playerType NPO',
  PRIMARY KEY (`id`),
  UNIQUE KEY `fragmentID` (`fragmentID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='Videos available for play';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `DictionaryEntry`
--

DROP TABLE IF EXISTS `DictionaryEntry`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DictionaryEntry` (
  `normalizedTag` varchar(63) NOT NULL DEFAULT '' COMMENT 'Normalized version of tag',
  `dictionary` varchar(63) NOT NULL DEFAULT '' COMMENT 'Dictionary in which tag belongs',
  PRIMARY KEY (`normalizedTag`,`dictionary`),
  KEY `type` (`dictionary`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='Tags that are awarded extra points';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Game`
--

DROP TABLE IF EXISTS `Game`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Game` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `start` datetime DEFAULT NULL COMMENT 'Starting date',
  `initiator_id` int(11) DEFAULT NULL COMMENT 'User who created this session',
  `video_id` int(11) NOT NULL COMMENT 'Video played',
  `countExistingVideoTags` int(11) NOT NULL DEFAULT '0' COMMENT 'Number of existing tags at creation time',
  PRIMARY KEY (`id`),
  KEY `FK21C012337EBE01` (`video_id`),
  KEY `FK21C0122F5F75EF` (`initiator_id`),
  KEY `FK21C0123B4EF296` (`video_id`),
  KEY `FK21C0121F1BDABA` (`initiator_id`),
  KEY `start` (`start`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='A session in which a specific video is played';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `TagEntry`
--

DROP TABLE IF EXISTS `TagEntry`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TagEntry` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `dictionary` varchar(31) CHARACTER SET utf8 DEFAULT NULL COMMENT 'Dictionary in which tag is found (awards extra points)',
  `normalizedTag` varchar(63) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT 'Normalized version of tag',
  `score` int(11) NOT NULL COMMENT 'Computed score (stored for performance)',
  `tag` varchar(63) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT 'Tag as entered by user',
  `gametime` int(11) NOT NULL COMMENT 'Time relative to video, in ms',
  `typingDuration` int(11) NOT NULL COMMENT 'How long it took the user to type the tag, in ms',
  `game_id` int(11) NOT NULL COMMENT 'Game to which tag belongs',
  `owner_id` int(11) NOT NULL COMMENT 'Player who entered the tag',
  `matchingTagEntry_id` int(11) DEFAULT NULL COMMENT 'Matching tag, if any (may be set later; awards extra points)',
  `pioneer` tinyint(1) NOT NULL COMMENT 'Whether there was no match when tag was entered',
  `creationDate` datetime NOT NULL COMMENT 'Date at which tag was entered',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `User` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `creationDate` datetime NOT NULL COMMENT 'Date at which user was created',
  `email` varchar(255) DEFAULT NULL COMMENT 'Email address of user; used for login',
  `name` varchar(255) DEFAULT NULL COMMENT 'Username for public display',
  `password` varchar(255) DEFAULT NULL COMMENT 'Encrypted password',
  `dateOfBirth` datetime DEFAULT NULL COMMENT 'Date of birth',
  `usernameFacebook` varchar(255) DEFAULT NULL COMMENT 'Facebook username or profile URL',
  `usernameHyves` varchar(255) DEFAULT NULL COMMENT 'Hyves username',
  `usernameTwitter` varchar(255) DEFAULT NULL COMMENT 'Twitter username',
  `gender` varchar(15) DEFAULT NULL COMMENT 'Either ''MALE'' or ''FEMALE''',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='Users who participated in one or more games';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ResetPassword`
--

DROP TABLE IF EXISTS `ResetPassword`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ResetPassword` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT 'User whose password to reset',
  `resetKey` varchar(70) DEFAULT NULL COMMENT 'Encrypted key that was sent to the user',
  `creationDate` datetime NOT NULL COMMENT 'Date at which the reset key was created',
  `resetDate` datetime DEFAULT NULL COMMENT 'Date at which the reset key was used to reset the password',
  PRIMARY KEY (`id`),
  UNIQUE KEY `resetKey` (`resetKey`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='Reset password requests for users';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Participant`
--

DROP TABLE IF EXISTS `Participant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Participant` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT 'Player joining',
  `game_id` int(11) NOT NULL COMMENT 'Game joined',
  `joinedOn` datetime NOT NULL COMMENT 'Time at which player joined the game',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='A participating User in a Game; used to show player list';
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-07-18 14:27:59
