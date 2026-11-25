-- Elenco oficial de 24 personajes ficticios del proyecto.
-- Las imágenes las agrega Persona 4 en src/main/resources/images/personajes/.
INSERT OR IGNORE INTO personajes
    (id_personaje, nombre, color_ojos, tipo_cabello, color_cabello, color_piel,
     usa_lentes, tiene_sonrisa, tiene_barba, ruta_imagen)
VALUES
 (1,'Abril','azul','ondulado','rubio','clara',1,1,0,'/images/personajes/01-abril.png'),
 (2,'Bruno','café','rizado','negro','morena',0,0,1,'/images/personajes/02-bruno.png'),
 (3,'Camila','verde','lacio','castaño','media',1,1,0,'/images/personajes/03-camila.png'),
 (4,'Darío','gris','calvo','ninguno','oscura',0,0,1,'/images/personajes/04-dario.png'),
 (5,'Emilia','azul','rizado','pelirrojo','clara',1,1,0,'/images/personajes/05-emilia.png'),
 (6,'Fabio','café','lacio','negro','oscura',1,0,0,'/images/personajes/06-fabio.png'),
 (7,'Gabriela','verde','ondulado','rubio','media',0,1,0,'/images/personajes/07-gabriela.png'),
 (8,'Héctor','gris','calvo','ninguno','morena',1,0,1,'/images/personajes/08-hector.png'),
 (9,'Inés','azul','lacio','negro','clara',1,1,0,'/images/personajes/09-ines.png'),
 (10,'Joaquín','café','ondulado','canoso','media',0,0,0,'/images/personajes/10-joaquin.png'),
 (11,'Kiara','verde','rizado','castaño','morena',1,1,0,'/images/personajes/11-kiara.png'),
 (12,'Leandro','gris','calvo','ninguno','oscura',0,0,1,'/images/personajes/12-leandro.png'),
 (13,'Mónica','azul','ondulado','pelirrojo','media',1,0,0,'/images/personajes/13-monica.png'),
 (14,'Nicolás','café','lacio','rubio','clara',0,1,0,'/images/personajes/14-nicolas.png'),
 (15,'Óscar','verde','calvo','ninguno','oscura',0,0,0,'/images/personajes/15-oscar.png'),
 (16,'Pablo','gris','rizado','negro','morena',1,1,1,'/images/personajes/16-pablo.png'),
 (17,'Renata','azul','lacio','castaño','oscura',0,0,0,'/images/personajes/17-renata.png'),
 (18,'Samuel','café','rizado','canoso','media',1,1,0,'/images/personajes/18-samuel.png'),
 (19,'Teresa','verde','ondulado','pelirrojo','clara',0,0,0,'/images/personajes/19-teresa.png'),
 (20,'Ulises','gris','lacio','canoso','morena',0,1,0,'/images/personajes/20-ulises.png'),
 (21,'Valeria','azul','ondulado','rubio','oscura',1,0,0,'/images/personajes/21-valeria.png'),
 (22,'William','café','calvo','ninguno','media',0,1,1,'/images/personajes/22-william.png'),
 (23,'Ximena','verde','rizado','castaño','clara',1,0,0,'/images/personajes/23-ximena.png'),
 (24,'Yadiel','gris','calvo','ninguno','morena',0,1,0,'/images/personajes/24-yadiel.png');

INSERT OR IGNORE INTO accesorios (nombre) VALUES
 ('aretes'), ('sombrero'), ('collar'), ('corbata'), ('diadema'),
 ('pañuelo'), ('bufanda'), ('hebilla'), ('pendientes'), ('gorra'), ('lazo');

INSERT OR IGNORE INTO personaje_accesorio (id_personaje, id_accesorio)
SELECT p.id_personaje, a.id_accesorio FROM personajes p, accesorios a
WHERE (p.nombre = 'Abril' AND a.nombre = 'aretes')
   OR (p.nombre = 'Bruno' AND a.nombre = 'sombrero')
   OR (p.nombre = 'Camila' AND a.nombre = 'collar')
   OR (p.nombre = 'Darío' AND a.nombre = 'corbata')
   OR (p.nombre = 'Emilia' AND a.nombre = 'diadema')
   OR (p.nombre = 'Gabriela' AND a.nombre = 'pañuelo')
   OR (p.nombre = 'Héctor' AND a.nombre = 'bufanda')
   OR (p.nombre = 'Inés' AND a.nombre = 'hebilla')
   OR (p.nombre = 'Kiara' AND a.nombre = 'pendientes')
   OR (p.nombre = 'Leandro' AND a.nombre = 'gorra')
   OR (p.nombre = 'Mónica' AND a.nombre = 'aretes')
   OR (p.nombre = 'Pablo' AND a.nombre IN ('bufanda', 'corbata'))
   OR (p.nombre = 'Renata' AND a.nombre = 'sombrero')
   OR (p.nombre = 'Teresa' AND a.nombre = 'lazo')
   OR (p.nombre = 'Ulises' AND a.nombre = 'corbata')
   OR (p.nombre = 'William' AND a.nombre = 'gorra')
   OR (p.nombre = 'Ximena' AND a.nombre = 'collar');
