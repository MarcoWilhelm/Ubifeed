package irl.tud.ubifeed.dbaccess.userdao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import irl.tud.ubifeed.Inject;
import irl.tud.ubifeed.business.modelfactory.ModelFactory;
import irl.tud.ubifeed.dbaccess.DalBackendServices;
import irl.tud.ubifeed.dbaccess.deliverydao.DeliveryDao;
import irl.tud.ubifeed.event.EventDto;
import irl.tud.ubifeed.exception.FatalErrorException;
import irl.tud.ubifeed.meal.MealDto;
import irl.tud.ubifeed.order.OrderDto;
import irl.tud.ubifeed.pickupstation.PickupStationDto;
import irl.tud.ubifeed.restaurant.RestaurantDto;
import irl.tud.ubifeed.seatcatdto.SeatCatDto;
import irl.tud.ubifeed.user.UserDto;
import irl.tud.ubifeed.venue.VenueDto;

public class UserDaoImpl implements UserDao {

	@Inject
	public ModelFactory	factory;

	@Inject
	public DalBackendServices dal;

	@Inject
	public DeliveryDao deliveryDao;

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
		}
		return toRet;
	}

	@Override
	public UserDto register(UserDto user) {
		int last_inserted_id = 0;
		UserDto test = factory.getUserDto();
		if((test = loginUser(user)) != null && test.getEmail() != null) {
			return null;
		}
		String insert = "INSERT INTO ubifeed.users (user_id, firstn, lastn, passw, email, phone, image)";
		String values = "VALUES(DEFAULT, ?, ?, ?, ?, ?, ?)";
		try(PreparedStatement ps = dal.getPreparedStatement(insert + values)) {
			ps.setString(1, user.getFirstName());
			ps.setString(2, user.getLastName());
			ps.setString(3, user.getPassword());
			ps.setString(4, user.getEmail());
			ps.setString(5, user.getPhone());
			ps.setString(6, user.getProfilePictureName());
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				last_inserted_id = rs.getInt(1);
			}
		}catch(SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		}
		user.setUserId(last_inserted_id);
		return user;
	}



	@Override
	public List<VenueDto> getAllVenues() {

		String select = "SELECT * FROM ubifeed.venues;";
		List<VenueDto> list = new ArrayList<VenueDto>();
		//get the Prepared Statement, it will close automatically
		try(PreparedStatement ps = dal.getPreparedStatement(select)) {

			//init prepared Statement
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				VenueDto toRet = factory.getVenueDto();
				// init the dto that will be returned by the method

				toRet.setVenueId(rs.getInt(1));
				toRet.setName(rs.getString(2));
				toRet.setAddress(rs.getString(3));
				toRet.setImagePath(rs.getString(4));
				//toRet.setCityName(rs.getString(4));
				//toRet.setCountryName(rs.getString(5));
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
		}

		return list;
	}

	@Override
	public List<RestaurantDto> getAllRestaurants(String venueId) {

		String select = "SELECT * FROM ubifeed.restaurants WHERE venue_id = ?";

		List<RestaurantDto> list = new ArrayList<RestaurantDto>();
		//get the Prepared Statement, it will close automatically
		try(PreparedStatement ps = dal.getPreparedStatement(select)) {
			ps.setInt(1, Integer.parseInt(venueId));
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
		}

		return list;
	}

	@Override
	public List<EventDto> getEvents(String venueId) {
		String select = "SELECT e.event_id, e.nme, e.dte ";
		String from = "FROM ubifeed.events_ e ";
		String where = "WHERE venue_id =? AND e.dte >= NOW();";

		List<EventDto> list = new ArrayList<EventDto>();

		//get the Prepared Statement, it will close automatically
		try(PreparedStatement ps = dal.getPreparedStatement(select + from + where)) {
			ps.setInt(1, Integer.parseInt(venueId));
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
		String select = "SELECT m.meal_id, m.nme, m.price, mc.meal_categ_id ";
		String from = "FROM ubifeed.meals m, ubifeed.meals_categories mc ";
		String where = "WHERE rest_id =? AND m.meal_categ_id = mc.meal_categ_id AND isDeleted = 0;";

		List<MealDto> list = new ArrayList<MealDto>();

		//get the Prepared Statement, it will close automatically
		try(PreparedStatement ps = dal.getPreparedStatement(select + from + where)) {
			ps.setInt(1, Integer.parseInt(restaurantId));
			//init prepared Statement
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				MealDto toRet = factory.getMealDto();
				// init the dto that will be returned by the method
				toRet.setMealId(rs.getInt(1));
				toRet.setName(rs.getString(2));
				toRet.setPrice(rs.getFloat(3));
				toRet.setCategoryId(rs.getInt(4));
				list.add(toRet);
			}
			//close the result set
			rs.close();
		}catch(SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		}

		return list;
	}

	@Override
	public List<SeatCatDto> getPickupDetails(String venueId) {		
		String select = "SELECT seat_cat_id, cat_name, venue_id ";
		String from = "FROM ubifeed.seat_categories s ";
		String where = "WHERE s.venue_id = ?";


		List<SeatCatDto> list = new ArrayList<SeatCatDto>();

		try(PreparedStatement ps = dal.getPreparedStatement(select + from + where)) {
			ps.setInt(1, Integer.parseInt(venueId));
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				SeatCatDto toRet = factory.getSeatCatDto();
				toRet.setSeatCatId(rs.getInt(1));
				toRet.setName(rs.getString(2));
				list.add(toRet);
			}
			rs.close();
		} catch(SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		}
		return list;
	}

	@Override
	public List<OrderDto> getAllOrders(String userId) {
		String select = "SELECT o.order_id, o.user_id, o.rest_id, o.pickup_id, o.order_status, r.nme, ps.loc_description ";
		String from = "FROM ubifeed.orders o, ubifeed.restaurants r, ubifeed.pickup_stations ps ";
		String where = "WHERE r.rest_id = o.rest_id AND "
				+ "o.pickup_id = ps.pickup_id AND user_id = ? AND order_status != 'DELIVERED' ";
		String order = "ORDER BY order_id DESC;";
		List<OrderDto> list = new ArrayList<OrderDto>();
		OrderDto toRet = null;
		try (PreparedStatement ps = dal.getPreparedStatement(select + from + where + order)) {
			ps.setInt(1, Integer.parseInt(userId));
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				RestaurantDto restaurant = factory.getRestaurantDto();
				PickupStationDto pickup = factory.getPickupStationDto();
				UserDto user = factory.getUserDto();
				toRet = factory.getOrderDto();

				toRet.setOrderId(rs.getInt(1));

				user.setUserId(rs.getInt(2));
				toRet.setUser(user);

				toRet.setMeals(deliveryDao.getMealFromOrder(toRet.getOrderId()));

				restaurant.setRestaurantId(rs.getInt(3));
				restaurant.setName(rs.getString(6));
				toRet.setRestaurant(restaurant);

				pickup.setPickupId(rs.getInt(4));
				pickup.setLocationDescription(rs.getString(7));
				toRet.setPickupStation(pickup);

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
	public void addOrders(Map<MealDto, Long> basket, int restaurantId, int userId, int seatCatId) {
		String insert = "INSERT INTO ubifeed.orders (order_id, user_id, rest_id, pickup_id, order_status) VALUES ";
		String values = "(DEFAULT, ?, ?, ?, DEFAULT)";

		int orderId = -1;
		OrderDto order = factory.getOrderDto();
		try (PreparedStatement ps = dal.getPreparedStatement(insert + values)) {
			ps.setInt(1, userId);
			ps.setInt(2, restaurantId);
			ps.setInt(3, getPickupFromSeat(seatCatId).getPickupId());
			ps.execute();
			ResultSet rs = ps.getGeneratedKeys();
			if(rs.next()) {
				orderId = rs.getInt(1);
			}
		} catch(SQLException sqlExcept) {
			sqlExcept.printStackTrace();
			throw new FatalErrorException(sqlExcept);
		}
		insert = "INSERT INTO ubifeed.order_meals (quantity, price, order_id, meal_id) VALUES ";
		values = "(?,?,?,?)";
		try (PreparedStatement ps = dal.getPreparedStatement(insert + values)) {
			for(Entry<MealDto, Long>entry : basket.entrySet()){
				try {
					ps.setInt(1, entry.getValue().intValue());
					ps.setDouble(2, entry.getKey().getPrice()*entry.getValue().intValue());
					ps.setInt(3, orderId);
					ps.setInt(4, entry.getKey().getMealId());
				} catch (SQLException sqlExcept) {
					sqlExcept.printStackTrace();
					throw new FatalErrorException(sqlExcept);
				}
			}
		} catch(SQLException sqlExcept) {
			sqlExcept.printStackTrace();
			throw new FatalErrorException(sqlExcept);
		}

	}

	private PickupStationDto getPickupFromSeat(int seatCatId) {
		String selectPickup = "SELECT sc.pickup_id FROM ubifeed.seat_categories sc ";
		String wherePickup = "WHERE sc.seat_cat_id = ?;";
		PickupStationDto pickupStation = factory.getPickupStationDto();

		try (PreparedStatement ps = dal.getPreparedStatement(selectPickup + wherePickup)) {
			ps.setInt(1, seatCatId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				pickupStation.setPickupId(rs.getInt(1));
			}
		} catch(SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		}
		return pickupStation;
	}
}
