-- MySQL dump 10.13  Distrib 8.0.39, for Linux (x86_64)
--
-- Host: localhost    Database: marvel
-- ------------------------------------------------------
-- Server version	8.0.39

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `nodes`
--

DROP TABLE IF EXISTS `nodes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nodes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `pregunta` text,
  `personaje` varchar(255) DEFAULT NULL,
  `parent_id` int DEFAULT NULL,
  `izquierdo_id` int DEFAULT NULL,
  `derecho_id` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nodes`
--

LOCK TABLES `nodes` WRITE;
/*!40000 ALTER TABLE `nodes` DISABLE KEYS */;
INSERT INTO `nodes` VALUES (1,'Es un superheroe?',NULL,NULL,2,3),(2,'Es parte de los Vengadores?',NULL,1,4,5),(3,'Es un villano?',NULL,1,6,7),(4,'Usa un traje metalico?',NULL,2,8,9),(5,'Vive en Nueva York?',NULL,2,10,11),(6,'Es de origen alienigena?',NULL,3,12,13),(7,'Tiene poderes mentales?',NULL,3,14,15),(8,'Es el lider?',NULL,4,16,17),(9,'Puede lanzar telaranas?',NULL,4,18,38),(10,'Es un hechicero?',NULL,5,20,52),(11,'Es un agente de SHIELD?',NULL,5,22,36),(12,'Tiene un ejercito?',NULL,6,24,34),(13,'Tiene la capacidad de cambiar de forma?',NULL,6,26,48),(14,'Es extremadamente inteligente?',NULL,7,28,29),(15,'Es conocido por su fuerza?',NULL,7,30,32),(16,NULL,'Iron Man',8,NULL,NULL),(17,NULL,'War Machine',8,NULL,NULL),(18,NULL,'Spider-Man',9,NULL,NULL),(19,NULL,'Black Widow',70,NULL,NULL),(20,NULL,'Doctor Strange',10,NULL,NULL),(21,NULL,'Scarlet Witch',52,NULL,NULL),(22,NULL,'Nick Fury',11,NULL,NULL),(23,NULL,'Maria Hill',66,NULL,NULL),(24,NULL,'Thanos',12,NULL,NULL),(25,NULL,'Loki',42,NULL,NULL),(26,NULL,'Mystique',13,NULL,NULL),(27,NULL,'Skrull',64,NULL,NULL),(28,NULL,'Professor X',14,NULL,NULL),(29,NULL,'Magneto',14,NULL,NULL),(30,NULL,'Red Hulk',15,NULL,NULL),(31,NULL,'Ghost Rider',68,NULL,NULL),(32,'Es pelirroja?',NULL,15,33,44),(33,NULL,'Mary Jane',32,NULL,NULL),(34,'Es el rey de los simbiontes?',NULL,12,35,42),(35,NULL,'Knull',34,NULL,NULL),(36,'Tiene los Huesos de Adamantium?',NULL,11,37,50),(37,NULL,'Wolverine',36,NULL,NULL),(38,'Es de color verde?',NULL,9,39,40),(39,NULL,'Hulk',38,NULL,NULL),(40,'Tiene un traje de Adamantium?',NULL,38,41,70),(41,NULL,'Black Phanter',40,NULL,NULL),(42,'Devora mundos?',NULL,34,43,25),(43,NULL,'Galactus',42,NULL,NULL),(44,'Esta hecho de plata?',NULL,32,45,46),(45,NULL,'Silver Surfer',44,NULL,NULL),(46,'Mata vampiros?',NULL,44,47,60),(47,NULL,'Blade',46,NULL,NULL),(48,'Es el rey de los vampiros?',NULL,13,49,56),(49,NULL,'Dracula',48,NULL,NULL),(50,'Tiene varios poderes aparte de lazar telaranas?',NULL,36,51,54),(51,NULL,'Miles Morales',50,NULL,NULL),(52,'Es ciego?',NULL,10,53,21),(53,NULL,'Daredevil',52,NULL,NULL),(54,'Puede usar el Shac-Fu?',NULL,50,55,58),(55,NULL,'Iron Fist',54,NULL,NULL),(56,'Tiene la mejor precision?',NULL,48,57,62),(57,NULL,'Bullseye',56,NULL,NULL),(58,'Esta hecho de roca?',NULL,54,59,66),(59,NULL,'La Cosa',58,NULL,NULL),(60,'Quiere fotos del hombre arana?',NULL,46,61,68),(61,NULL,'J.Jhonah Jameson',60,NULL,NULL),(62,'Utiliza un aerodeslizador?',NULL,56,63,64),(63,NULL,'Duende Verde',62,NULL,NULL),(64,'Tiene tentaculos mecanicos?',NULL,62,65,27),(65,NULL,'Doctor Octopus',64,NULL,NULL),(66,'Puede controlar el clima?',NULL,58,67,23),(67,NULL,'Tormenta',66,NULL,NULL),(68,'Puede romper la cuarta pared?',NULL,60,69,31),(69,NULL,'DeadPool',68,NULL,NULL),(70,'Se puede agrandar y encoger?',NULL,40,71,19),(71,NULL,'Ant-Man',70,NULL,NULL);
/*!40000 ALTER TABLE `nodes` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-18 17:47:31
