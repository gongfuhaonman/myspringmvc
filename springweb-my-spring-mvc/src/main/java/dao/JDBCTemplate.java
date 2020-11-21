package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import config.DataSourceConfig;

abstract public class JDBCTemplate<T> {
//	private String driverClass="com.mysql.jdbc.Driver";
	private String driverClass=DataSourceConfig.driverClass;
	private String jdbcURL=DataSourceConfig.jdbcURL;
	private String user=DataSourceConfig.user;
	private String pwd=DataSourceConfig.pwd;
	abstract public T execute() throws Exception;
	protected Connection getConnection()throws Exception {
			Class.forName(driverClass);
			Connection conn=DriverManager.getConnection(jdbcURL, user, pwd);
			return conn;
		
	}
	
}
