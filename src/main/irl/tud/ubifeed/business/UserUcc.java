package irl.tud.ubifeed.business;

import java.util.List;

import irl.tud.ubifeed.event.EventDto;
import irl.tud.ubifeed.meal.MealDto;
import irl.tud.ubifeed.order.OrderDto;
import irl.tud.ubifeed.pickupstation.PickupStationDto;
import irl.tud.ubifeed.restaurant.RestaurantDto;
import irl.tud.ubifeed.user.UserDto;
import irl.tud.ubifeed.venue.VenueDto;

/**
 * 
 * @author Yann
 *
 */
public interface UserUcc {

	/**
	 * Gets a User from UserDao and verify his password.
	 * 
	 * @param user DTO with the mail and the password
	 * @return UserDTO if exists and correct and password verified else null.
	 */
	UserDto loginUser(UserDto user);
	
	/**
	 * Register a User in the Database
	 * @param user the user to create
	 * @return the user created
	 */
	UserDto registerUser(UserDto user);
	
	
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
	 * Gets the full menu of restaurant n° restauranId
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
	
	
	List<PickupStationDto> getPickupDetails(String venueId);
	
	List<OrderDto> getAllOrders(String userId, String seat_cat_id);

}
