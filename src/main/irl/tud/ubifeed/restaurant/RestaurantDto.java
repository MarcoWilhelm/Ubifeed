package irl.tud.ubifeed.restaurant;

import java.util.List;

public interface RestaurantDto {
	
	int getRestaurantId();
	
	void setRestaurantId(int restaurantId);
	
	String getName();
	
	void setName(String name);
	
	String getAddress();
	
	void setAddress(String address);
	
	public String getDescription();
	
	public void setDescription(String description);
	
	String getPassword();
	
	void setPassword(String password);
	
	String getEmail();
	
	void setEmail(String email);
	
	String getPhone();
	
	void setPhone(String phone);
	
	List<String> getPictures();
	
	void setPictures(List<String> pictures);

}
