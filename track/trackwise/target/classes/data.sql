INSERT INTO category (id, category_name) VALUES (1, 'Food') ON DUPLICATE KEY UPDATE category_name = 'Food';
INSERT INTO category (id, category_name) VALUES (2, 'Travel') ON DUPLICATE KEY UPDATE category_name = 'Travel';
INSERT INTO category (id, category_name) VALUES (3, 'Bills') ON DUPLICATE KEY UPDATE category_name = 'Bills';
INSERT INTO category (id, category_name) VALUES (4, 'Shopping') ON DUPLICATE KEY UPDATE category_name = 'Shopping';
INSERT INTO category (id, category_name) VALUES (5, 'Others') ON DUPLICATE KEY UPDATE category_name = 'Others';
