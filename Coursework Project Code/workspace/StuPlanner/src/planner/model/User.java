package planner.model;
import java.io.*;
import java.text.ParseException;

/** An abstract class for the users for the application
 */
public abstract class User implements Serializable {

	private static final long serialVersionUID = 1L;
	private String username ;
	private String password ;
	private String accessRight;
	private String salt;

	public User(String username, String password, String accessRight, String salt)  {
		this.username = username ;
		this.password = password ;
		this.accessRight = accessRight ;
		this.salt = salt;
	}
	
	public String getUsername() { 
		return username; 
	}
	
	public String getPassword() { 
		return password; 
	}
	
	public String getAccessRight() { 
		return accessRight; 
	}
	
	public String getSalt() { 
		return salt; 
	}

	public abstract boolean equals(Object obj);

	public abstract void showUI() throws IOException, ParseException;
	
}