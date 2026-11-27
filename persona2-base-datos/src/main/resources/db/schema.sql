-- SQLite schema for Adivina Quién. Requires SQLite 3.25+.
PRAGMA foreign_keys = ON;

CREATE TABLE IF NOT EXISTS personajes (
    id_personaje INTEGER PRIMARY KEY,
    nombre TEXT NOT NULL COLLATE NOCASE UNIQUE CHECK (length(trim(nombre)) > 0),
    color_ojos TEXT NOT NULL CHECK (length(trim(color_ojos)) > 0),
    tipo_cabello TEXT NOT NULL CHECK (length(trim(tipo_cabello)) > 0),
    color_cabello TEXT NOT NULL CHECK (length(trim(color_cabello)) > 0),
    color_piel TEXT NOT NULL CHECK (length(trim(color_piel)) > 0),
    usa_lentes INTEGER NOT NULL CHECK (usa_lentes IN (0, 1)),
    tiene_sonrisa INTEGER NOT NULL CHECK (tiene_sonrisa IN (0, 1)),
    tiene_barba INTEGER NOT NULL CHECK (tiene_barba IN (0, 1)),
    ruta_imagen TEXT NOT NULL DEFAULT ''
);

CREATE TABLE IF NOT EXISTS accesorios (
    id_accesorio INTEGER PRIMARY KEY,
    nombre TEXT NOT NULL COLLATE NOCASE UNIQUE CHECK (length(trim(nombre)) > 0)
);

CREATE TABLE IF NOT EXISTS personaje_accesorio (
    id_personaje INTEGER NOT NULL,
    id_accesorio INTEGER NOT NULL,
    PRIMARY KEY (id_personaje, id_accesorio),
    FOREIGN KEY (id_personaje) REFERENCES personajes(id_personaje) ON DELETE CASCADE,
    FOREIGN KEY (id_accesorio) REFERENCES accesorios(id_accesorio) ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS partidas (
    id_partida INTEGER PRIMARY KEY,
    fecha_inicio TEXT NOT NULL DEFAULT (strftime('%Y-%m-%dT%H:%M:%fZ', 'now')),
    fecha_fin TEXT,
    estado TEXT NOT NULL DEFAULT 'EN_CURSO' CHECK (estado IN ('EN_CURSO', 'FINALIZADA', 'ABANDONADA')),
    turnos INTEGER NOT NULL DEFAULT 0 CHECK (turnos >= 0),
    CHECK (fecha_fin IS NULL OR fecha_fin >= fecha_inicio),
    CHECK ((estado = 'EN_CURSO' AND fecha_fin IS NULL) OR (estado <> 'EN_CURSO' AND fecha_fin IS NOT NULL))
);

CREATE TABLE IF NOT EXISTS participantes_partida (
    id_partida INTEGER NOT NULL,
    rol TEXT NOT NULL CHECK (rol IN ('HUMANO', 'COMPUTADORA')),
    nombre_mostrado TEXT NOT NULL CHECK (length(trim(nombre_mostrado)) > 0),
    id_personaje_secreto INTEGER,
    resultado TEXT CHECK (resultado IN ('VICTORIA', 'DERROTA', 'EMPATE')),
    PRIMARY KEY (id_partida, rol),
    FOREIGN KEY (id_partida) REFERENCES partidas(id_partida) ON DELETE CASCADE,
    FOREIGN KEY (id_personaje_secreto) REFERENCES personajes(id_personaje) ON DELETE SET NULL
);

CREATE INDEX IF NOT EXISTS idx_partidas_fecha ON partidas(fecha_inicio DESC);
CREATE INDEX IF NOT EXISTS idx_partidas_estado_fecha ON partidas(estado, fecha_inicio DESC);
CREATE INDEX IF NOT EXISTS idx_participante_personaje ON participantes_partida(id_personaje_secreto);

CREATE VIEW IF NOT EXISTS vista_estadisticas_jugador AS
SELECT p.nombre_mostrado AS jugador,
       COUNT(*) AS partidas,
       SUM(CASE WHEN p.resultado = 'VICTORIA' THEN 1 ELSE 0 END) AS victorias,
       SUM(CASE WHEN p.resultado = 'DERROTA' THEN 1 ELSE 0 END) AS derrotas,
       SUM(CASE WHEN p.resultado = 'EMPATE' THEN 1 ELSE 0 END) AS empates
FROM participantes_partida p
JOIN partidas j ON j.id_partida = p.id_partida
WHERE p.rol = 'HUMANO' AND j.estado = 'FINALIZADA'
GROUP BY p.nombre_mostrado COLLATE NOCASE;
