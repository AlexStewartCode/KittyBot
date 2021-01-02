package utils;

import java.util.*;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.internal.handle.GuildSetupController;

public class AdminControl 
{ 
	private Guild guild;
	
	public AdminControl(Guild guild)
	{
		this.guild = guild;
	}
	
	public boolean addRole(String memberID, String roleName)
	{
		try
		{
			Member member = guild.getMemberById(memberID);
			List<Role> roles = guild.getRolesByName(roleName, true);
			for(Role role : roles)
			{
				guild.addRoleToMember(member, role).complete();// submit instead? We need to queue.				
			}
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	
	public boolean removeRole(String memberID, String roleName)
	{
		try
		{
			Member member = guild.getMemberById(memberID);
			List<Role> roles = guild.getRolesByName(roleName, true);
			for(Role role : roles)
			{
				guild.removeRoleFromMember(member, role).complete();
			}
		}
		catch(Exception e)
		{
			return false; 
		}
		return true;
	}
	
	public boolean kickMember(String memberID)
	{
		try
		{
			guild.kick(guild.getMemberById(memberID)).complete();
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	
	public boolean muteMember(String memberID)
	{
		try
		{
			guild.mute(guild.getMemberById(memberID), true);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}
	
	public boolean banMember(String memberID)
	{
		try
		{
			guild.ban(guild.getMemberById(memberID), 0).complete();
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}
}
