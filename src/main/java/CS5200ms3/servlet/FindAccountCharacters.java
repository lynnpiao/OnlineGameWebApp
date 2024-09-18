package CS5200ms3.servlet;

import CS5200ms3.dal.*;
import CS5200ms3.model.*;
import CS5200ms3.model.Character;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Comparator;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * FindUsers is the primary entry point into the application.
 * 
 * Note the logic for doGet() and doPost() are almost identical. However, there is a difference:
 * doGet() handles the http GET request. This method is called when you put in the /findusers
 * URL in the browser.
 * doPost() handles the http POST request. This method is called after you click the submit button.
 * 
 * To run:
 * 1. Run the SQL script to recreate your database schema: http://goo.gl/86a11H.
 * 2. Insert test data. You can do this by running blog.tools.Inserter (right click,
 *    Run As > JavaApplication.
 *    Notice that this is similar to Runner.java in our JDBC example.
 * 3. Run the Tomcat server at localhost.
 * 4. Point your browser to http://localhost:8080/BlogApplication/findusers.
 */
@WebServlet("/findaccountcharacters")
public class FindAccountCharacters extends HttpServlet {
	
	protected CharacterDao characterDao;
	protected PlayerAccountDao playerAccountDao;
	protected WeaponDao weaponDao;
	
	@Override
	public void init() throws ServletException {
		characterDao = CharacterDao.getInstance();
		weaponDao = WeaponDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        List<Character> characters = new ArrayList<Character>();
        List<Weapon> weapons = new ArrayList<Weapon>();
        // Retrieve and validate name.
        // userName is retrieved from the URL query string.
        String userName = req.getParameter("userName");
        String sortedVal = req.getParameter("sortedVal");
        if (userName == null || userName.trim().isEmpty()) {
            messages.put("success", "Please enter a valid name.");
        } else {
        	// Retrieve BlogUsers, and store as a message.
        	try {
        		characters = characterDao.getCharactersByuserName(userName);
        		

//        		System.out.println("at first, out of the sort loop");
//        		for(Character chr:characters) {
//        			System.out.println(chr.getFirstName());
//        		}
//        		System.out.println(sortedVal.trim());
        		
        		if ( "firstName".equals(sortedVal.trim()) ) {
//        			
        			characters.sort(Comparator.comparing(Character::getFirstName));
//        			System.out.println("in the sort ");
//        			for(Character chr:characters) {
//            			System.out.println(chr.getFirstName());
//            		}
        		}else {
        			characters.sort(Comparator.comparing(Character::getLastName));
        		}
        		
        		weapons = weaponDao.getWeaponByuserName(userName);
       		        		
//        		PlayerAccount account = playerAccountDao.getPlayerByUserName(userName);
        		
            } catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
        	messages.put("success", "Displaying Character results for " + userName);
        	// Save the previous search term, so it can be used as the default
        	// in the input box when rendering FindUsers.jsp.
//        	messages.put("previousFirstName", userName);
        }
        int accountID = 1;
        for(Character cha: characters) {
    		accountID = cha.getPlayerAccount().getAccountID();
        }
        req.setAttribute("accountID", accountID);
        req.setAttribute("characters", characters);
        req.setAttribute("weapons", weapons);
        
        req.getRequestDispatcher("/FindAccountCharacters.jsp").forward(req, resp);
	}
	
//	@Override
//    public void doPost(HttpServletRequest req, HttpServletResponse resp)
//    		throws ServletException, IOException {
//        // Map for storing messages.
//        Map<String, String> messages = new HashMap<String, String>();
//        req.setAttribute("messages", messages);
//
//        List<BlogUsers> blogUsers = new ArrayList<BlogUsers>();
//        
//        // Retrieve and validate name.
//        // firstname is retrieved from the form POST submission. By default, it
//        // is populated by the URL query string (in FindUsers.jsp).
//        String firstName = req.getParameter("firstname");
//        if (firstName == null || firstName.trim().isEmpty()) {
//            messages.put("success", "Please enter a valid name.");
//        } else {
//        	// Retrieve BlogUsers, and store as a message.
//        	try {
//            	blogUsers = blogUsersDao.getBlogUsersFromFirstName(firstName);
//            } catch (SQLException e) {
//    			e.printStackTrace();
//    			throw new IOException(e);
//            }
//        	messages.put("success", "Displaying results for " + firstName);
//        }
//        req.setAttribute("blogUsers", blogUsers);
//        
//        req.getRequestDispatcher("/FindUsers.jsp").forward(req, resp);
//    }
}
