
Create Table PRODUCT(
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(250) NOT NULL,
  category VARCHAR(250) NOT NULL,
  price DOUBLE  NOT NULL,
  releaseDate DATE,
  available BOOLEAN
);