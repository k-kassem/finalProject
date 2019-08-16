CREATE TABLE IF NOT EXISTS message (
    id INT AUTO_INCREMENT,
	to_user_id VARCHAR(255) ,
	sent_date date ,
	from_user_id VARCHAR(255) ,
	title VARCHAR(255) ,
	context VARCHAR(255),
    PRIMARY KEY (id) 
)  ENGINE=INNODB;