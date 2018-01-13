
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
	
	public static void main(String[] args) {
	
		try (Connection conn = DriverManager.getConnection("jdbc:sqlite:G:\\Program\\Java\\TestDB\\testjava.db");
				Statement statement = (Statement) conn.createStatement();) {
			
			String sql = "CREATE TABLE contacts (name TEXT, phone INTEGER, email TEXT );";
			statement.execute(sql);
		} catch (SQLException e) {
			System.out.println("Something is wrong: " + e.getMessage());
		} 
		
		
	}
	
}
