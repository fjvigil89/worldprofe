TRUNCATE TABLE worldprofe.reserva CASCADE;
TRUNCATE TABLE worldprofe.clase_libre CASCADE;
TRUNCATE TABLE worldprofe.email_reserva CASCADE;
UPDATE comun.profesor SET clases_impartidas = 0;
UPDATE comun.alumno SET creditos_totales = 0, creditos_disponibles = 0, creditos_consumidos = 0;
