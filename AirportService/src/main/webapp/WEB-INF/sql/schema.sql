DROP DATABASE IF EXISTS airport;
CREATE DATABASE airport;

USE airport;

CREATE TABLE users (
	id          INT			   NOT NULL AUTO_INCREMENT,
    username    VARCHAR(10)    NOT NULL,
    password    VARCHAR(32)    NOT NULL,
    enabled     TINYINT,
    PRIMARY KEY (id)
);

CREATE TABLE authorities (
	user_id		INT			   NOT NULL,
    authority   VARCHAR(15)    NOT NULL,
    INDEX user_id (user_id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE employee (
	id			INT				NOT NULL AUTO_INCREMENT,
	first_name  VARCHAR(20)		NOT NULL,
	last_name  	VARCHAR(20)		NOT NULL,
	email		VARCHAR(30)		NOT NULL,
	job			VARCHAR(20)		NOT NULL,
	free		TINYINT			NOT NULL DEFAULT 1,
	PRIMARY KEY (id)
);
