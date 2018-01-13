
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
	
	public static void main(String[] args) {
	
		try (Connection conn = DriverManager.getConnection("jdbc:sqlite:G:\\Program\\Java\\TestDB\\testjava.db");
				Statement statement = (Statement) conn.createStatement();) {
//			conn.setAutoCommit(false);
			statement.execute("CREATE TABLE IF NOT EXISTS contacts "
							+ "(name TEXT, phone INTEGER, email TEXT)");
//			statement.execute("INSERT INTO contacts (name, phone, email) "
//							+ "VALUES ('Joe', 45632, 'joe@enywhere.com')");
//			statement.execute("INSERT INTO contacts (name, phone, email) "
//							+ "VALUES ('Jane', 4829484, 'jane@somewhere.com')");
//			statement.execute("INSERT INTO contacts (name, phone, email) "
//							+ "VALUES ('Fido', 9038, 'dog@email.com')");
//			statement.execute("UPDATE contacts SET phone = 5566789 WHERE name = 'Jane' "); 
//			statement.execute("DELETE FROM contacts WHERE name = 'Joe'");
			
			statement.execute("SELECT * FROM contacts");
			ResultSet result = statement.getResultSet();
			while (result.next()) {
				System.out.println(result.getString("name") + " " +
									result.getInt("phone") + " " +
									result.getString("email"));
			}
			result.close();
			
			
			
		} catch (SQLException e) {
			System.out.println("Something is wrong: " + e.getMessage());
		} 
		
		
	}
	
}
