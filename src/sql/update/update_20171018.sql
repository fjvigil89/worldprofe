-- ProfesorIdiomaNivel --
DROP TABLE IF EXISTS worldprofe.profesoridioma_nivel CASCADE;
CREATE TABLE worldprofe.profesoridioma_nivel (
    profesor_idioma_id BIGINT NOT NULL,
    nivel_id           BIGINT NOT NULL,
    CONSTRAINT profesoridioma_nivel_pk PRIMARY KEY (profesor_idioma_id, nivel_id),
    CONSTRAINT profesoridioma_nivel_fk_profesoridioma FOREIGN KEY (profesor_idioma_id) REFERENCES worldprofe.profesor_idioma (id) ON DELETE CASCADE,
    CONSTRAINT profesoridioma_nivel_fk_nivel FOREIGN KEY (nivel_id) REFERENCES comun.nivel (id) ON DELETE CASCADE
);

INSERT INTO worldprofe.profesoridioma_nivel (profesor_idioma_id, nivel_id)
SELECT id, nivel_id FROM worldprofe.profesor_idioma;

ALTER TABLE worldprofe.profesor_idioma DROP COLUMN nivel_id;

-- ZonaHoraria --
DROP TABLE IF EXISTS worldprofe.zona_horaria CASCADE;
CREATE TABLE worldprofe.zona_horaria (
    id          BIGSERIAL,
    gmt	        TEXT,
    CONSTRAINT zona_horaria_pk PRIMARY KEY (id)
);

ALTER TABLE comun.profesor ADD COLUMN zona_horaria_id BIGINT;
ALTER TABLE comun.profesor ADD CONSTRAINT profesor_fk_zonahoraria FOREIGN KEY (zona_horaria_id) REFERENCES worldprofe.zona_horaria (id) ON DELETE SET NULL;

INSERT INTO worldprofe.zona_horaria(gmt) VALUES ('GMT-12');
INSERT INTO worldprofe.zona_horaria(gmt) VALUES ('GMT-11');
INSERT INTO worldprofe.zona_horaria(gmt) VALUES ('GMT-10');
INSERT INTO worldprofe.zona_horaria(gmt) VALUES ('GMT-09');
INSERT INTO worldprofe.zona_horaria(gmt) VALUES ('GMT-08');
INSERT INTO worldprofe.zona_horaria(gmt) VALUES ('GMT-07');
INSERT INTO worldprofe.zona_horaria(gmt) VALUES ('GMT-06');
INSERT INTO worldprofe.zona_horaria(gmt) VALUES ('GMT-05');
INSERT INTO worldprofe.zona_horaria(gmt) VALUES ('GMT-04');
INSERT INTO worldprofe.zona_horaria(gmt) VALUES ('GMT-03');
INSERT INTO worldprofe.zona_horaria(gmt) VALUES ('GMT-02');
INSERT INTO worldprofe.zona_horaria(gmt) VALUES ('GMT-01');
INSERT INTO worldprofe.zona_horaria(gmt) VALUES ('GMT+00');
INSERT INTO worldprofe.zona_horaria(gmt) VALUES ('GMT+01');
INSERT INTO worldprofe.zona_horaria(gmt) VALUES ('GMT+02');
INSERT INTO worldprofe.zona_horaria(gmt) VALUES ('GMT+03');
INSERT INTO worldprofe.zona_horaria(gmt) VALUES ('GMT+04');
INSERT INTO worldprofe.zona_horaria(gmt) VALUES ('GMT+05');
INSERT INTO worldprofe.zona_horaria(gmt) VALUES ('GMT+06');
INSERT INTO worldprofe.zona_horaria(gmt) VALUES ('GMT+07');
INSERT INTO worldprofe.zona_horaria(gmt) VALUES ('GMT+08');
INSERT INTO worldprofe.zona_horaria(gmt) VALUES ('GMT+09');
INSERT INTO worldprofe.zona_horaria(gmt) VALUES ('GMT+10');
INSERT INTO worldprofe.zona_horaria(gmt) VALUES ('GMT+11');
INSERT INTO worldprofe.zona_horaria(gmt) VALUES ('GMT+12');
INSERT INTO worldprofe.zona_horaria(gmt) VALUES ('GMT+13');
INSERT INTO worldprofe.zona_horaria(gmt) VALUES ('GMT+14');

ALTER TABLE comun.profesor ADD COLUMN clases_impartidas BIGINT DEFAULT 0;
