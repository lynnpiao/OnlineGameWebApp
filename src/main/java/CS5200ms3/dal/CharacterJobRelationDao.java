package CS5200ms3.dal;

import CS5200ms3.model.*;
import CS5200ms3.model.Character;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class CharacterJobRelationDao {
	protected ConnectionManager connectionManager;

	private static CharacterJobRelationDao instance = null;
	protected CharacterJobRelationDao() {
		connectionManager = new ConnectionManager();
	}
	public static CharacterJobRelationDao getInstance() {
		if(instance == null) {
			instance = new CharacterJobRelationDao();
		}
		return instance;
	}

	public CharacterJobRelation create(CharacterJobRelation characterJobRelation) throws SQLException {
		String insertCharacterJobRelation =
			"INSERT INTO CharacterJobRelation(characterID, jobID, jobLevel, isPlayed) VALUES(?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertCharacterJobRelation);
			// BlogPosts has an auto-generated key. So we want to retrieve that key.
			insertStmt.setInt(1, characterJobRelation.getCharacter().getCharacterID());
			// Note: for the sake of simplicity, just set Picture to null for now.
			insertStmt.setInt(2, characterJobRelation.getJob().getJobID());
			insertStmt.setInt(3, characterJobRelation.getJobLevel());
			insertStmt.setBoolean(4, characterJobRelation.isPlayed());
			insertStmt.executeUpdate();
			
			// Retrieve the auto-generated key and set it, so it can be used by the caller.
			// For more details, see:
			// http://dev.mysql.com/doc/connector-j/en/connector-j-usagenotes-last-insert-id.html

			
			// Note 1: if this was an UPDATE statement, then the person fields should be
			// updated before returning to the caller.
			// Note 2: there are no auto-generated keys, so no update to perform on the
			// input param person.
			return characterJobRelation;
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

	/**
	 * Update the content of the Weapon instance.
	 * This runs a UPDATE statement.
	 */
	public CharacterJobRelation updateJobLevelAndisPlayed(CharacterJobRelation characterJobRelation, int newJobLevel, boolean newisPlayed) throws SQLException {
		String updateCharacterJobRelation = "UPDATE CharacterJobRelation SET jobLevel=?, isPlayed=? WHERE characterID=? AND jobID=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateCharacterJobRelation);
			updateStmt.setInt(1, newJobLevel);
			updateStmt.setBoolean(2, newisPlayed);
			updateStmt.setInt(3, characterJobRelation.getCharacter().getCharacterID());
			updateStmt.setInt(4, characterJobRelation.getJob().getJobID());
			
			updateStmt.executeUpdate();

			// Update the blogPost param before returning to the caller.
			characterJobRelation.setJobLevel(newJobLevel);
			return characterJobRelation;
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
	public CharacterJobRelation delete(CharacterJobRelation characterJobRelation) throws SQLException {
		// Note: BlogComments has a fk constraint on BlogPosts with the reference option
		// ON DELETE CASCADE. So this delete operation will delete all the referencing
		// BlogComments.
		String deleteCharacterJobRelation = "DELETE FROM CharacterJobRelation WHERE characterID=? AND jobID=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteCharacterJobRelation);
			deleteStmt.setInt(1, characterJobRelation.getCharacter().getCharacterID());
			deleteStmt.setInt(2, characterJobRelation.getJob().getJobID());
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

	/**
	 * Get the WeaponAttributeRelation record by fetching it from your MySQL instance.
	 * This runs a SELECT statement and returns a single WeaponAttributeRelation instance.
	 */
	public CharacterJobRelation getCharacterJobRelationByCharacterAndJob(Character character, Job job) throws SQLException {
		String selectCharacterJobRelation =
			"SELECT characterID, jobID, jobLevel, isPlayed " +
			"FROM CharacterJobRelation " +
			"WHERE characterID=? AND jobID=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectCharacterJobRelation);
			selectStmt.setInt(1, character.getCharacterID());
			selectStmt.setInt(2, job.getJobID());
			results = selectStmt.executeQuery();
			
			if(results.next()) {
				
				int jobLevel = results.getInt("jobLevel");
				boolean isPlayed = results.getBoolean("isPlayed");
				
				CharacterJobRelation characterJobRelation = new CharacterJobRelation(character, job, jobLevel, isPlayed);
				return characterJobRelation;
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
	 * Get the all the BlogPosts for a user.
	 */
	public List<CharacterJobRelation> getCharacterJobRelationsByCharacter(Character character) throws SQLException {
		List<CharacterJobRelation> characterJobRelations = new ArrayList<CharacterJobRelation>();
		String selectCharacterJobRelations =
			"SELECT CharacterJobRelation.characterID, jobID, jobLevel, isPlayed " +
			"FROM CharacterJobRelation INNER JOIN `Character` "
			+ "ON CharacterJobRelation.characterID=`Character`.characterID "
			+ "WHERE CharacterJobRelation.characterID=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectCharacterJobRelations);
			selectStmt.setInt(1, character.getCharacterID());
			results = selectStmt.executeQuery();
			JobDao jobDao = JobDao.getInstance();
			while(results.next()) {
				int jobID = results.getInt("jobID");
				Job job = jobDao.getJobByJobID(jobID);
				
				int jobLevel = results.getInt("jobLevel");
				boolean isPlayed = results.getBoolean("isPlayed");
				
				CharacterJobRelation characterJobRelation = new CharacterJobRelation(character, job, jobLevel, isPlayed);
				
				characterJobRelations.add(characterJobRelation);
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
		return characterJobRelations;
	}
	
	
	public List<CharacterJobRelation> getCharacterJobRelationsByJob(Job job) throws SQLException {
		List<CharacterJobRelation> characterJobRelations = new ArrayList<CharacterJobRelation>();
		String selectCharacterJobRelations =
			"SELECT characterID, CharacterJobRelation.jobID, jobLevel, isPlayed  " +
			"FROM CharacterJobRelation INNER JOIN Job "
			+ "ON CharacterJobRelation.jobID=Job.jobID " +
			"WHERE CharacterJobRelation.jobID=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectCharacterJobRelations);
			selectStmt.setInt(1, job.getJobID());
			results = selectStmt.executeQuery();
			CharacterDao characterDao = CharacterDao.getInstance();
			while(results.next()) {
				
				int characterID = results.getInt("characterID");
				Character character = characterDao.getCharacterByID(characterID);
				
				int jobLevel = results.getInt("jobLevel");
				boolean isPlayed = results.getBoolean("isPlayed");
				
				CharacterJobRelation characterJobRelation = new CharacterJobRelation(character, job, jobLevel, isPlayed);
				
				characterJobRelations.add(characterJobRelation);
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
		return characterJobRelations;
	}
}
