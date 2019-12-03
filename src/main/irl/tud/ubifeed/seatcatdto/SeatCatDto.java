package irl.tud.ubifeed.seatcatdto;

import irl.tud.ubifeed.pickupstation.PickupStationDto;
import irl.tud.ubifeed.venue.VenueDto;

public interface SeatCatDto {
	
	int getSeatCatId();
	
	void setSeatCatId(int seatCatId);
	
	String getName();
	
	void setName(String name);
	
	VenueDto getVenue();
	
	void setVenue(VenueDto venue);

	PickupStationDto getPickupStation();
	
	void setPickupStation(PickupStationDto pickupStation);
}
