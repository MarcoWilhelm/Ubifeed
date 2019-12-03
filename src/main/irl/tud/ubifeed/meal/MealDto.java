package irl.tud.ubifeed.meal;

import java.io.Serializable;
import java.util.List;

import irl.tud.ubifeed.restaurant.RestaurantDto;

public interface MealDto extends Serializable {
	
	int getMealId();
	
	void setMealId(int mealId);
	
	String getName();
	
	void setName(String name);
	
	RestaurantDto getRestaurant();
	
	void setRestaurant(RestaurantDto restaurant);
	
	String getPictures();
	
	void setPictures(String pictures);
	
	int getQuantity();
	
	void setQuantity(int quantity);
	
	double getPrice();
	
	void setPrice(double price);
	
	String getCategory();
	
	void setCategory(String category);

	int getCategoryId();
	
	void setCategoryId(int mealCategoryId);
	
	public int hashCode();
	
	public boolean equals(Object obj);
	
	
}
