package irl.tud.ubifeed.business;

import java.util.List;

import at.favre.lib.crypto.bcrypt.BCrypt;
import irl.tud.ubifeed.Inject;
import irl.tud.ubifeed.dbaccess.DalServices;
import irl.tud.ubifeed.dbaccess.userdao.UserDao;
import irl.tud.ubifeed.exception.FatalErrorException;
import irl.tud.ubifeed.event.EventDto;
import irl.tud.ubifeed.meal.MealDto;
import irl.tud.ubifeed.restaurant.RestaurantDto;
import irl.tud.ubifeed.user.User;
import irl.tud.ubifeed.user.UserDto;
import irl.tud.ubifeed.venue.VenueDto;

public class UserUccImpl implements UserUcc {

	@Inject
	public UserDao userDao;

	@Inject
	public DalServices dal;

	@Override
	public UserDto loginUser(UserDto user) {
		User toRet;
		try {

			// init user thanks to UserDao
			dal.startTransaction();
			toRet = (User) userDao.loginUser(user);
			dal.commitTransaction();
		} catch (FatalErrorException dbfExcept) {
			dal.rollbackTransaction();
			throw new FatalErrorException(dbfExcept);
		}

		if (toRet == null || toRet.getEmail() == null || !toRet.verifyPassword(user.getPassword())) {
			return null;
		}
		return toRet;
	}
	@Override
	public UserDto registerUser(UserDto user) {
		try {
			user.setPassword(BCrypt.withDefaults().hashToString(12, user.getPassword().toCharArray()));
			dal.startTransaction();
			user = userDao.register(user);
			dal.commitTransaction();
			return user;
		} catch (FatalErrorException dbfExcept) {
			dal.rollbackTransaction();
			throw new FatalErrorException(dbfExcept);
		}
	}
	@Override
	public List<VenueDto> getAllVenues() {
		List<VenueDto> venue;
		try {
			dal.startTransaction();
			venue = userDao.getAllVenues();
			dal.commitTransaction();
			return venue;
			
		} catch (FatalErrorException dbfExcept) {
			dal.rollbackTransaction();
			throw new FatalErrorException(dbfExcept);
		}
	}
	
	@Override
	public List<RestaurantDto> getAllRestaurants(String venueId) {
		List<RestaurantDto> restaurant;
		try {	
			dal.startTransaction();
			restaurant = userDao.getAllRestaurants(venueId);
			dal.commitTransaction();
			return restaurant;
		
		} catch (FatalErrorException dbfExcept) {
			dal.rollbackTransaction();
			throw new FatalErrorException(dbfExcept);
		}
	}
	@Override
	public List<EventDto> getEvents(String venueId) {
		List<EventDto> event;
		try {	
			dal.startTransaction();
			event = userDao.getEvents(venueId);
			dal.commitTransaction();
			return event;
		
		} catch (FatalErrorException dbfExcept) {
			dal.rollbackTransaction();
			throw new FatalErrorException(dbfExcept);
		}
	}
	@Override
	public List<MealDto> getMeals(String restaurantId) {
		List<MealDto> meal;
		try {	
			dal.startTransaction();
			meal = userDao.getMeals(restaurantId);
			dal.commitTransaction();
			return meal;
		
		} catch (FatalErrorException dbfExcept) {
			dal.rollbackTransaction();
			throw new FatalErrorException(dbfExcept);
		}
	}
	
	
}
