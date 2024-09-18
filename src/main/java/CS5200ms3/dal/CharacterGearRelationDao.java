package CS5200ms3.dal;

import CS5200ms3.model.*;
import CS5200ms3.model.Character;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CharacterGearRelationDao {
	protected ConnectionManager connectionManager; 
	 
	private static CharacterGearRelationDao instance = null; 
	protected CharacterGearRelationDao() {
		connectionManager = new ConnectionManager(); 
	}
	public static CharacterGearRelationDao getInstance() {
		if(instance == null) {
			instance = new CharacterGearRelationDao(); 
		}
		return instance; 
	}
	
	
	public CharacterGearRelation create(CharacterGearRelation characterGearRelation) throws SQLException {
		String insertCharacterGearRelation = "INSERT INTO CharacterGearRelation(characterID, slotType, gearID) VALUES(?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
			
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertCharacterGearRelation);
			insertStmt.setInt(1, characterGearRelation.getCharacter().getCharacterID()); 
			insertStmt.setString(2, characterGearRelation.getSlotType()); 
			insertStmt.setInt(3, characterGearRelation.getGear().getItemID()); 
			
			insertStmt.executeUpdate(); 
			
			return characterGearRelation; 
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(insertStmt != null) {
				insertStmt.close();
			}
		}
		
	}
	
	public CharacterGearRelation updateGear(CharacterGearRelation characterGearRelation, int newGearID) throws SQLException {
		String updateCharacterGearRelation = "UPDATE CharacterGearRelation SET gearID=? WHERE characterID=? AND slotType=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateCharacterGearRelation);
			updateStmt.setInt(1, newGearID);
			updateStmt.setInt(2, characterGearRelation.getCharacter().getCharacterID());
			updateStmt.setString(3, characterGearRelation.getSlotType());
			updateStmt.executeUpdate();
			GearDao gearDao = GearDao.getInstance();
			// Update the blogPost param before returning to the caller.
			Gear gear = gearDao.getGearByItemID(newGearID);
			
			characterGearRelation.setGear(gear);
			return characterGearRelation;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(updateStmt != null) {
				updateStmt.close();
			}
		}
	}

	public CharacterGearRelation delete(CharacterGearRelation characterGearRelation) throws SQLException {
		// Note: BlogComments has a fk constraint on BlogPosts with the reference option
		// ON DELETE CASCADE. So this delete operation will delete all the referencing
		// BlogComments.
		String deleteCharacterGearRelation = "DELETE FROM CharacterGearRelation WHERE characterID=? AND slotType=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteCharacterGearRelation);
			deleteStmt.setInt(1, characterGearRelation.getCharacter().getCharacterID());
			deleteStmt.setString(2, characterGearRelation.getSlotType());
			deleteStmt.executeUpdate();

			// Return null so the caller can no longer operate on the BlogPosts instance.
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(deleteStmt != null) {
				deleteStmt.close();
			}
		}
	}

	
	public CharacterGearRelation getCharacterGearRelationByCharacterAndSlot(Character character, String slotType) throws SQLException {
		String selectCharacterGearRelation = "SELECT characterID, slotType, gearID FROM CharacterGearRelation WHERE characterID=? AND slotType=?;"; 
		Connection connection = null;
		PreparedStatement selectStmt = null;	
		ResultSet results = null;
		
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectCharacterGearRelation);	
			
			selectStmt.setInt(1, character.getCharacterID());
			selectStmt.setString(2, slotType);

			results = selectStmt.executeQuery(); 
			
			CharacterDao characterDao = CharacterDao.getInstance(); 
			GearDao gearDao = GearDao.getInstance(); 
			
			if(results.next()) {
				int resultsCharacterID = results.getInt("characterID"); 
				String resultSlotType = results.getString("slotType"); 
				int resultsGearID = results.getInt("gearID"); 
				
				Character resultCharacter = characterDao.getCharacterByID(resultsCharacterID);
				
				Gear resultGear = gearDao.getGearByItemID(resultsGearID);
				
				CharacterGearRelation characterGearRelation = new CharacterGearRelation(resultCharacter, resultSlotType, resultGear); 
				
				return characterGearRelation; 	
			
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(selectStmt != null) {
				selectStmt.close();
			}
			if(results != null) {
				results.close();
			}
		}
		return null;
	
	}	
	
	public List<CharacterGearRelation> getCharacterGearRelationsByCharacter(Character character) throws SQLException {
		List<CharacterGearRelation> characterGearRelations = new ArrayList<CharacterGearRelation>();
		String selectCharacterGearRelations =
			"SELECT characterID, slotType, gearID FROM CharacterGearRelation WHERE characterID=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectCharacterGearRelations);
			selectStmt.setInt(1, character.getCharacterID());
			results = selectStmt.executeQuery();
//			CharacterDao characterDao = CharacterDao.getInstance();
			GearDao gearDao = GearDao.getInstance(); 
			while(results.next()) {
				
//				int characterID = results.getInt("characterID");
//				Character character = characterDao.getCharacterByID(characterID);
				String slotType = results.getString("slotType"); 
				int gearID = results.getInt("gearID"); 
				Gear gear = gearDao.getGearByItemID(gearID);
				
		
				CharacterGearRelation characterGearRelation = new CharacterGearRelation(character, slotType, gear); 
				
				characterGearRelations.add(characterGearRelation);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(selectStmt != null) {
				selectStmt.close();
			}
			if(results != null) {
				results.close();
			}
		}
		return characterGearRelations;
	}
	
	

}