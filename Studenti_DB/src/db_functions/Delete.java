package db_functions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.Connections;

public class Delete {
	public static void deleteStudent(int student_id) {
		if (student_id > Select.getCountId() || student_id < 1) {
			throw new NullPointerException("student_id must be valid.");
		}
		
		Connection conn = Connections.connect();

	    try {
	        conn.setAutoCommit(false);

			String q1 = """
					DELETE FROM
						Student_multiple_Marks
					WHERE
						student_id = ?;
				""";
				
				
				try (
					PreparedStatement pS1 = conn.prepareStatement(q1);
				) {
					pS1.setInt(1, student_id);
					
					pS1.executeUpdate();
				} catch (SQLException e) {
		       	 System.out.println(e.getMessage());
		        }
				
				String q2 = """
					DELETE FROM
						Students
					WHERE
						ID = ?;
				""";
					
				try (
					PreparedStatement pS2 = conn.prepareStatement(q2);
				) {
					pS2.setInt(1, student_id);
						
					pS2.executeUpdate();
				} catch (SQLException e) {
					System.out.println(e.getMessage());
			    }
	        
	        conn.commit(); 
	    } catch (SQLException e) {
	        try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			} 
	        
	        e.printStackTrace();
	    } finally {
	        try {
	            conn.setAutoCommit(true); 
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}
}
