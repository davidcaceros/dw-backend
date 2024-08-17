------------------------------------------------------------- AGREGAR CAMPO VISITA_ID -----------------------------------------------------------------------------
ALTER TABLE SOLICITUD
ADD visita_id BIGINT DEFAULT NULL;

-- Agregar la restricción de clave foránea (foreign key)
ALTER TABLE SOLICITUD
ADD CONSTRAINT fk_visita
FOREIGN KEY (visita_id)
REFERENCES VISITA(id);