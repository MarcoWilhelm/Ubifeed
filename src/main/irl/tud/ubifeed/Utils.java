package irl.tud.ubifeed;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

import org.apache.commons.fileupload.FileItem;

import irl.tud.ubifeed.exception.FatalErrorException;

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
		return Pattern.matches("[+]?[0-9]{7,13}", string);
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
	
	public static String localDateToString(LocalDateTime now) {
		String localDate = now.getDayOfMonth()+ "" + now.getMonthValue() + "" + now.getYear()
			+ "_"+now.getHour()+""+now.getMinute() + "" + now.getSecond();
		return localDate;
	}
	
	public static void uploadPicture(String fileName, String uploadFolderPath, byte[] bytes) {
		// write all files in upload folder
		
		createFolderIfNotExists(uploadFolderPath);
		System.out.println(fileName);
		System.out.println(uploadFolderPath);
		File uploadedFile = new File(uploadFolderPath + File.separator + fileName);
		try {
			uploadedFile.createNewFile();
			Files.write(Paths.get(uploadedFile.getPath()), bytes);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new FatalErrorException(e);
		}
	}
	
	private static void createFolderIfNotExists(String uploadFolderPath) {
		File folder = new File(uploadFolderPath);
		if (!folder.exists()) {
			folder.mkdirs();
		}
	}

}
