package irl.tud.ubifeed.business;

import irl.tud.ubifeed.Inject;
import irl.tud.ubifeed.dbaccess.DalServices;
import irl.tud.ubifeed.dbaccess.restaurantdao.RestaurantDao;

public class RestaurantUccImpl implements RestaurantUcc {
	
	@Inject
	public RestaurantDao restaurantDao;
	
	@Inject
	public DalServices dal;
}
