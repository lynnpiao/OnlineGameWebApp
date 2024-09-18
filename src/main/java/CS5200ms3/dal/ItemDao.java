package CS5200ms3.dal;

import CS5200ms3.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;



public class ItemDao{
	protected ConnectionManager connectionManager;
	// Single pattern: instantiation is limited to one object.
	private static ItemDao instance = null;
	protected ItemDao() {
		connectionManager = new ConnectionManager();
	}
	public static ItemDao getInstance() {
		if(instance == null) {
			instance = new ItemDao();
		}
		return instance;
	}

	public Item create(Item item) throws SQLException {
		String insertItem = "INSERT INTO Item(itemName,maxStackSize,isSellable,unitPrice,itemLevel) VALUES(?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();		
			insertStmt = connection.prepareStatement(insertItem,Statement.RETURN_GENERATED_KEYS);
			insertStmt.setString(1, item.getItemName());
			insertStmt.setInt(2, item.getMaxStackSize());
			insertStmt.setBoolean(3, item.getIsSellable());
//			insertStmt.setObject(4,item.getUnitPrice());
			// Allow null values 
			if (item.getUnitPrice() != null) {
                insertStmt.setInt(4, item.getUnitPrice());
            } else {
            	insertStmt.setNull(4, Types.INTEGER);
            }
			insertStmt.setInt(5,item.getItemLevel());
			insertStmt.executeUpdate();
			
			// Still using the expression that professor provided in class to catch whether there's expections
			resultKey = insertStmt.getGeneratedKeys();
			int itemID = -1;
			if(resultKey.next()) {
				itemID = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			item.setItemID(itemID);
			return item;
							
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
	
	public Item updateUnitPriceAndIsSellable(Item item, Integer newPrice, boolean isSellable) throws SQLException {
		String updateUnitPriceAndIsSellable = "UPDATE Item SET unitPrice=?, isSellable=? WHERE itemID=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateUnitPriceAndIsSellable);
			updateStmt.setInt(1, newPrice);
			// Sets the Created timestamp to the current time.
			updateStmt.setBoolean(2, isSellable);
			updateStmt.setInt(3, item.getItemID());
			updateStmt.executeUpdate();

			// Update the blogPost param before returning to the caller.
			item.setUnitPrice(newPrice);
			item.setSellable(isSellable);
			return item;
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
	
	public Item delete(Item item) throws SQLException {
		// Note: BlogComments has a fk constraint on BlogPosts with the reference option
		// ON DELETE CASCADE. So this delete operation will delete all the referencing
		// BlogComments.
		String deleteItem = "DELETE FROM Item WHERE itemID=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteItem);
			deleteStmt.setInt(1, item.getItemID());
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

	public Item getItemByItemID(int itemID) throws SQLException {
		
		String selectItem =
			"SELECT itemID,itemName,maxStackSize,isSellable,unitPrice,itemLevel FROM Item WHERE Item.itemID=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectItem);
			selectStmt.setInt(1, itemID);
			results = selectStmt.executeQuery();
			if(results.next()) {
				String itemName = results.getString("itemName");
				int maxStackSize = results.getInt("maxStackSize");
				boolean isSellable = results.getBoolean("isSellable");
				Integer unitPrice = results.getObject("unitPrice", Integer.class);
				int itemLevel = results.getInt("itemLevel");
				
				Item item = new Item(itemID, itemName, maxStackSize, isSellable, unitPrice, itemLevel);
				return item;
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
	
	
	/**
	 * Get the all the Item by itemLevel
	 */
	public List<Item> getItemsByItemLevel(int itemLevel) throws SQLException {
		List<Item> items = new ArrayList<Item>();
		String selectItems =
			"SELECT itemID,itemName,maxStackSize,isSellable,unitPrice,itemLevel " +
			"FROM Item " +
			"WHERE itemLevel=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectItems);
			selectStmt.setInt(1, itemLevel);
			results = selectStmt.executeQuery();
			while(results.next()) {
				int itemID = results.getInt("itemID");
				String itemName = results.getString("itemName");
				int maxStackSize = results.getInt("maxStackSize");
				boolean isSellable = results.getBoolean("isSellable");
				Integer unitPrice = results.getObject("unitPrice", Integer.class);
				
				Item item = new Item(itemID,itemName,maxStackSize,isSellable,unitPrice, itemLevel);
				
				items.add(item);
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
		return items;
	}
}
