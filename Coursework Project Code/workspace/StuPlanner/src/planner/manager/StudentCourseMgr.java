package planner.manager;

import java.util.*;

import planner.db.PlannerData;
import planner.model.Index;
import planner.model.Lesson;
import planner.model.Student;
import planner.model.Course;
import planner.model.CourseReg;

public class StudentCourseMgr extends CourseMgr{
	static ArrayList<CourseReg> registrationList = PlannerData.regList;
	
	/** Return true when the course code is already registered
	 * @param courseCode
	 * @return
	 */
	public static Boolean isExistingRegistration(Student student, String courseCode){
		Course courseToRegister = getCourseByCode(courseCode);
		
		for (CourseReg reg : registrationList) {			
			if ((reg.getStudent() == student) && (reg.getCourse() == courseToRegister)) {
				System.out.println("Registered course is found.");
				return true;}
		}
		return false;
	}
	/**
	 * Create a new course with the necessary information
	 */	
	public static void registerCourse(Student stu, String courseCode, int indexNumber){
		Course course 			= getCourseByCode (courseCode);
		Index indexToRegister   = getIndexByNumber(courseCode, indexNumber);
		Boolean status;
		int vacancy 		= indexToRegister.getVacancy();
		
		if (vacancy <= 0){
			status = false;
			indexToRegister.waitingListPlus();
			System.out.println("Due to lack of vacancy, your Index " + indexNumber + " (" + courseCode + ") will be put into waiting list.");
		}
		else{
			status =true;
			indexToRegister.vacancyMinus();
			System.out.println("Index " + indexNumber + " (" + courseCode + ") has been successfully added!");
		}
		
		CourseReg courseRegistration = new CourseReg (stu, course, indexToRegister, status);
		registrationList.add(courseRegistration);
		
	}
	
	/** Swap 2 indexes from 2 different users
	 * @param loggedInStudent
	 * @param peer
	 * @param courseCode
	 * @param yourIndexNumber
	 * @param peerIndexNumber
	 */
	public static void swapIndex(Student loggedInStudent, Student peer, String courseCode, int yourIndexNumber, int peerIndexNumber){
		Course course				= getCourseByCode (courseCode);
		CourseReg peerCourseReg 	= getCourseReg(peer, courseCode);
		CourseReg yourOldCourseReg	= getCourseReg(loggedInStudent, courseCode);
		Index yourIndex				= getIndexByNumber(courseCode, yourIndexNumber);
		Index peerIndex				= getIndexByNumber(courseCode, peerIndexNumber);
		
		registrationList.remove(yourOldCourseReg);
		registrationList.remove(peerCourseReg);
		CourseReg yourCourseReg 	= new CourseReg (loggedInStudent, course, peerIndex, true);
		peerCourseReg				= new CourseReg (peer, course, yourIndex, true);
		
		registrationList.add(yourCourseReg);
		registrationList.add(peerCourseReg);		
	}
	
	
	/**
	 * Remove record
	 * @param loggedInUser
	 * @param indexNumber
	 */
	public static void removeRecord(Student loggedInStudent, String courseCode, int indexNumber){
		CourseReg courseReg = getCourseReg(loggedInStudent, courseCode);
		Index index			= getIndexByNumber(courseCode, indexNumber);
		Boolean status 		= courseReg.getStatus();
		
		registrationList.remove(courseReg);
		System.out.println("Index " + indexNumber + " (" + courseCode + ") has been removed!");
		
		if (status == true){ // Registered
			updateWaitList(courseCode, index);
			index.vacancyPlus();
		}
		else{ // Waiting List
			index.waitingListMinus();
		}		
	}
	
	/** Update wait list by adding the first person on the stack
	 *  to an available vacancy
	 * @param courseCode
	 * @param index
	 * @param counter
	 */
	static void updateWaitList(String courseCode, Index index) {
		int indexNumber = index.getIndexNumber();
		
		for (CourseReg registration : registrationList){
			// Register students from the top of the waitlist
			// only registers as many as the counter
			if ((registration.getIndex() == index) && (registration.getStatus() == false)){
				registration.setStatus(true);
				
				//Notify users
				NotificationMgr.sendAlertWaitlist(registration.getStudent(), courseCode, indexNumber);
				index.waitingListMinus();
				index.vacancyMinus();
			}
		}
		
	}
	
	/** Remove all the records with the specified course
	 * @param course
	 */
	public static void removeRecord(Course course){
		ArrayList<CourseReg> temp = new ArrayList<CourseReg>();
		for (CourseReg c : registrationList) {
			if (c.getCourse().equals(course)) {
				temp.add(c);
			}
		}
		for (CourseReg courseRegToRemove: temp){
			registrationList.remove(courseRegToRemove);
		}
		System.out.println("Course registration records are removed.");
		
	}
	
	/** Get the course registration data from the student and course
	 * @param loggedInStudent
	 * @param courseCode
	 * @return
	 */
	public static CourseReg getCourseReg(Student loggedInStudent, String courseCode){
		Course course = getCourseByCode(courseCode);
		
		for (CourseReg c : registrationList) {
			if (c.getStudent().equals(loggedInStudent)) {
				if (c.getCourse().equals(course)){
					return c;}
				}
		}
		return null;
	}
	
	/** Get the course registration data from the student and course
	 * @param loggedInStudent
	 * @return
	 */
	public static CourseReg getCourseReg(Student loggedInStudent){
		for (CourseReg c : registrationList) {
			if (c.getStudent().equals(loggedInStudent)) {
				return c;
			}
		}
		return null;
	}
	
	/** Check if there is any clash in timetable
	 *  Return true when there is a clash and false when there is not
	 * @param loggedInStudent
	 * @param courseCode
	 * @param indexNumber
	 * @return
	 */
	public static Boolean checkClash(Student loggedInStudent, String courseCode, int indexNumber) {
		Course courseToRegister = getCourseByCode (courseCode);
		Index indexToRegister   = getIndexByNumber(courseCode, indexNumber);
		Index registeredIndex;
		Course registeredCourse;
		
		for (CourseReg registered: registrationList){
			if (registered.getStudent() == loggedInStudent){
				registeredCourse		= registered.getCourse();
				registeredIndex 		= registered.getIndex();
				
				//Check clash in exam timing
				if (registeredCourse.getExamDate() == courseToRegister.getExamDate()){
					System.out.println("There is a clash in exam date! Course is not registered.");
					return true;
				};
				
				// Check clash in timetable
				for (Lesson lessonRegistered: registeredIndex.getLessonList()){
					for (Lesson lessonToRegister: indexToRegister.getLessonList()){
						if (lessonRegistered.getLessonDay() == lessonToRegister.getLessonDay()){
							
							boolean isEqualStartTime = lessonToRegister.getLessonStartTime().compareTo(lessonRegistered.getLessonStartTime()) == 0;
							boolean isEqualEndTime = lessonToRegister.getLessonEndTime().compareTo(lessonRegistered.getLessonEndTime()) == 0;
							
							if( isEqualStartTime||isEqualEndTime||
								((lessonRegistered.getLessonEndTime().before(lessonToRegister.getLessonEndTime())&&
								(lessonRegistered.getLessonEndTime().after(lessonToRegister.getLessonStartTime()))) ||
								(lessonRegistered.getLessonStartTime().before(lessonToRegister.getLessonEndTime())&&
								(lessonRegistered.getLessonStartTime().after(lessonToRegister.getLessonStartTime()))))){
								System.out.println("There is a clash in lesson timetable! Course is not registered.");
								System.out.println("Registered course: " + registeredCourse.getCourseCode());
								System.out.println("Lesson clash:      " + lessonRegistered.getLessonType());
								System.out.println("Day:               " + CalendarMgr.dayIntToString(lessonRegistered.getLessonDay()));
								System.out.println("Timing:            " + CalendarMgr.timeToString(lessonRegistered.getLessonStartTime()) + " to " + CalendarMgr.timeToString(lessonRegistered.getLessonEndTime()));
								System.out.println();
								System.out.println("New course:        " + courseToRegister.getCourseCode());
								System.out.println("Lesson clash:      " + lessonToRegister.getLessonType());
								System.out.println("Day:               " + CalendarMgr.dayIntToString(lessonToRegister.getLessonDay()));
								System.out.println("Timing:            " + CalendarMgr.timeToString(lessonToRegister.getLessonStartTime()) + " to " + CalendarMgr.timeToString(lessonRegistered.getLessonEndTime()));
								return true;
							}
						}
						else continue;
		} } } }
		return false;
		
	}
}