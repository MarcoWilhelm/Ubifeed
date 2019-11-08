package irl.tud.ubifeed.dbaccess.userdao;

import irl.tud.ubifeed.user.UserDto;

public interface UserDao {
	
	UserDto loginUser(UserDto user);

	UserDto register(UserDto user);

}
