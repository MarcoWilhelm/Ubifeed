package irl.tud.ubifeed.presentation;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import irl.tud.ubifeed.exception.FatalErrorException;

public class ServletHelper {

	/**
	 * Send a text message to a client.
	 * 
	 * @param resp The HttpServletResponse.
	 * @param result The result sent to the client as text.
	 * @param contentType Type of the response sent to the client (html, text, json, ...).
	 * @param status Status code of the response.
	 */
	public void sendToClient(HttpServletResponse resp, String result, String contentType,
			int status) {
		resp.setStatus(status);
		resp.setContentType(contentType);
		byte[] msgBytes;
		try {
			msgBytes = result.getBytes("UTF-8");
			resp.setContentLength(msgBytes.length);
			resp.setCharacterEncoding("utf-8");
			resp.getOutputStream().write(msgBytes);
		} catch (IOException exc) {
			exc.printStackTrace();
		}
	}

	public File getFolderPath(String uploadFilePath) {
		File toRet = new File(uploadFilePath);
		if (!toRet.exists()) {
			toRet.mkdirs();
		}
		return toRet;
	}

	public String uploadPicture(HttpServletRequest req,String uploadFilePath) {
		// write all files in upload folder
		String profilePicture = "";
		try {
			for (Part part : req.getParts()) {
				if (part != null && part.getSize() > 0) {
					String fileName = part.getSubmittedFileName();
					String contentType = part.getContentType();

					// allows only JPEG files to be uploaded
					if (!contentType.equalsIgnoreCase("image/jpeg")) {
						continue;
					}
					profilePicture = fileName+"_"+LocalDateTime.now().toString();
					part.write(uploadFilePath + File.separator + profilePicture);
				}
			}
		} catch (IOException | ServletException e1) {
			e1.printStackTrace();
			throw new FatalErrorException(e1);
		}
		return profilePicture;

	}

}
