package irl.tud.ubifeed.business;

import irl.tud.ubifeed.Inject;
import irl.tud.ubifeed.dbaccess.DalServices;
import irl.tud.ubifeed.dbaccess.deliverydao.DeliveryDao;

public class DeliveryUccImpl implements DeliveryUcc {
	
	@Inject
	public DeliveryDao deliveryDao;
	
	@Inject
	public DalServices dal;
}
