insert into users (username, password, enabled) values ('admin','21232f297a57a5a743894a0e4a801fc3', 1);
insert into users (username, password, enabled) values ('user1','24c9e15e52afc47c225b757e7bee1f9d', 1);
insert into users (username, password, enabled) values ('user2','7e58d63b60197ceb55a1c487989a3720', 1);

insert into authorities (user_id, authority) values (1, 'ROLE_ADMIN');
insert into authorities (user_id, authority) values (2, 'ROLE_USER');
insert into authorities (user_id, authority) values (3, 'ROLE_USER');

insert into employee (id, first_name, last_name, email, job) values
	(1, 'Pilot', 'One', 'pone@gmail.com', 'PILOT'),
	(2, 'Operator', 'One', 'oone@gmail.com', 'OPERATOR'),
	(3, 'Navigator', 'One', 'none@gmail.com', 'NAVIGATOR'),
	(4, 'Attendant', 'One', 'aone@gmail.com', 'FLIGHT_ATTENDANT'),
	(5, 'Attendant', 'Two', 'atwo@gmail.com', 'FLIGHT_ATTENDANT'),
	(6, 'Pilot', 'Two', 'ptwo@gmail.com', 'PILOT'),
	(7, 'Operator', 'Two', 'otwo@gmail.com', 'OPERATOR'),
	(8, 'Navigator', 'Two', 'ntwo@gmail.com', 'NAVIGATOR'),
	(9, 'Attendant', 'Three', 'athree@gmail.com', 'FLIGHT_ATTENDANT'),
	(10, 'Attendant', 'Four', 'afour@gmail.com', 'FLIGHT_ATTENDANT');

