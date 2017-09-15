package planner.UI;

import java.util.Scanner;

import planner.manager.CourseMgr;
import planner.manager.PrintMgr;
import planner.manager.StaffCourseMgr;
import planner.manager.StudentCourseMgr;
import planner.model.Student;

/** A parent class for the UIs that is related to changing
 *  or viewing the courses
 */
public class CourseUI {
	
	protected static Scanner sc = new Scanner(System.in);
	
	/** Get the valid course registration record of the student
	 * @param loggedInStudent
	 */
	protected static String getValidCourseRegUI(Student loggedInStudent) {
    	String courseCode;
		Boolean flag;
		do {
	   		courseCode = getValidCourseCodeUI(1);
       		flag = !StudentCourseMgr.isExistingRegistration(loggedInStudent, courseCode);
		} while (flag);
    	return courseCode;
	}
	
	/** Print out the list of indexes for a particular course
	 */
	protected static void printIndexListUI(){
		String courseCode 		= getValidCourseCodeUI(1);
		CourseMgr.printIndexList(courseCode);
	}
	
	/** Print out the detail of a particular index
	 */	
	protected static void checkVacancyUI() {
		String courseCode 		= getValidCourseCodeUI(1);
		
		if (!CourseMgr.printIndexList(courseCode)) return;
		System.out.print("Please enter index number: ");
		int indexNumber = sc.nextInt(); sc.nextLine();
		
		CourseMgr.printIndexDetail(courseCode, indexNumber);
	}

	/** UI to prompt user to give a valid course code
	 *  Choice 1: to prompt for an existing code
	 *  Choice 2: to prompt for a new code (not existing)
	 * @param choice
	 * @return
	 */
	protected static String getValidCourseCodeUI(int choice){
		Boolean flag;
		String courseCode = null;
		
		switch (choice) {
		//course must exist in the list
		case 1:
			do {
				System.out.print("Enter the course's code: ");
				courseCode = sc.nextLine().toUpperCase();
				if (flag = !StaffCourseMgr.isExistingCourseCode(courseCode)){
					System.out.println("Course Code does not exist! Please re-enter.");
				}
			} while (flag);
			break;
		//for no duplication
		case 2:
			do {
				System.out.print("Enter the course's code: ");
				courseCode = sc.nextLine().toUpperCase();
				if (flag = StaffCourseMgr.isExistingCourseCode(courseCode)){
					System.out.println("Course Code already exists! Please re-enter.");
				}
			} while (flag);
			break;
		}
		return courseCode;
	}
	
	/** UI to prompt user for an existing index
	 * 	Choice 1: to prompt for an existing index
	 *  Choice 2: to prompt for a new index (not existing)
	 * @param courseCode
	 * @return
	 */
	protected static int getValidIndexUI(String courseCode, int choice){
		Boolean flag = null;
		int indexNumber = 0;
		
		switch (choice) {
		//index must exist in the list
		case 1:
			do {
				try{
					System.out.print("Enter the index number: ");
					indexNumber = sc.nextInt();
					sc.nextLine();
				}	catch (Exception e){
		       		sc.nextLine();
		       		System.out.println("Invalid input! Index Number must be a number!");
		       		flag = true;
		       		continue;
				}
				flag = !StaffCourseMgr.isExistingIndex(courseCode, indexNumber);
				if (flag){
					System.out.println("Index number does not exist! Please re-enter.");
				}
			} while (flag);
			break;
			
		//for no duplication
		case 2:
			do {
				try{	
					System.out.print("Enter the index number: ");
					indexNumber = sc.nextInt();
					sc.nextLine();
				}
				catch (Exception e){
					sc.nextLine();
					System.out.println("Invalid input! Index Number must be a number!");
					flag = true;
					continue;
				}
				flag = StaffCourseMgr.isExistingIndex(courseCode, indexNumber);
				
				if (flag){
				System.out.println("Index number already exists! Please re-enter.");
				} 
			
			}while (flag);
			break;
		}
		return indexNumber;	
		
	}
}
