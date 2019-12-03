package irl.tud.ubifeed.pickupstation;

import at.favre.lib.crypto.bcrypt.BCrypt;
import irl.tud.ubifeed.venue.VenueDto;

public class PickupStationImpl implements PickupStation {
	
	private int pickupId;
	private String email;
	private String password;
	private String locationDescription;
	private int seat_cat_id;
	private String seatCategoryName;
	private String name;
	
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
		BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), this.getPassword());
		return result.verified;
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
	@Override
	public String getName() {
		return this.name;
	}
	@Override
	public void setName(String name) {
		this.name = name;
	}
}
