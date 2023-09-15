ALTER TABLE categories DROP COLUMN image_id;
ALTER TABLE products DROP COLUMN image_id;

DROP TABLE IF EXISTS images;
CREATE TABLE images
(id INT PRIMARY KEY AUTO_INCREMENT NOT NULL, path TEXT DEFAULT NULL, category_id INT DEFAULT NULL,
product_id INT DEFAULT NULL, isPrime TINYINT DEFAULT 1);
INSERT INTO images(PATH, CATEGORY_ID, PRODUCT_ID, ISPRIME)
VALUES
    ('images/sneakers.png',1, NULL, 1),
    ('images/running shoes.png',2, NULL, 1),
    ('images/Nike x CLOT_prime.jpg',NULL, 1, 1),
    ('images/Vans x Sesame Street Style Oscar the Groach_prime.jpg',NULL, 2, 1),
    ('images/Vans CLASSIC SLIP-ON_prime.jpg',NULL, 3, 1),
    ('images/Nike Air Max 270_prime.jpg',NULL, 4, 1),
    ('images/Nike Challenger OG_prime.jpg',NULL, 5, 1),
    ('images/Nike React Vision_prime.jpg',NULL, 6, 1),
    ('images/Nike Air Force 1_prime.jpg',NULL, 7, 1),
    ('images/Nike x CLOT_2.jpg',NULL, 1, 0),
    ('images/Nike React Vision_2.jpg',NULL, 6, 0),
    ('images/Nike Challenger OG_2.jpg',NULL, 5, 0),
    ('images/Nike Air Max 270_2.jpg',NULL, 4, 0),
    ('images/Nike Air Force 1_2.jpg',NULL, 7, 0),
    ('images/Vans CLASSIC SLIP-ON_2.jpg',NULL, 3, 0);