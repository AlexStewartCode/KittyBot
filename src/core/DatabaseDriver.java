package core;

import java.sql.ResultSet;
import java.sql.SQLException;

import network.JDBCDriver;
import network.JDBCDriverSQLite;
import utils.GlobalLog;
import utils.LogFilter;

// Java DataBase Connection Driver - the generic version.
// This is where we put everything and swap out the moving parts, 
// ie MySQL, PostgreSQL, SQLite, etc...
//
// TODO(wisp): Right now this can be messed up with SQL injection stuff if users
// are allowed to directly touch data. Leaving it like this temporarily.
public class DatabaseDriver 
{
	private JDBCDriver driver;
	
	// Note: Changing these values can mess up the database...
	private final String globalTableName = "kitty_globals";
	private final String globalKeyName = "GlobalKey";
	private final String globalValueName = "GlobalValue";
	
	public DatabaseDriver()
	{
		driver = null;
	}
	
	public boolean Connect()
	{
		driver = new JDBCDriverSQLite();
		driver.Connect();
	
		// Require a global table if it doesn't exist already
		driver.ExecuteStatement("CREATE TABLE IF NOT EXISTS " + globalTableName + " (" + globalKeyName + " text PRIMARY KEY, " + globalValueName + " text);");
		
		return true;
	}
	
	// The key will be created if it doesn't exist and the value specified will be stored.
	public void CreateSetKey(String key, String value)
	{
		GlobalLog.Log(LogFilter.Database, "CreateSetKey: Key-" + key + " value-" + value);
		
		if(HasKey(key))
		{
			UpdateKey(key, value);
		}
		else
		{
			CreateKey(key, value);
		}
	}
	
	// the key will be created if it doesn't exist, and the default value returned.
	public String CreateGetKey(String key)
	{
		GlobalLog.Log(LogFilter.Database, "CreateGeyKey: key-" + key);
		
		if(HasKey(key))
		{
			return GetKey(key);
		}
		else
		{
			String newValue = "";
			CreateKey(key, newValue);
			return newValue;
		}
	}
	
	private void UpdateKey(String key, String value)
	{
		//GlobalLog.Log(LogFilter.Database, "Update key " + key);
		String command = "UPDATE " + globalTableName + " SET " + globalValueName + " = '" + value + "' WHERE " + globalKeyName + "= '" + key + "';";
		//GlobalLog.Log(LogFilter.Database, "Composed command: " + command);
		driver.ExecuteStatement(command);
	}
	
	private boolean HasKey(String key)
	{
		//GlobalLog.Log(LogFilter.Database, "Has key " + key);
		String command = "SELECT COUNT(1) as count FROM " + globalTableName + " WHERE " + globalKeyName + " = '" + key + "';";
		//GlobalLog.Log(LogFilter.Database, "Composed command: " + command);
		ResultSet set = driver.ExecuteReturningStatement(command);
		String out = ResultAsString(set, "count");
		return out.charAt(0) == '1';
	}
	
	private String GetKey(String key)
	{
		//GlobalLog.Log(LogFilter.Database, "Getting key " + key);
		String command = "SELECT " + globalValueName + " as searchedKey FROM " + globalTableName +" WHERE " + globalKeyName + "= \'" + key + "\';";
		//GlobalLog.Log(LogFilter.Database, "Composed command: " + command);
		ResultSet set = driver.ExecuteReturningStatement(command);
		return ResultAsString(set, "searchedKey");
	}
	
	private void CreateKey(String key, String value)
	{
		//GlobalLog.Log(LogFilter.Database, "Creating Key " + key);
		String command = "INSERT INTO " + globalTableName + " (GlobalKey, GlobalValue) VALUES ('" + key + "', '" + value + "');";
		//GlobalLog.Log(LogFilter.Database, "Composed command: " + command);
		driver.ExecuteStatement(command);
	}
	
	private String ResultAsString(ResultSet rs, String key)
	{
		if(rs == null)
			return "";
		
		try
		{
			boolean hasKey = rs.next();
			if(hasKey)
			{
				String val = rs.getString(key);
				return val;
			}
			else
				return null;
				
		}
		catch (SQLException e) 
		{
			GlobalLog.Error(LogFilter.Database, e.toString());
			return null;
		}	
	}
}