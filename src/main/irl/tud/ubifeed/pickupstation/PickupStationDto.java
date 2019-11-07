package irl.tud.ubifeed.pickupstation;

import irl.tud.ubifeed.venue.VenueDto;

public interface PickupStationDto {

	int getPickupId();

	void setPickupId(int pickupId);

	String getPhone();

	void setPhone(String phone);

	VenueDto getVenue();

	void setVenue(VenueDto venue);

	String getPassword();

	void setPassword(String password);

}
