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


@WebServlet("/updateunitpriceandissellable")
public class UpdateUnitPriceAndIsSellable extends HttpServlet {
	
	protected ItemDao itemDao;
	
	@Override
	public void init() throws ServletException {
		itemDao = ItemDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve user and validate.
        String itemID = req.getParameter("itemID");
        if (itemID == null || itemID.trim().isEmpty()) {
            messages.put("success", "Invalid Weapon.");
        } else {
        	try {
        		int intitemID = Integer.parseInt(itemID);
        		Item item = itemDao.getItemByItemID(intitemID);
        		
        		if(item == null) {
        			messages.put("success", "Weapon does not exist.");
        		}
        		req.setAttribute("item", item);
        	} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/UpdateUnitPriceAndIsSellable.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve user and validate.
        String itemID = req.getParameter("itemID");
        
        if (itemID == null || itemID.trim().isEmpty()) {
            messages.put("success", "Invalid Weapon.");
        } else {
        	try {
        		int intitemID = Integer.parseInt(itemID);
        		Item item = itemDao.getItemByItemID(intitemID);
        		
        		if(item == null) {
        			messages.put("success", "Weapon does not exist. No update to perform.");
        		} else {
        			String newunitPrice = req.getParameter("unitPrice");
        			String newisSellable = req.getParameter("isSellable");
        			
        			if (newunitPrice == null || newunitPrice.trim().isEmpty() || newisSellable == null || newisSellable.trim().isEmpty() ) {
        	            messages.put("success", "Please enter All needed Information.");
        	        } else {
        	        	int intunitPrice = Integer.parseInt(newunitPrice);
        	        	boolean isSellable = false;
        	        	if(newisSellable == "1") {
        	        		isSellable=true;}
        	        	item = itemDao.updateUnitPriceAndIsSellable(item, intunitPrice, isSellable);
        	   
        	        	messages.put("success", "Successfully updated the UnitPrice of " + item.getItemName());
        	        }
        		}
        		req.setAttribute("item", item);
        	} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/UpdateUnitPriceAndIsSellable.jsp").forward(req, resp);
    }
}
