package dataStructures;

public class KittyCharacter 
{
	private KittyUser owner; 
	private String name;
	private String bio; 
	private String refImage; 
	
	public KittyCharacter(KittyUser owner, String name, String bio, String refImage)
	{
		this.owner = owner;
		this.name = name;
		this.bio = bio;
		this.refImage = refImage; 
	}
	
	
	private boolean allowedToEdit(KittyUser editor)
	{
		if(editor.identifier.equals(owner.identifier))
		{
			return true;
		}
		return false; 
	}
	
	public boolean editBio(KittyUser editor, String bio)
	{
		if(allowedToEdit(editor))
		{
			this.bio = bio;
			return true; 
		}
		return false;
	}
	
	public boolean editRefImage(KittyUser editor, String refImage)
	{
		if(allowedToEdit(editor))
		{
			this.refImage = refImage;
			return true; 
		}
		return false;
	}
	
	public KittyUser getOwner()
	{
		return owner;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getBio()
	{
		return bio;
	}
	
	public String getRefImage()
	{
		return refImage; 
	}
	
	public String toString()
	{
		return name + " " + bio + " " + refImage;
	}
}
