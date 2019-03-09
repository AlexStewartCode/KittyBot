package core.rpg;

public abstract class RPGCommand
{
	public RPGCommand() { }
	
	// OVERRIDE ME
	public abstract String OnRun(RPGState state, RPGInput input);
}
