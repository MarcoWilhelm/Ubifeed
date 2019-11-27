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

CREATE TABLE ubifeed.seat_categories (
	seat_cat_id			SERIAL PRIMARY KEY,
    cat_name			VARCHAR(30) NOT NULL,
    venue_id			INT NOT NULL REFERENCES ubifeed.venues(venue_id)
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


CREATE TABLE ubifeed.restaurants_images (
    rest_img_id   SERIAL PRIMARY KEY,
    image    	  VARCHAR(60)
);

CREATE TABLE ubifeed.restaurants (
    rest_id                          SERIAL PRIMARY KEY,
    nme                              VARCHAR(30) NOT NULL,
    address                          VARCHAR(50) NOT NULL,
    descrip                 		     VARCHAR(60) NOT NULL,
    email                            VARCHAR(30) NOT NULL,
    passw                            VARCHAR(60) NOT NULL,
    venue_id      					         INT NOT NULL REFERENCES ubifeed.venues(venue_id),
    rest_img_id                      INT NOT NULL REFERENCES ubifeed.restaurants_images(rest_img_id));

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
    loc_description		VARCHAR(100) NOT NULL,
    seat_cat_id			INT NOT NULL REFERENCES ubifeed.seat_categories(seat_cat_id)
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
    pickup_id   				INT NOT NULL REFERENCES ubifeed.pickup_stations(pick_id)
);

ALTER TABLE ubifeed.orders ADD (
	order_status VARCHAR(50) NOT NULL DEFAULT 'In Preperation'
);

CREATE TABLE ubifeed.order_meals (
    quantity        INT,
    price           DOUBLE,
    order_id        INT NOT NULL REFERENCES ubifeed.orders (order_id),
    meal_id			INT NOT NULL REFERENCES ubifeed.meals(meal_id),
    CONSTRAINT order_meal_PK PRIMARY KEY(order_id, meal_id)
);


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
