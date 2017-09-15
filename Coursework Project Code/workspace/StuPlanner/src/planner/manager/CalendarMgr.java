package planner.manager;

import java.text.*;
import java.util.*;

/**
 * This class is responsible of getting the right input format
 * to allow for the conversion of the input to the Calendar class
 */
public class CalendarMgr {
	
	static Scanner sc = new Scanner(System.in);
	static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");	
	
	/** Convert Calendar to String of the right format
	 * @param cal
	 */
	public static String dateToString(Calendar cal) {
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		return String.format("%02d/%02d/%4d %02d:%02d", day, month + 1, year, hour, minute);
	}
	
	/** Convert Calendar time to String of the right format
	 * @param cal
	 */
	public static String timeToString(Calendar cal) {
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		return String.format("%02d:%02d", hour, minute);
	}
	
	/**
	 * Prompt user to give a valid access period
	 * @return the VALID reservation date time
	 */
	public static Calendar getValidDateTime(String mode){
		
		String date = "";

	    Date parsedDate = null;
		boolean validDate = false;		
		Calendar newDate = Calendar.getInstance();
		
		do{
			System.out.print("Enter " + mode + " (dd/MM/yyyy HH:mm): ");
			date  = sc.nextLine();
			dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		    try {
		    	parsedDate = dateFormat.parse(date);
		    	 
		    } catch (ParseException e) {
		        System.out.println("Input is not in the correct format!");
		        continue;
		    }
		    newDate.setTime(parsedDate);

		    validDate = true;

		} while(!validDate);
				
		return newDate;
	}
	
	/** Get the valid lesson time with the right format from user
	 * @param mode
	 * @return
	 */
	public static Calendar getValidLessonTime(String mode){
		
		String time = "";

	    Date parsedTime = null;
		boolean validTime = false;		
		Calendar newTime = Calendar.getInstance();
		
		do{
			System.out.print("Enter the lesson " + mode + " time (e.g. 13:30): ");
			time = sc.nextLine();
			dateFormat = new SimpleDateFormat("HH:mm");
		    try {
		    	parsedTime = dateFormat.parse(time);
		    	 
		    } catch (ParseException e) {
		        System.out.println("Input is not in the correct format!");
		        continue;
		    }
		    newTime.setTime(parsedTime);

		    validTime = true;

		} while(!validTime);
				
		return newTime;
	}
	
	public static String dayIntToString(int day){
		String dayString = null;
		switch(day){
			case 1: dayString = "MON";
					break;
			case 2: dayString = "TUE";
					break;
			case 3: dayString = "WED";
					break;
			case 4: dayString = "THU";
					break;
			case 5: dayString = "FRI";
					break;
			case 6: dayString = "SAT";
					break;	
			case 7: dayString = "SUN";
					break;
		}
		return dayString;
	}
}
