package CS5200ms3.dal;

import CS5200ms3.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;




public class GearJobRelationDao {
	protected ConnectionManager connectionManager; 
	 
	private static GearJobRelationDao instance = null; 
	protected GearJobRelationDao() {
		connectionManager = new ConnectionManager(); 
	}
	public static GearJobRelationDao getInstance() {
		if(instance == null) {
			instance = new GearJobRelationDao(); 
		}
		return instance; 
	}

	
	public GearJobRelation create(GearJobRelation gearJobRelation) throws SQLException {
		String insertGearJobRelation = "INSERT INTO GearJobRelation(gearID, jobID) VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		
		
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertGearJobRelation);
			insertStmt.setInt(1, gearJobRelation.getGear().getItemID()); 
			insertStmt.setInt(2, gearJobRelation.getJob().getJobID());
			
			insertStmt.executeUpdate(); 
			
			return gearJobRelation;
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
	
	
	public GearJobRelation delete(GearJobRelation gearJobRelation) throws SQLException {

		String deleteGearJobRelation = "DELETE FROM GearJobRelation WHERE gearID=? AND jobID=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteGearJobRelation);
			deleteStmt.setInt(1, gearJobRelation.getGear().getItemID());
			deleteStmt.setInt(2, gearJobRelation.getJob().getJobID());
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
	
	public GearJobRelation getGearJobRealtionByGearAndJob(Gear gear, Job job) throws SQLException {
		String selectGearJobRelation = "SELECT gearID, jobID from GearJobRelation WHERE gearID=? AND jobID=?;";
		
		Connection connection = null;
		PreparedStatement selectStmt = null;	
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectGearJobRelation);	
			selectStmt.setInt(1, gear.getItemID());
			selectStmt.setInt(2, job.getJobID());
			
			results = selectStmt.executeQuery(); 
			
			GearDao gearDao = GearDao.getInstance(); 
			JobDao jobDao = JobDao.getInstance(); 
			if(results.next()) {
				int resultsGearID = results.getInt("gearID"); 
				int resultsJobID = results.getInt("jobID"); 
				
				Gear resultGear = gearDao.getGearByItemID(resultsGearID);
				Job resultJob = jobDao.getJobByJobID(resultsJobID);
				
				GearJobRelation gearJobRelation = new GearJobRelation(resultGear, resultJob);
				
				return gearJobRelation; 
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
	
	public List<GearJobRelation> getGearJobRelationsByGear(Gear gear) throws SQLException {
		List<GearJobRelation> gearJobRelations = new ArrayList<GearJobRelation>();
		String selectGearJobRelations =
			"SELECT gearID, jobID FROM GearJobRelation WHERE gearID=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectGearJobRelations);
			selectStmt.setInt(1, gear.getItemID());
			results = selectStmt.executeQuery();
			JobDao jobDao = JobDao.getInstance();
			while(results.next()) {
				int jobID = results.getInt("jobID");
				Job job = jobDao.getJobByJobID(jobID);
				
				GearJobRelation gearJobRelation = new GearJobRelation(gear, job);
				
				gearJobRelations.add(gearJobRelation);
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
		return gearJobRelations;
	}
	
	
	public List<GearJobRelation> getGearJobRelationsByJob(Job job) throws SQLException {
		List<GearJobRelation> gearJobRelations = new ArrayList<GearJobRelation>();
		String selectGearJobRelations =
			"SELECT gearID, jobID FROM GearJobRelation WHERE jobID=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectGearJobRelations);
			selectStmt.setInt(1, job.getJobID());
			results = selectStmt.executeQuery();
			GearDao gearDao = GearDao.getInstance();
			while(results.next()) {
				
				int gearID = results.getInt("gearID");
				Gear gear = gearDao.getGearByItemID(gearID);
				
				GearJobRelation gearJobRelation = new GearJobRelation(gear, job);
				
				gearJobRelations.add(gearJobRelation);
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
		return gearJobRelations;
	}
		
		
}