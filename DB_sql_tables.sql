DROP TABLE orders;

DROP TABLE meals ;

DROP TABLE restaurants ;

DROP TABLE users ;

DROP TABLE pickup_stations ;

DROP TABLE venues ;

DROP TABLE cities ;

DROP TABLE countries ;

DROP TABLE meals_categories ;

DROP TABLE meals_images ;

DROP TABLE restaurants_images ;

DROP TABLE user_images ;

DROP TABLE venues_images ;


CREATE TABLE countries (
    country_id     SERIAL PRIMARY KEY,
    country_name   VARCHAR(30) NOT NULL
);

CREATE TABLE cities (
    city_id                SERIAL PRIMARY KEY,
    city_name              VARCHAR(30) NOT NULL,
    countries_country_id   INT NOT NULL REFERENCES countries (country_id)
);


CREATE TABLE meals (
    meal_id                          INT(7) unsigned NOT NULL,
    meal_name                        VARCHAR(30) NOT NULL,
    meal_price                       FLOAT(9, 2) unsigned NOT NULL,
    meals_images_meal_img_id         INT(7) unsigned NOT NULL,
    restaurants_rest_id              INT(7) unsigned NOT NULL,
    meals_categories_meal_categ_id   INT(7) unsigned NOT NULL
);

CREATE UNIQUE INDEX meals__idx ON
    meals (
        meals_categories_meal_categ_id
    ASC );

CREATE UNIQUE INDEX meals__idxv1 ON
    meals (
        meals_images_meal_img_id
    ASC );

CREATE UNIQUE INDEX meals__idxv2 ON
    meals (
        restaurants_rest_id
    ASC );

ALTER TABLE meals ADD CONSTRAINT meals_pk PRIMARY KEY ( meal_id );

CREATE TABLE meals_categories (
    meal_categ_id     INT(7) unsigned NOT NULL,
    meal_categ_name   VARCHAR(30) NOT NULL
);

ALTER TABLE meals_categories ADD CONSTRAINT meals_categories_pk PRIMARY KEY ( meal_categ_id );

CREATE TABLE meals_images (
    meal_img_id   INT(7) unsigned NOT NULL,
    meal_image    BLOB
);

ALTER TABLE meals_images ADD CONSTRAINT meals_images_pk PRIMARY KEY ( meal_img_id );

CREATE TABLE orders (
    order_id                    INT(7) unsigned NOT NULL,
    price                       FLOAT(9, 2) NOT NULL,
    users_user_id               INT(7) unsigned NOT NULL,
    restaurants_rest_id         INT(7) unsigned NOT NULL,
    pickup_stations_pickup_id   INT(7) unsigned NOT NULL
);

ALTER TABLE orders ADD CONSTRAINT orders_pk PRIMARY KEY ( order_id );

CREATE TABLE pickup_stations (
    pickup_id         INT(7) unsigned NOT NULL,
    pickup_email      VARCHAR(30) NOT NULL,
    pickup_password   VARCHAR(30) NOT NULL,
    pickup_phone      INT(15) unsigned NOT NULL,
    venues_venue_id   INT(7) unsigned NOT NULL
);

ALTER TABLE pickup_stations ADD CONSTRAINT pickup_stations_pk PRIMARY KEY ( pickup_id );

CREATE TABLE restaurants (
    rest_id                          INT(7) unsigned NOT NULL,
    restaurant_name                  VARCHAR(30) NOT NULL,
    rest_address                     VARCHAR(50) NOT NULL,
    rest_description                 VARCHAR(30) NOT NULL,
    rest_email                       VARCHAR(30) NOT NULL,
    rest_password                    VARCHAR(30) NOT NULL,
    restaurants_images_rest_img_id   INT(7) unsigned NOT NULL
);

CREATE UNIQUE INDEX restaurants__idx ON
    restaurants (
        restaurants_images_rest_img_id
    ASC );

ALTER TABLE restaurants ADD CONSTRAINT restaurants_pk PRIMARY KEY ( rest_id );

CREATE TABLE restaurants_images (
    rest_img_id   INT(7) unsigned NOT NULL,
    rest_image    BLOB
);

ALTER TABLE restaurants_images ADD CONSTRAINT restaurants_images_pk PRIMARY KEY ( rest_img_id );

CREATE TABLE user_images (
    us_img_id    INT(7) unsigned NOT NULL,
    user_image   BLOB NOT NULL
);

ALTER TABLE user_images ADD CONSTRAINT user_images_pk PRIMARY KEY ( us_img_id );

CREATE TABLE users (
    user_id                 INT(7) unsigned NOT NULL,
    first_name              VARCHAR(30) NOT NULL,
    last_name               VARCHAR(30) NOT NULL,
    user_password           VARCHAR(30) NOT NULL,
    user_email              VARCHAR(30) NOT NULL,
    user_phone              INT(15) unsigned NOT NULL,
    user_images_us_img_id   INT(7) unsigned NOT NULL
);

CREATE UNIQUE INDEX users__idx ON
    users (
        user_images_us_img_id
    ASC );

ALTER TABLE users ADD CONSTRAINT users_pk PRIMARY KEY ( user_id );

CREATE TABLE venues (
    venue_id                   INT(7) unsigned NOT NULL,
    venue_name                 VARCHAR(30) NOT NULL,
    venue_address              VARCHAR(50) NOT NULL,
    venue_date                 DATE NOT NULL,
    venues_images_ven_img_id   INT(7) unsigned NOT NULL,
    cities_city_id             INT(7) unsigned NOT NULL
);

CREATE UNIQUE INDEX venues__idx ON
    venues (
        venues_images_ven_img_id
    ASC );

ALTER TABLE venues ADD CONSTRAINT venues_pk PRIMARY KEY ( venue_id );

CREATE TABLE venues_images (
    ven_img_id    INT(7) unsigned NOT NULL,
    venue_image   BLOB NOT NULL
);

ALTER TABLE venues_images ADD CONSTRAINT venues_images_pk PRIMARY KEY ( ven_img_id );

ALTER TABLE cities
    ADD CONSTRAINT cities_countries_fk FOREIGN KEY ( countries_country_id )
        REFERENCES countries ( country_id );

ALTER TABLE meals
    ADD CONSTRAINT meals_meals_categories_fk FOREIGN KEY ( meals_categories_meal_categ_id )
        REFERENCES meals_categories ( meal_categ_id );

ALTER TABLE meals
    ADD CONSTRAINT meals_meals_images_fk FOREIGN KEY ( meals_images_meal_img_id )
        REFERENCES meals_images ( meal_img_id );

ALTER TABLE meals
    ADD CONSTRAINT meals_restaurants_fk FOREIGN KEY ( restaurants_rest_id )
        REFERENCES restaurants ( rest_id );

ALTER TABLE orders
    ADD CONSTRAINT orders_pickup_stations_fk FOREIGN KEY ( pickup_stations_pickup_id )
        REFERENCES pickup_stations ( pickup_id );

ALTER TABLE orders
    ADD CONSTRAINT orders_restaurants_fk FOREIGN KEY ( restaurants_rest_id )
        REFERENCES restaurants ( rest_id );

ALTER TABLE orders
    ADD CONSTRAINT orders_users_fk FOREIGN KEY ( users_user_id )
        REFERENCES users ( user_id );

ALTER TABLE pickup_stations
    ADD CONSTRAINT pickup_stations_venues_fk FOREIGN KEY ( venues_venue_id )
        REFERENCES venues ( venue_id );

--  ERROR: FK name length exceeds maximum allowed length(30) 
ALTER TABLE restaurants
    ADD CONSTRAINT restaurants_restaurants_images_fk FOREIGN KEY ( restaurants_images_rest_img_id )
        REFERENCES restaurants_images ( rest_img_id );

ALTER TABLE users
    ADD CONSTRAINT users_user_images_fk FOREIGN KEY ( user_images_us_img_id )
        REFERENCES user_images ( us_img_id );

ALTER TABLE venues
    ADD CONSTRAINT venues_cities_fk FOREIGN KEY ( cities_city_id )
        REFERENCES cities ( city_id );

ALTER TABLE venues
    ADD CONSTRAINT venues_venues_images_fk FOREIGN KEY ( venues_images_ven_img_id )
        REFERENCES venues_images ( ven_img_id );
