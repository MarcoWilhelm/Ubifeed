package irl.tud.ubifeed.dbaccess.userdao;

import java.util.List;
import java.util.Map;

import irl.tud.ubifeed.event.EventDto;
import irl.tud.ubifeed.meal.MealDto;
import irl.tud.ubifeed.order.OrderDto;
import irl.tud.ubifeed.pickupstation.PickupStationDto;
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
	
	List<PickupStationDto> getPickupDetails(String venueId);
	
	List<OrderDto> getAllOrders(String userId, String seat_cat_ids);

	void addOrders(Map<MealDto, Long> basket, int restaurantId, int userId, int seatCatId);
}
