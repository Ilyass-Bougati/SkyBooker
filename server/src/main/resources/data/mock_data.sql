-- This script populates the database with mock data for an airline reservation system.
-- Note : the other tables are generated in the StartupRunner


-- Populate Airlines
INSERT INTO companie_aerienne (iata_code, icao_code, nom) VALUES
('AF', 'AFR', 'Air France'),
('BA', 'BAW', 'British Airways'),
('LH', 'DLH', 'Lufthansa'),
('EK', 'UAE', 'Emirates'),
('AA', 'AAL', 'American Airlines'),
('QR', 'QTR', 'Qatar Airways'),
('SQ', 'SIA', 'Singapore Airlines');

-- Populate Cities
INSERT INTO villes (nom, pays) VALUES
('Paris', 'France'),
('London', 'United Kingdom'),
('New York', 'United States'),
('Berlin', 'Germany'),
('Rome', 'Italy'),
('Madrid', 'Spain'),
('Dubai', 'United Arab Emirates'),
('Tokyo', 'Japan'),
('Singapore', 'Singapore'),
('Casablanca', 'Morocco'),
('Bangkok', 'Thailand'),
('Sydney', 'Australia'),
('Cairo', 'Egypt'),
('Rio de Janeiro', 'Brazil'),
('Moscow', 'Russia'),
('Los Angeles', 'United States'),
('San Francisco', 'United States'),
('Toronto', 'Canada'),
('Mexico City', 'Mexico'),
('Buenos Aires', 'Argentina'),
('Amsterdam', 'Netherlands'),
('Mumbai', 'India');


-- Populate Clients
INSERT INTO clients (role_id, adresse, email, password, telephone) VALUES
((SELECT id FROM roles WHERE authority = 'ROLE_USER'), '123 Rue de Paris, Paris', 'jacques.durand@email.com', 'hash_password_1', '+33612345678'),
((SELECT id FROM roles WHERE authority = 'ROLE_USER'), '456 Oxford Street, London', 'emma.wilson@email.com', 'hash_password_2', '+44712345678'),
((SELECT id FROM roles WHERE authority = 'ROLE_USER'), '789 Fifth Avenue, New York', 'robert.brown@email.com', 'hash_password_3', '+12123456789'),
((SELECT id FROM roles WHERE authority = 'ROLE_USER'), '101 Unter den Linden, Berlin', 'laura.klein@email.com', 'hash_password_4', '+49301234567'),
((SELECT id FROM roles WHERE authority = 'ROLE_USER'), '202 Via Roma, Rome', 'alessio.ricci@email.com', 'hash_password_5', '+39612345678'),
((SELECT id FROM roles WHERE authority = 'ROLE_USER'), '303 Calle Mayor, Madrid', 'carlos.fernandez@email.com', 'hash_password_6', '+34612345678'),
((SELECT id FROM roles WHERE authority = 'ROLE_USER'), '404 Sukhumvit Road, Bangkok', 'li.chen@email.com', 'hash_password_7', '+66812345678'),
((SELECT id FROM roles WHERE authority = 'ROLE_USER'), '505 Sheikh Zayed Road, Dubai', 'mohammed.alfarsi@email.com', 'hash_password_8', '+97150123456'),
((SELECT id FROM roles WHERE authority = 'ROLE_USER'), '606 Ginza, Tokyo', 'akira.nakamura@email.com', 'hash_password_9', '+81312345678'),
((SELECT id FROM roles WHERE authority = 'ROLE_USER'), '707 Avenue Mohammed V, Casablanca', 'fatima.benali@email.com', 'hash_password_10', '+212612345678'),
-- Adding clients for new passengers needed for reservations
((SELECT id FROM roles WHERE authority = 'ROLE_USER'), 'Via Nazionale 10, Rome', 'marco.rossi@email.com', 'hash_password_11', '+39061234567'),
((SELECT id FROM roles WHERE authority = 'ROLE_USER'), '100 Main St, Anytown, USA', 'john.smith@email.com', 'hash_password_12', '+15551234567'),
((SELECT id FROM roles WHERE authority = 'ROLE_USER'), '1 Beijing Road, Beijing', 'chai.wang@email.com', 'hash_password_13', '+861012345678'),
((SELECT id FROM roles WHERE authority = 'ROLE_USER'), 'Hauptstrasse 5, Berlin', 'anna.schmidt@email.com', 'hash_password_14', '+491512345678'),
((SELECT id FROM roles WHERE authority = 'ROLE_USER'), '25 Nile Corniche, Cairo', 'omar.hassan@email.com', 'hash_password_15', '+201001234567');


-- Populate Passengers of various categories
INSERT INTO passagers (nom, prenom, cin, age, date_of_birth, categorie_id, client_id) VALUES
('Durand', 'Jacques', 'FR567890', 52, '1973-01-15', (SELECT id FROM categories WHERE nom = 'Standard'), (SELECT id FROM clients WHERE email = 'jacques.durand@email.com')),
('Wilson', 'Emma', 'UK123456', 24, '2001-07-20', (SELECT id FROM categories WHERE nom = 'Standard'), (SELECT id FROM clients WHERE email = 'emma.wilson@email.com')),
('Brown', 'Robert', 'US789012', 99, '1926-03-10', (SELECT id FROM categories WHERE nom = 'Standard'), (SELECT id FROM clients WHERE email = 'robert.brown@email.com')),
('Klein', 'Laura', 'DE345678', 43, '1982-11-03', (SELECT id FROM categories WHERE nom = 'Standard'), (SELECT id FROM clients WHERE email = 'laura.klein@email.com')),
('Ricci', 'Alessio', 'IT901234', 20, '2005-02-28', (SELECT id FROM categories WHERE nom = 'Standard'), (SELECT id FROM clients WHERE email = 'alessio.ricci@email.com')),
('Fernandez', 'Carlos', 'ES567890', 32, '1993-09-01', (SELECT id FROM categories WHERE nom = 'Standard'), (SELECT id FROM clients WHERE email = 'carlos.fernandez@email.com')),
('Chen', 'Li', 'TH123789', 17, '2008-05-12', (SELECT id FROM categories WHERE nom = 'Standard'), (SELECT id FROM clients WHERE email = 'li.chen@email.com')),
('Al-Farsi', 'Mohammed', 'AE456012', 90, '1935-06-25', (SELECT id FROM categories WHERE nom = 'Standard'), (SELECT id FROM clients WHERE email = 'mohammed.alfarsi@email.com')),
('Nakamura', 'Akira', 'JP789345', 55, '1970-04-02', (SELECT id FROM categories WHERE nom = 'Standard'), (SELECT id FROM clients WHERE email = 'akira.nakamura@email.com')),
('Benali', 'Fatima', 'MA012678', 18, '2007-08-19', (SELECT id FROM categories WHERE nom = 'Standard'), (SELECT id FROM clients WHERE email = 'fatima.benali@email.com')),
('Kim', 'Min-Ji', 'KR345901', 21, '2004-10-07', (SELECT id FROM categories WHERE nom = 'Standard'), (SELECT id FROM clients WHERE email = 'li.chen@email.com')),
('Silva', 'Maria', 'BR012345', 22, '2003-03-14', (SELECT id FROM categories WHERE nom = 'Standard'), (SELECT id FROM clients WHERE email = 'robert.brown@email.com')),
('Johnson', 'David', 'US567890', 40, '1985-12-01', (SELECT id FROM categories WHERE nom = 'Standard'), (SELECT id FROM clients WHERE email = 'robert.brown@email.com')),
('Lee', 'Soo-Jin', 'KR123456', 12, '2013-09-22', (SELECT id FROM categories WHERE nom = 'Standard'), (SELECT id FROM clients WHERE email = 'emma.wilson@email.com')),
('Rossi', 'Marco', 'IT112233', 45, '1980-06-30', (SELECT id FROM categories WHERE nom = 'Standard'), (SELECT id FROM clients WHERE email = 'marco.rossi@email.com')),
('Smith', 'John', 'US998877', 38, '1987-04-25', (SELECT id FROM categories WHERE nom = 'Standard'), (SELECT id FROM clients WHERE email = 'john.smith@email.com')),
('Wang', 'Chai', 'CN776655', 29, '1996-02-10', (SELECT id FROM categories WHERE nom = 'Standard'), (SELECT id FROM clients WHERE email = 'chai.wang@email.com')),
('Schmidt', 'Anna', 'DE101010', 33, '1992-07-05', (SELECT id FROM categories WHERE nom = 'Standard'), (SELECT id FROM clients WHERE email = 'anna.schmidt@email.com')),
('Hassan', 'Omar', 'EG202020', 50, '1975-01-20', (SELECT id FROM categories WHERE nom = 'Standard'), (SELECT id FROM clients WHERE email = 'omar.hassan@email.com'));


-- Populate Airports
INSERT INTO aeroports (iata_code, icao_code, nom, latitude, longitude, ville_id) VALUES
('CDG', 'LFPG', 'Charles de Gaulle Airport', 49.0097, 2.5479, (SELECT id FROM villes WHERE nom = 'Paris')),
('ORY', 'LFPO', 'Orly Airport', 48.7262, 2.3652, (SELECT id FROM villes WHERE nom = 'Paris')),
('LHR', 'EGLL', 'Heathrow Airport', 51.4700, -0.4543, (SELECT id FROM villes WHERE nom = 'London')),
('JFK', 'KJFK', 'John F. Kennedy International Airport', 40.6413, -73.7781, (SELECT id FROM villes WHERE nom = 'New York')),
('BER', 'EDDB', 'Berlin Brandenburg Airport', 52.3667, 13.5033, (SELECT id FROM villes WHERE nom = 'Berlin')),
('FCO', 'LIRF', 'Leonardo da Vinci International Airport', 41.8003, 12.2389, (SELECT id FROM villes WHERE nom = 'Rome')),
('MAD', 'LEMD', 'Adolfo Suárez Madrid–Barajas Airport', 40.4983, -3.5676, (SELECT id FROM villes WHERE nom = 'Madrid')),
('DXB', 'OMDB', 'Dubai International Airport', 25.2532, 55.3657, (SELECT id FROM villes WHERE nom = 'Dubai')),
('HND', 'RJTT', 'Haneda Airport', 35.5494, 139.7798, (SELECT id FROM villes WHERE nom = 'Tokyo')),
('SIN', 'WSSS', 'Singapore Changi Airport', 1.3644, 103.9915, (SELECT id FROM villes WHERE nom = 'Singapore')),
('CMN', 'GMMN', 'Mohammed V International Airport', 33.3675, -7.5892, (SELECT id FROM villes WHERE nom = 'Casablanca')),
('BKK', 'VTBS', 'Suvarnabhumi Airport', 13.6900, 100.7501, (SELECT id FROM villes WHERE nom = 'Bangkok')),
('SYD', 'YSSY', 'Sydney Airport', -33.9399, 151.1753, (SELECT id FROM villes WHERE nom = 'Sydney')),
('CAI', 'HECA', 'Cairo International Airport', 30.1219, 31.4056, (SELECT id FROM villes WHERE nom = 'Cairo')),
('GIG', 'SBGL', 'Rio de Janeiro/Galeão International Airport', -22.8100, -43.2500, (SELECT id FROM villes WHERE nom = 'Rio de Janeiro')),
('SVO', 'UUEE', 'Sheremetyevo International Airport', 55.9726, 37.4146, (SELECT id FROM villes WHERE nom = 'Moscow')),
('LAX', 'KLAX', 'Los Angeles International Airport', 33.9416, -118.4085, (SELECT id FROM villes WHERE nom = 'Los Angeles'));


-- Populate Aircraft
INSERT INTO avions (iata_code, icao_code, model, max_distance, companie_aerienne_id) VALUES
('A320', 'A320', 'Airbus A320', 6100, (SELECT id FROM companie_aerienne WHERE iata_code = 'AF')),
('B777', 'B77W', 'Boeing 777-300ER', 13650, (SELECT id FROM companie_aerienne WHERE iata_code = 'AF')),
('A380', 'A388', 'Airbus A380-800', 15000, (SELECT id FROM companie_aerienne WHERE iata_code = 'EK')),
('B747', 'B744', 'Boeing 747-400', 13450, (SELECT id FROM companie_aerienne WHERE iata_code = 'BA')),
('B787', 'B789', 'Boeing 787-9 Dreamliner', 14140, (SELECT id FROM companie_aerienne WHERE iata_code = 'AA')),
('A330', 'A333', 'Airbus A330-300', 11750, (SELECT id FROM companie_aerienne WHERE iata_code = 'LH')),
('A350', 'A359', 'Airbus A350-900', 15000, (SELECT id FROM companie_aerienne WHERE iata_code = 'QR')),
('B737', 'B738', 'Boeing 737-800', 5765, (SELECT id FROM companie_aerienne WHERE iata_code = 'SQ')),
('A321', 'A321', 'Airbus A321neo', 7400, (SELECT id FROM companie_aerienne WHERE iata_code = 'LH')),
('B767', 'B763', 'Boeing 767-300ER', 11093, (SELECT id FROM companie_aerienne WHERE iata_code = 'AA'));


-- Populate Capacities for each aircraft and class
-- Airbus A320 (AF)
INSERT INTO capacites (borne_inf, borne_sup, capacite, avion_id, classe_id) VALUES
(1, 30, 30, (SELECT id FROM avions WHERE model = 'Airbus A320' AND companie_aerienne_id = (SELECT id FROM companie_aerienne WHERE iata_code = 'AF')), (SELECT id FROM classes WHERE nom = 'Business')),
(31, 180, 150, (SELECT id FROM avions WHERE model = 'Airbus A320' AND companie_aerienne_id = (SELECT id FROM companie_aerienne WHERE iata_code = 'AF')), (SELECT id FROM classes WHERE nom = 'Economy'));

-- Boeing 777-300ER (AF)
INSERT INTO capacites (borne_inf, borne_sup, capacite, avion_id, classe_id) VALUES
(1, 8, 8, (SELECT id FROM avions WHERE model = 'Boeing 777-300ER' AND companie_aerienne_id = (SELECT id FROM companie_aerienne WHERE iata_code = 'AF')), (SELECT id FROM classes WHERE nom = 'First Class')),
(9, 68, 60, (SELECT id FROM avions WHERE model = 'Boeing 777-300ER' AND companie_aerienne_id = (SELECT id FROM companie_aerienne WHERE iata_code = 'AF')), (SELECT id FROM classes WHERE nom = 'Business')),
(69, 396, 328, (SELECT id FROM avions WHERE model = 'Boeing 777-300ER' AND companie_aerienne_id = (SELECT id FROM companie_aerienne WHERE iata_code = 'AF')), (SELECT id FROM classes WHERE nom = 'Economy'));

-- Airbus A380-800 (EK)
INSERT INTO capacites (borne_inf, borne_sup, capacite, avion_id, classe_id) VALUES
(1, 14, 14, (SELECT id FROM avions WHERE model = 'Airbus A380-800' AND companie_aerienne_id = (SELECT id FROM companie_aerienne WHERE iata_code = 'EK')), (SELECT id FROM classes WHERE nom = 'First Class')),
(15, 90, 76, (SELECT id FROM avions WHERE model = 'Airbus A380-800' AND companie_aerienne_id = (SELECT id FROM companie_aerienne WHERE iata_code = 'EK')), (SELECT id FROM classes WHERE nom = 'Business')),
(91, 507, 417, (SELECT id FROM avions WHERE model = 'Airbus A380-800' AND companie_aerienne_id = (SELECT id FROM companie_aerienne WHERE iata_code = 'EK')), (SELECT id FROM classes WHERE nom = 'Economy'));

-- Boeing 747-400 (BA)
INSERT INTO capacites (borne_inf, borne_sup, capacite, avion_id, classe_id) VALUES
(1, 14, 14, (SELECT id FROM avions WHERE model = 'Boeing 747-400' AND companie_aerienne_id = (SELECT id FROM companie_aerienne WHERE iata_code = 'BA')), (SELECT id FROM classes WHERE nom = 'First Class')),
(15, 86, 72, (SELECT id FROM avions WHERE model = 'Boeing 747-400' AND companie_aerienne_id = (SELECT id FROM companie_aerienne WHERE iata_code = 'BA')), (SELECT id FROM classes WHERE nom = 'Business')),
(87, 345, 259, (SELECT id FROM avions WHERE model = 'Boeing 747-400' AND companie_aerienne_id = (SELECT id FROM companie_aerienne WHERE iata_code = 'BA')), (SELECT id FROM classes WHERE nom = 'Economy'));

-- Boeing 787-9 Dreamliner (AA)
INSERT INTO capacites (borne_inf, borne_sup, capacite, avion_id, classe_id) VALUES
(1, 30, 30, (SELECT id FROM avions WHERE model = 'Boeing 787-9 Dreamliner' AND companie_aerienne_id = (SELECT id FROM companie_aerienne WHERE iata_code = 'AA')), (SELECT id FROM classes WHERE nom = 'Business')),
(31, 285, 255, (SELECT id FROM avions WHERE model = 'Boeing 787-9 Dreamliner' AND companie_aerienne_id = (SELECT id FROM companie_aerienne WHERE iata_code = 'AA')), (SELECT id FROM classes WHERE nom = 'Economy'));

-- Add capacities for the rest of the aircraft
INSERT INTO capacites (borne_inf, borne_sup, capacite, avion_id, classe_id) VALUES
(1, 42, 42, (SELECT id FROM avions WHERE model = 'Airbus A330-300' AND companie_aerienne_id = (SELECT id FROM companie_aerienne WHERE iata_code = 'LH')), (SELECT id FROM classes WHERE nom = 'Business')),
(43, 293, 251, (SELECT id FROM avions WHERE model = 'Airbus A330-300' AND companie_aerienne_id = (SELECT id FROM companie_aerienne WHERE iata_code = 'LH')), (SELECT id FROM classes WHERE nom = 'Economy')),

(1, 8, 8, (SELECT id FROM avions WHERE model = 'Airbus A350-900' AND companie_aerienne_id = (SELECT id FROM companie_aerienne WHERE iata_code = 'QR')), (SELECT id FROM classes WHERE nom = 'First Class')),
(9, 56, 48, (SELECT id FROM avions WHERE model = 'Airbus A350-900' AND companie_aerienne_id = (SELECT id FROM companie_aerienne WHERE iata_code = 'QR')), (SELECT id FROM classes WHERE nom = 'Business')),
(57, 325, 269, (SELECT id FROM avions WHERE model = 'Airbus A350-900' AND companie_aerienne_id = (SELECT id FROM companie_aerienne WHERE iata_code = 'QR')), (SELECT id FROM classes WHERE nom = 'Economy')),

(1, 12, 12, (SELECT id FROM avions WHERE model = 'Boeing 737-800' AND companie_aerienne_id = (SELECT id FROM companie_aerienne WHERE iata_code = 'SQ')), (SELECT id FROM classes WHERE nom = 'Business')),
(13, 176, 164, (SELECT id FROM avions WHERE model = 'Boeing 737-800' AND companie_aerienne_id = (SELECT id FROM companie_aerienne WHERE iata_code = 'SQ')), (SELECT id FROM classes WHERE nom = 'Economy')),

(1, 20, 20, (SELECT id FROM avions WHERE model = 'Airbus A321neo' AND companie_aerienne_id = (SELECT id FROM companie_aerienne WHERE iata_code = 'LH')), (SELECT id FROM classes WHERE nom = 'Business')),
(21, 220, 200, (SELECT id FROM avions WHERE model = 'Airbus A321neo' AND companie_aerienne_id = (SELECT id FROM companie_aerienne WHERE iata_code = 'LH')), (SELECT id FROM classes WHERE nom = 'Economy')),

(1, 24, 24, (SELECT id FROM avions WHERE model = 'Boeing 767-300ER' AND companie_aerienne_id = (SELECT id FROM companie_aerienne WHERE iata_code = 'AA')), (SELECT id FROM classes WHERE nom = 'Business')),
(25, 245, 221, (SELECT id FROM avions WHERE model = 'Boeing 767-300ER' AND companie_aerienne_id = (SELECT id FROM companie_aerienne WHERE iata_code = 'AA')), (SELECT id FROM classes WHERE nom = 'Economy'));


-- Populate Flights (creating realistic flight routes based on aircraft range)

-- Paris (CDG) to London (LHR)
INSERT INTO vols (date_depart , date_arrive, heure_depart, heure_arrive, etat, prix, aeroport_depart_id, aeroport_arrive_id, avion_id) VALUES
('2025-05-20', '2025-05-20', '07:30:00', '08:30:00', 'SCHEDULED', 250.00,
(SELECT id FROM aeroports WHERE iata_code = 'CDG'),
(SELECT id FROM aeroports WHERE iata_code = 'LHR'),
(SELECT id FROM avions WHERE model = 'Airbus A320' AND companie_aerienne_id = (SELECT id FROM companie_aerienne WHERE iata_code = 'AF'))),

('2025-05-20', '2025-05-20', '14:00:00', '15:00:00', 'SCHEDULED', 270.00,
(SELECT id FROM aeroports WHERE iata_code = 'CDG'),
(SELECT id FROM aeroports WHERE iata_code = 'LHR'),
(SELECT id FROM avions WHERE model = 'Airbus A320' AND companie_aerienne_id = (SELECT id FROM companie_aerienne WHERE iata_code = 'AF'))),

('2025-05-21', '2025-05-21', '08:15:00', '09:15:00', 'SCHEDULED', 250.00,
(SELECT id FROM aeroports WHERE iata_code = 'LHR'),
(SELECT id FROM aeroports WHERE iata_code = 'CDG'),
(SELECT id FROM avions WHERE model = 'Boeing 747-400' AND companie_aerienne_id = (SELECT id FROM companie_aerienne WHERE iata_code = 'BA')));

-- Paris (CDG) to New York (JFK) - Arrival time is local NY time
INSERT INTO vols (date_depart, date_arrive, heure_depart, heure_arrive, etat, prix, aeroport_depart_id, aeroport_arrive_id, avion_id) VALUES
('2025-05-20', '2025-05-20', '10:30:00', '13:30:00', 'SCHEDULED', 1200.00, -- Departure 10:30 Paris, Arrival 13:30 NY time
(SELECT id FROM aeroports WHERE iata_code = 'CDG'),
(SELECT id FROM aeroports WHERE iata_code = 'JFK'),
(SELECT id FROM avions WHERE model = 'Boeing 777-300ER' AND companie_aerienne_id = (SELECT id FROM companie_aerienne WHERE iata_code = 'AF'))),

('2025-05-22', '2025-05-23', '18:00:00', '07:00:00', 'SCHEDULED', 1350.00, -- Departure 18:00 NY, Arrival 07:00 next day Paris time
(SELECT id FROM aeroports WHERE iata_code = 'JFK'),
(SELECT id FROM aeroports WHERE iata_code = 'CDG'),
(SELECT id FROM avions WHERE model = 'Boeing 777-300ER' AND companie_aerienne_id = (SELECT id FROM companie_aerienne WHERE iata_code = 'AF')));

-- Dubai (DXB) to Tokyo (HND) - Arrival time is local Tokyo time
INSERT INTO vols (date_depart, date_arrive, heure_depart, heure_arrive, etat, prix, aeroport_depart_id, aeroport_arrive_id, avion_id) VALUES
('2025-05-21', '2025-05-22', '23:45:00', '15:30:00', 'SCHEDULED', 1800.00, -- Departure 23:45 Dubai, Arrival 15:30 next day Tokyo
(SELECT id FROM aeroports WHERE iata_code = 'DXB'),
(SELECT id FROM aeroports WHERE iata_code = 'HND'),
(SELECT id FROM avions WHERE model = 'Airbus A380-800' AND companie_aerienne_id = (SELECT id FROM companie_aerienne WHERE iata_code = 'EK'))),

('2025-05-24', '2025-05-24', '01:30:00', '17:15:00', 'SCHEDULED', 1750.00, -- Departure 01:30 Tokyo, Arrival 17:15 same day Dubai (after time zone conversion and flight)
(SELECT id FROM aeroports WHERE iata_code = 'HND'),
(SELECT id FROM aeroports WHERE iata_code = 'DXB'),
(SELECT id FROM avions WHERE model = 'Airbus A380-800' AND companie_aerienne_id = (SELECT id FROM companie_aerienne WHERE iata_code = 'EK')));

-- London (LHR) to Berlin (BER)
INSERT INTO vols (date_depart, date_arrive, heure_depart, heure_arrive, etat, prix, aeroport_depart_id, aeroport_arrive_id, avion_id) VALUES
('2025-05-22', '2025-05-22', '06:45:00', '09:30:00', 'SCHEDULED', 320.00,
(SELECT id FROM aeroports WHERE iata_code = 'LHR'),
(SELECT id FROM aeroports WHERE iata_code = 'BER'),
(SELECT id FROM avions WHERE model = 'Airbus A321neo' AND companie_aerienne_id = (SELECT id FROM companie_aerienne WHERE iata_code = 'LH'))),

('2025-05-23', '2025-05-23', '17:15:00', '20:00:00', 'CANCELLED', 290.00,
(SELECT id FROM aeroports WHERE iata_code = 'BER'),
(SELECT id FROM aeroports WHERE iata_code = 'LHR'),
(SELECT id FROM avions WHERE model = 'Airbus A321neo' AND companie_aerienne_id = (SELECT id FROM companie_aerienne WHERE iata_code = 'LH')));

-- New York (JFK) to Rome (FCO)
INSERT INTO vols (date_depart, date_arrive, heure_depart, heure_arrive, etat, prix, aeroport_depart_id, aeroport_arrive_id, avion_id) VALUES
('2025-05-25', '2025-05-26', '19:20:00', '10:15:00', 'CANCELLED', 1450.00, -- Dep 19:20 NY, Arr 10:15 next day Rome
(SELECT id FROM aeroports WHERE iata_code = 'JFK'),
(SELECT id FROM aeroports WHERE iata_code = 'FCO'),
(SELECT id FROM avions WHERE model = 'Boeing 787-9 Dreamliner' AND companie_aerienne_id = (SELECT id FROM companie_aerienne WHERE iata_code = 'AA'))),

('2025-05-28', '2025-05-28', '12:10:00', '17:05:00', 'CANCELLED', 1400.00, -- Dep 12:10 Rome, Arr 17:05 NY (after time zone & flight)
(SELECT id FROM aeroports WHERE iata_code = 'FCO'),
(SELECT id FROM aeroports WHERE iata_code = 'JFK'),
(SELECT id FROM avions WHERE model = 'Boeing 787-9 Dreamliner' AND companie_aerienne_id = (SELECT id FROM companie_aerienne WHERE iata_code = 'AA')));

-- Madrid (MAD) to Casablanca (CMN)
INSERT INTO vols (date_depart, date_arrive, heure_depart, heure_arrive, etat, prix, aeroport_depart_id, aeroport_arrive_id, avion_id) VALUES
('2025-05-21', '2025-05-21', '11:40:00', '13:20:00', 'SCHEDULED', 410.00,
(SELECT id FROM aeroports WHERE iata_code = 'MAD'),
(SELECT id FROM aeroports WHERE iata_code = 'CMN'),
(SELECT id FROM avions WHERE model = 'Airbus A350-900' AND companie_aerienne_id = (SELECT id FROM companie_aerienne WHERE iata_code = 'QR'))),

('2025-05-22', '2025-05-22', '15:30:00', '17:10:00', 'SCHEDULED', 395.00,
(SELECT id FROM aeroports WHERE iata_code = 'CMN'),
(SELECT id FROM aeroports WHERE iata_code = 'MAD'),
(SELECT id FROM avions WHERE model = 'Airbus A350-900' AND companie_aerienne_id = (SELECT id FROM companie_aerienne WHERE iata_code = 'QR')));

-- Singapore (SIN) to Sydney (SYD)
INSERT INTO vols (date_depart, date_arrive, heure_depart, heure_arrive, etat, prix, aeroport_depart_id, aeroport_arrive_id, avion_id) VALUES
('2025-05-24', '2020-05-25', '21:15:00', '07:45:00', 'SCHEDULED', 980.00,
(SELECT id FROM aeroports WHERE iata_code = 'SIN'),
(SELECT id FROM aeroports WHERE iata_code = 'SYD'),
(SELECT id FROM avions WHERE model = 'Boeing 737-800' AND companie_aerienne_id = (SELECT id FROM companie_aerienne WHERE iata_code = 'SQ'))),

('2025-05-26', '2025-05-26', '09:20:00', '17:50:00', 'SCHEDULED', 950.00, -- Dep SYD, Arr SIN same day
(SELECT id FROM aeroports WHERE iata_code = 'SYD'),
(SELECT id FROM aeroports WHERE iata_code = 'SIN'),
(SELECT id FROM avions WHERE model = 'Boeing 737-800' AND companie_aerienne_id = (SELECT id FROM companie_aerienne WHERE iata_code = 'SQ')));

-- Cairo (CAI) to Moscow (SVO)
INSERT INTO vols (date_depart, date_arrive, heure_depart, heure_arrive, etat, prix, aeroport_depart_id, aeroport_arrive_id, avion_id) VALUES
('2025-05-23', '2025-05-23', '08:30:00', '14:15:00', 'SCHEDULED', 660.00,
(SELECT id FROM aeroports WHERE iata_code = 'CAI'),
(SELECT id FROM aeroports WHERE iata_code = 'SVO'),
(SELECT id FROM avions WHERE model = 'Airbus A330-300' AND companie_aerienne_id = (SELECT id FROM companie_aerienne WHERE iata_code = 'LH'))),

('2025-05-25', '2025-05-25', '16:40:00', '22:25:00', 'SCHEDULED', 680.00,
(SELECT id FROM aeroports WHERE iata_code = 'SVO'),
(SELECT id FROM aeroports WHERE iata_code = 'CAI'),
(SELECT id FROM avions WHERE model = 'Airbus A330-300' AND companie_aerienne_id = (SELECT id FROM companie_aerienne WHERE iata_code = 'LH')));

-- Rio de Janeiro (GIG) to Madrid (MAD)
INSERT INTO vols (date_depart, date_arrive, heure_depart, heure_arrive, etat, prix, aeroport_depart_id, aeroport_arrive_id, avion_id) VALUES
('2025-05-22', '2025-05-23', '23:10:00', '14:30:00', 'SCHEDULED', 1650.00, -- Dep GIG, Arr MAD next day
(SELECT id FROM aeroports WHERE iata_code = 'GIG'),
(SELECT id FROM aeroports WHERE iata_code = 'MAD'),
(SELECT id FROM avions WHERE model = 'Boeing 777-300ER' AND companie_aerienne_id = (SELECT id FROM companie_aerienne WHERE iata_code = 'AF'))),

('2025-05-26', '2025-05-27', '16:45:00', '08:05:00', 'SCHEDULED', 1620.00, -- Dep MAD, Arr GIG next day
(SELECT id FROM aeroports WHERE iata_code = 'MAD'),
(SELECT id FROM aeroports WHERE iata_code = 'GIG'),
(SELECT id FROM avions WHERE model = 'Boeing 777-300ER' AND companie_aerienne_id = (SELECT id FROM companie_aerienne WHERE iata_code = 'AF')));

-- Bangkok (BKK) to Tokyo (HND)
INSERT INTO vols (date_depart, date_arrive, heure_depart, heure_arrive, etat, prix, aeroport_depart_id, aeroport_arrive_id, avion_id) VALUES
('2025-05-20', '2025-05-20', '10:15:00', '18:40:00', 'SCHEDULED', 890.00,
(SELECT id FROM aeroports WHERE iata_code = 'BKK'),
(SELECT id FROM aeroports WHERE iata_code = 'HND'),
(SELECT id FROM avions WHERE model = 'Boeing 787-9 Dreamliner' AND companie_aerienne_id = (SELECT id FROM companie_aerienne WHERE iata_code = 'AA'))),

('2025-05-23', '2025-05-23', '13:50:00', '22:15:00', 'SCHEDULED', 860.00,
(SELECT id FROM aeroports WHERE iata_code = 'HND'),
(SELECT id FROM aeroports WHERE iata_code = 'BKK'),
(SELECT id FROM avions WHERE model = 'Boeing 787-9 Dreamliner' AND companie_aerienne_id = (SELECT id FROM companie_aerienne WHERE iata_code = 'AA')));

-- Add more flights for more diversity
-- Paris (ORY) to Madrid (MAD)
INSERT INTO vols (date_depart, date_arrive, heure_depart, heure_arrive, etat, prix, aeroport_depart_id, aeroport_arrive_id, avion_id) VALUES
('2025-05-26', '2025-05-26', '09:15:00', '11:30:00', 'SCHEDULED', 280.00,
(SELECT id FROM aeroports WHERE iata_code = 'ORY'),
(SELECT id FROM aeroports WHERE iata_code = 'MAD'),
(SELECT id FROM avions WHERE model = 'Airbus A320' AND companie_aerienne_id = (SELECT id FROM companie_aerienne WHERE iata_code = 'AF'))),

('2025-05-26', '2025-05-26', '16:20:00', '18:35:00', 'SCHEDULED', 310.00,
(SELECT id FROM aeroports WHERE iata_code = 'MAD'),
(SELECT id FROM aeroports WHERE iata_code = 'ORY'),
(SELECT id FROM avions WHERE model = 'Airbus A320' AND companie_aerienne_id = (SELECT id FROM companie_aerienne WHERE iata_code = 'AF')));

-- Tokyo (HND) to Singapore (SIN)
INSERT INTO vols (date_depart, date_arrive, heure_depart, heure_arrive, etat, prix, aeroport_depart_id, aeroport_arrive_id, avion_id) VALUES
('2025-05-27', '2025-05-27', '11:05:00', '17:40:00', 'SCHEDULED', 920.00,
(SELECT id FROM aeroports WHERE iata_code = 'HND'),
(SELECT id FROM aeroports WHERE iata_code = 'SIN'),
(SELECT id FROM avions WHERE model = 'Boeing 737-800' AND companie_aerienne_id = (SELECT id FROM companie_aerienne WHERE iata_code = 'SQ'))), 

('2025-05-29', '2025-05-29', '09:30:00', '16:05:00', 'SCHEDULED', 890.00,
(SELECT id FROM aeroports WHERE iata_code = 'SIN'),
(SELECT id FROM aeroports WHERE iata_code = 'HND'),
(SELECT id FROM avions WHERE model = 'Boeing 737-800' AND companie_aerienne_id = (SELECT id FROM companie_aerienne WHERE iata_code = 'SQ'))); 

-- London (LHR) to Dubai (DXB)
INSERT INTO vols (date_depart, date_arrive, heure_depart, heure_arrive, etat, prix, aeroport_depart_id, aeroport_arrive_id, avion_id) VALUES
('2025-05-28', '2025-05-29', '22:10:00', '08:45:00', 'SCHEDULED', 850.00, -- Dep LHR, Arr DXB next day
(SELECT id FROM aeroports WHERE iata_code = 'LHR'),
(SELECT id FROM aeroports WHERE iata_code = 'DXB'),
(SELECT id FROM avions WHERE model = 'Boeing 747-400' AND companie_aerienne_id = (SELECT id FROM companie_aerienne WHERE iata_code = 'BA'))), 

('2025-05-30', '2025-05-30', '01:30:00', '06:05:00', 'SCHEDULED', 820.00, -- Dep DXB, Arr LHR same day
(SELECT id FROM aeroports WHERE iata_code = 'DXB'),
(SELECT id FROM aeroports WHERE iata_code = 'LHR'),
(SELECT id FROM avions WHERE model = 'Boeing 747-400' AND companie_aerienne_id = (SELECT id FROM companie_aerienne WHERE iata_code = 'BA'))); 

-- New York (JFK) to Los Angeles (LAX)
INSERT INTO vols (date_depart, date_arrive, heure_depart, heure_arrive, etat, prix, aeroport_depart_id, aeroport_arrive_id, avion_id) VALUES
('2025-05-25', '2025-05-25', '07:20:00', '11:05:00', 'SCHEDULED', 680.00,
(SELECT id FROM aeroports WHERE iata_code = 'JFK'),
(SELECT id FROM aeroports WHERE iata_code = 'LAX'),
(SELECT id FROM avions WHERE model = 'Boeing 767-300ER' AND companie_aerienne_id = (SELECT id FROM companie_aerienne WHERE iata_code = 'AA'))),

('2025-05-27', '2025-05-27', '14:45:00', '22:30:00', 'SCHEDULED', 650.00,
(SELECT id FROM aeroports WHERE iata_code = 'LAX'),
(SELECT id FROM aeroports WHERE iata_code = 'JFK'),
(SELECT id FROM avions WHERE model = 'Boeing 767-300ER' AND companie_aerienne_id = (SELECT id FROM companie_aerienne WHERE iata_code = 'AA')));

-- Sydney (SYD) to Bangkok (BKK)
INSERT INTO vols (date_depart, date_arrive, heure_depart, heure_arrive, etat, prix, aeroport_depart_id, aeroport_arrive_id, avion_id) VALUES
('2025-05-26', '2025-05-27', '20:15:00', '03:50:00', 'SCHEDULED', 1120.00, -- Dep SYD, Arr BKK next day
(SELECT id FROM aeroports WHERE iata_code = 'SYD'),
(SELECT id FROM aeroports WHERE iata_code = 'BKK'),
(SELECT id FROM avions WHERE model = 'Boeing 737-800' AND companie_aerienne_id = (SELECT id FROM companie_aerienne WHERE iata_code = 'SQ'))),

('2025-05-28', '2025-05-28', '01:40:00', '15:15:00', 'SCHEDULED', 1080.00, -- Dep BKK, Arr SYD same day
(SELECT id FROM aeroports WHERE iata_code = 'BKK'),
(SELECT id FROM aeroports WHERE iata_code = 'SYD'),
(SELECT id FROM avions WHERE model = 'Boeing 737-800' AND companie_aerienne_id = (SELECT id FROM companie_aerienne WHERE iata_code = 'SQ'))); 


-- Create some reservations
-- Reservation 1: Paris to London for client Jacques Durand
INSERT INTO reservations (etat, prix_total, reserved_at, client_id, vol_id) VALUES
(0, 500.00, '2025-04-15 14:30:00',
(SELECT id FROM clients WHERE email = 'jacques.durand@email.com'),
(SELECT id FROM vols WHERE
aeroport_depart_id = (SELECT id FROM aeroports WHERE iata_code = 'CDG') AND
aeroport_arrive_id = (SELECT id FROM aeroports WHERE iata_code = 'LHR') AND
date_depart = '2025-05-20' AND heure_depart = '07:30:00') 
);

-- Add tickets for this reservation
INSERT INTO billets (siege, classe_id, passager_id, reservation_id) VALUES
(15, (SELECT id FROM classes WHERE nom = 'Business'),
(SELECT id FROM passagers WHERE cin = 'FR567890'), -- Jacques Durand
(SELECT currval('reservations_id_seq')));

-- Reservation 2: New York to Rome for client Robert Brown with 2 passengers
INSERT INTO reservations (etat, prix_total, reserved_at, client_id, vol_id) VALUES
(0, 2900.00, '2025-04-20 09:15:00',
(SELECT id FROM clients WHERE email = 'robert.brown@email.com'),
(SELECT id FROM vols WHERE
aeroport_depart_id = (SELECT id FROM aeroports WHERE iata_code = 'JFK') AND
aeroport_arrive_id = (SELECT id FROM aeroports WHERE iata_code = 'FCO') AND
date_depart = '2025-05-25' AND heure_depart = '19:20:00')
);

-- Add tickets for this reservation
INSERT INTO billets (siege, classe_id, passager_id, reservation_id) VALUES
(24, (SELECT id FROM classes WHERE nom = 'Business'),
(SELECT id FROM passagers WHERE cin = 'UK123456'), -- Emma Wilson (assuming she is travelling with Robert Brown)
(SELECT currval('reservations_id_seq'))),

(25, (SELECT id FROM classes WHERE nom = 'Business'),
(SELECT id FROM passagers WHERE cin = 'US789012'), -- Robert Brown
(SELECT currval('reservations_id_seq')));

-- Reservation 3: Dubai to Tokyo for client Alessio Ricci with family (3 passengers)
INSERT INTO reservations (etat, prix_total, reserved_at, client_id, vol_id) VALUES
(0, 5400.00, '2025-04-25 16:45:00',
(SELECT id FROM clients WHERE email = 'alessio.ricci@email.com'),
(SELECT id FROM vols WHERE
aeroport_depart_id = (SELECT id FROM aeroports WHERE iata_code = 'DXB') AND
aeroport_arrive_id = (SELECT id FROM aeroports WHERE iata_code = 'HND') AND
date_depart = '2025-05-21' AND heure_depart = '23:45:00') 
);

-- Add tickets for this reservation
INSERT INTO billets (siege, classe_id, passager_id, reservation_id) VALUES
(5, (SELECT id FROM classes WHERE nom = 'First Class'),
(SELECT id FROM passagers WHERE cin = 'AE456012'), -- Mohammed Al-Farsi
(SELECT currval('reservations_id_seq'))),

(45, (SELECT id FROM classes WHERE nom = 'Business'),
(SELECT id FROM passagers WHERE cin = 'TH123789'), -- Li Chen
(SELECT currval('reservations_id_seq'))),

(46, (SELECT id FROM classes WHERE nom = 'Business'),
(SELECT id FROM passagers WHERE cin = 'IT901234'), -- Alessio Ricci
(SELECT currval('reservations_id_seq')));

-- Reservation 4: Madrid to Casablanca for client Akira Nakamura
INSERT INTO reservations (etat, prix_total, reserved_at, client_id, vol_id) VALUES
(0, 410.00, '2025-05-01 11:20:00',
(SELECT id FROM clients WHERE email = 'akira.nakamura@email.com'),
(SELECT id FROM vols WHERE
aeroport_depart_id = (SELECT id FROM aeroports WHERE iata_code = 'MAD') AND
aeroport_arrive_id = (SELECT id FROM aeroports WHERE iata_code = 'CMN') AND
date_depart = '2025-05-21' AND heure_depart = '11:40:00')
);

-- Add ticket for this reservation
INSERT INTO billets (siege, classe_id, passager_id, reservation_id) VALUES
(8, (SELECT id FROM classes WHERE nom = 'Business'),
(SELECT id FROM passagers WHERE cin = 'ES567890'), -- Carlos Fernandez (assuming travelling for Akira)
(SELECT currval('reservations_id_seq')));

-- Reservation 5: Singapore to Sydney for Fatima Benali
INSERT INTO reservations (etat, prix_total, reserved_at, client_id, vol_id) VALUES
(0, 2850.00, '2025-05-02 15:40:00',
(SELECT id FROM clients WHERE email = 'fatima.benali@email.com'),
(SELECT id FROM vols WHERE
aeroport_depart_id = (SELECT id FROM aeroports WHERE iata_code = 'SIN') AND
aeroport_arrive_id = (SELECT id FROM aeroports WHERE iata_code = 'SYD') AND
date_depart = '2025-05-24' AND heure_depart = '21:15:00')
);

-- Add tickets for this reservation
INSERT INTO billets (siege, classe_id, passager_id, reservation_id) VALUES
(3, (SELECT id FROM classes WHERE nom = 'First Class'),
(SELECT id FROM passagers WHERE cin = 'JP789345'), -- Akira Nakamura
(SELECT currval('reservations_id_seq'))),

(4, (SELECT id FROM classes WHERE nom = 'First Class'),
(SELECT id FROM passagers WHERE cin = 'KR345901'), -- Kim Min-Ji
(SELECT currval('reservations_id_seq'))),

(120, (SELECT id FROM classes WHERE nom = 'Economy'),
(SELECT id FROM passagers WHERE cin = 'KR123456'), -- Lee Soo-Jin
(SELECT currval('reservations_id_seq')));

-- Reservation 6: Cairo to Moscow for client Marco Rossi
INSERT INTO reservations (etat, prix_total, reserved_at, client_id, vol_id) VALUES
(0, 660.00, '2025-05-10 09:10:00',
(SELECT id FROM clients WHERE email = 'marco.rossi@email.com'),
(SELECT id FROM vols WHERE
aeroport_depart_id = (SELECT id FROM aeroports WHERE iata_code = 'CAI') AND
aeroport_arrive_id = (SELECT id FROM aeroports WHERE iata_code = 'SVO') AND
date_depart = '2025-05-23' AND heure_depart = '08:30:00')
);

-- Reservation 7: Rio to Madrid for client John Smith
INSERT INTO reservations (etat, prix_total, reserved_at, client_id, vol_id) VALUES
(0, 3300.00, '2025-05-05 22:15:00',
(SELECT id FROM clients WHERE email = 'john.smith@email.com'), 
(SELECT id FROM vols WHERE
aeroport_depart_id = (SELECT id FROM aeroports WHERE iata_code = 'GIG') AND
aeroport_arrive_id = (SELECT id FROM aeroports WHERE iata_code = 'MAD') AND
date_depart = '2025-05-22' AND heure_depart = '23:10:00') 
);

-- Add tickets for this reservation
INSERT INTO billets (siege, classe_id, passager_id, reservation_id) VALUES
(18, (SELECT id FROM classes WHERE nom = 'Business'),
(SELECT id FROM passagers WHERE cin = 'BR012345'), -- Maria Silva
(SELECT currval('reservations_id_seq'))),

(19, (SELECT id FROM classes WHERE nom = 'Business'),
(SELECT id FROM passagers WHERE cin = 'US567890'), -- David Johnson
(SELECT currval('reservations_id_seq')));

-- Reservation 8: Bangkok to Tokyo for client Chai Wang
INSERT INTO reservations (etat, prix_total, reserved_at, client_id, vol_id) VALUES
(0, 2670.00, '2025-05-12 13:25:00',
(SELECT id FROM clients WHERE email = 'chai.wang@email.com'),
(SELECT id FROM vols WHERE
aeroport_depart_id = (SELECT id FROM aeroports WHERE iata_code = 'BKK') AND
aeroport_arrive_id = (SELECT id FROM aeroports WHERE iata_code = 'HND') AND
date_depart = '2025-05-20' AND heure_depart = '10:15:00') 
);

-- Add tickets for this reservation
INSERT INTO billets (siege, classe_id, passager_id, reservation_id) VALUES
(20, (SELECT id FROM classes WHERE nom = 'Business'),
(SELECT id FROM passagers WHERE cin = 'MA012678'), -- Fatima Benali
(SELECT currval('reservations_id_seq'))),

(21, (SELECT id FROM classes WHERE nom = 'Business'),
(SELECT id FROM passagers WHERE cin = 'DE345678'), -- Laura Klein
(SELECT currval('reservations_id_seq'))),

(22, (SELECT id FROM classes WHERE nom = 'Business'),
(SELECT id FROM passagers WHERE cin = 'FR567890'), -- Jacques Durand
(SELECT currval('reservations_id_seq')));

-- Reservation 9: London to Berlin for client Anna Schmidt
INSERT INTO reservations (etat, prix_total, reserved_at, client_id, vol_id) VALUES
(0, 320.00, '2025-05-15 18:30:00',
(SELECT id FROM clients WHERE email = 'anna.schmidt@email.com'), 
(SELECT id FROM vols WHERE
aeroport_depart_id = (SELECT id FROM aeroports WHERE iata_code = 'LHR') AND
aeroport_arrive_id = (SELECT id FROM aeroports WHERE iata_code = 'BER') AND 
date_depart = '2025-05-22' AND heure_depart = '06:45:00')
);

-- Add ticket for this reservation
INSERT INTO billets (siege, classe_id, passager_id, reservation_id) VALUES
(180, (SELECT id FROM classes WHERE nom = 'Economy'),
(SELECT id FROM passagers WHERE cin = 'UK123456'), -- Emma Wilson
(SELECT currval('reservations_id_seq')));

-- Reservation 10: Paris to New York for client Omar Hassan
INSERT INTO reservations (etat, prix_total, reserved_at, client_id, vol_id) VALUES
(0, 3600.00, '2025-05-08 07:55:00',
(SELECT id FROM clients WHERE email = 'omar.hassan@email.com'),
(SELECT id FROM vols WHERE
aeroport_depart_id = (SELECT id FROM aeroports WHERE iata_code = 'CDG') AND
aeroport_arrive_id = (SELECT id FROM aeroports WHERE iata_code = 'JFK') AND
date_depart = '2025-05-20' AND heure_depart = '10:30:00') 
);

-- Add tickets for this reservation
INSERT INTO billets (siege, classe_id, passager_id, reservation_id) VALUES
(2, (SELECT id FROM classes WHERE nom = 'First Class'),
(SELECT id FROM passagers WHERE cin = 'AE456012'), -- Mohammed Al-Farsi
(SELECT currval('reservations_id_seq'))),

(3, (SELECT id FROM classes WHERE nom = 'First Class'),
(SELECT id FROM passagers WHERE cin = 'MA012678'), -- Fatima Benali
(SELECT currval('reservations_id_seq'))),

(50, (SELECT id FROM classes WHERE nom = 'Business'),
(SELECT id FROM passagers WHERE cin = 'ES567890'), -- Carlos Fernandez
(SELECT currval('reservations_id_seq')));


-- Make some additional reservations for these new flights
-- Reservation 11: Paris (ORY) to Madrid for client Jacques Durand
INSERT INTO reservations (etat, prix_total, reserved_at, client_id, vol_id) VALUES
(0, 560.00, '2025-05-14 10:35:00',
(SELECT id FROM clients WHERE email = 'jacques.durand@email.com'),
(SELECT id FROM vols WHERE
aeroport_depart_id = (SELECT id FROM aeroports WHERE iata_code = 'ORY') AND
aeroport_arrive_id = (SELECT id FROM aeroports WHERE iata_code = 'MAD') AND
date_depart = '2025-05-26' AND heure_depart = '09:15:00')
);

-- Add tickets for this reservation
INSERT INTO billets (siege, classe_id, passager_id, reservation_id) VALUES
(20, (SELECT id FROM classes WHERE nom = 'Business'),
(SELECT id FROM passagers WHERE cin = 'FR567890'),
(SELECT currval('reservations_id_seq')));
