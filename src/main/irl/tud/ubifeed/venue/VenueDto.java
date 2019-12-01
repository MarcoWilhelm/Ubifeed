package irl.tud.ubifeed.venue;

import java.time.LocalDateTime;
import java.util.List;

import irl.tud.ubifeed.country.CityDto;

public interface VenueDto {
	
	int getVenueId();
	
	void setVenueId(int venueId);
	
	CityDto getCity();
	
	void setCity(CityDto city);
	
	String getAddress();
	
	void setAddress(String address);
	
	String getName();
	
	void setName(String name);
	
	LocalDateTime getDate();
	
	void setDate(LocalDateTime date);
	
	String getMap();
	
	void setMap(String map);
	
	List<String> getPictures();
	
	void setPictures(List<String> pictures);
	
	String getCityName();
	
	void setCityName(String cityname);

	String getCountryName();
	
	void setCountryName(String countryname);
	
	String getImagePath();
	
	void setImagePath(String path);

}
