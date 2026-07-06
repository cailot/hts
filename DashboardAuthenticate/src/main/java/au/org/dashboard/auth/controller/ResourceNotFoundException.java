package au.org.dashboard.auth.controller;

public class ResourceNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	ResourceNotFoundException(String username) {
	    super("Could not find user " + username);
	  }

}
