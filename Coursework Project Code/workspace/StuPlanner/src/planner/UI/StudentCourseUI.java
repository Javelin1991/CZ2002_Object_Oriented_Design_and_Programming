package planner.UI;

import java.io.*;
import java.text.*;
import java.util.*;

import planner.manager.CourseMgr;
import planner.manager.LoginMgr;
import planner.manager.PrintMgr;
import planner.manager.StudentCourseMgr;
import planner.model.Student;

/** Show the UI for the student to view and edit the courses registered 
 *  Inherit from CourseUI 
 */

public class StudentCourseUI extends CourseUI{
	
    private static Scanner sc = new Scanner(System.in);
    
	/** Display menu for students
	 */	
	public static void showStudentOption(Student loggedInStudent){
		int choice;
	
		StudentWhileLoop:
		while (true){
			System.out.println("***Welcome to Student panel!***");
			System.out.println("Please select an action:");
			System.out.println("(1) Register Course");
			System.out.println("(2) Drop Course");
			System.out.println("(3) Check/Print Registered Courses (Timetable)");
			System.out.println("(4) Change Index Number of Course");
			System.out.println("(5) Swop Index Number with Another Student");
			System.out.println("(6) View list of courses");
			System.out.println("(7) View list of indexes of a course");
			System.out.println("(8) Check index vacancies");
			System.out.println("(9) Select Notification Mode");
			System.out.println("(10) Logout");

			System.out.print("> ");
			try {
				choice = Integer.parseInt(sc.nextLine());
				switch (choice) {
				case 1: // Register Course
					registerCourseUI(loggedInStudent);
					break;
				case 2: // Drop Course
					dropCourseUI(loggedInStudent);
					break;
				case 3: // Check/Print Courses Registered
					CourseMgr.printRegisteredCourses(loggedInStudent);
					break;
				case 4: // Change Index Number of Course
					changeIndexNumberUI(loggedInStudent);
					break;
				case 5: // Swop Index Number with Another Student
					swopIndexNumberUI(loggedInStudent);
					break;
				case 6: // View List of courses
					CourseMgr.printCourseList();
					System.out.println();
					break;
				case 7: // View List of indexes
					printIndexListUI();
					System.out.println();	
					break;
				case 8: // Check Vacancies Available
					checkVacancyUI();
					break;
				case 9: // Select Notification Mode
					selectNotiModeUI(loggedInStudent);
					break;
				case 10: // Logout
					System.out.println("Successfully Logged Out!");
					System.out.println();
					break StudentWhileLoop;
				}
			} catch (Exception e) {
				System.out.println("Invalid Input! Please re-enter!");
			}
			System.out.println();
		}
	}

	private static void registerCourseUI(Student loggedInStudent){
	    int indexNumber = 0;
	    String courseCode = "";
	    char choice;
	    Boolean flag;
	    
	    do {
	   		courseCode = getValidCourseCodeUI(1);
       		if (StudentCourseMgr.isExistingRegistration(loggedInStudent, courseCode)){
      			flag = true;
      			System.out.println("You have already registered to this course! Please choose another course.");
      			}
      		else{
      			flag = false;
      		}
		} while (flag);
	    
	    CourseMgr.printIndexList(courseCode);
	    indexNumber = getValidIndexUI(courseCode, 1);

	    CourseMgr.printIndexInfo(courseCode, indexNumber);
		System.out.println();
		
		if (StudentCourseMgr.checkClash(loggedInStudent, courseCode, indexNumber)){
		
			System.out.println("Returning to the menu...");
			return;
		}
		
		System.out.print("Confirm to Add Course? (Y/N): ");
		choice = sc.nextLine().charAt(0);
		if (choice == 'Y' || choice == 'y'){
			StudentCourseMgr.registerCourse(loggedInStudent, courseCode, indexNumber);
		}
	}


	private static void dropCourseUI(Student loggedInStudent) throws ParseException, IOException{
    	String courseCode;
    	int indexNumber;
    	Boolean flag;
    	
    	if (!CourseMgr.printRegisteredCourses(loggedInStudent)) return;
    	System.out.println();

    	do {
	   		courseCode = getValidCourseCodeUI(1);
       		flag = !StudentCourseMgr.isExistingRegistration(loggedInStudent, courseCode);
		} while (flag);
    	
    	indexNumber = (StudentCourseMgr.getCourseReg(loggedInStudent, courseCode)).getIndex().getIndexNumber();
    	
    	CourseMgr.printIndexInfo(courseCode, indexNumber);
		
		System.out.println();
		System.out.print("Confirm to Drop Course? (Y/N): ");
		
		char choice = sc.nextLine().charAt(0);
		if (choice == 'Y' || choice == 'y'){
			StudentCourseMgr.removeRecord(loggedInStudent, courseCode, indexNumber);
			System.out.print("Course " + courseCode + " is dropped successfuly!");
		}
		System.out.println();
    }
    
    private static void changeIndexNumberUI(Student loggedInStudent){
    	String courseCode;
    	int indexNumber;
    	
    	if (!CourseMgr.printRegisteredCourses(loggedInStudent)) return;
    	System.out.println();

    	courseCode = getValidCourseRegUI(loggedInStudent);
    	indexNumber = (StudentCourseMgr.getCourseReg(loggedInStudent, courseCode)).getIndex().getIndexNumber();
    	CourseMgr.printIndexInfo(courseCode, indexNumber);
    	
    	CourseMgr.printIndexList(courseCode);
 
    	System.out.println("\nNEW INDEX");
    	System.out.println("------------------------------------------------");
		int newIndexNumber = getValidIndexUI(courseCode, 1);
		
		System.out.println("\nCurrent Index Information: " + indexNumber);
		System.out.println("=========================");
		CourseMgr.printIndexInfo(courseCode, indexNumber);

		System.out.println("\nNew Index Information: " + newIndexNumber);
		System.out.println("=====================");
		CourseMgr.printIndexInfo(courseCode, newIndexNumber);
		
		System.out.println();
		System.out.print("Confirm to Change Index Number? (Y/N): ");
		char choice = sc.nextLine().charAt(0);
		
		if (choice == 'Y' || choice == 'y'){
			StudentCourseMgr.removeRecord(loggedInStudent, courseCode, indexNumber);	
			StudentCourseMgr.registerCourse(loggedInStudent, courseCode, newIndexNumber);
			System.out.println("Index Number " + indexNumber + " has been changed to " + newIndexNumber);
		}
	}

	private static void swopIndexNumberUI(Student loggedInStudent){
    	String courseCode = "";
    	Boolean flag;
    	
    	if (!CourseMgr.printRegisteredCourses(loggedInStudent)) return;
    	
    	System.out.print("\nEnter Peer's Username: "); String peerUsername = sc.nextLine();
    	System.out.print("Enter Peer's Password: "); String peerPassword = sc.nextLine();
    	
    	Student peer = (Student) LoginMgr.compareUserPass(peerUsername, peerPassword, "STUDENT");
    
    	
		if (!(peer == null)) { // Successfully logged in
			
			if (peer == loggedInStudent){
				System.out.println("Invalid user input. Swap can only be done with someone else!");
				return;
			}
			do {
		   		courseCode = getValidCourseCodeUI(1);
	       		if (StudentCourseMgr.isExistingRegistration(loggedInStudent, courseCode) &&
	       			StudentCourseMgr.isExistingRegistration(peer, courseCode)){
	      			flag = false;}
	      		else{
	      			System.out.println("BOTH students need to be registered to the same course for swapping!");
	      			return;
	      		}
			} while (flag);
			
			System.out.println("\nStudent #1 " + loggedInStudent.getMatricNumber() + "'s Index to switch:");
			System.out.println("------------------------------------------------");
			int yourIndexNumber = getValidIndexUI(courseCode, 1);
			
			System.out.println("\nStudent #2 " + peer.getMatricNumber() + "'s Index to switch:");
			System.out.println("------------------------------------------------");
			int peerIndexNumber = getValidIndexUI(courseCode, 1);
			
			System.out.println();
			System.out.println("\nStudent #1 (" + loggedInStudent.getMatricNumber() + ")'s Index Information");
			System.out.println("================================================");
			CourseMgr.printIndexInfo(courseCode, yourIndexNumber);
			
			System.out.println();
			System.out.println("Student #2 (" + peer.getMatricNumber() + ")'s Index Information");
			System.out.println("================================================");
			CourseMgr.printIndexInfo(courseCode, peerIndexNumber);
			
			System.out.println();
			System.out.print("Confirm to Change Index Number? (Y/N): ");
			
			char choice = sc.nextLine().charAt(0);
			if (choice == 'Y' || choice == 'y'){
			
				StudentCourseMgr.swapIndex(loggedInStudent, peer, courseCode, yourIndexNumber, peerIndexNumber);
				System.out.println(loggedInStudent.getMatricNumber() + "-Index Number " + yourIndexNumber + " has been successfully swopped with " + peer.getMatricNumber() + "-Index Number " + peerIndexNumber);
			}
		}
    		
		else{
				System.out.println();
				System.out.println("Incorrect peer's username or password!");
			}
    }
    
    private static void selectNotiModeUI(Student loggedInStudent){
    	System.out.println("Please select your notification mode:");
    	System.out.println("=====================================");
    	System.out.println("(1) Send SMS");
    	System.out.println("(2) Send Email");
    	System.out.println("(3) Send both");
    	int choice = sc.nextInt();
    	sc.nextLine();
    	
    	loggedInStudent.setNotiMode(choice);
    	System.out.println("Notification mode is updated!");
    	
    }
}
