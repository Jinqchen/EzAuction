-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: ezauction2
-- ------------------------------------------------------
-- Server version	8.0.22

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `auction`
--

DROP TABLE IF EXISTS `auction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `auction` (
  `auctionID` int NOT NULL,
  `buyerID` char(11) DEFAULT NULL,
  `openingTime` datetime NOT NULL,
  `openingBid` int DEFAULT NULL,
  `closingBid` int DEFAULT NULL,
  `currentBid` int DEFAULT NULL,
  `currentHigh` int DEFAULT NULL,
  `reserve` int DEFAULT NULL,
  `copiesSold` int DEFAULT NULL,
  `increment` int DEFAULT NULL,
  PRIMARY KEY (`auctionID`),
  KEY `buyerID` (`buyerID`),
  CONSTRAINT `auction_ibfk_1` FOREIGN KEY (`buyerID`) REFERENCES `customer` (`customerID`) ON UPDATE CASCADE,
  CONSTRAINT `auction_chk_1` CHECK ((`openingBid` >= 0)),
  CONSTRAINT `auction_chk_2` CHECK ((`closingBid` >= 0)),
  CONSTRAINT `auction_chk_3` CHECK ((`copiesSold` >= 0)),
  CONSTRAINT `auction_chk_4` CHECK ((`increment` > 0))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auction`
--

LOCK TABLES `auction` WRITE;
/*!40000 ALTER TABLE `auction` DISABLE KEYS */;
INSERT INTO `auction` VALUES (1,'111-11-1112','2008-12-01 00:00:00',5,30,NULL,NULL,10,4,1),(2,'111-11-1112','2008-12-02 00:00:00',1000,3000,NULL,NULL,2000,1,10),(3,'111-11-1113','2008-09-01 00:00:00',15,30,NULL,NULL,16,2,1),(4,'111-11-1113','2008-10-01 00:00:00',10,18,NULL,NULL,10,5,1),(5,'111-11-1114','2021-10-02 00:00:00',80,150,NULL,NULL,90,1,5),(6,'111-11-1114','2021-08-02 00:00:00',100,200,NULL,NULL,110,1,10);
/*!40000 ALTER TABLE `auction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bidby`
--

DROP TABLE IF EXISTS `bidby`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bidby` (
  `auctionID` int NOT NULL,
  `customerID` char(11) NOT NULL,
  `bidTime` datetime NOT NULL,
  `bidPrice` int DEFAULT NULL,
  `currentBid` int DEFAULT NULL,
  `currentWinner` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`auctionID`,`customerID`,`bidTime`),
  KEY `customerID` (`customerID`),
  CONSTRAINT `bidby_ibfk_1` FOREIGN KEY (`auctionID`) REFERENCES `auction` (`auctionID`) ON UPDATE CASCADE,
  CONSTRAINT `bidby_ibfk_2` FOREIGN KEY (`customerID`) REFERENCES `customer` (`customerID`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bidby`
--

LOCK TABLES `bidby` WRITE;
/*!40000 ALTER TABLE `bidby` DISABLE KEYS */;
INSERT INTO `bidby` VALUES (1,'111-11-1111','2008-12-06 00:00:00',10,9,'haixia'),(1,'111-11-1111','2008-12-07 00:00:00',10,10,'haixia'),(1,'111-11-1111','2008-12-08 00:00:00',15,11,'shiyong'),(1,'111-11-1112','2008-12-05 00:00:00',10,5,'haixia'),(2,'111-11-1112','2008-12-05 00:00:00',3000,NULL,NULL),(3,'111-11-1111','2008-12-07 00:00:00',25,NULL,NULL),(3,'111-11-1113','2008-09-02 00:00:00',30,NULL,NULL),(4,'111-11-1113','2008-10-02 00:00:00',18,NULL,NULL),(5,'111-11-1114','2021-10-07 00:00:00',150,NULL,NULL),(6,'111-11-1114','2021-08-05 00:00:00',200,NULL,NULL);
/*!40000 ALTER TABLE `bidby` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `customerID` char(11) NOT NULL,
  `creditCardNumber` char(19) DEFAULT NULL,
  `rating` int DEFAULT NULL,
  `itemsPurchased` int DEFAULT '0',
  `itemsSold` int DEFAULT '0',
  PRIMARY KEY (`customerID`),
  CONSTRAINT `customer_ibfk_1` FOREIGN KEY (`customerID`) REFERENCES `person` (`SSN`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `customer_chk_1` CHECK (((`rating` >= 0) and (`rating` <= 5))),
  CONSTRAINT `customer_chk_2` CHECK ((`itemsPurchased` >= 0)),
  CONSTRAINT `customer_chk_3` CHECK ((`itemsSold` >= 0))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES ('111-11-1111','1234-5678-1234-5678',5,0,2),('111-11-1112','5678-1234-5678-1234',5,2,2),('111-11-1113','2345-6789-2345-6789',NULL,2,1),('111-11-1114','6789-2345-6789-2345',NULL,2,1);
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `customermostrevenue`
--

DROP TABLE IF EXISTS `customermostrevenue`;
/*!50001 DROP VIEW IF EXISTS `customermostrevenue`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `customermostrevenue` AS SELECT 
 1 AS `sellerID`,
 1 AS `sellerName`,
 1 AS `TOTALPRICE`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `described`
--

DROP TABLE IF EXISTS `described`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `described` (
  `auctionID` int NOT NULL,
  `itemID` int NOT NULL,
  PRIMARY KEY (`auctionID`,`itemID`),
  KEY `itemID` (`itemID`),
  CONSTRAINT `described_ibfk_1` FOREIGN KEY (`auctionID`) REFERENCES `auction` (`auctionID`) ON UPDATE CASCADE,
  CONSTRAINT `described_ibfk_2` FOREIGN KEY (`itemID`) REFERENCES `item` (`itemID`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `described`
--

LOCK TABLES `described` WRITE;
/*!40000 ALTER TABLE `described` DISABLE KEYS */;
INSERT INTO `described` VALUES (1,1),(2,2),(3,3),(4,4),(5,5),(6,6);
/*!40000 ALTER TABLE `described` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee` (
  `employeeID` char(11) NOT NULL,
  `startDate` date DEFAULT NULL,
  `hourlyRate` float DEFAULT NULL,
  PRIMARY KEY (`employeeID`),
  CONSTRAINT `employee_ibfk_1` FOREIGN KEY (`employeeID`) REFERENCES `person` (`SSN`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `employee_chk_1` CHECK ((`hourlyRate` > 0.0))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES ('123-45-6789','1998-11-01',60),('789-12-3456','1998-11-01',50);
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item`
--

DROP TABLE IF EXISTS `item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `item` (
  `itemID` int NOT NULL,
  `itemType` varchar(20) NOT NULL,
  `itemName` varchar(20) NOT NULL,
  `numCopies` int DEFAULT NULL,
  `yearManufactured` int DEFAULT NULL,
  `itemDescription` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`itemID`),
  CONSTRAINT `item_chk_1` CHECK ((`numCopies` >= 0))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item`
--

LOCK TABLES `item` WRITE;
/*!40000 ALTER TABLE `item` DISABLE KEYS */;
INSERT INTO `item` VALUES (1,'DVD','Titanic',4,2005,'Nice 2 hours Movie \"Titanic\" DVD'),(2,'Car','Nissan Sentra',1,2007,'A car of brand Nissan Sentra'),(3,'DVD','Schindlers List',4,2006,'Greate Movie DVD \"Schindlers List\"'),(4,'Food','Water',10,2005,'A bottle of water categoried as food'),(5,'Elec','AirPods',1,2020,'Electric Device AirPods wirelessly connected to your iPhone made in year of 2020. Great Experience.'),(6,'Elec','AirPods',1,2021,'Electric Device AirPods wirelessly connected to your iPhone made in year of 2021. Great Experience.');
/*!40000 ALTER TABLE `item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `person`
--

DROP TABLE IF EXISTS `person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `person` (
  `SSN` char(11) NOT NULL,
  `firstName` varchar(10) NOT NULL,
  `lastName` varchar(10) NOT NULL,
  `email` varchar(30) NOT NULL,
  `address` varchar(20) DEFAULT NULL,
  `city` varchar(20) DEFAULT NULL,
  `state` varchar(20) DEFAULT NULL,
  `zipCode` int DEFAULT NULL,
  `passwords` varchar(20) NOT NULL,
  `roles` varchar(20) DEFAULT 'customer',
  `telephone` char(12) NOT NULL,
  PRIMARY KEY (`SSN`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `telephone` (`telephone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `person`
--

LOCK TABLES `person` WRITE;
/*!40000 ALTER TABLE `person` DISABLE KEYS */;
INSERT INTO `person` VALUES ('111-11-1111','Shiyong','Lu','shiyong@cs.sunysb.edu','123 Success Street','Stony Brook','NY',11790,'88888888','customer','5166328959'),('111-11-1112','Haixia','Du','dhaixia@cs.sunysb.edu','456 Fortune Road','Stony Brook','NY',11790,'88888888','customer','5166324360'),('111-11-1113','John','Smith','shlu@ic.sunysb.edu','789 Peace Blvd','Los Angeles','CA',12345,'88888888','customer','4124434321'),('111-11-1114','Lewis','Phil','customerpml@cs.sunysb.edu','135 Knowledge Lane','Stony Brook','NY',11790,'88888888','customer','5166668888'),('123-45-6789','David','Smith','smithd@cs.sunysb.edu','123 College road','Stony Brook','NY',11790,'88888888','manager','516-215-2345'),('789-12-3456','David','Warren','smithw@cs.sunysb.edu','456 Sunken Street','Stony Brook','NY',11790,'88888888','employee','516-632-9987');
/*!40000 ALTER TABLE `person` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post`
--

DROP TABLE IF EXISTS `post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post` (
  `auctionID` int NOT NULL,
  `customerID` char(11) NOT NULL,
  `postTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `expireTime` datetime NOT NULL,
  PRIMARY KEY (`auctionID`,`customerID`),
  KEY `customerID` (`customerID`),
  CONSTRAINT `post_ibfk_1` FOREIGN KEY (`auctionID`) REFERENCES `auction` (`auctionID`) ON UPDATE CASCADE,
  CONSTRAINT `post_ibfk_2` FOREIGN KEY (`customerID`) REFERENCES `customer` (`customerID`) ON UPDATE CASCADE,
  CONSTRAINT `CHK_ExpireTime` CHECK ((`expireTime` > `postTime`))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post`
--

LOCK TABLES `post` WRITE;
/*!40000 ALTER TABLE `post` DISABLE KEYS */;
INSERT INTO `post` VALUES (1,'111-11-1114','2008-11-30 00:00:00','2008-12-31 00:00:00'),(2,'111-11-1113','2008-11-30 00:00:00','2008-12-31 00:00:00'),(3,'111-11-1111','2008-08-31 00:00:00','2008-09-15 00:00:00'),(4,'111-11-1111','2008-09-30 00:00:00','2008-10-15 00:00:00'),(5,'111-11-1112','2021-09-29 00:00:00','2021-11-29 00:00:00'),(6,'111-11-1112','2021-07-31 00:00:00','2021-09-14 00:00:00');
/*!40000 ALTER TABLE `post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supervisedby`
--

DROP TABLE IF EXISTS `supervisedby`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `supervisedby` (
  `auctionID` int NOT NULL,
  `employeeID` char(11) NOT NULL,
  PRIMARY KEY (`auctionID`,`employeeID`),
  KEY `employeeID` (`employeeID`),
  CONSTRAINT `supervisedby_ibfk_1` FOREIGN KEY (`auctionID`) REFERENCES `auction` (`auctionID`) ON UPDATE CASCADE,
  CONSTRAINT `supervisedby_ibfk_2` FOREIGN KEY (`employeeID`) REFERENCES `employee` (`employeeID`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supervisedby`
--

LOCK TABLES `supervisedby` WRITE;
/*!40000 ALTER TABLE `supervisedby` DISABLE KEYS */;
INSERT INTO `supervisedby` VALUES (1,'789-12-3456'),(2,'789-12-3456'),(3,'789-12-3456'),(4,'789-12-3456'),(5,'789-12-3456'),(6,'789-12-3456');
/*!40000 ALTER TABLE `supervisedby` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Final view structure for view `customermostrevenue`
--

/*!50001 DROP VIEW IF EXISTS `customermostrevenue`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `customermostrevenue` AS select `p`.`customerID` AS `sellerID`,concat(`pe`.`firstName`,' ',`pe`.`lastName`) AS `sellerName`,sum(`a`.`closingBid`) AS `TOTALPRICE` from (((`post` `p` join `auction` `a`) join `person` `pe`) join `customer` `c`) where ((`p`.`auctionID` = `a`.`auctionID`) and (`pe`.`SSN` = `c`.`customerID`) and (`p`.`customerID` = `c`.`customerID`)) group by `p`.`customerID` order by `TOTALPRICE` desc limit 1 */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-12-09 15:40:56
