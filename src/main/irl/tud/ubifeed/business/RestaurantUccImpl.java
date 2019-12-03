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
<<<<<<< HEAD
	public MealDto deleteMeal(MealDto meal, String restaurantId) {
		try {
			dal.startTransaction();
			meal = restaurantDao.deleteMeal(meal, restaurantId);
			System.out.println("ucc arrived");
=======
	public void prepareOrder(int orderId) {
		try {
			dal.startTransaction();
			restaurantDao.prepareOrder(orderId);
>>>>>>> 9aaafefff5f65214d023f92a852452fd56715d08
			dal.commitTransaction();
		} catch (Exception dbfExcept) {
			dal.rollbackTransaction();
		}
<<<<<<< HEAD
		return meal;
=======
		
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
		
>>>>>>> 9aaafefff5f65214d023f92a852452fd56715d08
	}
}
