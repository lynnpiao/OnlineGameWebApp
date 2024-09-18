package CS5200ms3.model;

public class Item {
	protected int itemID;
	protected String itemName;
	protected int maxStackSize;
	protected boolean isSellable;
	protected Integer unitPrice; 
	protected int itemLevel;

    public Item(int itemID, String itemName, int maxStackSize, boolean isSellable, Integer unitPrice, int itemLevel) {
    	this.itemID = itemID;
        this.itemName = itemName;
        this.maxStackSize = maxStackSize;
        this.isSellable = isSellable;
        this.unitPrice = unitPrice;
        this.itemLevel = itemLevel;
    }
    
    public Item(String itemName, int maxStackSize, boolean isSellable, Integer unitPrice, int itemLevel) {
        this.itemName = itemName;
        this.maxStackSize = maxStackSize;
        this.isSellable = isSellable;
        this.unitPrice = unitPrice;
        this.itemLevel = itemLevel;
    }
    

     public Item(int itemID) {
		this.itemID = itemID;
	}

	//Lin Adding constructor that can insert without adding unitPrice
	public Item(String itemName, int maxStackSize, boolean isSellable, int itemLevel) {
		this.itemName = itemName;
		this.maxStackSize = maxStackSize;
		this.isSellable = isSellable;
		this.itemLevel = itemLevel;
		this.unitPrice = null;
	}

	public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getMaxStackSize() {
        return maxStackSize;
    }

    public void setMaxStackSize(int maxStackSize) {
        this.maxStackSize = maxStackSize;
    }

    public boolean getIsSellable() {
        return isSellable;
    }

    public void setSellable(boolean sellable) {
        isSellable = sellable;
    }

    public Integer getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Integer unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getItemLevel() {
        return itemLevel;
    }

    public void setItemLevel(int itemLevel) {
        this.itemLevel = itemLevel;
    }

	@Override
	public String toString() {
		return "Item [itemID=" + itemID + ", itemName=" + itemName + ", maxStackSize=" + maxStackSize + ", isSellable="
				+ isSellable + ", unitPrice=" + unitPrice + ", itemLevel=" + itemLevel + "]";
	}
    
}
