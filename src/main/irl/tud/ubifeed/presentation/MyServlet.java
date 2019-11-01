package irl.tud.ubifeed.presentation;

import irl.tud.ubifeed.Inject;
import irl.tud.ubifeed.business.DeliveryUcc;
import irl.tud.ubifeed.business.RestaurantUcc;
import irl.tud.ubifeed.business.UserUcc;
import irl.tud.ubifeed.business.modelfactory.ModelFactory;

public class MyServlet {
	
	@Inject
	public UserUcc userUcc;
	
	@Inject
	public RestaurantUcc restaurantUcc;
	
	@Inject
	public DeliveryUcc deliveryUcc;
	
	@Inject
	public ModelFactory factory;
}
