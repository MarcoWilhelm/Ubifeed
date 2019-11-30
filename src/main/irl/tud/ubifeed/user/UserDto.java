package irl.tud.ubifeed.user;

public interface UserDto {

	int getUserId();

	void setUserId(int userID);

	String getFirstName();

	void setFirstName(String firstName);

	String getLastName();

	void setLastName(String lastName);

	String getPassword();

	void setPassword(String password);

	String getPhone();

	void setPhone(String phone);

	String getEmail();

	void setEmail(String email);

	String getProfilePictureName();

	void setProfilePictureName(String profilePictureName);
	
	byte[] getProfilePictureFile();
	
	void setProfilePictureFile(byte[] profilePictureFile);
}
