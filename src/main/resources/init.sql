DROP TABLE IF EXISTS users;

-- Create tables

CREATE TABLE users (
	id SERIAL PRIMARY KEY,
	namee VARCHAR(50) UNIQUE,
	passworde VARCHAR(200),
	rolee BOOL
)


-- Insert data


INSERT INTO users (namee, passwordd, rolee) VALUES 
('a', 'a', '0');