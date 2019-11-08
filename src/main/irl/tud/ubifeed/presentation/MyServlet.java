package irl.tud.ubifeed.presentation;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.servlet.DefaultServlet;

import com.owlike.genson.Genson;

import irl.tud.ubifeed.Inject;
import irl.tud.ubifeed.business.DeliveryUcc;
import irl.tud.ubifeed.business.RestaurantUcc;
import irl.tud.ubifeed.business.UserUcc;
import irl.tud.ubifeed.business.modelfactory.ModelFactory;
import irl.tud.ubifeed.user.UserDto;

public class MyServlet extends DefaultServlet {

	@Inject
	public UserUcc userUcc;

	@Inject
	public RestaurantUcc restaurantUcc;

	@Inject
	public DeliveryUcc deliveryUcc;

	@Inject
	public ModelFactory factory;

	/**
	 * Receive get requests from the client and threat it.
	 * 
	 * @param req The HttpServletRequest.
	 * @param resp The HttpServletResponse.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

	}

	/**
	 * Receive post requests from the client and threat it.
	 * 
	 * @param req The HttpServletRequest.
	 * @param resp The HttpServletResponse.
	 */
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) {
		String action = req.getParameter("action");
		// No handled call
		if (action == null) {
			return;
		}
		switch(action) {
		case "login-user":
			loginUser(req, resp);
			return;
		case "register-user":
			registerUser(req, resp);
			return;
		default:
			break;
		}
	}
	
	private void loginUser(HttpServletRequest req, HttpServletResponse resp) {
		UserDto user = factory.getUserDto();
		
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		
		user.setEmail(email);
		user.setPassword(password);
		
		user = userUcc.loginUser(user);
		Genson genson = new Genson();
		try {
			resp.getOutputStream().write(genson.serialize(user).getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void registerUser(HttpServletRequest req, HttpServletResponse resp) {
		UserDto user = factory.getUserDto();
		
		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		String phone = req.getParameter("phone");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		
		user.setEmail(email);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setPassword(password);
		user.setPhone(phone);
		
		userUcc.registerUser(user);
		
		Genson genson = new Genson();
		try {
			resp.getOutputStream().write(genson.serialize(user).getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

