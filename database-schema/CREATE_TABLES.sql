DROP DATABASE IF EXISTS miniasaas;
CREATE DATABASE miniasaas;
USE miniasaas;

DROP TABLE IF EXISTS customers;
CREATE TABLE customers (
	id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    cpf_cnpj VARCHAR(15) UNIQUE NOT NULL,
    state VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    street VARCHAR(255) NOT NULL,
    number INT NOT NULL,
    postal_code VARCHAR(255) NOT NULL,
	date_created DATETIME NOT NULL,
    date_updated DATETIME,
    deleted BOOL,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS users;
CREATE TABLE users (
	id INT NOT NULL AUTO_INCREMENT,
    customer_id INT NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    date_created DATETIME NOT NULL,
    date_updated DATETIME,
    deleted BOOL NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customers(id),
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS payers;
CREATE TABLE payers (
	id INT NOT NULL AUTO_INCREMENT,
    customer_id INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    cpf_cnpj VARCHAR(15) NOT NULL,
    state VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    street VARCHAR(255) NOT NULL,
    number INT NOT NULL,
    postal_code VARCHAR(255) NOT NULL,
	date_created DATETIME NOT NULL,
    date_updated DATETIME,
    deleted BOOL NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (customer_id) REFERENCES customers(id)
);

DROP TABLE IF EXISTS payment;
CREATE TABLE payment (
	id INT NOT NULL AUTO_INCREMENT,
    customer_id INT NOT NULL,
    payer_id INT NOT NULL,
    payment_type VARCHAR(30) NOT NULL,
    value DOUBLE NOT NULL,
    status VARCHAR(30) NOT NULL,
    due_date DATETIME NOT NULL,
    date_received DATETIME,
	date_created DATETIME NOT NULL,
    date_updated DATETIME,
    deleted BOOL NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (customer_id) REFERENCES customers(id),
    FOREIGN KEY (payer_id) REFERENCES payers(id)
);