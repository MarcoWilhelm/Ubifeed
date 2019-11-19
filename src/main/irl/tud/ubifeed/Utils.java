package irl.tud.ubifeed;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class Utils {
	
	/**
	 * verify if the string is null or empty
	 * 
	 * @param string to verify if it is null or empty
	 * @return boolean false if it is
	 */
	public static boolean isNotNullOrEmpty(String string) {
		return string != null && !string.isEmpty();
	}
	
	/**
	 * verify if the string is a phone number
	 * 
	 * @param string to verify if it is a email
	 * @return boolean true, if it is
	 */
	public static boolean isPhoneNumber(String string) {
		return Pattern.matches("[+]?[0-9]{7,11}", string);
	}
	/**
	 * verify if it is an email
	 * 
	 * @param string to verify if it is a email
	 * @return boolean true, if it is
	 */
	public static boolean isEmail(String string) {
		return Pattern.matches("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,}$", string);
	}


}
