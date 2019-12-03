package irl.tud.ubifeed.meal;

import java.util.List;

import irl.tud.ubifeed.restaurant.RestaurantDto;

public class MealImpl implements MealDto {

	private int mealId;
	private String name;
	private RestaurantDto restaurant;
	private String pictures;
	private int quantity;
	private double price;
	private String category;
	private int mealCategoryId;
	
	@Override
	public int getMealId() {
		return mealId;
	}
	@Override
	public void setMealId(int mealId) {
		this.mealId = mealId;
	}
	@Override
	public String getName() {
		return name;
	}
	@Override
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public RestaurantDto getRestaurant() {
		return restaurant;
	}
	@Override
	public void setRestaurant(RestaurantDto restaurant) {
		this.restaurant = restaurant;
	}
	@Override
	public String getPictures() {
		return pictures;
	}
	@Override
	public void setPictures(String pictures) {
		this.pictures = pictures;
	}
	@Override
	public int getQuantity() {
		return quantity;
	}
	@Override
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	@Override
	public double getPrice() {
		return price;
	}
	@Override
	public void setPrice(double price) {
		this.price = price;
	}
	@Override
	public String getCategory() {
		return category;
	}
	@Override
	public void setCategory(String category) {
		this.category = category;
	}
	@Override
	public int getCategoryId() {
		return mealCategoryId;
	}
	@Override
	public void setCategoryId(int mealCategoryId) {
		this.mealCategoryId = mealCategoryId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + mealId;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MealImpl other = (MealImpl) obj;
		if (mealId != other.mealId)
			return false;
		return true;
	}
	
	
		
}
