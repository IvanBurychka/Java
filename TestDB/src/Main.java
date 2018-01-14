
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

	public static final String DB_NAME = "testjava.db";
	public static final String CONNECTION_STRING = "jdbc:sqlite:G:\\Program\\Java\\TestDB\\" + DB_NAME;
	
	public static final String TABLE_CONTACTS = "contacts";
	
	public static final String COLUMN_NAME = "name";	
	public static final String COLUMN_PHONE = "phone";
	public static final String COLUMN_EMAIL = "email";
	
	public static void main(String[] args) {
	
		try (Connection conn = DriverManager.getConnection(CONNECTION_STRING);
				Statement statement = (Statement) conn.createStatement();) {
			
			
			statement.execute("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
			statement.execute("CREATE TABLE IF NOT EXISTS " + TABLE_CONTACTS +
						" ( " + COLUMN_NAME + " text, " +
								COLUMN_PHONE + " integer, " +
								COLUMN_EMAIL + " text)");
			
			statement.execute("INSERT INTO " + TABLE_CONTACTS + 
							" ( " + COLUMN_NAME + ", " + 
									COLUMN_PHONE + ", " + 
									COLUMN_EMAIL + 
							  ") " +
								   "VALUES ('Tim', 6545678, 'tim@email.com')" );
			
			statement.execute("INSERT INTO " + TABLE_CONTACTS + 
							" ( " + COLUMN_NAME + ", " + 
									COLUMN_PHONE + ", " + 
									COLUMN_EMAIL + 
							  ") " +
								   "VALUES ('Joe', 45632, 'joe@enywhere.com')" );
			
			statement.execute("INSERT INTO " + TABLE_CONTACTS + 
							" ( " + COLUMN_NAME + ", " + 
									COLUMN_PHONE + ", " + 
									COLUMN_EMAIL + 
							  ") " +
								   "VALUES ('Jane', 4829484, 'jane@somewhere.com')" );
			
			statement.execute("INSERT INTO " + TABLE_CONTACTS + 
							" ( " + COLUMN_NAME + ", " + 
									COLUMN_PHONE + ", " + 
									COLUMN_EMAIL + 
							  ") " +
								   "VALUES ('Fido', 9038, 'dog@email.com')" );
			
			statement.execute("UPDATE " + TABLE_CONTACTS + " SET " + COLUMN_PHONE + "=556678 WHERE " + COLUMN_NAME + "='Jane'");
			statement.execute("DELETE FROM " + TABLE_CONTACTS + " WHERE " + COLUMN_NAME + "='Joe'");
			
			ResultSet result = statement.executeQuery("SELECT * FROM " + TABLE_CONTACTS);
			
			while (result.next()) {
				System.out.println(result.getString(COLUMN_NAME) + " " +
									result.getInt(COLUMN_PHONE) + " " +
									result.getString(COLUMN_EMAIL));
			}
			result.close();
			
		} catch (SQLException e) {
			System.out.println("Something is wrong: " + e.getMessage());
		} 
		
		
	}
	
}
