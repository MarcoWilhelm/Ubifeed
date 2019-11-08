package irl.tud.ubifeed.business;

import irl.tud.ubifeed.user.UserDto;

/**
 * 
 * @author Yann
 *
 */
public interface UserUcc {

	/**
	 * Gets a User from UserDao and verify his password.
	 * 
	 * @param user DTO with the mail and the password
	 * @return UserDTO if exists and correct and password verified else null.
	 */
	UserDto loginUser(UserDto user);
	
	/**
	 * Register a User in the Database
	 * @param user the user to create
	 * @return the user created
	 */
	UserDto registerUser(UserDto user);

}
