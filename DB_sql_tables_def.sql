DROP TABLE orders;

DROP TABLE users ;

DROP TABLE restaurants ;

DROP TABLE meals ;

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

CREATE TABLE venues_images (
    ven_img_id    SERIAL PRIMARY KEY,
    venue_image   BLOB NOT NULL
);

CREATE TABLE venues (
    venue_id                   SERIAL PRIMARY KEY,
    venue_name                 VARCHAR(30) NOT NULL,
    venue_address              VARCHAR(50) NOT NULL,
    venue_date                 DATE NOT NULL,
    venues_images_ven_img_id   INT NOT NULL REFERENCES venues_images(ven_img_id),
    cities_city_id             INT NOT NULL REFERENCES cities(city_id)
);

CREATE TABLE meals_categories (
    meal_categ_id     SERIAL PRIMARY KEY,
    meal_categ_name   VARCHAR(30) NOT NULL
);

CREATE TABLE meals_images (
    meal_img_id   SERIAL PRIMARY KEY,
    meal_image    BLOB
);

CREATE TABLE restaurants_images (
    rest_img_id   SERIAL PRIMARY KEY,
    rest_image    BLOB
);

CREATE TABLE restaurants (
    rest_id                          SERIAL PRIMARY KEY,
    restaurant_name                  VARCHAR(30) NOT NULL,
    rest_address                     VARCHAR(50) NOT NULL,
    rest_description                 VARCHAR(30) NOT NULL,
    rest_email                       VARCHAR(30) NOT NULL,
    rest_password                    VARCHAR(30) NOT NULL,
    restaurants_images_rest_img_id   INT NOT NULL REFERENCES restaurants_images(rest_img_id)
);

CREATE TABLE meals (
    meal_id                          SERIAL PRIMARY KEY,
    meal_name                        VARCHAR(30) NOT NULL,
    meal_price                       FLOAT NOT NULL,
    meals_images_meal_img_id         INT NOT NULL REFERENCES meals_images(meal_img_id),
    restaurants_rest_id              INT NOT NULL REFERENCES restaurants(rest_id) ,
    meals_categories_meal_categ_id   INT NOT NULL REFERENCES meals_categories(meal_categ_id)
);

CREATE TABLE pickup_stations (
    pickup_id         SERIAL PRIMARY KEY,
    pickup_email      VARCHAR(30) NOT NULL,
    pickup_password   VARCHAR(30) NOT NULL,
    pickup_phone      INT NOT NULL,
    venues_venue_id   INT NOT NULL REFERENCES venues(venue_id)
);

CREATE TABLE user_images (
    us_img_id    SERIAL PRIMARY KEY,
    user_image   BLOB NOT NULL
);

CREATE TABLE users (
    user_id                 SERIAL PRIMARY KEY,
    first_name              VARCHAR(30) NOT NULL,
    last_name               VARCHAR(30) NOT NULL,
    user_password           VARCHAR(30) NOT NULL,
    user_email              VARCHAR(30) NOT NULL,
    user_phone              INT NOT NULL,
    user_images_us_img_id   INT NOT NULL REFERENCES user_images(us_img_id)
);

CREATE TABLE orders (
    order_id                    SERIAL PRIMARY KEY,
    price                       FLOAT NOT NULL,
    users_user_id               INT NOT NULL REFERENCES users(user_id),
    restaurants_rest_id         INT NOT NULL REFERENCES restaurants(rest_id),
    pickup_stations_pickup_id   INT NOT NULL REFERENCES pickup_stations(pick_id)
);