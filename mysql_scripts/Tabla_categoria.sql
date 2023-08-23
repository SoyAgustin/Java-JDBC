USE control_de_stock;

/*Se crea la tabla categoría con 
los campos id y nombre en la DB*/
CREATE TABLE categoria(
id INT AUTO_INCREMENT,
nombre VARCHAR(50) NOT NULL,
PRIMARY KEY(id)
)Engine=InnoDB;

/*Creamos varias categorias al mismo
tiempo*/
INSERT INTO categoria (nombre)
VALUES('Muebles'),('Tecnologia'),('Cocina'),('Zapatillas');

/*Visualizamos la tabla creada*/
SELECT * FROM categoria;

/*Creamos una nueva columna en producto*/
ALTER TABLE producto
ADD COLUMN categoria_id INT;

/*Visualizamos la tabla alterada*/
SELECT * FROM producto;

/*Vinculamos las dos tablas mediante una llave foranea*/
ALTER TABLE producto
ADD FOREIGN KEY (categoria_id) REFERENCES categoria(id);

/*Ahora debemos agregar cada artículo a su categoria*/
UPDATE producto
SET categoria_id=1
WHERE id=1;

UPDATE producto
SET categoria_id=2
WHERE id=2;

UPDATE producto
SET categoria_id=2
WHERE id=10;

UPDATE producto
SET categoria_id=2
WHERE id=11;

UPDATE producto
SET categoria_id=4
WHERE id=12;

UPDATE producto
SET categoria_id=4
WHERE id=13;

UPDATE producto
SET categoria_id=3
WHERE id=14;

UPDATE producto
SET categoria_id=3
WHERE id=36;

UPDATE producto
SET categoria_id=3
WHERE id=37;

UPDATE producto
SET categoria_id=3
WHERE id=38;

UPDATE producto
SET categoria_id=2
WHERE id=39;

/*Visualizamos nuevamente los productos
con su categoria_id asignada*/
SELECT * FROM producto;
