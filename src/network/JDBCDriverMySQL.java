package network;

import java.sql.ResultSet;

public class JDBCDriverMySQL extends JDBCDriver
{
	@Override
	public boolean Connect() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean Disconnect() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean ExecuteStatement(JDBCStatementType type, String statement, String[] args) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ResultSet ExecuteReturningStatement(JDBCStatementType type, String statement, String[] args) {
		// TODO Auto-generated method stub
		return null;
	}
}
