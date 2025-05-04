package db_functions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.Connections;
import student.Student;

public class Update {

    public static void beforeClosing(List<Student> currentStudents, List<Student> initialStudents) {
        try (Connection conn = Connections.connect()) {
            if (conn != null) {
                conn.setAutoCommit(false);
                
                for (Student currentStudent : currentStudents) {
                    Student initialStudent = findInitialStudent(initialStudents, currentStudent.getId());
                    
                    if (initialStudent == null) {
                        int newStudentId = insertNewStudent(conn, currentStudent);
                        insertStudentMarks(conn, newStudentId, currentStudent.getMarks());
                    } else {
                        updateStudentDetails(conn, currentStudent);
                        updateStudentMarks(conn, currentStudent.getId(), currentStudent.getMarks(), initialStudent.getMarks());
                    }
                }

                for (Student initialStudent : initialStudents) {
                    if (findCurrentStudent(currentStudents, initialStudent.getId()) == null) {
                        deleteStudentAndMarks(conn, initialStudent.getId());
                    }
                }

                conn.commit();
                System.out.println("Database updated successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Error updating database: " + e.getMessage());
            if (Connections.connect() != null) {
                try {
                    Connections.connect().rollback();
                    System.err.println("Transaction rolled back.");
                } catch (SQLException ex) {
                    System.err.println("Error rolling back transaction: " + ex.getMessage());
                }
            }
        } finally {
            if (Connections.connect() != null) {
                try {
                    Connections.connect().setAutoCommit(true);
                } catch (SQLException e) {
                    System.err.println("Error resetting auto-commit: " + e.getMessage());
                }
                Connections.disconnect();
            }
        }
    }

    private static Student findInitialStudent(List<Student> initialStudents, int id) {
        for (Student student : initialStudents) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }

    private static Student findCurrentStudent(List<Student> currentStudents, int id) {
        for (Student student : currentStudents) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }

    private static int insertNewStudent(Connection conn, Student student) throws SQLException {
        String sql = "INSERT INTO Students (first_name, second_name, birth_year, group_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, student.getFirst_name());
            pstmt.setString(2, student.getSecond_name());
            pstmt.setInt(3, student.getBirth_year());
            pstmt.setInt(4, student.getGroup_id());
            pstmt.executeUpdate();
            java.sql.ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Creating student failed, no ID obtained.");
            }
        }
    }

    private static void insertStudentMarks(Connection conn, int studentId, List<Integer> marks) throws SQLException {
        String sql = "INSERT INTO Student_multiple_Marks (student_id, mark_id) VALUES (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (Integer markId : marks) {
                pstmt.setInt(1, studentId);
                pstmt.setInt(2, markId);
                pstmt.executeUpdate();
            }
        }
    }

    private static void updateStudentDetails(Connection conn, Student student) throws SQLException {
        String sql = "UPDATE Students SET first_name = ?, second_name = ?, birth_year = ?, group_id = ? WHERE ID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, student.getFirst_name());
            pstmt.setString(2, student.getSecond_name());
            pstmt.setInt(3, student.getBirth_year());
            pstmt.setInt(4, student.getGroup_id());
            pstmt.setInt(5, student.getId());
            pstmt.executeUpdate();
        }
    }

    private static void updateStudentMarks(Connection conn, int studentId, List<Integer> currentMarks, List<Integer> initialMarks) throws SQLException {
        List<Integer> marksToAdd = new ArrayList<>(currentMarks);
        marksToAdd.removeAll(initialMarks);

        List<Integer> marksToRemove = new ArrayList<>(initialMarks);
        marksToRemove.removeAll(currentMarks);

        String insertSql = "INSERT INTO Student_multiple_Marks (student_id, mark_id) VALUES (?, ?)";
        try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
            for (Integer markId : marksToAdd) {
                insertStmt.setInt(1, studentId);
                insertStmt.setInt(2, markId);
                insertStmt.executeUpdate();
            }
        }

        String deleteSql = "DELETE FROM Student_multiple_Marks WHERE student_id = ? AND mark_id = ?";
        try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
            for (Integer markId : marksToRemove) {
                deleteStmt.setInt(1, studentId);
                deleteStmt.setInt(2, markId);
                deleteStmt.executeUpdate();
            }
        }
    }

    private static void deleteStudentAndMarks(Connection conn, int studentId) throws SQLException {
        try (PreparedStatement deleteMarksStmt = conn.prepareStatement("DELETE FROM Student_multiple_Marks WHERE student_id = ?")) {
            deleteMarksStmt.setInt(1, studentId);
            deleteMarksStmt.executeUpdate();
        }
        try (PreparedStatement deleteStudentStmt = conn.prepareStatement("DELETE FROM Students WHERE ID = ?")) {
            deleteStudentStmt.setInt(1, studentId);
            deleteStudentStmt.executeUpdate();
        }
    }
}