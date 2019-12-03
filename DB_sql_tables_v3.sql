DROP SCHEMA  if exists ubifeed ;
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
    city_id      		INT NOT NULL REFERENCES ubifeed.cities(city_id)
);

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

CREATE TABLE ubifeed.restaurants (
    rest_id                         SERIAL PRIMARY KEY,
    nme                             VARCHAR(30) NOT NULL,
    address                         VARCHAR(50) NOT NULL,
    descrip      					VARCHAR(60) NOT NULL,
    email                           VARCHAR(30) NOT NULL,
    passw                           VARCHAR(60) NOT NULL,
    venue_id      					INT NOT NULL REFERENCES ubifeed.venues(venue_id),
    image    	  					VARCHAR(60) NULL
);
/*
CREATE TABLE ubifeed.restaurants_images (
    rest_img_id   	SERIAL PRIMARY KEY,
    restaurant_id	INT NOT NULL REFERENCES
    image    	  	VARCHAR(60)
);
*/

CREATE TABLE ubifeed.meals (
    meal_id                          SERIAL PRIMARY KEY,
    nme                        		 VARCHAR(30) NOT NULL,
    price                       	 FLOAT NOT NULL,
    image                            VARCHAR(60),
    rest_id                          INT NOT NULL REFERENCES ubifeed.restaurants(rest_id) ,
    meal_categ_id                    INT NOT NULL REFERENCES ubifeed.meals_categories(meal_categ_id)
);

CREATE TABLE ubifeed.pickup_stations (
    pickup_id         	SERIAL PRIMARY KEY,
    email      			VARCHAR(30) NOT NULL,
    passw   			VARCHAR(60) NOT NULL,
    loc_description		VARCHAR(100) NOT NULL
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
    order_status 				VARCHAR(50) NOT NULL DEFAULT 'In Preparation'
);

CREATE TABLE ubifeed.order_meals (
    quantity        INT,
    price           DOUBLE,
    order_id        INT NOT NULL REFERENCES ubifeed.orders (order_id),
    meal_id			INT NOT NULL REFERENCES ubifeed.meals(meal_id),
    CONSTRAINT order_meal_PK PRIMARY KEY(order_id, meal_id)
);

INSERT INTO ubifeed.countries(country_id, nme) VALUES (DEFAULT, 'Ireland');
INSERT INTO ubifeed.countries(country_id, nme) VALUES (DEFAULT, "Italy");
INSERT INTO ubifeed.countries(country_id, nme) VALUES (3, "France");

INSERT INTO ubifeed.cities(city_id, nme, country_id) VALUES (DEFAULT, 'Dublin', 1);
INSERT INTO ubifeed.cities(city_id, nme, country_id) VALUES (DEFAULT, "Cork", 1);
INSERT INTO ubifeed.cities(city_id, nme, country_id) VALUES (DEFAULT, "Roma", 2);
INSERT INTO ubifeed.cities(city_id, nme, country_id) VALUES (DEFAULT, "Paris", 3);

INSERT INTO ubifeed.restaurants (rest_id, nme, address, descrip, email, passw, venue_id, image) VALUES 
(DEFAULT, 'McDonalds', 'Sector A', 'Fast Food', 'test1@restaurant.com', '$2a$12$641VfNdBN0HMMKwTYEP51.ixcc9k0SxFQmBge.yUpWXWEmoJlkjJG', 1, NULL);
INSERT INTO ubifeed.restaurants (rest_id, nme, address, descrip, email, passw, venue_id, image) VALUES 
(DEFAULT, 'Burger King', 'Sector B', 'Fast Food', 'test2@restaurant.com', '$2a$12$RsPGM94m5F7FQTPGjYxWT.o/YD0wyaGMOTBpq/BIC2eMMvvTWlnYq', 1, NULL);
INSERT INTO ubifeed.restaurants (rest_id, nme, address, descrip, email, passw, venue_id, image) VALUES 
(DEFAULT, 'Dall Italia Pastabar', 'Sector C', 'Italian Food, Pasta, Pizza', 'test3@restaurant.com', '$2a$12$PqEjMtP1XAxy0LrJEMdJOuE3BdZEexnwh84bdCx7JCVa8W395h5u6', 1, NULL);
INSERT INTO ubifeed.restaurants (rest_id, nme, address, descrip, email, passw, venue_id, image) VALUES
(DEFAULT, "Grannyfood", "somewhere else", 'kind of traditional', 'test4@restaurant.com', '$2a$12$MI1HsyiFzzxM48h5RY2KruF78p8U6X9nq1stwpUvAKEEDm8zRdDca', 1, NULL);
INSERT INTO ubifeed.restaurants (rest_id, nme, address, descrip, email, passw, venue_id, image) VALUES
(DEFAULT, "SpaghettiPizza", "a place", 'italian stereotypes', 'test5@restaurant.com', '$2a$12$S8tZXD8cfhenDUzHzpNvS.iX0JiLrsyTozAMUCTZt7AkmT5/fj6Pe', 2, NULL);
INSERT INTO ubifeed.restaurants (rest_id, nme, address, descrip, email, passw, venue_id, image) VALUES
(DEFAULT, "Il Legionario", "a place", 'Good Food', 'test6@restaurant.com', '$2a$12$U9ixOfSXKyg0mHD.5rqKs.k8TAPWZcrRMvyG6qIYH3aOQPNqpaNZi', 4, NULL);

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
(DEFAULT, "Coke", 2.00, null, 1, 1);
INSERT INTO ubifeed.meals (meal_id, nme, price, image, rest_id, meal_categ_id) values
(DEFAULT, "Big Mac", 6.70, null, 1, 2);
INSERT INTO ubifeed.meals (meal_id, nme, price, image, rest_id, meal_categ_id) values
(DEFAULT, "Pizza Margherita", 7.35, null, 3, 1);
INSERT INTO ubifeed.meals (meal_id, nme, price, image, rest_id, meal_categ_id) values
(DEFAULT, "Fish & Chips", 12.45, null, 2, 1);


INSERT INTO ubifeed.pickup_stations (pickup_id, email, passw, loc_description) VALUES 
(DEFAULT, 'test1@station.com', '$2a$12$qKAJXUXOCY0EM0/mmTXHhO8LT0zML2M0w/f6JFwByFUATRgZf0Mqu', 'At the entrance to Sektor C');
INSERT INTO ubifeed.pickup_stations (pickup_id, email, passw, loc_description) VALUES 
(DEFAULT, 'test2@station.com', '$2a$12$miPEhip/zvpGSDXrDi7yxOlX8ggFgwP5bNq8xW.Vr6/cuF/3Po8Ai', 'At the entrance to Sektor D');
INSERT INTO ubifeed.pickup_stations (pickup_id, email, passw, loc_description) VALUES 
(DEFAULT, 'test3@station.com', '$2a$12$TTI7Py9HQ6TUH5Ccyhxh9.dQ9CSrT/cqt6QT2wLxuBnjHjdq7LN2.', 'At the entrance to Sektor B');

INSERT INTO ubifeed.seat_categories (seat_cat_id, cat_name, venue_id, pickup_id) VALUES 
(DEFAULT, 'Sector A', 1, 3);
INSERT INTO ubifeed.seat_categories (seat_cat_id, cat_name, venue_id, pickup_id) VALUES 
(DEFAULT, 'Sector B', 1, 3);
INSERT INTO ubifeed.seat_categories (seat_cat_id, cat_name, venue_id, pickup_id)VALUES 
(DEFAULT, 'Sektor C', 1, 1);
INSERT INTO ubifeed.seat_categories (seat_cat_id, cat_name, venue_id, pickup_id) VALUES 
(DEFAULT, 'Sektor D', 1, 2);

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
 INSERT INTO ubifeed.order_meals (quantity, price, order_id, meal_id) VALUES
 (1, 4.3, 1, 2);
  INSERT INTO ubifeed.order_meals (quantity, price, order_id, meal_id) VALUES
 (1, 4.3, 2, 2);

INSERT INTO ubifeed.venues (venue_id, nme, address, city_id) VALUES 
(DEFAULT, "Croke park", "Dublin 3", 1);
INSERT INTO ubifeed.venues (venue_id, nme, address, city_id) VALUES 
(DEFAULT, "Aviva Stadium", "Dublin 4", 1);
INSERT INTO ubifeed.venues (venue_id, nme, address, city_id) VALUES 
(DEFAULT, 'Dalymount Park', 'Phibsborough, Dublin 7', 1);
INSERT INTO ubifeed.venues (venue_id, nme, address, city_id) VALUES 
(DEFAULT, "Pàirc Uì Chaoimh", "Ballintemple", 2);
INSERT INTO ubifeed.venues (venue_id, nme, address, city_id) VALUES 
(DEFAULT, "Stadio Olimpico", "Viale dei Gladiatori", 3);

INSERT INTO ubifeed.events_ (event_id, nme, dte, venue_id) VALUES
(DEFAULT, "Clapping hands", "2019-12-6", 1);
INSERT INTO ubifeed.events_ (event_id, nme, dte, venue_id) values
(DEFAULT, "Yelling", "2020-1-14", 1);
INSERT INTO ubifeed.events_ (event_id, nme, dte, venue_id) values
(DEFAULT, "Italy vs Ireland", "2020-1-19", 4);


