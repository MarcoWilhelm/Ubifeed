package irl.tud.ubifeed.business;

import at.favre.lib.crypto.bcrypt.BCrypt;
import irl.tud.ubifeed.Inject;
import irl.tud.ubifeed.dbaccess.DalServices;
import irl.tud.ubifeed.dbaccess.userdao.UserDao;
import irl.tud.ubifeed.user.User;
import irl.tud.ubifeed.user.UserDto;
import irl.tud.ubifeed.venue.VenueDto;

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
	@Override
	public UserDto registerUser(UserDto user) {
		try {
			user.setPassword(BCrypt.withDefaults().hashToString(12, user.getPassword().toCharArray()));
			dal.startTransaction();
			user = userDao.register(user);
			dal.commitTransaction();
			return user;
		} catch (RuntimeException dbfExcept) {
			dal.rollbackTransaction();
			throw new RuntimeException(dbfExcept);
		}
	}
	@Override
	public VenueDto getAllVenues(VenueDto venue) {
		try {
			dal.startTransaction();
			venue = userDao.getAllVenues(venue);
			dal.commitTransaction();
			return venue;
			
		} catch (RuntimeException dbfExcept) {
			dal.rollbackTransaction();
			throw new RuntimeException(dbfExcept);
		}
	}
	
	
}
