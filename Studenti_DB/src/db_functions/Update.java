package db_functions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import db.Connections;
import student.Student;

public class Update {
	public static void beforeClosing(ArrayList<Student> studentsArray) {
		String q = """
		""";
		
		Connection conn = Connections.connect();
		
        try (
        	PreparedStatement pS = conn.prepareStatement(q);
        ) {
            pS.executeQuery();
         } catch (SQLException e) {
        	 System.out.println(e.getMessage());
        }
	}
}
