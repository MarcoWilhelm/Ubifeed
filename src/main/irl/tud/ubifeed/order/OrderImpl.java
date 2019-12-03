package irl.tud.ubifeed.order;

import java.util.List;

import irl.tud.ubifeed.meal.MealDto;
import irl.tud.ubifeed.pickupstation.PickupStationDto;
import irl.tud.ubifeed.restaurant.RestaurantDto;
import irl.tud.ubifeed.user.UserDto;

public class OrderImpl implements OrderDto {
	
	private int orderId;
	private double price;
	private UserDto user;
	private PickupStationDto pickupStation;
	private List<MealDto> meals;
	private int userId;
	private RestaurantDto restaurant;
	private int pickupId;
	private String orderStatus;
	
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
	public int getUserId() {
		return this.userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public RestaurantDto getRestaurant() {
		return this.restaurant;
	}
	public void setRestaurant(RestaurantDto restaurant) {
		this.restaurant = restaurant;
	}
	public String getOrderStatus() {
		return this.orderStatus;
	}
	public void setOrderStatus(String status) {
		this.orderStatus = status;
	}
}
