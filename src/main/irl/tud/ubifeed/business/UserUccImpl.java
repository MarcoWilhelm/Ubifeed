package irl.tud.ubifeed.business;

import at.favre.lib.crypto.bcrypt.BCrypt;
import irl.tud.ubifeed.Inject;
import irl.tud.ubifeed.dbaccess.DalServices;
import irl.tud.ubifeed.dbaccess.userdao.UserDao;
import irl.tud.ubifeed.user.User;
import irl.tud.ubifeed.user.UserDto;

public class UserUccImpl implements UserUcc {

	@Inject
	public UserDao userDao;

	@Inject
	public DalServices dal;

	@Override
	public UserDto loginUser(UserDto user) {
		User toRet;
		try {

			// init user thanks to UserDao
			dal.startTransaction();
			toRet = (User) userDao.loginUser(user);
			dal.commitTransaction();
		} catch (RuntimeException dbfExcept) {
			dal.rollbackTransaction();
			throw new RuntimeException(dbfExcept);
		}

		if (toRet == null || toRet.getEmail() == null || !toRet.verifyPassword(user.getPassword())) {
			return null;
		}
		return toRet;
	}

	public UserDto registerUser(UserDto user) {
		try {
			user.setPassword(BCrypt.withDefaults().hashToString(12, user.getPassword().toCharArray()));
			System.out.println(user.getPassword());
			dal.startTransaction();
			user = userDao.register(user);
			dal.commitTransaction();
			return user;
		} catch (RuntimeException dbfExcept) {
			dal.rollbackTransaction();
			throw new RuntimeException(dbfExcept);
		}
	}
}
