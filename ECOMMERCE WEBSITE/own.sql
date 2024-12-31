CREATE DATABASE own;
USE own;
SHOW TABLES IN own;
SELECT * FROM product;
SELECT * FROM cart;
SELECT * FROM cart_products;
SELECT * FROM user;
DESCRIBE cart_product;
DESCRIBE product;
DESCRIBE cart;
ALTER TABLE cart_product MODIFY COLUMN product_id INT;
ALTER TABLE cart_product MODIFY COLUMN cart_id INT;
ALTER TABLE cart_product
ADD CONSTRAINT FK_product_id FOREIGN KEY (product_id) REFERENCES product(id);
ALTER TABLE cart_product
ADD CONSTRAINT FK_cart_id FOREIGN KEY (cart_id) REFERENCES cart(id);
DESCRIBE cart_product;
DESCRIBE cart;
DESCRIBE product;
