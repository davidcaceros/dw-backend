------------------------------------------------------------- proveedor -----------------------------------------------------------------------------
CREATE SEQUENCE seq_proveedor START 1;

CREATE TABLE proveedor (
	id_proveedor INT DEFAULT NEXTVAL('seq_proveedor'),
	nombre VARCHAR(50) NOT NULL,
	CONSTRAINT pk_proveedor PRIMARY KEY (id_proveedor)
);
------------------------------------------------------------- producto -----------------------------------------------------------------------------
CREATE SEQUENCE seq_producto START 1;

CREATE TABLE producto (
	id_producto INT DEFAULT NEXTVAL('seq_producto'),
    nombre VARCHAR(50) NOT NULL UNIQUE,
	descripcion VARCHAR(50),
	ubicacion_fisica VARCHAR(50),
	existencia_minima INT,
	codigo_proveedor INT,
	fecha_vencimiento DATE,
	activo BOOLEAN DEFAULT TRUE,
	fecha_creacion TIMESTAMP,
    fecha_actualizacion TIMESTAMP,
	CONSTRAINT pk_producto PRIMARY KEY (id_producto),
	CONSTRAINT proveedor_producto_fk FOREIGN KEY (codigo_proveedor) REFERENCES proveedor (id_proveedor)
);