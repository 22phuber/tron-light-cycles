/*
	tron_light_cycles database

	this script delete existing tron_light_cycles database
	and creates a new empty tron_light_cycles database
*/
DROP DATABASE IF EXISTS tron_light_cycles;
CREATE DATABASE tron_light_cycles;
ALTER DATABASE tron_light_cycles CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE tron_light_cycles;

/*
	userprofile table
*/
CREATE TABLE rider(
	username varchar(255) PRIMARY KEY,
	firstname varchar(255),
	name varchar(255),
	password varchar(64) NOT NULL,
	email varchar(255) NOT NULL,
	cycle_color varchar(20) DEFAULT 'white',
	ranking int UNSIGNED DEFAULT 0
);

