package planner.UI;

import java.text.*;
import java.util.*;
import java.io.*;

/**
 * The UI displayed to the staff as the admin.
 */
public class StaffUI{
	
	private static Scanner sc = new Scanner(System.in);
	/**
	 * Display options user can perform on restaurant food menu.
	 * User can Add new food, new promotion package or remove 
	 * menu item from menu
	 * @throws ParseException 
	 * @throws IOException 
	 */	

	public static void showStaffOption() throws IOException, ParseException {
		int choice;
		
		StaffWhileLoop:
		while(true){
			System.out.println("***Welcome to Staff panel!***");
			System.out.println("Which section do you want to go to?");
			System.out.println("(1) Student Section");
			System.out.println("(2) Course Section");
			System.out.println("(3) Logout");

			System.out.print("> ");
			choice = Integer.parseInt(sc.nextLine());
			try{
				switch (choice) {
					case 1: // Go to the student section
						StaffStudentUI.addUpdateStudentUI();
						break;
					case 2: // Go to the course section
						StaffCourseUI.addUpdateCourseUI();
						break;
					case 3: // Log out
						System.out.println("Successfully Logged Out!");
						System.out.println();
						break StaffWhileLoop;
					default:
						System.out.println("Invalid Input! Please re-enter!");
				}}
			catch (Exception e) {
				System.out.println("Invalid Input! Please re-enter!");
			}
			System.out.println();
		}
	}


    
}
