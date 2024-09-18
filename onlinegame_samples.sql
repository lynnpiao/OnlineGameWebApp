DROP SCHEMA IF EXISTS CS5200Project;
CREATE SCHEMA CS5200Project;
USE CS5200Project;

CREATE TABLE `PlayerAccount`(
	accountID INT AUTO_INCREMENT,
    userName VARCHAR(255) NOT NULL,
    emailAddress VARCHAR(255) NOT NULL,
    CONSTRAINT pk_PlayerAccount_accountID PRIMARY KEY (accountID),
    CONSTRAINT uq_PlayerAccount_userName UNIQUE(userName),
    CONSTRAINT uq_PlayerAccount_emailAddress UNIQUE(emailAddress)
);

CREATE TABLE `Attribute`(
	attributeName VARCHAR(255),
    CONSTRAINT pk_Attribute_attributeName PRIMARY KEY (attributeName)
);

CREATE TABLE `Slot`(
	slotType VARCHAR(255),
    CONSTRAINT pk_Slot_slotType PRIMARY KEY (slotType)
);

CREATE TABLE `Item`(
	itemID INT AUTO_INCREMENT,
    itemName VARCHAR(255) NOT NULL,
    maxStackSize INT NOT NULL,
    isSellable BOOL DEFAULT FALSE NOT NULL,
    unitPrice INT,
    itemLevel INT NOT NULL,
    CONSTRAINT pk_Item_itemID PRIMARY KEY (itemID)
    );

CREATE TABLE `Job` (
	jobID INT AUTO_INCREMENT,
    jobName VARCHAR (255) NOT NULL,
    jobLevelCap INT DEFAULT 90 NOT NULL,
    CONSTRAINT pk_Job_jobID PRIMARY KEY (jobID)
);

CREATE TABLE `Weapon`(
	itemID INT,
    requiredLevel INT NOT NULL,
	physicalDamage INT NOT NULL,
    autoAttack FLOAT NOT NULL,
    delay FLOAT NOT NULL,
    jobID INT NOT NULL,
    CONSTRAINT pk_Weapon_itemID PRIMARY KEY (itemID),
    CONSTRAINT fk_Weapon_itemID FOREIGN KEY (itemID) REFERENCES `Item` (itemID)
    ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_Weapon_jobID FOREIGN KEY (jobID) REFERENCES `Job` (jobID)
    ON UPDATE CASCADE ON DELETE CASCADE
    );

CREATE TABLE `WeaponAttributeRelation`(
	weaponID INT,
    attributeName VARCHAR(255),
    bonusNumber INT NOT NULL,
    CONSTRAINT pk_WeaponAttributeRelation_weaponID_attributeName PRIMARY KEY(weaponID, attributeName),
    CONSTRAINT fk_WeaponAttributeRelation_weaponID FOREIGN KEY (weaponID) REFERENCES `Weapon` (itemID)
    ON UPDATE CASCADE ON DELETE CASCADE, 
    CONSTRAINT fk_WeaponAttributeRelation_attributeName FOREIGN KEY (attributeName) REFERENCES `Attribute` (attributeName)
    ON UPDATE CASCADE ON DELETE CASCADE
    );

CREATE TABLE `Gear`(
	itemID INT,
    slotType VARCHAR(255),
    requiredLevel INT NOT NULL,
    CONSTRAINT pk_Gear_itemID PRIMARY KEY (itemID),
    CONSTRAINT fk_Gear_itemID FOREIGN KEY (itemID) REFERENCES `Item` (itemID)
	ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_Gear_slotType FOREIGN KEY (slotType) REFERENCES `Slot` (slotType)
	ON UPDATE CASCADE ON DELETE CASCADE
    );

CREATE TABLE `GearAttributeRelation`(
	gearID INT,
    attributeName VARCHAR(255),
    bonusNumber INT NOT NULL,
    CONSTRAINT pk_GearAttributeRelation_gearID_attributeName PRIMARY KEY(gearID, attributeName),
    CONSTRAINT fk_GearAttributeRelation_gearID FOREIGN KEY (gearID) REFERENCES `Gear` (itemID)
    ON UPDATE CASCADE ON DELETE CASCADE, 
    CONSTRAINT fk_GearAttributeRelation_attributeName FOREIGN KEY (attributeName) REFERENCES `Attribute` (attributeName)
    ON UPDATE CASCADE ON DELETE CASCADE
    );

CREATE TABLE `Consumable`(
	itemID INT,
    description VARCHAR(255),
    CONSTRAINT pk_Consumable_itemID PRIMARY KEY (itemID),
    CONSTRAINT fk_Consumable_itemID FOREIGN KEY (itemID) REFERENCES `Item` (itemID)
    ON UPDATE CASCADE ON DELETE CASCADE
    );

CREATE TABLE `ConsumableAttributeRelation`(
	consumableID INT,
    attributeName VARCHAR(255),
    bonusPercentage FLOAT NOT NULL,
    bonusCap INT,
    CONSTRAINT pk_ConsumableAttributeRelation_consumableID_attributeName PRIMARY KEY(consumableID, attributeName),
    CONSTRAINT fk_ConsumableAttributeRelation_consumableID FOREIGN KEY (consumableID) REFERENCES  `Consumable` (itemID)
    ON UPDATE CASCADE ON DELETE CASCADE, 
    CONSTRAINT fk_ConsumableAttributeRelation_attributeName FOREIGN KEY(attributeName) REFERENCES  `Attribute` (attributeName)
    ON UPDATE CASCADE ON DELETE CASCADE
    );

CREATE TABLE `Character`(
	characterID INT AUTO_INCREMENT,
    accountID INT NOT NULL,
    firstName VARCHAR(255),
    lastName VARCHAR(255),
    mainHandItemID INT DEFAULT 1 NOT NULL,
    CONSTRAINT pk_Character_characterID PRIMARY KEY (characterID),
    CONSTRAINT fk_Character_accountID FOREIGN KEY (accountID) REFERENCES `PlayerAccount`(accountID) 
    ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT uq_Charater_firstName_lastName UNIQUE(firstName, lastName),
    CONSTRAINT fk_Character_mainHandItemID FOREIGN KEY (mainHandItemID) REFERENCES `Weapon`(itemID)
    ON UPDATE CASCADE
    -- don’t want null or default, switch id on equip later on in implementation, can also use ON DELETE RESTRICT
);

CREATE TABLE `CharacterAttributeRelation`(
	characterID INT,
	attributeName VARCHAR(255),
    attributePower INT NOT NULL,
	CONSTRAINT pk_CharacterAttributeRelation_characterID_attributeName PRIMARY KEY(characterID, attributeName),
	CONSTRAINT fk_CharacterAttributeRelation_characterID FOREIGN KEY(characterID) REFERENCES `Character` (characterID)
    ON UPDATE CASCADE ON DELETE CASCADE, 
    CONSTRAINT fk_CharacterAttributeRelation_attributeName FOREIGN KEY (attributeName) REFERENCES `Attribute` (attributeName)
    ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE `CharacterGearRelation`(
     characterID INT,
     slotType VARCHAR(255),
     gearID INT NOT NULL, -- gearID NULL = unequipped or gearID 0 = unequipped
     CONSTRAINT pk_CharacterGearRelation_characterID_slotType PRIMARY KEY(characterID, slotType),
     CONSTRAINT fk_CharacterGearRelation_characterID FOREIGN KEY (characterID) REFERENCES `Character` (characterID)
     ON UPDATE CASCADE ON DELETE CASCADE, 
     CONSTRAINT fk_CharacterGearRelation_slotType FOREIGN KEY (slotType) REFERENCES `Slot` (slotType)
     ON UPDATE CASCADE ON DELETE CASCADE,
     CONSTRAINT fk_CharacterGearRelation_gearID FOREIGN KEY (gearID) REFERENCES `Gear` (itemID)
     ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE `Inventory`(
	characterID INT,
    inventoryPosition INT,
    itemID INT NOT NULL,
    itemQuantity INT NOT NULL,
    CONSTRAINT pk_Inventory_characterID_inventoryPosition PRIMARY KEY (characterID, inventoryPosition),
    CONSTRAINT fk_Inventory_itemID FOREIGN KEY (itemID) REFERENCES `Item`(itemID) 
    ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT fk_Inventory_characterID FOREIGN KEY (characterID) REFERENCES `Character`(characterID)
	ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE `CharacterJobRelation`(
    characterID INT,
	jobID INT,
    jobLevel INT DEFAULT 0 NOT NULL,
    isPlayed BOOL DEFAULT FALSE NOT NULL, -- dashboard, if never played turned grey
    CONSTRAINT pk_CharacterJobRelation_characterID_jobID PRIMARY KEY(characterID, jobID),
	CONSTRAINT fk_CharacterJobRelation_jobID FOREIGN KEY (jobID) REFERENCES `Job`(jobID) 
    ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT fk_CharacterJobRelation_characterID FOREIGN KEY (characterID) REFERENCES`Character`(characterID)
	ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE `Currency`(
	currencyID INT AUTO_INCREMENT,
    currencyName VARCHAR(255) NOT NULL,
    totalCap INT NOT NULL,
    weeklyCap INT,
    CONSTRAINT pk_Currency_currencyID PRIMARY KEY (currencyID)
);

CREATE TABLE `CharacterCurrencyRelation`(
	currencyID INT,
    characterID INT,
    amountOwned INT DEFAULT 0 NOT NULL,
    weeklyEarned INT,
	CONSTRAINT pk_CharacterCurrencyRelation_characterID_jobID PRIMARY KEY (characterID, currencyID),
	CONSTRAINT fk_CharacterCurrencyRelation_currencyID FOREIGN KEY (currencyID) REFERENCES `Currency`(currencyID)
	ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT fk_CharacterCurrencyRelation_characterID FOREIGN KEY (characterID)  REFERENCES `Character`(characterID)
	ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE `GearJobRelation`(
	gearID INT,
    jobID INT,
    CONSTRAINT pk_GearJobRelation_gearID_jobID PRIMARY KEY(gearID, jobID),
    CONSTRAINT fk_GearJobRelation_gearID FOREIGN KEY (gearID) REFERENCES `Gear` (itemID)
    ON UPDATE CASCADE ON DELETE CASCADE, 
    CONSTRAINT fk_GearJobRelation_jobID FOREIGN KEY (jobID) REFERENCES `Job` (jobID)
    ON UPDATE CASCADE ON DELETE CASCADE
    );
    
    INSERT INTO `Attribute`(attributeName)
	Values('Strength'), 
          ('Vitality'), 
          ('Determination'), 
          ('DirectHitRate'), 
          ('CriticalHit'), 
          ('Defense'), 
          ('MagicDefense'), 
          ('Tenacity'), 
          ('Intelligence'), 
          ('Mind'), 
          ('AttackPower'), 
          ('SkillSpeed'), 
          ('AttackMagicPotency'), 
          ('HealingMagicPotency'), 
          ('SpellSpeed'),
          ('AverageItemLevel'), 
          ('Piety'),
          ('Dexterity'),
          ('hp'),
          ('mp');

INSERT INTO `Slot`(slotType) 
     Values('mainhand'),
           ('head'), 
           ('offhand'), 
           ('body'), 
           ('earring'), 
           ('hands'), 
           ('wrist'), 
           ('legs'), 
           ('ring'), 
           ('feet');
