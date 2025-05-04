package db_functions;

import db.Connections;
import student.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import app_functions.HelperFns;

public class Select {
	public static void getAll(ArrayList<Student> studentsArray) {
		String q = """
			SELECT
			    S.ID,
			    S.first_name,
			    S.second_name,
			    S.birth_year,
			    S.group_id,
			    G.group_name,
			    GROUP_CONCAT(Marks.mark, ', ') AS all_marks
			FROM
			    Students S
			JOIN
			    Groups G ON S.group_id = G.ID
			LEFT JOIN
			    Student_multiple_Marks SG ON S.ID = SG.student_id
			LEFT JOIN
			    Marks ON SG.mark_id = Marks.ID
			GROUP BY
			    S.ID, S.first_name, S.second_name, S.birth_year, G.group_name;
		""";
		
		Connection conn = Connections.connect();
		
        try (
        	PreparedStatement pS = conn.prepareStatement(q);
        	ResultSet rs = pS.executeQuery();
        ) {
            while (rs.next()) {
            	String marks = rs.getString("all_marks");
            	ArrayList<Integer> iArray = new ArrayList<Integer>();
            	
            	if (marks != null) {
            		String sArray [] = marks.split(", ");
            		for (String s:sArray) {
            			try {
                			iArray.add(Integer.parseInt(s));
	            		} catch (NumberFormatException e) {
	            	        System.err.println("Error: Could not parse as an integer: " + e);
	            	    }
                	}
            	}
            	
            	studentsArray.add(new Student(
            		rs.getInt("ID"),
            		rs.getString("first_name"),
            		rs.getString("second_name"),
            		rs.getInt("birth_year"),
            		rs.getInt("group_id"),
            		iArray)
            	);
            }
         } catch (SQLException e) {
        	 System.out.println(e.getMessage());
        }
	}
	
	public static int getCountId() {
		String q = """
			SELECT
				MAX(S.ID)
			FROM
				Students S;
		""";
		
		Connection conn = Connections.connect();
		int finalCount = 0;
		
		try (
			PreparedStatement pS = conn.prepareStatement(q);
			ResultSet rs = pS.executeQuery();
		) {
			while (rs.next()) {
				finalCount = rs.getInt(1);
			}
		} catch (SQLException e) {
       	 System.out.println(e.getMessage());
        }
				
		return finalCount;
	}
	
	public static void getIdName() {
		String q = """
			SELECT
				S.ID,
				S.first_name,
				S.second_name
			FROM
				Students S;
		""";
		
		Connection conn = Connections.connect();
		
		try (
			PreparedStatement pS = conn.prepareStatement(q);
			ResultSet rs = pS.executeQuery();
		) {
			while (rs.next()) {
				System.out.println(
					rs.getInt("ID") + "\t" + 
					rs.getString("first_name") + "\t" + 
					rs.getString("second_name")
				);
			}
		} catch (SQLException e) {
       	 System.out.println(e.getMessage());
       }
	}
	
	public static void getOne(int student_id) {
		if (student_id > Select.getCountId() || student_id < 1) {
			throw new NullPointerException("ID must be valid.");
		}
		
		String q = """
			SELECT
			    S.ID,
			    S.first_name,
			    S.second_name,
			    S.birth_year,
			    AVG(Marks.mark) AS average_mark
			FROM
			    Students S
			LEFT JOIN
			    Student_multiple_Marks SG ON S.ID = SG.student_id
			LEFT JOIN
			    Marks ON SG.mark_id = Marks.ID
			WHERE
				S.ID = ?
			GROUP BY
			    S.ID, S.first_name, S.second_name, S.birth_year;
		""";
		
		Connection conn = Connections.connect();
		
		try (
			PreparedStatement pS = conn.prepareStatement(q);
		) {
			pS.setInt(1, student_id);
			
			ResultSet rs = pS.executeQuery();
			
			while (rs.next()) {
				double average = rs.getDouble("average_mark");
            	
            	System.out.println(
            		rs.getInt("ID") + "\t" + 
            		rs.getString("first_name") + "\t" + 
            		rs.getString("second_name") + "\t" + 
            		rs.getInt("birth_year") + "\t" + 
            		(average != 0 ? average : "No marks")
            	);
			}
		} catch (SQLException e) {
       	 System.out.println(e.getMessage());
       }
	}
	
	public static void getOneIdNameFunction(int student_id) {
		if (student_id > Select.getCountId() || student_id < 1) {
			throw new NullPointerException("ID must be valid.");
		}
		
		String q = """
			SELECT
				S.first_name,
				S.second_name,
				S.group_id
			FROM
				Students S
			WHERE
				S.ID = ?;
		""";
		
		Connection conn = Connections.connect();
		
		try (
			PreparedStatement pS = conn.prepareStatement(q);
		) {
			pS.setInt(1, student_id);
			
			ResultSet rs = pS.executeQuery();
			
			while (rs.next()) {
				if (rs.getInt("group_id") == 1) {
					System.out.println(
						student_id + "\t" + 
						HelperFns.groupMorse(rs.getString("first_name")) + "\t" + 
						HelperFns.groupMorse(rs.getString("second_name"))
					);
				} else {
					System.out.println(
						HelperFns.groupHash(rs.getString("first_name")) + "\t" + 
						HelperFns.groupHash(rs.getString("second_name"))
					);
				}
			}
		} catch (SQLException e) {
       	 System.out.println(e.getMessage());
       }
	}
	
	public static void getGroupAverage() {
		String q1 = """
			SELECT
			    AVG(Marks.mark) AS average_mark
			FROM
			    Students S
			LEFT JOIN
			    Student_multiple_Marks SG ON S.ID = SG.student_id
			LEFT JOIN
			    Marks ON SG.mark_id = Marks.ID
			WHERE
				S.group_id = 1;
		""";
		
		String q2 = """
			SELECT
				AVG(Marks.mark) AS average_mark
			FROM
			    Students S
			LEFT JOIN
			    Student_multiple_Marks SG ON S.ID = SG.student_id
			LEFT JOIN
			    Marks ON SG.mark_id = Marks.ID
			WHERE
				S.group_id = 2;
		""";
		
		Connection conn = Connections.connect();

		try (
				PreparedStatement pS1 = conn.prepareStatement(q1);
				ResultSet rs1 = pS1.executeQuery();
				
				PreparedStatement pS2 = conn.prepareStatement(q2);
				ResultSet rs2 = pS2.executeQuery();
		) {
			while (rs1.next()) {
				double average = rs1.getDouble("average_mark");
				System.out.print("Telecommunication: " + (average != 0 ? average : "No marks") + "\t");
			}
				
			while (rs2.next()) {
				double average = rs2.getDouble("average_mark");
				System.out.println("Cybersecurity: " + (average != 0 ? average : "No marks"));
			}
		} catch (SQLException e) {
	       	 System.out.println(e.getMessage());
	    }
	}
	
	public static void getGroupCount() {
		String q = """
			SELECT
			    G.group_name,
			    COUNT(S.ID) AS number_of_students
			FROM
			    Groups G
			LEFT JOIN
			    Students S ON G.ID = S.group_id
			GROUP BY
			    G.group_name;
		""";
		
		Connection conn = Connections.connect();

		try (
				PreparedStatement pS = conn.prepareStatement(q);
				ResultSet rs = pS.executeQuery();
				
		) {
			while (rs.next()) {
				int count = rs.getInt("number_of_students");
				System.out.println(rs.getString("group_name") + "\t" + count);
			}

		} catch (SQLException e) {
	       	 System.out.println(e.getMessage());
	    }
	}
	
	public static void getAllSorted() {
		String q = """
			SELECT
			    S.ID,
			    S.first_name,
			    S.second_name,
			    S.birth_year,
			    G.group_name,
			    AVG(Marks.mark) AS average_mark
			FROM
			    Students S
			JOIN
				  Groups G ON S.group_id = G.ID
			LEFT JOIN
			    Student_multiple_Marks SG ON S.ID = SG.student_id
			LEFT JOIN
			    Marks ON SG.mark_id = Marks.ID
			GROUP BY
				S.ID, S.first_name, S.second_name, S.birth_year, G.group_name
			ORDER BY
				second_name COLLATE NOCASE ASC;
		""";
		
		Connection conn = Connections.connect();
		
        try (
        	PreparedStatement pS = conn.prepareStatement(q);
        	ResultSet rs = pS.executeQuery();
        ) {
            while (rs.next()) {
            	Double average = rs.getDouble("average_mark");
            	
            	System.out.println(
            		rs.getInt("ID") + "\t" + 
            		rs.getString("first_name") + "\t" + 
            		rs.getString("second_name") + "\t" + 
            		rs.getInt("birth_year") + "\t" + 
            		(average != 0 ? average : "No marks")
            	);
            }
         } catch (SQLException e) {
        	 System.out.println(e.getMessage());
        }
	}
}
