package planner.UI;

import java.io.Console;
import java.text.ParseException;
import java.util.Scanner;

import planner.manager.LoginMgr;
import planner.model.Student;
import planner.model.User;

/** UI to get the username and password of the user
 */
public class LoginUI {
	static Scanner sc = new Scanner(System.in);

	public static User showLoginUI() throws ParseException {
		do {
			String accessRight = "";
			String username;
			String password;
			User loggedInUser;
			
			loginVerifyLoop: 
			while (true) {
				accessRight = domainUI();
				
				Console userConsole = System.console();
				if (userConsole != null) { // Password masking for console
					username = userConsole.readLine("Username: ");
					password = String.valueOf(userConsole.readPassword("Password: "));
				}
				else{
					System.out.print("Username: "); username = sc.nextLine();
					System.out.print("Password: "); password = sc.nextLine();	
				}
				
				loggedInUser = LoginMgr.compareUserPass(username, password, accessRight);
				
				if (!(loggedInUser == null)) {
					break loginVerifyLoop; }
				
				System.out.println();
				System.out.println("Incorrect username or password! Please re-enter!");
				System.out.println();
			}

			System.out.println();
			System.out.println("Hello, " + loggedInUser.getUsername());

			if (loggedInUser.getAccessRight().equals("STUDENT")) {
				if (LoginMgr.isValidAccessTime((Student) loggedInUser)){
					return loggedInUser;}
			}
			else{
				return loggedInUser;
			}
		} while (true);
		
		
	}
	
	private static String domainUI() {
		String accessRight = "";
		int choice = 0;
		domainLoop:
		while(true){
			System.out.println("********Select Domain********");
			System.out.println("(1) Student");
			System.out.println("(2) Staff");
			System.out.print("> ");
			try {
				choice = Integer.parseInt(sc.nextLine());
				switch (choice) {
				case 1:
					accessRight = "STUDENT";
					break domainLoop;
				case 2:
					accessRight = "ADMIN";
					break domainLoop;
				default:
					System.out.println("Invalid Input! Please re-enter!");
					System.out.println();
				}
			} catch (Exception e) {
				System.out.println("Invalid Input! Please re-enter!");
				System.out.println();
			}
		}
		return accessRight;
	}

}
