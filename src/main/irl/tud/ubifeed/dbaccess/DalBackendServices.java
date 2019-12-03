package irl.tud.ubifeed.dbaccess;

import java.sql.PreparedStatement;

public interface DalBackendServices {
	
	PreparedStatement getPreparedStatement(String query);
	
}
