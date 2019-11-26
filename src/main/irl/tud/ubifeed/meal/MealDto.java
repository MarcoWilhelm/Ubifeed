package irl.tud.ubifeed.meal;

import java.util.List;

import irl.tud.ubifeed.restaurant.RestaurantDto;

public interface MealDto {
	
	int getMealId();
	
	void setMealId(int mealId);
	
	String getName();
	
	void setName(String name);
	
	RestaurantDto getRestaurant();
	
	void setRestaurant(RestaurantDto restaurant);
	
	List<String> getPictures();
	
	void setPictures(List<String> pictures);
	
	int getQuantity();
	
	void setQuantity(int quantity);
	
	double getPrice();
	
	void setPrice(double price);
	
	String getCategory();
	
	void setCategory(String category);

	int getCategoryId();
	
	void setCategoryId(int mealCategoryId);
	
	
}
