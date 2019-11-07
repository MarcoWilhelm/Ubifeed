package irl.tud.ubifeed.country;

public class CountryImpl implements CountryDto {

	private int countryId;
	private String name;
	private String code;

	public int getCountryId() {
		return countryId;
	}
	@Override
	public void setCountryId(int countryId) {
		this.countryId = countryId;
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
	public String getCode() {
		return code;
	}
	@Override
	public void setCode(String code) {
		this.code = code;
	}

}
