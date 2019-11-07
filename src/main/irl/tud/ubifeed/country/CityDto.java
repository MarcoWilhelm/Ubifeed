package irl.tud.ubifeed.country;

public interface CityDto {
	
	int getCityId();
	
	void setCityId(int cityId);
	
	String getName();
	
	void setName(String name);
	
	CountryDto getCountry();
	
	void setCountry(CountryDto country);

}
