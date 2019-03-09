package core.rpg;

// Holds specific state information for a given user and their world
public class RPGState
{
	// General
	public String userID;
	
	// Stats and gameplay
	public RPGPlayer player;
	public RPGBattleContext battleContext;
	
	public RPGState(String userID)
	{
		this.userID = userID;
		this.player = new RPGPlayer();
		this.battleContext = null;
	}
}