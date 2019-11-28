package irl.tud.ubifeed.business;

import java.util.List;

import irl.tud.ubifeed.Inject;
import irl.tud.ubifeed.dbaccess.DalServices;
import irl.tud.ubifeed.dbaccess.restaurantdao.RestaurantDao;
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
}
