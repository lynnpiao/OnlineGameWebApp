package CS5200ms3.servlet;

import CS5200ms3.dal.*;
import CS5200ms3.model.*;
import CS5200ms3.model.Character;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/updatecurrentjob")
public class UpdateCurrentJob extends HttpServlet {
	
	protected WeaponDao weaponDao;
	protected GearDao gearDao;
	protected CharacterDao characterDao;
	protected CharacterGearRelationDao characterGearRelationDao;
	
	@Override
	public void init() throws ServletException {
		weaponDao = WeaponDao.getInstance();
		gearDao = GearDao.getInstance();
		characterDao = CharacterDao.getInstance();
		characterGearRelationDao = CharacterGearRelationDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve user and validate.
        String characterID = req.getParameter("characterID");
        String jobID = req.getParameter("jobID");
        List<Weapon> weapons = new ArrayList<Weapon>();
        List<Gear> gears = new ArrayList<Gear>();

//        List<Gear> currentgears =  new ArrayList<Gear>();
        List<CharacterGearRelation> characterGearRelations = new ArrayList<CharacterGearRelation>();
        List<Integer> currentGearIDs = new ArrayList<Integer>();
        List<Integer> newWeaponIDs = new ArrayList<Integer>();
        List<Integer> newGearIDs = new ArrayList<Integer>();
        List<String> isCurrentWeapon = new ArrayList<String>();
        List<String> isCurrentGear = new ArrayList<String>();
        List<Gear> disablegears = new ArrayList<Gear>();
        Weapon disableweapon = null;
        
        if (characterID == null || characterID.trim().isEmpty() || jobID == null || jobID.trim().isEmpty()) {
            messages.put("success", "Invalid Job Change.");
        } else {
        	try {
        		int intcharacterID = Integer.parseInt(characterID);
        		int intjobID = Integer.parseInt(jobID);
        		Character character = characterDao.getCharacterByID(intcharacterID);
        		
////        		int currentweaponID = character.getMainHandItem().getItemID();
//        		weapons = weaponDao.getWeaponByCharacterIDANDJobID(intjobID, intcharacterID);
//        		gears = gearDao.getGearByCharacterIDANDJobID(intjobID, intcharacterID);
//        		
        		if(character == null) {
        			messages.put("success", "Character does not exist.");
        		}else {
        			int currentweaponID = character.getMainHandItem().getItemID();
        			characterGearRelations = characterGearRelationDao.getCharacterGearRelationsByCharacter(character);
        			weapons = weaponDao.getWeaponByCharacterIDANDJobID(intjobID, intcharacterID);
            		gears = gearDao.getGearByCharacterIDANDJobID(intjobID, intcharacterID);
            		
            		// whether the qualified weapon and gear is in using
            		for(Weapon w1:weapons) {
                    	int itemID = w1.getItemID();
                    	newWeaponIDs.add(itemID);
                    	if( itemID == currentweaponID) {
                    		isCurrentWeapon.add("Yes");
                    	}else {
                    		isCurrentWeapon.add("No");
                    	}	
                    }
            		for(CharacterGearRelation cgr:characterGearRelations) {
            			currentGearIDs.add(cgr.getGear().getItemID());
            		}
            		for(Gear g1:gears) {
            			int itemID = g1.getItemID();
            			newGearIDs.add(itemID);
                    	if( currentGearIDs.contains(itemID)) {
                    		isCurrentGear.add("Yes");
                    	}else {
                    		isCurrentGear.add("No");
                    	}
            		}
            		// whether the current weapon and gear needs to be disabled 

//            		if(newWeaponIDs.contains(currentweaponID)) { 
//            			messages.put("weapondisable", "1");
//            		}else {
//            			messages.put("weapondisable", "0");
//            			disableweapon = weaponDao.getWeaponByItemID(currentweaponID);
//            		}
//            		
//            		for(CharacterGearRelation cgr:characterGearRelations) {
//            			int currentgearID = cgr.getGear().getItemID();
//            			if (newGearIDs.contains(currentgearID)) {
//            			}else {
//            				Gear currentgear = gearDao.getGearByItemID(currentgearID);
//            				disablegears.add(currentgear);
//            			}
//            		}
//            		if (disablegears.isEmpty()) {
//            			messages.put("geardisable","1");
//            		}else {
//            			messages.put("geardisable","0");
//            		}
//            		System.out.print("Weapon disable boolean value");
//            		System.out.print(messages.get("weapondisable"));
//            		System.out.print("Gear disable boolean value");
//            		System.out.print(messages.get("geardisable"));
            		
//					req.setAttribute("disableweapon", disableweapon);
        			req.setAttribute("character", character);
        		}
        		
        	} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.setAttribute("weapons", weapons);
        req.setAttribute("gears", gears);
        req.setAttribute("isCurrentWeapon", isCurrentWeapon);
        req.setAttribute("isCurrentGear", isCurrentGear);
        
//        req.setAttribute("disableweapon", disableweapon);
//        req.setAttribute("disablegears", disablegears);
        req.getRequestDispatcher("/UpdateCurrentJob.jsp").forward(req, resp);
	}
	
//	@Override
//    public void doPost(HttpServletRequest req, HttpServletResponse resp)
//    		throws ServletException, IOException {
//        // Map for storing messages.
//        Map<String, String> messages = new HashMap<String, String>();
//        req.setAttribute("messages", messages);
//
//        // Retrieve user and validate.
//        String characterID = req.getParameter("characterID");
//        String jobID = req.getParameter("jobID");
//        List<Weapon> weapons = new ArrayList<Weapon>();
//        List<Gear> gears = new ArrayList<Gear>();
//        
//        if (characterID == null || characterID.trim().isEmpty() || jobID == null || jobID.trim().isEmpty()) {
//            messages.put("success", "Invalid Job Change.");
//        } else {
//        	// Retrieve BlogUsers, and store as a message.
//        	try {
//        		int intcharacterID = Integer.parseInt(characterID);
//        		int intjobID = Integer.parseInt(jobID);
//        		Character character = characterDao.getCharacterByID(intcharacterID);
//        		weapons = weaponDao.getWeaponByCharacterIDANDJobID(intjobID, intcharacterID);
//        		gears = gearDao.getGearByCharacterIDANDJobID(intjobID, intcharacterID);
//        		messages.put("success", "Displaying Item results for " + character.getFirstName()+'-'+character.getLastName());
//        		
//        		req.setAttribute("character", character);
//            } catch (SQLException e) {
//    			e.printStackTrace();
//    			throw new IOException(e);
//            }
////        	messages.put("success", "Displaying Item results for " + userName);
//        	// Save the previous search term, so it can be used as the default
//        	// in the input box when rendering FindUsers.jsp.
////        	messages.put("previousFirstName", userName);
//        }
//        req.setAttribute("weapons", weapons);
//        req.setAttribute("gears", gears);
//        req.getRequestDispatcher("/UpdateCurrentJob.jsp").forward(req, resp);
//    }
}
