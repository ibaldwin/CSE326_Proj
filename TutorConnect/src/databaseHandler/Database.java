package databaseHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import event.Event;

/**
 * Responsible for the Database connection
 *
 */
public class Database
{
	private Connection c;
	
	
	
	//Constructor which creates the database connection
	public Database(){
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:E:\\Softwares\\workspace\\TutorConnect\\tutorConnect.db");
			c.setAutoCommit(true);
			System.out.println("Opened database successfully");
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			e.printStackTrace(System.out);
			c = null;
			//System.exit(0);
		}
		
	}
	
	//method to close the connection
	public void exterminate(){
		try {
			c.close();
		} catch (SQLException e) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			c = null;
			//System.exit(0);
		}
	}
	
/**
 * Creates the initial table
 * Modified from code from http://www.tutorialspoint.com/sqlite/sqlite_java.htm
 */
  public void createTable( )
  {
    Statement stmt = null;
    try {
      stmt = c.createStatement();
      String sql = "CREATE TABLE EVENT(" +
    		  	" TITLE			TEXT,"	+
    		  	" NAME			TEXT, " + 
    		  	" DAY			TEXT	DEFAULT CURRENT_DATE, " + 
    		  	" LOCATION		TEXT, " + 
    		  	" DESCRIPTION	TEXT, "+
    		  	" RSVP			INT, " + 
    		  	" VOTE			INT)";
      
      stmt.executeUpdate(sql);
      stmt.close();
      
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      e.getStackTrace();
      //System.exit(0);
    }
    System.out.println("EVENT Table created successfully");
    
    try {
        stmt = c.createStatement();
        String sql = "CREATE TABLE USER(" +
      		  	" NAME			TEXT, " + 
      		  	" PASSWORD		TEXT)";
        
        stmt.executeUpdate(sql);
        stmt.close();
        
      } catch ( Exception e ) {
        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        e.getStackTrace();
        //System.exit(0);
      }
      System.out.println("EVENT Table created successfully");
      
    
  }
  
  /**
   * Adds a new event to the table
   * @param event containing all the event details  
   **/
  public void createEntry(Event event)
  {
		  Statement stmt = null;
		  try {
			  stmt = c.createStatement();
			  String sql = "INSERT INTO EVENT (TITLE, NAME, DAY, LOCATION, DESCRIPTION, RSVP, VOTE)"
					  + "VALUES ('" + event.getTitle()
					  + "', '" + event.getCreatorName()
					  + "', '" + event.getDatetime()
					  + "', '" + event.getLocation()
					  + "', '" + event.getDescription() + "', 0, 0);"; 
			  System.out.println(sql);
			  stmt.executeUpdate(sql);
			  stmt.close();
		      
		  } catch ( Exception e ) {
			  System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			  e.getStackTrace();
			  //System.exit(0);
		  }
		  System.out.println("Entry created successfully");
	  }
  
  /**
   * Deletes an entry from the table
   * @param username the username to verify ownership of an event
   * @param id the event id of the event to delete
   * @return boolean with status of the query
   */
  public boolean deleteEntry(String username, int id) {
		  Statement stmt = null;
		  try {
			  stmt = c.createStatement();
			  String sql = "DELETE FROM EVENT WHERE ID IS " + id + " AND NAME IS '" + username + "';"; 
			  System.out.println(sql);
			  stmt.executeUpdate(sql);
			  sql = "SELECT * FROM EVENT WHERE ID IS " + id + ";" ;
			  ResultSet rs = stmt.executeQuery(sql);
				if(rs.next()){
					stmt.close();
					return false;
				}
			  stmt.close();
			  
		      
		  } catch ( Exception e ) {
			  System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			  e.getStackTrace();
			  //System.exit(0);
			  return false;
		  }
		  System.out.println("deleted event successfully");
		  return true;
  	}
  /**
   * select all entry from the table
   * @return arrayList<event> with a list of event objects
   */
  public ArrayList<Event> selectEntry()
  {
	  ArrayList<Event> eventList = new ArrayList<Event>();  
	  Statement stmt = null;
	  try {
		  stmt = c.createStatement();
		  String sql = "SELECT * FROM EVENT"; 
		  ResultSet result = stmt.executeQuery(sql);
		  
		  while(result.next()) {
			  eventList.add(new Event(result.getString("TITLE").toString(),result.getString("DAY").toString(), result.getString("NAME"),
					  					result.getString("LOCATION"), result.getString("DESCRIPTION"),
					  					result.getInt("RSVP"), result.getInt("VOTE")));
		  }
		  
		  stmt.close();
	      
	  } catch ( Exception e ) {
		  System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		  e.getStackTrace();
		  //System.exit(0);
	  }
	  System.out.println("Entries collected.");
	  
	  return eventList;
  }
  
  /**
   * returns the list of entries matching the search string
   * @param searchString the string that is searched
   * @return ArrayList<Event> list of all the events matching the search
   */
  public ArrayList<Event> searchedEntry(String searchString) {
	  ArrayList<Event> searchList = new ArrayList<Event>();  
	  Statement stmt = null;
	  try {
		  stmt = c.createStatement();
		  String sql = "SELECT * FROM EVENT WHERE TITLE LIKE '%" + searchString + "%';"; 
		  ResultSet result = stmt.executeQuery(sql);
		  
		  while(result.next()) {
			  searchList.add(new Event(result.getString("TITLE").toString(),result.getString("DAY").toString(), result.getString("NAME"),
					  					result.getString("LOCATION"), result.getString("DESCRIPTION"),
					  					result.getInt("RSVP"), result.getInt("VOTE")));
		  }
		  
		  stmt.close();
	      
	  } catch ( Exception e ) {
		  System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		  e.getStackTrace();
		  //System.exit(0);
	  }
	  System.out.println("Searched events collected");
	  
	  return searchList;
  }
  
  /**
   * returns the list of entries matching username
   * @param username of the user
   * @return ArrayList<Event> list of all the events matching the username
   */
  public ArrayList<Event> myEntries(String username) {
	  ArrayList<Event> myEventList = new ArrayList<Event>();  
	  Statement stmt = null;
	  try {
		  stmt = c.createStatement();
		  String sql = "SELECT * FROM EVENT WHERE NAME LIKE '" + username + "';"; 
		  ResultSet result = stmt.executeQuery(sql);
		  
		  while(result.next()) {
			  myEventList.add(new Event(result.getInt("ID"), result.getString("TITLE").toString(),result.getString("DAY").toString(), result.getString("NAME"),
					  					result.getString("LOCATION"), result.getString("DESCRIPTION"),
					  					result.getInt("RSVP"), result.getInt("VOTE")));
		  }
		  
		  stmt.close();
	      
	  } catch ( Exception e ) {
		  System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		  e.getStackTrace();
		  //System.exit(0);
	  }
	  System.out.println("My events collected");
	  
	  return myEventList;
  }
  
  /**
   * Edits an event in the table
   */
  public void editEntry() {
	  
  }
  
  /**
	 * Adds a new user's credetnials to the user table using the DB connection object
	 * @return boolean showing the status of the sql query
	 */
  protected boolean addUser(User user){
	  return User.addUser(c, user);	  
  }
  
  /**
	 * Checks whether a user's credentials exists in our database 
	 * @return boolean showing the status of the sql query
	 */
  protected boolean userExist(User user){
	  return User.checkUser(c, user);
  }
  

}