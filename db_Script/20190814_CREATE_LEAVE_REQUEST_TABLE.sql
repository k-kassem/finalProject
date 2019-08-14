CREATE TABLE IF NOT EXISTS leave_request (
    id INT AUTO_INCREMENT,
	userid VARCHAR(255) ,
	start_date date ,
	end_date date ,
	user_Name VARCHAR(255) ,
	user_Last_Name VARCHAR(255) ,
	departement VARCHAR(255),
	address_During_Leave VARCHAR(255),
	nb_Of_Days integer(100),
	leave_Type varchar(250),
    PRIMARY KEY (id) 
)  ENGINE=INNODB;