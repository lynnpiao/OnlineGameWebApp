package CS5200ms3.model;

public class Gear extends Item{
    private String slotType;
    private int requiredLevel;


    public Gear(int itemID, String itemName, int maxStackSize, boolean isSellable, Integer unitPrice, int itemLevel, String slotType, int requiredLevel) {
    	super(itemID,itemName,maxStackSize,isSellable, unitPrice,itemLevel);
        this.slotType = slotType;
        this.requiredLevel = requiredLevel;

    }
    
    public Gear(String itemName, int maxStackSize, boolean isSellable, Integer unitPrice, int itemLevel, String slotType, int requiredLevel) {
    	super(itemName,maxStackSize,isSellable, unitPrice,itemLevel);
        this.slotType = slotType;
        this.requiredLevel = requiredLevel;
    }
    

    public Gear(String itemName, int maxStackSize, boolean isSellable, int itemLevel, String slotType,
			int requiredLevel) {
		super(itemName, maxStackSize, isSellable, itemLevel);
		this.slotType = slotType;
		this.requiredLevel = requiredLevel;
	}

	public Gear(int itemID) {
		super(itemID);
	}
	

	public Gear(int itemID, String slotType, int requiredLevel) {
		super(itemID);
		this.slotType = slotType;
		this.requiredLevel = requiredLevel;
	}

	public String getSlotType() {
        return slotType;
    }

    public void setSlotType(String slotType) {
        this.slotType = slotType;
    }

    public int getRequiredLevel() {
        return requiredLevel;
    }

    public void setRequiredLevel(int requiredLevel) {
        this.requiredLevel = requiredLevel;
    }

	@Override
	public String toString() {
		return "Gear [slotType=" + slotType + ", requiredLevel=" + requiredLevel + "]";
	}
    

}
