package irl.tud.ubifeed.dbaccess.userdao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import irl.tud.ubifeed.Inject;
import irl.tud.ubifeed.business.modelfactory.ModelFactory;
import irl.tud.ubifeed.dbaccess.DalBackendServices;
import irl.tud.ubifeed.exception.FatalErrorException;
import irl.tud.ubifeed.event.EventDto;
import irl.tud.ubifeed.meal.MealDto;
import irl.tud.ubifeed.restaurant.RestaurantDto;
import irl.tud.ubifeed.user.UserDto;
import irl.tud.ubifeed.venue.VenueDto;

public class UserDaoImpl implements UserDao {

	@Inject
	public ModelFactory	factory;

	@Inject
	public DalBackendServices dal;

	@Override
	public UserDto loginUser(UserDto user) {
		String select = "SELECT u.user_id, u.firstn, u.lastn, u.passw, u.email, "
				+ "u.phone ";
		String from = "FROM ubifeed.users u ";
		String where = "WHERE u.email = ?";

		UserDto toRet = factory.getUserDto();
		//get the Prepared Statement, it will close automatically
		try(PreparedStatement ps = dal.getPreparedStatement(select + from + where)) {
			//init prepared Statement
			ps.setString(1, user.getEmail());
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				// init the dto that will be returned by the method
				toRet.setUserId(rs.getInt(1));
				toRet.setFirstName(rs.getString(2));
				toRet.setLastName(rs.getString(3));
				toRet.setPassword(rs.getString(4));
				toRet.setEmail(rs.getString(5));
				toRet.setPhone(rs.getString(6));
			}
			//close the result set
			rs.close();
		}catch(SQLException sqlExcept) {
			sqlExcept.printStackTrace();
			throw new FatalErrorException(sqlExcept);
		}
		return toRet;
	}

	@Override
	public UserDto register(UserDto user) {
		UserDto test = factory.getUserDto();
		if((test = loginUser(user)) != null && test.getEmail() != null) {
			return null;
		}
		String insert = "INSERT INTO ubifeed.users (user_id, firstn, lastn, passw, email, phone, image)";
		String values = "VALUES(DEFAULT, ?, ?, ?, ?, ?, NULL)";
		try(PreparedStatement ps = dal.getPreparedStatement(insert + values)) {
			ps.setString(1, user.getFirstName());
			ps.setString(2, user.getLastName());
			ps.setString(3, user.getPassword());
			ps.setString(4, user.getEmail());
			ps.setString(5, user.getPhone());
			ps.execute();
		}catch(SQLException sqlExcept) {
			sqlExcept.printStackTrace();
			throw new FatalErrorException(sqlExcept);
		}
		return user;
	}
	
	

	@Override
	public List<VenueDto> getAllVenues() {

		String select = "SELECT v.venue_id, v.nme, v.address, c.nme, co.nme ";
		String from = "FROM ubifeed.cities c, ubifeed.venues v, ubifeed.countries co  ";
		String where = "WHERE v.city_id = c.city_id AND c.country_id = co.country_id AND v.dte >= NOW() ";
		String order = "ORDER BY v.dte";

		List<VenueDto> list = new ArrayList<VenueDto>();
		//get the Prepared Statement, it will close automatically
		try(PreparedStatement ps = dal.getPreparedStatement(select + from + where + order)) {

			//init prepared Statement
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				VenueDto toRet = factory.getVenueDto();
				// init the dto that will be returned by the method

				toRet.setVenueId(rs.getInt(1));
				toRet.setName(rs.getString(2));
				toRet.setAddress(rs.getString(3));
				toRet.setCityName(rs.getString(4));
				toRet.setCountryName(rs.getString(5));
				list.add(toRet);

				/*toRet.setVenueId(rs.getInt(1));
				toRet.setName(rs.getString(2));
				toRet.setAddress(rs.getString(3));
				toRet.setDate(rs.getTimestamp(4).toLocalDateTime());
				
				list.add(toRet);*/
			}
			//close the result set
			rs.close();
		}catch(SQLException sqlExcept) {
			sqlExcept.printStackTrace();
			throw new FatalErrorException(sqlExcept);
		}
	
		return list;
	}

	@Override
	public List<RestaurantDto> getAllRestaurants(String venueId) {
		
		String select = "SELECT r.rest_id, r.nme,  r.address, r.descrip, r.email ";
		String from = "FROM ubifeed.restaurants r ";
		String where = "WHERE venue_id =" + venueId + ";";
		
		List<RestaurantDto> list = new ArrayList<RestaurantDto>();
		//get the Prepared Statement, it will close automatically
		try(PreparedStatement ps = dal.getPreparedStatement(select + from + where)) {
			//init prepared Statement
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				RestaurantDto toRet = factory.getRestaurantDto();
				// init the dto that will be returned by the method
				toRet.setRestaurantId(rs.getInt(1));
				toRet.setName(rs.getString(2));
				toRet.setAddress(rs.getString(3));
				toRet.setDescription(rs.getString(4));
				toRet.setEmail(rs.getString(5));
				list.add(toRet);
			}
			//close the result set
			rs.close();
		}catch(SQLException sqlExcept) {
			sqlExcept.printStackTrace();
			throw new FatalErrorException(sqlExcept);
		}
	
		return list;
	}

	@Override
	public List<EventDto> getEvents(String venueId) {
		String select = "SELECT e.event_id, e.nme, e.dte ";
		String from = "FROM ubifeed.events_ e ";
		String where = "WHERE venue_id =" + venueId + " AND e.dte >= NOW();";
		
		List<EventDto> list = new ArrayList<EventDto>();
		
		//get the Prepared Statement, it will close automatically
				try(PreparedStatement ps = dal.getPreparedStatement(select + from + where)) {
					//init prepared Statement
					ResultSet rs = ps.executeQuery();
					while(rs.next()) {
						EventDto toRet = factory.getEventDto();
						// init the dto that will be returned by the method
						toRet.setEventId(rs.getInt(1));
						toRet.setName(rs.getString(2));
						toRet.setDate(rs.getTimestamp(3).toLocalDateTime());
						list.add(toRet);
					}
					//close the result set
					rs.close();
				}catch(SQLException sqlExcept) {
					sqlExcept.printStackTrace();
					throw new FatalErrorException(sqlExcept);
				}
			
				return list;
		
		
	}

	@Override
	public List<MealDto> getMeals(String restaurantId) {
		String select = "SELECT m.meal_id, m.nme, m.price, 2, mc.nme ";
		String from = "FROM ubifeed.meals m, ubifeed.meals_categories mc ";
		String where = "WHERE rest_id =" + restaurantId + " AND m.meal_categ_id = mc.meal_categ_id;";
		
		List<MealDto> list = new ArrayList<MealDto>();
		
		//get the Prepared Statement, it will close automatically
				try(PreparedStatement ps = dal.getPreparedStatement(select + from + where)) {
					//init prepared Statement
					ResultSet rs = ps.executeQuery();
					while(rs.next()) {
						MealDto toRet = factory.getMealDto();
						// init the dto that will be returned by the method
						toRet.setMealId(rs.getInt(1));
						toRet.setName(rs.getString(2));
						toRet.setPrice(rs.getFloat(3));
						toRet.setCategory(rs.getString(4));
						list.add(toRet);
					}
					//close the result set
					rs.close();
				}catch(SQLException sqlExcept) {
					sqlExcept.printStackTrace();
					throw new FatalErrorException(sqlExcept);
				}
			
				return list;
		
	}
	
	
	
	
}
