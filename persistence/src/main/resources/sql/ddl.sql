CREATE USER MAPPING IF NOT EXISTS FOR db_user
SERVER PostgreSQL

-- Database: certificates

-- DROP DATABASE certificates;

CREATE DATABASE certificates
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'English_United States.1252'
    LC_CTYPE = 'English_United States.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

GRANT ALL PRIVILEGES ON certificates TO PostgreSQL;

//?
SET TIME ZONE '+3:00';