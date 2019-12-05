package irl.tud.ubifeed.dbaccess.restaurantdao;

import java.util.List;

import irl.tud.ubifeed.meal.MealDto;
import irl.tud.ubifeed.order.OrderDto;
import irl.tud.ubifeed.restaurant.Restaurant;
import irl.tud.ubifeed.restaurant.RestaurantDto;

public interface RestaurantDao {

	/**
	 * log in for a restaurant
	 * @param restaurant the email and the password
	 * @return the pickupStation if exists
	 */
	RestaurantDto loginRestaurant(RestaurantDto restaurant);

	/**
	 * Get all orders for this restaurant
	 * @param restaurantId the restaurant
	 * @return a list of orders
	 */
	List<OrderDto> getAllOrders(String restaurantId);

	/**
	 * add a meal to the database
	 * 
	 * @param meal the meal
	 * @param restaurantId the restaurant
	 * @return the meal if added successfully, else null 
	 */
	MealDto addMeal(MealDto meal, int restaurantId);

	/**
	 * Set is_deleteed to 0
	 * 
	 * @param meal the meal
	 * @param restaurantId the restaurant
	 * @return true if deleted successfully, else false 
	 */
	boolean deleteMeal(int mealId, int restaurantId);

	/**
	 * Set the orderStatus to "IN_PREPARATION"
	 * @param orderId the order
	 */
	void prepareOrder(int orderId);
	/**
	 * Set the orderStatus to "READY"
	 * @param orderId the order
	 */
	void finishOrder(int orderId);


}
