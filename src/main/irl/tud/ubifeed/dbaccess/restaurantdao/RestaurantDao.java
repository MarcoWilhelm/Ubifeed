package irl.tud.ubifeed.dbaccess.restaurantdao;

import java.util.List;

import irl.tud.ubifeed.meal.MealDto;
import irl.tud.ubifeed.order.OrderDto;
import irl.tud.ubifeed.restaurant.Restaurant;
import irl.tud.ubifeed.restaurant.RestaurantDto;

public interface RestaurantDao {

	RestaurantDto loginRestaurant(RestaurantDto restaurant);

	List<OrderDto> getAllOrders(String restaurantId);

	MealDto addMeal(MealDto meal, String restaurantId);

}
