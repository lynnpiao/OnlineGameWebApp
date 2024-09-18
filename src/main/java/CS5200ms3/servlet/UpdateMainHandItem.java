package CS5200ms3.servlet;

import CS5200ms3.dal.*;
import CS5200ms3.model.*;
import CS5200ms3.model.Character;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/updatemainhanditem")
public class UpdateMainHandItem extends HttpServlet {
	
	protected CharacterDao characterDao;
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

        // Retrieve user and validate.
        // Retrieve user and validate.
        String characterID = req.getParameter("characterID");
        String mainHandItemID = req.getParameter("mainHandItemID");
        
        if (characterID == null || characterID.trim().isEmpty() || mainHandItemID == null || mainHandItemID.trim().isEmpty()) {
            messages.put("success", "Invalid to update MainHandItem.");
        } else {
        	try {
        		
        		int intcharacterID = Integer.parseInt(characterID);
        		int intmainHandItemID = Integer.parseInt(mainHandItemID);
        		
        		Weapon weapon = weaponDao.getWeaponByItemID(intmainHandItemID);
        		Character character = characterDao.getCharacterByID(intcharacterID);
        		req.setAttribute("weapon", weapon);
        		
        		if(character == null) {
        			messages.put("success", "UserName does not exist.");
        		}
        		req.setAttribute("character", character);
        	} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/UpdateMainHandItem.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve user and validate.
        String characterID = req.getParameter("characterID");
        String mainHandItemID = req.getParameter("mainHandItemID");
        
        if (characterID == null || characterID.trim().isEmpty() || mainHandItemID == null || mainHandItemID.trim().isEmpty()) {
            messages.put("success", "Invalid to update MainHandItem.");
        } else {
        	try {
        		int intcharacterID = Integer.parseInt(characterID);
        		int intmainHandItemID = Integer.parseInt(mainHandItemID);
        		
        		Weapon weapon = weaponDao.getWeaponByItemID(intmainHandItemID);
        		Character character = characterDao.getCharacterByID(intcharacterID);
        		req.setAttribute("weapon", weapon);
        		if(character == null) {
        			messages.put("success", "Character does not exist. No update to perform.");
        		} else {
        			
        			if (weapon == null) {
        	            messages.put("success", "Weapon does not exist. No update to perform.");
        	        } else {
        	        	character = characterDao.updateMainHandItem(character, weapon);
        	        	messages.put("success", "Successfully updated " + weapon.getItemName());
        	        }
        		}
        		req.setAttribute("character", character);
        		
        	} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/UpdateMainHandItem.jsp").forward(req, resp);
    }
}
