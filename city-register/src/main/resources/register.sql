DROP TABLE IF EXISTS cr_address_person;
DROP TABLE IF EXISTS cr_person;
DROP TABLE IF EXISTS cr_address;
DROP TABLE IF EXISTS cr_street;
DROP TABLE IF EXISTS cr_district;


CREATE TABLE cr_district
(
    district_code INTEGER PRIMARY KEY NOT NULL,
    district_name VARCHAR(300)        NOT NULL
);

CREATE TABLE cr_street
(
    street_code INTEGER PRIMARY KEY NOT NULL,
    street_name VARCHAR(300)        NOT NULL
);

CREATE TABLE cr_address
(
    address_id    SERIAL PRIMARY KEY NOT NULL,
    district_code INTEGER            NOT NULL REFERENCES cr_district (district_code) ON DELETE RESTRICT,
    street_code   INTEGER            NOT NULL REFERENCES cr_street (street_code) ON DELETE RESTRICT,
    building      VARCHAR(10)        NOT NULL,
    extension     VARCHAR(10),
    apartment     VARCHAR(10)
);

CREATE TABLE cr_person
(
    person_id SERIAL PRIMARY KEY NOT NULL ,
    sur_name        VARCHAR(100) NOT NULL,
    given_name      VARCHAR(100) NOT NULL,
    patronymic      VARCHAR(100) NOT NULL,
    date_of_birth   DATE         NOT NULL,
    passport_seria  VARCHAR(10),
    passport_number VARCHAR(10),
    passport_date   DATE,
    c_certificate_number VARCHAR(10),
    c_certificate_date   DATE
);

CREATE TABLE cr_address_person (
    person_address_id SERIAL PRIMARY KEY NOT NULL ,
    address_id INTEGER NOT NULL REFERENCES cr_address (address_id) ON DELETE RESTRICT ,
    person_id INTEGER NOT NULL REFERENCES  cr_person (person_id) ON DELETE RESTRICT ,
    start_date DATE NOT NULL ,
    end_date DATE,
    temporal BOOLEAN DEFAULT FALSE
);

-- INSERT
INSERT INTO cr_district(district_code, district_name)
VALUES (1, 'Выборгский');

INSERT INTO cr_street(street_code, street_name)
VALUES (1, 'Самсоньевский проспект');

INSERT INTO cr_address(district_code, street_code, building, extension, apartment)
VALUES (1, 1, '10', '2', '121');

INSERT INTO cr_person(sur_name, given_name, patronymic, date_of_birth, passport_seria,
                      passport_number, passport_date, c_certificate_number, c_certificate_date)
VALUES ('Васильев','Павел','Николаевич','1995-03-18','1234','123456','2015-04-11', null, null);

INSERT INTO cr_person(sur_name, given_name, patronymic, date_of_birth, passport_seria,
                      passport_number, passport_date, c_certificate_number, c_certificate_date)
VALUES ('Васильева','Ирина','Николаевна','1997-08-21','4321','654321','2017-09-19', null, null);

INSERT INTO cr_person(sur_name, given_name, patronymic, date_of_birth, passport_seria,
                      passport_number, passport_date, c_certificate_number, c_certificate_date)
VALUES ('Васильева','Евгения','Павловна','2016-01-11', null, null, null, '456123', '2016-01-21');

INSERT INTO cr_person(sur_name, given_name, patronymic, date_of_birth, passport_seria,
                      passport_number, passport_date, c_certificate_number, c_certificate_date)
VALUES ('Васильев','Флександр','Павлович','2018-10-24', null, null, null, '321456', '2018-11-09');

INSERT INTO cr_address_person(address_id, person_id, start_date, end_date, temporal)
VALUES (1, 1, '2014-10-12', null, false);

INSERT INTO cr_address_person(address_id, person_id, start_date, end_date, temporal)
VALUES (1, 2, '2014-10-12', null, false);

INSERT INTO cr_address_person(address_id, person_id, start_date, end_date, temporal)
VALUES (1, 3, '2016-02-05', null, false);

INSERT INTO cr_address_person(address_id, person_id, start_date, end_date, temporal)
VALUES (1, 4, '2018-12-11', null, false);