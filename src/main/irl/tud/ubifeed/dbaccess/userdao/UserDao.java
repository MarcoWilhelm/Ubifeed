package irl.tud.ubifeed.dbaccess.userdao;

import irl.tud.ubifeed.user.UserDto;
import irl.tud.ubifeed.venue.VenueDto;

public interface UserDao {
	
	UserDto loginUser(UserDto user);

	UserDto register(UserDto user);
	
	VenueDto getAllVenues(VenueDto venue);

}
