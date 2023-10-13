public class user 
{
		protected String password;
	 	protected String email;
	 	protected String role;
	 
	    //constructors
	    public user() {
	    }
	 
	    public user(String email) 
	    {
	        this.email = email;
	    }
	    
	    public user(String email, String password) 
	    {
	    	this.password= password;
	    	this.email = email;
	    }
	    public user(String email, String password, String role) 
	    {
	    	this.password= password;
	    	this.email = email;
	    	this.role =role;
	    }
	    
	   //getter and setter methods
	    public String getEmail() {
	        return email;
	    }
	    public void setEmail(String email) {
	        this.email = email;
	    }
	    
	    public String getPassword() {
	        return password;
	    }
	    public void setPassword(String password) {
	        this.password = password;
	    }
	    
	    public String getRole() {
	        return role;
	    }
	    public void setRole(String role) {
	        this.role = role;
	    }
	  
	}