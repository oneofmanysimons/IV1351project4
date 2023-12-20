CREATE TABLE address (
 address_id INT NOT NULL,
 country VARCHAR(50),
 region VARCHAR(50),
 town VARCHAR(50),
 street_name VARCHAR(50),
 street_number INT
);

ALTER TABLE address ADD CONSTRAINT PK_address PRIMARY KEY (address_id);


CREATE TABLE instructor (
 instructor_id INT NOT NULL,
 name VARCHAR(50),
 email VARCHAR(50),
 phone_number REAL,
 personal_number REAL,
 instrument INT
);

ALTER TABLE instructor ADD CONSTRAINT PK_instructor PRIMARY KEY (instructor_id);


CREATE TABLE instrument (
 instrument_id INT NOT NULL,
 instrument_name VARCHAR(50),
 brand VARCHAR(50),
 price INT
);

ALTER TABLE instrument ADD CONSTRAINT PK_instrument PRIMARY KEY (instrument_id);


CREATE TABLE lesson (
 lesson_id INT NOT NULL,
 instructor_id INT NOT NULL,
 student_max INT,
 address INT NOT NULL,
 date TIMESTAMP(10)
);

ALTER TABLE lesson ADD CONSTRAINT PK_lesson PRIMARY KEY (lesson_id);


CREATE TABLE rent_terminate (
 instrument_id INT NOT NULL,
 student_id INT,
 date_rent VARCHAR(50),
 date_return VARCHAR(50),
 date_must_return VARCHAR(50),
 price INT,
 date VARCHAR(50)
);


CREATE TABLE student (
 student_id INT NOT NULL,
 name VARCHAR(50),
 email VARCHAR(50),
 phone REAL,
 personal_number REAL
);

ALTER TABLE student ADD CONSTRAINT PK_student PRIMARY KEY (student_id);


CREATE TABLE address_instructor (
 address_id INT NOT NULL,
 instructor_id INT NOT NULL
);

ALTER TABLE address_instructor ADD CONSTRAINT PK_address_instructor PRIMARY KEY (address_id,instructor_id);


CREATE TABLE address_lesson (
 lesson_id INT NOT NULL,
 address_id INT NOT NULL
);

ALTER TABLE address_lesson ADD CONSTRAINT PK_address_lesson PRIMARY KEY (lesson_id,address_id);


CREATE TABLE address_student (
 student_id INT NOT NULL,
 address_id INT NOT NULL
);

ALTER TABLE address_student ADD CONSTRAINT PK_address_student PRIMARY KEY (student_id,address_id);


CREATE TABLE ensemble (
 lesson_id INT NOT NULL,
 genre SMALLINT,
 student_num INT
);

ALTER TABLE ensemble ADD CONSTRAINT PK_ensemble PRIMARY KEY (lesson_id);


CREATE TABLE group_lesson (
 lesson_id INT NOT NULL,
 level SMALLINT NOT NULL,
 student_num INT
);

ALTER TABLE group_lesson ADD CONSTRAINT PK_group_lesson PRIMARY KEY (lesson_id);


CREATE TABLE individual_lesson (
 lesson_id INT NOT NULL,
 level SMALLINT NOT NULL,
 student_id INT
);

ALTER TABLE individual_lesson ADD CONSTRAINT PK_individual_lesson PRIMARY KEY (lesson_id);


CREATE TABLE parent (
 parent_id INT NOT NULL,
 student_id INT NOT NULL,
 name VARCHAR(50),
 email VARCHAR(50),
 phone_number REAL,
 personal_number REAL
);

ALTER TABLE parent ADD CONSTRAINT PK_parent PRIMARY KEY (parent_id,student_id);


CREATE TABLE rent_instrument (
 instrument_id INT NOT NULL,
 student_id INT NOT NULL,
 date_rent VARCHAR(50),
 date_return VARCHAR(50),
 date_must_return VARCHAR(50),
 price INT
);

ALTER TABLE rent_instrument ADD CONSTRAINT PK_rent_instrument PRIMARY KEY (instrument_id,student_id);


CREATE TABLE sibling (
 student_id INT NOT NULL,
 sibling_id INT
);

ALTER TABLE sibling ADD CONSTRAINT PK_sibling PRIMARY KEY (student_id);


CREATE TABLE accounting (
 lesson_id INT NOT NULL,
 student INT,
 instructor_id INT,
 level SMALLINT,
 date TIMESTAMP(10)
);

ALTER TABLE accounting ADD CONSTRAINT PK_accounting PRIMARY KEY (lesson_id);


CREATE TABLE instructor_pay (
 lesson_id INT NOT NULL,
 instructor_id INT NOT NULL,
 level SMALLINT,
 date TIMESTAMP(10),
 price INT
);

ALTER TABLE instructor_pay ADD CONSTRAINT PK_instructor_pay PRIMARY KEY (lesson_id,instructor_id);


CREATE TABLE students (
 lesson_id INT NOT NULL,
 student_id INT
);

ALTER TABLE students ADD CONSTRAINT PK_students PRIMARY KEY (lesson_id);


CREATE TABLE sibling_attend (
 sibling_id INT NOT NULL,
 lesson_id INT NOT NULL,
 student_id INT
);

ALTER TABLE sibling_attend ADD CONSTRAINT PK_sibling_attend PRIMARY KEY (sibling_id);


CREATE TABLE student_pay (
 lesson_id INT NOT NULL,
 student_id INT NOT NULL,
 level SMALLINT,
 sibling_attend INT,
 date TIMESTAMP(10),
 price INT
);

ALTER TABLE student_pay ADD CONSTRAINT PK_student_pay PRIMARY KEY (lesson_id,student_id);


ALTER TABLE rent_terminate ADD CONSTRAINT FK_rent_terminate_0 FOREIGN KEY (instrument_id) REFERENCES instrument (instrument_id);


ALTER TABLE address_instructor ADD CONSTRAINT FK_address_instructor_0 FOREIGN KEY (address_id) REFERENCES address (address_id);
ALTER TABLE address_instructor ADD CONSTRAINT FK_address_instructor_1 FOREIGN KEY (instructor_id) REFERENCES instructor (instructor_id);


ALTER TABLE address_lesson ADD CONSTRAINT FK_address_lesson_0 FOREIGN KEY (lesson_id) REFERENCES lesson (lesson_id);
ALTER TABLE address_lesson ADD CONSTRAINT FK_address_lesson_1 FOREIGN KEY (address_id) REFERENCES address (address_id);


ALTER TABLE address_student ADD CONSTRAINT FK_address_student_0 FOREIGN KEY (student_id) REFERENCES student (student_id);
ALTER TABLE address_student ADD CONSTRAINT FK_address_student_1 FOREIGN KEY (address_id) REFERENCES address (address_id);


ALTER TABLE ensemble ADD CONSTRAINT FK_ensemble_0 FOREIGN KEY (lesson_id) REFERENCES lesson (lesson_id);


ALTER TABLE group_lesson ADD CONSTRAINT FK_group_lesson_0 FOREIGN KEY (lesson_id) REFERENCES lesson (lesson_id);


ALTER TABLE individual_lesson ADD CONSTRAINT FK_individual_lesson_0 FOREIGN KEY (lesson_id) REFERENCES lesson (lesson_id);


ALTER TABLE parent ADD CONSTRAINT FK_parent_0 FOREIGN KEY (student_id) REFERENCES student (student_id);


ALTER TABLE rent_instrument ADD CONSTRAINT FK_rent_instrument_0 FOREIGN KEY (student_id) REFERENCES student (student_id);
ALTER TABLE rent_instrument ADD CONSTRAINT FK_rent_instrument_1 FOREIGN KEY (instrument_id) REFERENCES instrument (instrument_id);


ALTER TABLE sibling ADD CONSTRAINT FK_sibling_0 FOREIGN KEY (student_id) REFERENCES student (student_id);


ALTER TABLE accounting ADD CONSTRAINT FK_accounting_0 FOREIGN KEY (lesson_id) REFERENCES individual_lesson (lesson_id);
ALTER TABLE accounting ADD CONSTRAINT FK_accounting_1 FOREIGN KEY (lesson_id) REFERENCES group_lesson (lesson_id);
ALTER TABLE accounting ADD CONSTRAINT FK_accounting_2 FOREIGN KEY (lesson_id) REFERENCES ensemble (lesson_id);


ALTER TABLE instructor_pay ADD CONSTRAINT FK_instructor_pay_0 FOREIGN KEY (lesson_id) REFERENCES accounting (lesson_id);


ALTER TABLE students ADD CONSTRAINT FK_students_0 FOREIGN KEY (lesson_id) REFERENCES group_lesson (lesson_id);
ALTER TABLE students ADD CONSTRAINT FK_students_1 FOREIGN KEY (lesson_id) REFERENCES ensemble (lesson_id);
ALTER TABLE students ADD CONSTRAINT FK_students_2 FOREIGN KEY (student_id) REFERENCES accounting (lesson_id);


ALTER TABLE sibling_attend ADD CONSTRAINT FK_sibling_attend_0 FOREIGN KEY (lesson_id) REFERENCES students (lesson_id);
ALTER TABLE sibling_attend ADD CONSTRAINT FK_sibling_attend_1 FOREIGN KEY (student_id) REFERENCES sibling (student_id);


ALTER TABLE student_pay ADD CONSTRAINT FK_student_pay_0 FOREIGN KEY (lesson_id) REFERENCES accounting (lesson_id);
ALTER TABLE student_pay ADD CONSTRAINT FK_student_pay_1 FOREIGN KEY (student_id) REFERENCES accounting (lesson_id);
ALTER TABLE student_pay ADD CONSTRAINT FK_student_pay_2 FOREIGN KEY (sibling_attend) REFERENCES sibling_attend (sibling_id);


