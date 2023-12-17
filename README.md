# ISA-backend


# Skripta sa podacima:
INSERT INTO public.equipment(
    id, description, name, type)
VALUES 
    (101, 'Smanjuje otok kad vas ujede komarac', 'Lekovi za ujed komarca', 'tip1'),
    (102, 'Spusta pritisak', 'Lekovi za smirenje', 'tip1'),
    (103, 'Vitaminske tablete', 'Tablete C', 'tip2'),
    (104, 'Aparat za merenje temperature, UPOZORENJE: sadrzi zivu!', 'Toplomer', 'tip2'),
    (105, 'Kapi za oci', 'Vitalis', 'tip3');

INSERT INTO public.company(
    id, grade, location, name, start_time, end_time)
VALUES 
    (101, '4', 'New York', 'Medicine Solution', '09:00:00', '18:00:00'),
    (102, '3', 'San Francisco', 'Innovate Medicine Equipment', '09:00:00', '18:00:00'),
    (103, '5', 'Los Angeles', 'Future Med Group', '09:00:00', '18:00:00');

INSERT INTO public.company_equipment(
    company_id, equipment_id)
VALUES 
    (101, 101),
    (101, 102),
    (101, 103),
    (102, 101),
    (102, 102),
    (103, 103),
    (103, 104),
    (101, 105),
    (102, 104);

INSERT INTO public."user"(
    id, category, email, first_name, last_name, password, penalty_points, username, role, enabled, token)
VALUES 
    (101, '', 'admin@example.com', 'Admin', 'Admin', 'aaa', 0, 'admin', 'SYSTEM_ADMIN', true, 'token'),
    (102, 'REGULAR', 'pera@example.com', 'Petar', 'Petrovic', 'aaa', 0, 'pera', 'COMPANY_ADMIN', true, 'token'),
    (103, 'REGULAR', 'zika@example.com', 'Zika', 'Zeljkovic', 'aaa', 0, 'zika', 'COMPANY_ADMIN', true, 'token'),
    (104, 'REGULAR', 'mira@example.com', 'Mira', 'Milojevic', 'aaa', 0, 'mira', 'COMPANY_ADMIN', true, 'token'),
    (105, 'REGULAR', 'mika@example.com', 'Mika', 'Mikic', 'aaa', 0, 'mika', 'CUSTOMER', true, 'token'),
    (106, 'REGULAR', 'tea@example.com', 'Teodora', 'Mandic', 'aaa', 0, 'tea', 'CUSTOMER', true, 'token');

INSERT INTO public.system_admin(
	id, is_activated)
VALUES (101, true);

INSERT INTO public.company_admin(
	id, company_id, job_description)
VALUES 	
    (102, 101, 'sef'),
    (103, 102, 'HR radnik'),
    (104, 102, 'administrativni radnik');

INSERT INTO public.customer(
	id, phone_number, country, city, company_info)
VALUES  
	(105, '0656566565', 'Serbia', 'BG', 'Bolnica na Miseluku'),
	(106, '0624445553', 'Serbia', 'Backa Palanka', 'Bolnica na Miseluku');

INSERT INTO public.pick_up_appointment(
	id, company_admin_id, date, duration,  is_free)
VALUES 
	(101, 102, '2024-2-2 12:00:00', 2, true),
	(102, 102, '2024-2-2 14:00:00', 2, true),
	(103, 102, '2024-2-2 16:00:00', 2, true),
	(104, 103, '2024-2-2 12:00:00', 2, true),
	(105, 103, '2024-2-2 14:00:00', 2, true),
	(106, 103, '2024-2-2 16:00:00', 2, true),
	(107, 104, '2024-2-2 12:00:00', 2, true),
	(108, 104, '2024-2-2 14:00:00', 2, true),
	(109, 104, '2024-2-2 16:00:00', 2, true);


/*
INSERT INTO public.pick_up_appointment(
	id, company_admin_id, date, duration,  is_free)
VALUES 
	(501, 102, '2023-12-29 12:00:00', 2, false),
	(502, 102, '2022-12-30 14:00:00', 2, false),
	(503, 102, '2024-1-1 16:00:00', 2, false),
	(504, 103, '2024-1-2 12:00:00', 2, false);

-- Reservations
INSERT INTO public.reservation(
	id, employee_id, pick_up_appointment_id, company_id, status, equipment)
VALUES 
	(101, 105, 501, 101, 'PENDING', '{"id":105,"name":"Vitalis","type":"tip3","description":"Kapi za oci"}'),
	(102, 105, 502, 101, 'PENDING', '{"id":105,"name":"Vitalis","type":"tip3","description":"Kapi za oci"}'),
	(103, 105, 503, 101, 'PENDING', '{"id":105,"name":"Vitalis","type":"tip3","description":"Kapi za oci"}'),
	(104, 106, 504, 102, 'PENDING', '{"id":105,"name":"Vitalis","type":"tip3","description":"Kapi za oci"}');
*/

