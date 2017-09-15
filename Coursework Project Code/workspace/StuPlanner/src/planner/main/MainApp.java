package planner.main;

import java.io.*;
import java.text.*;
import planner.UI.LoginUI;
import planner.db.PlannerData;
import planner.model.User;

/**
 * Main application class to start up the Student Planner
 * application. Provides an interface for both students and 
 * staff to manage courses
 * @version 1.0
 * @since 2017-04-06
 */

public class MainApp {
	
	public static void main(String[] args) throws ParseException, IOException {
		//while(true){
			// Initialise data from the previous session
			PlannerData.loadPlanner();
			
			// The UI for login and return the user class
			User user = LoginUI.showLoginUI();
			
			// (Upcasting) the correct UI will be loaded based on the class
			user.showUI();
		
			//Save state before closing
			PlannerData.savePlanner();
		//}
	}
}
