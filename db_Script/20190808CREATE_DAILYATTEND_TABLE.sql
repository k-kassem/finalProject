CREATE TABLE IF NOT EXISTS daily_attend (
    id INT AUTO_INCREMENT,
	user_Id VARCHAR(255) ,
	start_Time VARCHAR(255) ,
	user_name VARCHAR(255) ,
	date date,
    PRIMARY KEY (id) 
)  ENGINE=INNODB;