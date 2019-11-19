package irl.tud.ubifeed.presentation;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.servlet.DefaultServlet;

import com.owlike.genson.Genson;

import irl.tud.ubifeed.Inject;
import irl.tud.ubifeed.Utils;
import irl.tud.ubifeed.business.DeliveryUcc;
import irl.tud.ubifeed.business.RestaurantUcc;
import irl.tud.ubifeed.business.UserUcc;
import irl.tud.ubifeed.business.modelfactory.ModelFactory;
import irl.tud.ubifeed.user.UserDto;

public class MyServlet extends DefaultServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
		System.out.println(action);
		// Action checking
		switch(action) {
		case "login-user":
			loginUser(req, resp);
			return;
		case "register-user":
			registerUser(req, resp);
			return;
		default:
			return;
		}
	}
	
	//for Preflight
	  @Override
	  protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
	          throws ServletException, IOException {
	      setAccessControlHeaders(resp);
	      resp.setStatus(HttpServletResponse.SC_OK);
	  }

	  private void setAccessControlHeaders(HttpServletResponse resp) {
	      resp.setHeader("Access-Control-Allow-Origin", "http://localhost:8100");
	      resp.setHeader("Access-Control-Allow-Methods", "POST");
	      resp.setHeader("Access-Control-Allow-Headers","origin, content-type, accept");
	  }
	
	
	private void loginUser(HttpServletRequest req, HttpServletResponse resp) {
		//create dto
		UserDto user = factory.getUserDto();
		
		//get the data from the request
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		System.out.println(req.getParameterMap());
		
		// check business for the data, we may need to creat a Util class for these checks
		// with methods like isNotNull(String)
		if(!Utils.isNotNullOrEmpty(email) || !Utils.isNotNullOrEmpty(password))
			return;
		
		//checks are ok, so init dto
		user.setEmail(email);
		user.setPassword(password);
		
		//Servlet -> Ucc
		user = userUcc.loginUser(user);
		// the instance that will create the json
		Genson genson = new Genson();
		try {
			//send the json to the app, we can create a method for sending the data back
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
		
		if(!Utils.isNotNullOrEmpty(email) || !Utils.isNotNullOrEmpty(password)  || !Utils.isNotNullOrEmpty(firstName)  
				|| !Utils.isNotNullOrEmpty(lastName))
			return;
		if(!Utils.isPhoneNumber(phone))
			return;
		if(!Utils.isEmail(email))
			return;
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

