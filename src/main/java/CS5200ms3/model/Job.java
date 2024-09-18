package CS5200ms3.model;

public class Job{
	protected int jobID;
	protected String jobName;
	protected int jobLevelCap;
	
	
   public Job(int jobID) {
		this.jobID = jobID;
	}

	//	insertion
	public Job(String jobName, int jobLevelCap) {
		this.jobName = jobName;
		this.jobLevelCap = jobLevelCap;
	}

//	retrieval
	public Job(int jobID, String jobName, int jobLevelCap) {
		this.jobID = jobID;
		this.jobName = jobName;
		this.jobLevelCap = jobLevelCap;
	}
	

	
	public int getJobID() {
		return jobID;
	}

	public void setJobID(int jobID) {
		this.jobID = jobID;
	}

	//able to set:
	//jobName, jobLevelCap
	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public int getJobLevelCap() {
		return jobLevelCap;
	}

	public void setJobLevelCap(int jobLevelCap) {
		this.jobLevelCap = jobLevelCap;
	}

	@Override
	public String toString() {
		return "Job [jobID=" + jobID + ", jobName=" + jobName + ", jobLevelCap=" + jobLevelCap + "]";
	}
	
	
}