package irl.tud.ubifeed.dbaccess.userdao;

import java.util.List;
import java.util.Map;

import irl.tud.ubifeed.event.EventDto;
import irl.tud.ubifeed.meal.MealDto;
import irl.tud.ubifeed.order.OrderDto;
import irl.tud.ubifeed.pickupstation.PickupStationDto;
import irl.tud.ubifeed.restaurant.RestaurantDto;
import irl.tud.ubifeed.seatcatdto.SeatCatDto;
import irl.tud.ubifeed.user.UserDto;
import irl.tud.ubifeed.venue.VenueDto;

public interface UserDao {
	
	/**
	 * Gets a User from UserDao and verify his password.
	 * 
	 * @param user DTO with the mail and the password
	 * @return UserDTO if exists else null.
	 */
	UserDto loginUser(UserDto user);

	/**
	 * Register a User in the Database
	 * @param user the user to create
	 * @return the user created
	 */
	UserDto register(UserDto user);
	
	//UserDto changeUser(UserDto user);
	/**
	 * Gets a list of venues stored in the DB
	 * @param venue
	 * @return the list of the venue
	 */
	List<VenueDto> getAllVenues();

	/**
	 * Gets a list of the restaurants present in venue n° venueId
	 * @param venueId
	 * @return the list of restaurants
	 */
	List<RestaurantDto> getAllRestaurants(String venueId);
	
	/**
	 * Gets the full menu of restaurant 
	 * @param restaurantId
	 * @return the menu
	 */
	List<MealDto> getMeals(String restaurantId);

	/**
	 * Gets a list of future events in a selected venue
	 * @param venueId
	 * @return lis of events
	 */
	List<EventDto> getEvents(String venueId);
	
	/**
	 * Get the seat categories of a venue
	 * @param venueId the venue id
	 * @return a list of the seat categories
	 */
	List<SeatCatDto> getPickupDetails(String venueId);
	
	/**
	 * get a list of orders of a user
	 * @param userId the user
	 * @return as list of the orders
	 */
	List<OrderDto> getAllOrders(String userId);

	/**
	 * add an order in the database
	 * @param basket a map of the meals with their quantity
	 * @param restaurantId the restaurant
	 * @param userId the user
	 * @param seatCatId the seat category of the user
	 */
	void addOrders(Map<MealDto, Long> basket, int restaurantId, int userId, int seatCatId);
}
