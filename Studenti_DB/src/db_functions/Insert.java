package db_functions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import db.Connections;

public class Insert {
	public static void newStudent(String first_name, String second_name, int birth_year, int group_id) {
		if (first_name == null || second_name == null || birth_year < 1 || group_id < 1 || group_id > 2) {
			throw new NullPointerException("first_name, second_name, birth_year (1 and above), group_id (1 or 2) have to be correctly input.");
		}
		
		Connection conn = Connections.connect();
		
		String q = """
			INSERT INTO
				Students (first_name, second_name, birth_year, group_id)
			VALUES
				(?, ?, ?, ?);
		""";
		
		try (
			PreparedStatement pS = conn.prepareStatement(q);
		) {
			pS.setString(1, first_name);
			pS.setString(2, second_name);
			pS.setInt(3, birth_year);
			pS.setInt(4, group_id);
			
			pS.executeUpdate();
		} catch (SQLException e) {
       	 System.out.println(e.getMessage());
       }
	}
	
	public static void newMark(int student_id, int mark_id) {
		if (student_id > Select.getCountId() || student_id < 1 || mark_id < 1 || mark_id > 5) {
			throw new NullPointerException("student_id must be valid and mark must be and integer from 1 to 5.");
		}
		
		Connection conn = Connections.connect();
		
		String q = """
			INSERT INTO
				Student_multiple_Marks (student_id, mark_id)
			VALUES
				(?, ?);
		""";
		
		try (
			PreparedStatement pS = conn.prepareStatement(q);
		) {
			pS.setInt(1, student_id);
			pS.setInt(2, mark_id);
			
			pS.executeUpdate();
		} catch (SQLException e) {
       	 System.out.println(e.getMessage());
       }
	}
}
