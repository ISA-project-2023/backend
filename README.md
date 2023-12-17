# ISA-backend - tim 47

Student 1: Lara Petković 	RA185-2020
Student 2: Dušan Suđić 		RA81-2020
Student 3: Jelisaveta Letić 	RA82-2020
Student 4: Luka Milanko 	RA78-2020

# Skripta sa podacima:

INSERT INTO public.equipment(
    id, description, name, type)
VALUES 
    (101, 'Smanjuje otok kad vas ujede komarac', 'Lekovi za ujed komarca', 'tip1'),
    (102, 'Spusta pritisak', 'Lekovi za smirenje', 'tip1'),
    (103, 'Vitaminske tablete', 'Tablete C', 'tip2'),
    (104, 'Aparat za merenje temperature, UPOZORENJE: sadrzi zivu!', 'Toplomer', 'tip2'),
    (105, 'Aparat za merenje pritiska', 'Izmeri pritisak', 'tip2'),
    (106, 'Kapi za oci', 'Vitalis', 'tip3'),
    (107, 'Antibiotik', 'Amoxilin', 'tip1'),
    (108, 'Analgetik', 'Paracetamol', 'tip1'),
    (109, 'Antialergijske tablete', 'Claritin', 'tip1'),
    (110, 'Inhalator za astmu', 'Ventolin', 'tip2'),
    (111, 'Flasteri za rane', 'Bandaids', 'tip3');

INSERT INTO public.company(
    id, grade, location, name, start_time, end_time)
VALUES 
    (101, '4', 'New York', 'Medicine Solution', '09:00:00', '18:00:00'),
    (102, '3', 'San Francisco', 'Innovate Medicine Equipment', '09:00:00', '18:00:00'),
    (103, '5', 'Los Angeles', 'Future Med Group', '09:00:00', '18:00:00');  
/*
--more company examples
    (104, '4', 'Chicago', 'MediTech Solutions', '08:30:00', '17:30:00'),
    (105, '3', 'Houston', 'Health Innovations Inc.', '08:00:00', '17:00:00'),
    (106, '5', 'Boston', 'Precision Health Solutions', '08:00:00', '18:00:00'),
    (107, '4', 'Miami', 'MedEquip Technologies', '09:30:00', '18:30:00');	*/

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
    (100, '', 'admin@example.com', 'Admin', 'Admin', 'aaa', 0, 'admin', 'SYSTEM_ADMIN', true, 'token'),
    (101, 'REGULAR', 'pera@example.com', 'Petar', 'Petrovic', 'aaa', 0, 'pera', 'COMPANY_ADMIN', true, 'token'),
    (102, 'REGULAR', 'zika@example.com', 'Zika', 'Zeljkovic', 'aaa', 0, 'zika', 'COMPANY_ADMIN', true, 'token'),
    (103, 'REGULAR', 'mira@example.com', 'Mira', 'Milojevic', 'aaa', 0, 'mira', 'COMPANY_ADMIN', true, 'token'),
    (104, 'REGULAR', 'sara@example.com', 'Sara', 'Zeljkovic', 'aaa', 0, 'sara',  'COMPANY_ADMIN', true, 'token'),
    (105, 'REGULAR', 'mia@example.com', 'Mia', 'Mandic', 'aaa', 0, 'mia', 'COMPANY_ADMIN', true, 'token'),
    (201, 'REGULAR', 'mika@example.com', 'Mika', 'Mikic', 'aaa', 0, 'mika', 'CUSTOMER', true, 'token'),
    (202, 'REGULAR', 'tea@example.com', 'Teodora', 'Mandic', 'aaa', 0, 'tea', 'CUSTOMER', true, 'token');

INSERT INTO public.system_admin(
    id, is_activated)
VALUES (100, true);

INSERT INTO public.company_admin(
    id, company_id, job_description, is_verified)
VALUES 
    (101, 101, 'sef', true),
    (102, 102, 'HR radnik', true),
    (103, 102, 'administrativni radnik', true),
    (104, 103, 'menadzer', true),
    (105, 103, 'administrativni radnik', true);

INSERT INTO public.customer(
    id, phone_number, country, city, company_info)
VALUES  
    (201, '0656566565', 'Serbia', 'Beograd', 'Bolnica na Miseluku'),
    (202, '0624445553', 'Serbia', 'Backa Palanka', 'Opstinska bolnica');


INSERT INTO public.pick_up_appointment(
	id, company_admin_id, date, duration,  is_free)
VALUES 
    -- Company Admin 101
    (101, 101, '2023-12-30 10:00:00', 1, true),
    (102, 101, '2023-12-30 12:00:00', 2, true),
    (103, 101, '2023-12-30 16:00:00', 1, true),
    (104, 101, '2024-01-02 10:00:00', 1, true),
    (105, 101, '2024-01-02 12:00:00', 2, true),

    -- Company Admin 102
    (106, 102, '2023-12-30 10:00:00', 1, true),
    (107, 102, '2023-12-30 12:00:00', 2, true),
    (108, 102, '2023-12-30 16:00:00', 1, true),
    (109, 102, '2024-01-02 10:00:00', 1, true),
    (110, 102, '2024-01-02 12:00:00', 2, true),

    -- Company Admin 103
    (111, 103, '2023-12-30 10:00:00', 1, true),
    (112, 103, '2023-12-30 12:00:00', 2, true),
    (113, 103, '2023-12-30 16:00:00', 1, true),
    (114, 103, '2024-01-02 10:00:00', 1, true),
    (115, 103, '2024-01-02 12:00:00', 2, true),

    -- Company Admin 104
    (116, 104, '2023-12-30 10:00:00', 1, true),
    (117, 104, '2023-12-30 12:00:00', 2, true),
    (118, 104, '2023-12-30 16:00:00', 1, true),
    (119, 104, '2024-01-02 10:00:00', 1, true),
    (120, 104, '2024-01-02 12:00:00', 2, true),

    -- Company Admin 105
    (121, 105, '2023-12-30 10:00:00', 1, true),
    (122, 105, '2023-12-30 12:00:00', 2, true),
    (123, 105, '2023-12-30 16:00:00', 1, true),
    (124, 105, '2024-01-02 10:00:00', 1, true),
    (125, 105, '2024-01-02 12:00:00', 2, true);


/*
-- PickupAppointments
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
	(101, 201, 501, 101, 'PENDING', '{"id":105,"name":"Vitalis","type":"tip3","description":"Kapi za oci"}'),
	(102, 201, 502, 101, 'PENDING', '{"id":105,"name":"Vitalis","type":"tip3","description":"Kapi za oci"}'),
	(103, 202, 503, 101, 'PENDING', '{"id":105,"name":"Vitalis","type":"tip3","description":"Kapi za oci"}'),
	(104, 202, 504, 102, 'PENDING', '{"id":105,"name":"Vitalis","type":"tip3","description":"Kapi za oci"}');
*/