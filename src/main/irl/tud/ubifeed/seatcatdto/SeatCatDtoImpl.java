package irl.tud.ubifeed.seatcatdto;

import irl.tud.ubifeed.pickupstation.PickupStationDto;
import irl.tud.ubifeed.venue.VenueDto;

public class SeatCatDtoImpl implements SeatCatDto {
	
	int seatCatId;
	String name;
	VenueDto venue;
	PickupStationDto pickupStation;
	
	@Override
	public int getSeatCatId() {
		return this.seatCatId;
	}
	@Override
	public void setSeatCatId(int seatCatId) {
		this.seatCatId = seatCatId;
	}
	@Override
	public String getName() {
		return this.name;
	}
	@Override
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public VenueDto getVenue() {
		return this.venue;
	}
	@Override
	public void setVenue(VenueDto venue) {
		this.venue = venue;
	}
	@Override
	public PickupStationDto getPickupStation() {
		return this.pickupStation;
	}
	@Override
	public void setPickupStation(PickupStationDto pickupStation) {
		this.pickupStation = pickupStation;
	}
	
	

}
