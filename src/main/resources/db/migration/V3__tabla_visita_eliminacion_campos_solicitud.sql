------------------------------------------------------------- VISITA -----------------------------------------------------------------------------

CREATE TABLE VISITA (
  id                    SERIAL      NOT NULL,
  id_solicitud          BIGINT      NOT NULL,
  latitud               VARCHAR,
  longitud              VARCHAR,
  comentario            VARCHAR,
  fecha_hora_inicio     TIMESTAMP,
  fecha_hora_fin        TIMESTAMP,
  CONSTRAINT pk_visita PRIMARY KEY (id),
  CONSTRAINT fk_solicitud FOREIGN KEY (id_solicitud) REFERENCES SOLICITUD (id)
);

CREATE UNIQUE INDEX visita_idx1 ON
  VISITA (id_solicitud);
-----------------------------------------------ELIMINACION DE CAMPOS TABLA SOLICITUD -----------------------------------------------------------------------------

-- Eliminar la columna "fecha_hora_inicio" de la tabla "SOLICITUD"
ALTER TABLE SOLICITUD DROP COLUMN fecha_hora_inicio;

-- Eliminar la columna "fecha_hora_fin" de la tabla "SOLICITUD"
ALTER TABLE SOLICITUD DROP COLUMN fecha_hora_fin;

-- Eliminar la columna "comentarios_tecnico" de la tabla "SOLICITUD"
ALTER TABLE SOLICITUD DROP COLUMN comentarios_tecnico;