package planner.model;

import java.io.Serializable;
import java.util.List;

/** The index of the class
 *  It has a one to many composition relationship with lessons
 * 
 */

public class Index implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int indexNumber;
	private String tutorialGroup;
	private int vacancy;
	private int waitingList;
	private List<Lesson> lessonList;
	
	
	public Index (int indexNumber, String tutorialGroup, int vacancy, int waitingList){
		this.indexNumber = indexNumber;
		this.tutorialGroup = tutorialGroup;
		this.vacancy = vacancy;
		this.waitingList = waitingList;
	}

	//Index Number
	public int getIndexNumber()
	{
		return indexNumber;
	}
	public void setIndexNumber(int indexNumber)
	{
		this.indexNumber = indexNumber;
	}
	// Tutorial Group
	public String getTutorialGroup()
	{
		return tutorialGroup;
	}
	public void setTutorialGroup(String tutorialGroup)
	{
		this.tutorialGroup = tutorialGroup;
	}
	//Vacancy
	public int getVacancy()
	{
		return vacancy;
	}
	public void setVacancy(int vacancy)
	{
		this.vacancy = vacancy;
	}
	//Waiting List
	public int getWaitingList()
	{
		return waitingList;
	}
	public void setWaitingList(int waitingList)
	{
		this.waitingList = waitingList;
	}
	
	public boolean equals(int o) {
		return getIndexNumber() == o;
	}

	/**
	 * @return the lessonList
	 */
	public List<Lesson> getLessonList() {
		return lessonList;
	}

	/**
	 * @param lessonList the lessonList to set
	 */
	public void setLessonList(List<Lesson> lessonList) {
		this.lessonList = lessonList;
	}
	
	/** Add the waiting list by one
	 */
	public void waitingListPlus (){
		this.waitingList++;
	}
	
	/** Deduct the waiting list by one
	 */
	public void waitingListMinus (){
		this.waitingList--;
	}
	
	/** Add the vacancy by one
	 */
	public void vacancyPlus (){
		this.vacancy++;
	}
	
	/** Deduct the vacancy by one
	 */
	public void vacancyMinus (){
		this.vacancy--;
	}

}