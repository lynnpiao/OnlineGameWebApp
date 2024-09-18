package CS5200ms3.servlet;

import CS5200ms3.dal.*;
import CS5200ms3.model.*;
import CS5200ms3.model.Character;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/findinventory")
public class FindInventory extends HttpServlet {
	
	protected InventoryDao inventoryDao;
	protected WeaponDao weaponDao;
	protected GearDao gearDao;
//	protected ConsumableDao consumableDao;
	protected CharacterDao characterDao;
	
	@Override
	public void init() throws ServletException {
		inventoryDao = InventoryDao.getInstance();
		weaponDao = WeaponDao.getInstance();
		gearDao = GearDao.getInstance();
//		consumableDao = ConsumableDao.getInstance();
		characterDao = CharacterDao.getInstance();
		
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
		
		// Retrieve and validate UserName.
        String characterID = req.getParameter("characterID");
        if (characterID == null || characterID.trim().isEmpty()) {
            messages.put("title", "Invalid Character.");
        } 
        
        // Retrieve BlogUsers, and store in the request.
        List<Inventory> inventorys = new ArrayList<Inventory>();
        List<String> itemCategorys = new ArrayList<String>();
        
        try {
        	int intcharacterID = Integer.parseInt(characterID);
        	Character character = characterDao.getCharacterByID(intcharacterID);
        	messages.put("title", "Inventory for Character: "+character.getFirstName()+" , "+character.getLastName());
        	inventorys = inventoryDao.getInventoryByCharacter(character);
        	
        	for(Inventory inv: inventorys) {
        		int itemID = inv.getItem().getItemID();
        		Weapon weapon = weaponDao.getWeaponByItemID(itemID);
        		Gear gear = gearDao.getGearByItemID(itemID);
        		if (weapon!=null) {
        			itemCategorys.add("Weapon");
        		}else if(gear!=null) {
        			itemCategorys.add("Gear");
        		}else {
        			itemCategorys.add("Consumable");
        		}
        	}
        	
        } catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
        }
        req.setAttribute("inventorys", inventorys);
        req.setAttribute("itemCategorys", itemCategorys);
        req.getRequestDispatcher("/FindInventory.jsp").forward(req, resp);
        
	}
}
