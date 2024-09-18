package CS5200ms3.dal;

import CS5200ms3.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Data access object (DAO) class to interact with the underlying BlogUsers table in your
 * MySQL instance. This is used to store {@link BlogUsers} into your MySQL instance and 
 * retrieve {@link BlogUsers} from MySQL instance.
 */
public class ConsumableDao extends ItemDao {
	// Single pattern: instantiation is limited to one object.
	private static ConsumableDao instance = null;
	protected ConsumableDao() {
		super();
	}
	public static ConsumableDao getInstance() {
		if(instance == null) {
			instance = new ConsumableDao();
		}
		return instance;
	}

	public Consumable create(Consumable consumable) throws SQLException {
		// Insert into the superclass table first.
		Item item = create(new Item(consumable.getItemName(),consumable.getMaxStackSize(),consumable.getIsSellable(),consumable.getUnitPrice(),consumable.getItemLevel()));
		String insertApplicant = "INSERT INTO Consumable(itemID,description) VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();		
			insertStmt = connection.prepareStatement(insertApplicant);
			insertStmt.setInt(1, item.getItemID());
			insertStmt.setString(2, consumable.getDescription());
			insertStmt.executeUpdate();
			return consumable;
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
	
	public Consumable delete(Consumable consumable) throws SQLException {
		String deleteConsumable = "DELETE FROM Consumable WHERE itemID=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteConsumable);
			deleteStmt.setInt(1, consumable.getItemID());
			int affectedRows = deleteStmt.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("No records available to delete for itemID=" + consumable.getItemID());
			}

			
			super.delete(consumable);

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

	public Consumable getConsumableByItemID(int itemID) throws SQLException {
		// To build an BlogUser object, we need the Persons record, too.
		String selectConsumable =
				"SELECT Consumable.itemID as itemID,itemName, maxStackSize,isSellable,unitPrice,itemLevel,description " +
				"FROM Consumable INNER JOIN Item " +
				"  ON Consumable.itemID = Item.itemID " +
				"WHERE Consumable.itemID=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectConsumable);
			selectStmt.setInt(1, itemID);
			results = selectStmt.executeQuery();
			if(results.next()) {
				String itemName = results.getString("itemName");
				int maxStackSize = results.getInt("maxStackSize");
				boolean isSellable = results.getBoolean("isSellable");
				Integer unitPrice = results.getObject("unitPrice", Integer.class);
				int itemLevel = results.getInt("itemLevel");
				String description = results.getString("description");
				Consumable consumable = new Consumable(itemID, itemName, maxStackSize, isSellable, unitPrice, itemLevel,description);
				return consumable;
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
}
