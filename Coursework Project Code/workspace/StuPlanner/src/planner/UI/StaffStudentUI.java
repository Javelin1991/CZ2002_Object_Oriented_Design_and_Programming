package planner.UI;

import java.text.*;
import java.util.*;
import java.io.*;

import planner.manager.CourseMgr;
import planner.manager.PrintMgr;
import planner.manager.StudentMgr;
import planner.manager.CalendarMgr;
import planner.manager.LoginMgr;

/** Show the UI for the staff to edit the students
 */
public class StaffStudentUI {
	
	private static Scanner sc = new Scanner(System.in);
	
	/**
	 * Show a UI that prompts Staff to add/update courses
	 * @throws ParseException 
	 * @throws IOException 
	 */
	public static void addUpdateStudentUI() throws IOException, ParseException{
		int choice;
		
		StaffStudentWhileLoop:
		while(true){
			System.out.println("***Welcome to Student panel!***");
			System.out.println("Please select an action:");
			System.out.println("(1) Edit student access period");
			System.out.println("(2) Add a student");
			System.out.println("(3) Delete a student");
			System.out.println("(4) Print student list by index number");
			System.out.println("(5) Print student list by course");
			System.out.println("(6) Back");

			System.out.print("> ");
			try{
				choice = Integer.parseInt(sc.nextLine());
				switch (choice) {
					case 1: // Edit student access period
						editAccessTimeUI();
						break;
					case 2: // Add a student
						addNewStudentUI();
						break;
					case 3: // Remove a student
						removeStudentUI();
						break;
					case 4: // Print student list by index number
						printStudentListIndexUI();
						break;
					case 5: // Print student list by course
						printStudentListCourseCodeUI();
						break;
					case 6: // Logout
						break StaffStudentWhileLoop;
					default:
						System.out.println("Invalid Input! Please re-enter!");
				}
			}catch (Exception e) {
				System.out.println("Invalid Input! Please re-enter!");
			}
			System.out.println();
		}
	}
    
    private static void removeStudentUI() {
    	Boolean flag = false;
		String matricNumber = "";
		
		CourseMgr.printStudentList();
		System.out.println();
		
		do {
			System.out.print("Enter the student's matric number: ");
			matricNumber = sc.nextLine();
			flag = StudentMgr.isExistingMatricNumber(matricNumber);
			if (flag){
				System.out.println("Matric number is not found in database.");
			}
		} while (flag);
		
		StudentMgr.removeStudent(matricNumber);
		System.out.println("Student with matric number " + matricNumber + " has been removed successfully!");
		
	}

	/** Show a UI that prompts Staff of the students'
    * details to add new student
    */
    private static void addNewStudentUI(){    	
		String username = "";
		String matricNumber = "";
		String firstName = "";
		String lastName = "";
		String email = "";
		boolean flag;
        int mobileNo = 0;

		do {
			System.out.print("Enter the student's username: ");
			username = sc.nextLine();
			flag = !(StudentMgr.isExistingUsername(username));
		} while (flag);

		System.out.print("Enter the student's first name: ");
		firstName = sc.nextLine();
		System.out.print("Enter the student's last name: ");
		lastName = sc.nextLine();

		do {
			System.out.print("Enter the student's matric number: ");
			matricNumber = sc.nextLine();
			flag = !StudentMgr.isExistingMatricNumber(matricNumber);
		} while (flag);

		System.out.print("Enter the student's gender (M/F): ");
		char gender = sc.next().charAt(0);
		sc.nextLine();
		
        System.out.print("Enter the student's nationality: ");
        String nationality = sc.nextLine();
        
        while(true){
        	try{
        		System.out.print("Enter the student's Mobile Number: ");
        		mobileNo = sc.nextInt();
        		sc.nextLine();
        		break;
        	} catch (Exception e){
        		sc.nextLine();
        		System.out.println("Invalid phone number!");
        	}
        }
        
        System.out.print("Enter the student's Email Address: ");
        email = sc.nextLine();
        
        Calendar accessStart = CalendarMgr.getValidDateTime("access start");
        Calendar accessEnd = CalendarMgr.getValidDateTime("access end");
        
        // Adding New Account (Note: Password = Matric Number)
        String salt = LoginMgr.generateSalt();
    	String password = matricNumber;
    	
        StudentMgr.addStudent	(username, password, salt,
        						 firstName, lastName, 
        						 matricNumber, gender, nationality, 
        						 mobileNo, email, accessStart, 
        						 accessEnd, 3);
        
        
	    System.out.println();
		System.out.println("New Student Added Successfully!");
		CourseMgr.printStudentList();
    }
	
	 /** Show a UI to update the students' access period
	 * done by the Staff
	 */
	private static void editAccessTimeUI(){		
		Boolean flag = false;
		String matricNumber = "";
		
		do {
			System.out.print("Enter the student's matric number: ");
			matricNumber = sc.nextLine();
			flag = StudentMgr.isExistingMatricNumber(matricNumber);
			if(flag){
				System.out.println("Matric number is not found in database.");
			}
		} while (flag);
	
		Calendar newAccessStart = CalendarMgr.getValidDateTime("new start access time");
		Calendar newAccessEnd = CalendarMgr.getValidDateTime("new end access time");
		StudentMgr.updateAccessPeriod(matricNumber, newAccessStart, newAccessEnd);
		System.out.println("Access time is updated Successfully!");
	}
	

	private static void printStudentListIndexUI(){
		
		String courseCode = CourseUI.getValidCourseCodeUI(1);
		
		CourseMgr.printIndexList(courseCode);
		
		int indexNumber = CourseUI.getValidIndexUI(courseCode,1);
        
		CourseMgr.printStudentList(courseCode, indexNumber);
	}
	
	private static void printStudentListCourseCodeUI() {
		String courseCode = CourseUI.getValidCourseCodeUI(1);
		CourseMgr.printStudentList(courseCode);
	}
}
