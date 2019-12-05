package irl.tud.ubifeed.presentation;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.algorithms.Algorithm;
import com.owlike.genson.Genson;
import com.owlike.genson.GensonBuilder;

import irl.tud.ubifeed.Config;
import irl.tud.ubifeed.pickupstation.PickupStationDto;
import irl.tud.ubifeed.restaurant.RestaurantDto;

public class ServletHelper {


	private Algorithm algorithm = Algorithm.HMAC256(Config.getConfigFor("secret"));
	private Genson genson = new GensonBuilder().useRuntimeType(true).useClassMetadata(true).create();

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

	/**
	 * Get the parameter
	 * 
	 * @param isMultiPart true if is a multipart/form-data request
	 * @param req the request, getParameters if isn't multipart
	 * @param parameters parameters if isMultiPart
	 * @param key key for the parameter
	 * @return the parameter
	 */
	public String getParameter(boolean isMultiPart, HttpServletRequest req, Map<String, String> parameters, String key) {
		return (isMultiPart) ? parameters.get(key): req.getParameter(key);
	}

	/**
	 * Add a cookie with a station role
	 * @param pickup the pickupStation
	 * @param req the request
	 * @param resp the response server to add Cookie
	 */
	public void addPickupCookie(PickupStationDto pickup, HttpServletRequest req, HttpServletResponse resp) {
		String url = req.getRequestURL().toString();
		String role = "station";
		String token = createToken(pickup.getPickupId(), role, url);

		addCookie(token, resp);

	}
	/**
	 * Add a cookie with a restaurant role
	 * @param pickup the pickupStation
	 * @param req the request
	 * @param resp the response server to add Cookie
	 */
	public void addRestaurantCookie(RestaurantDto restaurant, HttpServletRequest req, HttpServletResponse resp) {
		String url = req.getRequestURL().toString();
		String role = "restaurant";
		String token = createToken(restaurant.getRestaurantId(), role, url);

		addCookie(token, resp);

	}
	/**
	 * Add cookie to the server
	 * @param token token for the cookie
	 * @param resp the HttpServletResponse
	 */
	private void addCookie(String token, HttpServletResponse resp) {
		Cookie cookie = new Cookie("user", token);
		cookie.setPath("/");
		cookie.setMaxAge(60 * 60 * 24 * 365);

		resp.addCookie(cookie);
	}

	/**
	 * Create token for the cookie
	 * @param id id of the restaurant or pickupStation
	 * @param role of the user, restaurant or station
	 * @param ip URL of the request
	 * @return
	 */
	private String createToken(int id, String role, String ip) {	
		Builder tokenBuilder = JWT.create();

		tokenBuilder.withClaim("role", role);
		tokenBuilder.withClaim("id", id);
		tokenBuilder.withClaim("ip", ip);
		String token = tokenBuilder.sign(algorithm);

		return token;
	}

	/**
	 * get the cookie if exists
	 * @param req the httpServletRequest
	 * @return a map with the parameters in the cookie
	 */
	public Map<String,String> getCookie(HttpServletRequest req) {
		Map<String,String> map = new HashMap<String,String>();
		Cookie[] cookies = req.getCookies();
		String token = null;
		if (cookies != null) {
			for (Cookie c : cookies) {
				if ("user".equals(c.getName()) && c.getSecure()) {
					token = c.getValue();
				} else if ("user".equals(c.getName()) && token == null) {
					token = c.getValue();
				}
			}
		}
		if (token == null) {
			return null;
		}

		map.put("id", JWT.require(algorithm).build().verify(token).getClaim("id").asInt().toString());
		map.put("role", JWT.require(algorithm).build().verify(token).getClaim("role").asString());

		return map;
	}

	/**
	 * Get the current genson instance.
	 * 
	 * @return the current genson instance
	 */
	public Genson getGenson() {
		return genson;
	}
	
}