package irl.tud.ubifeed.dbaccess.deliverydao;

import irl.tud.ubifeed.Inject;
import irl.tud.ubifeed.business.modelfactory.ModelFactory;
import irl.tud.ubifeed.dbaccess.DalBackendServices;

public class DeliveryDaoImpl implements DeliveryDao {
	
	@Inject
	public ModelFactory	factory;
	
	@Inject
	public DalBackendServices dal;
}
