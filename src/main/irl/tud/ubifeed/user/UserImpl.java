package irl.tud.ubifeed.user;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class UserImpl implements User {
	
	private int userId;
	private String firstName;
	private String lastName;
	private String password;
	private String phone;
	private String email;
	private String profilePictureName;
	private byte[] profilePictureFile;
	
	@Override
	public int getUserId() {
		return userId;
	}
	@Override
	public void setUserId(int userId) {
		this.userId = userId;
	}
	@Override
	public String getFirstName() {
		return firstName;
	}
	@Override
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	@Override
	public String getLastName() {
		return lastName;
	}
	@Override
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	@Override
	public String getPassword() {
		return password;
	}
	@Override
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String getPhone() {
		return phone;
	}
	@Override
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Override
	public String getEmail() {
		return email;
	}
	@Override
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public boolean verifyPassword(String password) {
		BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), this.getPassword());
		return result.verified;
	}
	@Override
	public String getProfilePictureName() {
		return this.profilePictureName;
	}
	@Override
	public void setProfilePictureName(String profilePictureName) {
		this.profilePictureName = profilePictureName;
		
	}
	@Override
	public byte[] getProfilePictureFile() {
		return this.profilePictureFile;
	}
	@Override
	public void setProfilePictureFile(byte[] profilePictureFile) {
		this.profilePictureFile = profilePictureFile;
	}
}
