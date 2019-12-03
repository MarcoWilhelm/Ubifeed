package irl.tud.ubifeed.business;

import java.util.List;

import at.favre.lib.crypto.bcrypt.BCrypt;
import irl.tud.ubifeed.Inject;
import irl.tud.ubifeed.dbaccess.DalServices;
import irl.tud.ubifeed.dbaccess.restaurantdao.RestaurantDao;
import irl.tud.ubifeed.meal.MealDto;
import irl.tud.ubifeed.order.OrderDto;
import irl.tud.ubifeed.restaurant.Restaurant;
import irl.tud.ubifeed.restaurant.RestaurantDto;
import irl.tud.ubifeed.user.User;

public class RestaurantUccImpl implements RestaurantUcc {
	
	@Inject
	public RestaurantDao restaurantDao;
	
	@Inject
	public DalServices dal;

	@Override
	public RestaurantDto loginRestaurant(RestaurantDto restaurant) {
		Restaurant toRet = null;
		try {

			// init restaurant thanks to RestaurantDao
			dal.startTransaction();
			toRet = (Restaurant) restaurantDao.loginRestaurant(restaurant);
			dal.commitTransaction();
		} catch (Exception dbfExcept) {
			dal.rollbackTransaction();
		}

		if (toRet == null || toRet.getEmail() == null || !toRet.verifyPassword(restaurant.getPassword())) {
			return null;
		}
		return toRet;
	}

	@Override
	public List<OrderDto> getAllOrders(String restaurantId) {
		List<OrderDto> orders = null;
		try {
			dal.startTransaction();
			orders = restaurantDao.getAllOrders(restaurantId);
			dal.commitTransaction();
		} catch(Exception dbfExcept) {
			dal.rollbackTransaction();
		}
		return orders;
	}

	@Override
	public MealDto addMeal(MealDto meal, String restaurantId) {
		try {
			dal.startTransaction();
			meal = restaurantDao.addMeal(meal, restaurantId);
			dal.commitTransaction();
		} catch (Exception dbfExcept) {
			dal.rollbackTransaction();
		}
		return meal;
	}

	@Override
	public void prepareOrder(int orderId) {
		try {
			dal.startTransaction();
			restaurantDao.prepareOrder(orderId);
			dal.commitTransaction();
		} catch (Exception dbfExcept) {
			dal.rollbackTransaction();
		}
		
	}

	@Override
	public void finishOrder(int orderId) {
		try {
			dal.startTransaction();
			restaurantDao.finishOrder(orderId);
			dal.commitTransaction();
		} catch (Exception dbfExcept) {
			dal.rollbackTransaction();
		}
		
	}
}
