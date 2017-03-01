package dao.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import dao.DaoHelper;

public class DaoHelperImpl implements DaoHelper{

	private static DaoHelperImpl baseDao=new DaoHelperImpl();
	private InitialContext jndiContext = null;
	private Connection cnn = null;
	private DataSource datasource = null;
	private PreparedStatement pstmt=null;
	
	private DaoHelperImpl() {
		Properties properties = new Properties();
		properties.put(javax.naming.Context.PROVIDER_URL, "jnp:///");
		properties.put(javax.naming.Context.INITIAL_CONTEXT_FACTORY,
		"org.apache.naming.java.javaURLContextFactory");
//		TODO 上面的property是在干吗。。。一会查一下！！！！！
		try {
			jndiContext = new InitialContext(properties);
			datasource = (DataSource) jndiContext.lookup("java:comp/env/jdbc/myDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}	
	}
	public static DaoHelperImpl getInstance(){
		return baseDao;
	}

	/**
     * handle the sql with dynamic parameter
     * >1 parameters
     * @param sql
     * @param param
     */
	public ResultSet handlePreparedStatement(String sql,List<String> param){
		try {
			cnn = datasource.getConnection();
			pstmt=cnn.prepareStatement(sql);
			for(int i=0,len=param.size();i<len;i++){
				pstmt.setString(i+1, param.get(i));
			}
			return pstmt.executeQuery();
		} catch (SQLException e) {
			System.out.print("HandlePreparedStatement Method -- >=2 parameters Error:");
			e.printStackTrace();
		}
		return null;
	}
	/**
     * handle the sql with dynamic parameter
     * only 1 parameter
     * @param sql
     * @param param
     */
	public ResultSet handlePreparedStatement(String sql,String param){
		try {
			cnn = datasource.getConnection();
			pstmt=cnn.prepareStatement(sql);
			pstmt.setString(1, param);	
			return pstmt.executeQuery();
		} catch (SQLException e) {
			System.out.print("HandlePreparedStatement Method --1 parameter Error:");
			e.printStackTrace();
		}
		return null;
	}
	/**
     * handle the sql with dynamic parameter
     * no parameter
     * @param sql
     */
	public ResultSet handlePreparedStatement(String sql){
		try {
			cnn = datasource.getConnection();
			pstmt=cnn.prepareStatement(sql);
			return pstmt.executeQuery(sql);
		} catch (SQLException e) {
			System.out.print("HandlePreparedStatement Method --no parameter Error:");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void closeResultSet(ResultSet rs) {
		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void closeStatement_Connection() {
		try {
			if(pstmt!=null)pstmt.close();
			if(cnn!=null)cnn.close();
		} catch (SQLException e) {
			System.out.print("closing resource Error:");
			e.printStackTrace();
		}
	}


}
