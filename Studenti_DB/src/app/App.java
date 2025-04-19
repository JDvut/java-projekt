package app;

import java.util.Scanner;

import app_functions.HelperFns;
import db_functions.Delete;
import db_functions.Insert;
import db_functions.Select;

public class App {
	public static void main(String[] args) {
		try {
			System.out.println("Success :)");
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		
		Scanner sc = new Scanner(System.in);
		int userInput = 0;
		boolean running = true;
		
		while (running) {
			
			Select.getAll();
			System.out.println();
			System.out.println("Options: ");
			System.out.println("1 \t Add new Student (Required: first_name, second_name, birth_year, group-id).");
			System.out.println("2 \t Add new Mark (Required: student_id, mark).");
			System.out.println("3 \t Remove Student (Required: student_id).");
			System.out.println("4 \t Find one Student (Required: student_id).");
			System.out.println("5 \t Print skill from one Student (Required: student_id).");
			System.out.println("6 \t Print alphabetically sorted list of Students (By: seond_name).");
			System.out.println("7 \t Print the overall average of each group.");
			System.out.println("8 \t Print the total count of Students in each group.");
			System.out.println("9 \t Get the Students in a Students.txt file");
			System.out.println("10 \t Quit.");
			
			userInput = HelperFns.readUserInputInt(sc);
			
			switch (userInput) {
				case 1:
					try {
						System.out.print("first_name: ");
						String first_name = sc.next();
						
						System.out.print("second_name: ");
						String second_name = sc.next();
						
						System.out.print("birth_year: ");
						int birth_year = sc.nextInt();
						
						System.out.print("group_id (1 - TComm, 2 - CSec): ");
						int group_id = sc.nextInt();
						
						Insert.newStudent(first_name, second_name, birth_year, group_id);
					} catch (Exception e) {
						System.out.println(e);
					}
					break;
				case 2:
					try {
						System.out.print("student_id: ");
						int student_id = sc.nextInt();
						
						System.out.print("mark: ");
						int mark = sc.nextInt();
						
						Insert.newMark(student_id, mark);
					} catch (Exception e) {
						System.out.println(e);
					}
					break;
				case 3:
					try {
						System.out.print("student_id: ");
						int student_id = sc.nextInt();
						
						Delete.deleteStudent(student_id);
					} catch (Exception e) {
						System.out.println(e);
					}
					break;
				case 4:
					try {
						System.out.print("student_id: ");
						int student_id = sc.nextInt();
						
						Select.getOne(student_id);
					} catch (Exception e) {
						System.out.println(e);
					}
					break;
				case 5:
					try {
						System.out.print("student_id: ");
						int student_id = sc.nextInt();
						
						Select.getOneIdNameFunction(student_id);
					} catch (Exception e) {
						System.out.println(e);
					}
					break;
				case 6:
					try {
						System.out.println("Sorted: ");
						
						Select.getAllSorted();
					} catch (Exception e) {
						System.out.println(e);
					}
					break;
				case 7:
					try {
						System.out.println("Average: ");
						
						Select.getGroupAverage();
					} catch (Exception e) {
						System.out.println(e);
					}
					break;
				case 8:
					try {
						System.out.println("Count: ");
						
						Select.getGroupCount();
					} catch (Exception e) {
						System.out.println(e);
					}
					break;
				case 9:
					try {
						
					} catch (Exception e) {
						System.out.println(e);
					}
					break;
				case 10:
					running = false;
					break;
			}
		}
		
		System.out.println("Application quit.");
	}
}
