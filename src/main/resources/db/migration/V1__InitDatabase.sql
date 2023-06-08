CREATE TABLE doctor (
    id SERIAL NOT NULL PRIMARY KEY,
    username varchar (50),
    email varchar(100),
    phone_number BIGINT,
    skills varchar(30) ARRAY,
    biography TEXT,
    department varchar(40)
);

CREATE TABLE patient (
    id SERIAL NOT NULL PRIMARY KEY,
    username varchar(50),
    address varchar (100),
    phone_number BIGINT,
    email varchar (50),
    age INTEGER,
    blood_group VARCHAR(3),
    religion VARCHAR(20),
    occupation VARCHAR(30),
    gender CHAR(1),
    maritial_status VARCHAR(10),
    description TEXT
);

CREATE TABLE availability (
    id BIGSERIAL NOT NULL PRIMARY KEY,
    doctor_id BIGSERIAL NOT NULL, --Rel
    day_of_week varchar(10),
    start_time varchar(10),
    end_time varchar(10)
);

CREATE TABLE appointment (
    id BIGSERIAL NOT NULL PRIMARY KEY,
    doctor_id BIGSERIAL NOT NULL, --Rel
    patient_id BIGSERIAL NOT NULL, --Rel
    _date DATE,
    start_time varchar(5),
    end_time varchar(5),
    reason TEXT,
    medical_id BIGSERIAL
);

CREATE TABLE prescription (
    id BIGSERIAL NOT NULL PRIMARY KEY,
    medical_id BIGSERIAL NOT NULL, --Rel
    medication varchar (40),
    start_date DATE,
    end_date DATE,
    dosage integer,
    total DOUBLE PRECISION
);

CREATE TABLE medical_record (
    id BIGSERIAL NOT NULL PRIMARY KEY,
    doctor_id BIGSERIAL NOT NULL, --Rel
    patient_id BIGSERIAL NOT NULL, --Rel
    appointment_id BIGSERIAL NOT NULL, --Rel
    check_in_date DATE,
    disease varchar(40),
    status varchar(15),
    room_no varchar(6)
);

CREATE TABLE expense (
    id BIGSERIAL NOT NULL PRIMARY KEY,
    user_id BIGSERIAL, --Rel
    name varchar(30),
    category varchar(30),
    description TEXT,
    amount DOUBLE PRECISION,
    date_of_expense DATE,
    paid BOOLEAN
);

ALTER TABLE availability ADD CONSTRAINT fk_doctor FOREIGN KEY (doctor_id) REFERENCES doctor(id) ON DELETE CASCADE;

ALTER TABLE appointment ADD CONSTRAINT fk_doctor FOREIGN KEY (doctor_id) REFERENCES doctor(id),
                        ADD CONSTRAINT fk_patient FOREIGN KEY (patient_id) REFERENCES patient(id),
                        ADD CONSTRAINT fk_medical FOREIGN KEY (medical_id) REFERENCES medical_record(id);

ALTER TABLE prescription ADD CONSTRAINT fk_medical FOREIGN KEY (medical_id) REFERENCES medical_record(id);

ALTER TABLE medical_record ADD CONSTRAINT fk_doctor FOREIGN KEY (doctor_id) REFERENCES doctor(id),
                           ADD CONSTRAINT fk_patient FOREIGN KEY (patient_id) REFERENCES patient(id),
                           ADD CONSTRAINT fk_appointment FOREIGN KEY (appointment_id) REFERENCES appointment(id);

ALTER TABLE expense ADD CONSTRAINT fk_patient FOREIGN KEY (user_id) REFERENCES patient(id);