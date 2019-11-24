package irl.tud.ubifeed.event;

import java.time.LocalDateTime;
import java.util.List;

import irl.tud.ubifeed.venue.VenueDto;


public interface EventDto {
	
	int getEventId();
	
	void setEventId(int eventId);
	
	VenueDto getVenue();
	
	void setVenue(VenueDto venue);
	
	String getName();
	
	void setName(String name);
	
	LocalDateTime getDate();
	
	void setDate(LocalDateTime date);
	
	List<String> getPictures();
	
	void setPictures(List<String> pictures);
	
	/*String getCityName();
	
	void setCityName(String cityname);*/

}