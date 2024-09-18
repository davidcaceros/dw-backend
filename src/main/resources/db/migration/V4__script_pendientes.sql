-----------------------------------VENTA----------------------------------------
CREATE SEQUENCE seq_venta START 1;

CREATE TABLE venta (
	id_venta INT DEFAULT NEXTVAL('seq_venta'),
	id_cliente INT NOT NULL,
	id_vendedor INT NOT NULL,
	tipo_venta VARCHAR(25) NOT NULL,
	fecha_creacion TIMESTAMP NOT NULL,
	fecha_actualizacion TIMESTAMP,
	total_venta DOUBLE PRECISION NOT NULL,
	estado VARCHAR(25) NOT NULL,
	devolucion BOOLEAN,
	saldo_favor DOUBLE PRECISION,
	CONSTRAINT pk_venta PRIMARY KEY (id_venta),
	CONSTRAINT persona_cliente_fk FOREIGN KEY (id_cliente) REFERENCES persona (id_persona),
	CONSTRAINT persona_vendedor_fk FOREIGN KEY (id_vendedor) REFERENCES persona (id_persona)
);

-----------------------------------NOTA_CREDITO-----------------------------------

CREATE SEQUENCE seq_nota_credito START 1;

CREATE TABLE nota_credito (
	id_nota INT DEFAULT NEXTVAL('seq_nota_credito'),
	id_venta INT,
	descripcion VARCHAR(255) NOT NULL,
	CONSTRAINT pk_nota_credito PRIMARY KEY (id_nota),
	CONSTRAINT venta_nota_credito_fk FOREIGN KEY (id_venta) REFERENCES venta (id_venta)
);

---------------------------------DETALLE_VENTA-------------------------------------


CREATE SEQUENCE seq_detalle_venta START 1;

CREATE TABLE detalle_venta (
	id_detalle INT DEFAULT NEXTVAL('seq_detalle_venta'),
	id_producto INT NOT NULL,
	id_venta INT NOT NULL,
	cantidad DOUBLE PRECISION NOT NULL,
	fecha_creacion TIMESTAMP NOT NULL,
	fecha_actualizacion TIMESTAMP,
	CONSTRAINT pk_detalle_venta PRIMARY KEY (id_detalle),
	CONSTRAINT producto_detalle_venta_fk FOREIGN KEY (id_producto) REFERENCES producto (id_producto),
	CONSTRAINT venta_detalle_venta_fk FOREIGN KEY (id_venta) REFERENCES venta (id_venta)
);

-----------------------------------BITACORA_PAQUETE------------------------------------

CREATE SEQUENCE seq_bitacora_entregas START 1;
CREATE SEQUENCE seq_codigo_entrega START 1;

CREATE TABLE bitacora_entregas (
	id_bitacora INT DEFAULT NEXTVAL('seq_bitacora_entregas'),
	id_venta INT NOT NULL,
	estado VARCHAR(25) NOT NULL,
	latitud VARCHAR(150),
	longitud VARCHAR(150),
	fecha_creacion TIMESTAMP NOT NULL,
	fecha_actualizacion TIMESTAMP,
	codigo_entrega VARCHAR(20) UNIQUE,
	CONSTRAINT pk_bitacora_entregas PRIMARY KEY (id_bitacora),
	CONSTRAINT venta_bitacora_entregas_fk FOREIGN KEY (id_venta) REFERENCES venta (id_venta)
);

------------------------DEPARTAMENTO----------------------
CREATE SEQUENCE seq_departamento START 1;

CREATE TABLE departamento (
    id_departamento INT DEFAULT NEXTVAL('seq_departamento'),
    nombre_departamento VARCHAR(100) NOT NULL,
    codigo_departamento VARCHAR(3) NOT NULL,
	CONSTRAINT pk_departamento PRIMARY KEY (id_departamento)
);

------------------------MUNICIPIO----------------------

CREATE SEQUENCE seq_municipio START 1;

CREATE TABLE municipio (
    id_municipio INT DEFAULT NEXTVAL('seq_municipio'),
    id_departamento INT,
    nombre_municipio VARCHAR(100) NOT NULL,
    codigo_municipio VARCHAR(2) NOT NULL,
	CONSTRAINT pk_municipio PRIMARY KEY (id_municipio),
    CONSTRAINT fk_departamento_municipio FOREIGN KEY (id_departamento) REFERENCES departamento (id_departamento)
);

------------------------------------FUNCION_CODIGO_ENTREGA---------------------------------------

CREATE OR REPLACE FUNCTION generar_codigo_entrega(
    p_id_departamento INT,
    p_id_municipio INT
) RETURNS VARCHAR AS $$
DECLARE
    v_codigo_departamento VARCHAR(3);
    v_codigo_municipio VARCHAR(2);
    v_secuencia VARCHAR(7);
    v_codigo_entrega VARCHAR(20);
BEGIN
    SELECT codigo_departamento INTO v_codigo_departamento
    FROM departamento
    WHERE id_departamento = p_id_departamento;

    SELECT codigo_municipio INTO v_codigo_municipio
    FROM municipio
    WHERE id_municipio = p_id_municipio;

    v_secuencia := LPAD(NEXTVAL('seq_codigo_entrega')::text, 7, '0');

    v_codigo_entrega := v_codigo_departamento || v_codigo_municipio || v_secuencia;

    RETURN v_codigo_entrega;
END;
$$ LANGUAGE plpgsql;

------------------------------------------------------------- ALTER-----------------------------------------------------------------------------
ALTER TABLE producto ADD precio DOUBLE PRECISION;