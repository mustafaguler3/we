CREATE TABLE IF NOT EXISTS person (
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255)
);

INSERT INTO person (first_name, last_name)
VALUES
    ('Mustafa', 'Güler'),
    ('Ahmet', 'Yılmaz'),
    ('Ayşe', 'Demir');