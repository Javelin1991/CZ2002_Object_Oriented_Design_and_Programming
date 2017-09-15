package planner.manager;

import java.security.*;
import java.util.*;
import planner.db.PlannerData;
import planner.model.Staff;
import planner.model.Student;
import planner.model.User;

/**
 * A manager to validate the user name and passwords of the user
 * @version 1.0
 * @since 2017-04-06
 */

public class LoginMgr {
	
	static byte[] hashBytes;
	static ArrayList<Student> studentList 	= PlannerData.studentList;
	static ArrayList<Staff> staffList 		= PlannerData.staffList;

	/**
	 * Compare user password with the one registered in database
	 * @param username
	 * @param passwordToHash
	 * @param accountType
	 * @return
	 */
	public static User compareUserPass(String username, String passwordToHash, String accessRight){
		
		String salt;
		String securePassword;
		String correctPassword;
		ArrayList<Student> studentList 	= PlannerData.studentList;
		ArrayList<Staff> staffList 		= PlannerData.staffList;
		

		if (accessRight == "ADMIN"){
			for (Staff staff: staffList) {
				//retrieve salt from text data
	            salt 			= staff.getSalt();
	            
	            //hash user password input with salt
				securePassword 	= hashing(passwordToHash, salt);
				
				//hash the correct password input with salt;
				correctPassword = hashing(staff.getPassword(), staff.getSalt());
				
				//compare user input hash with hash retrieved from text data
				if (username.toLowerCase().equals(staff.getUsername().toLowerCase()) && securePassword.equals(correctPassword)) {
					return staff;
				}
			}
		}
		else if (accessRight == "STUDENT"){
			for (Student student: studentList) {
				//retrieve salt from text data
	            salt 			= student.getSalt();
	            
	            //hash user password input with salt
				securePassword 	= hashing(passwordToHash, salt);
				
				//hash the correct password input with salt;
				correctPassword = hashing(student.getPassword(), student.getSalt());
				
				//compare user input hash with hash retrieved from text data
				if (username.toLowerCase().equals(student.getUsername().toLowerCase()) && securePassword.equals(correctPassword)) {
					return student;
				}
			}
		}
		return null;
	}
	
	/** Generate salt for hashing
	 * @return 
	 */
	public static String generateSalt() {
		String salt;
		// random alphanumeric generator
		salt = UUID.randomUUID().toString();
		return salt;
	}
	
	/** Perform hashing of password
	 * @param passwordToBeHash
	 * @param salt
	 * @return
	 */
	public static String hashing(String passwordToBeHash, String salt)
	{
		String hashPassword = "";
		
		try {
			//generate salt
			if (salt.equals("")) {
				salt = generateSalt();
			}
			
			StringBuilder stringBuilder = new StringBuilder(passwordToBeHash);
			
			// Prepend salt to passwordToBeHash
			String passwordWithSaltToHash = stringBuilder.insert(0, salt).toString();
			
			// create SHA-512 Message Digest Instance
			MessageDigest sha512algo = MessageDigest.getInstance("SHA-512");

			// Get hash in decimal format
			StringBuilder sb2 = new StringBuilder();
			hashBytes = sha512algo.digest(passwordWithSaltToHash.getBytes());
			
			for (int i = 0; i < hashBytes.length; i++) {
				// convert it to hexadecimal format
				sb2.append(Integer.toString((hashBytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			
			hashPassword = sb2.toString();
			
		} catch (Exception e) {
			System.out.println("IOException > " + e.getMessage());
		}

		return hashPassword;
	}

	/** Verify if the time now now is within the access period
	 * @param loggedInUser
	 * @return true if now is within access period and false if it is not within the period
	 */
	public static Boolean isValidAccessTime(Student loggedInUser) {
		Calendar now = Calendar.getInstance();
			    
		int compareAccessStart 	= loggedInUser.getAccessStart().compareTo(now);
		int compareAccessEnd 	= loggedInUser.getAccessEnd().compareTo(now);
			   
		if (compareAccessStart*compareAccessEnd > 0){
			System.out.println("Sorry you are not allowed to access the portal now!");
			System.out.println("Please log in at your specified access period!");
			System.out.println("Your access time is from " + CalendarMgr.dateToString(loggedInUser.getAccessStart()) +
							   " to " + CalendarMgr.dateToString(loggedInUser.getAccessEnd()));
			System.out.println();
			return false;
		}
		return true;
	}
}