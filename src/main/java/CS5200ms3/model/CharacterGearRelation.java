package CS5200ms3.model; 

public class CharacterGearRelation {
	protected Character character; 
	protected String slotType; 
	protected Gear gear;
	
	public CharacterGearRelation(Character character, String slotType, Gear gear) {
		this.character = character;
		this.slotType = slotType;
		this.gear = gear;
	}
	

	public CharacterGearRelation(Character character, String slotType) {
		this.character = character;
		this.slotType = slotType;
	}


	public Character getCharacter() {
		return character;
	}

	public void setCharacter(Character character) {
		this.character = character;
	}

	public String getSlotType() {
		return slotType;
	}

	public void setSlotType(String slotType) {
		this.slotType = slotType;
	}

	public Gear getGear() {
		return gear;
	}

	public void setGear(Gear gear) {
		this.gear = gear;
	}


	@Override
	public String toString() {
		return "CharacterGearRelation [character=" + character + ", slotType=" + slotType + "]";
	} 
	
	
}