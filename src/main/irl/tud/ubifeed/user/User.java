package irl.tud.ubifeed.user;

public interface User extends UserDto {
	
	public boolean verifyPassword(String password);

}
