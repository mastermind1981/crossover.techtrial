
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
-- Dumping data for table `exam`
--

LOCK TABLES `exam` WRITE;
/*!40000 ALTER TABLE `exam` DISABLE KEYS */;
INSERT INTO `exam` VALUES (1,'The dummy test exam with only fake questions',10,'My first exam',3,5);
/*!40000 ALTER TABLE `exam` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Dumping data for table `question`
--

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
INSERT INTO `question` VALUES (1,'What if the capital of Great Britain?','\0',0,'The title of the question',1),(2,'Which cities are not in Europe?','',1,'Another title of the question',1),(3,'Which of the following is the name of island in Indonesia?','\0',2,'Question #3',1),(4,'What religion has a school named Zen?','\0',3,'The last question',1);
/*!40000 ALTER TABLE `question` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

--
-- Dumping data for table `answer`
--

LOCK TABLES `answer` WRITE;
/*!40000 ALTER TABLE `answer` DISABLE KEYS */;
INSERT INTO `answer` VALUES (1,0,'','London',1),(2,1,'\0','Paris',1),(3,2,'\0','Moscow',1),(4,3,'\0','Berlin',1),(5,0,'','Buenos Aires',2),(6,1,'\0','Paris',2),(7,2,'','Moscow',2),(8,3,'\0','Capetown',2),(9,0,'\0','Groovy',3),(10,1,'','Java',3),(11,2,'\0','Madagascar',3),(12,3,'\0','Scala',3),(13,0,'\0','Taoism',4),(14,1,'\0','Hinduism',4),(15,2,'\0','Judaism',4),(16,3,'','Buddhism',4);
/*!40000 ALTER TABLE `answer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `examuser`
--

LOCK TABLES `examuser` WRITE;
/*!40000 ALTER TABLE `examuser` DISABLE KEYS */;
INSERT INTO `examuser` VALUES (1,'','Test User','123','test'),(2,'','User for test','321','user');
/*!40000 ALTER TABLE `examuser` ENABLE KEYS */;
UNLOCK TABLES;



LOCK TABLES `user_roles` WRITE;
INSERT INTO `user_roles` (userid, ROLE)
VALUES (1, 'ROLE_USER'), (2, 'ROLE_USER');
UNLOCK TABLES;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-04-22 12:55:40
