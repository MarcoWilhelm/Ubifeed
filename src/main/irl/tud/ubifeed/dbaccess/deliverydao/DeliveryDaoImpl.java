package irl.tud.ubifeed.dbaccess.deliverydao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import irl.tud.ubifeed.Enum;
import irl.tud.ubifeed.Inject;
import irl.tud.ubifeed.business.modelfactory.ModelFactory;
import irl.tud.ubifeed.dbaccess.DalBackendServices;
import irl.tud.ubifeed.exception.FatalErrorException;
import irl.tud.ubifeed.meal.MealDto;
import irl.tud.ubifeed.order.OrderDto;
import irl.tud.ubifeed.pickupstation.PickupStation;
import irl.tud.ubifeed.pickupstation.PickupStationDto;
import irl.tud.ubifeed.restaurant.RestaurantDto;
import irl.tud.ubifeed.user.UserDto;

public class DeliveryDaoImpl implements DeliveryDao {

	@Inject
	public ModelFactory	factory;

	@Inject
	public DalBackendServices dal;

	@Override
	public PickupStationDto loginPickupStation(PickupStationDto pickupstation) {
		String select = "SELECT ps.pickup_id,ps.passw, ps.email ";
		String from = "FROM ubifeed.pickup_stations ps ";
		String where = "WHERE ps.email = ?";

		PickupStationDto toRet = factory.getPickupStationDto();
		//get the Prepared Statement, it will close automatically
		try(PreparedStatement ps = dal.getPreparedStatement(select + from + where)) {
			//init prepared Statement
			ps.setString(1, pickupstation.getEmail());
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				// init the dto that will be returned by the method
				toRet.setPickupId(rs.getInt(1));
				toRet.setPassword(rs.getString(2));
				toRet.setEmail(rs.getString(3));
			}
			//close the result set
			rs.close();
		} catch(SQLException sqlExcept) {
			sqlExcept.printStackTrace();
			throw new FatalErrorException(sqlExcept);
		}
		return toRet;
	}

	@Override
	public List<OrderDto> getAllOrders(String pickupId) {
		String select = "SELECT o.order_id, o.user_id, o.rest_id, o.pickup_id, o.order_status, r.nme, r.address, "
				+ "u.firstn, u.lastn, u.phone, u.email ";
		String from = "FROM ubifeed.orders o, ubifeed.restaurants r, ubifeed.users u ";
		String where = "WHERE u.user_id = o.user_id AND r.rest_id = o.rest_id AND o.order_status != 'DELIVERED' "
				+ "AND o.pickup_id = ? ";
		String order = "ORDER BY order_id DESC;";
		List<OrderDto> list = new ArrayList<OrderDto>();

		try (PreparedStatement ps = dal.getPreparedStatement(select + from + where + order)) {
			ps.setInt(1, Integer.parseInt(pickupId));
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				RestaurantDto restaurant = factory.getRestaurantDto();
				PickupStationDto pickup = factory.getPickupStationDto();
				UserDto user = factory.getUserDto();
				OrderDto toRet = factory.getOrderDto();
				toRet.setOrderId(rs.getInt(1));

				user.setUserId(rs.getInt(2));
				user.setFirstName(rs.getString(8));
				user.setLastName(rs.getString(9));
				user.setPhone(rs.getString(10));
				user.setEmail(rs.getString(11));
				toRet.setUser(user);

				restaurant.setRestaurantId(rs.getInt(3));
				restaurant.setName(rs.getString(6));
				restaurant.setAddress(rs.getString(7));
				toRet.setRestaurant(restaurant);

				pickup.setPickupId(rs.getInt(4));
				toRet.setPickupStation(pickup);

				toRet.setMeals(getMealFromOrder(toRet.getOrderId()));

				toRet.setOrderStatus(rs.getString(5));
				list.add(toRet);
			}
			rs.close();
		} catch(SQLException sqlExcept) {
			sqlExcept.printStackTrace();
			throw new FatalErrorException(sqlExcept);
		}
		return list;	
	}

	@Override
	public List<MealDto> getMealFromOrder(int orderId) {
		String select = "SELECT m.nme, m.image, om.quantity, om.price ";
		String from = "FROM ubifeed.meals m, ubifeed.order_meals om ";
		String where = "WHERE m.meal_id = om.meal_id and om.order_id = ?";
		List<MealDto> list = new ArrayList<MealDto>();

		try (PreparedStatement ps = dal.getPreparedStatement(select + from + where)) {
			ps.setInt(1, orderId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				MealDto meal = factory.getMealDto();
				meal.setName(rs.getString(1));
				meal.setPictures(rs.getString(2));
				meal.setQuantity(rs.getInt(3));
				meal.setPrice(rs.getDouble(4));

				list.add(meal);
			}

		} catch(SQLException sqlExcept) {
			sqlExcept.printStackTrace();
			throw new FatalErrorException(sqlExcept);
		}
		return list;
	}

	@Override
	public String getOrderStatus(int orderId) {
		String select = "SELECT o.order_status ";
		String from = "FROM ubifeed.orders o ";
		String where ="WHERE o.order_id = ?";
		
		String toRet = null;
		try (PreparedStatement ps = dal.getPreparedStatement(select + from + where)) {
			ps.setInt(1, orderId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				toRet = rs.getString(1);
			}

		} catch(SQLException sqlExcept) {
			sqlExcept.printStackTrace();
			throw new FatalErrorException(sqlExcept);
		}
		return toRet;
	}

	@Override
	public void takeOrder(int orderId) {
		if(!Enum.States.valueOf(getOrderStatus(orderId)).equals(Enum.States.READY)){
			return;
		}
		System.out.println("TAKE_ORDER");
		String update = "UPDATE ubifeed.orders o SET o.order_status = ? ";
		String where = "WHERE o.order_id = ?";
		
		try(PreparedStatement ps = dal.getPreparedStatement(update + where)) {
			ps.setString(1, Enum.States.IN_STATION.toString());
			ps.setInt(2, orderId);
			ps.execute();
		}catch(SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		}
	}

	@Override
	public void deliverOrder(int orderId) {
		if(!Enum.States.valueOf(getOrderStatus(orderId)).equals(Enum.States.IN_STATION)){
			return;
		}
		String update = "UPDATE ubifeed.orders o SET o.order_status = ? ";
		String where = "WHERE o.order_id = ?";
		
		try(PreparedStatement ps = dal.getPreparedStatement(update + where)) {
			ps.setString(1, Enum.States.DELIVERED.toString());
			ps.setInt(2, orderId);
			ps.execute();
		}catch(SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		}
	}
}
