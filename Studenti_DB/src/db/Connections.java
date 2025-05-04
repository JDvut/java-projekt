package db;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class Connections {
	private static  volatile Connection conn;
	
	public static Connection connect() {
		conn = null;
	
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:db/student.db");
		} catch (SQLException e) {
			System.out.println("An error has occured while CONNECTING: " + e);
		}
		return conn;
	}
	
	public static void disconnect() {
		if (conn != null) {
			try {
				conn.close();
		    } catch (SQLException e) {
		    	System.out.println("An error has occured while DISCONNECTING: " + e);
		    }
		}
	}
}