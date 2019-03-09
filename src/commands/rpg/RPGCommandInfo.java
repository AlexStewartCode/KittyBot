package commands.rpg;

import core.rpg.RPGArmor;
import core.rpg.RPGCommand;
import core.rpg.RPGInput;
import core.rpg.RPGState;
import core.rpg.RPGWeapon;

public class RPGCommandInfo extends RPGCommand
{

	@Override
	public String OnRun(RPGState state, RPGInput input)
	{
		String out = null;
		switch(input.value.trim().toLowerCase())
		{
			case "weapon":
			case "weapo":
			case "weap":
			case "wea":
			case "we":
			case "w":
			case "wepon":
			case "wepo":
			case "wep":
			case "hand":
			case "att":
			case "attack":
				out = WeaponStats(state.player.GetWeapon());
				break;

			case "armour":
			case "armou":
			case "armor":
			case "armr":
			case "armo":
			case "arm":
			case "ar":
			case "a":
			case "def":
			case "defense":
			case "body":
			case "dress":
			case "wear":
			case "outfit":
				out = ArmorStats(state.player.GetArmor());
				break;
		}
		
		if(out != null)
			out = "```\n" + out + "```";
		
		return out;
	}
	
	private String WeaponStats(RPGWeapon weapon)
	{
		if(weapon == null)
			return null;
		
		String out = "";
		out += "[" + weapon.GetName() + "]";
		out += "\n";
		out += "  att: " + weapon.GetAttack();
		out += "\n";
		out += "value: " + weapon.GetValue() + "gp";
		out += "\n";
		out += "\n";
		out += weapon.GetDescription();
		
		return out;
	}
	
	private String ArmorStats(RPGArmor armor)
	{
		if(armor == null)
			return null;
		
		String out = "";
		out += "[" + armor.GetName() + "]";
		out += "\n";
		out += "  def: " + armor.GetDefense();
		out += "\n";
		out += "value: " + armor.GetValue() + "gp";
		out += "\n";
		out += "\n";
		out += armor.GetDescription();
		
		return out;
	}
}
