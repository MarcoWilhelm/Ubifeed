package irl.tud.ubifeed.pickupstation;

import irl.tud.ubifeed.venue.VenueDto;

public interface PickupStationDto {

	int getPickupId();

	void setPickupId(int pickupId);
	
	String getEmail();
	
	void setEmail(String email);

	String getPassword();

	void setPassword(String password);
	
	String getLocationDescription();
	
	void setLocationDescription(String description);
	
	int getSeatCategoryId();
	
	void setSeatCategoryId(int id);
	
	String getSeatCategoryName();
	
	void setSeatCategoryName(String name);
	
	String getName();
	
	void setName(String name);

}
