DROP TABLE IF EXISTS USERS;
DROP TABLE IF EXISTS RESOURCES;
DROP TABLE IF EXISTS ACCOUNTS;
CREATE TABLE USERS (ID INT IDENTITY(1,1) PRIMARY KEY, LOGIN VARCHAR(255) UNIQUE, PASS VARCHAR(255), SALT VARCHAR(255));
CREATE TABLE RESOURCES (ID INT IDENTITY(1,1) PRIMARY KEY, USERID INT, PATH VARCHAR(255), ROLE VARCHAR(255));
CREATE TABLE ACCOUNTS (ID INT IDENTITY(1,1),RESOURCEID INT, VOL INT, DS DATE, DE DATE);
ALTER TABLE RESOURCES ADD FOREIGN KEY (USERID) REFERENCES USERS (ID);
ALTER TABLE ACCOUNTS ADD FOREIGN KEY (RESOURCEID) REFERENCES RESOURCES (ID);