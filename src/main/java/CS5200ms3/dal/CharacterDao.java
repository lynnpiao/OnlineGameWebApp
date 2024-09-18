package CS5200ms3.dal;

import CS5200ms3.model.*;
import CS5200ms3.model.Character;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CharacterDao {
	protected ConnectionManager connectionManager;
	
	//singleton setup, static instance, private constructor, getInstance()
	private static CharacterDao instance = null;
	protected CharacterDao() {
		connectionManager = new ConnectionManager();
	}
	public static CharacterDao getInstance() {
		if(instance == null) {
			instance = new CharacterDao();
		}
		return instance;
	}

	//END PROGRESS HERE
	public Character create(Character character) throws SQLException{
		String insertCharacter = "INSERT INTO `Character`(accountID,firstName,lastName,mainHandItemID) VALUES(?,?,?,?);" ;
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertCharacter,
					Statement.RETURN_GENERATED_KEYS);
			insertStmt.setInt(1, character.getPlayerAccount().getAccountID());
			insertStmt.setString(2, character.getFirstName());
			insertStmt.setString(3, character.getLastName());
			insertStmt.setInt(4, character.getMainHandItem().getItemID());
			insertStmt.executeUpdate();
				
				// Retrieve the auto-generated key and set it, so it can be used by the caller.
				// For more details, see:
				// http://dev.mysql.com/doc/connector-j/en/connector-j-usagenotes-last-insert-id.html
			resultKey = insertStmt.getGeneratedKeys();
			int characterID = -1;
			if(resultKey.next()) {
				characterID = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			character.setCharacterID(characterID);
			return character;
			
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
			if(resultKey != null) {
				resultKey.close();
			}
		}
	}
	
	public Character updateName(Character character, String newFirstName, String newLastName) throws SQLException {
		String updateName = "UPDATE `Character` SET firstName=?, lastName=? WHERE characterID=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateName);
			updateStmt.setString(1, newFirstName);
			updateStmt.setString(2, newLastName);
			updateStmt.setInt(3, character.getCharacterID());
			updateStmt.executeUpdate();
			character.setFirstName(newFirstName);
			character.setLastName(newLastName);
			return character;
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

	public Character updateMainHandItem(Character character, Weapon weapon) throws SQLException {
		String updateMainHandItem = "UPDATE `Character` SET mainHandItemID=? WHERE characterID=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateMainHandItem);
			updateStmt.setInt(1, weapon.getItemID());
			updateStmt.setInt(2, character.getCharacterID());
			updateStmt.executeUpdate();

			character.setMainHandItemID(weapon);
			return character;
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
	
	/**
	 * Delete the BlogPosts instance.
	 * This runs a DELETE statement.
	 */
	public Character delete(Character character) throws SQLException {
		// Note: BlogComments has a fk constraint on BlogPosts with the reference option
		// ON DELETE CASCADE. So this delete operation will delete all the referencing
		// BlogComments.
		String deleteCharacter = "DELETE FROM `Character` WHERE characterID=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteCharacter);
			deleteStmt.setInt(1, character.getCharacterID());
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
	
	
	public Character getCharacterByID(int characterID) throws SQLException {
		String selectCharacter =
			"SELECT characterID,accountID,firstName,lastName,mainHandItemID " +
			"FROM `Character` " +
			"WHERE characterID=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectCharacter);
			selectStmt.setInt(1, characterID);
			results = selectStmt.executeQuery();
			PlayerAccountDao playerAccountDao  = PlayerAccountDao.getInstance();
			WeaponDao weaponDao  = WeaponDao.getInstance();
			if(results.next()) {
				int accountID = results.getInt("accountID");
				PlayerAccount account = playerAccountDao.getPlayerByAccountID(accountID);
				
				String firstName = results.getString("firstName");
				String lastName = results.getString("lastName");
				
				int mainHandItemID = results.getInt("mainHandItemID");
				Weapon weapon = weaponDao.getWeaponByItemID(mainHandItemID);
				
				Character character = new Character(characterID,account,firstName,lastName,weapon);
				return character;
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
	 * Get the all the Character for a player account.
	 */
	public List<Character> getCharactersByPlayerAccount(PlayerAccount account) throws SQLException {
		List<Character> characters = new ArrayList<Character>();
		String selectCharacters =
			"SELECT characterID, PlayerAccount.accountID, firstName, lastName, mainHandItemID " +
			"FROM `Character` INNER JOIN PlayerAccount ON `Character`.accountID = PlayerAccount.accountID "
			+ "WHERE PlayerAccount.accountID=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectCharacters);
			selectStmt.setInt(1, account.getAccountID());
			results = selectStmt.executeQuery();
			WeaponDao weaponDao  = WeaponDao.getInstance();
			while(results.next()) {
				int characterID = results.getInt("characterID");
				String firstName = results.getString("firstName");
				String lastName = results.getString("lastName");
				
				int mainHandItemID = results.getInt("mainHandItemID");
				Weapon weapon = weaponDao.getWeaponByItemID(mainHandItemID);
				
				Character character = new Character(characterID,account,firstName,lastName,weapon);
				
				characters.add(character);
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
		return characters;
	}
	
	public List<Character> getCharactersByuserName(String userName) throws SQLException {
		List<Character> characters = new ArrayList<Character>();
		String selectCharacters =
			"SELECT characterID, `Character`.accountID, firstName, lastName, mainHandItemID " +
			"FROM `Character` INNER JOIN PlayerAccount ON `Character`.accountID = PlayerAccount.accountID "
			+ "WHERE PlayerAccount.userName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectCharacters);
			selectStmt.setString(1, userName);
			results = selectStmt.executeQuery();
			WeaponDao weaponDao  = WeaponDao.getInstance();
			PlayerAccountDao playerAccountDao  = PlayerAccountDao.getInstance();
			while(results.next()) {
				int characterID = results.getInt("characterID");
				int accountID = results.getInt("accountID");
				PlayerAccount account = playerAccountDao.getPlayerByAccountID(accountID);
				
				String firstName = results.getString("firstName");
				String lastName = results.getString("lastName");
				
				int mainHandItemID = results.getInt("mainHandItemID");
				Weapon weapon = weaponDao.getWeaponByItemID(mainHandItemID);
				Character character = new Character(characterID,account,firstName,lastName,weapon);
				characters.add(character);
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
		return characters;
	}
	
	public List<Character> getCharactersByWeapon(Weapon weapon) throws SQLException {
		List<Character> characters = new ArrayList<Character>();
		String selectCharacters =
			"SELECT characterID, accountID, firstName, lastName, mainHandItemID " +
			"FROM `Character` INNER JOIN Weapon "
			+ "ON `Character`.mainHandItemID=Weapon.itemID "
			+ "WHERE mainHandItemID=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectCharacters);
			selectStmt.setInt(1, weapon.getItemID());
			results = selectStmt.executeQuery();
			PlayerAccountDao playerAccountDao  = PlayerAccountDao.getInstance();
			while(results.next()) {
				
				int characterID = results.getInt("characterID");
				int accountID = results.getInt("accountID");
				PlayerAccount account = playerAccountDao.getPlayerByAccountID(accountID);
				
				String firstName = results.getString("firstName");
				String lastName = results.getString("lastName");
				
				Character character = new Character(characterID,account,firstName,lastName,weapon);
				
				characters.add(character);
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
		return characters;
	}
}