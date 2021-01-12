-- script creacion - postgres --

-- Eliminamos y creamos la BD
-- DROP DATABASE IF EXISTS sife1701;
-- CREATE DATABASE sife1701 WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'Spanish_Spain.1252' LC_CTYPE = 'Spanish_Spain.1252';
--

CREATE SCHEMA IF NOT EXISTS comun;
CREATE SCHEMA IF NOT EXISTS worldprofe;

CREATE EXTENSION IF NOT EXISTS unaccent;

---------- COMUN ----------

-- Usuario --
DROP TABLE IF EXISTS comun.usuario CASCADE;
CREATE TABLE comun.usuario (
    id                  BIGSERIAL    NOT NULL UNIQUE,
    rol                 VARCHAR(40)  NOT NULL,
    login               VARCHAR(100) UNIQUE,
    email               VARCHAR(100) NOT NULL UNIQUE,
    password            VARCHAR(255) NOT NULL,
    foto                TEXT,
    validado            BOOLEAN      NOT NULL DEFAULT FALSE,
    activado            BOOLEAN      NOT NULL DEFAULT FALSE,
    eliminado           BOOLEAN      NOT NULL DEFAULT FALSE,
    fecha_registro      TIMESTAMP    NOT NULL,
    fecha_ultimo_acceso TIMESTAMP,
    accesos             BIGINT       DEFAULT 0,
    clave_activacion    VARCHAR(20),
    clave_reset         VARCHAR(20),
    fecha_reset         TIMESTAMP,
    version             INTEGER      NOT NULL DEFAULT 0,
    CONSTRAINT usuario_pk PRIMARY KEY (id)
);

-- UsuarioSocial --
DROP TABLE IF EXISTS comun.usuario_social CASCADE;
CREATE TABLE comun.usuario_social (
    id               BIGSERIAL,
    user_id          TEXT         NOT NULL,
    provider_id      TEXT         NOT NULL,
    provider_user_id TEXT         NOT NULL,
    rank             BIGINT       NOT NULL,
    display_name     VARCHAR(255),
    profile_url      VARCHAR(255),
    image_url        VARCHAR(255),
    access_token     VARCHAR(255) NOT NULL,
    secret           VARCHAR(255),
    refresh_token    VARCHAR(255),
    expire_time      BIGINT,
    CONSTRAINT usuario_social_pk PRIMARY KEY (id)
);

-- DatosFacturacion --
DROP TABLE IF EXISTS comun.datos_facturacion CASCADE;
CREATE TABLE comun.datos_facturacion (
	id             BIGSERIAL,
	nombre         VARCHAR(255),
	cif            VARCHAR(20),
	nif            VARCHAR(20),
	provincia      VARCHAR(50),
	localidad      VARCHAR(50),
	cp             VARCHAR(10),
	direccion      VARCHAR(500),
	CONSTRAINT datos_facturacion_pk PRIMARY KEY (id)
);

-- Pais --
DROP TABLE IF EXISTS comun.pais CASCADE;
CREATE TABLE comun.pais (
    id     BIGSERIAL,
    nombre TEXT,
    codigo VARCHAR(2),
    CONSTRAINT pais_pk PRIMARY KEY (id)
);

-- Empresa --
DROP TABLE IF EXISTS comun.empresa CASCADE;
CREATE TABLE comun.empresa (
    id        			  BIGSERIAL REFERENCES comun.usuario ON DELETE CASCADE,
    creditos_totales  	  INT DEFAULT 0,
    creditos_disponibles  INT DEFAULT 0,
    creditos_distribuidos INT DEFAULT 0,
    caducidad 			  TIMESTAMP,
    datos_facturacion_id  BIGINT,
    CONSTRAINT empresa_pk PRIMARY KEY (id),
    CONSTRAINT empresa_fk_datosfacturacion FOREIGN KEY (datos_facturacion_id) REFERENCES comun.datos_facturacion (id) ON DELETE SET NULL
);

-- Alumno --
DROP TABLE IF EXISTS comun.alumno CASCADE;
CREATE TABLE comun.alumno (
    id                    BIGSERIAL REFERENCES comun.usuario ON DELETE CASCADE,
    nombre                TEXT,
    apellidos             TEXT,
    skype                 VARCHAR(100) UNIQUE,
    creditos_totales  	  INT DEFAULT 0,
    creditos_disponibles  INT DEFAULT 0,
    creditos_consumidos   INT DEFAULT 0,
    empresa_id            BIGINT,
    CONSTRAINT alumno_pk PRIMARY KEY (id),
    CONSTRAINT alumno_fk_empresa FOREIGN KEY (empresa_id) REFERENCES comun.empresa (id) ON DELETE SET NULL
);

-- Profesor --
DROP TABLE IF EXISTS comun.profesor CASCADE;
CREATE TABLE comun.profesor (
    id                   BIGSERIAL REFERENCES comun.usuario ON DELETE CASCADE,
    nombre               TEXT,
    apellidos            TEXT,
    skype                VARCHAR(100) UNIQUE,
    texto_presentacion   TEXT,
    fecha_nacimiento     TIMESTAMP,
    disponibilidad       VARCHAR(15),
    datos_facturacion_id BIGINT,
    pais_id              BIGINT,
    CONSTRAINT profesor_pk PRIMARY KEY (id),
    CONSTRAINT profesor_fk_datosfacturacion FOREIGN KEY (datos_facturacion_id) REFERENCES comun.datos_facturacion (id) ON DELETE SET NULL,
    CONSTRAINT profesor_fk_pais FOREIGN KEY (pais_id) REFERENCES comun.pais (id) ON DELETE SET NULL
);

-- Idioma --
DROP TABLE IF EXISTS comun.idioma CASCADE;
CREATE TABLE comun.idioma (
    id      BIGSERIAL,
    nombre  TEXT       NOT NULL UNIQUE,
    CONSTRAINT idioma_pk PRIMARY KEY (id)
);

-- Nivel --
DROP TABLE IF EXISTS comun.nivel CASCADE;
CREATE TABLE comun.nivel (
    id      BIGSERIAL,
    nombre  TEXT       NOT NULL UNIQUE,
    CONSTRAINT nivel_pk PRIMARY KEY (id)
);

---------- WORLDPROFE ----------

-- Gestión de páginas estáticas --

DROP TABLE IF EXISTS worldprofe.estatica CASCADE;
CREATE TABLE worldprofe.estatica (
    id            BIGSERIAL,
    identificador TEXT UNIQUE,
    CONSTRAINT estatica_pk PRIMARY KEY (id)
);

DROP TABLE IF EXISTS worldprofe.estatica_i18n CASCADE;
CREATE TABLE worldprofe.estatica_i18n (
    id            BIGSERIAL,
    titulo        VARCHAR(100),
    contenido     TEXT,
    idioma_codigo VARCHAR(20),
    estatica_id   BIGINT,
    CONSTRAINT estatica_i18n_pk PRIMARY KEY (id),
    CONSTRAINT estatica_i18n_fk_estatica FOREIGN KEY (estatica_id) REFERENCES worldprofe.estatica (id) ON DELETE CASCADE
);

-- Configuracion --
DROP TABLE IF EXISTS worldprofe.configuracion CASCADE;
CREATE TABLE worldprofe.configuracion (
    id                                  BIGSERIAL,
    iva                                 BIGINT,
    tiempo_maximo_respuesta             BIGINT,
    tiempo_antes_inicio_cancelar        BIGINT,
    tiempo_maximo_actualizar_incidencia BIGINT,
    CONSTRAINT configuracion_pk PRIMARY KEY (id)
);

-- ClaseLibre --
DROP TABLE IF EXISTS worldprofe.clase_libre CASCADE;
CREATE TABLE worldprofe.clase_libre (
    id          BIGSERIAL,
    fecha       TIMESTAMP WITH TIME ZONE,
    ocupada     BOOLEAN DEFAULT FALSE,
    profesor_id BIGINT,
    version BIGINT DEFAULT 0,
    CONSTRAINT clase_libre_pk PRIMARY KEY (id),
    CONSTRAINT clase_libre_fk_profesor FOREIGN KEY (profesor_id) REFERENCES comun.profesor(id) ON DELETE SET NULL
);

-- Reserva --
DROP TABLE IF EXISTS worldprofe.reserva CASCADE;
CREATE TABLE worldprofe.reserva (
	id 				    BIGSERIAL,
	estado			    VARCHAR(50),
	motivo_profesor     TEXT,
	motivo_alumno	    TEXT,
	revisada            BOOLEAN,
	fecha               TIMESTAMP WITH TIME ZONE,
	alumno_id		    BIGINT,
	profesor_id         BIGINT,
	claselibre_id       BIGINT,
	version        	    BIGINT DEFAULT 0,
	CONSTRAINT reserva_pk PRIMARY KEY (id),
	CONSTRAINT reserva_fk_alumno FOREIGN KEY (alumno_id) REFERENCES comun.alumno (id) ON DELETE SET NULL,
	CONSTRAINT reserva_fk_profesor FOREIGN KEY (profesor_id) REFERENCES comun.profesor (id) ON DELETE SET NULL,
	CONSTRAINT reserva_fk_claselibre FOREIGN KEY (claselibre_id) REFERENCES worldprofe.clase_libre (id) ON DELETE SET NULL
);

-- ProfesorIdioma --
DROP TABLE IF EXISTS worldprofe.profesor_idioma CASCADE;
CREATE TABLE worldprofe.profesor_idioma (
    id          BIGSERIAL,
	profesor_id BIGINT NOT NULL,
	idioma_id	BIGINT NOT NULL,
	nivel_id    BIGINT NOT NULL,
	CONSTRAINT profesor_idioma_pk PRIMARY KEY (id),
    CONSTRAINT profesor_idioma_fk_profesor FOREIGN KEY (profesor_id) REFERENCES comun.profesor (id) ON DELETE CASCADE,
    CONSTRAINT profesor_idioma_fk_idioma FOREIGN KEY (idioma_id) REFERENCES comun.idioma (id) ON DELETE CASCADE,
    CONSTRAINT profesor_idioma_fk_nivel FOREIGN KEY (nivel_id) REFERENCES comun.nivel (id) ON DELETE CASCADE
);

-- Capacidad --
DROP TABLE IF EXISTS worldprofe.capacidad CASCADE;
CREATE TABLE worldprofe.capacidad (
	id 			BIGSERIAL,
	nombre		VARCHAR(100),
	CONSTRAINT capacidad_pk PRIMARY KEY (id)
);

-- ProfesorCapacidad --
DROP TABLE IF EXISTS worldprofe.profesor_capacidad CASCADE;
CREATE TABLE worldprofe.profesor_capacidad (
	profesor_id  BIGINT NOT NULL,
	capacidad_id BIGINT NOT NULL,
	CONSTRAINT profesor_capacidad_pk PRIMARY KEY (profesor_id, capacidad_id),
    CONSTRAINT profesor_capacidad_fk_profesor FOREIGN KEY (profesor_id) REFERENCES comun.profesor (id) ON DELETE CASCADE,
    CONSTRAINT profesor_capacidad_fk_capacidad FOREIGN KEY (capacidad_id) REFERENCES worldprofe.capacidad (id) ON DELETE CASCADE
);

-- PaqueteCreditos --
DROP TABLE IF EXISTS worldprofe.paquete_creditos CASCADE;
CREATE TABLE worldprofe.paquete_creditos (
	id 		 BIGSERIAL,
	creditos BIGINT,
	coste    NUMERIC(15, 10),
	CONSTRAINT paquete_creditos_pk PRIMARY KEY (id)
);

-- AlumnoPaquetecreditos --
DROP TABLE IF EXISTS worldprofe.alumno_paquetecreditos CASCADE;
CREATE TABLE worldprofe.alumno_paquetecreditos (
	alumno_id 		    BIGINT NOT NULL,
	paquete_creditos_id BIGINT NOT NULL,
	CONSTRAINT alumno_paquetecreditos_pk PRIMARY KEY (alumno_id, paquete_creditos_id),
	CONSTRAINT alumno_paquetecreditos_fk_alumno FOREIGN KEY (alumno_id) REFERENCES comun.alumno (id) ON DELETE CASCADE,
	CONSTRAINT alumno_paquetecreditos_fk_paquetecreditos FOREIGN KEY (paquete_creditos_id) REFERENCES worldprofe.paquete_creditos (id) ON DELETE CASCADE
);

-- Compra --
DROP TABLE IF EXISTS worldprofe.compra CASCADE;
CREATE TABLE worldprofe.compra (
    id                  BIGSERIAL,
    fecha               TIMESTAMP,
    creditos            BIGINT,
    precio              NUMERIC(15,10),
    precio_con_iva      NUMERIC(15,10),
    porcentaje_iva      BIGINT,
    paquete_creditos_id BIGINT,
    paypal_payment_id   VARCHAR(255),
    realizada           BOOLEAN DEFAULT FALSE,
    usuario_id          BIGINT NOT NULL,
    CONSTRAINT compra_pk PRIMARY KEY (id),
    CONSTRAINT compra_fk_paquete_creditos FOREIGN KEY (paquete_creditos_id) REFERENCES worldprofe.paquete_creditos (id) ON DELETE SET NULL,
    CONSTRAINT compra_fk_usuario FOREIGN KEY (usuario_id) REFERENCES comun.usuario (id) ON DELETE SET NULL
);

-- Emails sin enviar de reservas --
DROP TABLE IF EXISTS worldprofe.email_reserva CASCADE;
CREATE TABLE worldprofe.email_reserva (
    id	       BIGSERIAL,
    fecha	   TIMESTAMP WITH TIME ZONE,
    url_base   VARCHAR(255),
    locale	   VARCHAR(10),
    reserva_id BIGINT NOT NULL,
    CONSTRAINT email_reserva_pk PRIMARY KEY (id),
    CONSTRAINT email_reserva_fk_reserva FOREIGN KEY (reserva_id) REFERENCES worldprofe.reserva (id) ON DELETE CASCADE
);
