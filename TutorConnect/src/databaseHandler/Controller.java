package databaseHandler;

import java.util.ArrayList;

import event.Event;

/**
 * Interface with the database
 * 
 */

public class Controller {
	private static Database db = null; // Holds database connection
	private static boolean searched = false;
	
	/**
	 * Gets a connection to the database
	 * @return db the database object
	 */
	private static Database getDb() {
		System.out.println("inside getDb");
		if(db == null) {
			System.out.println("db is null");
			db = new Database();
			System.out.println("created new DatabaseConnection");
		}
		
		System.out.println("db not null");
		return db;
	}
	
	/**
	 * Creates a new event in the database
	 * @param event Event to be pushed.
	 */
	public static void pushEvent(Event event) {
		System.out.println("inside pushEvent");
		getDb().createEntry(event);
		System.out.println("entry created");
	}
	
	/**
	 * Delete an event in the database
	 * @param username the username to check when deleting.
	 * @param id the event id for the event to be deleted
	 */
	public static boolean deleteEvent(String username, String id) {
		System.out.println("inside deleteEvent");
		return getDb().deleteEntry(username, Integer.parseInt(id));
		//System.out.println("entry deleted");
	}
	
	/**
	 * Gets the event list from the database
	 * @return entries ArrayList<event> the list of event objects from DB.
	 */
	public static String getEventList() {
		String entries = "";
		System.out.println("inside getEventList");
		ArrayList<Event> events = getDb().selectEntry();
		for (Event e : events){
			entries = entries + "<span>"+ e.stringToHTML() + "</span><br/>";
		}
		System.out.println(entries);
		return entries;
	}
	
	/**
	 * Gets the event list from the database matching search string
	 * @return entries ArrayList<event> the list of event objects from DB.
	 */
	public static String getSearchedEvents(String searchString) {
		String entries = "";
		System.out.println("inside getSearchedEvents");
		ArrayList<Event> events = getDb().searchedEntry(searchString);
		for (Event e : events){
			entries = entries + "<span>"+ e.stringToHTML() + "</span><br/>";
		}
		System.out.println(entries);
		if(entries.isEmpty()){
			searched = false;
		} else{
			searched = true;
		}
		return entries;
	}
	
	/**
	 * Gets the event list from the database matching username
	 * @return entries ArrayList<event> the list of event objects from DB.
	 */
	public static String getMyEvents(String username) {
		String entries = "";
		System.out.println("inside getEventList");
		ArrayList<Event> events = getDb().myEntries(username);
		
		for (Event e : events){
			entries = entries + "<span>"+ e.myEventsStringToHTML() + "</span><br/>";
		}
		System.out.println(entries);
		
		return entries;
	}
	
	/**
	 * Adds a new user's credetnials to the user table using the DB connection object
	 * @return boolean showing the status of the sql query
	 */
	public static boolean addUser(User user){
		 return getDb().addUser(user);
	  }
	
	/**
	 * Checks whether a user's credentials exists in our database 
	 * @return boolean showing the status of the sql query
	 */
	public static boolean userExist(User user){
		  return getDb().userExist(user);
	  }
	
	/**
	 * Checks for the status of search 
	 * @return boolean showing the status of the sql query for search
	 */
	public static boolean hasSearched(){
		if(searched == false){
			return false;
		} 
		searched = false;
		return true;
	}
}
