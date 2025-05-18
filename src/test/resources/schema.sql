CREATE TABLE students
(
    student_id int PRIMARY KEY AUTO_INCREMENT,
    name varchar(10) DEFAULT NULL,
    name_reading varchar(20) DEFAULT NULL,
    nickname varchar(10) DEFAULT NULL,
    mail_address varchar(50) DEFAULT NULL,
    city varchar(10) DEFAULT NULL,
    age int DEFAULT NULL,
    gender varchar(3) DEFAULT NULL,
    remark varchar(100) DEFAULT NULL,
    is_delete int DEFAULT 0
);

CREATE TABLE course_id_name
(
  course_id varchar(3) NOT NULL,
  course_name varchar(20) DEFAULT NULL,
  PRIMARY KEY (course_id)
);

CREATE TABLE students_courses
(
  course_detail_id int AUTO_INCREMENT PRIMARY KEY,
  student_id int,
  course_id varchar(3) DEFAULT NULL,
  course_name varchar(20) DEFAULT NULL,
  started_date date DEFAULT NULL,
  finish_date date DEFAULT NULL,
  is_delete int DEFAULT 0,
  FOREIGN KEY (student_id) REFERENCES students (student_id),
  FOREIGN KEY (course_id) REFERENCES course_id_name (course_id)
);

