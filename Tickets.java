package finalPackage;
//Adrian Aponte
//ITMD411 Final Project
// A help desk trouble ticket system using JDBC and SQL. Allows user to insert, view, update, and delete tickets.
import java.sql.*;
import java.util.Random;
import java.util.Scanner;

public class Tickets {
	
	  // declare a Scanner class object
	  final static Scanner sc = new Scanner(System.in);

	  private Connection connect = null;
	  private Statement statement = null;

	  static String ans=null;
	  
	  public void createDataBase() throws Exception {
	    try {
	      // This will load the MySQL driver, each DB has its own driver
	      Class.forName("com.mysql.jdbc.Driver");
	      // Setup the connection with the DB
	      connect = DriverManager.getConnection("jdbc:mysql://www.papademas.net/tickets?"
	              + "user=****&password=*****");
	      System.out.println("Connected Succesfully...");
	      //create table
	    
	      statement = connect.createStatement();
	      //Creates Table titled aaponTicket with all these column names
	      String sql = "CREATE TABLE aaponTicket " +
	                   "(id INTEGER NOT NULL AUTO_INCREMENT, " +
	                   " ticketNum INTEGER, " +" dateStart TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "+"lastModifyDate DATETIME, "+
	                   " ticketDesc VARCHAR(100), " +"ticketStatus VARCHAR(50), "+"userID VARCHAR(50), "+"assignee VARCHAR(50), "+ "lastModifiedBy VARCHAR(50), "+
	                   " PRIMARY KEY ( id ))"; 

	      statement.executeUpdate(sql);
	      System.out.println("Created table aaponTicket in given database...");
	      
		//end create table
	      
	    } catch (Exception e) {
	    	System.out.println(e.getMessage());  
	    }
	    finally{
	    	if (statement != null) statement.close();//closes statement
		    if (connect != null) connect.close();//closes connection
	    }
	    }
	  public void insertIntoDataBase() throws Exception {
		    try {
		    
		    int ticket_num=0;
		    String ticket_desc=null;
		    String ticket_status=null;
		    String ticket_userID=null;
		    //automatically sets ticket status variable to new when inserting a ticket
		    char tickstatus ='n';
		        switch (tickstatus) {
		            case 'n':  ticket_status= "New";
		                     break;
		            case 'o':  ticket_status = "Open";
		                     break;
		            case 'p':  ticket_status = "Pending";
		                     break;
		            case 'c':  ticket_status = "Closed";
		                     break;
		            default: ticket_status = "No Update";
		                     break;
		        }
		    Random rand = new Random();
		    
		    //generate random ticket number
		     ticket_num = rand.nextInt(10001) + 1000;  //generate number from 1000 to 11000 	
		    //prompt user for ticket information
			System.out.println("Enter trouble ticket description");
			ticket_desc = sc.nextLine();
			//prompt user for their User ID
			System.out.println("Enter your User ID");
			ticket_userID= sc.nextLine();
			
         // This will load the MySQL driver, each DB has its own driver
		      Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection with the DB
			 connect = DriverManager.getConnection("jdbc:mysql://www.papademas.net/tickets?"
				              + "user=root&password=jamesp");
			 System.out.println("Connected Succesfully...");
		    	 //System.out.println("Creating trouble ticket...");
		         
		    	 statement = connect.createStatement();
				 //Executes insert into apponTicket table with user provided ticket description and user ID     
		         String sql = "INSERT INTO aaponTicket (ticketNum,ticketDesc,ticketStatus,userID) " +
		                      "VALUES ("+ticket_num+",'"+ticket_desc+"','"+ticket_status+"','"+ticket_userID+"')";
		         statement.executeUpdate(sql);
		         //returns to user their ticket number for future reference
		         System.out.println("Ticket created.Your ticket number is: "+ticket_num);
				 sleep();
		      
			  } catch (Exception e) {
			    System.out.println(e.getMessage());  
			  }
		    finally{
		    	if (statement != null) statement.close();//closes statement
				if (connect != null) connect.close();//closes connection
		    }
		}

	  public void viewFromDataBase() throws Exception {
		    try {
		    	Class.forName("com.mysql.jdbc.Driver");
				//Setup the connection with the DB
				connect = DriverManager.getConnection("jdbc:mysql://www.papademas.net/tickets?"
				         + "user=root&password=jamesp");
				System.out.println("Connected Succesfully...");
		    	int ticket_num;
		    	String numtick="";
		    	int numberOfTickets;
		    	while(numtick == null || numtick.equals("")||integerValidation(numtick)==false||positiveValidation(numtick)==false)
		    	{
		    		System.out.println("Do you want to see a specific ticket or all tickets? \n1 for a specific ticket \nany other positive integer for all tickets");
		    		numtick=sc.nextLine();
		    	}
		    	numberOfTickets =Integer.parseInt(numtick);
		    	
		    	//if user wants to view only one ticket this if statement will trigger
		    	if (numberOfTickets==1)
		    	{
		    		
		    		System.out.println("What is the ticket number?");
		    		ticket_num=sc.nextInt();
		    		
		   		 statement = connect.createStatement();
			      //SELECTS whole contents of aaponTicket table
		         String sql = "SELECT *" +
		                      "FROM aaponTicket WHERE ticketNum="+ticket_num;
		         ResultSet rs = statement.executeQuery(sql);
		       //while there are still records their information will be outputed
			       while(rs.next()){
			           //Retrieve by column name
			           int id  = rs.getInt("id");
			           int tickNum = rs.getInt("ticketNum");
			           Date dateStart = rs.getDate("dateStart");
			           Date lastModDate = rs.getDate("lastModifyDate");
			           String ticketDes = rs.getString("ticketDesc");
			           String ticketStat = rs.getString("ticketStatus");
			           String userID = rs.getString("userID");
			           String assignee = rs.getString("assignee");
			           String lastModifiedBy = rs.getString("lastModifiedBy");
			           //Display values
			           System.out.print("ID: " + id);
			           System.out.print(" ticketNum: " + tickNum);
			           System.out.print(" dateStart: " + dateStart);
			           System.out.print(" lastModifyDate: " + lastModDate);
			           System.out.print(" ticketDesc: " + ticketDes);
			           System.out.print(" ticketStatus: " + ticketStat);
			           System.out.print(" userID: " + userID);
			           System.out.print(" assignee: " + assignee);
			           System.out.print(" lastModifiedBy: " + lastModifiedBy+"\n\n");
			        }
			       rs.close();
		    	}//if user types any other number besides one this else clause will trigger
		    	else{
		    		 statement = connect.createStatement();
				      //SELECTS whole contents of aaponTicket table
			         String sql = "SELECT *" +
			                      "FROM aaponTicket";
			         ResultSet rs = statement.executeQuery(sql);
			       //while there are still records their information will be outputed
				       while(rs.next()){
				           //Retrieve by column name
				           int id  = rs.getInt("id");
				           int tickNum = rs.getInt("ticketNum");
				           Date dateStart = rs.getDate("dateStart");
				           Date lastModDate = rs.getDate("lastModifyDate");
				           String ticketDes = rs.getString("ticketDesc");
				           String ticketStat = rs.getString("ticketStatus");
				           String userID = rs.getString("userID");
				           String assignee = rs.getString("assignee");
				           String lastModifiedBy = rs.getString("lastModifiedBy");
				           //Display values
				           System.out.print("ID: " + id);
				           System.out.print(" ticketNum: " + tickNum);
				           System.out.print(" dateStart: " + dateStart);
				           System.out.print(" lastModifyDate: " + lastModDate);
				           System.out.print(" ticketDesc: " + ticketDes);
				           System.out.print(" ticketStatus: " + ticketStat);
				           System.out.print(" userID: " + userID);
				           System.out.print(" assignee: " + assignee);
				           System.out.print(" lastModifiedBy: " + lastModifiedBy+"\n\n");
				        }
				       rs.close();
		    		
		    	}
		    	return;
		    	
		    } catch (Exception e) {
			    System.out.println(e.getMessage());  
			  } finally{
				  
				  if (statement != null) statement.close();//closes statement
				  if (connect != null) connect.close();	//closes connection
			  }
		}
		    

	  public void updateDataBase() throws Exception {
		    try {
		    
		    int ticket_num;
		    String ticket_desc=null;
		    String ticket_status=null;
		    String ticket_assignee=null;
		    char tickstatus;
		    
		    //asks user for name for assignee purposes
		    System.out.println("Hello Techie, \n What is your name?");
	 		ticket_assignee=sc.nextLine();
	 		//asks user for ticket number to see which ticket will be updated
		    System.out.println("What is the ticket number you'd like to update?");
    		ticket_num=sc.nextInt();
    	
		    //gives option to update ticket status first    
		     System.out.println("Update trouble ticket status"
		   			+ "\nChoose a status by letter below"
		   			+ "\no) Open ticket"
		   			+ "\np) Pending ticket"
		   			+ "\nc) Closed ticket"
		   			+ "\nany other key for no status update");
		     
			tickstatus = sc.next().charAt(0);
			
			switch (tickstatus) {
			
            case 'o':  ticket_status = "Open";
                     break;
            case 'p':  ticket_status = "Pending";
                     break;
            case 'c':  ticket_status = "Closed";
                     break;
            default: ticket_status = "No Status Update";
                     break;
			}
			
       // This will load the MySQL driver, each DB has its own driver
		       Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection with the DB
			 connect = DriverManager.getConnection("jdbc:mysql://www.papademas.net/tickets?"
				              + "user=root&password=jamesp");    
			 System.out.println("Connected Succesfully...");
			
	if(tickstatus=='c')// if ticket status is changed to closed this if statement will be executed
				{
		 			statement = connect.createStatement();
		 			String sql = "UPDATE aaponTicket " +
		 					"SET ticketStatus='"+ticket_status+"', lastModifyDate=CURRENT_TIMESTAMP, lastModifiedBy='"+ticket_assignee+"'"+
		 					"WHERE ticketNum="+ticket_num;
		 			statement.executeUpdate(sql);
		 			sleep();
		 			System.out.println("Ticket "+ticket_num+" has been closed");
		 			statement.close();//closes statement
			    	connect.close();//closes connection
			    	return;
				}
	else if(tickstatus=='o'){// if ticket status is changed to open this else if statement will be executed
		 		statement = connect.createStatement();
		         String sql = "UPDATE aaponTicket " +
		                      "SET ticketStatus='"+ticket_status+"', lastModifyDate=CURRENT_TIMESTAMP, lastModifiedBy='"+ticket_assignee+"'"
		                      +"WHERE ticketNum="+ticket_num;
		         statement.executeUpdate(sql);
				 sleep();
		         System.out.println("Ticket opened...");
		         
		}
	else if(tickstatus=='p'){// if ticket status is changed to pending this else if statement will be executed
		 statement = connect.createStatement();
		String sql = "UPDATE aaponTicket " +
					"SET ticketStatus='"+ticket_status+"', lastModifyDate=CURRENT_TIMESTAMP, lastModifiedBy='"+ticket_assignee+"'"
					 +"WHERE ticketNum="+ticket_num;
					statement.executeUpdate(sql);
			sleep();
			System.out.println("Ticket status updated...");
			
	}
	else{
			System.out.println ("No Status Update");
			
		}
	int updateYesOrNo;
	String yesORno=" ";
	
	while(yesORno == null || yesORno.equals("")||integerValidation(yesORno)==false||positiveValidation(yesORno)==false)
	{
	System.out.println("Do you want to update the description? \n 1 for yes \n any other positive integer for no");
	yesORno=sc.nextLine();
	}
	updateYesOrNo =Integer.parseInt(yesORno);
	
	
	if(updateYesOrNo==1){//if user does want to update a ticket description this will execute
		System.out.println("What would you like to update the description to?");
		ticket_desc= sc.nextLine();
		statement = connect.createStatement();
		String sql = "UPDATE aaponTicket " +
				"SET ticketDesc='"+ticket_desc+"', lastModifyDate=CURRENT_TIMESTAMP, lastModifiedBy= '"+ticket_assignee+"'";
		statement.executeUpdate(sql);
		sleep();
		System.out.println("Ticket description updated...");
		statement.close();//closes statement
    	connect.close();//closes connection
	}
	else{//if user does not want to update a ticket description this will execute
		sleep();
		System.out.println("Thanks for your time.");
		statement.close();//closes statement
    	connect.close();//closes connection
	}
	
	return;
	
			  } catch (Exception e) {
			    System.out.println(e.getMessage());  
			  } 
		    finally{
		    	if (statement != null) statement.close();//closes statement
		    	if (connect != null) connect.close();	//closes connection
		    }
		}

	  public void deleteFromDataBase() throws Exception {
		    try {
		    
		    int ticket_num=0;
		
		    // This will load the MySQL driver, each DB has its own driver
		       Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection with the DB
			 connect = DriverManager.getConnection("jdbc:mysql://www.papademas.net/tickets?"
				             + "user=root&password=jamesp");
			 System.out.println("Connected Succesfully...");
			
			 //asks user what ticket they want to delete
			 System.out.println("What Ticket Number would you like to delete?");
			 ticket_num=sc.nextInt();
			int deleteOrNah;
			 //makes sure the user wants to proceed with deleting the ticket they mentioned
			 
				 System.out.println("Are you sure you want to delete ticket number :"+ticket_num+"? \n 1 for yes \n any other positive integer for no");
			 	 deleteOrNah=sc.nextInt();
			 
			 
			 if(deleteOrNah==1){//this statement will execute if the user does decide they want to delete the ticket
				    statement = connect.createStatement();
				    String sql = "DELETE FROM aaponTicket WHERE ticketNum="+ticket_num;
		 			statement.executeUpdate(sql);
		 			sleep();
		 			System.out.println("Ticket number : "+ticket_num+" has been deleted...");
		 			statement.close();
		 			connect.close();
				}
				//this statement will execute if the user decided they don't want to delete the ticket
					System.out.println("Thanks for your time.");
				
			 
			 return;
			 
			  } catch (Exception e) {
			    System.out.println(e.getMessage());  
			  } 
		    finally{
		    	if (statement != null) statement.close();//closes statement
		    	if (connect != null) connect.close();//closes connection
		    }
		}
	  

	  
  public void sleep()
  {
  	try {
  	    Thread.sleep(3000);
  	} catch(InterruptedException ex) {
  	    Thread.currentThread().interrupt();
  	}
  	
  }
  
  
  public static void menu()
  {
  	
  	System.out.println("Welcome to the Trouble Ticket Menu-Driven System"
  			+ "\nChose a menu item by number below"
  			+ "\n0) Create database"
  			+ "\n1) Create ticket"
  			+ "\n2) View a ticket"
  			+ "\n3) Update a ticket's status"
  			+ "\n4) Purge a ticket"
  			+ "\n5) Exit the program");
  			ans = sc.nextLine();
  }
  

  
	public static void main(String[] args) throws Exception {
	      
		Tickets dao = new Tickets();
	    
		menu();  //initial call to build menu
		try{
          do 
          {
                  	 switch(ans.charAt(0))
                  	 {
                  	 case '0':
                  		dao.createDataBase();
                  	 case '1': 
                  		 dao.insertIntoDataBase();
                  		 break;
                  	case '2': 
                 		 dao.viewFromDataBase();
                 		 break;
                  	case '3': 
                 		 dao.updateDataBase();
                 		 break;
                  	case '4': 
                 		 dao.deleteFromDataBase();
                 		 break;
                  	 case '5': 
                  		 System.exit(0);
                  		 break;
                  		 
                  	 default:
                  	 }
            
          	menu(); //repeat menu
          }while (!ans.equals("5"));
		}catch(Exception e) {System.out.println(e.getMessage());}
		
		
}
	public static boolean positiveValidation(String postInt)//checks if input is positive
	{
		int num=Integer.parseInt(postInt);
	if(num>0)
		return true;
	else
		return false;
	}


	public static boolean integerValidation( String input ) { //Checks if input is an integer
	    try {
	        Integer.parseInt( input );
	        return true;
	    }
	    catch( Exception e ) {
	        return false;
	    }
}
}
