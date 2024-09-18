package CS5200ms3.model;

public class Inventory {
	protected Character character;
	protected int inventoryPosition;
	protected Item item; // used itemID before 
	protected int itemQuantity;
	
	
	public Inventory(Character character, int inventoryPosition, Item item, int itemQuantity) {
		this.character = character;
		this.inventoryPosition = inventoryPosition;
		this.item = item;
		this.itemQuantity = itemQuantity;
	}


	public Inventory(Character character, int inventoryPosition) {
		this.character = character;
		this.inventoryPosition = inventoryPosition;
	}


	public Character getCharacter() {
		return character;
	}


	public void setCharacter(Character character) {
		this.character = character;
	}


	public int getInventoryPosition() {
		return inventoryPosition;
	}


	public void setInventoryPosition(int inventoryPosition) {
		this.inventoryPosition = inventoryPosition;
	}


	public Item getItem() {
		return item;
	}


	public void setItem(Item item) {
		this.item = item;
	}


	public int getItemQuantity() {
		return itemQuantity;
	}


	public void setItemQuantity(int itemQuantity) {
		this.itemQuantity = itemQuantity;
	}


	@Override
	public String toString() {
		return "Inventory [character=" + character + ", inventoryPosition=" + inventoryPosition + ", item=" + item
				+ ", itemQuantity=" + itemQuantity + "]";
	}
	
	
	
	
	
}