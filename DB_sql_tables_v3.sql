DROP SCHEMA  if exists ubifeed_test ;
CREATE SCHEMA ubifeed_test;

CREATE TABLE ubifeed_test.countries (
    country_id     SERIAL PRIMARY KEY,
	  nme   VARCHAR(30) NOT NULL
);

CREATE TABLE ubifeed_test.cities (
    city_id                SERIAL PRIMARY KEY,
    nme                    VARCHAR(30) NOT NULL,
    country_id             INT NOT NULL REFERENCES ubifeed_test.countries (country_id)
);

CREATE TABLE ubifeed_test.venues_images (
    ven_img_id    SERIAL PRIMARY KEY,
    image         VARCHAR(60),
    venue_id      INT NOT NULL REFERENCES ubifeed_test.venues(venue_id)
);

CREATE TABLE ubifeed_test.venues (
    venue_id            SERIAL PRIMARY KEY,
    nme                 VARCHAR(30) NOT NULL,
    address             VARCHAR(50) NOT NULL,
    city_id      		INT NOT NULL REFERENCES ubifeed_test.cities(city_id)
);

CREATE TABLE ubifeed_test.seat_categories (
	seat_cat_id			SERIAL PRIMARY KEY,
    cat_name			VARCHAR(30) NOT NULL,
    venue_id			INT NOT NULL REFERENCES ubifeed_Test.venues(venue_id)
);

CREATE TABLE ubifeed_test.events_images (
    ev_img_id    SERIAL PRIMARY KEY,
    image         VARCHAR(60),
    event_id     INT NOT NULL REFERENCES ubifeed_test.events_(event_id)
);

CREATE TABLE ubifeed_test.events_ (
	event_id	SERIAL PRIMARY KEY,
    nme			VARCHAR(30) NOT NULL,
    dte			DATE NOT NULL,
    venue_id	INT NOT NULL REFERENCES ubifeed_test.venues(venue_id)
);

CREATE TABLE ubifeed_test.venues_events (
    venue_id        	INT NOT NULL REFERENCES ubifeed_test.venues (venue_id),
    event_id			INT NOT NULL REFERENCES ubifeed_test.events_(event_id),
    CONSTRAINT order_meal_PK PRIMARY KEY(venue_id, event_id)
);

CREATE TABLE ubifeed_test.meals_categories (
    meal_categ_id     SERIAL PRIMARY KEY,
    nme   			  VARCHAR(30) NOT NULL
);


CREATE TABLE ubifeed_test.restaurants_images (
    rest_img_id   SERIAL PRIMARY KEY,
    image    	  VARCHAR(60)
);

CREATE TABLE ubifeed_test.restaurants (
    rest_id                          SERIAL PRIMARY KEY,
    nme                              VARCHAR(30) NOT NULL,
    address                          VARCHAR(50) NOT NULL,
    descrip                 		     VARCHAR(60) NOT NULL,
    email                            VARCHAR(30) NOT NULL,
    passw                            VARCHAR(60) NOT NULL,
    venue_id      					         INT NOT NULL REFERENCES ubifeed_test.venues(venue_id),
    rest_img_id                      INT NOT NULL REFERENCES ubifeed_test.restaurants_images(rest_img_id));

CREATE TABLE ubifeed_test.meals (
    meal_id                          SERIAL PRIMARY KEY,
    nme                        		 VARCHAR(30) NOT NULL,
    price                       	 FLOAT NOT NULL,
    image                            VARCHAR(60),
    rest_id                          INT NOT NULL REFERENCES ubifeed_test.restaurants(rest_id) ,
    meal_categ_id                    INT NOT NULL REFERENCES ubifeed_test.meals_categories(meal_categ_id)
);

CREATE TABLE ubifeed_test.pickup_stations (
    pickup_id         	SERIAL PRIMARY KEY,
    email      			VARCHAR(30) NOT NULL,
    passw   			VARCHAR(60) NOT NULL,
    loc_description		VARCHAR(100) NOT NULL,
    seat_cat_id			INT NOT NULL REFERENCES ubifeed_test.seat_categories(seat_cat_id)
);

CREATE TABLE ubifeed_test.users (
    user_id                SERIAL PRIMARY KEY,
    firstn                 VARCHAR(30) NOT NULL,
    lastn                  VARCHAR(30) NOT NULL,
    passw                  VARCHAR(60) NOT NULL,
    email                  VARCHAR(30) NOT NULL,
    phone                  VARCHAR(15) NOT NULL,
    image                  VARCHAR(60)
);

CREATE TABLE ubifeed_test.orders (
    order_id                    SERIAL PRIMARY KEY,
    user_id               		INT NOT NULL REFERENCES ubifeed_test.users(user_id),
    rest_id         			INT NOT NULL REFERENCES ubifeed_test.restaurants(rest_id),
    pickup_id   				INT NOT NULL REFERENCES ubifeed_test.pickup_stations(pick_id)
);

CREATE TABLE ubifeed_test.order_meals (
    quantity        INT,
    price           DOUBLE,
    order_id        INT NOT NULL REFERENCES ubifeed_test.orders (order_id),
    meal_id			INT NOT NULL REFERENCES ubifeed_test.meals(meal_id),
    CONSTRAINT order_meal_PK PRIMARY KEY(order_id, meal_id)
)
