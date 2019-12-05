package irl.tud.ubifeed.business;

import java.util.List;

import irl.tud.ubifeed.order.OrderDto;
import irl.tud.ubifeed.pickupstation.PickupStationDto;

public interface DeliveryUcc {

	/**
	 * log in for a pickupStation
	 * @param pickupstation the email and the password
	 * @return the pickupStation if logged in
	 */
	PickupStationDto loginPickupStation(PickupStationDto pickupstation);

	/**
	 * Get all orders for this pickupStation
	 * @param pickupId the pickupStation
	 * @return a list of orders
	 */
	List<OrderDto> getAllOrders(String pickupId);

	/**
	 * Set the orderStatus to "DELIVERED"
	 * @param orderId the order
	 */
	void deliverOrder(int orderId);

	/**
	 * Set the orderStatus to "IN_STATION"
	 * @param orderId the order
	 */
	void takeOrder(int orderId);

}
