package irl.tud.ubifeed.presentation;

import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifyException;
import com.auth0.jwt.algorithms.Algorithm;
import com.owlike.genson.Genson;
import com.owlike.genson.GensonBuilder;

import irl.tud.ubifeed.Config;
import irl.tud.ubifeed.exception.FatalErrorException;
import irl.tud.ubifeed.pickupstation.PickupStationDto;
import irl.tud.ubifeed.restaurant.RestaurantDto;

public class ServletHelper {

	private static JWTSigner jwt = new JWTSigner(Config.getConfigFor("secret"));
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
		claims.put("role", role);
		claims.put("id", id);
		claims.put("ip", ip);

		String token = jwt.sign(claims);

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
	    try {
	    	Map<String, Object> decodedPayload = JWT.require(algorithm).build().verify(token);
	    	map.put("id", decodedPayload.get("id").toString());
	    	map.put("role", decodedPayload.get("role").toString());
		} catch (InvalidKeyException | NoSuchAlgorithmException | IllegalStateException | SignatureException
				| IOException | JWTVerifyException e) {
			e.printStackTrace();
			throw new FatalErrorException(e);
		}
		
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
