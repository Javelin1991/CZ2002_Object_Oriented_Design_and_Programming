package planner.model;

import java.io.Serializable;
import java.util.Calendar;

/** Specify the venue and timing of the lesson
 */
public class Lesson implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String lessonType;
	private int lessonDay;
	private String lessonVenue;
	private Calendar lessonStartTime;
	private Calendar lessonEndTime;
	
	public Lesson (String lessonType, int lessonDay, Calendar lessonStartTime, Calendar lessonEndTime, String lessonVenue){
		this.lessonType	 		= lessonType;
		this.lessonDay 			= lessonDay;
		this.lessonStartTime 	= lessonStartTime;
		this.lessonEndTime		= lessonEndTime;
		this.lessonVenue 		= lessonVenue;
	}

	public String getLessonType() {
		return lessonType;
	}

	public void setLessonType(String lessonType) {
		this.lessonType = lessonType;
	}

	public int getLessonDay() {
		return lessonDay;
	}

	public void setLessonDay(int lessonDay) {
		this.lessonDay = lessonDay;
	}

	public String getLessonVenue() {
		return lessonVenue;
	}

	public void setLessonVenue(String lessonVenue) {
		this.lessonVenue = lessonVenue;
	}


	/**
	 * @return the lessonStartTime
	 */
	public Calendar getLessonStartTime() {
		return lessonStartTime;
	}

	/**
	 * @param lessonStartTime the lessonStartTime to set
	 */
	public void setLessonStartTime(Calendar lessonStartTime) {
		this.lessonStartTime = lessonStartTime;
	}

	/**
	 * @return the lessonEndTime
	 */
	public Calendar getLessonEndTime() {
		return lessonEndTime;
	}

	/**
	 * @param lessonEndTime the lessonEndTime to set
	 */
	public void setLessonEndTime(Calendar lessonEndTime) {
		this.lessonEndTime = lessonEndTime;
	}
	
}
