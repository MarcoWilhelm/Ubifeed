package irl.tud.ubifeed.business.modelfactory;

import irl.tud.ubifeed.country.CityDto;
import irl.tud.ubifeed.country.CountryDto;
import irl.tud.ubifeed.event.EventDto;
import irl.tud.ubifeed.meal.MealDto;
import irl.tud.ubifeed.order.OrderDto;
import irl.tud.ubifeed.pickupstation.PickupStation;
import irl.tud.ubifeed.pickupstation.PickupStationDto;
import irl.tud.ubifeed.restaurant.Restaurant;
import irl.tud.ubifeed.restaurant.RestaurantDto;
import irl.tud.ubifeed.seatcatdto.SeatCatDto;
import irl.tud.ubifeed.user.User;
import irl.tud.ubifeed.user.UserDto;
import irl.tud.ubifeed.venue.VenueDto;

public interface ModelFactory {

	public CountryDto getCountryDto();
	
	public CityDto getCityDto();
	
	public MealDto getMealDto();
	
	public OrderDto getOrderDto();
	
	public PickupStation getPickupStation();
	
	public PickupStationDto getPickupStationDto();
	
	public Restaurant getRestaurant();
	
	public RestaurantDto getRestaurantDto();
	
	public User getUser();
	
	public UserDto getUserDto();
	
	public VenueDto getVenueDto();

	public EventDto getEventDto();
	
	public SeatCatDto getSeatCatDto();
	
}
