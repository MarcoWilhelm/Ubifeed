package irl.tud.ubifeed.dbaccess.userdao;

import irl.tud.ubifeed.Inject;
import irl.tud.ubifeed.business.modelfactory.ModelFactory;
import irl.tud.ubifeed.dbaccess.DalBackendServices;

public class UserDaoImpl implements UserDao {
	
	@Inject
	public ModelFactory	factory;
	
	@Inject
	public DalBackendServices dal;
}
