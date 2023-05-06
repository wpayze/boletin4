CREATE TABLE IF NOT EXISTS persistent_logins ( 
	username VARCHAR(100) NOT NULL, 
	series VARCHAR(64) PRIMARY KEY, 
	token VARCHAR(64) NOT NULL, 
	last_used TIMESTAMP NOT NULL);
  
DELETE FROM roles;
DELETE FROM users;
DELETE FROM user_role;

INSERT INTO roles (id, name) VALUES 
(1, 'ROLE_ADMIN'),
(2, 'ROLE_ACTUATOR'),
(3, 'ROLE_USER');

INSERT INTO users (id, username, password, name) VALUES 
(1, 'admin', '$2a$10$BsbENHEAka/4OMaIY7feoOTv17SmbdCN53BtLdJWgZn14dY8bvieu', 'administrator'),
(3, 'user', '$2a$10$BsbENHEAka/4OMaIY7feoOTv17SmbdCN53BtLdJWgZn14dY8bvieu', 'regular user');

INSERT INTO user_role(user_id, role_id) VALUES
(1,1),
(1,2),
(1,3),
(3,2),
(3,3);

INSERT INTO posts(post_id, title, content, created_on, updated_on) VALUES
(100, 'Introducing SpringBoot','SpringBoot is an opinionated approach for building Spring applications.', '2017-06-20', null),
(101, 'CRUD using Spring Data JPA','Spring Data JPA provides JpaRepository which can be extended to have CRUD operations', '2017-06-25', null),
(102, 'Securing Web apps using SpringSecurity','Spring Security provides Authentication and Authorization support.', '2017-04-20', now());

