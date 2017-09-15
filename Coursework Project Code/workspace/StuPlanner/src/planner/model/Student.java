
package planner.model;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import planner.UI.StudentCourseUI;

/** Student class that implements the User abstract class
 *  has a unique STUDENT accessRight and additional attributes
 */

public class Student extends User{
	
	private static final long serialVersionUID = 1L;
	private String firstName; 
	private String lastName;
	private String matricNumber;
	private char gender;
	private String nationality; 
	private int mobileNo;
	private String email;
	private Calendar accessStart;
	private Calendar accessEnd;
	private int notiMode;
	
	//constructor
	public Student (String userName, String password, String salt,
					String firstName, String lastName, String matricNumber, char gender,
					String nationality, int mobileNo, String email, Calendar accessStart, Calendar accessEnd, int notiMode) {
		super(userName, password, "STUDENT", salt);
		this.firstName 			= firstName;
		this.lastName 			= lastName;
		this.matricNumber		= matricNumber;
		this.gender				= gender;
		this.nationality		= nationality;
		this.mobileNo           = mobileNo;
		this.email              = email;
		this.accessStart 		= accessStart;
		this.accessEnd 			= accessEnd;
		this.notiMode			= notiMode;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMatricNumber() {
		return matricNumber;
	}

	public void setMatricNumber(String matricNumber) {
		this.matricNumber = matricNumber;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public int getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(int mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Calendar getAccessStart() {
		return accessStart;
	}

	public void setAccessStart(Calendar accessStart) {
		this.accessStart = accessStart;
	}

	public Calendar getAccessEnd() {
		return accessEnd;
	}

	public void setAccessEnd(Calendar accessEnd) {
		this.accessEnd = accessEnd;
	}

	public int getNotiMode() {
		return notiMode;
	}

	public void setNotiMode(int notiMode) {
		this.notiMode = notiMode;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Student) {
			Student acc = (Student)obj;
			return (getUsername().equals(acc.getUsername()));
		}
		return false;
	}

	@Override
	public void showUI() throws IOException, ParseException {
		StudentCourseUI.showStudentOption(this);
	}
	
	
}