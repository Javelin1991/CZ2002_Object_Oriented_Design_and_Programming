package planner.model;

import java.io.Serializable;

/** An association class linking the student and the course
 *  that they have registered
 */
public class CourseReg implements Serializable{
	
	private Student student;
	private Course course;
	private Index index;
	
	// True when it is registered and false when it is on waitlist
	private Boolean Status;

	public CourseReg(Student student, Course course, Index index, Boolean status){
		this.setStudent(student);
		this.setCourse(course);
		this.setIndex(index);
		this.setStatus(status);
	}

	/**
	 * @return the student
	 */
	public Student getStudent() {
		return student;
	}

	/**
	 * @param student the student to set
	 */
	public void setStudent(Student student) {
		this.student = student;
	}

	/**
	 * @return the course
	 */
	public Course getCourse() {
		return course;
	}

	/**
	 * @param course the course to set
	 */
	public void setCourse(Course course) {
		this.course = course;
	}

	/**
	 * @return the index
	 */
	public Index getIndex() {
		return index;
	}

	/**
	 * @param index the index to set
	 */
	public void setIndex(Index index) {
		this.index = index;
	}

	/**
	 * @return the status
	 */
	public Boolean getStatus() {
		return Status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Boolean status) {
		Status = status;
	}
	
}