-- username: admin, password: admin
INSERT INTO USER (USERNAME, PASSWORD) VALUES ('admin', '$2a$10$kLFfKkH8fY0PeTXusn0gqe7IzyPL3DEdSfhglVFeIfV6GxdJIAvfy');
INSERT INTO USER_ROLES (USER_ID, ROLE_ID) VALUES (
	(SELECT ID FROM USER WHERE USERNAME = 'admin'), (SELECT ID FROM ROLE WHERE ROLE = 'ROLE_ADMIN') 
);
INSERT INTO USER_ROLES (USER_ID, ROLE_ID) VALUES (
	(SELECT ID FROM USER WHERE USERNAME = 'admin'), (SELECT ID FROM ROLE WHERE ROLE = 'ROLE_USER') 
);