
DROP TABLE IF EXISTS employee CASCADE;

DROP SEQUENCE IF EXISTS seq1 CASCADE;

CREATE SEQUENCE seq1 START WITH 1;

CREATE TABLE employee (
   id BIGINT NOT NULL PRIMARY KEY DEFAULT nextval('seq1'),
   first_name VARCHAR(255) NOT NULL,
   last_name VARCHAR(255) NOT NULL,
   city VARCHAR(255)
);
