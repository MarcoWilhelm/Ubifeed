package irl.tud.ubifeed.presentation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.eclipse.jetty.servlet.DefaultServlet;

import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;

import at.favre.lib.crypto.bcrypt.BCrypt;
import irl.tud.ubifeed.Config;
import irl.tud.ubifeed.Inject;
import irl.tud.ubifeed.Utils;
import irl.tud.ubifeed.business.DeliveryUcc;
import irl.tud.ubifeed.business.RestaurantUcc;
import irl.tud.ubifeed.business.UserUcc;
import irl.tud.ubifeed.business.modelfactory.ModelFactory;
import irl.tud.ubifeed.event.EventDto;
import irl.tud.ubifeed.exception.FatalErrorException;
import irl.tud.ubifeed.meal.MealDto;
import irl.tud.ubifeed.order.OrderDto;
import irl.tud.ubifeed.pickupstation.PickupStationDto;
import irl.tud.ubifeed.restaurant.RestaurantDto;
import irl.tud.ubifeed.seatcatdto.SeatCatDto;
import irl.tud.ubifeed.user.UserDto;
import irl.tud.ubifeed.venue.VenueDto;

@MultipartConfig
public class MyServlet extends DefaultServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int MAX_MEM_SIZE = 1024*1024*1;
	private static final int MAX_FILE_SIZE = 1024*1024*50;

	@Inject
	public UserUcc userUcc;

	@Inject
	public RestaurantUcc restaurantUcc;

	@Inject
	public DeliveryUcc deliveryUcc;

	@Inject
	public ModelFactory factory;

	@Inject
	public ServletHelper servletHelper;

	private String html = "";



	/**
	 * Receive get requests from the client and threat it.
	 * 
	 * @param req The HttpServletRequest.
	 * @param resp The HttpServletResponse.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("doGet");

		// Static files return
		String path = req.getRequestURI();
		if (path.contains("/assets")) {
			super.doGet(req, resp);
			return;
		}

		this.html = "";
		//get content of the html file

		try (Stream<String> lines = Files.lines(Paths.get(Config.getConfigFor("index")))) {
			lines.forEach(line -> html += line);
		} catch (IOException exc) {
			exc.printStackTrace();
		}

		servletHelper.sendToClient(resp, html, "text/html", HttpServletResponse.SC_ACCEPTED);

	}


	/**
	 * Receive post requests from the client and threat it.
	 * 
	 * @param req The HttpServletRequest.
	 * @param resp The HttpServletResponse.
	 */

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("doPost");

		boolean isMultiPart = ServletFileUpload.isMultipartContent(req);

		Map<String, String> parameters = new HashMap<String, String>();
		if(isMultiPart) {

			DiskFileItemFactory factory = new DiskFileItemFactory();

			// maximum size that will be stored in memory
			factory.setSizeThreshold(MAX_MEM_SIZE);

			// Location to save data that is larger than maxMemSize.
			factory.setRepository(new File("c:\\temp"));

			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload(factory);

			// maximum file size to be uploaded.
			upload.setSizeMax(MAX_FILE_SIZE);

			try { 
				// Parse the request to get file items.
				List<FileItem> fileItems = upload.parseRequest(req);

				// Process the uploaded file items
				Iterator<FileItem> i = fileItems.iterator();
				while ( i.hasNext () ) {
					FileItem fi = (FileItem)i.next();
					if ( !fi.isFormField () ) {
						// Get the uploaded file parameters
						String fieldName = fi.getFieldName();
						String fileName = fi.getName();
						if (!fi.getContentType().equalsIgnoreCase("image/jpeg")) {
							continue;
						}
						byte[] bytes = fi.get();
						String localDate = Utils.localDateToString(LocalDateTime.now());
						String picture = fileName.substring(0, fileName.indexOf('.'))+"_"+ localDate + ".jpg";
						if(req.getAttribute("pictureName") == null) { 
							req.setAttribute("pictureName", new ArrayList<String>());
							((List<String>)req.getAttribute("pictureName")).add(picture);
							req.setAttribute("pictureFile", new ArrayList<byte[]>());
							((List<byte[]>)req.getAttribute("pictureFile")).add(bytes);
						}else{
							List<String> names = (List<String>)req.getAttribute("pictureName");
							names.add(picture);
							List<byte[]> files = (List<byte[]>)req.getAttribute("pictureFile");
							files.add(bytes);
						}

					}
					else {
						parameters.put(fi.getFieldName(), fi.getString());
					}
				}
			} catch(Exception ex) {
				servletHelper.sendToClient(resp, "Internal Server Error", "text/plain",
						HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				ex.printStackTrace();
				return;
			}
		}
		String action = servletHelper.getParameter(isMultiPart, req, parameters, "action");

		// No handled call
		if (action == null) {
			return;
		}
		System.out.println(action);

		try {
			// Action checking
			switch(action) {
			case "login-user":
				loginUser(req, resp, isMultiPart, parameters);
				return;
			case "register-user":
				registerUser(req, resp, isMultiPart, parameters);
				return;
			case "login-restaurant":
				loginRestaurant(req, resp, isMultiPart, parameters);
				return;
			case "login-pickup-station":
				loginPickupStation(req, resp, isMultiPart, parameters);
				return;
			case "log-out" :
				logOut(req,resp);
				return;
			case "verification" :
				verification(req,resp);
				return;
			case "get-all-venues":
				getAllVenues(req, resp);
				return;
			case "get-all-restaurants":
				getAllRestaurants(req, resp, isMultiPart, parameters);
				return;
			case "get-events":
				getEvents(req, resp, isMultiPart, parameters);
				return;	
			case "get-meals":
				getMeals(req, resp, isMultiPart, parameters);
				return;
			case "get-all-orders":
				getAllOrders(req, resp, isMultiPart, parameters);
				return;
			case "get-pickup-details":
				getPickupDetails(req, resp, isMultiPart, parameters);

			case "delete-meal":
				deleteMeal(req, resp, isMultiPart, parameters);
				return;
			case "add-order":
				addOrder(req, resp, isMultiPart, parameters);
			}

			Map<String,String> cookie = servletHelper.getCookie(req);
			if(cookie == null || cookie.isEmpty()) {
				servletHelper.sendToClient(resp, "Access denied", "text/plain",
						HttpServletResponse.SC_UNAUTHORIZED);
				return;
			}
			System.out.println(cookie.get("role"));
			if(cookie.get("role").equals("restaurant")) {
				switch(action) {
				case "get-all-orders-rest":
					getAllOrdersRest(req, resp, isMultiPart, parameters);
					return;
				case "edit-menu":
					editMenu(req, resp, isMultiPart, parameters);
					return;
				case "prepare-order":
					prepareOrder(req, resp, isMultiPart, parameters);
					return;
				case "finish-order":
					finishOrder(req, resp, isMultiPart, parameters);
					return;
				case "add-meal":
					addMeal(req, resp, isMultiPart, parameters);
					return;
				case "delete-meal":
					deleteMeal(req, resp, isMultiPart, parameters);
					return;
				}
			}
			if(cookie.get("role").equals("station")) {
				switch(action) {
				case "get-all-orders-pickup":
					getAllOrdersPickup(req, resp, isMultiPart, parameters);
					return;
				case "take-order":
					takeOrder(req, resp, isMultiPart, parameters);
					return;
				case "deliver-order":
					deliverOrder(req, resp, isMultiPart, parameters);
					return;
				}
			}
		}catch(FatalErrorException e) {
			servletHelper.sendToClient(resp, "Internal Server Error", "text/plain",
					HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
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
		resp.setHeader("Access-Control-Allow-Origin", "http://localhost:8100, http://localhost:8080");
		resp.setHeader("Access-Control-Allow-Methods", "POST, GET");
		resp.setHeader("Access-Control-Allow-Headers","origin, content-type, accept");
	}

	private void prepareOrder(HttpServletRequest req, HttpServletResponse resp, boolean isMultiPart, Map<String,String> parameters) {
		int orderId = Integer.parseInt(servletHelper.getParameter(isMultiPart, req, parameters,"orderId"));
		
		restaurantUcc.prepareOrder(orderId);
	}
	
	private void finishOrder(HttpServletRequest req, HttpServletResponse resp, boolean isMultiPart, Map<String,String> parameters) {
		int orderId = Integer.parseInt(servletHelper.getParameter(isMultiPart, req, parameters,"orderId"));
		
		restaurantUcc.finishOrder(orderId);
	}
	
	private void takeOrder(HttpServletRequest req, HttpServletResponse resp, boolean isMultiPart, Map<String,String> parameters) {
		int orderId = Integer.parseInt(servletHelper.getParameter(isMultiPart, req, parameters,"orderId"));
		System.out.println("UCC_TAKE_ORDER");
		deliveryUcc.takeOrder(orderId);
	}
	
	private void deliverOrder(HttpServletRequest req, HttpServletResponse resp, boolean isMultiPart, Map<String,String> parameters) {
		int orderId = Integer.parseInt(servletHelper.getParameter(isMultiPart, req, parameters,"orderId"));
		
		deliveryUcc.deliverOrder(orderId);
	}
	
	private void addMeal(HttpServletRequest req, HttpServletResponse resp, boolean isMultiPart, Map<String,String> parameters) {
		MealDto meal = factory.getMealDto();
		String restaurantId = servletHelper.getParameter(isMultiPart, req, parameters,"restaurantId");

		String name = servletHelper.getParameter(isMultiPart, req, parameters,"name");
		Double price = Double.parseDouble(servletHelper.getParameter(isMultiPart, req, parameters,"price")); 
		String category = servletHelper.getParameter(isMultiPart, req, parameters,"category");
		String image = ((List<String>)req.getAttribute("pictureName")).get(0);


		meal.setName(name);
		meal.setPrice(price);
		meal.setCategory(category);
		meal.setPictures(image);

		meal = restaurantUcc.addMeal(meal, restaurantId);

		if(meal != null && Utils.isNotNullOrEmpty(meal.getPictures())) {
			byte[] bytes = ((List<byte[]>) req.getAttribute("pictureFile")).get(0);
			Utils.uploadPicture(meal.getPictures(), Config.getConfigFor("picturesPath") + File.separator + Config.getConfigFor("profilePictures"), bytes);
		}

		servletHelper.sendToClient(resp, servletHelper.getGenson().serialize(meal), "application/json", HttpServletResponse.SC_ACCEPTED);

	}

	private void deleteMeal(HttpServletRequest req, HttpServletResponse resp, boolean isMultiPart, Map<String,String> parameters) {
		MealDto meal = factory.getMealDto();
		String restaurantId = servletHelper.getParameter(isMultiPart, req, parameters,"restaurantId");
		int mealId = Integer.parseInt(servletHelper.getParameter(isMultiPart, req, parameters,"mealId"));
		
		meal.setMealId(mealId);
		
		meal = restaurantUcc.deleteMeal(meal, restaurantId);
   
		servletHelper.sendToClient(resp, servletHelper.getGenson().serialize(meal), "application/json", HttpServletResponse.SC_ACCEPTED);

	}

	private void editMenu(HttpServletRequest req, HttpServletResponse resp, boolean isMultiPart, Map<String,String> parameters) {
		// TODO Auto-generated method stub
	}

	private void logOut(HttpServletRequest req, HttpServletResponse resp) {
		// Creating null cookie
		Cookie cookie = new Cookie("user", "");
		cookie.setPath("/");
		cookie.setMaxAge(0);
		// Resetting cookie
		resp.addCookie(cookie);
	}
	private void verification(HttpServletRequest req, HttpServletResponse resp) {
		Map<String,String> cookie = servletHelper.getCookie(req);
		if (cookie == null || cookie.isEmpty()) {
			servletHelper.sendToClient(resp, "", "text/plain",
					HttpServletResponse.SC_ACCEPTED);
		} else {
			servletHelper.sendToClient(resp, servletHelper.getGenson().serialize(cookie), "application/json",
					HttpServletResponse.SC_ACCEPTED);
		}
	}

	private void loginUser(HttpServletRequest req, HttpServletResponse resp, boolean isMultiPart, Map<String,String> parameters) {
		//create dto
		UserDto user = factory.getUserDto();

		//get the data from the request
		String email = servletHelper.getParameter(isMultiPart, req, parameters, "email");
		String password = servletHelper.getParameter(isMultiPart, req, parameters, "password");

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

	private void registerUser(HttpServletRequest req, HttpServletResponse resp, boolean isMultiPart, Map<String,String> parameters) {
		UserDto user = factory.getUserDto();

		String firstName = servletHelper.getParameter(isMultiPart, req, parameters,"firstName");
		String lastName = servletHelper.getParameter(isMultiPart, req, parameters,"lastName");
		String phone = servletHelper.getParameter(isMultiPart, req, parameters,"phone");
		String email = servletHelper.getParameter(isMultiPart, req, parameters,"email");
		String password = servletHelper.getParameter(isMultiPart, req, parameters,"password");
		//String image = ((List<String>)req.getAttribute("pictureName")).get(0);

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
		//user.setProfilePictureName(image);

		user = userUcc.registerUser(user);

		if(user != null && Utils.isNotNullOrEmpty(user.getProfilePictureName())) {
			byte[] bytes = ((List<byte[]>) req.getAttribute("pictureFile")).get(0);
			Utils.uploadPicture(user.getProfilePictureName(), Config.getConfigFor("picturesPath") + File.separator + Config.getConfigFor("profilePictures"), bytes);
		}

		servletHelper.sendToClient(resp, servletHelper.getGenson().serialize(user), "application/json", HttpServletResponse.SC_ACCEPTED);

	}

	private void loginRestaurant(HttpServletRequest req, HttpServletResponse resp, boolean isMultiPart, Map<String,String> parameters) {
		//create dto
		RestaurantDto restaurant = factory.getRestaurantDto();

		//get the data from the request
		String email = servletHelper.getParameter(isMultiPart, req, parameters,"email");
		String password = servletHelper.getParameter(isMultiPart, req, parameters,"password");

		// check business for the data, we may need to creat a Util class for these checks
		// with methods like isNotNull(String)
		if(!Utils.isNotNullOrEmpty(email) || !Utils.isNotNullOrEmpty(password))
			return;

		//checks are ok, so init dto
		restaurant.setEmail(email);
		restaurant.setPassword(password);

		//Servlet -> Ucc
		restaurant = restaurantUcc.loginRestaurant(restaurant);

		if(restaurant != null)
			this.servletHelper.addRestaurantCookie(restaurant, req, resp);

		//send the json to the app, we can create a method for sending the data back
		servletHelper.sendToClient(resp, servletHelper.getGenson().serialize(restaurant), "application/json", HttpServletResponse.SC_ACCEPTED);

	}

	private void loginPickupStation(HttpServletRequest req, HttpServletResponse resp, boolean isMultiPart, Map<String,String> parameters) {
		//create dto
		PickupStationDto pickupStation = factory.getPickupStationDto();

		//get the data from the request
		String email = servletHelper.getParameter(isMultiPart, req, parameters,"email");
		String password = servletHelper.getParameter(isMultiPart, req, parameters,"password");

		// check business for the data, we may need to creat a Util class for these checks
		// with methods like isNotNull(String)
		if(!Utils.isNotNullOrEmpty(email) || !Utils.isNotNullOrEmpty(password))
			return;

		//checks are ok, so init dto
		pickupStation.setEmail(email);
		pickupStation.setPassword(password);

		//Servlet -> Ucc
		pickupStation = deliveryUcc.loginPickupStation(pickupStation);

		if(pickupStation != null)
			this.servletHelper.addPickupCookie(pickupStation, req, resp);

		//send the json to the app, we can create a method for sending the data back
		servletHelper.sendToClient(resp, servletHelper.getGenson().serialize(pickupStation), "application/json", HttpServletResponse.SC_ACCEPTED);

	}




	private void getAllVenues(HttpServletRequest req, HttpServletResponse resp) {

		List<VenueDto> venue = userUcc.getAllVenues();

		servletHelper.sendToClient(resp, servletHelper.getGenson().serialize(venue), "application/json", HttpServletResponse.SC_ACCEPTED);

	}


	private void getAllRestaurants(HttpServletRequest req, HttpServletResponse resp, boolean isMultiPart, Map<String,String> parameters) {

		String venueId = servletHelper.getParameter(isMultiPart, req, parameters,"venueId");

		List<RestaurantDto> restaurants = userUcc.getAllRestaurants(venueId);

		servletHelper.sendToClient(resp, servletHelper.getGenson().serialize(restaurants), "application/json", HttpServletResponse.SC_ACCEPTED);

	}

	private void getEvents(HttpServletRequest req, HttpServletResponse resp, boolean isMultiPart, Map<String,String> parameters) {


		String venueId = servletHelper.getParameter(isMultiPart, req, parameters,"venueId");

		List<EventDto> events = userUcc.getEvents(venueId);

		servletHelper.sendToClient(resp, servletHelper.getGenson().serialize(events), "application/json", HttpServletResponse.SC_ACCEPTED);

	}

	private void getMeals(HttpServletRequest req, HttpServletResponse resp, boolean isMultiPart, Map<String,String> parameters) {

		String restaurantId = servletHelper.getParameter(isMultiPart, req, parameters,"restaurantId");

		List<MealDto> meals = userUcc.getMeals(restaurantId);

		servletHelper.sendToClient(resp, servletHelper.getGenson().serialize(meals), "application/json", HttpServletResponse.SC_ACCEPTED);

	}

	private void getPickupDetails(HttpServletRequest req, HttpServletResponse resp, boolean isMultiPart, Map<String,String> parameters) {
		String venueId = servletHelper.getParameter(isMultiPart, req, parameters,"venueId");
		List<SeatCatDto> pickupDetails = userUcc.getPickupDetails(venueId);

		servletHelper.sendToClient(resp, servletHelper.getGenson().serialize(pickupDetails), "application/json", HttpServletResponse.SC_ACCEPTED);

	}

	private void getAllOrders(HttpServletRequest req, HttpServletResponse resp, boolean isMultiPart, Map<String,String> parameters) {
		String userId = servletHelper.getParameter(isMultiPart, req, parameters,"userId");
		String seat_cat_id = servletHelper.getParameter(isMultiPart, req, parameters, "seatCatId");
		List<OrderDto> orders = userUcc.getAllOrders(userId, seat_cat_id);

		servletHelper.sendToClient(resp, servletHelper.getGenson().serialize(orders), "application/json", HttpServletResponse.SC_ACCEPTED);
	}

	private void getAllOrdersRest(HttpServletRequest req, HttpServletResponse resp, boolean isMultiPart, Map<String,String> parameters) {
		String restaurantId = servletHelper.getCookie(req).get("id");
		List<OrderDto> orders = restaurantUcc.getAllOrders(restaurantId);

		servletHelper.sendToClient(resp, servletHelper.getGenson().serialize(orders), "application/json", HttpServletResponse.SC_ACCEPTED);

	}

	private void getAllOrdersPickup(HttpServletRequest req, HttpServletResponse resp, boolean isMultiPart, Map<String,String> parameters) {

		//String restaurantId = servletHelper.getParameter(isMultiPart, req, parameters,"restaurantId");
		//List<OrderDto> orders = deliveryUcc.getAllOrders();

		String pickupId = servletHelper.getCookie(req).get("id");
		List<OrderDto> orders = deliveryUcc.getAllOrders(pickupId);


		servletHelper.sendToClient(resp, servletHelper.getGenson().serialize(orders), "application/json", HttpServletResponse.SC_ACCEPTED);

	}

	private void addOrder(HttpServletRequest req, HttpServletResponse resp, boolean isMultiPart, Map<String,String> parameters) {
		String foodBasketStr = servletHelper.getParameter(isMultiPart, req, parameters, "foodbasket");
		String drinksBasketStr = servletHelper.getParameter(isMultiPart, req, parameters, "drinksbasket");
		int restaurantId = Integer.parseInt(servletHelper.getParameter(isMultiPart, req, parameters, "restaurantId"));
		int userId = Integer.parseInt(servletHelper.getParameter(isMultiPart, req, parameters, "userId"));
		int seatCatId = Integer.parseInt(servletHelper.getParameter(isMultiPart, req, parameters, "seatCatId"));

		Map<Integer, MealDto> foodBasket = servletHelper.getGenson().deserialize(foodBasketStr, new GenericType<Map<Integer,MealDto>>(){});
		Map<Integer, MealDto> drinksBasket = servletHelper.getGenson().deserialize(drinksBasketStr, new GenericType<Map<Integer, MealDto>>(){});

		
		Map<MealDto, Long> basket = Stream.concat(foodBasket.values().stream(), drinksBasket.values().stream()).collect(Collectors.groupingBy(m -> m, Collectors.counting()));
		System.out.println("Basket : " + basket);
		userUcc.addOrder(basket, restaurantId, userId, seatCatId);
	}



}

