CREATE OR REPLACE DATABASE marvel_akinator;
USE marvel;

-- Tabla para almacenar las preguntas y personajes
CREATE TABLE nodes (
   id INT AUTO_INCREMENT PRIMARY KEY,
   pregunta TEXT,
   personaje VARCHAR(255),
   parent_id INT,
   izquierdo_id INT,
   derecho_id INT,
   FOREIGN KEY (parent_id) REFERENCES nodes(id),
   FOREIGN KEY (izquierdo_id) REFERENCES nodes(id),
   FOREIGN KEY (derecho_id) REFERENCES nodes(id)
);