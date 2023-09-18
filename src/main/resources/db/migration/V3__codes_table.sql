DROP TABLE IF EXISTS discount_codes;
CREATE TABLE discount_codes
(id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, name VARCHAR(20) NOT NULL, discount FLOAT(4,2) NOT NULL);
INSERT INTO discount_codes(name, discount)
VALUES
    ('FREE', 10.00),
    ('MINSK',5.00);

ALTER TABLE orders DROP COLUMN code;
ALTER TABLE orders ADD COLUMN code_id INT AFTER shipping_cost;
