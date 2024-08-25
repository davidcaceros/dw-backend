------------------------------------------------------------- rol -----------------------------------------------------------------------------
CREATE SEQUENCE "seq_rol" START 1;

CREATE TABLE "rol" (
    "id_rol" INT DEFAULT NEXTVAL('"seq_rol"') PRIMARY KEY,
    "nombre" VARCHAR(25),
    "slug" VARCHAR(25),
    "fecha_creacion" TIMESTAMP
);
------------------------------------------------------------- persona -----------------------------------------------------------------------------
CREATE SEQUENCE "seq_persona" START 100;

CREATE TABLE "persona" (
    "id_persona" INT DEFAULT NEXTVAL('"seq_persona"') PRIMARY KEY,
    "primer_nombre" VARCHAR(25) NOT NULL,
    "segundo_nombre" VARCHAR(25),
    "primer_apellido" VARCHAR(25) NOT NULL,
    "segundo_apellido" VARCHAR(25),
    "apellido_casada" VARCHAR(25),
    "dpi" VARCHAR(14) NOT NULL,
    "nit" VARCHAR(25),
    "pasaporte" VARCHAR(20),
    "telefono" VARCHAR(9) NOT NULL,
    "correo" VARCHAR(50) NOT NULL UNIQUE,
    "direccion" VARCHAR(100) NOT NULL,
    "estado" VARCHAR(32) NOT NULL,
    "id_rol" INT,
    "categoria" VARCHAR(20),
    "fecha_creacion" TIMESTAMP,
    "fecha_actualizacion" TIMESTAMP,
    CONSTRAINT "rol_persona_fk" FOREIGN KEY ("id_rol") REFERENCES "rol" ("id_rol")
);
------------------------------------------------------------- usuario -----------------------------------------------------------------------------
CREATE SEQUENCE "seq_usuario" START 100;

CREATE TABLE "usuario" (
    "id_usuario" INT DEFAULT NEXTVAL('"seq_usuario"') PRIMARY KEY,
    "id_persona" INT,
    "contrasena" VARCHAR(255),
    "activo" BOOLEAN,
    "fecha_creacion" TIMESTAMP,
    "fecha_actualizacion" TIMESTAMP,
    CONSTRAINT "persona_usuario_fk" FOREIGN KEY ("id_persona") REFERENCES "persona" ("id_persona")
);
