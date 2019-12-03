package irl.tud.ubifeed.dbaccess.restaurantdao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import irl.tud.ubifeed.Inject;
import irl.tud.ubifeed.business.modelfactory.ModelFactory;
import irl.tud.ubifeed.dbaccess.DalBackendServices;
import irl.tud.ubifeed.meal.MealDto;
import irl.tud.ubifeed.order.OrderDto;
import irl.tud.ubifeed.restaurant.RestaurantDto;
import irl.tud.ubifeed.user.UserDto;

public class RestaurantDaoImpl implements RestaurantDao{
	
	@Inject
	public ModelFactory	factory;
	
	@Inject
	public DalBackendServices dal;

	@Override
	public RestaurantDto loginRestaurant(RestaurantDto restaurant) {
		String select = "SELECT r.rest_id, r.nme, r.address, r.descrip, r.passw, r.email ";
		String from = "FROM ubifeed.restaurants r ";
		String where = "WHERE r.email = ?";

		RestaurantDto toRet = factory.getRestaurantDto();
		//get the Prepared Statement, it will close automatically
		try(PreparedStatement ps = dal.getPreparedStatement(select + from + where)) {
			//init prepared Statement
			ps.setString(1, restaurant.getEmail());
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				// init the dto that will be returned by the method
				toRet.setRestaurantId(rs.getInt(1));
				toRet.setName(rs.getString(2));
				toRet.setAddress(rs.getString(3));
				toRet.setDescription(rs.getString(4));
				toRet.setPassword(rs.getString(5));
				toRet.setEmail(rs.getString(6));
			}
			//close the result set
			rs.close();
		}catch(SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		}
		return toRet;
	}

	@Override
	public List<OrderDto> getAllOrders(String restaurantId) {
		String select = "SELECT order_id, user_id, rest_id, pickup_id, order_status ";
		String from = "FROM ubifeed.orders ";
		String where = "WHERE rest_id = " + restaurantId + " AND order_status != 'DELIVERED' ";
		String order = "ORDER BY order_id DESC;";
		List<OrderDto> list = new ArrayList<OrderDto>();
		
		try (PreparedStatement ps = dal.getPreparedStatement(select + from + where + order)) {
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				OrderDto toRet = factory.getOrderDto();
				toRet.setOrderId(rs.getInt(1));
				toRet.setUserId(rs.getInt(2));
				toRet.setRestaurantId(rs.getInt(3));
				toRet.setPickupId(rs.getInt(4));
				toRet.setOrderStatus(rs.getString(5));
				list.add(toRet);
			}
			rs.close();
		} catch(SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		}
		return list;
	}

	@Override
	public MealDto addMeal(MealDto meal, String restaurantId) {
		String insert = "INSERT INTO ubifeed.meals (meal_id, nme, price, image, rest_id, meal_categ_id) ";
		String values = "VALUES(DEFAULT, ?, ?, ?, ?, ?)";
		try(PreparedStatement ps = dal.getPreparedStatement(insert + values)) {
			ps.setString(1, meal.getName());
			ps.setDouble(2, meal.getPrice());
			ps.setString(3, meal.getPictures() ); 
			ps.setString(4, restaurantId);
			ps.setString(5, meal.getCategory());
			ps.execute();
		}catch(SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		}
		return meal;
	}

	@Override
	public MealDto deleteMeal(MealDto meal, String restaurantId) {
		String delete = "DELETE FROM ubifeed.meals ";
		String where = "WHERE meal_id = ? AND rest_id = " + restaurantId +";";
		
		try(PreparedStatement ps = dal.getPreparedStatement(delete + where)) {
			ps.setInt(1, meal.getMealId());
			ps.execute();
			System.out.println("deleted");
		}catch(SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		}
		return meal;
	}
}
