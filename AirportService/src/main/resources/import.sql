DROP TABLE IF EXISTS authorities;

DROP TABLE IF EXISTS users;

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

insert into users (username, password, enabled) values ('admin','21232f297a57a5a743894a0e4a801fc3', 1); -- hash is admin
insert into users (username, password, enabled) values ('user1','24c9e15e52afc47c225b757e7bee1f9d', 1); -- hash is user1
insert into users (username, password, enabled) values ('user2','7e58d63b60197ceb55a1c487989a3720', 1); -- hash is user2

insert into authorities (user_id, authority) values (1, 'ROLE_ADMIN');
insert into authorities (user_id, authority) values (2, 'ROLE_DISPATCHER');
insert into authorities (user_id, authority) values (3, 'ROLE_DISPATCHER');

insert into employee (id, first_name, last_name, email, job) values (1, 'Pilot', 'One', 'pone@gmail.com', 'PILOT');
insert into employee (id, first_name, last_name, email, job) values (2, 'Operator', 'One', 'oone@gmail.com', 'OPERATOR');
insert into employee (id, first_name, last_name, email, job) values (3, 'Navigator', 'One', 'none@gmail.com', 'NAVIGATOR');
insert into employee (id, first_name, last_name, email, job) values (4, 'Attendant', 'One', 'aone@gmail.com', 'FLIGHT_ATTENDANT');
insert into employee (id, first_name, last_name, email, job) values (5, 'Attendant', 'Two', 'atwo@gmail.com', 'FLIGHT_ATTENDANT');
insert into employee (id, first_name, last_name, email, job) values (6, 'Pilot', 'Two', 'ptwo@gmail.com', 'PILOT');
insert into employee (id, first_name, last_name, email, job) values (7, 'Operator', 'Two', 'otwo@gmail.com', 'OPERATOR');
insert into employee (id, first_name, last_name, email, job) values (8, 'Navigator', 'Two', 'ntwo@gmail.com', 'NAVIGATOR');
insert into employee (id, first_name, last_name, email, job) values (9, 'Attendant', 'Three', 'athree@gmail.com', 'FLIGHT_ATTENDANT');
insert into employee (id, first_name, last_name, email, job) values (10, 'Attendant', 'Four', 'afour@gmail.com', 'FLIGHT_ATTENDANT');