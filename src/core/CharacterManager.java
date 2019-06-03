package core;

import java.util.Vector;

import dataStructures.KittyCharacter;
import dataStructures.KittyUser;
import utils.GlobalLog;
import utils.LogFilter;

public class CharacterManager 
{
	public static CharacterManager instance; 
	
	private Vector <KittyCharacter> characters = new Vector<KittyCharacter>();
	public CharacterManager()
	{
		if(instance == null)
		{
			instance = this;
		}
		else
		{
			GlobalLog.Error(LogFilter.Core, "Attempted to create a second CharacterManager singleton!");
			return;
		}
	}
	
	public boolean addCharacter(KittyUser user, String name, String bio, String refImage)
	{
		for(KittyCharacter character:characters)
		{
			if(character.getName().equals(name) && character.getOwner().discordID.equals(user.discordID))
			{
				return false; 
			}
		}
		
		characters.add(new KittyCharacter(user, name, bio, refImage)); 
		return true; 
	}
	
	public boolean removeCharacter(KittyUser user, String name)
	{
		for(KittyCharacter character:characters)
		{
			if(character.getName().equals(name) && character.getOwner().discordID.equals(user.discordID))
			{
				characters.remove(character);
				return true;
			}
		}
		return false; 
	}
	
	public boolean editRefImage(KittyUser user, String name, String refImage)
	{
		for(KittyCharacter character:characters)
		{
			if(character.getName().equals(name) && character.getOwner().discordID.equals(user.discordID))
			{
				return character.editRefImage(user, refImage);
			}
		}
		return false; 
	}
	
	public boolean editBio(KittyUser user, String name, String bio)
	{
		for(KittyCharacter character:characters)
		{
			if(character.getName().equals(name) && character.getOwner().discordID.equals(user.discordID))
			{
				return character.editBio(user, bio);
			}
		}
		return false; 
	}
	
}
