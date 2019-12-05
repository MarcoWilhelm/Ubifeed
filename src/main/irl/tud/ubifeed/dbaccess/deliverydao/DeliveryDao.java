package irl.tud.ubifeed.dbaccess.deliverydao;

import java.util.List;

import irl.tud.ubifeed.meal.MealDto;
import irl.tud.ubifeed.order.OrderDto;
import irl.tud.ubifeed.pickupstation.PickupStation;
import irl.tud.ubifeed.pickupstation.PickupStationDto;

public interface DeliveryDao {

	/**
	 * log in for a pickupStation
	 * @param pickupstation the email and the password
	 * @return the pickupStation if email correct
	 */
	PickupStationDto loginPickupStation(PickupStationDto pickupstation);

	/**
	 * Get all orders for this pickupStation
	 * @param pickupId the pickupStation
	 * @return a list of orders
	 */
	List<OrderDto> getAllOrders(String pickupId);

	/**
	 * Get all meals for an order
	 * @param orderId the orders
	 * @return a list of meals
	 */
	List<MealDto> getMealFromOrder(int orderId);
	
	/**
	 * get the state of an order
	 * @param orderId the order
	 * @return the state
	 */
	public String getOrderStatus(int orderId);
	
	/**
	 * Set the orderStatus to "DELIVERED"
	 * @param orderId the order
	 */
	public void takeOrder(int orderId);
	
	/**
	 * Set the orderStatus to "IN_STATION"
	 * @param orderId the order
	 */
	public void deliverOrder(int orderId);
}
