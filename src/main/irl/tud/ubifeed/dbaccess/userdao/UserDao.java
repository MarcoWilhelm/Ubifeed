package irl.tud.ubifeed.dbaccess.userdao;

import java.util.List;

import irl.tud.ubifeed.event.EventDto;
import irl.tud.ubifeed.meal.MealDto;
import irl.tud.ubifeed.restaurant.RestaurantDto;
import irl.tud.ubifeed.user.UserDto;
import irl.tud.ubifeed.venue.VenueDto;

public interface UserDao {
	
	UserDto loginUser(UserDto user);

	UserDto register(UserDto user);
	
	List<VenueDto> getAllVenues();

	List<RestaurantDto> getAllRestaurants(String venueId);
	
	List<MealDto> getMeals(String restaurantId);

	List<EventDto> getEvents(String venueId);
}
