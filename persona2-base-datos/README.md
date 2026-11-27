# Persistencia SQLite — Adivina Quién

Módulo de base de datos de Persona 2. El esquema guarda los personajes y sus características físicas, y deja preparado el historial para que Persona 6 pueda registrar partidas y consultar estadísticas.

## Archivos

- `src/main/resources/db/schema.sql`: tablas, claves, restricciones, índices y vista de estadísticas.
- `src/main/resources/db/seed.sql`: 24 personajes iniciales y sus accesorios.
- `docs/modelo_entidad_relacion.md`: diagrama ER y cardinalidades de las relaciones.
- `docs/elenco_personajes.md`: listado acordado con rasgos y nombre/ruta exactos de cada imagen para Persona 4.
- `data/adivina-quien.db`: base de datos SQLite lista para abrir.
- `src/main/java/persistencia/ConexionSQLite.java`: conexión JDBC; activa claves foráneas e inicializa esquema/datos base de forma idempotente al primer acceso.
- `src/main/java/persistencia/PersonajeDAO.java`: operaciones CRUD y consultas de personajes.

## Uso desde Java

El proyecto usa Java 21 y Maven. La dependencia `org.xerial:sqlite-jdbc` está declarada en `pom.xml`. El DAO utiliza `modelo.Personaje` con el constructor/getters de la clase del UML. El `modelo.Personaje` incluido aquí es un DTO de compatibilidad para compilar este módulo por separado; al integrarlo en el repositorio principal, conserva la clase `modelo.Personaje` que ya existe allí y copia el paquete `persistencia` y los recursos `db`.

```java
PersonajeDAO dao = new PersonajeDAO();
List<Personaje> tablero = dao.obtener24Aleatorios();
Personaje personaje = dao.buscarPorId(1);
```

La ruta predeterminada es `data/adivina-quien.db`, relativa al directorio de ejecución. Se puede cambiar con `-Dadivina.db.path=ruta/archivo.db`. Para una partida, Persona 6 puede insertar en `partidas` y dos filas en `participantes_partida` (roles `HUMANO` y `COMPUTADORA`); al terminar, actualiza estado, fecha de fin, turnos y resultado de cada participante. La vista `vista_estadisticas_jugador` agrupa partidas finalizadas por nombre mostrado.

## Imágenes de personajes

Persona 4 debe colocar los archivos PNG con los nombres de `docs/elenco_personajes.md` en `src/main/resources/images/personajes/`. Las rutas guardadas en la base comienzan con `/images/personajes/` y se pueden cargar desde Java con `Personaje.class.getResource(rutaImagen)`.

## Modelo

`personajes` almacena los rasgos de cardinalidad uno (ojos, cabello, piel, lentes, sonrisa, barba e imagen). `accesorios` y `personaje_accesorio` normalizan el rasgo de cardinalidad múltiple. Las relaciones usan FK con borrado en cascada para los accesorios del personaje. Las partidas guardan fecha, estado y turnos; participantes de partida conservan rol, nombre, personaje secreto y resultado. No se vincula a cuentas porque la autenticación se gestiona fuera de SQLite según la división del proyecto.

Todas las conexiones activan `PRAGMA foreign_keys=ON`. Los booleanos se almacenan como `0/1`. El DAO representa los accesorios en `Personaje.accesorios` como texto separado por coma al mapearlos; en la base se mantienen en filas independientes.
