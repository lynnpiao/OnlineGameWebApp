package CS5200ms3.dal;

import CS5200ms3.model.*;
import CS5200ms3.model.Character;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InventoryDao {
	protected ConnectionManager connectionManager;
	
	//singleton setup, static instance, private constructor, getInstance()
	private static InventoryDao instance = null;
	protected InventoryDao() {
		connectionManager = new ConnectionManager();
	}
	public static InventoryDao getInstance() {
		if(instance == null) {
			instance = new InventoryDao();
		}
		return instance;
	}

	public Inventory create(Inventory inventory) throws SQLException{
		String insertInventory = "INSERT INTO Inventory(characterID, inventoryPosition, itemID, itemQuantity) VALUES(?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertInventory);
			insertStmt.setInt(1, inventory.getCharacter().getCharacterID());
			insertStmt.setInt(2, inventory.getInventoryPosition());
			insertStmt.setInt(3, inventory.getItem().getItemID());
			insertStmt.setInt(4, inventory.getItemQuantity());
			insertStmt.executeUpdate();

			return inventory;
			
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
			if(results != null) {
				results.close();
			}
		}
	}
	
	public Inventory updateItemQuantity(Inventory inventory, int newItemQuantity) throws SQLException {
		String updateInventory = "UPDATE Inventory SET itemQuantity=? WHERE characterID=? AND inventoryPosition=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateInventory);
			updateStmt.setInt(1, newItemQuantity);
			updateStmt.setInt(2, inventory.getCharacter().getCharacterID());
			updateStmt.setInt(3, inventory.getInventoryPosition());
			updateStmt.executeUpdate();

			// Update the blogPost param before returning to the caller.
			inventory.setItemQuantity(newItemQuantity);
			return inventory;
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
	
	public Inventory delete(Inventory inventory) throws SQLException {
		// Note: BlogComments has a fk constraint on BlogPosts with the reference option
		// ON DELETE CASCADE. So this delete operation will delete all the referencing
		// BlogComments.
		String deleteInventory = "DELETE FROM Inventory WHERE characterID=? AND inventoryPosition=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteInventory);
			deleteStmt.setInt(1, inventory.getCharacter().getCharacterID());
			deleteStmt.setInt(2, inventory.getInventoryPosition());
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
	
	public Inventory getInventoryByCharacterAndPostion(Character character, int inventoryPosition) throws SQLException {
		String selectCharacterAttributeRelation =
			"SELECT characterID, inventoryPosition, itemID, itemQuantity " +
			"FROM Inventory " +
			"WHERE characterID=? AND inventoryPosition=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectCharacterAttributeRelation);
			selectStmt.setInt(1, character.getCharacterID());
			selectStmt.setInt(2, inventoryPosition);
			results = selectStmt.executeQuery();
			ItemDao itemDao = ItemDao.getInstance();
			
			if(results.next()) {
				
				int itemID = results.getInt("itemID");
				Item item = itemDao.getItemByItemID(itemID);
				int itemQuantity = results.getInt("itemQuantity");
				Inventory inventory = new Inventory(character, inventoryPosition, item, itemQuantity);
				return inventory;
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
	
	
	public List<Inventory> getInventoryByCharacter(Character character) throws SQLException {
		List<Inventory> inventorys = new ArrayList<Inventory>();
		String selectCharacterAttributeRelations =
			"SELECT Inventory.characterID, inventoryPosition, itemID, itemQuantity " +
			"FROM Inventory INNER JOIN `Character` "
			+ "ON Inventory.characterID=`Character`.characterID "
			+ "WHERE Inventory.characterID=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectCharacterAttributeRelations);
			selectStmt.setInt(1, character.getCharacterID());
			results = selectStmt.executeQuery();
			ItemDao itemDao = ItemDao.getInstance();
			while(results.next()) {
				int inventoryPosition = results.getInt("inventoryPosition");
				int itemID = results.getInt("itemID");
				Item item = itemDao.getItemByItemID(itemID);
				int itemQuantity = results.getInt("itemQuantity");
				
			    Inventory inventory = new Inventory(character, inventoryPosition, item, itemQuantity);
				
			    inventorys.add(inventory);
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
		return inventorys;
	}
}