package irl.tud.ubifeed.order;

import java.util.List;

import irl.tud.ubifeed.meal.MealDto;
import irl.tud.ubifeed.pickupstation.PickupStationDto;
import irl.tud.ubifeed.restaurant.RestaurantDto;
import irl.tud.ubifeed.user.UserDto;

public interface OrderDto {
	
	int getOrderId();

	void setOrderId(int orderId);

	double getPrice();

	void setPrice(double price);

	UserDto getUser();

	void setUser(UserDto user);

	PickupStationDto getPickupStation();

	void setPickupStation(PickupStationDto pickupStation);

	List<MealDto> getMeals();

	void setMeals(List<MealDto> meals);
		
	RestaurantDto getRestaurant();
	
	void setRestaurant(RestaurantDto restaurant);
	
	String getOrderStatus();
	
	void setOrderStatus(String status);

}
