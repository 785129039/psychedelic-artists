SET FOREIGN_KEY_CHECKS=0;



DROP TABLE IF EXISTS genre CASCADE
;
DROP TABLE IF EXISTS preset CASCADE
;
DROP TABLE IF EXISTS preset_comment CASCADE
;
DROP TABLE IF EXISTS preset_genre CASCADE
;
DROP TABLE IF EXISTS preset_tag CASCADE
;
DROP TABLE IF EXISTS rights CASCADE
;
DROP TABLE IF EXISTS role CASCADE
;
DROP TABLE IF EXISTS sample CASCADE
;
DROP TABLE IF EXISTS sample_comment CASCADE
;
DROP TABLE IF EXISTS sample_genre CASCADE
;
DROP TABLE IF EXISTS sample_tag CASCADE
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


CREATE TABLE preset
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
	PRIMARY KEY (id),
	KEY (user_id),
	KEY (created_by),
	KEY (modified_by)

) 
;


CREATE TABLE preset_comment
(
	id BIGINT NOT NULL AUTO_INCREMENT,
	username VARCHAR(50) NOT NULL,
	comment TEXT NOT NULL,
	preset_id BIGINT NOT NULL,
	modified_by VARCHAR(50) NOT NULL,
	created_by VARCHAR(50) NOT NULL,
	modified_on DATETIME NOT NULL,
	created_on DATETIME NOT NULL,
	PRIMARY KEY (id),
	KEY (preset_id)

) 
;


CREATE TABLE preset_genre
(
	preset_id BIGINT NOT NULL,
	genre_id BIGINT NOT NULL,
	KEY (genre_id),
	KEY (preset_id)

) 
;


CREATE TABLE preset_tag
(
	preset_id BIGINT NOT NULL,
	tag_id BIGINT NOT NULL,
	KEY (preset_id),
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


CREATE TABLE sample
(
	id BIGINT NOT NULL AUTO_INCREMENT,
	user_id BIGINT NOT NULL,
	name VARCHAR(50) NOT NULL,
	description TEXT,
	path VARCHAR(255) NOT NULL,
	modified_on DATETIME NOT NULL,
	created_on DATETIME NOT NULL,
	created_by BIGINT NOT NULL,
	modified_by BIGINT NOT NULL,
	PRIMARY KEY (id),
	KEY (created_by),
	KEY (modified_by),
	KEY (user_id)

) 
;


CREATE TABLE sample_comment
(
	id BIGINT NOT NULL AUTO_INCREMENT,
	username VARCHAR(50) NOT NULL,
	comment TEXT NOT NULL,
	sample_id BIGINT NOT NULL,
	created_by VARCHAR(50) NOT NULL,
	modified_by VARCHAR(50) NOT NULL,
	created_on DATETIME NOT NULL,
	modified_on DATETIME NOT NULL,
	PRIMARY KEY (id),
	KEY (sample_id)

) 
;


CREATE TABLE sample_genre
(
	sample_id BIGINT NOT NULL,
	genre_id BIGINT NOT NULL,
	KEY (genre_id),
	KEY (sample_id)

) 
;


CREATE TABLE sample_tag
(
	sample_id BIGINT NOT NULL,
	tag_id BIGINT NOT NULL,
	KEY (sample_id),
	KEY (tag_id)

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

ALTER TABLE preset ADD CONSTRAINT FK_preset_user 
	FOREIGN KEY (user_id) REFERENCES user (id)
;

ALTER TABLE preset ADD CONSTRAINT FK_preset_user_cr 
	FOREIGN KEY (created_by) REFERENCES user (id)
;

ALTER TABLE preset ADD CONSTRAINT FK_preset_user_mf 
	FOREIGN KEY (modified_by) REFERENCES user (id)
;

ALTER TABLE preset_comment ADD CONSTRAINT FK_preset_comment_preset 
	FOREIGN KEY (preset_id) REFERENCES preset (id)
;

ALTER TABLE preset_genre ADD CONSTRAINT FK_preset_genre_genre 
	FOREIGN KEY (genre_id) REFERENCES genre (id)
;

ALTER TABLE preset_genre ADD CONSTRAINT FK_preset_genre_preset 
	FOREIGN KEY (preset_id) REFERENCES preset (id)
;

ALTER TABLE preset_tag ADD CONSTRAINT FK_preset_tag_preset 
	FOREIGN KEY (preset_id) REFERENCES preset (id)
;

ALTER TABLE preset_tag ADD CONSTRAINT FK_preset_tag_tag 
	FOREIGN KEY (tag_id) REFERENCES tag (id)
;

ALTER TABLE rights ADD CONSTRAINT FK_rights_roles 
	FOREIGN KEY (role_id) REFERENCES role (id)
;

ALTER TABLE rights ADD CONSTRAINT FK_rights_users 
	FOREIGN KEY (user_id) REFERENCES user (id)
;

ALTER TABLE sample ADD CONSTRAINT FK_sample_user_cr 
	FOREIGN KEY (created_by) REFERENCES user (id)
;

ALTER TABLE sample ADD CONSTRAINT FK_sample_user_mf 
	FOREIGN KEY (modified_by) REFERENCES user (id)
;

ALTER TABLE sample ADD CONSTRAINT FK_samples_users 
	FOREIGN KEY (user_id) REFERENCES user (id)
;

ALTER TABLE sample_comment ADD CONSTRAINT FK_sample_comments_samples 
	FOREIGN KEY (sample_id) REFERENCES sample (id)
;

ALTER TABLE sample_genre ADD CONSTRAINT FK_sample_genre_genre 
	FOREIGN KEY (genre_id) REFERENCES genre (id)
;

ALTER TABLE sample_genre ADD CONSTRAINT FK_sample_genre_sample 
	FOREIGN KEY (sample_id) REFERENCES sample (id)
;

ALTER TABLE sample_tag ADD CONSTRAINT FK_sample_tag_sample 
	FOREIGN KEY (sample_id) REFERENCES sample (id)
;

ALTER TABLE sample_tag ADD CONSTRAINT FK_sample_tag_tag 
	FOREIGN KEY (tag_id) REFERENCES tag (id)
;

ALTER TABLE tag ADD CONSTRAINT FK_tag_user_cr 
	FOREIGN KEY (created_by) REFERENCES user (id)
;

ALTER TABLE tag ADD CONSTRAINT FK_tag_user_mf 
	FOREIGN KEY (modified_by) REFERENCES user (id)
;
