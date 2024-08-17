------------------------------------------------------------- ROL -----------------------------------------------------------------------------
CREATE TABLE ROL (
  id            SERIAL          NOT NULL,
  nombre        VARCHAR,
  slug          VARCHAR,
  descripcion   VARCHAR,
  CONSTRAINT pk_rol PRIMARY KEY (id)
);

------------------------------------------------------------- USUARIO -----------------------------------------------------------------------------

CREATE TABLE USUARIO (
  id                    SERIAL      NOT NULL,
  id_rol                BIGINT,
  nombre                VARCHAR,
  apellido              VARCHAR,
  email                 VARCHAR,
  dpi                   VARCHAR(13),
  telefono              VARCHAR(8),
  contraseña            VARCHAR,
  fecha_creacion        TIMESTAMP,
  fecha_actualizacion   TIMESTAMP,
  enabled               BOOLEAN     NOT NULL,
  CONSTRAINT pk_usuario PRIMARY KEY (id),
  CONSTRAINT fk_rol FOREIGN KEY (id_rol) REFERENCES ROL (id)
);

CREATE UNIQUE INDEX usuario_idx1 ON
  USUARIO (dpi);
------------------------------------------------------------- SUPERVISOR_TECNICO -----------------------------------------------------------------------------

CREATE TABLE SUPERVISOR_TECNICO (
  id            SERIAL  NOT NULL,
  id_supervisor BIGINT,
  id_tecnico    BIGINT,
  CONSTRAINT pk_supervisor_tecnico PRIMARY KEY (id),
  CONSTRAINT fk_supervisor FOREIGN KEY (id_supervisor) REFERENCES USUARIO (id),
  CONSTRAINT fk_tecnico FOREIGN KEY (id_tecnico) REFERENCES USUARIO (id)
);

------------------------------------------------------------- SOLICITUD -----------------------------------------------------------------------------

CREATE TABLE SOLICITUD (
  id                    SERIAL      NOT NULL,
  id_cliente            BIGINT,
  tipo_servicio         VARCHAR,
  descripcion           VARCHAR(128),
  direccion             VARCHAR,
  latitud               VARCHAR,
  longitud              VARCHAR,
  id_tecnico            BIGINT,
  fecha_visita          TIMESTAMP,
  fecha_hora_inicio     TIMESTAMP,
  fecha_hora_fin        TIMESTAMP,
  comentarios_tecnico   VARCHAR,
  estatus               VARCHAR,
  fecha_creacion        TIMESTAMP,
  fecha_actualizacion   TIMESTAMP,
  enabled               BOOLEAN       NOT NULL,
  CONSTRAINT pk_solicitud PRIMARY KEY (id),
  CONSTRAINT fk_cliente FOREIGN KEY (id_cliente) REFERENCES USUARIO (id),
  CONSTRAINT fk_tecnico FOREIGN KEY (id_tecnico) REFERENCES USUARIO (id)
);

