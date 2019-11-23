package irl.tud.ubifeed.event;

import java.time.LocalDateTime;
import java.util.List;

import irl.tud.ubifeed.venue.VenueDto;

public class EventImpl implements EventDto{

	private int eventId;
	private VenueDto venue;
	private String name;
	private LocalDateTime date;
	private List<String> pictures;
	
	
	
	@Override
	public int getEventId() {
		return eventId;
	}

	@Override
	public void setEventId(int eventId) {
		this.eventId = eventId;
		
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
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;	
	}

	@Override
	public LocalDateTime getDate() {
		return date;
	}

	@Override
	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	@Override
	public List<String> getPictures() {
		return pictures;
	}

	@Override
	public void setPictures(List<String> pictures) {
		this.pictures = pictures;
	}
	
	
}