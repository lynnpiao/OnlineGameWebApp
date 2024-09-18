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


@WebServlet("/updatecharactergear")
public class UpdateCharacterGear extends HttpServlet {
	
	protected CharacterDao characterDao;
	protected GearDao gearDao;
	protected CharacterGearRelationDao characterGearRelationDao;
	
	@Override
	public void init() throws ServletException {
		characterDao = CharacterDao.getInstance();
		gearDao = GearDao.getInstance();
		characterGearRelationDao = CharacterGearRelationDao.getInstance();
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
        String gearID = req.getParameter("gearID");
        String slotType = req.getParameter("slotType");
        
        if (characterID == null || characterID.trim().isEmpty() || gearID == null || gearID.trim().isEmpty()) {
            messages.put("success", "Invalid to update Gear.");
        } else {
        	try {
        		
        		int intcharacterID = Integer.parseInt(characterID);
        		int intgearID = Integer.parseInt(gearID);
        		
        		Gear gear= gearDao.getGearByItemID(intgearID);
        		Character character = characterDao.getCharacterByID(intcharacterID);
        		req.setAttribute("gear", gear);
        		
        		if(character == null) {
        			messages.put("success", "UserName does not exist.");
        		}
        		req.setAttribute("character", character);
        	} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/UpdateCharacterGear.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve user and validate.
        String characterID = req.getParameter("characterID");
        String gearID = req.getParameter("gearID");
        String slotType = req.getParameter("slotType");
        
        if (characterID == null || characterID.trim().isEmpty() || gearID == null || gearID.trim().isEmpty()) {
            messages.put("success", "Invalid to update MainHandItem.");
        } else {
        	try {
        		int intcharacterID = Integer.parseInt(characterID);
        		int intgearID = Integer.parseInt(gearID);
        		
        		Gear gear = gearDao.getGearByItemID(intgearID);
        		Character character = characterDao.getCharacterByID(intcharacterID);
        		CharacterGearRelation characterGearRelation = characterGearRelationDao.getCharacterGearRelationByCharacterAndSlot(character, slotType);
        		
        		req.setAttribute("gear", gear);
        		
        		if(character == null) {
        			messages.put("success", "Character does not exist. No update to perform.");
        		} else {
        			;
        			if (gear == null) {
        	            messages.put("success", "Gear does not exist. No update to perform.");
        	        } else {
        	        	if (characterGearRelation == null) {
        	        	CharacterGearRelation characterGearRelation2 = new CharacterGearRelation(character, slotType, gear);
        	        	characterGearRelation2 = characterGearRelationDao.create(characterGearRelation2);
        		        	
        	        	messages.put("success", "Create new CharacterGearRelation.");
        	        	}else {
        	        	characterGearRelation = characterGearRelationDao.updateGear(characterGearRelation, intgearID);
        	        	messages.put("success", "Successfully updated " + gear.getItemName());}
        	        }
        		}
        		req.setAttribute("character", character);
        		
        	} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/UpdateCharacterGear.jsp").forward(req, resp);
    }
}
