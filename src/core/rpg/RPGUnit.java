package core.rpg;

public abstract class RPGUnit 
{
	protected int healthMax;
	protected int healthCurrent;
	
	public int GetHealthMax() { return healthMax; }
	public int GetHealthCurrent() { return healthCurrent; }
	public boolean IsAlive() { return healthCurrent > 0; }

	// Generic implementation. Consider implementing armor in 
	// derived classes, for example.
	public void ApplyDamage(int value) 
	{ 
		if(value < 0)
			value = 0;
		
		healthCurrent -= value; 
	}
	
	// Generic implementation. Consider applying boosts in
	// derived classes, for example.
	public void ApplyHealing(int value) 
	{
		if(value < 0)
			value = 0;
		
		healthCurrent += value;
		
		if(healthCurrent > healthMax)
			healthCurrent = healthMax;
	}
}
