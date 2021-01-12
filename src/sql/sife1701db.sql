--
-- PostgreSQL database dump
--

-- Dumped from database version 10.14 (Ubuntu 10.14-0ubuntu0.18.04.1)
-- Dumped by pg_dump version 10.14 (Ubuntu 10.14-0ubuntu0.18.04.1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: comun; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA comun;


ALTER SCHEMA comun OWNER TO postgres;

--
-- Name: worldprofe; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA worldprofe;


ALTER SCHEMA worldprofe OWNER TO postgres;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


--
-- Name: unaccent; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS unaccent WITH SCHEMA public;


--
-- Name: EXTENSION unaccent; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION unaccent IS 'text search dictionary that removes accents';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: alumno; Type: TABLE; Schema: comun; Owner: postgres
--

CREATE TABLE comun.alumno (
    id bigint NOT NULL,
    apellidos text,
    skype character varying(100),
    creditos_totales integer DEFAULT 0,
    creditos_disponibles integer DEFAULT 0,
    creditos_consumidos integer DEFAULT 0,
    empresa_id bigint
);


ALTER TABLE comun.alumno OWNER TO postgres;

--
-- Name: alumno_id_seq; Type: SEQUENCE; Schema: comun; Owner: postgres
--

CREATE SEQUENCE comun.alumno_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE comun.alumno_id_seq OWNER TO postgres;

--
-- Name: alumno_id_seq; Type: SEQUENCE OWNED BY; Schema: comun; Owner: postgres
--

ALTER SEQUENCE comun.alumno_id_seq OWNED BY comun.alumno.id;


--
-- Name: datos_facturacion; Type: TABLE; Schema: comun; Owner: postgres
--

CREATE TABLE comun.datos_facturacion (
    id bigint NOT NULL,
    nombre character varying(255),
    nif character varying(20),
    provincia character varying(50),
    localidad character varying(50),
    cp character varying(10),
    direccion character varying(500)
);


ALTER TABLE comun.datos_facturacion OWNER TO postgres;

--
-- Name: datos_facturacion_id_seq; Type: SEQUENCE; Schema: comun; Owner: postgres
--

CREATE SEQUENCE comun.datos_facturacion_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE comun.datos_facturacion_id_seq OWNER TO postgres;

--
-- Name: datos_facturacion_id_seq; Type: SEQUENCE OWNED BY; Schema: comun; Owner: postgres
--

ALTER SEQUENCE comun.datos_facturacion_id_seq OWNED BY comun.datos_facturacion.id;


--
-- Name: empresa; Type: TABLE; Schema: comun; Owner: postgres
--

CREATE TABLE comun.empresa (
    id bigint NOT NULL,
    creditos_totales integer DEFAULT 0,
    creditos_disponibles integer DEFAULT 0,
    creditos_distribuidos integer DEFAULT 0,
    caducidad timestamp without time zone
);


ALTER TABLE comun.empresa OWNER TO postgres;

--
-- Name: empresa_id_seq; Type: SEQUENCE; Schema: comun; Owner: postgres
--

CREATE SEQUENCE comun.empresa_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE comun.empresa_id_seq OWNER TO postgres;

--
-- Name: empresa_id_seq; Type: SEQUENCE OWNED BY; Schema: comun; Owner: postgres
--

ALTER SEQUENCE comun.empresa_id_seq OWNED BY comun.empresa.id;


--
-- Name: idioma; Type: TABLE; Schema: comun; Owner: postgres
--

CREATE TABLE comun.idioma (
    id bigint NOT NULL,
    nombre text NOT NULL
);


ALTER TABLE comun.idioma OWNER TO postgres;

--
-- Name: idioma_id_seq; Type: SEQUENCE; Schema: comun; Owner: postgres
--

CREATE SEQUENCE comun.idioma_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE comun.idioma_id_seq OWNER TO postgres;

--
-- Name: idioma_id_seq; Type: SEQUENCE OWNED BY; Schema: comun; Owner: postgres
--

ALTER SEQUENCE comun.idioma_id_seq OWNED BY comun.idioma.id;


--
-- Name: nivel; Type: TABLE; Schema: comun; Owner: postgres
--

CREATE TABLE comun.nivel (
    id bigint NOT NULL,
    nombre text NOT NULL
);


ALTER TABLE comun.nivel OWNER TO postgres;

--
-- Name: nivel_id_seq; Type: SEQUENCE; Schema: comun; Owner: postgres
--

CREATE SEQUENCE comun.nivel_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE comun.nivel_id_seq OWNER TO postgres;

--
-- Name: nivel_id_seq; Type: SEQUENCE OWNED BY; Schema: comun; Owner: postgres
--

ALTER SEQUENCE comun.nivel_id_seq OWNED BY comun.nivel.id;


--
-- Name: pais; Type: TABLE; Schema: comun; Owner: postgres
--

CREATE TABLE comun.pais (
    id bigint NOT NULL,
    nombre text,
    codigo character varying(2)
);


ALTER TABLE comun.pais OWNER TO postgres;

--
-- Name: pais_id_seq; Type: SEQUENCE; Schema: comun; Owner: postgres
--

CREATE SEQUENCE comun.pais_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE comun.pais_id_seq OWNER TO postgres;

--
-- Name: pais_id_seq; Type: SEQUENCE OWNED BY; Schema: comun; Owner: postgres
--

ALTER SEQUENCE comun.pais_id_seq OWNED BY comun.pais.id;


--
-- Name: profesor; Type: TABLE; Schema: comun; Owner: postgres
--

CREATE TABLE comun.profesor (
    id bigint NOT NULL,
    apellidos text,
    skype character varying(100),
    texto_presentacion text,
    fecha_nacimiento timestamp without time zone,
    disponibilidad character varying(15),
    pais_id bigint,
    zona_horaria_id bigint,
    clases_impartidas bigint DEFAULT 0,
    descripcion_capacidades text
);


ALTER TABLE comun.profesor OWNER TO postgres;

--
-- Name: profesor_id_seq; Type: SEQUENCE; Schema: comun; Owner: postgres
--

CREATE SEQUENCE comun.profesor_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE comun.profesor_id_seq OWNER TO postgres;

--
-- Name: profesor_id_seq; Type: SEQUENCE OWNED BY; Schema: comun; Owner: postgres
--

ALTER SEQUENCE comun.profesor_id_seq OWNED BY comun.profesor.id;


--
-- Name: usuario; Type: TABLE; Schema: comun; Owner: postgres
--

CREATE TABLE comun.usuario (
    id bigint NOT NULL,
    rol character varying(40) NOT NULL,
    login character varying(100),
    email character varying(100) NOT NULL,
    password character varying(255) NOT NULL,
    foto text,
    validado boolean DEFAULT false NOT NULL,
    activado boolean DEFAULT false NOT NULL,
    eliminado boolean DEFAULT false NOT NULL,
    fecha_registro timestamp without time zone NOT NULL,
    fecha_ultimo_acceso timestamp without time zone,
    accesos bigint DEFAULT 0,
    clave_activacion character varying(20),
    clave_reset character varying(20),
    fecha_reset timestamp without time zone,
    version integer DEFAULT 0 NOT NULL,
    nombre character varying(255),
    telefono_movil character varying(20),
    observaciones text,
    datos_facturacion_id bigint,
    baja boolean DEFAULT false
);


ALTER TABLE comun.usuario OWNER TO postgres;

--
-- Name: usuario_id_seq; Type: SEQUENCE; Schema: comun; Owner: postgres
--

CREATE SEQUENCE comun.usuario_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE comun.usuario_id_seq OWNER TO postgres;

--
-- Name: usuario_id_seq; Type: SEQUENCE OWNED BY; Schema: comun; Owner: postgres
--

ALTER SEQUENCE comun.usuario_id_seq OWNED BY comun.usuario.id;


--
-- Name: usuario_social; Type: TABLE; Schema: comun; Owner: postgres
--

CREATE TABLE comun.usuario_social (
    id bigint NOT NULL,
    user_id text NOT NULL,
    provider_id text NOT NULL,
    provider_user_id text NOT NULL,
    rank bigint NOT NULL,
    display_name character varying(255),
    profile_url character varying(255),
    image_url character varying(255),
    access_token character varying(255) NOT NULL,
    secret character varying(255),
    refresh_token character varying(255),
    expire_time bigint
);


ALTER TABLE comun.usuario_social OWNER TO postgres;

--
-- Name: usuario_social_id_seq; Type: SEQUENCE; Schema: comun; Owner: postgres
--

CREATE SEQUENCE comun.usuario_social_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE comun.usuario_social_id_seq OWNER TO postgres;

--
-- Name: usuario_social_id_seq; Type: SEQUENCE OWNED BY; Schema: comun; Owner: postgres
--

ALTER SEQUENCE comun.usuario_social_id_seq OWNED BY comun.usuario_social.id;


--
-- Name: alumno_paquetecreditos; Type: TABLE; Schema: worldprofe; Owner: postgres
--

CREATE TABLE worldprofe.alumno_paquetecreditos (
    alumno_id bigint NOT NULL,
    paquete_creditos_id bigint NOT NULL
);


ALTER TABLE worldprofe.alumno_paquetecreditos OWNER TO postgres;

--
-- Name: capacidad; Type: TABLE; Schema: worldprofe; Owner: postgres
--

CREATE TABLE worldprofe.capacidad (
    id bigint NOT NULL,
    nombre character varying(100)
);


ALTER TABLE worldprofe.capacidad OWNER TO postgres;

--
-- Name: capacidad_id_seq; Type: SEQUENCE; Schema: worldprofe; Owner: postgres
--

CREATE SEQUENCE worldprofe.capacidad_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE worldprofe.capacidad_id_seq OWNER TO postgres;

--
-- Name: capacidad_id_seq; Type: SEQUENCE OWNED BY; Schema: worldprofe; Owner: postgres
--

ALTER SEQUENCE worldprofe.capacidad_id_seq OWNED BY worldprofe.capacidad.id;


--
-- Name: clase_libre; Type: TABLE; Schema: worldprofe; Owner: postgres
--

CREATE TABLE worldprofe.clase_libre (
    id bigint NOT NULL,
    fecha timestamp with time zone,
    ocupada boolean DEFAULT false,
    profesor_id bigint,
    version bigint DEFAULT 0
);


ALTER TABLE worldprofe.clase_libre OWNER TO postgres;

--
-- Name: clase_libre_id_seq; Type: SEQUENCE; Schema: worldprofe; Owner: postgres
--

CREATE SEQUENCE worldprofe.clase_libre_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE worldprofe.clase_libre_id_seq OWNER TO postgres;

--
-- Name: clase_libre_id_seq; Type: SEQUENCE OWNED BY; Schema: worldprofe; Owner: postgres
--

ALTER SEQUENCE worldprofe.clase_libre_id_seq OWNED BY worldprofe.clase_libre.id;


--
-- Name: compra; Type: TABLE; Schema: worldprofe; Owner: postgres
--

CREATE TABLE worldprofe.compra (
    id bigint NOT NULL,
    fecha timestamp without time zone,
    creditos bigint,
    precio numeric(15,10),
    precio_con_iva numeric(15,10),
    porcentaje_iva bigint,
    paquete_creditos_id bigint,
    paypal_payment_id character varying(255),
    realizada boolean DEFAULT false,
    usuario_id bigint NOT NULL,
    solicitar_factura boolean DEFAULT false
);


ALTER TABLE worldprofe.compra OWNER TO postgres;

--
-- Name: compra_id_seq; Type: SEQUENCE; Schema: worldprofe; Owner: postgres
--

CREATE SEQUENCE worldprofe.compra_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE worldprofe.compra_id_seq OWNER TO postgres;

--
-- Name: compra_id_seq; Type: SEQUENCE OWNED BY; Schema: worldprofe; Owner: postgres
--

ALTER SEQUENCE worldprofe.compra_id_seq OWNED BY worldprofe.compra.id;


--
-- Name: configuracion; Type: TABLE; Schema: worldprofe; Owner: postgres
--

CREATE TABLE worldprofe.configuracion (
    id bigint NOT NULL,
    iva bigint,
    tiempo_maximo_respuesta bigint,
    tiempo_antes_inicio_cancelar bigint,
    tiempo_maximo_actualizar_incidencia bigint,
    correo_notificaciones text DEFAULT ''::text NOT NULL
);


ALTER TABLE worldprofe.configuracion OWNER TO postgres;

--
-- Name: configuracion_id_seq; Type: SEQUENCE; Schema: worldprofe; Owner: postgres
--

CREATE SEQUENCE worldprofe.configuracion_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE worldprofe.configuracion_id_seq OWNER TO postgres;

--
-- Name: configuracion_id_seq; Type: SEQUENCE OWNED BY; Schema: worldprofe; Owner: postgres
--

ALTER SEQUENCE worldprofe.configuracion_id_seq OWNED BY worldprofe.configuracion.id;


--
-- Name: email_reserva; Type: TABLE; Schema: worldprofe; Owner: postgres
--

CREATE TABLE worldprofe.email_reserva (
    id bigint NOT NULL,
    fecha timestamp with time zone,
    url_base character varying(255),
    locale character varying(10),
    reserva_id bigint NOT NULL
);


ALTER TABLE worldprofe.email_reserva OWNER TO postgres;

--
-- Name: email_reserva_id_seq; Type: SEQUENCE; Schema: worldprofe; Owner: postgres
--

CREATE SEQUENCE worldprofe.email_reserva_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE worldprofe.email_reserva_id_seq OWNER TO postgres;

--
-- Name: email_reserva_id_seq; Type: SEQUENCE OWNED BY; Schema: worldprofe; Owner: postgres
--

ALTER SEQUENCE worldprofe.email_reserva_id_seq OWNED BY worldprofe.email_reserva.id;


--
-- Name: estatica; Type: TABLE; Schema: worldprofe; Owner: postgres
--

CREATE TABLE worldprofe.estatica (
    id bigint NOT NULL,
    identificador text
);


ALTER TABLE worldprofe.estatica OWNER TO postgres;

--
-- Name: estatica_i18n; Type: TABLE; Schema: worldprofe; Owner: postgres
--

CREATE TABLE worldprofe.estatica_i18n (
    id bigint NOT NULL,
    titulo character varying(100),
    contenido text,
    idioma_codigo character varying(20),
    estatica_id bigint
);


ALTER TABLE worldprofe.estatica_i18n OWNER TO postgres;

--
-- Name: estatica_i18n_id_seq; Type: SEQUENCE; Schema: worldprofe; Owner: postgres
--

CREATE SEQUENCE worldprofe.estatica_i18n_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE worldprofe.estatica_i18n_id_seq OWNER TO postgres;

--
-- Name: estatica_i18n_id_seq; Type: SEQUENCE OWNED BY; Schema: worldprofe; Owner: postgres
--

ALTER SEQUENCE worldprofe.estatica_i18n_id_seq OWNED BY worldprofe.estatica_i18n.id;


--
-- Name: estatica_id_seq; Type: SEQUENCE; Schema: worldprofe; Owner: postgres
--

CREATE SEQUENCE worldprofe.estatica_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE worldprofe.estatica_id_seq OWNER TO postgres;

--
-- Name: estatica_id_seq; Type: SEQUENCE OWNED BY; Schema: worldprofe; Owner: postgres
--

ALTER SEQUENCE worldprofe.estatica_id_seq OWNED BY worldprofe.estatica.id;


--
-- Name: paquete_creditos; Type: TABLE; Schema: worldprofe; Owner: postgres
--

CREATE TABLE worldprofe.paquete_creditos (
    id bigint NOT NULL,
    creditos bigint,
    coste numeric(15,10)
);


ALTER TABLE worldprofe.paquete_creditos OWNER TO postgres;

--
-- Name: paquete_creditos_id_seq; Type: SEQUENCE; Schema: worldprofe; Owner: postgres
--

CREATE SEQUENCE worldprofe.paquete_creditos_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE worldprofe.paquete_creditos_id_seq OWNER TO postgres;

--
-- Name: paquete_creditos_id_seq; Type: SEQUENCE OWNED BY; Schema: worldprofe; Owner: postgres
--

ALTER SEQUENCE worldprofe.paquete_creditos_id_seq OWNED BY worldprofe.paquete_creditos.id;


--
-- Name: profesor_capacidad; Type: TABLE; Schema: worldprofe; Owner: postgres
--

CREATE TABLE worldprofe.profesor_capacidad (
    profesor_id bigint NOT NULL,
    capacidad_id bigint NOT NULL
);


ALTER TABLE worldprofe.profesor_capacidad OWNER TO postgres;

--
-- Name: profesor_idioma; Type: TABLE; Schema: worldprofe; Owner: postgres
--

CREATE TABLE worldprofe.profesor_idioma (
    id bigint NOT NULL,
    profesor_id bigint NOT NULL,
    idioma_id bigint NOT NULL
);


ALTER TABLE worldprofe.profesor_idioma OWNER TO postgres;

--
-- Name: profesor_idioma_id_seq; Type: SEQUENCE; Schema: worldprofe; Owner: postgres
--

CREATE SEQUENCE worldprofe.profesor_idioma_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE worldprofe.profesor_idioma_id_seq OWNER TO postgres;

--
-- Name: profesor_idioma_id_seq; Type: SEQUENCE OWNED BY; Schema: worldprofe; Owner: postgres
--

ALTER SEQUENCE worldprofe.profesor_idioma_id_seq OWNED BY worldprofe.profesor_idioma.id;


--
-- Name: profesoridioma_nivel; Type: TABLE; Schema: worldprofe; Owner: postgres
--

CREATE TABLE worldprofe.profesoridioma_nivel (
    profesor_idioma_id bigint NOT NULL,
    nivel_id bigint NOT NULL
);


ALTER TABLE worldprofe.profesoridioma_nivel OWNER TO postgres;

--
-- Name: reserva; Type: TABLE; Schema: worldprofe; Owner: postgres
--

CREATE TABLE worldprofe.reserva (
    id bigint NOT NULL,
    estado character varying(50),
    motivo_profesor text,
    motivo_alumno text,
    revisada boolean,
    fecha timestamp with time zone,
    alumno_id bigint,
    profesor_id bigint,
    claselibre_id bigint,
    version bigint DEFAULT 0,
    fecha_solicitud timestamp without time zone DEFAULT now()
);


ALTER TABLE worldprofe.reserva OWNER TO postgres;

--
-- Name: reserva_id_seq; Type: SEQUENCE; Schema: worldprofe; Owner: postgres
--

CREATE SEQUENCE worldprofe.reserva_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE worldprofe.reserva_id_seq OWNER TO postgres;

--
-- Name: reserva_id_seq; Type: SEQUENCE OWNED BY; Schema: worldprofe; Owner: postgres
--

ALTER SEQUENCE worldprofe.reserva_id_seq OWNED BY worldprofe.reserva.id;


--
-- Name: zona_horaria; Type: TABLE; Schema: worldprofe; Owner: postgres
--

CREATE TABLE worldprofe.zona_horaria (
    id bigint NOT NULL,
    gmt text
);


ALTER TABLE worldprofe.zona_horaria OWNER TO postgres;

--
-- Name: zona_horaria_id_seq; Type: SEQUENCE; Schema: worldprofe; Owner: postgres
--

CREATE SEQUENCE worldprofe.zona_horaria_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE worldprofe.zona_horaria_id_seq OWNER TO postgres;

--
-- Name: zona_horaria_id_seq; Type: SEQUENCE OWNED BY; Schema: worldprofe; Owner: postgres
--

ALTER SEQUENCE worldprofe.zona_horaria_id_seq OWNED BY worldprofe.zona_horaria.id;


--
-- Name: alumno id; Type: DEFAULT; Schema: comun; Owner: postgres
--

ALTER TABLE ONLY comun.alumno ALTER COLUMN id SET DEFAULT nextval('comun.alumno_id_seq'::regclass);


--
-- Name: datos_facturacion id; Type: DEFAULT; Schema: comun; Owner: postgres
--

ALTER TABLE ONLY comun.datos_facturacion ALTER COLUMN id SET DEFAULT nextval('comun.datos_facturacion_id_seq'::regclass);


--
-- Name: empresa id; Type: DEFAULT; Schema: comun; Owner: postgres
--

ALTER TABLE ONLY comun.empresa ALTER COLUMN id SET DEFAULT nextval('comun.empresa_id_seq'::regclass);


--
-- Name: idioma id; Type: DEFAULT; Schema: comun; Owner: postgres
--

ALTER TABLE ONLY comun.idioma ALTER COLUMN id SET DEFAULT nextval('comun.idioma_id_seq'::regclass);


--
-- Name: nivel id; Type: DEFAULT; Schema: comun; Owner: postgres
--

ALTER TABLE ONLY comun.nivel ALTER COLUMN id SET DEFAULT nextval('comun.nivel_id_seq'::regclass);


--
-- Name: pais id; Type: DEFAULT; Schema: comun; Owner: postgres
--

ALTER TABLE ONLY comun.pais ALTER COLUMN id SET DEFAULT nextval('comun.pais_id_seq'::regclass);


--
-- Name: profesor id; Type: DEFAULT; Schema: comun; Owner: postgres
--

ALTER TABLE ONLY comun.profesor ALTER COLUMN id SET DEFAULT nextval('comun.profesor_id_seq'::regclass);


--
-- Name: usuario id; Type: DEFAULT; Schema: comun; Owner: postgres
--

ALTER TABLE ONLY comun.usuario ALTER COLUMN id SET DEFAULT nextval('comun.usuario_id_seq'::regclass);


--
-- Name: usuario_social id; Type: DEFAULT; Schema: comun; Owner: postgres
--

ALTER TABLE ONLY comun.usuario_social ALTER COLUMN id SET DEFAULT nextval('comun.usuario_social_id_seq'::regclass);


--
-- Name: capacidad id; Type: DEFAULT; Schema: worldprofe; Owner: postgres
--

ALTER TABLE ONLY worldprofe.capacidad ALTER COLUMN id SET DEFAULT nextval('worldprofe.capacidad_id_seq'::regclass);


--
-- Name: clase_libre id; Type: DEFAULT; Schema: worldprofe; Owner: postgres
--

ALTER TABLE ONLY worldprofe.clase_libre ALTER COLUMN id SET DEFAULT nextval('worldprofe.clase_libre_id_seq'::regclass);


--
-- Name: compra id; Type: DEFAULT; Schema: worldprofe; Owner: postgres
--

ALTER TABLE ONLY worldprofe.compra ALTER COLUMN id SET DEFAULT nextval('worldprofe.compra_id_seq'::regclass);


--
-- Name: configuracion id; Type: DEFAULT; Schema: worldprofe; Owner: postgres
--

ALTER TABLE ONLY worldprofe.configuracion ALTER COLUMN id SET DEFAULT nextval('worldprofe.configuracion_id_seq'::regclass);


--
-- Name: email_reserva id; Type: DEFAULT; Schema: worldprofe; Owner: postgres
--

ALTER TABLE ONLY worldprofe.email_reserva ALTER COLUMN id SET DEFAULT nextval('worldprofe.email_reserva_id_seq'::regclass);


--
-- Name: estatica id; Type: DEFAULT; Schema: worldprofe; Owner: postgres
--

ALTER TABLE ONLY worldprofe.estatica ALTER COLUMN id SET DEFAULT nextval('worldprofe.estatica_id_seq'::regclass);


--
-- Name: estatica_i18n id; Type: DEFAULT; Schema: worldprofe; Owner: postgres
--

ALTER TABLE ONLY worldprofe.estatica_i18n ALTER COLUMN id SET DEFAULT nextval('worldprofe.estatica_i18n_id_seq'::regclass);


--
-- Name: paquete_creditos id; Type: DEFAULT; Schema: worldprofe; Owner: postgres
--

ALTER TABLE ONLY worldprofe.paquete_creditos ALTER COLUMN id SET DEFAULT nextval('worldprofe.paquete_creditos_id_seq'::regclass);


--
-- Name: profesor_idioma id; Type: DEFAULT; Schema: worldprofe; Owner: postgres
--

ALTER TABLE ONLY worldprofe.profesor_idioma ALTER COLUMN id SET DEFAULT nextval('worldprofe.profesor_idioma_id_seq'::regclass);


--
-- Name: reserva id; Type: DEFAULT; Schema: worldprofe; Owner: postgres
--

ALTER TABLE ONLY worldprofe.reserva ALTER COLUMN id SET DEFAULT nextval('worldprofe.reserva_id_seq'::regclass);


--
-- Name: zona_horaria id; Type: DEFAULT; Schema: worldprofe; Owner: postgres
--

ALTER TABLE ONLY worldprofe.zona_horaria ALTER COLUMN id SET DEFAULT nextval('worldprofe.zona_horaria_id_seq'::regclass);


--
-- Data for Name: alumno; Type: TABLE DATA; Schema: comun; Owner: postgres
--

COPY comun.alumno (id, apellidos, skype, creditos_totales, creditos_disponibles, creditos_consumidos, empresa_id) FROM stdin;
\.


--
-- Data for Name: datos_facturacion; Type: TABLE DATA; Schema: comun; Owner: postgres
--

COPY comun.datos_facturacion (id, nombre, nif, provincia, localidad, cp, direccion) FROM stdin;
\.


--
-- Data for Name: empresa; Type: TABLE DATA; Schema: comun; Owner: postgres
--

COPY comun.empresa (id, creditos_totales, creditos_disponibles, creditos_distribuidos, caducidad) FROM stdin;
\.


--
-- Data for Name: idioma; Type: TABLE DATA; Schema: comun; Owner: postgres
--

COPY comun.idioma (id, nombre) FROM stdin;
1	Español
2	Inglés
3	Francés
4	Alemán
5	Chino
6	Italiano
7	Portugués
8	Japonés
9	Gallego
10	Catalán
11	Euskera
12	Bable
\.


--
-- Data for Name: nivel; Type: TABLE DATA; Schema: comun; Owner: postgres
--

COPY comun.nivel (id, nombre) FROM stdin;
1	A1
2	A2
3	B1
4	B2
5	C1
6	C2
\.


--
-- Data for Name: pais; Type: TABLE DATA; Schema: comun; Owner: postgres
--

COPY comun.pais (id, nombre, codigo) FROM stdin;
3041565	Andorra	AD
290557	Emiratos Árabes Unidos	AE
1149361	Afganistán	AF
3576396	Antigua y Barbuda	AG
783754	Albania	AL
174982	Armenia	AM
3351879	Angola	AO
3865483	Argentina	AR
2782113	Austria	AT
2077456	Australia	AU
587116	Azerbaiyán	AZ
3277605	Bosnia-Herzegovina	BA
3374084	Barbados	BB
1210997	Bangladesh	BD
2802361	Bélgica	BE
2361809	Burkina Faso	BF
732800	Bulgaria	BG
290291	Bahréin	BH
433561	Burundi	BI
2395170	Benín	BJ
1820814	Brunéi	BN
3923057	Bolivia	BO
3469034	Brasil	BR
3572887	Bahamas	BS
1252634	Bután	BT
933860	Botsuana	BW
630336	Bielorrusia	BY
3582678	Belice	BZ
6251999	Canadá	CA
203312	Congo Democrático	CD
239880	República Centroafricana	CF
2260494	Congo [República]	CG
2287781	Costa de Marfil	CI
3895114	Chile	CL
2233387	Camerún	CM
1814991	China	CN
3686110	Colombia	CO
3624060	Costa Rica	CR
3562981	Cuba	CU
3374766	Cabo Verde	CV
146669	Chipre	CY
3077311	República Checa	CZ
2658434	Suiza	CH
2921044	Alemania	DE
223816	Yibuti	DJ
2623032	Dinamarca	DK
3575830	Dominica	DM
3508796	República Dominicana	DO
2589581	Argelia	DZ
3658394	Ecuador	EC
453733	Estonia	EE
357994	Egipto	EG
338010	Eritrea	ER
2510769	España	ES
337996	Etiopía	ET
660013	Finlandia	FI
2205218	Fiyi	FJ
3017382	Francia	FR
2400553	Gabón	GA
2635167	Reino Unido	GB
3580239	Granada	GD
614540	Georgia	GE
2300660	Ghana	GH
2413451	Gambia	GM
2420477	Guinea	GN
2309096	Guinea Ecuatorial	GQ
390903	Grecia	GR
3595528	Guatemala	GT
2372248	Guinea-Bissáu	GW
3378535	Guyana	GY
3608932	Honduras	HN
3202326	Croacia	HR
3723988	Haití	HT
719819	Hungría	HU
1643084	Indonesia	ID
2963597	Irlanda	IE
294640	Israel	IL
1269750	India	IN
99237	Irak	IQ
130758	Irán	IR
2629691	Islandia	IS
3175395	Italia	IT
3489940	Jamaica	JM
248816	Jordania	JO
1861060	Japón	JP
192950	Kenia	KE
1527747	Kirguistán	KG
1831722	Camboya	KH
4030945	Kiribati	KI
921929	Comoras	KM
3575174	San Cristóbal y Nieves	KN
1873107	Corea del Norte	KP
1835841	Corea del Sur	KR
285570	Kuwait	KW
1522867	Kazajstán	KZ
1655842	Laos	LA
272103	Líbano	LB
3576468	Santa Lucía	LC
3042058	Liechtenstein	LI
1227603	Sri Lanka	LK
2275384	Liberia	LR
932692	Lesotho	LS
597427	Lituania	LT
2960313	Luxemburgo	LU
458258	Letonia	LV
2215636	Libia	LY
2542007	Marruecos	MA
2993457	Mónaco	MC
617790	Moldavia	MD
3194884	Montenegro	ME
1062947	Madagascar	MG
718075	Macedonia	MK
2453866	Mali	ML
1327865	Myanmar [Birmania]	MM
2029969	Mongolia	MN
2378080	Mauritania	MR
2562770	Malta	MT
934292	Mauricio	MU
1282028	Maldivas	MV
927384	Malaui	MW
3996063	México	MX
1733045	Malasia	MY
1036973	Mozambique	MZ
3355338	Namibia	NA
2440476	Níger	NE
2328926	Nigeria	NG
3617476	Nicaragua	NI
2750405	Holanda	NL
3144096	Noruega	NO
1282988	Nepal	NP
2110425	Nauru	NR
2186224	Nueva Zelanda	NZ
286963	Omán	OM
3703430	Panamá	PA
3932488	Perú	PE
2088628	Papúa-Nueva Guinea	PG
1694008	Filipinas	PH
1168579	Pakistán	PK
798544	Polonia	PL
2264397	Portugal	PT
3437598	Paraguay	PY
289688	Qatar	QA
798549	Rumanía	RO
6290252	Serbia	RS
2017370	Rusia	RU
49518	Ruanda	RW
102358	Arabia Saudí	SA
2103350	Islas Salomón	SB
241170	Seychelles	SC
366755	Sudán	SD
2661886	Suecia	SE
1880251	Singapur	SG
3190538	Eslovenia	SI
3057568	Eslovaquia	SK
2403846	Sierra Leona	SL
3168068	San Marino	SM
2245662	Senegal	SN
51537	Somalia	SO
3382998	Surinam	SR
7909807	Sudán del Sur	SS
2410758	Santo Tomé y Príncipe	ST
3585968	El Salvador	SV
163843	Siria	SY
934841	Suazilandia	SZ
2434508	Chad	TD
2363686	Togo	TG
1605651	Tailandia	TH
1220409	Tayikistán	TJ
1966436	Timor Oriental	TL
1218197	Turkmenistán	TM
2464461	Túnez	TN
4032283	Tonga	TO
298795	Turquía	TR
3573591	Trinidad y Tobago	TT
2110297	Tuvalu	TV
1668284	Taiwán	TW
149590	Tanzania	TZ
690791	Ucrania	UA
226074	Uganda	UG
6252001	Estados Unidos	US
3439705	Uruguay	UY
1512440	Uzbekistán	UZ
3164670	Ciudad del Vaticano	VA
3577815	San Vicente y las Granadinas	VC
3625428	Venezuela	VE
1562822	Vietnam	VN
2134431	Vanuatu	VU
4034894	Samoa	WS
831053	Kosovo	XK
69543	Yemen	YE
953987	Sudáfrica	ZA
895949	Zambia	ZM
878675	Zimbawe	ZW
\.


--
-- Data for Name: profesor; Type: TABLE DATA; Schema: comun; Owner: postgres
--

COPY comun.profesor (id, apellidos, skype, texto_presentacion, fecha_nacimiento, disponibilidad, pais_id, zona_horaria_id, clases_impartidas, descripcion_capacidades) FROM stdin;
\.


--
-- Data for Name: usuario; Type: TABLE DATA; Schema: comun; Owner: postgres
--

COPY comun.usuario (id, rol, login, email, password, foto, validado, activado, eliminado, fecha_registro, fecha_ultimo_acceso, accesos, clave_activacion, clave_reset, fecha_reset, version, nombre, telefono_movil, observaciones, datos_facturacion_id, baja) FROM stdin;
1	ROLE_ADMIN	admin	admin	a4a88c0872bf652bb9ed803ece5fd6e82354838a9bf59ab4babb1dab322154e1	\N	t	t	f	2020-10-16 16:00:55.449394	2020-10-17 09:49:45.557	1	\N	\N	\N	1	\N	\N	\N	\N	f
\.


--
-- Data for Name: usuario_social; Type: TABLE DATA; Schema: comun; Owner: postgres
--

COPY comun.usuario_social (id, user_id, provider_id, provider_user_id, rank, display_name, profile_url, image_url, access_token, secret, refresh_token, expire_time) FROM stdin;
\.


--
-- Data for Name: alumno_paquetecreditos; Type: TABLE DATA; Schema: worldprofe; Owner: postgres
--

COPY worldprofe.alumno_paquetecreditos (alumno_id, paquete_creditos_id) FROM stdin;
\.


--
-- Data for Name: capacidad; Type: TABLE DATA; Schema: worldprofe; Owner: postgres
--

COPY worldprofe.capacidad (id, nombre) FROM stdin;
1	Traductor jurado
2	Coaching
3	Traducción simultánea
4	Economía
5	Medicina
6	Mecánica
7	Nutrición
8	Tecnología
9	Naturaleza
\.


--
-- Data for Name: clase_libre; Type: TABLE DATA; Schema: worldprofe; Owner: postgres
--

COPY worldprofe.clase_libre (id, fecha, ocupada, profesor_id, version) FROM stdin;
\.


--
-- Data for Name: compra; Type: TABLE DATA; Schema: worldprofe; Owner: postgres
--

COPY worldprofe.compra (id, fecha, creditos, precio, precio_con_iva, porcentaje_iva, paquete_creditos_id, paypal_payment_id, realizada, usuario_id, solicitar_factura) FROM stdin;
\.


--
-- Data for Name: configuracion; Type: TABLE DATA; Schema: worldprofe; Owner: postgres
--

COPY worldprofe.configuracion (id, iva, tiempo_maximo_respuesta, tiempo_antes_inicio_cancelar, tiempo_maximo_actualizar_incidencia, correo_notificaciones) FROM stdin;
1	21	24	1	24	
\.


--
-- Data for Name: email_reserva; Type: TABLE DATA; Schema: worldprofe; Owner: postgres
--

COPY worldprofe.email_reserva (id, fecha, url_base, locale, reserva_id) FROM stdin;
\.


--
-- Data for Name: estatica; Type: TABLE DATA; Schema: worldprofe; Owner: postgres
--

COPY worldprofe.estatica (id, identificador) FROM stdin;
1	principal
\.


--
-- Data for Name: estatica_i18n; Type: TABLE DATA; Schema: worldprofe; Owner: postgres
--

COPY worldprofe.estatica_i18n (id, titulo, contenido, idioma_codigo, estatica_id) FROM stdin;
\.


--
-- Data for Name: paquete_creditos; Type: TABLE DATA; Schema: worldprofe; Owner: postgres
--

COPY worldprofe.paquete_creditos (id, creditos, coste) FROM stdin;
\.


--
-- Data for Name: profesor_capacidad; Type: TABLE DATA; Schema: worldprofe; Owner: postgres
--

COPY worldprofe.profesor_capacidad (profesor_id, capacidad_id) FROM stdin;
\.


--
-- Data for Name: profesor_idioma; Type: TABLE DATA; Schema: worldprofe; Owner: postgres
--

COPY worldprofe.profesor_idioma (id, profesor_id, idioma_id) FROM stdin;
\.


--
-- Data for Name: profesoridioma_nivel; Type: TABLE DATA; Schema: worldprofe; Owner: postgres
--

COPY worldprofe.profesoridioma_nivel (profesor_idioma_id, nivel_id) FROM stdin;
\.


--
-- Data for Name: reserva; Type: TABLE DATA; Schema: worldprofe; Owner: postgres
--

COPY worldprofe.reserva (id, estado, motivo_profesor, motivo_alumno, revisada, fecha, alumno_id, profesor_id, claselibre_id, version, fecha_solicitud) FROM stdin;
\.


--
-- Data for Name: zona_horaria; Type: TABLE DATA; Schema: worldprofe; Owner: postgres
--

COPY worldprofe.zona_horaria (id, gmt) FROM stdin;
1	GMT-12
2	GMT-11
3	GMT-10
4	GMT-09
5	GMT-08
6	GMT-07
7	GMT-06
8	GMT-05
9	GMT-04
10	GMT-03
11	GMT-02
12	GMT-01
13	GMT+00
14	GMT+01
15	GMT+02
16	GMT+03
17	GMT+04
18	GMT+05
19	GMT+06
20	GMT+07
21	GMT+08
22	GMT+09
23	GMT+10
24	GMT+11
25	GMT+12
26	GMT+13
27	GMT+14
\.


--
-- Name: alumno_id_seq; Type: SEQUENCE SET; Schema: comun; Owner: postgres
--

SELECT pg_catalog.setval('comun.alumno_id_seq', 1, false);


--
-- Name: datos_facturacion_id_seq; Type: SEQUENCE SET; Schema: comun; Owner: postgres
--

SELECT pg_catalog.setval('comun.datos_facturacion_id_seq', 1, false);


--
-- Name: empresa_id_seq; Type: SEQUENCE SET; Schema: comun; Owner: postgres
--

SELECT pg_catalog.setval('comun.empresa_id_seq', 1, false);


--
-- Name: idioma_id_seq; Type: SEQUENCE SET; Schema: comun; Owner: postgres
--

SELECT pg_catalog.setval('comun.idioma_id_seq', 12, true);


--
-- Name: nivel_id_seq; Type: SEQUENCE SET; Schema: comun; Owner: postgres
--

SELECT pg_catalog.setval('comun.nivel_id_seq', 6, true);


--
-- Name: pais_id_seq; Type: SEQUENCE SET; Schema: comun; Owner: postgres
--

SELECT pg_catalog.setval('comun.pais_id_seq', 1, false);


--
-- Name: profesor_id_seq; Type: SEQUENCE SET; Schema: comun; Owner: postgres
--

SELECT pg_catalog.setval('comun.profesor_id_seq', 1, false);


--
-- Name: usuario_id_seq; Type: SEQUENCE SET; Schema: comun; Owner: postgres
--

SELECT pg_catalog.setval('comun.usuario_id_seq', 1, true);


--
-- Name: usuario_social_id_seq; Type: SEQUENCE SET; Schema: comun; Owner: postgres
--

SELECT pg_catalog.setval('comun.usuario_social_id_seq', 1, false);


--
-- Name: capacidad_id_seq; Type: SEQUENCE SET; Schema: worldprofe; Owner: postgres
--

SELECT pg_catalog.setval('worldprofe.capacidad_id_seq', 9, true);


--
-- Name: clase_libre_id_seq; Type: SEQUENCE SET; Schema: worldprofe; Owner: postgres
--

SELECT pg_catalog.setval('worldprofe.clase_libre_id_seq', 1, false);


--
-- Name: compra_id_seq; Type: SEQUENCE SET; Schema: worldprofe; Owner: postgres
--

SELECT pg_catalog.setval('worldprofe.compra_id_seq', 1, false);


--
-- Name: configuracion_id_seq; Type: SEQUENCE SET; Schema: worldprofe; Owner: postgres
--

SELECT pg_catalog.setval('worldprofe.configuracion_id_seq', 1, true);


--
-- Name: email_reserva_id_seq; Type: SEQUENCE SET; Schema: worldprofe; Owner: postgres
--

SELECT pg_catalog.setval('worldprofe.email_reserva_id_seq', 1, false);


--
-- Name: estatica_i18n_id_seq; Type: SEQUENCE SET; Schema: worldprofe; Owner: postgres
--

SELECT pg_catalog.setval('worldprofe.estatica_i18n_id_seq', 1, false);


--
-- Name: estatica_id_seq; Type: SEQUENCE SET; Schema: worldprofe; Owner: postgres
--

SELECT pg_catalog.setval('worldprofe.estatica_id_seq', 1, true);


--
-- Name: paquete_creditos_id_seq; Type: SEQUENCE SET; Schema: worldprofe; Owner: postgres
--

SELECT pg_catalog.setval('worldprofe.paquete_creditos_id_seq', 1, false);


--
-- Name: profesor_idioma_id_seq; Type: SEQUENCE SET; Schema: worldprofe; Owner: postgres
--

SELECT pg_catalog.setval('worldprofe.profesor_idioma_id_seq', 1, false);


--
-- Name: reserva_id_seq; Type: SEQUENCE SET; Schema: worldprofe; Owner: postgres
--

SELECT pg_catalog.setval('worldprofe.reserva_id_seq', 1, false);


--
-- Name: zona_horaria_id_seq; Type: SEQUENCE SET; Schema: worldprofe; Owner: postgres
--

SELECT pg_catalog.setval('worldprofe.zona_horaria_id_seq', 27, true);


--
-- Name: alumno alumno_pk; Type: CONSTRAINT; Schema: comun; Owner: postgres
--

ALTER TABLE ONLY comun.alumno
    ADD CONSTRAINT alumno_pk PRIMARY KEY (id);


--
-- Name: alumno alumno_skype_key; Type: CONSTRAINT; Schema: comun; Owner: postgres
--

ALTER TABLE ONLY comun.alumno
    ADD CONSTRAINT alumno_skype_key UNIQUE (skype);


--
-- Name: datos_facturacion datos_facturacion_pk; Type: CONSTRAINT; Schema: comun; Owner: postgres
--

ALTER TABLE ONLY comun.datos_facturacion
    ADD CONSTRAINT datos_facturacion_pk PRIMARY KEY (id);


--
-- Name: empresa empresa_pk; Type: CONSTRAINT; Schema: comun; Owner: postgres
--

ALTER TABLE ONLY comun.empresa
    ADD CONSTRAINT empresa_pk PRIMARY KEY (id);


--
-- Name: idioma idioma_nombre_key; Type: CONSTRAINT; Schema: comun; Owner: postgres
--

ALTER TABLE ONLY comun.idioma
    ADD CONSTRAINT idioma_nombre_key UNIQUE (nombre);


--
-- Name: idioma idioma_pk; Type: CONSTRAINT; Schema: comun; Owner: postgres
--

ALTER TABLE ONLY comun.idioma
    ADD CONSTRAINT idioma_pk PRIMARY KEY (id);


--
-- Name: nivel nivel_nombre_key; Type: CONSTRAINT; Schema: comun; Owner: postgres
--

ALTER TABLE ONLY comun.nivel
    ADD CONSTRAINT nivel_nombre_key UNIQUE (nombre);


--
-- Name: nivel nivel_pk; Type: CONSTRAINT; Schema: comun; Owner: postgres
--

ALTER TABLE ONLY comun.nivel
    ADD CONSTRAINT nivel_pk PRIMARY KEY (id);


--
-- Name: pais pais_pk; Type: CONSTRAINT; Schema: comun; Owner: postgres
--

ALTER TABLE ONLY comun.pais
    ADD CONSTRAINT pais_pk PRIMARY KEY (id);


--
-- Name: profesor profesor_pk; Type: CONSTRAINT; Schema: comun; Owner: postgres
--

ALTER TABLE ONLY comun.profesor
    ADD CONSTRAINT profesor_pk PRIMARY KEY (id);


--
-- Name: profesor profesor_skype_key; Type: CONSTRAINT; Schema: comun; Owner: postgres
--

ALTER TABLE ONLY comun.profesor
    ADD CONSTRAINT profesor_skype_key UNIQUE (skype);


--
-- Name: usuario usuario_email_key; Type: CONSTRAINT; Schema: comun; Owner: postgres
--

ALTER TABLE ONLY comun.usuario
    ADD CONSTRAINT usuario_email_key UNIQUE (email);


--
-- Name: usuario usuario_login_key; Type: CONSTRAINT; Schema: comun; Owner: postgres
--

ALTER TABLE ONLY comun.usuario
    ADD CONSTRAINT usuario_login_key UNIQUE (login);


--
-- Name: usuario usuario_pk; Type: CONSTRAINT; Schema: comun; Owner: postgres
--

ALTER TABLE ONLY comun.usuario
    ADD CONSTRAINT usuario_pk PRIMARY KEY (id);


--
-- Name: usuario_social usuario_social_pk; Type: CONSTRAINT; Schema: comun; Owner: postgres
--

ALTER TABLE ONLY comun.usuario_social
    ADD CONSTRAINT usuario_social_pk PRIMARY KEY (id);


--
-- Name: alumno_paquetecreditos alumno_paquetecreditos_pk; Type: CONSTRAINT; Schema: worldprofe; Owner: postgres
--

ALTER TABLE ONLY worldprofe.alumno_paquetecreditos
    ADD CONSTRAINT alumno_paquetecreditos_pk PRIMARY KEY (alumno_id, paquete_creditos_id);


--
-- Name: capacidad capacidad_pk; Type: CONSTRAINT; Schema: worldprofe; Owner: postgres
--

ALTER TABLE ONLY worldprofe.capacidad
    ADD CONSTRAINT capacidad_pk PRIMARY KEY (id);


--
-- Name: clase_libre clase_libre_pk; Type: CONSTRAINT; Schema: worldprofe; Owner: postgres
--

ALTER TABLE ONLY worldprofe.clase_libre
    ADD CONSTRAINT clase_libre_pk PRIMARY KEY (id);


--
-- Name: compra compra_pk; Type: CONSTRAINT; Schema: worldprofe; Owner: postgres
--

ALTER TABLE ONLY worldprofe.compra
    ADD CONSTRAINT compra_pk PRIMARY KEY (id);


--
-- Name: configuracion configuracion_pk; Type: CONSTRAINT; Schema: worldprofe; Owner: postgres
--

ALTER TABLE ONLY worldprofe.configuracion
    ADD CONSTRAINT configuracion_pk PRIMARY KEY (id);


--
-- Name: email_reserva email_reserva_pk; Type: CONSTRAINT; Schema: worldprofe; Owner: postgres
--

ALTER TABLE ONLY worldprofe.email_reserva
    ADD CONSTRAINT email_reserva_pk PRIMARY KEY (id);


--
-- Name: estatica_i18n estatica_i18n_pk; Type: CONSTRAINT; Schema: worldprofe; Owner: postgres
--

ALTER TABLE ONLY worldprofe.estatica_i18n
    ADD CONSTRAINT estatica_i18n_pk PRIMARY KEY (id);


--
-- Name: estatica estatica_identificador_key; Type: CONSTRAINT; Schema: worldprofe; Owner: postgres
--

ALTER TABLE ONLY worldprofe.estatica
    ADD CONSTRAINT estatica_identificador_key UNIQUE (identificador);


--
-- Name: estatica estatica_pk; Type: CONSTRAINT; Schema: worldprofe; Owner: postgres
--

ALTER TABLE ONLY worldprofe.estatica
    ADD CONSTRAINT estatica_pk PRIMARY KEY (id);


--
-- Name: paquete_creditos paquete_creditos_pk; Type: CONSTRAINT; Schema: worldprofe; Owner: postgres
--

ALTER TABLE ONLY worldprofe.paquete_creditos
    ADD CONSTRAINT paquete_creditos_pk PRIMARY KEY (id);


--
-- Name: profesor_capacidad profesor_capacidad_pk; Type: CONSTRAINT; Schema: worldprofe; Owner: postgres
--

ALTER TABLE ONLY worldprofe.profesor_capacidad
    ADD CONSTRAINT profesor_capacidad_pk PRIMARY KEY (profesor_id, capacidad_id);


--
-- Name: profesor_idioma profesor_idioma_pk; Type: CONSTRAINT; Schema: worldprofe; Owner: postgres
--

ALTER TABLE ONLY worldprofe.profesor_idioma
    ADD CONSTRAINT profesor_idioma_pk PRIMARY KEY (id);


--
-- Name: profesoridioma_nivel profesoridioma_nivel_pk; Type: CONSTRAINT; Schema: worldprofe; Owner: postgres
--

ALTER TABLE ONLY worldprofe.profesoridioma_nivel
    ADD CONSTRAINT profesoridioma_nivel_pk PRIMARY KEY (profesor_idioma_id, nivel_id);


--
-- Name: reserva reserva_pk; Type: CONSTRAINT; Schema: worldprofe; Owner: postgres
--

ALTER TABLE ONLY worldprofe.reserva
    ADD CONSTRAINT reserva_pk PRIMARY KEY (id);


--
-- Name: zona_horaria zona_horaria_pk; Type: CONSTRAINT; Schema: worldprofe; Owner: postgres
--

ALTER TABLE ONLY worldprofe.zona_horaria
    ADD CONSTRAINT zona_horaria_pk PRIMARY KEY (id);


--
-- Name: alumno alumno_fk_empresa; Type: FK CONSTRAINT; Schema: comun; Owner: postgres
--

ALTER TABLE ONLY comun.alumno
    ADD CONSTRAINT alumno_fk_empresa FOREIGN KEY (empresa_id) REFERENCES comun.empresa(id) ON DELETE SET NULL;


--
-- Name: alumno alumno_id_fkey; Type: FK CONSTRAINT; Schema: comun; Owner: postgres
--

ALTER TABLE ONLY comun.alumno
    ADD CONSTRAINT alumno_id_fkey FOREIGN KEY (id) REFERENCES comun.usuario(id) ON DELETE CASCADE;


--
-- Name: empresa empresa_id_fkey; Type: FK CONSTRAINT; Schema: comun; Owner: postgres
--

ALTER TABLE ONLY comun.empresa
    ADD CONSTRAINT empresa_id_fkey FOREIGN KEY (id) REFERENCES comun.usuario(id) ON DELETE CASCADE;


--
-- Name: profesor profesor_fk_pais; Type: FK CONSTRAINT; Schema: comun; Owner: postgres
--

ALTER TABLE ONLY comun.profesor
    ADD CONSTRAINT profesor_fk_pais FOREIGN KEY (pais_id) REFERENCES comun.pais(id) ON DELETE SET NULL;


--
-- Name: profesor profesor_fk_zonahoraria; Type: FK CONSTRAINT; Schema: comun; Owner: postgres
--

ALTER TABLE ONLY comun.profesor
    ADD CONSTRAINT profesor_fk_zonahoraria FOREIGN KEY (zona_horaria_id) REFERENCES worldprofe.zona_horaria(id) ON DELETE SET NULL;


--
-- Name: profesor profesor_id_fkey; Type: FK CONSTRAINT; Schema: comun; Owner: postgres
--

ALTER TABLE ONLY comun.profesor
    ADD CONSTRAINT profesor_id_fkey FOREIGN KEY (id) REFERENCES comun.usuario(id) ON DELETE CASCADE;


--
-- Name: usuario usuario_fk_datosfacturacion; Type: FK CONSTRAINT; Schema: comun; Owner: postgres
--

ALTER TABLE ONLY comun.usuario
    ADD CONSTRAINT usuario_fk_datosfacturacion FOREIGN KEY (datos_facturacion_id) REFERENCES comun.datos_facturacion(id) ON DELETE SET NULL;


--
-- Name: alumno_paquetecreditos alumno_paquetecreditos_fk_alumno; Type: FK CONSTRAINT; Schema: worldprofe; Owner: postgres
--

ALTER TABLE ONLY worldprofe.alumno_paquetecreditos
    ADD CONSTRAINT alumno_paquetecreditos_fk_alumno FOREIGN KEY (alumno_id) REFERENCES comun.alumno(id) ON DELETE CASCADE;


--
-- Name: alumno_paquetecreditos alumno_paquetecreditos_fk_paquetecreditos; Type: FK CONSTRAINT; Schema: worldprofe; Owner: postgres
--

ALTER TABLE ONLY worldprofe.alumno_paquetecreditos
    ADD CONSTRAINT alumno_paquetecreditos_fk_paquetecreditos FOREIGN KEY (paquete_creditos_id) REFERENCES worldprofe.paquete_creditos(id) ON DELETE CASCADE;


--
-- Name: clase_libre clase_libre_fk_profesor; Type: FK CONSTRAINT; Schema: worldprofe; Owner: postgres
--

ALTER TABLE ONLY worldprofe.clase_libre
    ADD CONSTRAINT clase_libre_fk_profesor FOREIGN KEY (profesor_id) REFERENCES comun.profesor(id) ON DELETE SET NULL;


--
-- Name: compra compra_fk_paquete_creditos; Type: FK CONSTRAINT; Schema: worldprofe; Owner: postgres
--

ALTER TABLE ONLY worldprofe.compra
    ADD CONSTRAINT compra_fk_paquete_creditos FOREIGN KEY (paquete_creditos_id) REFERENCES worldprofe.paquete_creditos(id) ON DELETE SET NULL;


--
-- Name: compra compra_fk_usuario; Type: FK CONSTRAINT; Schema: worldprofe; Owner: postgres
--

ALTER TABLE ONLY worldprofe.compra
    ADD CONSTRAINT compra_fk_usuario FOREIGN KEY (usuario_id) REFERENCES comun.usuario(id) ON DELETE SET NULL;


--
-- Name: email_reserva email_reserva_fk_reserva; Type: FK CONSTRAINT; Schema: worldprofe; Owner: postgres
--

ALTER TABLE ONLY worldprofe.email_reserva
    ADD CONSTRAINT email_reserva_fk_reserva FOREIGN KEY (reserva_id) REFERENCES worldprofe.reserva(id) ON DELETE CASCADE;


--
-- Name: estatica_i18n estatica_i18n_fk_estatica; Type: FK CONSTRAINT; Schema: worldprofe; Owner: postgres
--

ALTER TABLE ONLY worldprofe.estatica_i18n
    ADD CONSTRAINT estatica_i18n_fk_estatica FOREIGN KEY (estatica_id) REFERENCES worldprofe.estatica(id) ON DELETE CASCADE;


--
-- Name: profesor_capacidad profesor_capacidad_fk_capacidad; Type: FK CONSTRAINT; Schema: worldprofe; Owner: postgres
--

ALTER TABLE ONLY worldprofe.profesor_capacidad
    ADD CONSTRAINT profesor_capacidad_fk_capacidad FOREIGN KEY (capacidad_id) REFERENCES worldprofe.capacidad(id) ON DELETE CASCADE;


--
-- Name: profesor_capacidad profesor_capacidad_fk_profesor; Type: FK CONSTRAINT; Schema: worldprofe; Owner: postgres
--

ALTER TABLE ONLY worldprofe.profesor_capacidad
    ADD CONSTRAINT profesor_capacidad_fk_profesor FOREIGN KEY (profesor_id) REFERENCES comun.profesor(id) ON DELETE CASCADE;


--
-- Name: profesor_idioma profesor_idioma_fk_idioma; Type: FK CONSTRAINT; Schema: worldprofe; Owner: postgres
--

ALTER TABLE ONLY worldprofe.profesor_idioma
    ADD CONSTRAINT profesor_idioma_fk_idioma FOREIGN KEY (idioma_id) REFERENCES comun.idioma(id) ON DELETE CASCADE;


--
-- Name: profesor_idioma profesor_idioma_fk_profesor; Type: FK CONSTRAINT; Schema: worldprofe; Owner: postgres
--

ALTER TABLE ONLY worldprofe.profesor_idioma
    ADD CONSTRAINT profesor_idioma_fk_profesor FOREIGN KEY (profesor_id) REFERENCES comun.profesor(id) ON DELETE CASCADE;


--
-- Name: profesoridioma_nivel profesoridioma_nivel_fk_nivel; Type: FK CONSTRAINT; Schema: worldprofe; Owner: postgres
--

ALTER TABLE ONLY worldprofe.profesoridioma_nivel
    ADD CONSTRAINT profesoridioma_nivel_fk_nivel FOREIGN KEY (nivel_id) REFERENCES comun.nivel(id) ON DELETE CASCADE;


--
-- Name: profesoridioma_nivel profesoridioma_nivel_fk_profesoridioma; Type: FK CONSTRAINT; Schema: worldprofe; Owner: postgres
--

ALTER TABLE ONLY worldprofe.profesoridioma_nivel
    ADD CONSTRAINT profesoridioma_nivel_fk_profesoridioma FOREIGN KEY (profesor_idioma_id) REFERENCES worldprofe.profesor_idioma(id) ON DELETE CASCADE;


--
-- Name: reserva reserva_fk_alumno; Type: FK CONSTRAINT; Schema: worldprofe; Owner: postgres
--

ALTER TABLE ONLY worldprofe.reserva
    ADD CONSTRAINT reserva_fk_alumno FOREIGN KEY (alumno_id) REFERENCES comun.alumno(id) ON DELETE SET NULL;


--
-- Name: reserva reserva_fk_claselibre; Type: FK CONSTRAINT; Schema: worldprofe; Owner: postgres
--

ALTER TABLE ONLY worldprofe.reserva
    ADD CONSTRAINT reserva_fk_claselibre FOREIGN KEY (claselibre_id) REFERENCES worldprofe.clase_libre(id) ON DELETE SET NULL;


--
-- Name: reserva reserva_fk_profesor; Type: FK CONSTRAINT; Schema: worldprofe; Owner: postgres
--

ALTER TABLE ONLY worldprofe.reserva
    ADD CONSTRAINT reserva_fk_profesor FOREIGN KEY (profesor_id) REFERENCES comun.profesor(id) ON DELETE SET NULL;


--
-- PostgreSQL database dump complete
--

