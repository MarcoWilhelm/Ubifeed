package irl.tud.ubifeed.business;

import irl.tud.ubifeed.Inject;
import irl.tud.ubifeed.dbaccess.DalServices;
import irl.tud.ubifeed.dbaccess.userdao.UserDao;

public class UserUccImpl implements UserUcc {

	@Inject
	public UserDao userDao;
	
	@Inject
	public DalServices dal;
}
