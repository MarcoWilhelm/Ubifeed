package irl.tud.ubifeed.dbaccess.deliverydao;

import java.util.List;

import irl.tud.ubifeed.order.OrderDto;
import irl.tud.ubifeed.pickupstation.PickupStation;
import irl.tud.ubifeed.pickupstation.PickupStationDto;

public interface DeliveryDao {

	PickupStationDto loginPickupStation(PickupStationDto pickupstation);

	List<OrderDto> getAllOrders();

}
