import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
 
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class ControlServlet extends HttpServlet {
	    private static final long serialVersionUID = 1L;
	    private userDAO userDAO = new userDAO();
	    private String currentUser;
	    private HttpSession session=null;
	    
	    class Quote{
	    
	    };
	    
	    public ControlServlet()
	    {
	    	
	    }
	    
	    public void init()
	    {
	    	userDAO = new userDAO();
	    	currentUser= "";
	    }
	    
	    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        doGet(request, response);
	    }
	    
	    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        String action = request.getServletPath();
	        System.out.println(action);
	    
	    try {
        	switch(action) {  
        	case "/login":
        		// Print all the users
        		List<user> users = (userDAO.listAllUsers());
		        String formattedUsers = usersToString(users);
		        
        		System.out.println("case 1111111111111111111");
        		login(request,response);
        		break;
        	case "/register":
        		System.out.println("2222222222222222222");
        		register(request, response);
        		break;
        	case "/newQuote":
        		System.out.println("CREATING NEW QUOTE");
        		newQuote(request, response);
        		break;
        	case "/initialize":
        		System.out.println("case 333333333333333333333");
        		userDAO.init();
        		System.out.println("Database successfully initialized!");
        		rootPage(request,response,"");
        		break;
        	case "/root":
        		System.out.println("case 44444444");
        		rootPage(request,response, "");
        		break;
        	case "/logout":
        		System.out.println("case 6666666666666666");
        		logout(request,response);
        		break;
        	case "/listQuotes":
        		List<quote> quotes = (userDAO.listAllQuotes());
        		printQuotes(quotes);
        		System.out.println();
        		System.out.println("case 7777777777777777");
        	    adminPage(request, response, "");
        	    break;
        	 case "/list": 
        		 System.out.println("case 8888888888888888888888");
                 System.out.println("The action is: list");
                 listUser(request, response);           	
                 break;
        	 case "/updateQuote":
        		 System.out.println("case 101010101010");
                 System.out.println("The action is: update");
                 updateQuote(request, response);           	
                 break;
        	 case "/bigClient":
        		 System.out.println("case BBBB");
                 System.out.println("The action is: bigClient");
                 bigClient(request, response,"");           	
                 break;
        	 case "/highestTreeCut":
        		 System.out.println("case BBBB");
                 System.out.println("The action is: Highest Tree");
                 highestTree(request, response,"");           	
                 break;
        	 case "/oneTree":
        		 System.out.println("case BBBB");
                 System.out.println("The action is: One Tree Clients");
                 oneTreeClients(request, response,"");           	
                 break;
        	 case "/badClients":
        		 System.out.println("case BBBB");
                 System.out.println("The action is: Bad Clients");
                 badClients(request, response,"");           	
                 break;
                 
        	 case "/goodClients":
        		 System.out.println("case BBBB");
                 System.out.println("The action is: good Clients");
                 goodClients(request, response,"");           	
                 break;
        	 case "/stats":
        		 System.out.println("case BBBB");
                 System.out.println("The action is:  stats ");
                 statsPage(request, response,"");           	
                 break;
        	 case "/prosp":
        		 System.out.println("case BBBB");
                 System.out.println("The action is:  prosp ");
                 prosppage(request, response,"");           	
                 break;
        	 case "/easy":
        		 System.out.println("case BBBB");
                 System.out.println("The action is:  easy ");
                 easyClient(request, response,"");           	
                 break;
        	 case "/overdue":
        		 System.out.println("case BBBB");
                 System.out.println("The action is: overdue ");
                 overdue(request, response,"");           	
                 break;
             default: 
            	 System.out.println("case 99999999999999999999	");
            	 break;
	    	}
	    }
	    catch(Exception ex) {
        	System.out.println(ex.getMessage());
	    	}
	    }
        	
	    private void listUser(HttpServletRequest request, HttpServletResponse response)
	            throws SQLException, IOException, ServletException {
	        System.out.println("listUser started: 00000000000000000000000000000000000");
	        List<user> listUser = userDAO.listAllUsers();
	        request.setAttribute("listUser", listUser);       
	        RequestDispatcher dispatcher = request.getRequestDispatcher("UserList.jsp");       
	        dispatcher.forward(request, response);
	        System.out.println("listPeople finished: 111111111111111111111111111111111111");
	    }
	    	        
	    private void rootPage(HttpServletRequest request, HttpServletResponse response, String view) throws ServletException, IOException, SQLException{
	    	System.out.println("root view");
			request	.setAttribute("listUser", userDAO.listAllUsers());
	    	request.getRequestDispatcher("rootView.jsp").forward(request, response);
	    	
	    }
	    
	    
	    private void bigClient(HttpServletRequest request, HttpServletResponse response, String view) throws ServletException, IOException, SQLException {
	        List<quote> bigClientQuotes = userDAO.bigClients();

	        // Check if there are any quotes
	        if (bigClientQuotes != null && !bigClientQuotes.isEmpty()) {
	      
	            for (quote q : bigClientQuotes) {
	                System.out.println("Email: " + q.getEmail());
	            }

	            // Assuming you have a method to retrieve quote information based on the email
	            // You may need to adjust this based on your actual implementation
	            List<quote> quoteInfoList = new ArrayList<>();
	            for (quote q : bigClientQuotes) {
	            	quote quoteInfo = userDAO.quoteInfo(q.getEmail());
	                if (quoteInfo != null) {
	                    quoteInfoList.add(quoteInfo);
	                }
	            }

	            // Send the list of quote information to the front end
	            request.setAttribute("quoteInfoList", quoteInfoList);
	        } else {
	            System.out.println("No big clients found.");
	        }

	        request.getRequestDispatcher("bigclient.jsp").forward(request, response);
	    }
	    private void prosppage(HttpServletRequest request, HttpServletResponse response, String view) throws ServletException, IOException, SQLException {
	        List<quote> prospClient = userDAO.prospective();

	        // Check if there are any quotes
	        if (prospClient != null && !prospClient.isEmpty()) {
	      
	            for (quote q : prospClient) {
	                System.out.println("Email: " + q.getEmail());
	            }

	            // Assuming you have a method to retrieve quote information based on the email
	            // You may need to adjust this based on your actual implementation
	            List<quote> quoteInfoList = new ArrayList<>();
	            for (quote q : prospClient) {
	            	quote quoteInfo = userDAO.quoteInfo(q.getEmail());
	                if (quoteInfo != null) {
	                    quoteInfoList.add(quoteInfo);
	                }
	            }

	            // Send the list of quote information to the front end
	            request.setAttribute("quoteInfoList", quoteInfoList);
	        } else {
	            System.out.println("No big clients found.");
	        }

	        request.getRequestDispatcher("prospective.jsp").forward(request, response);
	    }
	    
	    private void easyClient(HttpServletRequest request, HttpServletResponse response, String view) throws ServletException, IOException, SQLException {
	        List<quote> easyClient = userDAO.easyClients();

	        // Check if there are any quotes
	        if (easyClient != null && !easyClient.isEmpty()) {
	      
	            for (quote q : easyClient) {
	                System.out.println("Email: " + q.getEmail());
	            }

	            // Assuming you have a method to retrieve quote information based on the email
	            // You may need to adjust this based on your actual implementation
	            List<quote> quoteInfoList = new ArrayList<>();
	            for (quote q : easyClient) {
	            	quote quoteInfo = userDAO.quoteInfo(q.getEmail());
	                if (quoteInfo != null) {
	                    quoteInfoList.add(quoteInfo);
	                }
	            }

	            // Send the list of quote information to the front end
	            request.setAttribute("quoteInfoList", quoteInfoList);
	        } else {
	            System.out.println("No big clients found.");
	        }

	        request.getRequestDispatcher("easyclient.jsp").forward(request, response);
	    }
	    
	    private void overdue(HttpServletRequest request, HttpServletResponse response, String view) throws ServletException, IOException, SQLException {
	        List<quote> overdueList = userDAO.overdueClients();

	        // Check if there are any quotes
	        if (overdueList != null && !overdueList.isEmpty()) {
	      
	            for (quote q : overdueList) {
	                System.out.println("Email: " + q.getEmail());
	            }

	            // Assuming you have a method to retrieve quote information based on the email
	            // You may need to adjust this based on your actual implementation
	            List<quote> quoteInfoList = new ArrayList<>();
	            for (quote q : overdueList) {
	            	quote quoteInfo = userDAO.quoteInfo(q.getEmail());
	                if (quoteInfo != null) {
	                    quoteInfoList.add(quoteInfo);
	                }
	            }

	            // Send the list of quote information to the front end
	            request.setAttribute("quoteInfoList", quoteInfoList);
	        } else {
	            System.out.println("No overdueList clients found.");
	        }

	        request.getRequestDispatcher("overdue.jsp").forward(request, response);
	    }
	    
	    private void goodClients(HttpServletRequest request, HttpServletResponse response, String view) throws ServletException, IOException, SQLException {
	        List<quote> goodClientsQuotes = userDAO.goodClients();

	        // Check if there are any quotes
	        if (goodClientsQuotes != null && !goodClientsQuotes.isEmpty()) {
	      
	            for (quote q : goodClientsQuotes) {
	                System.out.println("Email: " + q.getEmail());
	            }

	            // Assuming you have a method to retrieve quote information based on the email
	            // You may need to adjust this based on your actual implementation
	            List<quote> quoteInfoList = new ArrayList<>();
	            for (quote q : goodClientsQuotes) {
	            	quote quoteInfo = userDAO.quoteInfo(q.getEmail());
	                if (quoteInfo != null) {
	                    quoteInfoList.add(quoteInfo);
	                }
	            }

	            // Send the list of quote information to the front end
	            request.setAttribute("quoteInfoList", quoteInfoList);
	        } else {
	            System.out.println("No big clients found.");
	        }

	        request.getRequestDispatcher("goodclients.jsp").forward(request, response);
	    }
	    
	    private void statsPage(HttpServletRequest request, HttpServletResponse response, String view) throws ServletException, IOException, SQLException {
	       

	        request.setAttribute("quoteInfoList", userDAO.listAllQuotes());
	        request.setAttribute("total", userDAO.totalStatistics());
	        request.setAttribute("average", userDAO.averageStatistics());
	        request.getRequestDispatcher("stats.jsp").forward(request, response);
	    }
	    
	    
	    private void badClients(HttpServletRequest request, HttpServletResponse response, String view) throws ServletException, IOException, SQLException {
	        List<quote> badClientsQuotes = userDAO.badClients();

	        // Check if there are any quotes
	        if (badClientsQuotes != null && !badClientsQuotes.isEmpty()) {
	      
	            for (quote q : badClientsQuotes) {
	                System.out.println("Email: " + q.getEmail());
	            }

	            // Assuming you have a method to retrieve quote information based on the email
	            // You may need to adjust this based on your actual implementation
	            List<quote> quoteInfoList = new ArrayList<>();
	            for (quote q : badClientsQuotes) {
	            	quote quoteInfo = userDAO.quoteInfo(q.getEmail());
	                if (quoteInfo != null) {
	                    quoteInfoList.add(quoteInfo);
	                }
	            }

	            // Send the list of quote information to the front end
	            request.setAttribute("quoteInfoList", quoteInfoList);
	        } else {
	            System.out.println("No bad clients found.");
	        }

	        request.getRequestDispatcher("badclients.jsp").forward(request, response);
	    }
	    
	    private void oneTreeClients(HttpServletRequest request, HttpServletResponse response, String view) throws ServletException, IOException, SQLException {
	        List<quote> oneTreeClientList = userDAO.oneTree();

	        // Check if there are any quotes
	        if (oneTreeClientList != null && !oneTreeClientList.isEmpty()) {
	      
	            for (quote q : oneTreeClientList) {
	                System.out.println("Email: " + q.getEmail());
	            }

	            // Assuming you have a method to retrieve quote information based on the email
	            // You may need to adjust this based on your actual implementation
	            List<quote> quoteInfoList = new ArrayList<>();
	            for (quote q : oneTreeClientList) {
	            	quote quoteInfo = userDAO.quoteInfo(q.getEmail());
	                if (quoteInfo != null) {
	                    quoteInfoList.add(quoteInfo);
	                }
	            }

	            // Send the list of quote information to the front end
	            request.setAttribute("quoteInfoList", quoteInfoList);
	        } else {
	            System.out.println("No 1 tree clients found.");
	        }

	        request.getRequestDispatcher("bigclient.jsp").forward(request, response);
	    }
	    
	    private void highestTree(HttpServletRequest request, HttpServletResponse response, String view) throws ServletException, IOException, SQLException {
	        List<quote> highestTreeQuote = userDAO.highestTreeCut();

	        // Check if there are any quotes
	        if (highestTreeQuote != null && !highestTreeQuote.isEmpty()) {
	      
	            for (quote q : highestTreeQuote) {
	                System.out.println("Email: " + q.getEmail());
	            }

	            // Assuming you have a method to retrieve quote information based on the email
	            // You may need to adjust this based on your actual implementation
	            List<quote> quoteInfoList = new ArrayList<>();
	            for (quote q : highestTreeQuote) {
	            	quote quoteInfo = userDAO.quoteInfo(q.getEmail());
	                if (quoteInfo != null) {
	                    quoteInfoList.add(quoteInfo);
	                }
	            }

	            // Send the list of quote information to the front end
	            request.setAttribute("quoteInfoList", quoteInfoList);
	        } else {
	            System.out.println("No big clients found.");
	        }

	        request.getRequestDispatcher("highesttree.jsp").forward(request, response);
	    }

	    
	    
	    private void adminPage(HttpServletRequest request, HttpServletResponse response, String view) throws ServletException, IOException, SQLException{
//	    	System.out.println("admin view");
//	    	List<quote> listQuotes = userDAO.listAllQuotes(); // Ensure 'Quote' is the correct class name
//            String tree_size= listQuotes.get(0).getTreeSize();
//            System.out.println(tree_size);
//            request.setAttribute("listQuotes", userDAO.listAllQuotes()); // Set the list as an attribute
//            request.setAttribute("accountName", currentUser); // Set the list as an attribute
//            request.setAttribute("tree_size", tree_size); // Set the list as an attribute
//
//	    	request.getRequestDispatcher("admin.jsp").forward(request, response);
//	    	
	    	
	    }

	    
	    protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
	    	 String email = request.getParameter("email");
	    	 String password = request.getParameter("password");
	    	 
	    	 if (email.equals("root") && password.equals("pass1234")) {
				 System.out.println("Login Successful! Redirecting to root");
				 session = request.getSession();
				 session.setAttribute("username", email);
				 rootPage(request, response, "");
	    	 }
	    	 else if(userDAO.isValid(email, password)) 
	    	 {
	    		//CHECKS IF IT IS AN CLIENT LOGGING IN
	    		  if ("client".equals(userDAO.getUserRole(email))) {
	    	            
	    	            System.out.println("Login Successful! Redirecting to activitypage.jsp");
	    	            request.setAttribute("clientName", email);
	    	            request.setAttribute("quoteInfo", userDAO.quoteInfo(email));
	    	            
	    	            request.getRequestDispatcher("activitypage.jsp").forward(request, response);
	    	        } 
	    		  
	    		  //CHECKS IF IT IS AN ADMIN LOGGING IN
	    		  else if ("admin".equals(userDAO.getUserRole(email))) {
	    	            
	    			  
	    	            System.out.println("Login Successful! Redirecting to admin.jsp");
	    	            currentUser= email;
	    	            System.out.println("admin view");
	    	            printQuotes(userDAO.listAllQuotes());
	    	            System.out.println("Printing out table");
	    	            userDAO.printTable();
	    	            request.setAttribute("quoteArray", convertQuotesTo2DArray(userDAO.listAllQuotes()));
	    	            request.setAttribute("accountName", currentUser); 
	    	            //response.sendRedirect("listQuotes");  
	    	            request.getRequestDispatcher("admin.jsp").forward(request, response);
	    	         
	    	           
	    	        }
			 	 currentUser = email;
				 System.out.println("Login Successful! Redirecting");
				 request.getRequestDispatcher("activitypage.jsp").forward(request, response);
			 			 			 			 
	    	 }
	    	 else {
	    		 request.setAttribute("loginStr","Login Failed: Please check your credentials.");
	    		 request.getRequestDispatcher("login.jsp").forward(request, response);
	    	 }
	    }
	           
	    private void newQuote(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
	    	
	   
		   	String quoteDateStr = request.getParameter("quoteDate");
		   	Date newQuoteDate = null;

		   	// Parse the date string to a Date object (you may need to handle exceptions)
		   	try {
		   	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		   	    newQuoteDate = dateFormat.parse(quoteDateStr);
		   	} catch (ParseException e) {
		   	    e.printStackTrace();
		   	}
		   	userDAO.updateQuote(request.getParameter("clientName"),request.getParameter("treePrice"),request.getParameter("treeSize"),request.getParameter("treeHeight"),newQuoteDate,request.getParameter("quoteResponse"));
		   	request.setAttribute("clientName", request.getParameter("clientName"));
            request.setAttribute("quoteInfo", userDAO.quoteInfo(request.getParameter("clientName")));
            
            request.getRequestDispatcher("activitypage.jsp").forward(request, response);
	    }
	    
	    
	    private void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
	    	String email = request.getParameter("email");
	   	 	
	   	 	String password = request.getParameter("password");
	   	 	String confirm = request.getParameter("confirmation");
	   	 	String role = request.getParameter("role");
	   	 	if (password.equals(confirm)) {
	   	 		if (!userDAO.checkEmail(email)) {
		   	 		System.out.println("Registration Successful! Added to database");
		            user users = new user(email,password,role);
		   	 		userDAO.insert(users);
		   	 		response.sendRedirect("login.jsp");   // you don't send anything back simulare call from the brower. 
	   	 		}
		   	 	else {
		   	 		System.out.println("Username taken, please enter new username");
		    		 request.setAttribute("errorOne","Registration failed: Username taken, please enter a new username.");
		    		 request.getRequestDispatcher("register.jsp").forward(request, response);
		   	 	}
	   	 	}
	   	 	else {
	   	 		System.out.println("Password and Password Confirmation do not match");
	   		 request.setAttribute("errorTwo","Registration failed: Password and Password Confirmation do not match.");
	   		 request.getRequestDispatcher("register.jsp").forward(request, response);
	   	 	}
	    }    
	    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
	    	currentUser = "";
        		response.sendRedirect("login.jsp");
        	}
	
	    public String usersToString(List<user> users) {
	        StringBuilder result = new StringBuilder();
	        for (user u : users) {
	            result.append("Email: ").append(u.getEmail()).append("\n");
	            result.append("Password: ").append(u.getPassword()).append("\n");
	            result.append("Role: ").append(u.getRole()).append("\n");
	            result.append("\n"); // Add a separator between users
	        }
	        return result.toString();
	    }
	    public void printQuotes(List<quote> quotes) {
	        for (quote q : quotes) {
	            System.out.println("Email: " + q.getEmail());
	            System.out.println("Tree Price: " + q.getTreePrice());
	            System.out.println("Tree Size: " + q.getTreeSize());
	            System.out.println("Tree Number: " + q.getTreeHeight());
	            System.out.println("Quote Date: " + q.getQuoteDate());
	            System.out.println("Quote Response: " + q.getQuoteResponse());
	            System.out.println(); // Add a separator between quotes
	        }
	    }
	    
	    public String[][] convertQuotesTo2DArray(List<quote> listQuotes) {
	        int numRows = listQuotes.size();
	        int numCols = 6; // Assuming you want a 2D array with 6 columns

	        String[][] result = new String[numRows][numCols];

	        for (int i = 0; i < numRows; i++) {
	            quote quoteObject = listQuotes.get(i);
	            result[i][0] = quoteObject.getEmail();
	            result[i][1] = quoteObject.getTreePrice();
	            result[i][2] = quoteObject.getTreeSize();
	            result[i][3] = quoteObject.getTreeHeight();
	            result[i][4] = quoteObject.getQuoteDate().toString(); // Convert Date to String
	            result[i][5] = quoteObject.getQuoteResponse();
	        }

	        return result;
	    }

	    public void updateQuote(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
	    	
	         response.setContentType("application/json");

	         try (BufferedReader reader = request.getReader()) {
	             StringBuilder jsonData = new StringBuilder();
	             String line;
	             while ((line = reader.readLine()) != null) {
	                 jsonData.append(line);
	             }

	             // Remove curly braces from the beginning and end
	             String jsonContent = jsonData.toString().substring(1, jsonData.length() - 1);

	             String[] components = jsonContent.split(",");
	             String user = null;
	             String newInfo = null;
	             String position = null;

	             for (String component : components) {
	                 String[] parts = component.split(":");
	                 if (parts.length == 2) {
	                     String key = parts[0].trim();
	                     String value = parts[1].trim();

	                     // Remove surrounding double quotes
	                     value = value.replaceAll("\"", "").trim();

	                     if (key.equals("\"user\"")) {
	                         user = value;
	                     } else if (key.equals("\"newInfo\"")) {
	                         newInfo = value;
	                     } else if (key.equals("\"position\"")) {
	                         position = value;
	                     }
	                 }
	             }
	             System.out.println("User: " + user);
	             System.out.println("New Info: " + newInfo);
	             String newPos = position.replaceAll("}", "");

	             int number = Integer.parseInt(newPos);
	             System.out.println("Position: " + number);
	             userDAO.updateQuoteFromFrontend(user, newInfo, number);
		    	 
	           
	             response.getWriter().write("Update successful");
	         } catch (IOException e) {
	             
	             response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	             response.getWriter().write("Error: " + e.getMessage());
	         }
	    	
	    	
	    }
	    
	     	    
	    
}
	        
	        
	    
	        
	        
	        
	    


