package irl.tud.ubifeed.presentation;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.servlet.DefaultServlet;

import irl.tud.ubifeed.Inject;
import irl.tud.ubifeed.business.DeliveryUcc;
import irl.tud.ubifeed.business.RestaurantUcc;
import irl.tud.ubifeed.business.UserUcc;
import irl.tud.ubifeed.business.modelfactory.ModelFactory;

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
		  
	  }

}
	  
