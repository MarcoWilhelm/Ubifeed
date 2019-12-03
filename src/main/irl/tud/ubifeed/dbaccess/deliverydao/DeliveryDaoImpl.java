package irl.tud.ubifeed.dbaccess.deliverydao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import irl.tud.ubifeed.Inject;
import irl.tud.ubifeed.business.modelfactory.ModelFactory;
import irl.tud.ubifeed.dbaccess.DalBackendServices;
import irl.tud.ubifeed.order.OrderDto;
import irl.tud.ubifeed.pickupstation.PickupStation;
import irl.tud.ubifeed.pickupstation.PickupStationDto;
import irl.tud.ubifeed.restaurant.RestaurantDto;

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
		}catch(SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		}
		return toRet;
	}

	@Override
	public List<OrderDto> getAllOrders(String pickupId) {
		String select = "SELECT o.order_id, o.user_id, o.rest_id, o.pickup_id, o.order_status ";
		String from = "FROM ubifeed.orders o ";
		String where = "WHERE o.order_status != 'DELIVERED' AND o.pickup_id = ? ";
		String order = "ORDER BY order_id DESC;";
		List<OrderDto> list = new ArrayList<OrderDto>();
		
		try (PreparedStatement ps = dal.getPreparedStatement(select + from + where + order)) {
			ps.setInt(1, Integer.parseInt(pickupId));
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
		return list;	}
}
