import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.sql.PreparedStatement;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * Servlet implementation class Connect
 */
@WebServlet("/userDAO")
public class userDAO 
{
	private static final long serialVersionUID = 1L;
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	public userDAO(){}
	
	/** 
	 * @see HttpServlet#HttpServlet()
     */
    protected void connect_func() throws SQLException {
    	//uses default connection to the database
        if (connect == null || connect.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            connect = (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/testdb?allowPublicKeyRetrieval=true&useSSL=false&user=john&password=Muzahidisme1!");
            System.out.println(connect);
        }
    }
    
    public boolean database_login(String email, String password) throws SQLException{
    	try {
    		connect_func("root","pass1234");
    		String sql = "select * from user where email = ?";
    		preparedStatement = connect.prepareStatement(sql);
    		preparedStatement.setString(1, email);
    		ResultSet rs = preparedStatement.executeQuery();
    		return rs.next();
    	}
    	catch(SQLException e) {
    		System.out.println("failed login");
    		return false;
    	}
    }
	//connect to the database 
    public void connect_func(String username, String password) throws SQLException {
        if (connect == null || connect.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            connect = (Connection) DriverManager
  			      .getConnection("jdbc:mysql://127.0.0.1:3306/userdb?"
  			          + "useSSL=false&user=" + username + "&password=" + password);
            System.out.println(connect);
        }
    }
    
    public List<user> listAllUsers() throws SQLException {
        List<user> listUser = new ArrayList<user>();        
        String sql = "SELECT * FROM User";      
        connect_func();      
        statement = (Statement) connect.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
         
        while (resultSet.next()) {
            String email = resultSet.getString("email");
         
            String password = resultSet.getString("password");
            String role = resultSet.getString("role");

             
            user users = new user(email, password,role);
            listUser.add(users);
        }        
        resultSet.close();
        disconnect();        
        return listUser;
    }
    public List<quote> listAllQuotes() throws SQLException {
        List<quote> listQuotes = new ArrayList<quote>();        
        String sql = "SELECT * FROM User";       
        connect_func();      
        statement = (Statement) connect.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
         
        while (resultSet.next()) {
            if (resultSet.getString("quote_date")!=null)
        	{String email = resultSet.getString("email");
            
            String tree_price = resultSet.getString("tree_price");
            String tree_size = resultSet.getString("tree_size");
            String tree_height = resultSet.getString("tree_height");
            Date quote_date = resultSet.getDate("quote_date");
            String quote_response = resultSet.getString("quote_response");

            quote quotes = new quote(email, tree_price, tree_size, tree_height, quote_date, quote_response);
            listQuotes.add(quotes);
        }
        }        
        resultSet.close();
        disconnect();        
        return listQuotes;
    }

    
    protected void disconnect() throws SQLException {
        if (connect != null && !connect.isClosed()) {
        	connect.close();
        }
    }
    
    public void insert(user users) throws SQLException {
    	connect_func("root","pass1234");         
		String sql = "insert into User(email,password, role) values (?, ?, ?)";
		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
		preparedStatement.setString(1, users.getEmail());
		preparedStatement.setString(2, users.getPassword());
		preparedStatement.setString(3, users.getRole());
		preparedStatement.executeUpdate();
        preparedStatement.close();
    }
    
    public void updateQuote(String email, String treePrice, String treeSize, String treeHeight, Date quoteDate, String quoteResponse) throws SQLException {
        connect_func();

        String sql = "UPDATE User " +
                     "SET tree_price = ?, tree_size = ?, tree_height = ?, quote_date = ?, quote_response = ? " +
                     "WHERE email = ?";

        preparedStatement = connect.prepareStatement(sql);
        preparedStatement.setString(1, treePrice);
        preparedStatement.setString(2, treeSize);
        preparedStatement.setString(3, treeHeight);
        preparedStatement.setDate(4, new java.sql.Date(quoteDate.getTime()));
        preparedStatement.setString(5, quoteResponse);
        preparedStatement.setString(6, email);
        
        preparedStatement.executeUpdate();
        preparedStatement.close();
        
        System.out.println("UPDATING NEW QUOTE");
    }
    
    public boolean delete(String email) throws SQLException {
        String sql = "DELETE FROM User WHERE email = ?";        
        connect_func();
         
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setString(1, email);
         
        boolean rowDeleted = preparedStatement.executeUpdate() > 0;
        preparedStatement.close();
        return rowDeleted;     
    }
     
    public boolean update(user users) throws SQLException {
        String sql = "update User set password = ? where email = ?";
        connect_func();
        
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setString(1, users.getEmail());
		preparedStatement.setString(2, users.getPassword());
		preparedStatement.setString(3, users.getRole());
        boolean rowUpdated = preparedStatement.executeUpdate() > 0;
        preparedStatement.close();
        return rowUpdated;     
    }
    
    public user getUser(String email) throws SQLException {
    	user user = null;
        String sql = "SELECT * FROM User WHERE email = ?";
         
        connect_func();
         
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setString(1, email);
         
        ResultSet resultSet = preparedStatement.executeQuery();
         
        if (resultSet.next()) {
      
            String password = resultSet.getString("password");

            user = new user(email, password);
        }
         
        resultSet.close();
        statement.close();
         
        return user;
    }
    
    public boolean checkEmail(String email) throws SQLException {
    	boolean checks = false;
    	String sql = "SELECT * FROM User WHERE email = ?";
    	connect_func();
    	preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setString(1, email);
        ResultSet resultSet = preparedStatement.executeQuery();
        
        System.out.println(checks);	
        
        if (resultSet.next()) {
        	checks = true;
        }
        
        System.out.println(checks);
    	return checks;
    }
    
    public boolean checkPassword(String password) throws SQLException {
    	boolean checks = false;
    	String sql = "SELECT * FROM User WHERE password = ?";
    	connect_func();
    	preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setString(1, password);
        ResultSet resultSet = preparedStatement.executeQuery();
        
        System.out.println(checks);	
        
        if (resultSet.next()) {
        	checks = true;
        }
        
        System.out.println(checks);
       	return checks;
    }
    
    public String getUserRole(String email) throws SQLException {
        String role = null;
        String sql = "SELECT role FROM User WHERE email = ?"; // Query to retrieve the role based on email
        connect_func();

        try (PreparedStatement preparedStatement = connect.prepareStatement(sql)) {
            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                role = resultSet.getString("role");
            }
        } catch (SQLException e) {
            // Handle any database-related exceptions here
            e.printStackTrace();
        } finally {
            disconnect();
        }

        return role;
    }
    
    public boolean isValid(String email, String password) throws SQLException
    {
    	String sql = "SELECT * FROM User";
    	connect_func();
    	statement = (Statement) connect.createStatement();
    	ResultSet resultSet = statement.executeQuery(sql);
    	
    	resultSet.last();
    	
    	int setSize = resultSet.getRow();
    	resultSet.beforeFirst();
    	
    	for(int i = 0; i < setSize; i++)
    	{
    		resultSet.next();
    		if(resultSet.getString("email").equals(email) && resultSet.getString("password").equals(password)) {
    			return true;
    		}		
    	}
    	return false;
    }
    
    
    public List<quote> prospective() throws ServletException, IOException, SQLException {
        List<quote> prospectiveClients = new ArrayList<>();
        System.out.println("Looking for Biggest Client");

        String sql = "SELECT * FROM User";
        connect_func();
        statement = (Statement) connect.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            String email = resultSet.getString("email");
            String tree_price = resultSet.getString("tree_price");
            String tree_size = resultSet.getString("tree_size");
            String tree_height = resultSet.getString("tree_height");
            Date quote_date = resultSet.getDate("quote_date");
            String quote_response = resultSet.getString("quote_response");

            // Check if quote_response is "no", "No", or "NO", or if it's null
            if (quote_response != null && quote_response.equalsIgnoreCase("no")) {
                // Create a new quote object and add it to the list
                quote userQuote = new quote(email, tree_price, tree_size, tree_height, quote_date, quote_response);
                prospectiveClients.add(userQuote);
            }
        }

        resultSet.close();
        disconnect();
        return prospectiveClients;
    }
    
    public List<quote> easyClients() throws ServletException, IOException, SQLException {
        List<quote> easyClientstUsers = new ArrayList<>();
        System.out.println("Looking for Biggest Client");

        String sql = "SELECT * FROM User";
        connect_func();
        statement = (Statement) connect.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            String email = resultSet.getString("email");
            String tree_price = resultSet.getString("tree_price");
            String tree_size = resultSet.getString("tree_size");
            String tree_height = resultSet.getString("tree_height");
            Date quote_date = resultSet.getDate("quote_date");
            String quote_response = resultSet.getString("quote_response");

            // Check if quote_response is "no", "No", or "NO", or if it's null
            if (quote_response != null && (quote_response.equalsIgnoreCase("accept")|| quote_response.equalsIgnoreCase("accepted"))) {
                // Create a new quote object and add it to the list
                quote userQuote = new quote(email, tree_price, tree_size, tree_height, quote_date, quote_response);
                easyClientstUsers.add(userQuote);
            }
        }

        resultSet.close();
        disconnect();
        return easyClientstUsers;
    }

    
    public List<quote> bigClients() throws ServletException, IOException, SQLException {
        List<quote> bigClientUsers = new ArrayList<>();
        System.out.println("Looking for Biggest Client");

        String sql = "SELECT * FROM User";
        connect_func();
        statement = (Statement) connect.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        double maxTreeHeight = Double.MIN_VALUE;

        while (resultSet.next()) {
            String email = resultSet.getString("email");
            String tree_price = resultSet.getString("tree_price");
            String tree_size = resultSet.getString("tree_size");
            String tree_height = resultSet.getString("tree_height");
            Date quote_date = resultSet.getDate("quote_date");
            String quote_response = resultSet.getString("quote_response");

            if (tree_height != null) {
                // Remove spaces from tree_height
                tree_height = tree_height.replaceAll("\\s", "");

                try {
                    // Convert tree_height to double for comparison
                    double currentTreeHeight = Double.parseDouble(tree_height);

                    if (currentTreeHeight > maxTreeHeight) {
                        // Reset the list if a taller tree is found
                        maxTreeHeight = currentTreeHeight;
                        bigClientUsers = new ArrayList<>();
                    }

                    if (currentTreeHeight == maxTreeHeight) {
                        // Add the current user to the list if their tree height is equal to the max
                        bigClientUsers.add(new quote(email, tree_price, tree_size, tree_height, quote_date, quote_response));
                    }
                } catch (NumberFormatException e) {
                    // Handle the case where tree_height cannot be parsed as a double
                    System.err.println("Error parsing tree_height as double: " + tree_height);
                }
            }
        }

        resultSet.close();
        disconnect();
        return bigClientUsers;
    }
    
    public List<quote> oneTree() throws ServletException, IOException, SQLException {
        List<quote> oneTreeClients = new ArrayList<>();
        System.out.println("Looking for One Tree Client");

        String sql = "SELECT * FROM User";
        connect_func();
        statement = (Statement) connect.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            String email = resultSet.getString("email");
            String tree_price = resultSet.getString("tree_price");
            String tree_size = resultSet.getString("tree_size");
            String tree_height = resultSet.getString("tree_height");
            Date quote_date = resultSet.getDate("quote_date");
            String quote_response = resultSet.getString("quote_response");

            if (tree_height != null && tree_height.equals("1")) {
                // Add the client to the list if their tree_height is 1
                oneTreeClients.add(new quote(email, tree_price, tree_size, tree_height, quote_date, quote_response));
            }
        }

        resultSet.close();
        disconnect();
        return oneTreeClients;
    }


    public List<quote> highestTreeCut() throws ServletException, IOException, SQLException {
        List<quote> highestTreeCutUser = new ArrayList<>();
        System.out.println("Looking for Highest Client");

        String sql = "SELECT * FROM User";
        connect_func();
        statement = (Statement) connect.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        double maxTreeSize = Double.MIN_VALUE;

        while (resultSet.next()) {
            String email = resultSet.getString("email");
            String tree_price = resultSet.getString("tree_price");
            String tree_size = resultSet.getString("tree_size");
            String tree_height = resultSet.getString("tree_height");
            Date quote_date = resultSet.getDate("quote_date");
            String quote_response = resultSet.getString("quote_response");

            if (tree_size != null) {
                // Remove spaces from tree_height
            	tree_size = tree_size.replaceAll("\\s", "");

                try {
                    // Convert tree_height to double for comparison
                    double currentTreeHeight = Double.parseDouble(tree_size);

                    if (currentTreeHeight > maxTreeSize) {
                        // Reset the list if a taller tree is found
                    	maxTreeSize = currentTreeHeight;
                        highestTreeCutUser = new ArrayList<>();
                    }

                    if (currentTreeHeight == maxTreeSize) {
                        // Add the current user to the list if their tree height is equal to the max
                    	highestTreeCutUser.add(new quote(email, tree_price, tree_size, tree_height, quote_date, quote_response));
                    }
                } catch (NumberFormatException e) {
                    // Handle the case where tree_height cannot be parsed as a double
                    System.err.println("Error parsing tree_size as double: " + tree_size);
                }
            }
        }

        resultSet.close();
        disconnect();
        return highestTreeCutUser;
    }
    
    
    public List<quote> goodClients() throws ServletException, IOException, SQLException {
        List<quote> goodClientsList = new ArrayList<>();
        System.out.println("Looking for Clients with Future Quote Dates");

        String sql = "SELECT * FROM User";
        connect_func();
        statement = (Statement) connect.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        // Get the current date and time
        LocalDateTime currentDateTime = LocalDateTime.now();

        while (resultSet.next()) {
            String email = resultSet.getString("email");
            String tree_price = resultSet.getString("tree_price");
            String tree_size = resultSet.getString("tree_size");
            String tree_height = resultSet.getString("tree_height");
            Date quote_date = resultSet.getDate("quote_date");
            String quote_response = resultSet.getString("quote_response");

            if (quote_date != null) {
                // Convert quote_date to LocalDateTime
                LocalDateTime localQuoteDateTime = quote_date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

                // Check if the quote_date is after 48 hours from the current date and time
                boolean isAfter = localQuoteDateTime.isAfter(currentDateTime.plusHours(48));

                if (isAfter) {
                    // Add the client to the list if quote_date is after 48 hours from the current date
                    goodClientsList.add(new quote(email, tree_price, tree_size, tree_height, quote_date, quote_response));
                }
            }
        }

        resultSet.close();
        disconnect();
        return goodClientsList;
    }

    
    public List<quote> overdueClients() throws ServletException, IOException, SQLException {
        List<quote> overdueClients = new ArrayList<>();
        System.out.println("Looking for Clients with Overdue fee");

        String sql = "SELECT * FROM User";
        connect_func();
        statement = (Statement) connect.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Convert LocalDate to LocalDateTime by combining it with midnight time
        LocalDateTime currentDateTime = LocalDateTime.of(currentDate, LocalTime.MIDNIGHT);

        // Subtract 49 hours from the current date and time
        LocalDateTime resultDateTime = currentDateTime.minusHours(178);

        // Now, if needed, you can extract the resulting LocalDate
        LocalDate resultDate = resultDateTime.toLocalDate();

        while (resultSet.next()) {
            String email = resultSet.getString("email");
            String tree_price = resultSet.getString("tree_price");
            String tree_size = resultSet.getString("tree_size");
            String tree_height = resultSet.getString("tree_height");
            Date quote_date = resultSet.getDate("quote_date");
            String quote_response = resultSet.getString("quote_response");
            
            if (quote_date != null) {
       
                  // Define the desired date format
                  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                  // Parse the given date string "2021-06-14" into a LocalDate
                  LocalDate specificDate = LocalDate.parse(quote_date.toString(), formatter);
                  // Check if the specific date is before the current date
                  boolean isBefore = specificDate.isBefore(resultDate);
                 if (isBefore) {
                     // Add the client to the list if quote_date is past the current date
                	 overdueClients.add(new quote(email, tree_price, tree_size, tree_height, quote_date, quote_response));
                 }
            }
        }

        resultSet.close();
        disconnect();
        return overdueClients;
    }
    
    
    public List<quote> badClients() throws ServletException, IOException, SQLException {
        List<quote> badClientsList = new ArrayList<>();
        System.out.println("Looking for Clients with Past Quote Dates");

        String sql = "SELECT * FROM User";
        connect_func();
        statement = (Statement) connect.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Convert LocalDate to LocalDateTime by combining it with midnight time
        LocalDateTime currentDateTime = LocalDateTime.of(currentDate, LocalTime.MIDNIGHT);

        // Subtract 49 hours from the current date and time
        LocalDateTime resultDateTime = currentDateTime.minusHours(49);

        // Now, if needed, you can extract the resulting LocalDate
        LocalDate resultDate = resultDateTime.toLocalDate();

        while (resultSet.next()) {
            String email = resultSet.getString("email");
            String tree_price = resultSet.getString("tree_price");
            String tree_size = resultSet.getString("tree_size");
            String tree_height = resultSet.getString("tree_height");
            Date quote_date = resultSet.getDate("quote_date");
            String quote_response = resultSet.getString("quote_response");
            
            if (quote_date != null) {
       
                  // Define the desired date format
                  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                  // Parse the given date string "2021-06-14" into a LocalDate
                  LocalDate specificDate = LocalDate.parse(quote_date.toString(), formatter);
                  // Check if the specific date is before the current date
                  boolean isBefore = specificDate.isBefore(resultDate);
                 if (isBefore) {
                     // Add the client to the list if quote_date is past the current date
                     badClientsList.add(new quote(email, tree_price, tree_size, tree_height, quote_date, quote_response));
                 }
            }
        }

        resultSet.close();
        disconnect();
        return badClientsList;
    }

    public int[] totalStatistics() throws ServletException, IOException, SQLException {
        int[] results = new int[3];
        System.out.println("Calculating Statistics");

        String sql = "SELECT * FROM User";
        connect_func();
        statement = (Statement) connect.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            // Retrieve values from the current record
            String tree_price = resultSet.getString("tree_price");
            String tree_size = resultSet.getString("tree_size");
            String tree_height = resultSet.getString("tree_height");

            // Attempt to parse non-null values to integers
            if (tree_price != null && tree_size != null && tree_height != null) {
                try {
                    int priceValue = Integer.parseInt(tree_price);
                    int sizeValue = Integer.parseInt(tree_size);
                    int heightValue = Integer.parseInt(tree_height);

                    // Accumulate values in the results array
                    results[0] += priceValue;
                    results[1] += sizeValue;
                    results[2] += heightValue;
                } catch (NumberFormatException e) {
                    // Handle the case where parsing fails (e.g., value is not an integer)
                    // You can log a message or ignore the quote and move on to the next
                    System.err.println("Skipping quote due to parsing error: " + e.getMessage());
                }
            }
        }

        System.out.println(results[0]+" "+results[1]+" "+results[2]);
        resultSet.close();
        disconnect();
        return results;
    }
    public double[] averageStatistics() throws ServletException, IOException, SQLException {
        int[] results = new int[3];
        int[] counts = new int[3];
        
        System.out.println("Calculating Statistics");

        String sql = "SELECT * FROM User";
        connect_func();
        statement = (Statement) connect.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            // Retrieve values from the current record
            String tree_price = resultSet.getString("tree_price");
            String tree_size = resultSet.getString("tree_size");
            String tree_height = resultSet.getString("tree_height");

            // Attempt to parse non-null values to integers
            if (tree_price != null && tree_size != null && tree_height != null) {
                try {
                    int priceValue = Integer.parseInt(tree_price);
                    int sizeValue = Integer.parseInt(tree_size);
                    int heightValue = Integer.parseInt(tree_height);

                    // Accumulate values and counts in the respective arrays
                    results[0] += priceValue;
                    results[1] += sizeValue;
                    results[2] += heightValue;

                    counts[0]++;
                    counts[1]++;
                    counts[2]++;
                } catch (NumberFormatException e) {
                    // Handle the case where parsing fails (e.g., value is not an integer)
                    // You can log a message or ignore the quote and move on to the next
                    System.err.println("Skipping quote due to parsing error: " + e.getMessage());
                }
            }
        }

        System.out.println(results[0] + " " + results[1] + " " + results[2]);

        // Calculate averages
        double[] averages = new double[3];
        for (int i = 0; i < averages.length; i++) {
            if (counts[i] > 0) {
                averages[i] = (double) results[i] / counts[i];
            }
        }

        System.out.println(averages[0] + " " + averages[1] + " " + averages[2]);

        resultSet.close();
        disconnect();
        return averages;
    }




    public void init() throws SQLException, FileNotFoundException, IOException{
    	connect_func();
        statement =  (Statement) connect.createStatement();
        
        String[] INITIAL = {
        	    
        		"use testdb;",
        		"drop table if exists User;",
        	    ("CREATE TABLE if not exists User( " +
        	        "email VARCHAR(50) NOT NULL, " +
        	        "password VARCHAR(20) NOT NULL, " +
        	        "role VARCHAR(20) NOT NULL, " +
        	        "tree_price VARCHAR(20), " + // New column
        	        "tree_size VARCHAR(20), " +  // New column
        	        "tree_height VARCHAR(20), " + // New column
        	        "quote_date DATE, " + // New column
        	        "quote_response VARCHAR(20), " + // New column
        	        "PRIMARY KEY (email) " + 
        	    "); ")
        	};
        
        
        String[] TUPLES = {
        	    "insert into User(email, password, role, tree_price, tree_size, tree_height, quote_date, quote_response) values ('susie@gmail.com', 'susie1234', 'client', '10', '3', '4', '2021-06-14', 'pending');",
        	    "insert into User(email, password, role, tree_price, tree_size, tree_height, quote_date, quote_response) values ('don@gmail.com', 'don123', 'client', null, null, null, null, null);",
        	    "insert into User(email, password, role, tree_price, tree_size, tree_height, quote_date, quote_response) values ('margarita@gmail.com', 'margarita1234', 'client', null, null, null, null, null);",
        	    "insert into User(email, password, role, tree_price, tree_size, tree_height, quote_date, quote_response) values ('jo@gmail.com', 'jo1234', 'client', null, null, null, null, null);",
        	    "insert into User(email, password, role, tree_price, tree_size, tree_height, quote_date, quote_response) values ('wallace@gmail.com', 'wallace1234', 'client', null, null, null, null, null);",
        	    "insert into User(email, password, role, tree_price, tree_size, tree_height, quote_date, quote_response) values ('amelia@gmail.com', 'amelia1234', 'client', null, null, null, null, null);",
        	    "insert into User(email, password, role, tree_price, tree_size, tree_height, quote_date, quote_response) values ('sophie@gmail.com', 'sophie1234', 'client', null, null, null, null, null);",
        	    "insert into User(email, password, role, tree_price, tree_size, tree_height, quote_date, quote_response) values ('angelo@gmail.com', 'angelo1234', 'client', null, null, null, null, null);",
        	    "insert into User(email, password, role, tree_price, tree_size, tree_height, quote_date, quote_response) values ('ruddy@gmail.com', 'rudy1234', 'client', null, null, null, null, null);",
        	    "insert into User(email, password, role, tree_price, tree_size, tree_height, quote_date, quote_response) values ('jeannette@gmail.com', 'jeannette1234', 'client', null, null, null, null, null);",
        	    "insert into User(email, password, role, tree_price, tree_size, tree_height, quote_date, quote_response) values ('root', 'pass1234', 'admin', null, null, null, null, null);"
        	};



        //for loop to put these in database
        for (int i = 0; i < INITIAL.length; i++)
        	statement.execute(INITIAL[i]);
        for (int i = 0; i < TUPLES.length; i++)	
        	statement.execute(TUPLES[i]);
    
        disconnect();
    }
    
    
   
    public void printTable() {
    	 try {
             Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/testdb?allowPublicKeyRetrieval=true&useSSL=false&user=john&password=Muzahidisme1!");
             Statement statement = connection.createStatement();
             
             // Execute a SELECT query to retrieve data from the User table
             String selectQuery = "SELECT * FROM User";
             ResultSet resultSet = statement.executeQuery(selectQuery);
             
             // Print the data
             while (resultSet.next()) {
                 String email = resultSet.getString("email");
                 String password = resultSet.getString("password");
                 String role = resultSet.getString("role");
                 String treePrice = resultSet.getString("tree_price");
                 String treeSize = resultSet.getString("tree_size");
                 String treeHeight = resultSet.getString("tree_height");
                 Date quoteDate = resultSet.getDate("quote_date");
                 String quoteResponse = resultSet.getString("quote_response");
                 
                 System.out.println("Email: " + email);
                 System.out.println("Password: " + password);
                 System.out.println("Role: " + role);
                 System.out.println("Tree Price: " + treePrice);
                 System.out.println("Tree Size: " + treeSize);
                 System.out.println("Tree Height: " + treeHeight);
                 System.out.println("Quote Date: " + quoteDate);
                 System.out.println("Quote Response: " + quoteResponse);
                 System.out.println();
             }
             
             // Close resources
             resultSet.close();
             statement.close();
             connection.close();
         } catch (SQLException e) {
             e.printStackTrace();
         }
    }
    
    
    public void printQuotes(List<quote> quotes) {
        for (quote q : quotes) {
            System.out.println("Email: " + q.getEmail());
            System.out.println("Tree Price: " + q.getTreePrice());
            System.out.println("Tree Size: " + q.getTreeSize());
            System.out.println("Tree Height: " + q.getTreeHeight());
            System.out.println("Quote Date: " + q.getQuoteDate());
            System.out.println("Quote Response: " + q.getQuoteResponse());
            System.out.println(); // Add a separator between quotes
        }
    }
    
	
	public quote quoteInfo(String user) {
		
		
		quote quoteInfo = null;

	    try {
	        connect_func(); // Establish your database connection

	        String sql = "SELECT email, tree_price, tree_size, tree_height, quote_date, quote_response FROM User WHERE email = ? AND quote_date IS NOT NULL";
	        PreparedStatement preparedStatement = connect.prepareStatement(sql);
	        preparedStatement.setString(1, user);
	        ResultSet resultSet = preparedStatement.executeQuery();

	        if (resultSet.next()) {
	            String email = resultSet.getString("email");
	            String treePrice = resultSet.getString("tree_price");
	            String treeSize = resultSet.getString("tree_size");
	            String treeHeight = resultSet.getString("tree_height");
	            Date quoteDate = resultSet.getDate("quote_date");
	            String quoteResponse = resultSet.getString("quote_response");

	            quoteInfo = new quote(email, treePrice, treeSize, treeHeight, quoteDate, quoteResponse);
	        }

	        // Close resources
	        resultSet.close();
	        preparedStatement.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        // Close the database connection here
	    }

	    return quoteInfo;
		
	}

	
	public void updateQuoteFromFrontend(String user, String newInfo, int position)  throws SQLException {
		 connect_func();

//         String sql = "UPDATE User " +
//                      "SET tree_price = ?, tree_size = ?, tree_height = ?, quote_date = ?, quote_response = ? " +
//                      "WHERE email = ?";

        // preparedStatement = connect.prepareStatement(sql);
     
        
         
         switch (position) {
         case 1:
        	 String sql = "UPDATE User " +
                      "SET tree_price = ?" + "WHERE email = ?";
        	 preparedStatement = connect.prepareStatement(sql);
        	 preparedStatement.setString(1, newInfo);
        	 preparedStatement.setString(2, user);
        	 preparedStatement.executeUpdate();
             preparedStatement.close();
         case 2:
        	 String sql2 = "UPDATE User " +
                      "SET tree_size = ?" + "WHERE email = ?";
        	 preparedStatement = connect.prepareStatement(sql2);
        	 preparedStatement.setString(1, newInfo);
        	 preparedStatement.setString(2, user);
        	 preparedStatement.executeUpdate();
             preparedStatement.close();
         case 3:
        	 String sql3 = "UPDATE User " +
                      "SET tree_height = ?" + "WHERE email = ?";
        	 preparedStatement = connect.prepareStatement(sql3);
        	 preparedStatement.setString(1, newInfo);
        	 preparedStatement.setString(2, user);
        	 preparedStatement.executeUpdate();
             preparedStatement.close();
         case 4:
        	 String sql4 = "UPDATE User " +
                      "SET quote_date = ?" + "WHERE email = ?";
        	 preparedStatement = connect.prepareStatement(sql4);
        	 preparedStatement.setString(1, "5203-45-20");
        	 preparedStatement.setString(2, user);
        	 preparedStatement.executeUpdate();
             preparedStatement.close();
         case 5:
        	 String sql5 = "UPDATE User " +
                      "SET quote_response = ?" + "WHERE email = ?";
        	 preparedStatement = connect.prepareStatement(sql5);
        	 preparedStatement.setString(1, newInfo);
        	 preparedStatement.setString(2, user);
        	 preparedStatement.executeUpdate();
             preparedStatement.close();
         
         }
         System.out.println("UPDATING NEW QUOTE");
	}
}
