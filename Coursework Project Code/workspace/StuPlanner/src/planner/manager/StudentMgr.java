package planner.manager;

import java.util.ArrayList;
import java.util.Calendar;

import planner.db.PlannerData;
import planner.model.CourseReg;
import planner.model.Student;

/**
 * Manages the modification of the student data
 */
public class StudentMgr {
	static ArrayList<Student> studentList = PlannerData.studentList;
	static ArrayList<CourseReg> regList = PlannerData.regList;
	
	/** Get the student from the matric number input
	 * @param matricNumber
	 * @return
	 */
	public static Student getStudentByMatric(String matricNumber){
		for (Student student : studentList) {
			if (student.getMatricNumber().equals(matricNumber)) {
				return student;}
		}
		return null;
	}
	
	/** Update the access period of the student
	 * @param matricNumber
	 * @param newAccessStart
	 * @param newAccessEnd
	 */
	public static void updateAccessPeriod(String matricNumber, Calendar newAccessStart, Calendar newAccessEnd){
		Student student = getStudentByMatric(matricNumber);
		student.setAccessStart(newAccessStart);
		student.setAccessEnd(newAccessEnd);
	}
	
	/** To check if the username is already in the student list
	 *  return false if it is existing
	 *  return true if it is not existing
	 * @param username
	 * @return
	 */
	public static Boolean isExistingUsername(String username){
		for (Student s : studentList) {
			if (s.getUsername().equals(username)) {
				System.out.println("Username already exists! Please re-enter.");
				return false;
			}
		}
		return true;
	}
	
	/** To check if the matric number is already in the student list
	 *  
	 * @param matricNumber
	 * @return false if it is existing and true if it is not existing
	 */
	public static Boolean isExistingMatricNumber(String matricNumber){
		for (Student s : studentList) {
			if (s.getMatricNumber().equals(matricNumber)) {
				System.out.println("Matric number is found in database.");
				return false;
			}
		}
		return true;
	}
	
	/** Add a new student
	 */
	public static void addStudent(String username, String password, String salt, String firstName, String lastName,
			String matricNumber, char gender, String nationality, int mobileNo, String email, Calendar accessStart,
			Calendar accessEnd, int i) {
        Student newStud = new Student(username, password, salt,
        							  firstName, lastName, 
        							  matricNumber, gender, nationality, 
        							  mobileNo, email, accessStart, 
        							  accessEnd, 3);
        studentList.add(newStud);
	}

	public static void removeStudent(String matricNumber) {
		Student student = getStudentByMatric(matricNumber);
		studentList.remove(student);
		
		// To avoid the error: Concurrent Modification
		int counter = 0;
		while(counter != regList.size()){
			for(CourseReg courseReg : regList){
				if (courseReg.getStudent().equals(student)){
					regList.remove(courseReg);
					counter = 0;
					break;
				}
				counter++;
			}
		}
	}
}
