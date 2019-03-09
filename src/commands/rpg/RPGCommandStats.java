package commands.rpg;

import core.rpg.RPGArmor;
import core.rpg.RPGCommand;
import core.rpg.RPGExpTable;
import core.rpg.RPGInput;
import core.rpg.RPGPlayer;
import core.rpg.RPGState;
import core.rpg.RPGWeapon;

public class RPGCommandStats extends RPGCommand
{
	@Override
	public String OnRun(RPGState state, RPGInput input) 
	{
		RPGPlayer player = state.player;
		long exp = player.GetEXP();
		long level = RPGExpTable.LevelFromEXP(player.GetEXP());
		long ceil = RPGExpTable.EXPCeil(level);
		
		String indent = "";
		String linebreak = "\n";
		
		String out = "";
		out += indent + "[" + player.GetName() + ", lv. " + level + "]";
		out += indent + linebreak;
		out += indent + "_______________";
		out += indent + linebreak;
		
		out += indent + "   EXP: " + exp;
		out += indent + " (until next: " + (ceil - exp) + ")";
		out += indent + linebreak;
				
		out += indent + "  Gold: " + player.GetGold();
		out += indent + linebreak;
		
		out += indent + "Health: " + player.GetHealthCurrent() + "/" + player.GetHealthMax();
		out += indent + linebreak;
		
		RPGWeapon weapon = player.GetWeapon();
		out += indent + "_____";
		out += indent + linebreak; 
		out += indent + "Weapon: " + weapon.GetName();
		out += indent + linebreak;
		out += indent + "   att: " + weapon.GetAttack();
		out += indent + linebreak;
		
		RPGArmor armor = player.GetArmor();
		out += indent + "_____";
		out += indent + linebreak; 
		out += indent + "Armor: " + armor.GetName();
		out += indent + linebreak;
		out += indent + "  def: " + armor.GetDefense() ;
		out += indent + linebreak;
		
		out += indent + linebreak;
		out += "Use 'info armor' / 'info weapon' for details!";
		
		return "```\n" + out + "```";
	}
}
