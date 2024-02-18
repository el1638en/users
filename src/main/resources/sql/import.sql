INSERT INTO roles (id, name) VALUES (1, 'ROLE_USER') on conflict (name) do nothing;
INSERT INTO roles (id, name) VALUES (2, 'ROLE_MODERATOR') on conflict (name) do nothing;
INSERT INTO roles (id, name) VALUES (3, 'ROLE_ADMIN') on conflict (name) do nothing;

INSERT INTO users (email, password) VALUES ('jackson.michell@test.com', '$2a$10$mmEBQaI.2Om2JoQHxKACaO6.44he5wnk4Ko9nolvDpkegC3ekGS9C');
INSERT INTO user_roles VALUES (LASTVAL(), 1);
INSERT INTO patients (address, birthdate, city, email, firstname, lastname, latitude, longitude, phone, postcode, ssnumber, user_id) VALUES ('Margam Country Park', '1973-04-30', 'Port Talbot', 'jackson.michell@test.com', 'Jackson', 'Mitchell', 51.562731, -3.725558, '0845 643 9215', 'SA13 2TJ', 'ZZ361112T', 1);

INSERT INTO users  (email, password) VALUES ('mandy.lloyd@test.com', '$2a$10$cvT3FWEzH0wgfi1xgnpmsebUbXo435txY0N2ml2.9ka1IqAM7Ux5m');
INSERT INTO user_roles VALUES (LASTVAL(), 2);
INSERT INTO patients (address, birthdate, city, email, firstname, lastname, latitude, longitude, phone, postcode, ssnumber, user_id) VALUES ('Crown Passage, St James''s', '1974-07-24', 'London', 'mandy.lloyd@test.com', 'Mandy', 'Lloyd', 51.503129, -0.133165, '020 7839 8831', 'SW1Y 6QY', 'ZZ576137T', 2);

INSERT INTO users  (email, password) VALUES ('jayden.reid@test.com', '$2a$10$j8WGw7toEPuxZWjtKb4uMucAu2MdUsliheBJqeSmwFsdE4pr7mxPe');
INSERT INTO user_roles VALUES (LASTVAL(), 3);
INSERT INTO patients (address, birthdate, city, email, firstname, lastname, latitude, longitude, phone, postcode, ssnumber, user_id) VALUES ('23 Smith St', '1982-03-28', 'London', 'jayden.reid@test.com', 'Jayden', 'Reid', 51.488954, -0.163229, '020 7730 9182', 'SW3 4EW', 'ZZ204368T', 3);


