package irl.tud.ubifeed;

import java.util.regex.Pattern;

public class Utils {
	
	public static boolean isNotNullOrEmpty(String string) {
		return string != null && !string.isEmpty();
	}
	
	public static boolean isPhoneNumber(String string) {
		return Pattern.matches("[+]?[0-9]{7,11}", string);
	}
	
	public static boolean isEmail(String string) {
		return Pattern.matches("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,}$", string);
	}

}
