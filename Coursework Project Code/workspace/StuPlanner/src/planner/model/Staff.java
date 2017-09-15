package planner.model;

import java.io.IOException;
import java.text.ParseException;

import planner.UI.StaffUI;

/** Staff class that implements the User abstract class
 *  has a unique ADMIN accessRight
 */
public class Staff extends User{
	
	private static final long serialVersionUID = 1L;
	
	public Staff (String userName, String password, String salt){
		super(userName, password, "ADMIN", salt);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Staff) {
			Staff acc = (Staff)obj;
			return (getUsername().equals(acc.getUsername()));
		}
		return false;
	}

	@Override
	public void showUI() throws IOException, ParseException {
		StaffUI.showStaffOption();	
	}
	

}
