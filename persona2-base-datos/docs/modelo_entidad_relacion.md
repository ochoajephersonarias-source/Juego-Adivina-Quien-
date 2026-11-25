# Modelo entidad-relación

Modelo correspondiente al esquema SQLite de Adivina Quién. La vista `vista_estadisticas_jugador` es derivada y no almacena datos por separado.

```mermaid
erDiagram
    PERSONAJES ||--o{ PERSONAJE_ACCESORIO : tiene
    ACCESORIOS ||--o{ PERSONAJE_ACCESORIO : asignado_a
    PARTIDAS ||--o{ PARTICIPANTES_PARTIDA : registra
    PERSONAJES o|--o{ PARTICIPANTES_PARTIDA : personaje_secreto

    PERSONAJES {
        INTEGER id_personaje PK
        TEXT nombre UK
        TEXT color_ojos
        TEXT tipo_cabello
        TEXT color_cabello
        TEXT color_piel
        INTEGER usa_lentes
        INTEGER tiene_sonrisa
        INTEGER tiene_barba
        TEXT ruta_imagen
    }
    ACCESORIOS {
        INTEGER id_accesorio PK
        TEXT nombre UK
    }
    PERSONAJE_ACCESORIO {
        INTEGER id_personaje PK, FK
        INTEGER id_accesorio PK, FK
    }
    PARTIDAS {
        INTEGER id_partida PK
        TEXT fecha_inicio
        TEXT fecha_fin
        TEXT estado
        INTEGER turnos
    }
    PARTICIPANTES_PARTIDA {
        INTEGER id_partida PK, FK
        TEXT rol PK
        TEXT nombre_mostrado
        INTEGER id_personaje_secreto FK
        TEXT resultado
    }
```

## Cardinalidades y reglas

- Un personaje puede tener cero o varios accesorios; un accesorio puede pertenecer a varios personajes. `personaje_accesorio` resuelve esta relación N:M con PK compuesta.
- Una partida puede registrar participantes con roles distintos. `participantes_partida` limita cada rol a una fila por partida mediante la PK compuesta `(id_partida, rol)`; los roles permitidos son `HUMANO` y `COMPUTADORA`.
- Cada participante puede guardar cero o un personaje secreto. Un personaje puede ser secreto de varios participantes a lo largo del historial.
- Al borrar una partida se borran sus filas de participantes; al borrar un personaje se eliminan sus asociaciones a accesorios y se pone en `NULL` la referencia histórica al personaje secreto.
- Los campos booleanos se almacenan como `0/1`; `resultado` admite `VICTORIA`, `DERROTA` o `EMPATE`.
