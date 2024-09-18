package CS5200ms3.model;

/**
 * Character and Job Relations stored
 */
public class CharacterJobRelation {
	protected Character character;
	protected Job job;
	protected int jobLevel;
	protected boolean isPlayed;
	
	public CharacterJobRelation(Character character, Job job, int jobLevel, boolean isPlayed) {
		this.character = character;
		this.job = job;
		this.jobLevel = jobLevel;
		this.isPlayed = isPlayed;
	}
	
	public CharacterJobRelation(Character character, Job job) {
		this.character = character;
		this.job = job;
	}

	public Character getCharacter() {
		return character;
	}
	public void setCharacter(Character character) {
		this.character = character;
	}
	public Job getJob() {
		return job;
	}
	public void setJob(Job job) {
		this.job = job;
	}
	public int getJobLevel() {
		return jobLevel;
	}
	public void setJobLevel(int jobLevel) {
		this.jobLevel = jobLevel;
	}
	public boolean isPlayed() {
		return isPlayed;
	}
	public void setPlayed(boolean isPlayed) {
		this.isPlayed = isPlayed;
	}

	@Override
	public String toString() {
		return "CharacterJobRelation [character=" + character + ", job=" + job + ", jobLevel=" + jobLevel
				+ ", isPlayed=" + isPlayed + "]";
	}
	
	
	
}
