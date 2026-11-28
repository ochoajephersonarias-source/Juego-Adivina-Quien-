# Juego Adivina Quién (`Juego-Adivina-Quien-`)

Proyecto para la clase **Programación Intermedia 3 (PAC 2026)** desarrollado en Java, SQLite, Swing, JFreeChart y UML (Umbrello).

## Estructura actual del repositorio
- `ElegisteA/`: proyecto base en Java (modelo POO, lógica de partida, usuarios y vistas).
- `persona2-base-datos/`: módulo Maven con persistencia SQLite (`ConexionSQLite`, `PersonajeDAO`), esquema (`schema.sql`), datos iniciales (`seed.sql`) y documentación en `docs/`.

## Ramas por rol
- `main`: rama principal estable (integración mediante Pull Requests).
- `persona1-arquitectura`: UML en Umbrello, arquitectura y documentación técnica.
- `persona2-base-datos`: base de datos SQLite, JDBC y DAOs (ya fusionada en `main`).
- `persona3`: lógica del juego y estructura POO.
- `persona4-interfaz`: interfaz gráfica Swing del tablero e imágenes de personajes.
- `persona5-usuarios`: autenticación, gestión de usuarios en archivo de texto, sesión e internacionalización.
- `persona6-historial-estadisticas`: historial de partidas, estadísticas y gráficas con JFreeChart.
- `integracion/orden-proyecto`: rama de ordenamiento e integración no destructiva.

## Reglas de colaboración
- No modificar, mover ni borrar archivos de otros compañeros sin aprobación explícita de su autor.
- No hacer `git push --force` ni reescribir historial en ramas compartidas.
- Integrar cambios hacia `main` únicamente mediante Pull Requests revisados.
