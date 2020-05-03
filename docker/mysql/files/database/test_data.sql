-- MySQL dump 10.13  Distrib 5.7.30, for Linux (x86_64)
--
-- Host: localhost    Database: tron_light_cycles
-- ------------------------------------------------------
-- Server version	5.7.30

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'user','2020-05-02 13:02:36','2020-05-02 13:02:36'),(2,'moderator','2020-05-02 13:02:36','2020-05-02 13:02:36'),(3,'admin','2020-05-02 13:02:36','2020-05-02 13:02:36');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'xX_420NoScope_Xx','anna_bolika@active.com','$2a$08$aqCvdNlVBF52YmMN4SfbTO/o3nGw7cvmsQKsdNhaK4LOTt4Io0Gs6','Anna','Bolika','rgb(0,0,0)',69,'2020-05-02 13:02:56','2020-05-02 13:02:56'),(2,'qw65ef4asd6fq4q','wifi@password.com','$2a$08$T.OWk9lQIcu3LHlrmqqKAuGAanDih/LGvN5AFF0BSC0T6ZndZtmn6','Wifi','Password','rgb(0,0,255)',1,'2020-05-02 13:12:00','2020-05-02 13:12:00'),(3,'6erMike','mike@6er_what_else.ch','$2a$08$tdrbMQwon3XE3OHyVKl6cONLqbSwqfLNn6pVe54tRTQK7IIwMi9j6','Mike','Iten','rgb(128,64,255)',6,'2020-05-02 13:15:18','2020-05-02 13:15:18'),(4,'BestDev','php4live@yahoo.com','$2a$08$iSweuGxp.n1VIjoeIMkp1OY96I7tnwR2vm8Y2ymoyQogRG0SGUo3C','Hans','Müller','rgb(0,255,0)',666,'2020-05-02 13:17:09','2020-05-02 13:17:09'),(5,'vergeltungswaffe','v2@3reich.de','$2a$08$cDKRgXjjpgLmBgrFeuWym.LqJcr9WI3tXRiEwZZ2lvWRMcxNNBDkm','Heinrich','Himmler','rgb(0,0,0)',88,'2020-05-02 13:19:33','2020-05-02 13:19:33'),(6,'username','this@guy.com','$2a$08$DTuNOsVj0dRxs1daqF/NwuldfJ35gRR1T4QHujx5aYnOqIu4r6dYy','firstname','name','rgb(255,192,203)',22,'2020-05-02 13:21:12','2020-05-02 13:21:12'),(7,'minimalist','e@mail.com','$2a$08$CLSlWBd2I3UTNxtEkynPhen53Yj3H0ZE7WdMUcwjj/b4rbISN4BYi','','','rgb(0,0,0)',0,'2020-05-02 13:24:18','2020-05-02 13:24:18'),(8,'AussiePRO','straya@cunt.com.au','$2a$08$espemDL0XxpVk6pJZGceT.IwMHIvmCnNcfMOFJeDYutJ5ud8LUiEy','Oliver','Smith','rgb(138,43,226)',9999999,'2020-05-02 13:27:12','2020-05-02 13:27:12');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` VALUES ('2020-05-02 13:02:56','2020-05-02 13:02:56',1,1),('2020-05-02 13:12:00','2020-05-02 13:12:00',1,2),('2020-05-02 13:15:18','2020-05-02 13:15:18',1,3),('2020-05-02 13:17:09','2020-05-02 13:17:09',1,4),('2020-05-02 13:19:33','2020-05-02 13:19:33',1,5),('2020-05-02 13:21:12','2020-05-02 13:21:12',1,6),('2020-05-02 13:24:18','2020-05-02 13:24:18',1,7),('2020-05-02 13:27:12','2020-05-02 13:27:12',1,8);
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;