CREATE DATABASE control_de_stock;
USE control_de_stock;
CREATE table producto(
id INT AUTO_INCREMENT, /*Aumento automatico del índice*/
nombre VARCHAR(50) NOT NULL,
descripcion VARCHAR(255),
cantidad INT NOT NULL DEFAULT 0,
PRIMARY KEY(id)
) ENGINE = InnoDB;

INSERT INTO producto(nombre, descripcion, cantidad)
 VALUES ('Mesa','Mesa de 4 lugares',10);
 
INSERT INTO producto(nombre, descripcion, cantidad)
 VALUES ('Celular','Celular Samsung',50);

SELECT * FROM producto;