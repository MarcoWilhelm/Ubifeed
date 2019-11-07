package irl.tud.ubifeed.pickupstation;

import irl.tud.ubifeed.venue.VenueDto;

public class PickupStationImpl implements PickupStation {
	
	private int pickupId;
	private String phone;
	private VenueDto venue;
	private String password;
	
	@Override
	public int getPickupId() {
		return pickupId;
	}
	@Override
	public void setPickupId(int pickupId) {
		this.pickupId = pickupId;
	}
	@Override
	public String getPhone() {
		return phone;
	}
	@Override
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Override
	public VenueDto getVenue() {
		return venue;
	}
	@Override
	public void setVenue(VenueDto venue) {
		this.venue = venue;
	}
	@Override
	public String getPassword() {
		return password;
	}
	@Override
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public boolean verifyPassword(String password) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
