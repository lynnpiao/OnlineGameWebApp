package CS5200ms3.model; 

public class GearJobRelation {
	protected Gear gear; 
	protected Job job;
	
	
	public GearJobRelation(Gear gear, Job job) {
		this.gear = gear;
		this.job = job;
	}
	public Gear getGear() {
		return gear;
	}
	public void setGear(Gear gear) {
		this.gear = gear;
	}
	public Job getJob() {
		return job;
	}
	public void setJob(Job job) {
		this.job = job;
	}
	@Override
	public String toString() {
		return "GearJobRelation [getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	
	
	
	
}
	
	
