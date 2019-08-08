CREATE TABLE IF NOT EXISTS monthly_attend (
    id INT AUTO_INCREMENT,
	user_Id VARCHAR(255) ,
	start_Time VARCHAR(255) ,
	end_Time VARCHAR(255) ,
	first_name VARCHAR(255) ,
	last_name VARCHAR(255) ,
	nb_of_hour double ,
	date date,
    PRIMARY KEY (id) 
)  ENGINE=INNODB;