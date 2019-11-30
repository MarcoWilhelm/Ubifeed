package irl.tud.ubifeed.presentation;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.fileupload.FileItem;

import com.auth0.jwt.JWTSigner;

import irl.tud.ubifeed.Config;
import irl.tud.ubifeed.Utils;
import irl.tud.ubifeed.exception.FatalErrorException;
import irl.tud.ubifeed.pickupstation.PickupStationDto;
import irl.tud.ubifeed.restaurant.RestaurantDto;

public class ServletHelper {

	private static JWTSigner jwt = new JWTSigner(Config.getConfigFor("secret"));

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

	public String getParameter(boolean isMultiPart, HttpServletRequest req, Map<String, String> parameters, String key) {
		return (isMultiPart) ? parameters.get(key): req.getParameter(key);
	}

	public void addPickupCookie(PickupStationDto pickup, HttpServletRequest req, HttpServletResponse resp) {
		Map<String, Object> claims = new HashMap<String, Object>();
		String url = req.getRequestURL().toString();
		String role = "station";

		String token = createToken(claims, pickup.getPickupId(), role, url);

		addCookie(token, resp);

	}
	public void addRestaurantCookie(RestaurantDto restaurant, HttpServletRequest req, HttpServletResponse resp) {
		Map<String, Object> claims = new HashMap<String, Object>();
		String url = req.getRequestURL().toString();
		String role = "restaurant";

		String token = createToken(claims, restaurant.getRestaurantId(), role, url);

		addCookie(token, resp);

	}
	private void addCookie(String token, HttpServletResponse resp) {
		Cookie cookie = new Cookie("user", token);
		cookie.setPath("/");
		cookie.setMaxAge(60 * 60 * 24 * 365);

		resp.addCookie(cookie);
	}

	private String createToken(Map<String, Object> claims, int id, String role, String ip) {
		claims.put("role", id);
		claims.put("id", id);
		claims.put("ip", ip);

		String token = jwt.sign(claims);

		return token;
	}

}
