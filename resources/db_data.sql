insert into users(id, name, username, password, is_active, role) values
(nextval('users_id_seq'), 'Administrator', 'admin', '$2a$08$xDJ4lsYKSRLNmI6txqK2a.08NraFv2326pW/1pILqGFMvrzsnqnXu', true, 'ADMIN'),
(nextval('users_id_seq'), 'Moderator', 'moder', '$2a$08$q5k05Qx5kx0wc07SHgMxYeu1CMNkr65Fp.qhc50zavkmPi5e4.Cxu', true, 'MODER');

insert into categories(id, name) values
(nextval('categories_id_seq'), 'Food'),
(nextval('categories_id_seq'), 'Other');

insert into products(id, category_id, photo_url, name,  description, price) values
(nextval('products_id_seq'), 1, 'https://images.unsplash.com/photo-1565299624946-b28f40a0ae38?w=200', 'Pizza', 'big and delicious pizza', 19),
(nextval('products_id_seq'), 1, 'https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=200', 'Burger', 'burger with lettuce and tomatoes', 9),
(nextval('products_id_seq'), 1, 'https://images.unsplash.com/photo-1544025162-d76694265947?w=200', 'Steak', 'roasted ribs with sliced tomatoes and potatoes', 39);

insert into messages(id, name, description, text) values
(nextval('messages_id_seq'), 'START_MESSAGE', 'Start', 'Online shop :)'),
(nextval('messages_id_seq'), 'ORDER_CREATED_MESSAGE', 'Order', 'Order created.'),
(nextval('messages_id_seq'), 'CART_MESSAGE', 'Cart button', '<b>Cart</b>:

-Name: %PRODUCT_NAME%
-Description: %PRODUCT_DESCRIPTION%

Price, quantity, total:
%PRODUCT_PRICE% $ * %PRODUCT_QUANTITY% pcs. = %PRODUCT_TOTAL_PRICE% $');
