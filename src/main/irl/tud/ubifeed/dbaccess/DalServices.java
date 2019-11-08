package irl.tud.ubifeed.dbaccess;

public interface DalServices {
	
	void startTransaction();
	
	void commitTransaction();
	
	void rollbackTransaction();

}
