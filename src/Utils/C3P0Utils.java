package Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3P0Utils {

	private static Connection conn = null;
	private static DataSource ds = null;

	static {
		ds = new ComboPooledDataSource("c3p0");
	}

	public static Connection getConnection() throws Exception {
		return ds.getConnection();
	}

	public static void relese(Connection conn, PreparedStatement stmt, ResultSet rs) throws SQLException {
		if (conn != null)
			conn.close();
		if (stmt != null)
			stmt.close();
		if (rs != null)
			rs.close();
	}

	/*
	 * @Test public void test2() throws Exception{ //��c3p0-config.xml�����ļ��õ����Դ DataSource ds =
	 * new ComboPooledDataSource("c3p0");
	 * 
	 * // //�õ�c3p0���������� // ComboPooledDataSource comboPooledDataSource =
	 * (ComboPooledDataSource) ds;
	 * 
	 * 
	 * }
	 * 
	 * @Test public void test() throws Exception{ ComboPooledDataSource cpds = new
	 * ComboPooledDataSource(); cpds.setDriverClass("com.mysql.jdbc.Driver");
	 * cpds.setJdbcUrl("jdbc:mysql://localhost:3306/bookstore"); cpds.setUser("root");
	 * cpds.setPassword("123456");
	 * 
	 * System.out.println(cpds.getConnection()); System.out.println(cpds.getMaxPoolSize()); }
	 */

}
