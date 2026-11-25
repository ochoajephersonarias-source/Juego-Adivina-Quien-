# Elenco base de personajes — Adivina Quién

Este es el elenco original de 24 personajes ficticios para la primera versión del juego. Los IDs y las rutas quedan estables para que la lógica y la interfaz puedan referenciarlos. Persona 4 debe entregar una imagen por personaje con el nombre exacto indicado en la última columna.

Ubicación esperada de las imágenes en el proyecto Java integrado: `src/main/resources/images/personajes/`. La ruta almacenada en SQLite es de classpath (comienza con `/images/personajes/`), para que la interfaz pueda cargarla con `getResource`.

| ID | Personaje | Ojos | Tipo de cabello | Color de cabello | Piel | Lentes | Sonrisa | Barba | Accesorios | Archivo de imagen |
|---:|---|---|---|---|---|---|---|---|---|---|
| 1 | Abril | Azul | Ondulado | Rubio | Clara | Sí | Sí | No | Aretes | `01-abril.png` |
| 2 | Bruno | Café | Rizado | Negro | Morena | No | No | Sí | Sombrero | `02-bruno.png` |
| 3 | Camila | Verde | Lacio | Castaño | Media | Sí | Sí | No | Collar | `03-camila.png` |
| 4 | Darío | Gris | Calvo | Ninguno | Oscura | No | No | Sí | Corbata | `04-dario.png` |
| 5 | Emilia | Azul | Rizado | Pelirrojo | Clara | Sí | Sí | No | Diadema | `05-emilia.png` |
| 6 | Fabio | Café | Lacio | Negro | Oscura | Sí | No | No | Ninguno | `06-fabio.png` |
| 7 | Gabriela | Verde | Ondulado | Rubio | Media | No | Sí | No | Pañuelo | `07-gabriela.png` |
| 8 | Héctor | Gris | Calvo | Ninguno | Morena | Sí | No | Sí | Bufanda | `08-hector.png` |
| 9 | Inés | Azul | Lacio | Negro | Clara | Sí | Sí | No | Hebilla | `09-ines.png` |
| 10 | Joaquín | Café | Ondulado | Canoso | Media | No | No | No | Ninguno | `10-joaquin.png` |
| 11 | Kiara | Verde | Rizado | Castaño | Morena | Sí | Sí | No | Pendientes | `11-kiara.png` |
| 12 | Leandro | Gris | Calvo | Ninguno | Oscura | No | No | Sí | Gorra | `12-leandro.png` |
| 13 | Mónica | Azul | Ondulado | Pelirrojo | Media | Sí | No | No | Aretes | `13-monica.png` |
| 14 | Nicolás | Café | Lacio | Rubio | Clara | No | Sí | No | Ninguno | `14-nicolas.png` |
| 15 | Óscar | Verde | Calvo | Ninguno | Oscura | No | No | No | Ninguno | `15-oscar.png` |
| 16 | Pablo | Gris | Rizado | Negro | Morena | Sí | Sí | Sí | Bufanda y corbata | `16-pablo.png` |
| 17 | Renata | Azul | Lacio | Castaño | Oscura | No | No | No | Sombrero | `17-renata.png` |
| 18 | Samuel | Café | Rizado | Canoso | Media | Sí | Sí | No | Ninguno | `18-samuel.png` |
| 19 | Teresa | Verde | Ondulado | Pelirrojo | Clara | No | No | No | Lazo | `19-teresa.png` |
| 20 | Ulises | Gris | Lacio | Canoso | Morena | No | Sí | No | Corbata | `20-ulises.png` |
| 21 | Valeria | Azul | Ondulado | Rubio | Oscura | Sí | No | No | Ninguno | `21-valeria.png` |
| 22 | William | Café | Calvo | Ninguno | Media | No | Sí | Sí | Gorra | `22-william.png` |
| 23 | Ximena | Verde | Rizado | Castaño | Clara | Sí | No | No | Collar | `23-ximena.png` |
| 24 | Yadiel | Gris | Calvo | Ninguno | Morena | No | Sí | No | Ninguno | `24-yadiel.png` |

## Balance de rasgos

- Ojos: seis personajes por color (azul, café, verde y gris).
- Tipo de cabello: seis por tipo (lacio, ondulado, rizado y calvo).
- Piel: seis por tono (clara, media, morena y oscura).
- Lentes: doce sí y doce no.
- Sonrisa: doce sí y doce no.
- Barba: seis sí y dieciocho no.

Para los personajes calvos, `color_cabello` se guarda como `ninguno`; el tipo de cabello es el rasgo que indica que no tienen cabello. Los accesorios múltiples se almacenan como filas independientes en `personaje_accesorio`.
