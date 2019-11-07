package irl.tud.ubifeed.country;

public class CityImpl implements CityDto {
	
	private int cityId;
	private String name;
	private CountryDto country;
	
	@Override
	public int getCityId() {
		return cityId;
	}
	@Override
	public void setCityId(int cityId) {
		this.cityId = cityId;
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
	public CountryDto getCountry() {
		return country;
	}
	@Override
	public void setCountry(CountryDto country) {
		this.country = country;
	}
}
