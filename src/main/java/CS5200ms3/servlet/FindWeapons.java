package CS5200ms3.servlet;

import CS5200ms3.dal.*;
import CS5200ms3.model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/findweapons")
public class FindWeapons extends HttpServlet {
	
	protected WeaponDao weaponDao;
//	protected WeaponAttributeRelationDao weaponAttributeRelationDao;
	
	@Override
	public void init() throws ServletException {
		weaponDao = WeaponDao.getInstance();
//		weaponAttributeRelationDao = WeaponAttributeRelationDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
		// Retrieve and validate UserName.
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String itemID = req.getParameter("itemID");
        if (itemID == null || itemID.trim().isEmpty()) {
            messages.put("title", "Invalid Weapon.");
        } else {
        	messages.put("title", "Weapon for Character: " + firstName +" , "+ lastName);
        }
        // Retrieve BlogUsers, and store in the request.
//        Weapon weapon = new ArrayList<Weapon>();
        int intitemID = Integer.parseInt(itemID);
        Weapon weapons = null;
        try {
        	weapons = weaponDao.getWeaponByItemID(intitemID);
        	req.setAttribute("weapons", weapons);
        } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
        }
 
        req.getRequestDispatcher("/FindWeapons.jsp").forward(req, resp);
	}
}
