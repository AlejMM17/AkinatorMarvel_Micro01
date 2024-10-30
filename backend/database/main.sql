CREATE OR REPLACE DATABASE marvel_akinator;
USE marvel_akinator;

CREATE TABLE questions(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    questions   VARCHAR(200) NOT NULL,
    yes_node_id INT,
    no_node_id  INT
    CONSTRAINT fk_yes_node FOREIGN KEY (yes_node_id) REFERENCES questions(id),
    CONSTRAINT fk_no_node FOREIGN KEY (no_node_id) REFERENCES questions(id)
);

CREATE TABLE characters(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(200) NOT NULL,
    node_id   INT
    CONSTRAINT fk_node FOREIGN KEY (node_id) REFERENCES questions(id)
);