package CS5200ms3.dal;

import CS5200ms3.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data access object (DAO) class to interact with the underlying BlogUsers table in your
 * MySQL instance. This is used to store {@link BlogUsers} into your MySQL instance and 
 * retrieve {@link BlogUsers} from MySQL instance.
 */
public class GearDao extends ItemDao {
	// Single pattern: instantiation is limited to one object.
	private static GearDao instance = null;
	protected GearDao() {
		super();
	}
	public static GearDao getInstance() {
		if(instance == null) {
			instance = new GearDao();
		}
		return instance;
	}

	public Gear create(Gear gear) throws SQLException {
		// Insert into the superclass table first.
		Item item = create(new Item(gear.getItemName(), gear.getMaxStackSize(), gear.getIsSellable(),
				gear.getUnitPrice(), gear.getItemLevel()));
		
		String insertGear = "INSERT INTO Gear(itemID,slotType,requiredLevel) VALUES(?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();		
			insertStmt = connection.prepareStatement(insertGear);
			insertStmt.setInt(1, item.getItemID());
			insertStmt.setString(2, gear.getSlotType());
			insertStmt.setInt(3, gear.getRequiredLevel());
			insertStmt.executeUpdate();
			return gear;
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

	public Gear delete(Gear gear) throws SQLException {
		String deleteGear = "DELETE FROM Gear WHERE itemID=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteGear);
			deleteStmt.setInt(1, gear.getItemID());
			int affectedRows = deleteStmt.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("No records available to delete for itemID=" + gear.getItemID());
			}

			super.delete(gear);

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

	public Gear getGearByItemID(int itemID) throws SQLException {
		// To build an BlogUser object, we need the Persons record, too.
		String selectGear =
				"SELECT Gear.itemID as itemID,itemName, maxStackSize,isSellable,unitPrice,itemLevel,slotType, requiredLevel " +
				"FROM Gear INNER JOIN Item "
				+ "ON Gear.itemID = Item.itemID " +
				"WHERE Gear.itemID=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectGear);
			selectStmt.setInt(1, itemID);
			results = selectStmt.executeQuery();
			if(results.next()) {
				String itemName = results.getString("itemName");
				int maxStackSize = results.getInt("maxStackSize");
				boolean isSellable = results.getBoolean("isSellable");
				Integer unitPrice = results.getInt("unitPrice");
				int itemLevel = results.getInt("itemLevel");
				String slotType = results.getString("slotType");
				int requiredLevel =results.getInt("requiredLevel");
				Gear gear = new Gear(itemID, itemName, maxStackSize, isSellable, unitPrice, itemLevel,slotType,requiredLevel);
				return gear;
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
	
	public List<Gear> getGearByCharacterIDANDJobID(int jobID, int characterID) throws SQLException {
		// To build an BlogUser object, we need the Persons record, too.
		List<Gear> gears = new ArrayList<Gear>();
		String selectGears =
			"SELECT DISTINCT "
			+ "g.itemID as itemID, i2.itemName, i2.maxStackSize, i2.isSellable, "
			+ "i2.unitPrice , i2.itemLevel, g.slotType, g.requiredLevel "
			+ "FROM Inventory i "
			+ "INNER JOIN Gear g on g.itemID =i.itemID "
			+ "INNER JOIN CharacterJobRelation cjr on cjr.characterID =i.characterID "
			+ "INNER JOIN GearJobRelation gjr on gjr.jobID = cjr.jobID "
			+ "LEFT JOIN Item i2 on i.itemID =i2.itemID "
			+ "WHERE cjr.jobID =? and cjr.characterID=?  and cjr.jobLevel >= g.requiredLevel ;" ;
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectGears);
			selectStmt.setInt(1, jobID);
			selectStmt.setInt(2, characterID);
			results = selectStmt.executeQuery();
			while(results.next()) {
				int itemID = results.getInt("itemID");
				String itemName = results.getString("itemName");
				int maxStackSize = results.getInt("maxStackSize");
				boolean isSellable = results.getBoolean("isSellable");
				Integer unitPrice = results.getInt("unitPrice");
				int itemLevel = results.getInt("itemLevel");
				String slotType = results.getString("slotType");
				int requiredLevel = results.getInt("requiredLevel");
				Gear gear = new Gear(itemID, itemName, maxStackSize, isSellable, unitPrice, itemLevel,slotType,requiredLevel);
				gears.add(gear);
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
		return gears;
	}
	
	public List<Gear> getGearBySlot(String slotType) throws SQLException {
		// To build an BlogUser object, we need the Persons record, too.
		List<Gear> gears = new ArrayList<Gear>();
		String selectGears =
			"SELECT Gear.itemID as itemID,itemName, maxStackSize,isSellable,unitPrice,itemLevel,slotType,requiredLevel "
			+ "FROM Gear INNER JOIN Item "
			+ "ON Gear.itemID = Item.itemID " +
			"WHERE Gear.slotType=?;" ;
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectGears);
			selectStmt.setString(1, slotType);
			results = selectStmt.executeQuery();
			while(results.next()) {
				int itemID = results.getInt("itemID");
				String itemName = results.getString("itemName");
				int maxStackSize = results.getInt("maxStackSize");
				boolean isSellable = results.getBoolean("isSellable");
				Integer unitPrice = results.getInt("unitPrice");
				int itemLevel = results.getInt("itemLevel");
				int requiredLevel = results.getInt("requiredLevel");
				Gear gear = new Gear(itemID, itemName, maxStackSize, isSellable, unitPrice, itemLevel,slotType,requiredLevel);
				gears.add(gear);
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
		return gears;
	}
}
