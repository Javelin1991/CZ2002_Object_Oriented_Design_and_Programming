package planner.manager;

import java.util.*;
import planner.model.Course;
import planner.model.Index;
import planner.model.Lesson;

/** Manages the modifications to the courses
 *  and the logic to run the commands
 *  done by the staff
 */
public class StaffCourseMgr extends CourseMgr{
	
	/** Add a new course
	 * @param courseCode
	 * @param courseName
	 * @param au
	 * @param school
	 * @param courseType
	 * @param examDate
	 */
	public static void addCourse(String courseCode, String courseName, int au, String school, String courseType, Calendar examDate){
		Course newCourse 		= new Course(courseCode, courseName, au, school, courseType, examDate);
		List<Index> indexList 	= new ArrayList<Index>();
		
		newCourse.setIndexList(indexList);
		
		
		courseList.add(newCourse);
		System.out.println();
		CourseMgr.printCourseList();
		System.out.println();
	}
	
	/** Remove the spceified courseCode
	 * @param courseCode
	 */
	public static void removeCourse(String courseCode){
		if (isExistingCourseCode(courseCode)){
			Course course = getCourseByCode(courseCode);
			
			// remove the registration record
			StudentCourseMgr.removeRecord(course);
			
			// remove from the list of courses
			courseList.remove(course);
			System.out.println("Course " + course.getCourseName() + " (" + courseCode + ") has been removed!");
		}
		else{
			System.out.println("Course code is not found!\n");
		}
	}
	
	/** Return true when the course code already is on the list
	 * @param courseCode
	 * @return
	 */
	public static Boolean isExistingCourseCode(String courseCode){
		for (Course c : courseList) {
			if (c.getCourseCode().equals(courseCode)) {
				return true;}
		}
		return false;
	}
	
	/** Update the Course Code
	 * @param courseCode
	 * @param newCourseCode
	 */
	public static void updateCourseCode(String courseCode, String newCourseCode){
		Course c = getCourseByCode (courseCode);
		c.setCourseCode(newCourseCode);
	}
	
	/** Update the course's school
	 * @param courseCode
	 * @param newSchool
	 */
	public static void updateSchool(String courseCode, String newSchool){
		Course c = getCourseByCode (courseCode);
		c.setSchool(newSchool);
	}
	
	/** Add new index to the selected course
	 * @param courseCode
	 * @param indexNumber
	 * @param tutorialGroup
	 * @param vacancy
	 */
	public static void addIndex (String courseCode, int indexNumber, String tutorialGroup, int vacancy){
		List<Lesson> lessonList = new ArrayList<Lesson>();
		Course course 			= getCourseByCode(courseCode);
		List<Index> indexList 	= course.getIndexList();
		Index newIndex 			= new Index(indexNumber, tutorialGroup, vacancy, 0);
		newIndex.setLessonList(lessonList);

		indexList.add(newIndex);
		course.setIndexList(indexList);
		
	}
	
	/** Remove index from the course
	 * @param courseCode
	 * @param indexNumber
	 */
	public static void removeIndex (String courseCode, int indexNumber){
		Course course 			= getCourseByCode(courseCode);
		List<Index> indexList 	= course.getIndexList();
		Index index 			= getIndexByNumber(courseCode, indexNumber);
		
		indexList.remove(index);
		course.setIndexList(indexList);

	}
	
	/** Return true when the course already has the index
	 * @param courseCode
	 * @return
	 */
	public static Boolean isExistingIndex(String courseCode, int indexNumber){
		Course course 			= getCourseByCode(courseCode);
		List<Index> indexList 	= course.getIndexList();
		
		if (!(indexList == null)){
			for (Index i: indexList ) {
				if (i.getIndexNumber() == indexNumber) {
					return true;
				}}
			}
		return false;
	}
	
	
	/** Add a new lesson to the specific index
	 * @param courseCode
	 * @param indexNumber
	 * @param lessonType
	 * @param lessonDay
	 * @param lessonTime
	 * @param lessonVenue
	 */
	public static void addLesson (String courseCode, int indexNumber, String lessonType, int lessonDay, Calendar lessonStartTime, Calendar lessonEndTime, String lessonVenue ){
		Lesson newLesson 		= new Lesson(lessonType, lessonDay, lessonStartTime, lessonEndTime, lessonVenue);
		Index index 			= getIndexByNumber(courseCode, indexNumber);
		List<Lesson> lessonList =  index.getLessonList();
		lessonList.add(newLesson);
		index.setLessonList(lessonList);
	}
	
	/** Update index number
	 * @param courseCode
	 * @param indexNumber
	 * @param newIndexNumber
	 */
	public static void updateIndex(String courseCode, int indexNumber, int newIndexNumber){
		if (!isExistingIndex(courseCode, newIndexNumber)){
			Index index = getIndexByNumber(courseCode, indexNumber);
			index.setIndexNumber(newIndexNumber);
		}
		else{
			System.out.println("Index number already existed!");
		}
	}
	
	/** Update the vacancy of the index
	 *  and register the students in the waitinglist
	 * @param courseCode
	 * @param indexNumber
	 * @param newVacancy
	 */
	public static void updateVacancy(String courseCode, int indexNumber, int newVacancy){
		Index index 	= getIndexByNumber(courseCode, indexNumber);
		int waitingList = index.getWaitingList();
		
		if (waitingList >= newVacancy){
			index.setVacancy(0);
		}
		else{
			index.setVacancy(newVacancy);
		}
		
		//register people in the waitlist if there is an increase in vacancy
		if (waitingList > 0 && index.getVacancy() > 0){
			StudentCourseMgr.updateWaitList(courseCode, index);
		}
	}
}
