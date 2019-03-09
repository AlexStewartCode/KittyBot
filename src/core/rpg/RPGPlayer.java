package core.rpg;

public class RPGPlayer extends RPGUnit 
{
	private String name;
	private long gold;
	private long exp;
	
	RPGWeapon weapon;
	RPGArmor armor;
	
	public RPGPlayer()
	{
		super();
		
		name = "Wanderer";
		healthCurrent = 5;
		healthMax = 5;
		gold = 0;
		weapon = new RPGWeapon();
		armor = new RPGArmor();
	}
	
	// Getters
	public long GetEXP() { return exp; }
	public long GetGold() { return gold; }
	public String GetName() { return name; }
	public RPGArmor GetArmor() { return armor; };
	public RPGWeapon GetWeapon() { return weapon; };
	
	// Setters
	public void SetName(String name) { this.name = name; }
	
	// Interactions
	public void ApplyEXP(int expToGive)
	{
		if(expToGive < 0)
			expToGive = 0;
		
		exp += expToGive;
	}
	
	public void GiveGold(long amount) 
	{
		if(amount < 0)
			amount = 0;
		
		gold += amount;
	}
	
	public void SpendGold(long amount)
	{
		if(amount < 0)
			amount = 0;
		
		gold -= amount;
	}
}
