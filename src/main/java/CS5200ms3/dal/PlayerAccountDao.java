package CS5200ms3.dal;

import CS5200ms3.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PlayerAccountDao {
	protected ConnectionManager connectionManager;
	
	//singleton setup, static instance, private constructor, getInstance()
	private static PlayerAccountDao instance = null;
	protected PlayerAccountDao() {
		connectionManager = new ConnectionManager();
	}
	public static PlayerAccountDao getInstance() {
		if(instance == null) {
			instance = new PlayerAccountDao();
		}
		return instance;
	}

	
	public PlayerAccount create(PlayerAccount playerAccount) throws SQLException{
		String insertPlayerAccount = "INSERT INTO PlayerAccount(userName, emailAddress) VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet autoIncrementAccountIDs = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertPlayerAccount, Statement.RETURN_GENERATED_KEYS);
			insertStmt.setString(1, playerAccount.getUserName());
			insertStmt.setString(2, playerAccount.getEmailAddress());
			insertStmt.executeUpdate();
			
			//auto-incremented userID handling
			autoIncrementAccountIDs = insertStmt.getGeneratedKeys();
			if (autoIncrementAccountIDs.next()) {
				playerAccount = new PlayerAccount(autoIncrementAccountIDs.getInt(1), playerAccount.getUserName(), playerAccount.getEmailAddress());
	        } else {
	            throw new SQLException("Cannot create PlayerAccount, no accountID found.");
	        }
			return playerAccount;
			
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
			if(autoIncrementAccountIDs != null) {
				autoIncrementAccountIDs.close();
			}
		}
	}
	
	public PlayerAccount updatePlayerAccountEmail(PlayerAccount playerAccount, String newEmail) throws SQLException{
		String updateEmail = "UPDATE PlayerAccount SET email=? WHERE accountID=?;";
		//FINISH THE SQL QUERY
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateEmail);
			updateStmt.setString(1, newEmail);
			updateStmt.setInt(2, playerAccount.getAccountID());
			int affectedRows = updateStmt.executeUpdate();
			
			if(affectedRows == 0) {
				throw new SQLException("No valid PlayerAccount records to update");
			}
			// Update the rating param before returning to the caller.
			playerAccount.setEmailAddress(newEmail);
			return playerAccount;
			
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
	
	public PlayerAccount updateUserName(PlayerAccount playerAccount, String newUserName) throws SQLException {
		String updatePlayerAccount = "UPDATE PlayerAccount SET userName=? WHERE accountID=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updatePlayerAccount);
			updateStmt.setString(1, newUserName);
			updateStmt.setInt(2, playerAccount.getAccountID());
			updateStmt.executeUpdate();
			
			// Update the person param before returning to the caller.
			playerAccount.setUserName(newUserName);
			return playerAccount;
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
	
	public PlayerAccount delete(PlayerAccount playerAccount) throws SQLException {
		String deletePlayerAccount = "DELETE FROM PlayerAccount WHERE accountID=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deletePlayerAccount);
			deleteStmt.setInt(1, playerAccount.getAccountID());
			deleteStmt.executeUpdate();

			// Return null so the caller can no longer operate on the Persons instance.
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
	
	public PlayerAccount getPlayerByAccountID(int accountID) throws SQLException{
		String selectAccount = "SELECT accountID, userName, emailAddress FROM PlayerAccount WHERE accountID = ?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectAccount);
			selectStmt.setInt(1, accountID);
			results = selectStmt.executeQuery();
			
			if (results.next()) {
				String resUsername = results.getString("username");
				String resEmail = results.getString("emailAddress");
				PlayerAccount playerAccount = new PlayerAccount(accountID, resUsername, resEmail);
				return playerAccount;
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