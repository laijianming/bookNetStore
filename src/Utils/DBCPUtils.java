package Utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;

public class DBCPUtils {

	private static Connection conn = null;
	private static DataSource dataSource = null;

	static {

		// 得到数据源
		try {
			Properties prop = new Properties();
			InputStream in = DBCPUtils.class.getClassLoader().getResourceAsStream("dbcp.properties");
			prop.load(in);

			dataSource = BasicDataSourceFactory.createDataSource(prop);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Connection getConection() throws Exception {

		return dataSource.getConnection();

	}

	public static void relese(Connection conn, PreparedStatement stmt, ResultSet rs) {
		try {
			if (conn != null)
				conn.close();
			if (stmt != null)
				stmt.close();
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
