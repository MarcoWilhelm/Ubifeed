package irl.tud.ubifeed.business.modelfactory;

import irl.tud.ubifeed.country.CityDto;
import irl.tud.ubifeed.country.CityImpl;
import irl.tud.ubifeed.country.CountryDto;
import irl.tud.ubifeed.country.CountryImpl;
import irl.tud.ubifeed.event.EventDto;
import irl.tud.ubifeed.event.EventImpl;
import irl.tud.ubifeed.meal.MealDto;
import irl.tud.ubifeed.meal.MealImpl;
import irl.tud.ubifeed.order.OrderDto;
import irl.tud.ubifeed.order.OrderImpl;
import irl.tud.ubifeed.pickupstation.PickupStation;
import irl.tud.ubifeed.pickupstation.PickupStationDto;
import irl.tud.ubifeed.pickupstation.PickupStationImpl;
import irl.tud.ubifeed.restaurant.Restaurant;
import irl.tud.ubifeed.restaurant.RestaurantDto;
import irl.tud.ubifeed.restaurant.RestaurantImpl;
import irl.tud.ubifeed.seatcatdto.SeatCatDto;
import irl.tud.ubifeed.seatcatdto.SeatCatDtoImpl;
import irl.tud.ubifeed.user.User;
import irl.tud.ubifeed.user.UserDto;
import irl.tud.ubifeed.user.UserImpl;
import irl.tud.ubifeed.venue.VenueDto;
import irl.tud.ubifeed.venue.VenueImpl;

public class ModelFactoryImpl implements ModelFactory {

	@Override
	public CountryDto getCountryDto() {
		return new CountryImpl();
	}

	@Override
	public CityDto getCityDto() {
		return new CityImpl();
	}

	@Override
	public MealDto getMealDto() {
		return new MealImpl();
	}

	@Override
	public OrderDto getOrderDto() {
		return new OrderImpl();
	}

	@Override
	public PickupStation getPickupStation() {
		return new PickupStationImpl();
	}

	@Override
	public PickupStationDto getPickupStationDto() {
		return new PickupStationImpl();
	}

	@Override
	public Restaurant getRestaurant() {
		return new RestaurantImpl();
	}

	@Override
	public RestaurantDto getRestaurantDto() {
		return new RestaurantImpl();
	}

	@Override
	public User getUser() {
		return new UserImpl();
	}

	@Override
	public UserDto getUserDto() {
		return new UserImpl();
	}

	@Override
	public VenueDto getVenueDto() {
		return new VenueImpl();
	}

	@Override
	public EventDto getEventDto() {
		return new EventImpl();
	}
	
	@Override
	public SeatCatDto getSeatCatDto() {
		return new SeatCatDtoImpl();
	}


}
