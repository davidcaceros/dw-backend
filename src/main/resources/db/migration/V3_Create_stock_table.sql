------------------------------------------------------------- stock -----------------------------------------------------------------------------
CREATE SEQUENCE seq_stock START 1;

CREATE TABLE stock (
	id_stock INT DEFAULT NEXTVAL('seq_stock'),
	id_producto INT,
	existencia INT,
	fecha_creacion TIMESTAMP NOT NULL,
	fecha_actualizacion TIMESTAMP,
	CONSTRAINT pk_stock PRIMARY KEY (idstock),
	CONSTRAINT producto_stock_fk FOREIGN KEY (id_producto) REFERENCES producto (id_producto)
);