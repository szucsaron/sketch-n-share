DROP TABLE IF EXISTS users;

-- Create tables

CREATE TABLE users (
	id SERIAL PRIMARY KEY,
	name VARCHAR(50) UNIQUE,
	password VARCHAR(200),
	role NUMERIC(1)
);


-- Insert data


INSERT INTO users (name, password, role) VALUES 
('a', 'a', '0');