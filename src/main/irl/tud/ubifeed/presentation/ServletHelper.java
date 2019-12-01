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
	private Genson genson = new GensonBuilder().useRuntimeType(true).create();

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
		String url = req.getRequestURL().toString();
		String role = "station";
		System.out.println(pickup.getPickupId());
		String token = createToken(pickup.getPickupId(), role, url);

		addCookie(token, resp);

	}
	public void addRestaurantCookie(RestaurantDto restaurant, HttpServletRequest req, HttpServletResponse resp) {
		String url = req.getRequestURL().toString();
		String role = "restaurant";
		System.out.println(restaurant.getRestaurantId());
		String token = createToken(restaurant.getRestaurantId(), role, url);

		addCookie(token, resp);

	}
	private void addCookie(String token, HttpServletResponse resp) {
		Cookie cookie = new Cookie("user", token);
		cookie.setPath("/");
		cookie.setMaxAge(60 * 60 * 24 * 365);

		resp.addCookie(cookie);
	}

	private String createToken(int id, String role, String ip) {	
		Builder tokenBuilder = JWT.create();

		System.out.println("Create token : " +id);
		tokenBuilder.withClaim("role", role);
		tokenBuilder.withClaim("id", id);
		tokenBuilder.withClaim("ip", ip);
		String token = tokenBuilder.sign(algorithm);

		return token;
	}

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

		System.out.println(JWT.require(algorithm).build().verify(token).getClaims());
		System.out.println(JWT.require(algorithm).build().verify(token).getClaim("id").asInt().toString());
		System.out.println(JWT.require(algorithm).build().verify(token).getClaim("ip").asString());
		System.out.println(JWT.require(algorithm).build().verify(token).getClaim("role").asString());
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
