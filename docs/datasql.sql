INSERT INTO role (code) VALUES ('ROLE_ADMIN');
set @role_id = LAST_INSERT_ID();
INSERT INTO role(code) VALUES ('ROLE_USER');
set @role_user_id = LAST_INSERT_ID();
INSERT INTO USER (name, enabled, email,PASSWORD) VALUES ('Nexum', 1, 'nexum@psychedelic-artists.cz','c7c15778e6211c00178b73bdbb977181f5d1d3605ee2ec16a8f4214c3c1b2a79');
set @admin_id = LAST_INSERT_ID();
INSERT INTO rights(user_id,role_id) values (@admin_id, @role_id);
INSERT INTO rights(user_id,role_id) values (@admin_id, @role_user_id);