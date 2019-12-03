package irl.tud.ubifeed.business;

import java.util.List;

import at.favre.lib.crypto.bcrypt.BCrypt;
import irl.tud.ubifeed.Inject;
import irl.tud.ubifeed.dbaccess.DalServices;
import irl.tud.ubifeed.dbaccess.userdao.UserDao;
import irl.tud.ubifeed.event.EventDto;
import irl.tud.ubifeed.exception.FatalErrorException;
import irl.tud.ubifeed.meal.MealDto;
import irl.tud.ubifeed.order.OrderDto;
import irl.tud.ubifeed.pickupstation.PickupStationDto;
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
		User toRet = null;
		try {

			// init user thanks to UserDao
			dal.startTransaction();
			toRet = (User) userDao.loginUser(user);
			dal.commitTransaction();
		} catch (Exception dbfExcept) {
			dal.rollbackTransaction();
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
		} catch (Exception dbfExcept) {
			dal.rollbackTransaction();
		}
		return user;
	}
	@Override
	public List<VenueDto> getAllVenues() {
		List<VenueDto> venue = null;
		try {
			dal.startTransaction();
			venue = userDao.getAllVenues();
			dal.commitTransaction();			
		} catch (Exception dbfExcept) {
			dal.rollbackTransaction();
		}
		return venue;
	}
	
	@Override
	public List<RestaurantDto> getAllRestaurants(String venueId) {
		List<RestaurantDto> restaurant = null;
		try {	
			dal.startTransaction();
			restaurant = userDao.getAllRestaurants(venueId);
			dal.commitTransaction();		
		} catch (Exception dbfExcept) {
			dal.rollbackTransaction();
		}
		return restaurant;
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
		List<MealDto> meal = null;
		try {	
			dal.startTransaction();
			meal = userDao.getMeals(restaurantId);
			dal.commitTransaction();		
		} catch (Exception dbfExcept) {
			dal.rollbackTransaction();
		}
		return meal;
	}
	
	@Override
	public List<PickupStationDto> getPickupDetails(String venueId) {
		List<PickupStationDto> details = null;
		try {
			dal.startTransaction();
			details = userDao.getPickupDetails(venueId);
			dal.commitTransaction();
		} catch (Exception dbfExcept) {
			dal.rollbackTransaction();
		}
		return details;
	}
	
	@Override
	public List<OrderDto> getAllOrders(String userId, String seat_cat_id) {
		List<OrderDto> orders = null;
		try {
			dal.startTransaction();
			orders = userDao.getAllOrders(userId, seat_cat_id);
			dal.commitTransaction();
		} catch(Exception dbfExcept) {
			dal.rollbackTransaction();
		}
		return orders;
	}
	
	@Override
	public void addOrder(String foodbasket, String drinksbasket, String restaurantId, String userId, String seatCatId) {
		try {
			dal.startTransaction();
			//orders = userDao.getAllOrders(userId, seat_cat_id);
			dal.commitTransaction();
		} catch(Exception dbfExcept) {
			dal.rollbackTransaction();
		}
	}
}
