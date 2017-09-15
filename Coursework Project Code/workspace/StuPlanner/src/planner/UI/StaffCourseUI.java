package planner.UI;

import java.io.*;
import java.text.*;
import java.util.*;

import planner.manager.CalendarMgr;
import planner.manager.CourseMgr;
import planner.manager.PrintMgr;
import planner.manager.StaffCourseMgr;

/** Show the UI for the staff to edit the courses
 *  Inherit from CourseUI 
 */
public class StaffCourseUI extends CourseUI{
	
	/**
	 * Show a UI that prompts Staff to add/update courses
	 * @throws ParseException 
	 * @throws IOException 
	 */
	public static void addUpdateCourseUI() throws IOException, ParseException{
		int choice;
		
		CourseWhileLoop:
		while(true){
			System.out.println("***Welcome to Course panel!***");
			System.out.println("Please select an action:");
			System.out.println("(1) Add a new course");
			System.out.println("(2) Update existing course/index");
			System.out.println("(3) Remove a course");
			System.out.println("(4) Add a new index");
			System.out.println("(5) Remove an index");
			System.out.println("(6) View list of courses");
			System.out.println("(7) View list of indexes of a course");
			System.out.println("(8) Check index vacancies");
			System.out.println("(9) Back");

			System.out.print("> ");
				try{
				choice = Integer.parseInt(sc.nextLine());
				switch (choice) {
				case 1: // Add a new course
					addNewCourseUI();
					break;
				case 2: // Update existing course
					updateCourseAndIndexUI();
					break;
				case 3: // Remove a course
					removeCourseUI();
					break;
				case 4: // Add a new index
					addNewIndexUI();
					System.out.println();
					break;
				case 5: // Remove an Index
					removeIndexUI();
					System.out.println();
					break;
				case 6: // Print list of courses (extra feature)
					CourseMgr.printCourseList();
					System.out.println();
					break;
				case 7: // View list of indexes of a course
					printIndexListUI();
					System.out.println();
					break;
				case 8: // Check index vacancy
					checkVacancyUI();
					System.out.println();
					break;
				case 9: // Back
					break CourseWhileLoop;
				default:
					System.out.println("Invalid Input! Please re-enter!");
					System.out.println();
				}
			}catch (Exception e) {
				System.out.println("Invalid Input! Please re-enter!");
			}
		}
	}
	

	/**
	 * Show a UI to add a new course
	 * done by the Staff
	 * @throws IOException 
	 * @throws ParseException 
	 */
	private static void addNewCourseUI() throws IOException, ParseException{
		String courseCode = "";
		int au = 0;

		courseCode = getValidCourseCodeUI(2);
		
		System.out.print("Enter the course's name: "); String courseName = sc.nextLine();
		
        while(true){
        	try{
        		System.out.print("Enter the number of AUs: "); au = sc.nextInt();
        		sc.nextLine();
        		break;
        	} catch (Exception e){
        		sc.nextLine();
        		System.out.println("Invalid input! Academic Unit must be a number!");
        	}
        }
        
        System.out.print("Enter the school that offers the course (eg: SCE): ");
        String school= sc.nextLine();
        
		System.out.print("Enter the course's type: ");
		String courseType = sc.nextLine();
		
		Calendar examDate = CalendarMgr.getValidDateTime("an Exam Date");
		StaffCourseMgr.addCourse(courseCode, courseName.toUpperCase(), au, school.toUpperCase(), courseType.toUpperCase(), examDate);
	}
	
	/** Remove the course by taking in the course code
	 *  done by staff
	 */
	private static void removeCourseUI(){
		System.out.print("Enter the course's code:"); String courseCode = sc.nextLine();
		StaffCourseMgr.removeCourse(courseCode.toUpperCase());	
	}
	
	/** Update the course that is already in the system
	 * @throws IOException
	 * @throws ParseException
	 */
	private static void updateCourseAndIndexUI() throws IOException, ParseException{
		System.out.println();
		System.out.println("PLease select one of the following:");
		System.out.println("===================================");
		System.out.println("1. Update course code");
		System.out.println("2. Update school of the course");
		System.out.println("3. Update index numbers of the course");
		System.out.println("4. Update vacancy of the course");
		boolean invalidInput = false; 
		do{
			int in = sc.nextInt();
			sc.nextLine();
			
			switch(in){
			case 1		:	updateCourseCodeUI();
							break;
							
			case 2 		:	updateCourseSchoolUI();
							break;
							
			case 3		:	updateIndexNumberUI();
							break;
							
			case 4		:	updateIndexVacancyUI();
							break;
							
			default		: 	System.out.println("Invalid Input. Please enter again");
							invalidInput = true; 
			}
		}while(invalidInput);
	}
	
	/** UI to update the vacancy of an index
	 */
	private static void updateIndexVacancyUI() {
		String courseCode 		= getValidCourseCodeUI(1);
		
		if (!CourseMgr.printIndexList(courseCode)) return;
		System.out.print("Please enter index number that you want to modify: ");
		int indexNumber = sc.nextInt(); sc.nextLine();
		
		System.out.print("Please enter new vacancy: ");
		int newVacancy = sc.nextInt(); sc.nextLine();
		
		StaffCourseMgr.updateVacancy(courseCode, indexNumber, newVacancy);
		System.out.print("\nVacancy is successfully updated!\n");
		CourseMgr.printIndexDetail(courseCode, indexNumber);
		
	}

	/** UI to update the Index Number
	 */
	private static void updateIndexNumberUI() {
		String courseCode 		= getValidCourseCodeUI(1);
		
		if (!CourseMgr.printIndexList(courseCode)) return;
		System.out.print("Please enter index number that you want to modify: ");
		int indexNumber = sc.nextInt(); sc.nextLine();
		
		System.out.print("Please enter new index number: ");
		int newIndexNumber = sc.nextInt(); sc.nextLine();
		
		StaffCourseMgr.updateIndex(courseCode, indexNumber, newIndexNumber);
		System.out.print("\nIndex Number is successfully updated!\n");
		CourseMgr.printIndexList(courseCode);
		
	}

	/** UI to update the course's school
	 */
	private static void updateCourseSchoolUI() {
		String courseCode 		= getValidCourseCodeUI(1);
		String newSchool	 	= null;
		
		CourseMgr.printCourseDetail(courseCode);
		System.out.print("Please enter new school:"); newSchool = sc.next();
		
		StaffCourseMgr.updateSchool(courseCode, newSchool);
		System.out.print("\nSchool is successfully updated!\n");
		CourseMgr.printCourseDetail(courseCode); sc.nextLine();
		
	}

	/** UI to update course code
	 */
	private static void updateCourseCodeUI() {
		String courseCode 		= getValidCourseCodeUI(1);
		String newCourseCode 	= null;
		
		CourseMgr.printCourseDetail(courseCode);
		System.out.print("Please enter new course code:"); newCourseCode = sc.next().toUpperCase();
		
		StaffCourseMgr.updateCourseCode(courseCode, newCourseCode);
		System.out.print("\nCourse code is successfully updated!\n");
		CourseMgr.printCourseDetail(newCourseCode); sc.nextLine();
		
	}
	
	/** UI to add new index to an existing course
	 * @throws IOException
	 * @throws ParseException
	 */
	private static void addNewIndexUI(){		
		String courseCode = "";
		int indexNumber;
		
		CourseMgr.printCourseList();
		
		courseCode = getValidCourseCodeUI(1);
		indexNumber = getValidIndexUI(courseCode,2);
		
		System.out.print("Enter the tutorial group: "); String tutorialGroup = sc.nextLine();
		System.out.print("Enter the index vacancy: "); int vacancy = sc.nextInt();
		sc.nextLine();
		
		StaffCourseMgr.addIndex(courseCode, indexNumber, tutorialGroup, vacancy);
		addNewLessonUI(courseCode, indexNumber);
		
		System.out.print("\nIndex is successfully added!\n");
		CourseMgr.printIndexInfo(courseCode, indexNumber);
		
	}
	
	/** UI to add a new lesson
	 * @param courseCode
	 * @param indexNumber
	 */
	private static void addNewLessonUI(String courseCode, int indexNumber){
		System.out.print("Enter number of lessons: "); int noOfLesson = sc.nextInt();
		sc.nextLine();
		
		for (int i = 0; i < noOfLesson; i++){
			System.out.println();
			System.out.println("Please enter Lesson for the index you have added " + (i+1));
			System.out.println("----------------------------");
			System.out.print("Enter the lesson type (LAB/TUT/LEC): "); String lessonType = sc.nextLine();
			System.out.print("Enter the lesson day (e.g 1 for Mon, 2 for Tues): "); int lessonDay = sc.nextInt(); sc.nextLine();
			Calendar lessonStartTime = CalendarMgr.getValidLessonTime("start");
			Calendar lessonEndTime	 = CalendarMgr.getValidLessonTime("end");
			System.out.print("Enter the lesson venue (e.g. LT2A): "); String lessonVenue = sc.nextLine();
			
			StaffCourseMgr.addLesson(courseCode, indexNumber, lessonType, lessonDay, lessonStartTime, lessonEndTime, lessonVenue);
		}
	}
	
	/** UI to remove an index from a course
	 * @throws IOException
	 * @throws ParseException
	 */
	private static void removeIndexUI(){
		String courseCode = getValidCourseCodeUI(1);
		
		CourseMgr.printIndexList(courseCode.toUpperCase());
		
		System.out.println();
		int indexNumber = getValidIndexUI(courseCode, 1);
		
		StaffCourseMgr.removeIndex(courseCode, indexNumber);
		System.out.println("Index number " + indexNumber + " has been removed.");
		CourseMgr.printIndexList(courseCode);
		
	}
	
	
	
}
