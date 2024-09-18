package CS5200ms3.dal;

import CS5200ms3.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JobDao {
	protected ConnectionManager connectionManager;
	
	//singleton setup, static instance, private constructor, getInstance()
	private static JobDao instance = null;
	protected JobDao() {
		connectionManager = new ConnectionManager();
	}
	public static JobDao getInstance() {
		if(instance == null) {
			instance = new JobDao();
		}
		return instance;
	}

	//END PROGRESS HERE
	public Job create(Job job) throws SQLException{
		// 
		String insertJob = "INSERT INTO Job(jobName, jobLevelCap) VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertJob, Statement.RETURN_GENERATED_KEYS);
			insertStmt.setString(1, job.getJobName());
			insertStmt.setInt(2, job.getJobLevelCap());
			insertStmt.executeUpdate();
			
			//auto-incremented userID handling
			//WAIT FOR WEAPON CLASS TO BE ADDED
			resultKey = insertStmt.getGeneratedKeys();
			int jobID = -1;
			if(resultKey.next()) {
				jobID = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			job.setJobID(jobID);
			return job;
			
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
	
	public Job delete(Job job) throws SQLException {

		String deleteJob = "DELETE FROM Job WHERE jobID=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteJob);
			deleteStmt.setInt(1, job.getJobID());
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
	
	public Job getJobByJobID(int jobID) throws SQLException{
		String selectJob = "SELECT jobID, jobName, jobLevelCap FROM Job WHERE jobID = ?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectJob);
			selectStmt.setInt(1, jobID);
			results = selectStmt.executeQuery();
			
			if (results.next()) {
				String resName = results.getString("jobName");
				int resLevelCap = results.getInt("jobLevelCap");
				Job job = new Job(jobID, resName, resLevelCap);
				return job;
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
	
	
	public List<Job> getJobsByJobLevelCap(int jobLevelCap) throws SQLException {
		List<Job> jobs = new ArrayList<Job>();
		String selectJobs =
			"SELECT jobID,jobName,jobLevelCap " +
			"FROM Job " +
			"WHERE jobLevelCap=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectJobs);
			selectStmt.setInt(1, jobLevelCap);
			results = selectStmt.executeQuery();
			while(results.next()) {
				int jobID = results.getInt("jobID");
				String jobName = results.getString("jobName");
				
				Job job = new Job(jobID,jobName, jobLevelCap);
				
				jobs.add(job);
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
		return jobs;
	}
}