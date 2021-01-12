-- 21/12/2018
-- (Carlos)
-- Añadimos información para saber que usuarios se han dado de baja
ALTER TABLE comun.usuario
    ADD COLUMN baja BOOLEAN DEFAULT FALSE;
