package irl.tud.ubifeed.dbaccess.userdao;

import java.util.List;

import irl.tud.ubifeed.restaurant.RestaurantDto;
import irl.tud.ubifeed.user.UserDto;
import irl.tud.ubifeed.venue.VenueDto;

public interface UserDao {
	
	UserDto loginUser(UserDto user);

	UserDto register(UserDto user);
	
	List<VenueDto> getAllVenues();

	List<RestaurantDto> getAllRestaurants();

}
