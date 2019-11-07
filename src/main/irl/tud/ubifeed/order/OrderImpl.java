package irl.tud.ubifeed.order;

import java.util.List;

import irl.tud.ubifeed.meal.MealDto;
import irl.tud.ubifeed.pickupstation.PickupStationDto;
import irl.tud.ubifeed.user.UserDto;

public class OrderImpl implements OrderDto {
	
	private int orderId;
	private double price;
	private UserDto user;
	private PickupStationDto pickupStation;
	private List<MealDto> meals;
	
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public UserDto getUser() {
		return user;
	}
	public void setUser(UserDto user) {
		this.user = user;
	}
	public PickupStationDto getPickupStation() {
		return pickupStation;
	}
	public void setPickupStation(PickupStationDto pickupStation) {
		this.pickupStation = pickupStation;
	}
	public List<MealDto> getMeals() {
		return meals;
	}
	public void setMeals(List<MealDto> meals) {
		this.meals = meals;
	}

}
