package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Datasource {

	public static final String DB_NAME = "music.db";
	public static final String CONNECTION_STRING = "jdbc:sqlite:D:\\Program\\Java\\Music\\" + DB_NAME;
	
	public static final String TABLE_ALBUMS = "albums"; 
	public static final String COLUMN_ALBUM_ID = "_id";
	public static final String COLUMN_ALBUM_NAME = "name";
	public static final String COLUMN_ALBUM_ARTIST = "artist";
	
	public static final String TABLE_ARTISTS = "artists";
	public static final String COLUMN_ARTIST_ID = "_id"; 	
	public static final String COLUMN_ARTIST_NAME = "name";
	
	public static final String TABLE_SONGS = "songs";
	public static final String COLUMN_SONG_TRACK = "track";
	public static final String COLUMN_SONG_TITLE = "title";
	public static final String COLUMN_SONG_ALBUM = "album";
	
	private Connection conn;
	
	public boolean open() {
		
		try {
			conn = DriverManager.getConnection(CONNECTION_STRING);
			return true;
		} catch (SQLException e) {
			System.out.println("Can't create conection with database" + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	
	public void close() {

		try {
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			System.out.println("Can't close conection" + e.getMessage());
		}
	}
	
	public List<Artist> querryArtist(){
		
		Statement statement = null;
		ResultSet result = null;
		
		try {
			statement = conn.createStatement();
			result = statement.executeQuery("SELECT * FROM " + TABLE_ARTISTS);
					
			List<Artist> artists = new ArrayList<>();
			while (result.next()) {
				Artist artist = new Artist();
				artist.setId(result.getInt(COLUMN_ARTIST_ID));
				artist.setName(result.getString(COLUMN_ARTIST_NAME));
				artists.add(artist);
			}
			
			return artists;
		} catch (SQLException e) {
			System.out.println("Query failed " + e.getMessage());
			return null;
		} finally {
			try {
				if(result != null) {
				result.close();
				}
			} catch (SQLException e) {
				System.out.println("Can't close result: " + e.getMessage());
				e.printStackTrace();
				
			}
			
			try {
				if(statement != null) {
				statement.close();
				}
			} catch (SQLException e) {
				System.out.println("Can't close statement: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}
		
	
}











