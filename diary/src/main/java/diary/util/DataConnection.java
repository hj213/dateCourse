package diary.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataConnection {
	public static Connection getConnection() throws SQLException {
		String url = "jdbc:mysql://localhost:3306/diary_db";
		String user = "root";
		String password = "Yun422537265!";
		
		return DriverManager.getConnection(url,user,password);
	}
}
