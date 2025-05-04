package app;

import java.util.Scanner;

import app_functions.HelperFns;
import app_functions.ValidateFns;
import db_functions.Select;
import db_functions.Update;
import exceptions.InvalidGroupIdException;
import exceptions.InvalidIdException;
import exceptions.InvalidMarkException;
import student.Student;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class App {
	public static void main(String[] args) {
		ArrayList<Student> studentArray = new ArrayList<Student>();

		Scanner sc = new Scanner(System.in);
		int userInput = 0;
		boolean running = true;
		
		Select.getAll(studentArray);
		while (running) {
			for (int i = 0; i < studentArray.size(); i++) {
				studentArray.get(i).printAll();
			}
			System.out.println();
			System.out.println("Options: ");
			System.out.println("1 \t Add new Student (Required: first_name, second_name, birth_year, group-id).");
			System.out.println("2 \t Add new Mark (Required: student_id, mark).");
			System.out.println("3 \t Remove one Student (Required: student_id).");
			System.out.println("4 \t Find one Student (Required: student_id).");
			System.out.println("5 \t Print skill from one Student (Required: student_id).");
			System.out.println("6 \t Print alphabetically sorted list of Students (By: seond_name).");
			System.out.println("7 \t Print the overall average of each group.");
			System.out.println("8 \t Print the total count of Students in each group.");
			System.out.println("9 \t Get the Students in a Students.txt file.");
			System.out.println("10 \t Remove one Student from a file (Required: student_id).");
			System.out.println("11 \t Find one Student in a file (Required: student_id).");
			System.out.println("12 \t Quit.");

			userInput = HelperFns.readUserInputInt(sc);
			
			switch (userInput) {
				case 1:
					try {
						int id = studentArray.get(studentArray.size() - 1).getId() + 1;
						
						System.out.print("first_name: ");
						String first_name = sc.next();
						
						System.out.print("second_name: ");
						String second_name = sc.next();
						
						System.out.print("birth_year: ");
						int birth_year = sc.nextInt();
						
						System.out.print("group_id (1 - TComm, 2 - CSec): ");
						int group_id = sc.nextInt();
						ValidateFns.validateGroup_id(group_id);
						
						ArrayList<Integer> initArray = new ArrayList<Integer>();
						
						studentArray.add(new Student(id, first_name, second_name, birth_year, group_id, initArray));
						
						System.out.println(studentArray.size());
					} catch (InvalidGroupIdException e) {
						System.out.println(e);
					} catch (Exception e) {
						System.out.println(e);
					}
					break;
				case 2:
					try {
						System.out.print("student_id: ");
						int student_id = sc.nextInt();
						ValidateFns.validateId(student_id, studentArray);

						System.out.print("mark: ");
						int mark = sc.nextInt();
						ValidateFns.validateMark(mark);
						
						for (int i = 0; i < studentArray.size(); i++) {
							studentArray.get(i).newMark(student_id, mark);
						}
					} catch (InvalidIdException e) {
						System.out.println(e);
					} catch (InvalidMarkException e) {
						System.out.println(e);
					} catch (Exception e) {
						System.out.println(e);
					}
					break;
				case 3:
					try {
						System.out.print("student_id: ");
						int student_id = sc.nextInt();
						ValidateFns.validateId(student_id, studentArray);
						
						for (int i = 0; i < studentArray.size(); i++) {
							if (studentArray.get(i).getId() == student_id) {
								studentArray.remove(i);
							}
						}
					} catch (InvalidIdException e) {
						System.out.println(e);
					} catch (Exception e) {
						System.out.println(e);
					}
					break;
				case 4:
					try {
						System.out.print("student_id: ");
						int student_id = sc.nextInt();
						
						for (int i = 0; i < studentArray.size(); i++) {
							if (studentArray.get(i).getId() == student_id) {
								studentArray.get(i).getOne(student_id);
							}
						}
					} catch (Exception e) {
						System.out.println(e);
					}
					break;
				case 5:					
					try {
						System.out.print("student_id: ");
						int student_id = sc.nextInt();
						ValidateFns.validateId(student_id, studentArray);
						
						for (int i = 0; i < studentArray.size(); i++) {
							studentArray.get(i).getOneIdNameFunction(student_id);
						}
					} catch (InvalidIdException e) {
						System.out.println(e);
					} catch (Exception e) {
						System.out.println(e);
					}
					break;
				case 6:
					try {
						System.out.println("Sorted: ");
						
						@SuppressWarnings("unchecked")
						ArrayList<Student> tempA = (ArrayList<Student>)studentArray.clone();
						
						Collections.sort(tempA);
						
						for (int i = 0; i < tempA.size(); i++) {
							tempA.get(i).printAll();
						}
						System.out.println();
						
					} catch (Exception e) {
						System.out.println(e);
					}
					break;
				case 7:
					try {
						double tempT = 0;
						double tempC = 0;
						
						for (int i = 0; i < studentArray.size(); i++) {
							if (studentArray.get(i).getGroup_id() == 1) {
								for (int j = 0; j < studentArray.get(i).getMarks().size(); j++) {
									tempT += studentArray.get(i).getMarks().get(j);
								}
							} else {
								for (int j = 0; j < studentArray.get(i).getMarks().size(); j++) {
									tempC += studentArray.get(i).getMarks().get(j);
								}
							}
						}
						
						studentArray.get(0).getGroupAverage(tempT, tempC);
						
						System.out.println();
					} catch (Exception e) {
						System.out.println(e);
					}
					break;
				case 8:
					try {
						System.out.println("Count: ");
						
						studentArray.get(0).getGroupCount();
						
						System.out.println();
					} catch (Exception e) {
						System.out.println(e);
					}
					break;
				case 9:
					try {
						File fileObj = new File("Students.txt");
						FileWriter writer = new FileWriter(fileObj);
						
						for (int i = 0; i < studentArray.size(); i++) {
							writer.write(
								"id: " + studentArray.get(i).getId() + " " + 
								"first_name: " + studentArray.get(i).getFirst_name() + " " +
								"second_name: " + studentArray.get(i).getSecond_name() + " " +
								"birth_year: " + studentArray.get(i).getBirth_year() + " " +
								(studentArray.get(i).getGroup_id() == 1 ? "Telecommunication" : "Cybersecurity") + " " +
								(studentArray.get(i).getMarks().size() == 0 ? "NoMarks" : studentArray.get(i).getMarks()) +
								"\n"
							);
						}
						
						writer.close();
					} catch (IOException e) {
						System.out.println(e);
					} catch (Exception e) {
						System.out.println(e);
					}
					break;
				case 10:
					try {
						File fileObj = new File("Students.txt");
						FileWriter writer = new FileWriter(fileObj);
						
						@SuppressWarnings("unchecked")
						ArrayList<Student> tempA = (ArrayList<Student>)studentArray.clone();
												
						System.out.print("student_id: ");
						int student_id = sc.nextInt();
						ValidateFns.validateId(student_id, tempA);
						
						for (int i = 0; i < tempA.size(); i++) {
							if (tempA.get(i).getId() == student_id) {
								tempA.remove(i);
							}
						}
						
						for (int i = 0; i < tempA.size(); i++) {
							writer.write(
								"id: " + tempA.get(i).getId() + " " + 
								"first_name: " + tempA.get(i).getFirst_name() + " " +
								"second_name: " + tempA.get(i).getSecond_name() + " " +
								"birth_year: " + tempA.get(i).getBirth_year() + " " +
								(tempA.get(i).getGroup_id() == 1 ? "Telecommunication" : "Cybersecurity") + " " +
								(tempA.get(i).getMarks().size() == 0 ? "NoMarks" : tempA.get(i).getMarks()) +
								"\n"
							);
						}
						
						writer.close();
					} catch (InvalidIdException e) {
						System.out.println(e);
					} catch (IOException e) {
						System.out.println(e);
					} catch (Exception e) {
						System.out.println(e);
					}
					break;
				case 11:
					try {
						File fileObj = new File("Students.txt");
						
						System.out.print("student_id: ");
						int student_id = sc.nextInt();
						
						try (Scanner reader = new Scanner(fileObj)) {
							while (reader.hasNextLine()) {
								String line = reader.nextLine();
								
								StringBuilder fileId = new StringBuilder();
								for (int i = 4; i < line.length(); i++) {
									if (line.charAt(i) == ' ') break;									
									fileId.append(line.charAt(i));
								}
								
								if (Integer.parseInt(fileId.toString()) == student_id) {
									System.out.println(line);
								}
							}
							
							System.out.println();
							
							reader.close();
						}						
					} catch (FileNotFoundException e) {
						System.out.println("FileNotFoundException" + e);
					} catch (Exception e) {
						System.out.println(e);
					}
					break;
				case 12:
					ArrayList<Student> tempA = new ArrayList<Student>();
					Select.getAll(tempA);
					
					Update.beforeClosing(studentArray, tempA);
					running = false;
					break;
			}
		}
		
		System.out.println("Application quit.");
	}
}
