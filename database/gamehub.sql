-- MySQL dump 10.13  Distrib 8.0.17, for Win64 (x86_64)
--
-- Host: localhost    Database: gamehub
-- ------------------------------------------------------
-- Server version	8.0.17

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
-- Table structure for table `choice`
--

DROP TABLE IF EXISTS `choice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `choice` (
  `Id` int(11) NOT NULL,
  `SceneId` int(11) DEFAULT NULL,
  `Caption` varchar(4369) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `PK_Scene5_idx` (`SceneId`),
  CONSTRAINT `PK_Scene5` FOREIGN KEY (`SceneId`) REFERENCES `scenes` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `games`
--

DROP TABLE IF EXISTS `games`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `games` (
  `Id` int(11) NOT NULL,
  `Name` varchar(4369) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `PlayersCount` int(11) DEFAULT NULL,
  `StartSceneId1` int(11) DEFAULT NULL,
  `StartSceneId2` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `PK_Scenes_idx` (`StartSceneId1`),
  KEY `PK_Scenes2_idx` (`StartSceneId2`),
  KEY `PK_Scenes4_idx` (`StartSceneId2`),
  CONSTRAINT `PK_Scenes` FOREIGN KEY (`StartSceneId1`) REFERENCES `scenes` (`Id`),
  CONSTRAINT `PK_Scenes4` FOREIGN KEY (`StartSceneId2`) REFERENCES `scenes` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `imageresources`
--

DROP TABLE IF EXISTS `imageresources`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `imageresources` (
  `Id` int(11) NOT NULL,
  `Path` varchar(4369) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `Image` longblob,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `matrixvariant`
--

DROP TABLE IF EXISTS `matrixvariant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `matrixvariant` (
  `Id` int(11) NOT NULL,
  `SceneId` int(11) DEFAULT NULL,
  `MatrixPosition` varchar(4369) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `Value` int(11) DEFAULT NULL,
  `NextSceneId1` int(11) DEFAULT NULL,
  `NextSceneId2` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `PK_Scenes_idx` (`NextSceneId1`),
  KEY `PK_Scenes2_idx` (`NextSceneId1`),
  KEY `PK_Scenes6_idx` (`SceneId`),
  KEY `PK_Scenes7_idx` (`NextSceneId2`),
  CONSTRAINT `PK_Scenes2` FOREIGN KEY (`NextSceneId1`) REFERENCES `scenes` (`Id`),
  CONSTRAINT `PK_Scenes6` FOREIGN KEY (`SceneId`) REFERENCES `scenes` (`Id`),
  CONSTRAINT `PK_Scenes7` FOREIGN KEY (`NextSceneId2`) REFERENCES `scenes` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `scenes`
--

DROP TABLE IF EXISTS `scenes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `scenes` (
  `Id` int(11) NOT NULL,
  `SpriteId` int(11) DEFAULT NULL,
  `BackgroundId` int(11) DEFAULT NULL,
  `Text` varchar(4369) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `Type` varchar(4369) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `PK_Scenes2_idx` (`BackgroundId`),
  KEY `PK_Scenes3_idx` (`BackgroundId`),
  KEY `PK_Sprite_idx` (`SpriteId`),
  CONSTRAINT `PK_Scenes3` FOREIGN KEY (`BackgroundId`) REFERENCES `scenes` (`Id`),
  CONSTRAINT `PK_Sprite` FOREIGN KEY (`SpriteId`) REFERENCES `sprite` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sprite`
--

DROP TABLE IF EXISTS `sprite`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sprite` (
  `Id` int(11) NOT NULL,
  `PositionX` int(11) DEFAULT NULL,
  `PositionY` int(11) DEFAULT NULL,
  `ResourceId` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `PK_Resource_idx` (`ResourceId`),
  CONSTRAINT `PK_Resource` FOREIGN KEY (`ResourceId`) REFERENCES `imageresources` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping routines for database 'gamehub'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-10-06 16:49:15
