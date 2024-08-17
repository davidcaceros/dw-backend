------------------------------------------------------------- AGREGAR CAMPO VISITA_ID -----------------------------------------------------------------------------
ALTER TABLE SOLICITUD
ADD id_supervisor BIGINT DEFAULT NULL;

-- Agregar la restricción de clave foránea (foreign key)
ALTER TABLE SOLICITUD
ADD CONSTRAINT fk_supervisor
FOREIGN KEY (id_supervisor)
REFERENCES USUARIO(id);