package CS5200ms3.model;

public class Consumable extends Item{
    private String description;

    public Consumable(int itemID, String itemName, int maxStackSize, boolean isSellable, Integer unitPrice, int itemLevel, String description) {
    	super(itemID,itemName,maxStackSize,isSellable, unitPrice,itemLevel);
        this.description = description;
    }
    
    public Consumable(String itemName, int maxStackSize, boolean isSellable, Integer unitPrice, int itemLevel, String description) {
    	super(itemName,maxStackSize,isSellable, unitPrice,itemLevel);
        this.description = description;
    }

    public Consumable(String itemName, int maxStackSize, boolean isSellable, int itemLevel, String description) {
		super(itemName, maxStackSize, isSellable, itemLevel);
		this.description = description;
	}
    

	public Consumable(int itemID) {
		super(itemID);
	}

	public Consumable(int itemID, String description) {
		super(itemID);
		this.description = description;
	}

	public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

	@Override
	public String toString() {
		return "Consumable [description=" + description + "]";
	}
    
    
}
