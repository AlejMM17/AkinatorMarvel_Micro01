CREATE OR REPLACE DATABASE marvel_akinator;
USE marvel;

-- Tabla para almacenar las preguntas y personajes
CREATE TABLE nodes (
   id INT AUTO_INCREMENT PRIMARY KEY,
   pregunta TEXT,
   personaje VARCHAR(255),
   parent_id INT,
   izquierdo_id INT,
   derecho_id INT
);


INSERT INTO nodes (pregunta, personaje, parent_id, izquierdo_id, derecho_id) VALUES
    ('¿Es un superhéroe?', NULL, NULL, 2, 3),
    ('¿Es parte de los Vengadores?', NULL, 1, 4, 5),
    ('¿Es un villano?', NULL, 1, 6, 7),
    ('¿Usa un traje metálico?', NULL, 2, 8, 9),
    ('¿Vive en Nueva York?', NULL, 2, 10, 11),
    ('¿Es de origen alienígena?', NULL, 3, 12, 13),
    ('¿Tiene poderes mentales?', NULL, 3, 14, 15),
    ('¿Es el líder?', NULL, 4, 16, 17),
    ('¿Puede lanzar telarañas?', NULL, 4, 18, 19),
    ('¿Es un hechicero?', NULL, 5, 20, 21),
    ('¿Es un agente de SHIELD?', NULL, 5, 22, 23),
    ('¿Tiene un ejército?', NULL, 6, 24, 25),
    ('¿Tiene la capacidad de cambiar de forma?', NULL, 6, 26, 27),
    ('¿Es extremadamente inteligente?', NULL, 7, 28, 29),
    ('¿Es conocido por su fuerza?', NULL, 7, 30, 0);

INSERT INTO nodes (pregunta, personaje, parent_id) VALUES
    (NULL, 'Iron Man', 8),
    (NULL, 'War Machine', 8),
    (NULL, 'Spider-Man', 9),
    (NULL, 'Black Widow', 9),
    (NULL, 'Doctor Strange', 10),
    (NULL, 'Scarlet Witch', 10),
    (NULL, 'Nick Fury', 11),
    (NULL, 'Maria Hill', 11),
    (NULL, 'Thanos', 12),
    (NULL, 'Loki', 12),
    (NULL, 'Mystique', 13),
    (NULL, 'Skrull', 13),
    (NULL, 'Professor X', 14),
    (NULL, 'Magneto', 14),
    (NULL, 'Hulk', 15);

UPDATE nodes SET izquierdo_id = 16, derecho_id = 17 WHERE id = 8;
UPDATE nodes SET izquierdo_id = 18, derecho_id = 19 WHERE id = 9;
UPDATE nodes SET izquierdo_id = 20, derecho_id = 21 WHERE id = 10;
UPDATE nodes SET izquierdo_id = 22, derecho_id = 23 WHERE id = 11;
UPDATE nodes SET izquierdo_id = 24, derecho_id = 25 WHERE id = 12;
UPDATE nodes SET izquierdo_id = 26, derecho_id = 27 WHERE id = 13;
UPDATE nodes SET izquierdo_id = 28, derecho_id = 29 WHERE id = 14;
UPDATE nodes SET izquierdo_id = 30, derecho_id = NULL WHERE id = 15;
