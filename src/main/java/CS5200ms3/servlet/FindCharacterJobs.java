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


@WebServlet("/findcharacterjobs")
public class FindCharacterJobs extends HttpServlet {
	
	protected CharacterJobRelationDao characterJobRelationDao;
	protected CharacterDao characterDao;
//	protected WeaponAttributeRelationDao weaponAttributeRelationDao;
	
	@Override
	public void init() throws ServletException {
		characterJobRelationDao = CharacterJobRelationDao.getInstance();
		characterDao = CharacterDao.getInstance();
//		weaponAttributeRelationDao = WeaponAttributeRelationDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        
        List<CharacterJobRelation> characterJobRelations = new ArrayList<CharacterJobRelation>();
        List<String> isCurrentJob = new ArrayList<String>();
		// Retrieve and validate UserName.
        String characterID = req.getParameter("characterID");
        String currentjobID = req.getParameter("currentjobID");
        int intcurrentjobID = Integer.parseInt(currentjobID);
        
//        String itemID = req.getParameter("itemID");
        if (characterID == null || characterID.trim().isEmpty()) {
            messages.put("title", "Invalid Finding Jobs for the character.");
        }
        
        int incharacterID = Integer.parseInt(characterID);
        
        try {
//        	BlogUsers blogUser = new BlogUsers(userName);
        	
        	Character character = characterDao.getCharacterByID(incharacterID);
        	messages.put("title", "Finding Jobs for Character: "+character.getFirstName()+" , "+character.getLastName());
        	
        	characterJobRelations = characterJobRelationDao.getCharacterJobRelationsByCharacter(character);
        	
//        	req.setAttribute("characterJobRelations", characterJobRelations);
        } catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
        }
//        req.setAttribute("weapons", weapons);
        
        for(CharacterJobRelation cjr:characterJobRelations) {
        	int jobid = cjr.getJob().getJobID();
        	if (jobid==intcurrentjobID) {
        		isCurrentJob.add("Yes");
        	}else {
        		isCurrentJob.add("No");
        	}
        }
        req.setAttribute("isCurrentJob", isCurrentJob);
    	req.setAttribute("characterJobRelations", characterJobRelations);
        req.getRequestDispatcher("/FindCharacterJobs.jsp").forward(req, resp);
	}
}
