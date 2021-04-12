CREATE TABLE user (id VARCHAR(255)  PRIMARY KEY,
                   first_name VARCHAR(255),
                   last_name VARCHAR(255),
                   birthdate DATE,
                   email VARCHAR(255),
                   phone_number VARCHAR(255),
                   city VARCHAR(255),
                   sign_in_date TIMESTAMP,
                   username VARCHAR(255),
                   is_new BOOLEAN
                   );