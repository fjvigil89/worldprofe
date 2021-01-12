
-- 16/11/2018
-- (Carlos)

-- Añadimos el campo nombre a la tabla de usuario y lo quitamos de las subentidades
ALTER TABLE comun.usuario ADD COLUMN nombre VARCHAR(255);
UPDATE comun.usuario SET nombre = p.nombre FROM (select p.id, p.nombre from comun.profesor p) as p WHERE comun.usuario.id = p.id;
UPDATE comun.usuario SET nombre = a.nombre FROM (select a.id, a.nombre from comun.alumno a) as a WHERE comun.usuario.id = a.id;
ALTER TABLE comun.profesor DROP COLUMN nombre;
ALTER TABLE comun.alumno DROP COLUMN nombre;

-- Añadimos teléfono móvil y observaciones
ALTER TABLE comun.usuario ADD COLUMN telefono_movil VARCHAR(20);
ALTER TABLE comun.usuario ADD COLUMN observaciones TEXT;

-- Añadimos campo de descripción de capacidades al profesor
ALTER TABLE comun.profesor ADD COLUMN descripcion_capacidades TEXT;

-- Limpiamos los datos de columnas y tablas que no se van a utilizar por el momento (capacidades, zona horaria)
TRUNCATE TABLE worldprofe.profesor_capacidad;
UPDATE comun.profesor SET zona_horaria_id = null;

-- 20/11/2018

-- Movemos la clave foránea a datos de facturación a usuario
ALTER TABLE comun.usuario
    ADD COLUMN datos_facturacion_id BIGINT,
    ADD CONSTRAINT usuario_fk_datosfacturacion FOREIGN KEY (datos_facturacion_id) REFERENCES comun.datos_facturacion (id) ON DELETE SET NULL;
UPDATE comun.usuario
SET datos_facturacion_id = p.datos_facturacion_id
FROM (select p.id, p.datos_facturacion_id from comun.profesor p) as p
WHERE comun.usuario.id = p.id;
UPDATE comun.usuario
SET datos_facturacion_id = e.datos_facturacion_id
FROM (select e.id, e.datos_facturacion_id from comun.empresa e) as e
WHERE comun.usuario.id = e.id;
ALTER TABLE comun.profesor
    DROP COLUMN datos_facturacion_id;
ALTER TABLE comun.empresa
    DROP COLUMN datos_facturacion_id;

-- 21/11/2018

-- Añadimos campo para la solicitud de una factura
ALTER TABLE worldprofe.compra ADD COLUMN solicitar_factura BOOLEAN DEFAULT FALSE;

-- Columna de configuración de correo de administrador para notificaciones
ALTER TABLE worldprofe.configuracion ADD correo_notificaciones TEXT DEFAULT '' NOT NULL;

-- Eliminamos columna que no se estaba utilizando
ALTER TABLE comun.datos_facturacion DROP COLUMN cif;
