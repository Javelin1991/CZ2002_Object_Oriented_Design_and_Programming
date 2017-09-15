package planner.model;

import java.io.Serializable;
import java.util.*;

/** The course data with code and other details
 *  Has a one to many composition relationship with indexes
 */
public class Course implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * The course code unique to each module
	 */
	private String courseCode;
	
	/**
	 * The name of the course
	 */
	private String courseName;
	
	/**
	 * The number of Academic Unit (AU) of the course
	 */
	private int au;
	
	/**
	 * The school that offers the module
	 */
	private String school;
	
	/**
	 * The type of course to be registered as
	 */
	private String courseType;
	
	/** The exam date and time of the module
	 *  Assume that on one day, there are only 3 fix slots for exams
	 *  and the slots are at the same timings everyday
	 */
	private Calendar examDate;
	
	/** The list of indexes associated to the module
	 */
	private List<Index> indexList;
	
	
	public Calendar getExamDate() {
		return examDate;
	}
	

	/** Constructor for Course
	 */
	public Course (String courseCode, String courseName, int au, String school, String courseType, Calendar examDate) {
		this.courseCode			= courseCode;
		this.courseName			= courseName;
		this.au					= au;
		this.school				= school;
		this.examDate			= examDate;
		this.courseType			= courseType;
	}
	
	
	

	public String getCourseCode() {
		return courseCode;
	}
	
	/**
	 * Change the course code
	 * @param courseCode
	 */
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public int getAU() {
		return au;
	}

	public void setAU(int aU) {
		au = aU;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}
	
	public String getCourseType() {
		return courseType;
	}
	
	public void setCourseType(String courseType) {
		this.courseType = courseType;
	}
	
	public void setExamDate(Calendar examDate){
		this.examDate = examDate;
	}
	
	public boolean equals(Object o) {
		if (o instanceof Course) {
			Course st = (Course)o;
			return (getCourseCode().equals(st.getCourseCode()));
		}
		return false;
	}


	/**
	 * @return the index
	 */
	public List<Index> getIndexList() {
		return indexList;
	}


	/**
	 * 
	 * @param index the index to set
	 */
	public void setIndexList(List<Index> index) {
		this.indexList = index;
	}

}