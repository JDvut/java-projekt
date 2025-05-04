package app_functions;

import java.util.ArrayList;

import exceptions.InvalidGroupIdException;
import exceptions.InvalidIdException;
import exceptions.InvalidMarkException;
import student.Student;

public class ValidateFns {
	public static boolean validateId(int id, ArrayList<Student> studentArray) throws InvalidIdException{
        for (Student student : studentArray) {
            if (student.getId() == id) {
            	return true;
            }
        }
        
        throw new InvalidIdException();
	}
	
	public static boolean validateGroup_id(int group_id) throws InvalidGroupIdException {
		if (group_id != 1 || group_id != 2) {
			throw new InvalidGroupIdException();
		}
		
		return true;
	}
	
	public static boolean validateMark(int mark) throws InvalidMarkException {
		if (mark != 1 || mark != 2 || mark != 3 || mark != 4 || mark != 5) {
			throw new InvalidMarkException();
		}
		
		return true;
	}
}
