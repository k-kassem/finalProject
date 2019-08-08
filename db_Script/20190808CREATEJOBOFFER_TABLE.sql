CREATE TABLE IF NOT EXISTS job_offer (
    id INT AUTO_INCREMENT,
	job_title VARCHAR(255) ,
	education VARCHAR(255) ,
	description VARCHAR(255) ,
	location VARCHAR(255) ,
	company_name VARCHAR(255) ,
	seniority_level VARCHAR(255) ,
	dead_Line date ,
	number_of_application VARCHAR(255) ,
	employement_Type VARCHAR(255) ,
	skills VARCHAR(255) ,
	experience VARCHAR(255) ,
    PRIMARY KEY (id) 
)  ENGINE=INNODB;