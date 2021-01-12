-- 10/01/2019
-- (Carlos)
-- Añadimos la fecha en la que se solicitó la reserva
ALTER TABLE worldprofe.reserva
    ADD COLUMN fecha_solicitud TIMESTAMP DEFAULT now();
