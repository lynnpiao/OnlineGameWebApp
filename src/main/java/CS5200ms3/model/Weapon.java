package CS5200ms3.model;

public class Weapon extends Item {
	protected int requiredLevel;
	protected int physicalDamage;
	protected float autoAttack;
	protected float delay;
	protected Job job;

    public Weapon(int itemID, String itemName, int maxStackSize, boolean isSellable, Integer unitPrice, int itemLevel, int requiredLevel, int physicalDamage, float autoAttack, float delay,Job job) {
    	super(itemID,itemName,maxStackSize,isSellable, unitPrice,itemLevel);
        this.requiredLevel = requiredLevel;
        this.physicalDamage = physicalDamage;
        this.autoAttack = autoAttack;
        this.delay = delay;
        this.job = job;
    }
    
    public Weapon(String itemName, int maxStackSize, boolean isSellable, Integer unitPrice, int itemLevel, int requiredLevel, int physicalDamage, float autoAttack, float delay,Job job) {
    	super(itemName,maxStackSize,isSellable, unitPrice,itemLevel);
        this.requiredLevel = requiredLevel;
        this.physicalDamage = physicalDamage;
        this.autoAttack = autoAttack;
        this.delay = delay;
        this.job = job;
    }
    

    public Weapon(int itemID, String itemName, int maxStackSize, boolean isSellable, Integer unitPrice, int itemLevel) {
		super(itemID, itemName, maxStackSize, isSellable, unitPrice, itemLevel);
	}


	public Weapon(int itemID, int requiredLevel, int physicalDamage, float autoAttack, float delay, Job job) {
		super(itemID);
		this.requiredLevel = requiredLevel;
		this.physicalDamage = physicalDamage;
		this.autoAttack = autoAttack;
		this.delay = delay;
		this.job = job;
	}

	public Weapon(String itemName, int maxStackSize, boolean isSellable, int itemLevel, int requiredLevel,
			int physicalDamage, float autoAttack, float delay, Job job) {
		super(itemName, maxStackSize, isSellable, itemLevel);
		this.requiredLevel = requiredLevel;
		this.physicalDamage = physicalDamage;
		this.autoAttack = autoAttack;
		this.delay = delay;
		this.job = job;
	}

	public int getRequiredLevel() {
        return requiredLevel;
    }

    public void setRequiredLevel(int requiredLevel) {
        this.requiredLevel = requiredLevel;
    }

    public int getPhysicalDamage() {
        return physicalDamage;
    }

    public void setPhysicalDamage(int physicalDamage) {
        this.physicalDamage = physicalDamage;
    }

    public float getAutoAttack() {
        return autoAttack;
    }

    public void setAutoAttack(float autoAttack) {
        this.autoAttack = autoAttack;
    }

    public float getDelay() {
        return delay;
    }

    public void setDelay(float delay) {
        this.delay = delay;
    }
    
    public Job getjob() {
        return job;
    }

    public void setjobID(Job job) {
        this.job = job;
    }

	@Override
	public String toString() {
		return "Weapon [requiredLevel=" + requiredLevel + ", physicalDamage=" + physicalDamage + ", autoAttack="
				+ autoAttack + ", delay=" + delay + ", job=" + job + "]";
	}

    
}
