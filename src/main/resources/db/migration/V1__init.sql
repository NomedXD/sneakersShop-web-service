DROP TABLE IF EXISTS categories;
CREATE TABLE categories
(id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, name VARCHAR(45) DEFAULT (NULL),
 sometext VARCHAR(45) DEFAULT (NULL), image_id INT DEFAULT (NULL));
INSERT INTO categories(name, sometext, image_id)
VALUES
    ('Sneakers','High rated sneakers',1),
    ('Running shoes','Best seller running shoes',2);

DROP TABLE IF EXISTS images;
CREATE TABLE images
(id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, path TEXT DEFAULT (NULL));
INSERT INTO images(path)
VALUES
    ('images/sneakers.png'),
    ('images/running shoes.png'),
    ('images/Nike x CLOT.jpg'),
    ('images/Vans x Sesame Street Style Oscar the Groach.png'),
    ('images/Vans CLASSIC SLIP-ON.png'),
    ('images/Nike Air Max 270.jpg'),
    ('images/Nike Challenger OG.jpg'),
    ('images/Nike React Vision.jpg'),
    ('images/Nike Air Force 1.jpg');

DROP TABLE IF EXISTS orders;
CREATE TABLE orders
(id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,price FLOAT DEFAULT (NULL),
 date DATE DEFAULT (NULL), user_id INT DEFAULT (NULL), cc_number TINYTEXT DEFAULT (NULL), shipping_type VARCHAR(45) DEFAULT (NULL),
 shipping_cost FLOAT(4,2) DEFAULT (NULL), code VARCHAR(10) DEFAULT (NULL), address TINYTEXT DEFAULT (NULL), customer_notes TEXT DEFAULT (NULL));
INSERT INTO orders(price, date, user_id, cc_number, shipping_type, shipping_cost, code, address, customer_notes)
VALUES
    (800,'2023-08-07', 1, '11111 **** **** 1111', 'Delivery by courier', 10.00, 'code', 'Rokossovskogo', 'Notes');

DROP TABLE IF EXISTS orders_products;
CREATE TABLE orders_products(order_id INT PRIMARY KEY NOT NULL, product_id INT NOT NULL);
INSERT INTO orders_products(order_id, product_id)
VALUES
    (1,1);

DROP TABLE IF EXISTS products;
CREATE TABLE products
(id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, name VARCHAR(45) DEFAULT (NULL), description TEXT DEFAULT (NULL),
 category_id INT DEFAULT (NULL), price FLOAT DEFAULT (NULL), image_id INT DEFAULT (NULL));
INSERT INTO products(name, description, category_id, price, image_id)
VALUES
    ('Nike x CLOT','The top of the Air Force 1 "Royal University Blue Silk" is made of blue silky fabric decorated with a branded CLOT print. The print is inspired by millennial Chinese patterns and is repeated on a gray-brown leather layer hiding under the top. This symbolizes the yin-yang philosophy and the CLOT message that beauty can be found both outside and inside. The design of the new model is completed by the laconic CLOT branding and a rubber outsole in a classic brown color. For a short video as part of the advertising campaign, the brand attracted the popular Chinese actor and martial artist Donnie Yen, who starred in "Rogue One. Star Wars: Stories", a series of films "Ip Man" and other blockbusters.',1, 800, 3),
    ('Vans x Sesame Street Style Oscar the Groach', 'Vans, the original action sports brand and global icon of creative exploration, is proud to collaborate with one of the most impactful television programs of all time: Sesame Street. Highlighting both brands’ shared focus on creative discovery and uplifting a “We All Belong” message, the Vans x Sesame Street collection honours the iconic street where kids grow smarter, stronger, and kinder.', 2, 90, 4),
    ('Vans CLASSIC SLIP-ON', 'The Vans Classic Slip-On 98 DX Shoes borrow details from the original Classic Slip-On and offer modernised comfort with upgraded Ortholite® sockliners. These shoes also include throwback details like the original style number and colour palette or reissued prints, higher glossed foxing tape, original drill lining weight, and sturdy canvas checkerboard print uppers to complete the look.', 2, 70, 5),
    ('Nike Air Max 270', 'Nike Air Max 270 is an iconic model for fans of street style. The usual air insert for the line is enlarged to the maximum size here, which creates a recognizable silhouette and gives top-end cushioning. The streamlined design refers us to the running classics, but there are no standard restrictions of sports shoes here. The upper made of wear-resistant synthetics guarantees a perfect fit on the foot, and the elastic inner insert and the cunning design of the sock are also responsible for the convenience. Like all the best Nike sneakers, the 270s found the perfect balance between vintage shapes and the most up-to-date technological equipment.', 1, 152, 6),
    ('Nike Challenger OG', 'Ageless classics – the first batch of these running shoes was released back in 1979, and has hardly changed since then. Strict black-and-white coloring made the "challengers" an excellent option for every day, but over time the line has grown into brighter options. The upper of the sneakers is a combination of mesh, nylon and suede – this ensures both light weight and overall comfort when wearing a pair from spring to autumn. The emphasis here is not on manufacturability, but on general ideas about convenience – a soft insert in the ankle area, good cushioning of the sole, ease of care. Nike Challenger OG is suitable for both men and women, the size grid even provides options for children.', 1, 83, 7),
    ('Nike React Vision', 'Here, the brand''s achievements in the field of working with textiles are fully used: the multilayer synthetic upper attracts the eye, but at the same time provides the highest comfort, since it completely repeats the silhouette of the foot. The plastic heel lock in tandem with an aggressive sole creates a unique image – React Vision is really not to be confused with analogues. The appearance of the sneakers is as if it is the result of manual labor. Well, where without the Nike React branded foam material – the road under your feet is literally not felt, even if you walk along it for far from the first hour.', 1, 136 , 8),
    ('Nike Air Force 1', 'Nike Air Force 1 is the quintessence of the seventies style of the last century. There is a cute retro, and obvious basketball silhouettes, and the perforation of the skin, which counts down just from the moment when sneakers turn into shoes for every day. In terms of performance, the "forces" are comfortable and perfectly suitable for the demi-season: the skin is hardy, any dirt from them is easily removed with a conventional damp cloth.But its popularity is only growing from year to year: the design of really iconic sneakers, like wine, only gets better after a while.', 1, 140, 9);

DROP TABLE IF EXISTS time_statistic;
CREATE TABLE time_statistic(id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, description VARCHAR(45) DEFAULT (NULL));
INSERT INTO time_statistic(description)
VALUES
    ('Time to read categorySneakers is 0.5591866'),
    ('Time to read category Sneakers is 0.060088'),
    ('Time to read category Sneakers is 0.3226103');

DROP TABLE IF EXISTS users;
CREATE TABLE users(id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, mail VARCHAR(45) UNIQUE DEFAULT (NULL),
                   password VARCHAR(45) DEFAULT (NULL), name VARCHAR(45) DEFAULT (NULL), surname VARCHAR(45) DEFAULT (NULL),
                   date DATE DEFAULT (NULL), balance FLOAT DEFAULT (0), mobile VARCHAR(45) DEFAULT ('Unspecified'),
                   street VARCHAR(45) DEFAULT ('Unspecified') , accommodation_number VARCHAR(45) DEFAULT ('Unspecified'),
                   flat_number VARCHAR(45) DEFAULT ('Unspecified'));
INSERT INTO users(mail, password, name, surname, date, balance, mobile, street, accommodation_number, flat_number)
VALUES
    ('user1@gmail.com','hello1@','Ivan', 'Matoshka', '2023-08-01', 0, '+375333856386', 'Rokossovskogo', '44', '777');