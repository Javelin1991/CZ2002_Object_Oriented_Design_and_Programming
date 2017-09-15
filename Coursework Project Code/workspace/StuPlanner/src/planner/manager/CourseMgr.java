package planner.manager;

import java.util.ArrayList;
import java.util.List;

import planner.db.PlannerData;
import planner.model.Course;
import planner.model.CourseReg;
import planner.model.Index;
import planner.model.Lesson;
import planner.model.Student;

/**
 * A super class that has the common methods used by the child classes,
 * hence allowing for an efficient code reuse
 */
public class CourseMgr {
	static ArrayList<Course> courseList = PlannerData.courseList;
	static ArrayList<Student> studentList = PlannerData.studentList;
	static ArrayList<CourseReg> registrationList = PlannerData.regList;
	
	/** Get the Course from the courseCode string
	 * @param courseCode
	 * @return
	 */
	public static Course getCourseByCode (String courseCode){
		for (Course c : courseList) {
			if (c.getCourseCode().equals(courseCode)) {
				return c;}
		}
		return null;
	}
	
	/** Return the Index object from the courseCode and
	 *  indexNumber
	 * @param courseCode
	 * @param indexNumber
	 * @return
	 */
	public static Index getIndexByNumber (String courseCode, int indexNumber){
		Course course = getCourseByCode(courseCode);
		List<Index> indexList = course.getIndexList();
		
		for (Index i: indexList ) {
			if (i.getIndexNumber() == indexNumber) {
				return i;
			}
		}
		return null;
	}
	

	/**Print out all the courses on the Course List
	 * from index 0 to the end of the array
	 */
	public static void printCourseList(){
		if(courseList.size() <= 0){
			System.out.println("There are no course in the list.");
			return;
		}
		else{
			System.out.println("\nCourse Code\t Course Name");
			System.out.println("----------------------------------------");
			for (Course course : courseList)
			{
				System.out.print(course.getCourseCode() + "\t\t ");
				System.out.print(course.getCourseName());
				System.out.println();
			}
		}
	}
	
	/** Print out the details of the course
	 * @param courseCode
	 */
	public static void printCourseDetail(String courseCode){
		Course course = StaffCourseMgr.getCourseByCode(courseCode);
		
		System.out.println("Course Code\t Course Name \t School");
		System.out.println("----------------------------------------");
		System.out.print(course.getCourseCode()  + "\t\t " +
						 course.getCourseName()  + "\t\t " +
						 course.getSchool() 		+ "\n"	  );
		System.out.println();
		System.out.println();
			
	}

	/** Print the details of the specified index
	 * @param courseCode
	 * @param indexNumber
	 */
	public static void printIndexDetail(String courseCode, int indexNumber) {
		Index index = StaffCourseMgr.getIndexByNumber(courseCode, indexNumber);
		
		System.out.println("Course Code\tIndex Number\t Vacancy\t Waitlist");
		System.out.println("----------------------------------------");
		System.out.print(courseCode  			+ "\t\t" +
						 indexNumber  			+ "\t\t" +
						 index.getVacancy() 	+ "\t\t" +
						 index.getWaitingList() + "\n"   );
		System.out.println();
		System.out.println();
		
	}
	
	/** Print list of students who are registered to a specified index
	 * @param indexNumber
	 */
	public static void printStudentList(String courseCode, int indexNumber){
		boolean flag = false;
		Index index 	= StaffCourseMgr.getIndexByNumber(courseCode, indexNumber);
		
		System.out.println("\nCourse Code: " + courseCode);
		System.out.println("Index Number: " + indexNumber);
		System.out.println("Username\t Matric Number\t Full Name\t\t Status");
		System.out.println("-----------------------------------------------------------");
		
		for (CourseReg courseReg : registrationList){
			if(courseReg.getIndex().equals(index)){
				Student student = courseReg.getStudent();
				System.out.print(student.getUsername() + "\t\t ");
				System.out.print(student.getMatricNumber() + "\t ");
				System.out.print(student.getFirstName() + " " + student.getLastName() + " \t");
				if(courseReg.getStatus()){
					System.out.print("Registered");
				}
				else{
					System.out.print("On wait-list");
				}
				System.out.println();
				flag = true;
			}
		}
		if (!flag) System.out.println("\nNo record is found!\n");
	}
	
	/**
	 * Print out all the indexes of a course
	 * from index 0 to the end of the array
	 */
	public static Boolean printIndexList(String courseCode){
		Course course = StaffCourseMgr.getCourseByCode(courseCode);
		List<Index> indexList = course.getIndexList();
		
		if(indexList.size() <= 0){
			System.out.println("There is no index for this course.\n");
			return false;
		}
		else{
			System.out.println();
			System.out.println("Course Code: " + courseCode);
			System.out.println("INDEX LIST:");
			System.out.println("----------------------------------------");
			for(Index index : indexList) {
				System.out.print(index.getIndexNumber());
				System.out.println();
			}
		}
		System.out.println();
		return true;
	}
	
	/** Print list of students registered to the specified course code
	 * @param courseCode
	 */
	public static void printStudentList(String courseCode) {
		boolean flag = false;
		Course course = CourseMgr.getCourseByCode (courseCode);
		
		System.out.println();
		System.out.println("Course Code: " + courseCode);
		System.out.println("Username\t Matric Number\t Full Name");
		System.out.println("-----------------------------------------------------------------");
		
		for (CourseReg courseReg : registrationList){
			if(courseReg.getCourse().equals(course)){
				Student student = courseReg.getStudent();
				System.out.print(student.getUsername() + "\t\t ");
				System.out.print(student.getMatricNumber() + "\t ");
				System.out.print(student.getFirstName() + " " + student.getLastName());
				System.out.println();
				
				flag = true;
			}
		}
		if (!flag) System.out.println("\nNo record is found!");
	}
	
	/** Print out the studentL list
	 */
	public static void printStudentList(){
		boolean flag = false;
		System.out.println();
		System.out.println("Matric Number\tFull Name");
		System.out.println("---------------------------------------------------");
		
		if(studentList.size() <= 0){
			System.out.println("\nNo record is found!\n");
			return;
		}
		
		for (Student s: studentList){
			System.out.print(s.getMatricNumber() + "\t");
			System.out.print(s.getFirstName() + " " + s.getLastName());
			System.out.println();
			
			flag = true;
		}
		if (!flag) System.out.println("\nNo record is found!");
	}
	
	/** Print out the index information
	 * @param courseCode
	 * @param indexNumber
	 */
	public static void printIndexInfo(String courseCode, int indexNumber){
		
		Index index 			= StaffCourseMgr.getIndexByNumber(courseCode, indexNumber);
		List<Lesson> lessonList = index.getLessonList();
		
		System.out.println();
		System.out.println("Type\t Day\t Start Time\t End Time \t Venue");
		System.out.println("-----------------------------------------------------------------");

		for (Lesson lesson : lessonList){
			System.out.print(lesson.getLessonType() + "\t ");				
			System.out.print(CalendarMgr.dayIntToString(lesson.getLessonDay()) + "\t ");
			System.out.print(CalendarMgr.timeToString(lesson.getLessonStartTime()) + "\t\t ");	
			System.out.print(CalendarMgr.timeToString(lesson.getLessonEndTime()) + "\t\t ");
			System.out.print(lesson.getLessonVenue() + "\t ");
			System.out.println();
			}
		
	}
	

	/** Print all the registered course of the student
	 * @param loggedInStudent
	 */
	public static Boolean printRegisteredCourses(Student loggedInStudent){
		Course courseRegistered;
		Index indexRegistered;
		List<Lesson> lessonRegistered;
		Boolean flag = false;
		int totalAURegistered = 0;
		
		System.out.println();
		System.out.println("Course Code\t AU\t Course Type\t Index Number\t Status\t\t Lesson Type\t Group\t Day\t Time\t\t Venue\t ");
		System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------");
		
		for(CourseReg registration  : registrationList) {
			if (registration.getStudent().equals(loggedInStudent)){
				courseRegistered = registration.getCourse();
				indexRegistered	 = registration.getIndex();
				lessonRegistered = indexRegistered.getLessonList();
				
				System.out.print(courseRegistered.getCourseCode() + "\t\t ");
				System.out.print(courseRegistered.getAU() + "\t ");
				System.out.print(courseRegistered.getCourseType() + "\t\t ");
				System.out.print(indexRegistered.getIndexNumber() + "\t\t ");
				if (registration.getStatus()){
					System.out.print("Registered\t ");
					totalAURegistered += courseRegistered.getAU();
				}
				else{
					System.out.print("On waitlist\t ");
				}
				int counter = 0;
				for(Lesson lesson : lessonRegistered){
					if (counter > 0){
						System.out.println();
						System.out.print("\t\t\t\t\t\t\t\t\t ");
					}
					System.out.print(lesson.getLessonType() + "\t\t ");
					System.out.print(indexRegistered.getTutorialGroup() + "\t ");
					System.out.print(CalendarMgr.dayIntToString(lesson.getLessonDay()) + "\t ");
					System.out.print(CalendarMgr.timeToString(lesson.getLessonStartTime()) + "-" + CalendarMgr.timeToString(lesson.getLessonEndTime()) + "\t ");
					System.out.print(lesson.getLessonVenue() + "\t\t ");
					counter++;
				}
				System.out.println();
				flag = true;
			}
		}
		System.out.println("Total AU Registered: " + totalAURegistered);
		if (!flag) System.out.println("No course is registered!");
		
		return flag;
	}
	
}
