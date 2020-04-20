CREATE TABLE organization (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(100),
  PRIMARY KEY (id)
) ENGINE = InnoDB;

CREATE TABLE user (
  username VARCHAR(64) NOT NULL,
  password VARCHAR(64) NOT NULL,
  org_id INT,
  PRIMARY KEY (username),
  CONSTRAINT
    FOREIGN KEY (org_id) REFERENCES organization (id)
    ON DELETE SET NULL
    ON UPDATE CASCADE
) ENGINE = InnoDB;
