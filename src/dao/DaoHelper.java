package dao;
import java.sql.ResultSet;
import java.util.List;

public interface DaoHelper {
	public void closeResultSet(ResultSet rs);
	public void closeStatement_Connection();
	public ResultSet handlePreparedStatement(String sql,List<String> param);
	public ResultSet handlePreparedStatement(String sql,String param);
	public ResultSet handlePreparedStatement(String sql);
		
	
}
