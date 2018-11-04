package Utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import exception.DaoException;

public class JDBCUtils {
    private static Connection conn = null;

    public static Connection getConnection() {
	String url = "";

	try {
	    Properties prop = new Properties();
	    InputStream in = JDBCUtils.class.getClassLoader()
		    .getResourceAsStream("JDBC.properties");
	    prop.load(in);
	    url = prop.getProperty("url");
	    conn = DriverManager.getConnection(url, prop);
	} catch (Exception e) {
	    throw new DaoException();
	}

	return conn;
    }

    public static void relese(Connection conn, PreparedStatement stmt,
	    ResultSet rs) {
	try {
	    if (conn != null)
		conn.close();
	    if (stmt != null)
		stmt.close();
	    if (rs != null)
		rs.close();
	} catch (Exception e) {
	    System.out.println("释放资源失败");
	}
    }

}
