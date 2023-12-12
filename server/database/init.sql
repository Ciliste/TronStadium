-- PostgreSQL

CREATE TABLE IF NOT EXISTS users (

	id SERIAL PRIMARY KEY,
	username VARCHAR(25) NOT NULL,
	password_hash VARCHAR(60) NOT NULL,
	created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	token VARCHAR(20),
	token_expires TIMESTAMP
);