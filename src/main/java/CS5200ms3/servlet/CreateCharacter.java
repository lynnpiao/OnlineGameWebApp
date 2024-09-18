package CS5200ms3.servlet;

import CS5200ms3.dal.*;
import CS5200ms3.model.*;
import CS5200ms3.model.Character;
import CS5200ms3.model.PlayerAccount;
import CS5200ms3.model.Weapon;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/createcharacter")
public class CreateCharacter extends HttpServlet {
	
	protected CharacterDao characterDao;
	protected WeaponDao weaponDao;
	protected PlayerAccountDao playerAccountDao;
	
	@Override
	public void init() throws ServletException {
		characterDao = CharacterDao.getInstance();
		weaponDao = WeaponDao.getInstance();
		playerAccountDao = PlayerAccountDao.getInstance();
		
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        //Just render the JSP.   
        req.getRequestDispatcher("/CreateCharacter.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve and validate name.
        String accountID = req.getParameter("accountID");
        String userName = req.getParameter("userName");
        String firstName = req.getParameter("firstName");
    	String lastName = req.getParameter("lastName");
    	String itemID = req.getParameter("mainHandItemID");
    	
        if (accountID == null || accountID.trim().isEmpty()) {
            messages.put("success", "Invalid Account to create Character");
        } else {
        	// Create the 
        	int intaccountID = Integer.parseInt(accountID);
        	int intitemID =  Integer.parseInt(itemID);
        	
        	try {
        		PlayerAccount account = playerAccountDao.getPlayerByAccountID(intaccountID);
        		Weapon weapon = weaponDao.getWeaponByItemID(intitemID);
        		Character character = new Character(account, firstName, lastName, weapon);
	        	character = characterDao.create(character);
	        	messages.put("success", "Successfully created new Character under UserName:" + userName);
	        } catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
        		
        	} 	
        	
//        	// dob must be in the format yyyy-mm-dd.
//        	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        	String stringDob = req.getParameter("dob");
//        	Date dob = new Date();
//        	try {
//        		PlayerAccount account = playerAccountDao.getPlayerByAccountID(intaccountID);
//        	} catch (ParseException e) {
//        		e.printStackTrace();
//				throw new IOException(e);
//        	}
//	        try {
//	        	// Exercise: parse the input for StatusLevel.
//	        	Character character = new Character(account,firstName,lastName, weapon);
//	        	character = characterDao.create(character);
//	        	messages.put("success", "Successfully created new Character" + firstName + lastName);
//	        } catch (SQLException e) {
//				e.printStackTrace();
//				throw new IOException(e);
//	        }
        }
        
        req.getRequestDispatcher("/CreateCharacter.jsp").forward(req, resp);
    }
}
