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
public class WeaponDao extends ItemDao {
	// Single pattern: instantiation is limited to one object.
	private static WeaponDao instance = null;
	protected WeaponDao() {
		super();
	}
	public static WeaponDao getInstance() {
		if(instance == null) {
			instance = new WeaponDao();
		}
		return instance;
	}

	public Weapon create(Weapon weapon) throws SQLException {
		// Insert into the superclass table first.
		Item item = create(new Item(weapon.getItemName(),weapon.getMaxStackSize(),weapon.getIsSellable(),weapon.getUnitPrice(),weapon.getItemLevel()));
		
		String insertWeapon = "INSERT INTO Weapon(itemID,requiredLevel,physicalDamage,autoAttack,delay,jobID) VALUES(?,?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();		
			insertStmt = connection.prepareStatement(insertWeapon);
			insertStmt.setInt(1, item.getItemID());
			insertStmt.setInt(2, weapon.getRequiredLevel());
			insertStmt.setInt(3, weapon.getPhysicalDamage());
			insertStmt.setFloat(4, weapon.getAutoAttack());
			insertStmt.setFloat(5, weapon.getDelay());
			insertStmt.setInt(6, weapon.getjob().getJobID());
			insertStmt.executeUpdate();
			return weapon;
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

	
	public Weapon delete(Weapon weapon) throws SQLException {
		String deleteWeapon = "DELETE FROM Weapon WHERE itemID=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteWeapon);
			deleteStmt.setInt(1, weapon.getItemID());
			int affectedRows = deleteStmt.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("No records available to delete for itemID=" + weapon.getItemID());
			}

			super.delete(weapon);

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
	
	public Weapon getWeaponByItemID(int itemID) throws SQLException {
		// To build an BlogUser object, we need the Persons record, too.
		String selectWeapon =
				"SELECT Weapon.itemID as itemID,itemName, maxStackSize,isSellable,unitPrice,itemLevel,requiredLevel,physicalDamage,autoAttack,delay,jobID " +
				"FROM Weapon INNER JOIN Item " +
				"  ON Weapon.itemID = Item.itemID " +
				"WHERE Weapon.itemID=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectWeapon);
			selectStmt.setInt(1, itemID);
			results = selectStmt.executeQuery();
			JobDao jobDao = JobDao.getInstance();
			if(results.next()) {
				String itemName = results.getString("itemName");
				int maxStackSize = results.getInt("maxStackSize");
				boolean isSellable = results.getBoolean("isSellable");
				Integer unitPrice = results.getObject("unitPrice", Integer.class);
				int itemLevel = results.getInt("itemLevel");
				int requiredLevel =results.getInt("requiredLevel");
				int physicalDamage = results.getInt("physicalDamage");
				float autoAttack = results.getFloat("autoAttack");
				float delay = results.getFloat("delay");
				int jobID = results.getInt("jobID");
				Job job = jobDao.getJobByJobID(jobID);
                
				Weapon weapon = new Weapon(itemID, itemName, maxStackSize, isSellable, unitPrice, 
						itemLevel,requiredLevel,physicalDamage,autoAttack,delay,job);
				return weapon;
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
	
	
	public List<Weapon> getWeaponByuserName(String userName) throws SQLException {
		// To build an BlogUser object, we need the Persons record, too.
		List<Weapon> weapons = new ArrayList<Weapon>();
		String selectWeapon =
			"SELECT w.itemID as itemID, i.itemName, i.maxStackSize, i.isSellable, i.unitPrice , i.itemLevel, w.requiredLevel, "
			+ "w.physicalDamage,w.autoAttack,w.delay,w.jobID "
			+ "FROM Weapon w "
			+ "INNER JOIN Item i on i.itemID = w.itemID "
			+ "INNER JOIN `Character` c on c.mainHandItemID = w.itemID "
			+ "INNER JOIN PlayerAccount pa on pa.accountID =c.accountID "
			+ "WHERE pa.userName = ?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		JobDao jobDao = JobDao.getInstance();
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectWeapon);
			selectStmt.setString(1, userName);
			results = selectStmt.executeQuery();
			while(results.next()) {
				int itemID = results.getInt("itemID");
				String itemName = results.getString("itemName");
				int maxStackSize = results.getInt("maxStackSize");
				boolean isSellable = results.getBoolean("isSellable");
				Integer unitPrice = results.getObject("unitPrice", Integer.class);
				int itemLevel = results.getInt("itemLevel");
				int requiredLevel = results.getInt("requiredLevel");
				int physicalDamage = results.getInt("physicalDamage");
				float autoAttack = results.getFloat("autoAttack");
				float delay = results.getInt("delay");
				int jobID = results.getInt("jobID");
				Job job = jobDao.getJobByJobID(jobID);
				
				Weapon weapon = new Weapon(itemID, itemName, maxStackSize, isSellable, unitPrice, 
						itemLevel,requiredLevel,physicalDamage,autoAttack, delay, job);
				
				weapons.add(weapon);
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
		return weapons;
	}
	
	public List<Weapon> getWeaponByCharacterIDANDJobID(int jobID, int characterID) throws SQLException {
		// To build an BlogUser object, we need the Persons record, too.
		List<Weapon> weapons = new ArrayList<Weapon>();
		String selectWeapon =
			"SELECT w.itemID as itemID, i2.itemName, i2.maxStackSize, i2.isSellable, i2.unitPrice , "
			+ "i2.itemLevel, w.requiredLevel,w.physicalDamage,w.autoAttack,w.delay, w.jobID "
			+ "FROM Inventory i "
			+ "INNER JOIN Weapon w on i.itemID =w.itemID "
			+ "INNER JOIN CharacterJobRelation cjr on cjr.characterID =i.characterID "
			+ "LEFT JOIN Item i2 on i.itemID =i2.itemID "
			+ "WHERE cjr.jobID =? and cjr.characterID=?  and cjr.jobLevel >= w.requiredLevel; ";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		JobDao jobDao = JobDao.getInstance();
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectWeapon);
			selectStmt.setInt(1, jobID);
			selectStmt.setInt(2, characterID);
			results = selectStmt.executeQuery();
			while(results.next()) {
				int itemID = results.getInt("itemID");
				String itemName = results.getString("itemName");
				int maxStackSize = results.getInt("maxStackSize");
				boolean isSellable = results.getBoolean("isSellable");
				Integer unitPrice = results.getObject("unitPrice", Integer.class);
				int itemLevel = results.getInt("itemLevel");
				int requiredLevel = results.getInt("requiredLevel");
				int physicalDamage = results.getInt("physicalDamage");
				float autoAttack = results.getFloat("autoAttack");
				float delay = results.getInt("delay");
				Job job = jobDao.getJobByJobID(jobID);
				
				Weapon weapon = new Weapon(itemID, itemName, maxStackSize, isSellable, unitPrice, 
						itemLevel,requiredLevel,physicalDamage,autoAttack, delay, job);
				
				weapons.add(weapon);
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
		return weapons;
	}
	
	public List<Weapon> getWeaponByJob(Job job) throws SQLException {
		// To build an BlogUser object, we need the Persons record, too.
		List<Weapon> weapons = new ArrayList<Weapon>();
		String selectWeapon =
			"SELECT Weapon.itemID as itemID,itemName, maxStackSize,isSellable,unitPrice,itemLevel,requiredLevel,physicalDamage,autoAttack,delay,jobID " +
			"FROM Weapon INNER JOIN Item ON Weapon.itemID = Item.itemID "
			+ "WHERE Weapon.jobID=?;" ;
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectWeapon);
			selectStmt.setInt(1, job.getJobID());
			results = selectStmt.executeQuery();
			while(results.next()) {
				int itemID = results.getInt("itemID");
				String itemName = results.getString("itemName");
				int maxStackSize = results.getInt("maxStackSize");
				boolean isSellable = results.getBoolean("isSellable");
				Integer unitPrice = results.getObject("unitPrice", Integer.class);
				int itemLevel = results.getInt("itemLevel");
				int requiredLevel = results.getInt("requiredLevel");
				int physicalDamage = results.getInt("physicalDamage");
				float autoAttack = results.getFloat("autoAttack");
				float delay = results.getInt("delay");
				
				Weapon weapon = new Weapon(itemID, itemName, maxStackSize, isSellable, unitPrice, 
						itemLevel,requiredLevel,physicalDamage,autoAttack, delay, job);
				
				weapons.add(weapon);
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
		return weapons;
	}
}
