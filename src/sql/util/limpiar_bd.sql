TRUNCATE TABLE worldprofe.compra CASCADE;
TRUNCATE TABLE worldprofe.reserva CASCADE;
TRUNCATE TABLE worldprofe.clase_libre CASCADE;
TRUNCATE TABLE worldprofe.email_reserva CASCADE;

-- Usuarios
UPDATE comun.profesor
SET clases_impartidas = 0;
UPDATE comun.alumno
SET creditos_totales     = 0,
    creditos_disponibles = 0,
    creditos_consumidos  = 0;
UPDATE comun.empresa
SET creditos_totales      = 0,
    creditos_disponibles  = 0,
    creditos_distribuidos = 0;

DELETE
FROM comun.usuario
WHERE id not in (1, 31, 33, 34);
