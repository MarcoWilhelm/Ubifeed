package irl.tud.ubifeed.business;

import java.util.List;

import irl.tud.ubifeed.order.OrderDto;
import irl.tud.ubifeed.pickupstation.PickupStationDto;

public interface DeliveryUcc {

	PickupStationDto loginPickupStation(PickupStationDto pickupstation);

	List<OrderDto> getAllOrders(String pickupId);

}
