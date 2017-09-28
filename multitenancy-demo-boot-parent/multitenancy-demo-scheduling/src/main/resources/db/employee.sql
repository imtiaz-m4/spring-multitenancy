CREATE TABLE employee (
  id bigint NOT NULL AUTO_INCREMENT,
  first_name varchar(250) DEFAULT NULL,
  last_name varchar(250) DEFAULT NULL,
  department varchar(250) DEFAULT NULL,
  office varchar(250) DEFAULT NULL,
  email varchar(250) DEFAULT NULL,
  phone varchar(250) DEFAULT NULL,
  version integer,
  PRIMARY KEY ('id'),
  UNIQUE KEY 'id_UNIQUE' ('id')
);
