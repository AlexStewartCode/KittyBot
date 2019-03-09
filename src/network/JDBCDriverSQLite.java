package network;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import utils.GlobalLog;
import utils.LogFilter;

// Only to be called by the generic driver.
public class JDBCDriverSQLite extends JDBCDriver
{
	Connection connection = null;
	String databaseFolder = "db/";
	String databaseName = "catfood";
	
	@Override
	public boolean Connect()
	{		
		try 
		{
			{ // Scope to discard file...
				File f = new File(databaseFolder);
				if(!f.exists() || !f.isDirectory())
				{
					f.mkdir();
				}
			}
			
			// db parameters
			String url = "jdbc:sqlite:db/" + databaseName + ".db";
			
			// create a connection to the database
			connection = DriverManager.getConnection(url);
			
			GlobalLog.Log(LogFilter.Database, "Connection to SQLite has been established.");
		} 
		catch (SQLException e) 
		{
			GlobalLog.Error(e.getMessage());
		}
		
		return connection != null;
	}
	
	@Override
	public boolean Disconnect()
	{
		try
		{
			if (connection != null)
				connection.close();
			
			return true;
		} 
		catch (SQLException ex) 
		{
			GlobalLog.Error(ex.getMessage());
			return false;
		}
	}
	
	@Override
	public ResultSet ExecuteReturningStatement(String sql)
	{
		if(connection == null)
			return null;
		
		if(sql == null || sql.length() == 0)
			return null;
		
		try 
		{
			Statement statement = connection.createStatement();
			ResultSet set = statement.executeQuery(sql);
			return set;
		}
		catch (SQLException e) 
		{
			try {
				GlobalLog.Fatal(e.getMessage());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return null;
		}
	}
	
	public boolean ExecuteStatement(String sql)
	{
		if(connection == null)
			return false;
		
		if(sql == null || sql.length() == 0)
			return false;
		
		try 
		{
			Statement statement = connection.createStatement();
			boolean executed = statement.execute(sql);
			return executed;
		}
		catch (SQLException e) 
		{
			try {
				GlobalLog.Fatal(e.getMessage());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return false;
		}
	}
}
