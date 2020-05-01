/*
 * tron_light_cycles database
 */
DROP DATABASE IF EXISTS tron_light_cycles;

CREATE DATABASE `tron_light_cycles`
/*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */
ALTER DATABASE tron_light_cycles CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE tron_light_cycles;

/*
 *	users table
 */
Create Table: CREATE TABLE `users` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`username` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
	`email` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
	`password` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
	`firstname` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
	`name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
	`cycle_color` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
	`ranking` bigint(20) unsigned DEFAULT NULL,
	`createdAt` datetime NOT NULL,
	`updatedAt` datetime NOT NULL,
	PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci
/*
 *	roles table
 */
Create Table: CREATE TABLE `roles` (
	`id` int(11) NOT NULL,
	`name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
	`createdAt` datetime NOT NULL,
	`updatedAt` datetime NOT NULL,
	PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci
/*
 *	user_roles table
 */
Create Table: CREATE TABLE `user_roles` (
	`createdAt` datetime NOT NULL,
	`updatedAt` datetime NOT NULL,
	`roleId` int(11) NOT NULL,
	`userId` int(11) NOT NULL,
	PRIMARY KEY (`roleId`, `userId`),
	KEY `userId` (`userId`),
	CONSTRAINT `user_roles_ibfk_1` FOREIGN KEY (`roleId`) REFERENCES `roles` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT `user_roles_ibfk_2` FOREIGN KEY (`userId`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci