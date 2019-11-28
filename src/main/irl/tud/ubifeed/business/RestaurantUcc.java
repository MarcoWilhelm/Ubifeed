package irl.tud.ubifeed.business;

import java.util.List;

import irl.tud.ubifeed.order.OrderDto;
import irl.tud.ubifeed.restaurant.RestaurantDto;

public interface RestaurantUcc {

	RestaurantDto loginRestaurant(RestaurantDto restaurant);

	List<OrderDto> getAllOrders(String restaurantId);

}
