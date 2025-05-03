package student;

import java.util.ArrayList;

import app_functions.HelperFns;

public class Student implements Comparable<Student> {
/*
	data structure
	
	[
		{
			"id": 1,
			"first_name": "A",
			"second_name": "B",
			"birth_year": 1992,
			"group_id": 1,
			"marks": [1, 1, 1, 1] 
		}
	]
*/
	
	private int id;
	private String first_name;
	private String second_name;
	private int birth_year;
	private int group_id;
	private ArrayList<Integer> marks = new ArrayList<Integer>();
	
	private static double tel = 0;
	private static double cyb = 0;
	
	public Student(int _id, String _first_name, String _second_name, int _birth_year, int _group_id, ArrayList<Integer> _marks) {
		this.id = _id;
		this.first_name = _first_name;
		this.second_name = _second_name;
		this.birth_year = _birth_year;
		this.group_id = _group_id;
		
		for (int i = 0; i < _marks.size(); i++) {
			this.marks.add(_marks.get(i));
		}
		
		if (_group_id == 1) {
			tel++;
		} else {
			cyb++;
		}
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getSecond_name() {
		return second_name;
	}

	public void setSecond_name(String second_name) {
		this.second_name = second_name;
	}

	public int getBirth_year() {
		return birth_year;
	}

	public void setBirth_year(int birth_year) {
		this.birth_year = birth_year;
	}

	public int getGroup_id() {
		return group_id;
	}

	public void setGroup_id(int group_id) {
		this.group_id = group_id;
	}

	public ArrayList<Integer> getMarks() {
		return marks;
	}

	public void setMarks(ArrayList<Integer> marks) {
		this.marks = marks;
	}
	
	@Override
	public int compareTo(Student o) {
		return this.getSecond_name().compareTo(o.getSecond_name());
	}
	
	public void newMark(int id, int mark) {
		if (this.id == id) {
			this.marks.add(mark);
		}
	}
	
	public void printAll() {
		System.out.println(
			this.id + "\t" + 
			this.first_name + "\t" + 
			this.second_name + "\t" +
			this.birth_year + "\t" + 
			(this.group_id == 1 ? "Telecommunication" : "Cybersecurity") + "\t" +
			(this.marks.size() == 0 ? "no marks" : this.marks)
		);
	}
	
	public void getOne(int id) {
		double average;
		double temp = 0;
		
		if (this.id == id) {
			for (int i = 0; i < this.marks.size(); i++) {
				temp += this.marks.get(i);
			}
			
			average = temp/this.marks.size();
			
			System.out.println(
				this.id + "\t" +
				this.first_name + "\t" +
				this.second_name + "\t" +
				this.birth_year + "\t" +
				(average == 0 ? "no marks" : average)
			);
		}
	}
	
	public void getOneIdNameFunction(int id) {
		if (this.id == id) {
			if (this.group_id == 1) {
				System.out.println(
					this.id + "\t" + 
					HelperFns.groupMorse(this.first_name) + "\t" + 
					HelperFns.groupMorse(this.second_name)
				);
			} else {
				System.out.println(
					HelperFns.groupHash(this.first_name) + "\t" + 
					HelperFns.groupHash(this.second_name)
				);
			}
		}
	}
	
	public void getGroupAverage(double marksT, double marksC) {
		double averageT = marksT/tel;
		double averageC = marksC/cyb;
		
		System.out.println(
			"Telecommunication avgerage: " + averageT + "\n" + "Cybersecurity avgerage: " + averageC
		);
	}
	
	public void getGroupCount() {
		System.out.println(
				"Telecommunication count: " + tel + "\n" + "Cybersecurity count: " + cyb
		);
	}
}
