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

CREATE TABLE ubifeed.order_meals (
    quantity        INT,
    price           DOUBLE,
    order_id        INT NOT NULL REFERENCES ubifeed.orders (order_id),
    meal_id			INT NOT NULL REFERENCES ubifeed.meals(meal_id),
    CONSTRAINT order_meal_PK PRIMARY KEY(order_id, meal_id)
)
