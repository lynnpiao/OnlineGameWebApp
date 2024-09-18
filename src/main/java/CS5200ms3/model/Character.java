package CS5200ms3.model;

public class Character {
	protected int characterID;
	protected PlayerAccount playerAccount;
	protected String firstName;
	protected String lastName;
	//CHANGE TO TYPE WEAPON
	protected Weapon mainHandItem;
	
	
//	insertion	
	public Character(PlayerAccount playerAccount, String firstName, String lastName, Weapon mainHandItem) {
		this.playerAccount = playerAccount;
		this.firstName = firstName;
		this.lastName = lastName;
		this.mainHandItem = mainHandItem;
	}


//	retrieval
	public Character(int characterID, PlayerAccount playerAccount, String firstName, String lastName, Weapon mainHandItem) {
		this.characterID = characterID;
		this.playerAccount = playerAccount;
		this.firstName = firstName;
		this.lastName = lastName;
		this.mainHandItem = mainHandItem;
	}
	

	public Character(int characterID) {
	this.characterID = characterID;
	}

	
	public int getCharacterID() {
		return characterID;
	}

	public void setCharacterID(int characterID) {
		this.characterID = characterID;
	}

	public PlayerAccount getPlayerAccount() {
		return playerAccount;
	}

	public void setPlayerAccount(PlayerAccount playerAccount) {
		this.playerAccount = playerAccount;
	}

	//able to set:
	//firstName, lastName, mainHandItemID
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Weapon getMainHandItem() {
		return mainHandItem;
	}

	public void setMainHandItemID(Weapon mainHandItem) {
		this.mainHandItem = mainHandItem;
	}

	@Override
	public String toString() {
		return "Character [characterID=" + characterID + ", playerAccount=" + playerAccount + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", mainHandItem=" + mainHandItem + "]";
	}

	
}