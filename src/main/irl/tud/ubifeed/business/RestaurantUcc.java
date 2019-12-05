package irl.tud.ubifeed.business;

import java.util.List;

import irl.tud.ubifeed.meal.MealDto;
import irl.tud.ubifeed.order.OrderDto;
import irl.tud.ubifeed.restaurant.RestaurantDto;

public interface RestaurantUcc {

	/**
	 * log in for a restaurant
	 * @param restaurant the email and the password
	 * @return the pickupStation if logged in
	 */
	RestaurantDto loginRestaurant(RestaurantDto restaurant);

	/**
	 * Get all orders for this restaurant
	 * @param restaurantId the restaurant
	 * @return a list of orders
	 */
	List<OrderDto> getAllOrders(String restaurantId);

	/**
	 * add a meal to the menu
	 * 
	 * @param meal the meal
	 * @param restaurantId the restaurant
	 * @return the meal if added successfully, else null 
	 */
	MealDto addMeal(MealDto meal, int restaurantId);

	/**
	 * Delete a meal from the menue
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
