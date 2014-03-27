SET FOREIGN_KEY_CHECKS=0;



DROP TABLE IF EXISTS genre CASCADE
;
DROP TABLE IF EXISTS record CASCADE
;
DROP TABLE IF EXISTS record_comment CASCADE
;
DROP TABLE IF EXISTS record_genre CASCADE
;
DROP TABLE IF EXISTS record_statistic CASCADE
;
DROP TABLE IF EXISTS record_tag CASCADE
;
DROP TABLE IF EXISTS rights CASCADE
;
DROP TABLE IF EXISTS role CASCADE
;
DROP TABLE IF EXISTS tag CASCADE
;
DROP TABLE IF EXISTS user CASCADE
;

CREATE TABLE genre
(
	id BIGINT NOT NULL AUTO_INCREMENT,
	name VARCHAR(50),
	created_by BIGINT NOT NULL,
	modified_by BIGINT NOT NULL,
	created_on DATETIME NOT NULL,
	modified_on DATETIME NOT NULL,
	published INTEGER NOT NULL,
	PRIMARY KEY (id),
	KEY (created_by),
	KEY (modified_by)

) 
;


CREATE TABLE record
(
	id BIGINT NOT NULL AUTO_INCREMENT,
	name VARCHAR(50) NOT NULL,
	path VARCHAR(255),
	description TEXT,
	user_id BIGINT NOT NULL,
	modified_by BIGINT NOT NULL,
	created_by BIGINT NOT NULL,
	modified_on DATETIME NOT NULL,
	created_on DATETIME NOT NULL,
	type VARCHAR(50) NOT NULL,
	PRIMARY KEY (id),
	KEY (user_id),
	KEY (created_by),
	KEY (modified_by)

) 
;


CREATE TABLE record_comment
(
	id BIGINT NOT NULL AUTO_INCREMENT,
	username VARCHAR(50) NOT NULL,
	comment TEXT NOT NULL,
	record_id BIGINT NOT NULL,
	modified_by VARCHAR(50) NOT NULL,
	created_by VARCHAR(50) NOT NULL,
	modified_on DATETIME NOT NULL,
	created_on DATETIME NOT NULL,
	PRIMARY KEY (id),
	KEY (record_id)

) 
;


CREATE TABLE record_genre
(
	record_id BIGINT NOT NULL,
	genre_id BIGINT NOT NULL,
	KEY (genre_id),
	KEY (record_id)

) 
;


CREATE TABLE record_statistic
(
	rating_count BIGINT NOT NULL DEFAULT 0,
	rating_sum BIGINT NOT NULL DEFAULT 0,
	rating_percent BIGINT NOT NULL DEFAULT 0,
	downloads BIGINT NOT NULL DEFAULT 0,
	record_id BIGINT NOT NULL,
	PRIMARY KEY (record_id)

) 
;


CREATE TABLE record_tag
(
	record_id BIGINT NOT NULL,
	tag_id BIGINT NOT NULL,
	KEY (record_id),
	KEY (tag_id)

) 
;


CREATE TABLE rights
(
	user_id BIGINT NOT NULL,
	role_id BIGINT NOT NULL,
	KEY (role_id)

) 
;


CREATE TABLE role
(
	id BIGINT NOT NULL AUTO_INCREMENT,
	code VARCHAR(50) NOT NULL,
	PRIMARY KEY (id)

) 
;


CREATE TABLE tag
(
	id BIGINT NOT NULL AUTO_INCREMENT,
	name VARCHAR(255) NOT NULL,
	created_by BIGINT NOT NULL,
	modified_by BIGINT NOT NULL,
	created_on DATETIME NOT NULL,
	modified_on DATETIME NOT NULL,
	PRIMARY KEY (id),
	KEY (created_by),
	KEY (modified_by)

) 
;


CREATE TABLE user
(
	id BIGINT NOT NULL AUTO_INCREMENT,
	email VARCHAR(50) NOT NULL,
	password VARCHAR(255) NOT NULL,
	enabled INTEGER NOT NULL,
	name VARCHAR(50) NOT NULL,
	PRIMARY KEY (id)

) 
;



SET FOREIGN_KEY_CHECKS=1;


ALTER TABLE genre ADD CONSTRAINT FK_genre_user_cr 
	FOREIGN KEY (created_by) REFERENCES user (id)
;

ALTER TABLE genre ADD CONSTRAINT FK_genre_user_mf 
	FOREIGN KEY (modified_by) REFERENCES user (id)
;

ALTER TABLE record ADD CONSTRAINT FK_preset_user 
	FOREIGN KEY (user_id) REFERENCES user (id)
;

ALTER TABLE record ADD CONSTRAINT FK_preset_user_cr 
	FOREIGN KEY (created_by) REFERENCES user (id)
;

ALTER TABLE record ADD CONSTRAINT FK_preset_user_mf 
	FOREIGN KEY (modified_by) REFERENCES user (id)
;

ALTER TABLE record_comment ADD CONSTRAINT FK_preset_comment_preset 
	FOREIGN KEY (record_id) REFERENCES record (id)
;

ALTER TABLE record_genre ADD CONSTRAINT FK_preset_genre_genre 
	FOREIGN KEY (genre_id) REFERENCES genre (id)
;

ALTER TABLE record_genre ADD CONSTRAINT FK_preset_genre_preset 
	FOREIGN KEY (record_id) REFERENCES record (id)
;

ALTER TABLE record_statistic ADD CONSTRAINT FK_record_statistic_record 
	FOREIGN KEY (record_id) REFERENCES record (id)
;

ALTER TABLE record_tag ADD CONSTRAINT FK_preset_tag_preset 
	FOREIGN KEY (record_id) REFERENCES record (id)
;

ALTER TABLE record_tag ADD CONSTRAINT FK_preset_tag_tag 
	FOREIGN KEY (tag_id) REFERENCES tag (id)
;

ALTER TABLE rights ADD CONSTRAINT FK_rights_roles 
	FOREIGN KEY (role_id) REFERENCES role (id)
;

ALTER TABLE rights ADD CONSTRAINT FK_rights_users 
	FOREIGN KEY (user_id) REFERENCES user (id)
;

ALTER TABLE tag ADD CONSTRAINT FK_tag_user_cr 
	FOREIGN KEY (created_by) REFERENCES user (id)
;

ALTER TABLE tag ADD CONSTRAINT FK_tag_user_mf 
	FOREIGN KEY (modified_by) REFERENCES user (id)
;
