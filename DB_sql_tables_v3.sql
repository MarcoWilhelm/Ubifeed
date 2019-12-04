DROP SCHEMA  if exists ubifeed;
CREATE SCHEMA ubifeed;

CREATE TABLE ubifeed.countries (
    country_id     SERIAL PRIMARY KEY,
	  nme   VARCHAR(30) NOT NULL
);

CREATE TABLE ubifeed.cities (
    city_id                SERIAL PRIMARY KEY,
    nme                    VARCHAR(30) NOT NULL,
    country_id             INT NOT NULL REFERENCES ubifeed.countries (country_id)
);

CREATE TABLE ubifeed.venues_images (
    ven_img_id    SERIAL PRIMARY KEY,
    image         VARCHAR(60),
    venue_id      INT NOT NULL REFERENCES ubifeed.venues(venue_id)
);

CREATE TABLE ubifeed.venues (
    venue_id            SERIAL PRIMARY KEY,
    nme                 VARCHAR(30) NOT NULL,
    address             VARCHAR(50) NOT NULL,
    image_path			VARCHAR(50) NOT NULL,
    city_id      		INT NOT NULL REFERENCES ubifeed.cities(city_id)
);
/*
CREATE TABLE ubifeed.seat_categories (
	seat_cat_id			SERIAL PRIMARY KEY,
    cat_name			VARCHAR(30) NOT NULL,
    venue_id			INT NOT NULL REFERENCES ubifeed.venues(venue_id)
);
*/
CREATE TABLE ubifeed.events_images (
    ev_img_id    SERIAL PRIMARY KEY,
    image         VARCHAR(60),
    event_id     INT NOT NULL REFERENCES ubifeed.events_(event_id)
);

CREATE TABLE ubifeed.events_ (
	event_id	SERIAL PRIMARY KEY,
    nme			VARCHAR(30) NOT NULL,
    dte			DATE NOT NULL,
    venue_id	INT NOT NULL REFERENCES ubifeed.venues(venue_id)
);

CREATE TABLE ubifeed.venues_events (
    venue_id        	INT NOT NULL REFERENCES ubifeed.venues (venue_id),
    event_id			INT NOT NULL REFERENCES ubifeed.events_(event_id),
    CONSTRAINT order_meal_PK PRIMARY KEY(venue_id, event_id)
);

CREATE TABLE ubifeed.meals_categories (
    meal_categ_id     SERIAL PRIMARY KEY,
    nme   			  VARCHAR(30) NOT NULL
);


CREATE TABLE ubifeed.restaurants_images (
    rest_img_id   SERIAL PRIMARY KEY,
    image    	  VARCHAR(60)
);

CREATE TABLE ubifeed.restaurants (
    rest_id                          SERIAL PRIMARY KEY,
    nme                              VARCHAR(30) NOT NULL,
    address                          VARCHAR(50) NOT NULL,
    descrip                 		 VARCHAR(60) NOT NULL,
    email                            VARCHAR(30) NOT NULL,
    passw                            VARCHAR(60) NOT NULL,
    venue_id      					 INT NOT NULL REFERENCES ubifeed.venues(venue_id),
    rest_img_id                      INT REFERENCES ubifeed.restaurants_images(rest_img_id));

CREATE TABLE ubifeed.meals (
    meal_id                          SERIAL PRIMARY KEY,
    nme                        		 VARCHAR(30) NOT NULL,
    price                       	 FLOAT NOT NULL,
    image                            VARCHAR(60),
    isDeleted						 BOOL DEFAULT 0,
    rest_id                          INT NOT NULL REFERENCES ubifeed.restaurants(rest_id) ,
    meal_categ_id                    INT NOT NULL REFERENCES ubifeed.meals_categories(meal_categ_id)
);

CREATE TABLE ubifeed.pickup_stations (
    pickup_id         	SERIAL PRIMARY KEY,
    email      			VARCHAR(30) NOT NULL,
    passw   			VARCHAR(60) NOT NULL,
    loc_description		VARCHAR(100) NOT NULL,

    -- seat_cat_id			INT NOT NULL REFERENCES ubifeed.seat_categories(seat_cat_id),

    nme					VARCHAR(100) NOT NULL
);
CREATE TABLE ubifeed.seat_categories (
	seat_cat_id			SERIAL PRIMARY KEY,
    cat_name			VARCHAR(30) NOT NULL,
    venue_id			INT NOT NULL REFERENCES ubifeed.venues(venue_id),
    pickup_id			INT NOT NULL REFERENCES ubifeed.pickup_stations(pickup_id)

);

CREATE TABLE ubifeed.users (
    user_id                SERIAL PRIMARY KEY,
    firstn                 VARCHAR(30) NOT NULL,
    lastn                  VARCHAR(30) NOT NULL,
    passw                  VARCHAR(60) NOT NULL,
    email                  VARCHAR(30) NOT NULL,
    phone                  VARCHAR(15) NOT NULL,
    image                  VARCHAR(60)
);

CREATE TABLE ubifeed.orders (
    order_id                    SERIAL PRIMARY KEY,
    user_id               		INT NOT NULL REFERENCES ubifeed.users(user_id),
    rest_id         			INT NOT NULL REFERENCES ubifeed.restaurants(rest_id),
    pickup_id   				INT NOT NULL REFERENCES ubifeed.pickup_stations(pick_id),
    order_status				VARCHAR(50) NOT NULL DEFAULT 'ORDERED'
);

CREATE TABLE ubifeed.order_meals (
    quantity        INT,
    price           DOUBLE,
    order_id        INT NOT NULL REFERENCES ubifeed.orders (order_id),
    meal_id			INT NOT NULL REFERENCES ubifeed.meals(meal_id),
    CONSTRAINT order_meal_PK PRIMARY KEY(order_id, meal_id)
);

INSERT INTO ubifeed.countries(country_id, nme) VALUES (DEFAULT, 'Ireland');

INSERT INTO ubifeed.cities(city_id, nme, country_id) VALUES (DEFAULT, 'Dublin', 1);

INSERT INTO ubifeed.venues (venue_id, nme, address, city_id, image_path) VALUES 
(DEFAULT, "Croke park", "Dublin 3", 1, '../../../../assets/img/crokepark.jpg');
INSERT INTO ubifeed.venues (venue_id, nme, address, city_id, image_path) VALUES 
(DEFAULT, "Aviva Stadium", "Dublin 4", 1, '../../../../assets/img/aviva.jpg');
INSERT INTO ubifeed.venues (venue_id, nme, address, city_id, image_path) VALUES 
(DEFAULT, 'Dalymount Park', 'Phibsborough, Dublin 7', 1, '../../../../assets/img/dalymount.png');
-- INSERT INTO ubifeed.venues (venue_id, nme, address, city_id, image_path) VALUES 
-- (DEFAULT, "Pàirc Uì Chaoimh", "Ballintemple", 2, '../../../../assets/img/pairc.jpg');
-- INSERT INTO ubifeed.venues (venue_id, nme, address, city_id, image_path) VALUES 
-- (DEFAULT, "Stadio Olimpico", "Viale dei Gladiatori", 3, '../../../../assets/img/olimpico.jpg');

INSERT INTO ubifeed.restaurants (rest_id, nme, address, descrip, email, passw, venue_id, rest_img_id) VALUES 
(DEFAULT, 'McDonalds', 'Sector A', 'Fast Food', 'test1@restaurant.com', '$2a$12$641VfNdBN0HMMKwTYEP51.ixcc9k0SxFQmBge.yUpWXWEmoJlkjJG', 1, NULL);
INSERT INTO ubifeed.restaurants (rest_id, nme, address, descrip, email, passw, venue_id, rest_img_id) VALUES 
(DEFAULT, 'Burger King', 'Sector B', 'Fast Food', 'test2@restaurant.com', '$2a$12$RsPGM94m5F7FQTPGjYxWT.o/YD0wyaGMOTBpq/BIC2eMMvvTWlnYq', 1, NULL);
INSERT INTO ubifeed.restaurants (rest_id, nme, address, descrip, email, passw, venue_id, rest_img_id) VALUES 
(DEFAULT, 'Dall Italia Pastabar', 'Sector C', 'Italian Food, Pasta, Pizza', 'test3@restaurant.com', '$2a$12$PqEjMtP1XAxy0LrJEMdJOuE3BdZEexnwh84bdCx7JCVa8W395h5u6', 1, NULL);
INSERT INTO ubifeed.restaurants (rest_id, nme, address, descrip, email, passw, venue_id, rest_img_id) VALUES
(DEFAULT, "Grannyfood", "somewhere else", 'kind of traditional', 'test4@restaurant.com', '$2a$12$MI1HsyiFzzxM48h5RY2KruF78p8U6X9nq1stwpUvAKEEDm8zRdDca', 1, NULL);
INSERT INTO ubifeed.restaurants (rest_id, nme, address, descrip, email, passw, venue_id, rest_img_id) VALUES
(DEFAULT, "SpaghettiPizza", "a place", 'italian stereotypes', 'test5@restaurant.com', '$2a$12$S8tZXD8cfhenDUzHzpNvS.iX0JiLrsyTozAMUCTZt7AkmT5/fj6Pe', 2, NULL);
INSERT INTO ubifeed.restaurants (rest_id, nme, address, descrip, email, passw, venue_id, rest_img_id) VALUES
(DEFAULT, "Il Legionario", "a place", 'Good Food', 'test6@restaurant.com', '$2a$12$U9ixOfSXKyg0mHD.5rqKs.k8TAPWZcrRMvyG6qIYH3aOQPNqpaNZi', 2, NULL);

INSERT INTO ubifeed.meals_categories (meal_categ_id, nme) VALUES 
(DEFAULT, 'Food');
INSERT INTO ubifeed.meals_categories (meal_categ_id, nme) VALUES 
(DEFAULT, 'Drinks');

INSERT INTO ubifeed.meals (meal_id, nme, price, image, rest_id, meal_categ_id) VALUES 
(DEFAULT, 'Hamburger', 5.20, null, 1, 1);
INSERT INTO ubifeed.meals (meal_id, nme, price, image, rest_id, meal_categ_id) VALUES 
(DEFAULT, 'Cheeseburger', 4.30, null, 1, 1);
INSERT INTO ubifeed.meals (meal_id, nme, price, image, rest_id, meal_categ_id) VALUES 
(DEFAULT, 'Hot Dog', 6.80, null, 1, 1);
INSERT INTO ubifeed.meals (meal_id, nme, price, image, rest_id, meal_categ_id) VALUES 
(DEFAULT, 'Coca Cola', 2.50, null, 1, 2);
INSERT INTO ubifeed.meals (meal_id, nme, price, image, rest_id, meal_categ_id) VALUES 
(DEFAULT, 'Fanta', 2.50, null, 1, 2);
INSERT INTO ubifeed.meals (meal_id, nme, price, image, rest_id, meal_categ_id) values
(DEFAULT, "Coke", 2.00, null, 1, 2);
INSERT INTO ubifeed.meals (meal_id, nme, price, image, rest_id, meal_categ_id) values
(DEFAULT, "Big Mac", 6.70, null, 1, 1);
INSERT INTO ubifeed.meals (meal_id, nme, price, image, rest_id, meal_categ_id) values
(DEFAULT, "Pizza Margherita", 7.35, null, 3, 1);
INSERT INTO ubifeed.meals (meal_id, nme, price, image, rest_id, meal_categ_id) values
(DEFAULT, "Fish & Chips", 12.45, null, 2, 1);

INSERT INTO ubifeed.pickup_stations (pickup_id, email, passw, loc_description, nme) VALUES 
(DEFAULT, 'test1@station.com', '$2a$12$qKAJXUXOCY0EM0/mmTXHhO8LT0zML2M0w/f6JFwByFUATRgZf0Mqu', 'At the entrance to Sektor A', 'Station A');
INSERT INTO ubifeed.pickup_stations (pickup_id, email, passw, loc_description, nme) VALUES 
(DEFAULT, 'test2@station.com', '$2a$12$miPEhip/zvpGSDXrDi7yxOlX8ggFgwP5bNq8xW.Vr6/cuF/3Po8Ai', 'At the entrance to Sektor B', 'Station B');
INSERT INTO ubifeed.pickup_stations (pickup_id, email, passw, loc_description, nme) VALUES 
(DEFAULT, 'test3@station.com', '$2a$12$TTI7Py9HQ6TUH5Ccyhxh9.dQ9CSrT/cqt6QT2wLxuBnjHjdq7LN2.', 'At the entrance to Sektor C', 'Station C');
INSERT INTO ubifeed.pickup_stations (pickup_id, email, passw, loc_description, nme) VALUES 
(DEFAULT, 'test4@station.com', '$2a$12$TTI7Py9HQ6TUH5Ccyhxh9.dQ9CSrT/cqt6QT2wLxuBnjHjdq7LN2.', 'At the entrance to Sektor D', 'Station D');

INSERT INTO ubifeed.seat_categories (seat_cat_id, cat_name, venue_id, pickup_id) VALUES 
(DEFAULT, 'Stand A', 1, 3);
INSERT INTO ubifeed.seat_categories (seat_cat_id, cat_name, venue_id, pickup_id) VALUES 
(DEFAULT, 'Stand B', 1, 3);
INSERT INTO ubifeed.seat_categories (seat_cat_id, cat_name, venue_id, pickup_id)VALUES 
(DEFAULT, 'Stand C', 1, 1);
INSERT INTO ubifeed.seat_categories (seat_cat_id, cat_name, venue_id, pickup_id) VALUES 
(DEFAULT, 'Stand D', 1, 2);

INSERT INTO ubifeed.users(user_id, firstn, lastn, passw, email, phone, image) VALUES 
(DEFAULT, 'Yann', 'Pollet', '$2a$12$PeA9KrgPyhI91/PVNLJy1eejU8NVUnPC7I4yCiY9KLKuvnSdEUYEy', 'test@user.com', '0123456789', NULL);

INSERT INTO ubifeed.orders (order_id, user_id, rest_id, pickup_id, order_status) VALUES 
(DEFAULT, 1, 1, 1, DEFAULT);
INSERT INTO ubifeed.orders (order_id, user_id, rest_id, pickup_id, order_status) VALUES 
(DEFAULT, 1, 1, 1, DEFAULT);
INSERT INTO ubifeed.orders (order_id, user_id, rest_id, pickup_id, order_status) VALUES 
(DEFAULT, 1, 1, 1, DEFAULT);

INSERT INTO ubifeed.order_meals (quantity, price, order_id, meal_id) VALUES
 (1, 5.4, 1, 1);

INSERT INTO ubifeed.events_ (event_id, nme, dte, venue_id) VALUES
(DEFAULT, "Clapping hands", "2019-12-6", 1);
INSERT INTO ubifeed.events_ (event_id, nme, dte, venue_id) values
(DEFAULT, "Yelling", "2020-1-14", 1);
INSERT INTO ubifeed.events_ (event_id, nme, dte, venue_id) values
(DEFAULT, "Italy vs Ireland", "2020-1-19", 4);

select* from ubifeed.meals;
UPDATE ubifeed.meals SET isDeleted = 1  WHERE meal_id = 6 AND rest_id = 1;


-- INSERT INTO ubifeed.countries(country_id, nme) VALUES (DEFAULT, "Italy");
-- INSERT INTO ubifeed.countries(country_id, nme) VALUES (DEFAULT, "France");

-- INSERT INTO ubifeed.cities(city_id, nme, country_id) VALUES (DEFAULT, "Cork", 1);
-- INSERT INTO ubifeed.cities(city_id, nme, country_id) VALUES (DEFAULT, "Roma", 2);
-- INSERT INTO ubifeed.cities(city_id, nme, country_id) VALUES (DEFAULT, "Paris", 3);








/*
INSERT INTO ubifeed.seat_categories (seat_cat_id, cat_name, venue_id) VALUES 
(DEFAULT, 'Sector A', 1);
INSERT INTO ubifeed.seat_categories (seat_cat_id, cat_name, venue_id) VALUES 
(DEFAULT, 'Sector B', 1);
INSERT INTO ubifeed.seat_categories (seat_cat_id, cat_name, venue_id)VALUES 
(DEFAULT, 'Sektor C', 1);
INSERT INTO ubifeed.seat_categories (seat_cat_id, cat_name, venue_id) VALUES 
(DEFAULT, 'Sektor D', 1);

INSERT INTO ubifeed.pickup_stations (pickup_id, email, passw, loc_description, seat_cat_id) VALUES 
(DEFAULT, 'test1@station.com', '$2a$12$XOZMKEWc2SN92HRMlRFWQ.R.ouq1/hH9uxj/HCxLMx05YqfknGJNe', 'At the entrance to Sektor C', 1);
INSERT INTO ubifeed.pickup_stations (pickup_id, email, passw, loc_description, seat_cat_id) VALUES 
(DEFAULT, 'test2@station.com', '$2a$12$cLDVWH5PvOO5a85wrhIx2eQxN.1ZBfBpzcE9OlZmclteaLQNmNlY2', 'At the entrance to Sektor D', 2);
INSERT INTO ubifeed.pickup_stations (pickup_id, email, passw, loc_description, seat_cat_id) VALUES 
(DEFAULT, 'test3@station.com', '$2a$12$DndGWJjGc0OAFuZwr.f6aekw2lsYy/ngy5xHEIO6ccAMRh9B6T8Mu', 'At the entrance to Sektor B', 3);


-- INSERT INTO ubifeed.users(user_id, firstn, lastn, passw, email, phone, image) VALUES 
-- (DEFAULT, 'abc', 'abc', '$2a$12$PeA9KrgPyhI91/PVNLJy1eejU8NVUnPC7I4yCiY9KLKuvnSdEUYEy', 'test@user.com', '0123456789', NULL);
*/






 








/*========

Use ubifeed;

INSERT INTO countries VALUES (
 	1, 'Ireland'
);

INSERT INTO cities VALUES (
 	1, 'Dublin', 1
);

INSERT INTO venues VALUES (
	1, 'Croke Park', 'Jones Rd, Drumcondra, Dublin 3', 1
);

INSERT INTO venues VALUES (
	2, 'Aviva Stadium', 'Lansdowne Rd, Dublin 4', 1
);

INSERT INTO venues VALUES (
	3, 'Dalymount Park', 'Phibsborough, Dublin 7', 1
);

INSERT INTO restaurants_images VALUES (
	1, 'a'
);

INSERT INTO restaurants VALUES (
	1, 'McDonalds', 'Sektor A', 'Fast Food', 'test@test.com', 'test1234', 1, 1
);

INSERT INTO restaurants VALUES (
	2, 'Burger King', 'Sektor B', 'Fast Food', 'test@test.com', 'test1234', 1, 1
);

INSERT INTO restaurants VALUES (
	3, 'Dall Italia Pastabar', 'Sektor C', 'Italian Food, Pasta, Pizza', 'test@test.com', 'test1234', 1, 1
);

INSERT INTO meals_categories VALUES (
	1, 'Food'
);

INSERT INTO meals_categories VALUES (
	2, 'Drinnks'
);

INSERT INTO meals VALUES (
	1, 'Hamburger', 5.20, null, 1, 1
);

INSERT INTO meals VALUES (
	2, 'Cheeseburger', 4.30, null, 1, 1
);

INSERT INTO meals VALUES (
	3, 'Hot Dog', 6.80, null, 1, 1
);

INSERT INTO meals VALUES (
	4, 'Coca Cola', 2.50, null, 1, 2
);

INSERT INTO meals VALUES (
	5, 'Fanta', 2.50, null, 1, 2
);

INSERT INTO seat_categories VALUES (
	1, 'Sektor A', 1
);

INSERT INTO seat_categories VALUES (
	2, 'Sektor B', 1
);

INSERT INTO seat_categories VALUES (
	3, 'Sektor C', 1
);

INSERT INTO seat_categories VALUES (
	4, 'Sektor D', 1
);

INSERT INTO pickup_stations VALUES (
	1, 'test@test.com', 'test1234', 'At the entrance to Sektor C', 1
);

INSERT INTO pickup_stations VALUES (
	2, 'test@test.com', 'test1234', 'At the entrance to Sektor D', 2
);

INSERT INTO pickup_stations VALUES (
	3, 'test@test.com', 'test1234', 'At the entrance to Sektor B', 3
);

INSERT INTO orders VALUES (
	1, 1, 1, 1, default
);

INSERT INTO orders VALUES (
	2, 1, 1, 1, default
);

INSERT INTO orders VALUES (
	3, 1, 1, 1, default
);

INSERT INTO order_meals VALUES (
	1, 5.4, 1, 1
);





INSERT INTO ubifeed.venues (venue_id, nme, address, city_id) values
(1, "Croke park", "Dublin 3", 1);
INSERT INTO ubifeed.venues (venue_id, nme, address, city_id) values
(2, "Aviva Stadium", "Dublin 4", 1);
INSERT INTO ubifeed.venues (venue_id, nme, address, city_id) values
(3, "Pàirc Uì Chaoimh", "Ballintemple", 2);
INSERT INTO ubifeed.venues (venue_id, nme, address, city_id) values
(4, "Stadio Olimpico", "Viale dei Gladiatori", 3);



INSERT INTO ubifeed.cities(city_id, nme, country_id) values
(1, "Dublin", 1);
INSERT INTO ubifeed.cities(city_id, nme, country_id) values
(2, "Cork", 1);
INSERT INTO ubifeed.cities(city_id, nme, country_id) values
(3, "Roma", 2);
INSERT INTO ubifeed.cities(city_id, nme, country_id) values
(4, "Paris", 3);

INSERT INTO ubifeed.countries(country_id, nme) values
(1, "Ireland");
INSERT INTO ubifeed.countries(country_id, nme) values
(2, "Italy");
INSERT INTO ubifeed.countries(country_id, nme) values
(3, "France");

INSERT INTO ubifeed.restaurants (rest_id, nme, address, descrip, email, passw, venue_id, rest_img_id) values
(1, "McDonalds", "somewhere", 'old shitty mc', 'mcdonald@mcdonald.mc', '1234', 1, 1);
INSERT INTO ubifeed.restaurants (rest_id, nme, address, descrip, email, passw, venue_id, rest_img_id) values
(2, "Grannyfood", "somewhere else", 'kind of traditional', 'grannyfood@granny.ie', '1234', 1, 2);
INSERT INTO ubifeed.restaurants (rest_id, nme, address, descrip, email, passw, venue_id, rest_img_id) values
(3, "SpaghettiPizza", "a place", 'italian stereotypes', 'spPizza@pizza.it', '1234', 2, 3);
INSERT INTO ubifeed.restaurants (rest_id, nme, address, descrip, email, passw, venue_id, rest_img_id) values
(4, "Il Legionario", "a place", 'Good Food', 'legionario@pizza.it', '1234', 4, 4);

SELECT * FROM ubifeed.restaurants;


INSERT INTO ubifeed.events_ (event_id, nme, dte, venue_id) values
(1, "Clapping hands", "2019-12-6", 1);
INSERT INTO ubifeed.events_ (event_id, nme, dte, venue_id) values
(2, "Yelling", "2020-1-14", 1);
INSERT INTO ubifeed.events_ (event_id, nme, dte, venue_id) values
(3, "Italy vs Ireland", "2020-1-19", 4);



INSERT INTO ubifeed.meals (meal_id, nme, price, image, rest_id, meal_categ_id) values
(1, "Coke", 2.00, "image", 1, 1);
INSERT INTO ubifeed.meals (meal_id, nme, price, image, rest_id, meal_categ_id) values
(2, "Big Mac", 6.70, "image", 1, 2);
INSERT INTO ubifeed.meals (meal_id, nme, price, image, rest_id, meal_categ_id) values
(3, "Pizza Margherita", 7.35, "image", 3, 4);
INSERT INTO ubifeed.meals (meal_id, nme, price, image, rest_id, meal_categ_id) values
(4, "Fish & Chips", 12.45, "image", 2, 5);
>>>>>>> 4445754ad42ff4f2ad6332eb5f57558a010845fe

SELECT * FROM ubifeed.meals;

INSERT INTO ubifeed.meals_categories (meal_categ_id, nme) values
(1, "Drink");
INSERT INTO ubifeed.meals_categories (meal_categ_id, nme) values
(2, "Sandwich");
INSERT INTO ubifeed.meals_categories (meal_categ_id, nme) values
(3, "Desserts");
INSERT INTO ubifeed.meals_categories (meal_categ_id, nme) values
(4, "Pizza");
INSERT INTO ubifeed.meals_categories (meal_categ_id, nme) values
(5, "Main Course");
*/