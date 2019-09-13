package commands.dew.core.api;

public interface IDewPlayer extends IDewEntity
{
	public String getName();
	public String getCurrentRealm();
	public int getRealmX();
	public int getRealmY();
}
