package CS5200ms3.model;

public class PlayerAccount {
	protected int accountID;
	protected String userName;
	protected String emailAddress;
	
	
	
    public PlayerAccount() {
		super();
	}

//	insertion
	public PlayerAccount(String userName, String emailAddress) {
		this.userName = userName;
		this.emailAddress = emailAddress;
	}
	
   //	retrieval
	public PlayerAccount(int accountID, String userName, String emailAddress) {
		this.accountID = accountID;
		this.userName = userName;
		this.emailAddress = emailAddress;
	}
	
	// primary key constructor
	public PlayerAccount(int accountID) {
		this.accountID = accountID;
	}

	public int getAccountID() {
		return accountID;
	}


	//no setter for auto-increment fields
	
	//able to set:
	//username, email
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	@Override
	public String toString() {
		return "PlayerAccount [accountID=" + accountID + ", userName=" + userName + ", emailAddress=" + emailAddress
				+ "]";
	}
	
	
	
	
}