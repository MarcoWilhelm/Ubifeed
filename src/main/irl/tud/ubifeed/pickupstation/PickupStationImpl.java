package irl.tud.ubifeed.pickupstation;

import irl.tud.ubifeed.venue.VenueDto;

public class PickupStationImpl implements PickupStation {
	
	private int pickupId;
	private String email;
	private String password;
	private String locationDescription;
	private int seat_cat_id;
	private String seatCategoryName;
	
	@Override
	public int getPickupId() {
		return pickupId;
	}
	@Override
	public void setPickupId(int pickupId) {
		this.pickupId = pickupId;
	}
	@Override
	public String getEmail() {
		return this.email;
	}
	@Override
	public void setEmail(String email) {
		this.email = email;
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
	@Override
	public String getLocationDescription() {
		return this.locationDescription;
	}
	@Override
	public void setLocationDescription(String description) {
		this.locationDescription = description;
	}
	@Override
	public int getSeatCategoryId() {
		return this.seat_cat_id;
	}
	@Override
	public void setSeatCategoryId(int id) {
		this.seat_cat_id = id;
	}
	@Override
	public String getSeatCategoryName() {
		return this.seatCategoryName;
	}
	@Override
	public void setSeatCategoryName(String name) {
		this.seatCategoryName = name;
	}
}
