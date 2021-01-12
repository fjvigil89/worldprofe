-- =============== DATOS ===============

-- Para generar contraseñas con salt SHA256:
-- http://www.lorem-ipsum.co.uk/hasher.php
-- String to hash: admin.
-- String to salt: {admin}
--
-- Roles: ROLE_ADMIN, ROLE_COLABORADOR

-- Geonames (Solo en caso de tener geonames importado en la base de datos)
/*INSERT INTO comun.pais(id, nombre, codigo)
SELECT g.geonameid as id,
     COALESCE(
         ( SELECT alternatename
           FROM geonames.alternatename
           WHERE isolanguage LIKE 'es' AND geonameid = g.geonameid
           ORDER BY isshortname, ispreferredname LIMIT 1)
     , g.name) as name,
     g.country as code
 FROM geonames.geoname g
 WHERE g.type LIKE 'COUNTRY' AND g.country NOT IN (SELECT codigo FROM comun.pais)
 ORDER BY 3;*/

INSERT INTO comun.usuario (email, login, rol, validado, activado, fecha_registro, password)
VALUES
    ('admin', 'admin', 'ROLE_ADMIN', TRUE, TRUE, now(),
     'a4a88c0872bf652bb9ed803ece5fd6e82354838a9bf59ab4babb1dab322154e1');

-- Páginas Estáticas que se van a poder administrar
INSERT INTO worldprofe.estatica (identificador) VALUES ('principal');

INSERT INTO worldprofe.capacidad(nombre) VALUES('Traductor jurado');
INSERT INTO worldprofe.capacidad(nombre) VALUES('Coaching');
INSERT INTO worldprofe.capacidad(nombre) VALUES('Traducción simultánea');

-- Idiomas
INSERT INTO comun.idioma(nombre) VALUES('Español');
INSERT INTO comun.idioma(nombre) VALUES('Inglés');

-- Niveles
INSERT INTO comun.nivel(nombre) VALUES('A1');
INSERT INTO comun.nivel(nombre) VALUES('A2');
INSERT INTO comun.nivel(nombre) VALUES('B1');
INSERT INTO comun.nivel(nombre) VALUES('B2');
INSERT INTO comun.nivel(nombre) VALUES('C1');
INSERT INTO comun.nivel(nombre) VALUES('C2');

-- Configuración
INSERT INTO worldprofe.configuracion(iva, tiempo_maximo_respuesta, tiempo_antes_inicio_cancelar,
tiempo_maximo_actualizar_incidencia) VALUES (21, 24, 1, 24);

-- Países
INSERT INTO comun.pais VALUES (3041565, 'Andorra', 'AD');
INSERT INTO comun.pais VALUES (290557, 'Emiratos Árabes Unidos', 'AE');
INSERT INTO comun.pais VALUES (1149361, 'Afganistán', 'AF');
INSERT INTO comun.pais VALUES (3576396, 'Antigua y Barbuda', 'AG');
INSERT INTO comun.pais VALUES (783754, 'Albania', 'AL');
INSERT INTO comun.pais VALUES (174982, 'Armenia', 'AM');
INSERT INTO comun.pais VALUES (3351879, 'Angola', 'AO');
INSERT INTO comun.pais VALUES (3865483, 'Argentina', 'AR');
INSERT INTO comun.pais VALUES (2782113, 'Austria', 'AT');
INSERT INTO comun.pais VALUES (2077456, 'Australia', 'AU');
INSERT INTO comun.pais VALUES (587116, 'Azerbaiyán', 'AZ');
INSERT INTO comun.pais VALUES (3277605, 'Bosnia-Herzegovina', 'BA');
INSERT INTO comun.pais VALUES (3374084, 'Barbados', 'BB');
INSERT INTO comun.pais VALUES (1210997, 'Bangladesh', 'BD');
INSERT INTO comun.pais VALUES (2802361, 'Bélgica', 'BE');
INSERT INTO comun.pais VALUES (2361809, 'Burkina Faso', 'BF');
INSERT INTO comun.pais VALUES (732800, 'Bulgaria', 'BG');
INSERT INTO comun.pais VALUES (290291, 'Bahréin', 'BH');
INSERT INTO comun.pais VALUES (433561, 'Burundi', 'BI');
INSERT INTO comun.pais VALUES (2395170, 'Benín', 'BJ');
INSERT INTO comun.pais VALUES (1820814, 'Brunéi', 'BN');
INSERT INTO comun.pais VALUES (3923057, 'Bolivia', 'BO');
INSERT INTO comun.pais VALUES (3469034, 'Brasil', 'BR');
INSERT INTO comun.pais VALUES (3572887, 'Bahamas', 'BS');
INSERT INTO comun.pais VALUES (1252634, 'Bután', 'BT');
INSERT INTO comun.pais VALUES (933860, 'Botsuana', 'BW');
INSERT INTO comun.pais VALUES (630336, 'Bielorrusia', 'BY');
INSERT INTO comun.pais VALUES (3582678, 'Belice', 'BZ');
INSERT INTO comun.pais VALUES (6251999, 'Canadá', 'CA');
INSERT INTO comun.pais VALUES (203312, 'Congo Democrático', 'CD');
INSERT INTO comun.pais VALUES (239880, 'República Centroafricana', 'CF');
INSERT INTO comun.pais VALUES (2260494, 'Congo [República]', 'CG');
INSERT INTO comun.pais VALUES (2287781, 'Costa de Marfil', 'CI');
INSERT INTO comun.pais VALUES (3895114, 'Chile', 'CL');
INSERT INTO comun.pais VALUES (2233387, 'Camerún', 'CM');
INSERT INTO comun.pais VALUES (1814991, 'China', 'CN');
INSERT INTO comun.pais VALUES (3686110, 'Colombia', 'CO');
INSERT INTO comun.pais VALUES (3624060, 'Costa Rica', 'CR');
INSERT INTO comun.pais VALUES (3562981, 'Cuba', 'CU');
INSERT INTO comun.pais VALUES (3374766, 'Cabo Verde', 'CV');
INSERT INTO comun.pais VALUES (146669, 'Chipre', 'CY');
INSERT INTO comun.pais VALUES (3077311, 'República Checa', 'CZ');
INSERT INTO comun.pais VALUES (2658434, 'Suiza', 'CH');
INSERT INTO comun.pais VALUES (2921044, 'Alemania', 'DE');
INSERT INTO comun.pais VALUES (223816, 'Yibuti', 'DJ');
INSERT INTO comun.pais VALUES (2623032, 'Dinamarca', 'DK');
INSERT INTO comun.pais VALUES (3575830, 'Dominica', 'DM');
INSERT INTO comun.pais VALUES (3508796, 'República Dominicana', 'DO');
INSERT INTO comun.pais VALUES (2589581, 'Argelia', 'DZ');
INSERT INTO comun.pais VALUES (3658394, 'Ecuador', 'EC');
INSERT INTO comun.pais VALUES (453733, 'Estonia', 'EE');
INSERT INTO comun.pais VALUES (357994, 'Egipto', 'EG');
INSERT INTO comun.pais VALUES (338010, 'Eritrea', 'ER');
INSERT INTO comun.pais VALUES (2510769, 'España', 'ES');
INSERT INTO comun.pais VALUES (337996, 'Etiopía', 'ET');
INSERT INTO comun.pais VALUES (660013, 'Finlandia', 'FI');
INSERT INTO comun.pais VALUES (2205218, 'Fiyi', 'FJ');
INSERT INTO comun.pais VALUES (3017382, 'Francia', 'FR');
INSERT INTO comun.pais VALUES (2400553, 'Gabón', 'GA');
INSERT INTO comun.pais VALUES (2635167, 'Reino Unido', 'GB');
INSERT INTO comun.pais VALUES (3580239, 'Granada', 'GD');
INSERT INTO comun.pais VALUES (614540, 'Georgia', 'GE');
INSERT INTO comun.pais VALUES (2300660, 'Ghana', 'GH');
INSERT INTO comun.pais VALUES (2413451, 'Gambia', 'GM');
INSERT INTO comun.pais VALUES (2420477, 'Guinea', 'GN');
INSERT INTO comun.pais VALUES (2309096, 'Guinea Ecuatorial', 'GQ');
INSERT INTO comun.pais VALUES (390903, 'Grecia', 'GR');
INSERT INTO comun.pais VALUES (3595528, 'Guatemala', 'GT');
INSERT INTO comun.pais VALUES (2372248, 'Guinea-Bissáu', 'GW');
INSERT INTO comun.pais VALUES (3378535, 'Guyana', 'GY');
INSERT INTO comun.pais VALUES (3608932, 'Honduras', 'HN');
INSERT INTO comun.pais VALUES (3202326, 'Croacia', 'HR');
INSERT INTO comun.pais VALUES (3723988, 'Haití', 'HT');
INSERT INTO comun.pais VALUES (719819, 'Hungría', 'HU');
INSERT INTO comun.pais VALUES (1643084, 'Indonesia', 'ID');
INSERT INTO comun.pais VALUES (2963597, 'Irlanda', 'IE');
INSERT INTO comun.pais VALUES (294640, 'Israel', 'IL');
INSERT INTO comun.pais VALUES (1269750, 'India', 'IN');
INSERT INTO comun.pais VALUES (99237, 'Irak', 'IQ');
INSERT INTO comun.pais VALUES (130758, 'Irán', 'IR');
INSERT INTO comun.pais VALUES (2629691, 'Islandia', 'IS');
INSERT INTO comun.pais VALUES (3175395, 'Italia', 'IT');
INSERT INTO comun.pais VALUES (3489940, 'Jamaica', 'JM');
INSERT INTO comun.pais VALUES (248816, 'Jordania', 'JO');
INSERT INTO comun.pais VALUES (1861060, 'Japón', 'JP');
INSERT INTO comun.pais VALUES (192950, 'Kenia', 'KE');
INSERT INTO comun.pais VALUES (1527747, 'Kirguistán', 'KG');
INSERT INTO comun.pais VALUES (1831722, 'Camboya', 'KH');
INSERT INTO comun.pais VALUES (4030945, 'Kiribati', 'KI');
INSERT INTO comun.pais VALUES (921929, 'Comoras', 'KM');
INSERT INTO comun.pais VALUES (3575174, 'San Cristóbal y Nieves', 'KN');
INSERT INTO comun.pais VALUES (1873107, 'Corea del Norte', 'KP');
INSERT INTO comun.pais VALUES (1835841, 'Corea del Sur', 'KR');
INSERT INTO comun.pais VALUES (285570, 'Kuwait', 'KW');
INSERT INTO comun.pais VALUES (1522867, 'Kazajstán', 'KZ');
INSERT INTO comun.pais VALUES (1655842, 'Laos', 'LA');
INSERT INTO comun.pais VALUES (272103, 'Líbano', 'LB');
INSERT INTO comun.pais VALUES (3576468, 'Santa Lucía', 'LC');
INSERT INTO comun.pais VALUES (3042058, 'Liechtenstein', 'LI');
INSERT INTO comun.pais VALUES (1227603, 'Sri Lanka', 'LK');
INSERT INTO comun.pais VALUES (2275384, 'Liberia', 'LR');
INSERT INTO comun.pais VALUES (932692, 'Lesotho', 'LS');
INSERT INTO comun.pais VALUES (597427, 'Lituania', 'LT');
INSERT INTO comun.pais VALUES (2960313, 'Luxemburgo', 'LU');
INSERT INTO comun.pais VALUES (458258, 'Letonia', 'LV');
INSERT INTO comun.pais VALUES (2215636, 'Libia', 'LY');
INSERT INTO comun.pais VALUES (2542007, 'Marruecos', 'MA');
INSERT INTO comun.pais VALUES (2993457, 'Mónaco', 'MC');
INSERT INTO comun.pais VALUES (617790, 'Moldavia', 'MD');
INSERT INTO comun.pais VALUES (3194884, 'Montenegro', 'ME');
INSERT INTO comun.pais VALUES (1062947, 'Madagascar', 'MG');
INSERT INTO comun.pais VALUES (718075, 'Macedonia', 'MK');
INSERT INTO comun.pais VALUES (2453866, 'Mali', 'ML');
INSERT INTO comun.pais VALUES (1327865, 'Myanmar [Birmania]', 'MM');
INSERT INTO comun.pais VALUES (2029969, 'Mongolia', 'MN');
INSERT INTO comun.pais VALUES (2378080, 'Mauritania', 'MR');
INSERT INTO comun.pais VALUES (2562770, 'Malta', 'MT');
INSERT INTO comun.pais VALUES (934292, 'Mauricio', 'MU');
INSERT INTO comun.pais VALUES (1282028, 'Maldivas', 'MV');
INSERT INTO comun.pais VALUES (927384, 'Malaui', 'MW');
INSERT INTO comun.pais VALUES (3996063, 'México', 'MX');
INSERT INTO comun.pais VALUES (1733045, 'Malasia', 'MY');
INSERT INTO comun.pais VALUES (1036973, 'Mozambique', 'MZ');
INSERT INTO comun.pais VALUES (3355338, 'Namibia', 'NA');
INSERT INTO comun.pais VALUES (2440476, 'Níger', 'NE');
INSERT INTO comun.pais VALUES (2328926, 'Nigeria', 'NG');
INSERT INTO comun.pais VALUES (3617476, 'Nicaragua', 'NI');
INSERT INTO comun.pais VALUES (2750405, 'Holanda', 'NL');
INSERT INTO comun.pais VALUES (3144096, 'Noruega', 'NO');
INSERT INTO comun.pais VALUES (1282988, 'Nepal', 'NP');
INSERT INTO comun.pais VALUES (2110425, 'Nauru', 'NR');
INSERT INTO comun.pais VALUES (2186224, 'Nueva Zelanda', 'NZ');
INSERT INTO comun.pais VALUES (286963, 'Omán', 'OM');
INSERT INTO comun.pais VALUES (3703430, 'Panamá', 'PA');
INSERT INTO comun.pais VALUES (3932488, 'Perú', 'PE');
INSERT INTO comun.pais VALUES (2088628, 'Papúa-Nueva Guinea', 'PG');
INSERT INTO comun.pais VALUES (1694008, 'Filipinas', 'PH');
INSERT INTO comun.pais VALUES (1168579, 'Pakistán', 'PK');
INSERT INTO comun.pais VALUES (798544, 'Polonia', 'PL');
INSERT INTO comun.pais VALUES (2264397, 'Portugal', 'PT');
INSERT INTO comun.pais VALUES (3437598, 'Paraguay', 'PY');
INSERT INTO comun.pais VALUES (289688, 'Qatar', 'QA');
INSERT INTO comun.pais VALUES (798549, 'Rumanía', 'RO');
INSERT INTO comun.pais VALUES (6290252, 'Serbia', 'RS');
INSERT INTO comun.pais VALUES (2017370, 'Rusia', 'RU');
INSERT INTO comun.pais VALUES (49518, 'Ruanda', 'RW');
INSERT INTO comun.pais VALUES (102358, 'Arabia Saudí', 'SA');
INSERT INTO comun.pais VALUES (2103350, 'Islas Salomón', 'SB');
INSERT INTO comun.pais VALUES (241170, 'Seychelles', 'SC');
INSERT INTO comun.pais VALUES (366755, 'Sudán', 'SD');
INSERT INTO comun.pais VALUES (2661886, 'Suecia', 'SE');
INSERT INTO comun.pais VALUES (1880251, 'Singapur', 'SG');
INSERT INTO comun.pais VALUES (3190538, 'Eslovenia', 'SI');
INSERT INTO comun.pais VALUES (3057568, 'Eslovaquia', 'SK');
INSERT INTO comun.pais VALUES (2403846, 'Sierra Leona', 'SL');
INSERT INTO comun.pais VALUES (3168068, 'San Marino', 'SM');
INSERT INTO comun.pais VALUES (2245662, 'Senegal', 'SN');
INSERT INTO comun.pais VALUES (51537, 'Somalia', 'SO');
INSERT INTO comun.pais VALUES (3382998, 'Surinam', 'SR');
INSERT INTO comun.pais VALUES (7909807, 'Sudán del Sur', 'SS');
INSERT INTO comun.pais VALUES (2410758, 'Santo Tomé y Príncipe', 'ST');
INSERT INTO comun.pais VALUES (3585968, 'El Salvador', 'SV');
INSERT INTO comun.pais VALUES (163843, 'Siria', 'SY');
INSERT INTO comun.pais VALUES (934841, 'Suazilandia', 'SZ');
INSERT INTO comun.pais VALUES (2434508, 'Chad', 'TD');
INSERT INTO comun.pais VALUES (2363686, 'Togo', 'TG');
INSERT INTO comun.pais VALUES (1605651, 'Tailandia', 'TH');
INSERT INTO comun.pais VALUES (1220409, 'Tayikistán', 'TJ');
INSERT INTO comun.pais VALUES (1966436, 'Timor Oriental', 'TL');
INSERT INTO comun.pais VALUES (1218197, 'Turkmenistán', 'TM');
INSERT INTO comun.pais VALUES (2464461, 'Túnez', 'TN');
INSERT INTO comun.pais VALUES (4032283, 'Tonga', 'TO');
INSERT INTO comun.pais VALUES (298795, 'Turquía', 'TR');
INSERT INTO comun.pais VALUES (3573591, 'Trinidad y Tobago', 'TT');
INSERT INTO comun.pais VALUES (2110297, 'Tuvalu', 'TV');
INSERT INTO comun.pais VALUES (1668284, 'Taiwán', 'TW');
INSERT INTO comun.pais VALUES (149590, 'Tanzania', 'TZ');
INSERT INTO comun.pais VALUES (690791, 'Ucrania', 'UA');
INSERT INTO comun.pais VALUES (226074, 'Uganda', 'UG');
INSERT INTO comun.pais VALUES (6252001, 'Estados Unidos', 'US');
INSERT INTO comun.pais VALUES (3439705, 'Uruguay', 'UY');
INSERT INTO comun.pais VALUES (1512440, 'Uzbekistán', 'UZ');
INSERT INTO comun.pais VALUES (3164670, 'Ciudad del Vaticano', 'VA');
INSERT INTO comun.pais VALUES (3577815, 'San Vicente y las Granadinas', 'VC');
INSERT INTO comun.pais VALUES (3625428, 'Venezuela', 'VE');
INSERT INTO comun.pais VALUES (1562822, 'Vietnam', 'VN');
INSERT INTO comun.pais VALUES (2134431, 'Vanuatu', 'VU');
INSERT INTO comun.pais VALUES (4034894, 'Samoa', 'WS');
INSERT INTO comun.pais VALUES (831053, 'Kosovo', 'XK');
INSERT INTO comun.pais VALUES (69543, 'Yemen', 'YE');
INSERT INTO comun.pais VALUES (953987, 'Sudáfrica', 'ZA');
INSERT INTO comun.pais VALUES (895949, 'Zambia', 'ZM');
INSERT INTO comun.pais VALUES (878675, 'Zimbawe', 'ZW');
