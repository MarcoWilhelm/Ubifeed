package irl.tud.ubifeed.dbaccess.deliverydao;

import java.util.List;

import irl.tud.ubifeed.meal.MealDto;
import irl.tud.ubifeed.order.OrderDto;
import irl.tud.ubifeed.pickupstation.PickupStation;
import irl.tud.ubifeed.pickupstation.PickupStationDto;

public interface DeliveryDao {

	PickupStationDto loginPickupStation(PickupStationDto pickupstation);

	List<OrderDto> getAllOrders(String pickupId);

	List<MealDto> getMealFromOrder(int orderId);
	
	public String getOrderStatus(int orderId);
	
	public void takeOrder(int orderId);
	
	public void deliverOrder(int orderId);
}
