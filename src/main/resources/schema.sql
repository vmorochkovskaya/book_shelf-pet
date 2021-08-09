DROP TABLE IF EXISTS books;

CREATE TABLE  authors(
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(250) NOT NULL
);

CREATE TABLE  genres(
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(250) NOT NULL);

CREATE TABLE  books(
id INT AUTO_INCREMENT PRIMARY KEY,
idAuthor INT NOT NULL,
idGenre INT NOT NULL,
title VARCHAR(250) NOT NULL,
priceOld  VARCHAR(250) DEFAULT NULL,
price VARCHAR(250) DEFAULT NULL,
FOREIGN KEY (idAuthor) REFERENCES authors(id),
FOREIGN KEY (idGenre) REFERENCES genres(id));




