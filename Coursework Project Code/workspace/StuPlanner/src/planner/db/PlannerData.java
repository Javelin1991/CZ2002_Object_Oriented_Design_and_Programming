package planner.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import planner.manager.LoginMgr;
import planner.model.Course;
import planner.model.CourseReg;
import planner.model.Student;
import planner.model.Index;
import planner.model.Staff;

/**
 * Static planner database class keeping track of
 * data in the system
 * @version 1.0
 * @since 2017-04-07
 */
public class PlannerData {
	
	public static final Path 	DATAPATH 					= Paths.get(System.getProperty("user.dir"), "data");
	public static final String 	PLANNER_FILE_NAME			= "planner.dat";	
	
	public static ArrayList<Staff> 					staffList;
	public static ArrayList<Student> 				studentList;
	public static ArrayList<Course> 				courseList;	
	public static ArrayList<CourseReg>				regList;
	
	/**
	 * Save planner's current state
	 */
	public static void savePlanner(){
		
		if(!Files.exists(DATAPATH)){
			System.out.println("Data folder not found!");
			File dir = new File(DATAPATH.toString());
			if(dir.mkdir()){
				System.out.println("Directory " + DATAPATH + " created...");
			}
		}
		
		Object[] plannerObject 	= {	studentList,
									staffList,
									courseList,
									regList
									};
		
		Path 				saveFileName 	= Paths.get(DATAPATH.toString(), PLANNER_FILE_NAME);
		FileOutputStream   	fos 			= null;
		ObjectOutputStream 	oos 			= null;
		
		try {
			fos = new FileOutputStream(saveFileName.toString());
			oos = new ObjectOutputStream(fos);
			oos.writeObject(plannerObject);
			oos.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
	}
	
	/**
	 * Load planner's previous save state.
	 * @throws ParseException 
	 */
	@SuppressWarnings("unchecked")
	public static void loadPlanner() throws ParseException{
		
		Object[] plannerMember 		= null;
		Path saveData 				= Paths.get(DATAPATH.toString(), PLANNER_FILE_NAME);
		FileInputStream fis 		= null;
		ObjectInputStream ois 		= null;
		
		try {
			fis = new FileInputStream(saveData.toString());
			ois = new ObjectInputStream(fis);
			plannerMember = (Object[]) ois.readObject();			
			if(plannerMember != null){
				studentList		= (ArrayList<Student>) 	 plannerMember[0];
				staffList 		= (ArrayList<Staff>)	 plannerMember[1];
				courseList 		= (ArrayList<Course>)  	 plannerMember[2];
				regList 		= (ArrayList<CourseReg>) plannerMember[3];
			}
			ois.close();
		} catch (IOException ex) {
			System.out.println(PLANNER_FILE_NAME + " not found or does not exists. Default configuration will be loaded.");
			initPlanner();
		} catch (ClassCastException|ClassNotFoundException ex) {
			System.out.println("Data file " + PLANNER_FILE_NAME + " is corrupted. Default configurations will be loaded instead.");
			initPlanner();
		}
		
	}
	
	/**
	 * Initialise restaurant static members
	 * @throws ParseException 
	 */
	public static void initPlanner() throws ParseException{
		initStaff();
		initStudent();
		initCourse();
		initReg();
	}
	
	private static void initReg() {
		ArrayList<CourseReg> regList	= new ArrayList<CourseReg>();
		Student stud1 					= studentList.get(0);
		Course course1 					= courseList.get(0);
		Index index1					= (course1.getIndexList()).get(0);
		
		CourseReg courseReg1 = new CourseReg(stud1, course1, index1, true);
		regList.add(courseReg1);
		
		PlannerData.regList = regList;
	}

	/**
	 * Initialise staff data
	 */
	public static void initStaff(){
		ArrayList<Staff> staffList = new ArrayList<Staff>();
		String salt		 = LoginMgr.generateSalt();
		Staff staff1 = new Staff("STAFF1", "STAFF1", salt);
		staffList.add(staff1);
		
		salt		 = LoginMgr.generateSalt();
		Staff staff2 = new Staff("STAFF2", "STAFF2", salt);
		staffList.add(staff2);
		
		PlannerData.staffList = staffList;
	}
	
	/**
	 * Initialise student data
	 */
	public static void initStudent(){
		ArrayList<Student> studentList = new ArrayList<Student>();
		String salt		 = LoginMgr.generateSalt();
		Date parsedDate = null;	
		Calendar newDate1 = Calendar.getInstance();
		Calendar newDate2 = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		//Course1
		try {
			parsedDate = dateFormat.parse("21/10/2017 12:30");
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		newDate1.setTime(parsedDate);
		try {
			parsedDate = dateFormat.parse("22/10/2017 12:30");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		newDate2.setTime(parsedDate);
		
		Student stud1 = new Student("STUD1", "STUD1", salt,
									"Billy", "Tan", "U143245E", 'M', "Singaporean",
									87876272, "tmiharja001@e.ntu.edu.sg", newDate1, newDate2, 2);
		
		
		studentList.add(stud1);
		
		PlannerData.studentList = studentList;
	}
	
	/**
	 * Initialise courses
	 * @throws ParseException 
	 */
	public static void initCourse() throws ParseException{
		ArrayList<Course> courseList = new ArrayList<Course>();
		List<Index> indexList = new ArrayList<Index>();
	    Date parsedDate = null;	
		Calendar newDate = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		//Course1
		parsedDate = dateFormat.parse("21/10/2017 12:30");
		newDate.setTime(parsedDate);
		Index newIndex1 			= new Index(1000, "FSP1", 30, 0);
	
		Course course1 = new Course("CZ2002", "OODP", 3, "SCE", "Core",newDate);
		indexList.add(newIndex1);
		course1.setIndexList(indexList);
		courseList.add(course1);
		
		//Course 2
		parsedDate = dateFormat.parse("20/10/2017 09:30");
		newDate.setTime(parsedDate);
		Index newIndex2 			= new Index(1000, "FSP1", 30, 0);
	
		Course course2 = new Course("CZ2005", "OODP", 3, "SCE", "Core",newDate);
		indexList.add(newIndex2);
		course1.setIndexList(indexList);
		courseList.add(course2);
		
		PlannerData.courseList = courseList;
	}
	

}
