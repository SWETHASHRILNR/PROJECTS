CREATE DATABASE IF NOT EXISTS book;
USE book;

CREATE TABLE IF NOT EXISTS admin(
aid INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
aname VARCHAR(150) NOT NULL,
apass VARCHAR(150) NOT NULL);

CREATE TABLE IF NOT EXISTS book(
bid INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
btitle VARCHAR(150) NOT NULL,
keywords VARCHAR(150) NOT NULL,
file VARCHAR(150) NOT NULL);

CREATE TABLE IF NOT EXISTS comment(
cid INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
bid INT NOT NULL,
sid INT NOT NULL,
comm VARCHAR(150) NOT NULL,
logs DATETIME NOT NULL);
DROP TABLE comment;

CREATE TABLE IF NOT EXISTS request(
rid INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
id INT NOT NULL,
mes TEXT NOT NULL,
logs DATETIME NOT NULL);

CREATE TABLE IF NOT EXISTS student(
id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(150) NOT NULL,
pass VARCHAR(150) NOT NULL,
mail VARCHAR(150) NOT NULL,
dep VARCHAR(150) NOT NULL);

INSERT INTO admin VALUES(NULL, 'admin', 'admin');
INSERT INTO student VALUES(NULL, 'Swetha', 1234, 'swe@gmail.com', 'CSE');
INSERT INTO book VALUES(NULL, 'Data Structures','data, structure, algorithm', 'upload/DATA STRUCTURE.pdf');
INSERT INTO request VALUES(NULL, 1, 'Need PHP book', CURRENT_TIMESTAMP());
INSERT INTO comment VALUES(NULL, 1, 1, 'Useful and easy to study', CURRENT_TIMESTAMP());
SELECT student.name, request.mes, request.logs FROM student INNER JOIN request ON student.id=request.id;

SELECT book.btitle, student.name, comment.comm, comment.logs FROM comment INNER JOIN book ON book.bid=comment.bid INNER JOIN student ON comment.sid=student.id;

SELECT * FROM admin;
SELECT * FROM student;
SELECT * FROM request;
SELECT * FROM book;
DELETE FROM student WHERE id=2;

SELECT student.name, comment.comm, comment.logs FROM comment INNER JOIN student ON comment.sid=student.id WHERE comment.bid=1 ORDER BY comment.cid DESC;




