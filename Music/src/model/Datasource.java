package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Datasource {

	public static final String DB_NAME = "music.db";
	public static final String CONNECTION_STRING = "jdbc:sqlite:G:\\Program\\Java\\Music\\" + DB_NAME;
	
	public static final String TABLE_ALBUMS = "albums"; 
	public static final String COLUMN_ALBUM_ID = "_id";
	public static final String COLUMN_ALBUM_NAME = "name";
	public static final String COLUMN_ALBUM_ARTIST = "artist";
	public static final int INDEX_ALBUM_ID = 1;
	public static final int INDEX_ALBUM_NAME = 2;
	public static final int INDEX_ALBUM_ARTIST = 3;
	
	public static final String TABLE_ARTISTS = "artists";
	public static final String COLUMN_ARTIST_ID = "_id"; 	
	public static final String COLUMN_ARTIST_NAME = "name";
	public static final int INDEX_ARTIST_ID = 1;
	public static final int INDEX_ARTIST_NAME = 2;
	
	public static final String TABLE_SONGS = "songs";
	public static final String COLUMN_SONG_ID = "_id";
	public static final String COLUMN_SONG_TRACK = "track";
	public static final String COLUMN_SONG_TITLE = "title";
	public static final String COLUMN_SONG_ALBUM = "album";
	public static final int INDEX_SONG_ID = 1;
	public static final int INDEX_SONG_TRACK = 2;
	public static final int INDEX_SONG_TITLE = 3;
	public static final int INDEX_SONG_ALBUM = 4;
	
	public static final int ORDER_BY_NONE = 1;
	public static final int ORDER_BY_ASC = 2;
	public static final int ORDER_BY_DESC = 3;
	
	public static final String QUERY_ALBUMS_BY_ARTIST_START = 
			"SELECT " + TABLE_ALBUMS + '.' + COLUMN_ALBUM_NAME +
			" FROM " + TABLE_ALBUMS + " INNER JOIN " + TABLE_ARTISTS + " ON " + TABLE_ALBUMS + '.' +
			COLUMN_ALBUM_ARTIST + " = " + TABLE_ARTISTS + '.' + COLUMN_ARTIST_ID + " WHERE " + 
			TABLE_ARTISTS + '.' + COLUMN_ARTIST_NAME  + " = \"";
	
	public static final String QUERY_ALBUMS_BY_ARTIST_SORT = 
			" ORDER BY " + TABLE_ALBUMS + "." + COLUMN_ALBUM_NAME + " COLLATE NOCASE ";
			
	public static final String QUERY_ARTIST_FOR_SONG_START =
			"SELECT " + TABLE_ARTISTS + "." + COLUMN_ARTIST_NAME + ", " +
			TABLE_ALBUMS + "." + COLUMN_ALBUM_NAME + ", " + TABLE_SONGS + "." + COLUMN_SONG_TRACK +
			" FROM " + TABLE_SONGS + " INNER JOIN " + TABLE_ALBUMS + 
			" ON " + TABLE_SONGS + "." + COLUMN_SONG_ALBUM + '=' + TABLE_ALBUMS + "." + COLUMN_ALBUM_ID +
			" INNER JOIN " + TABLE_ARTISTS + " ON "
			+ TABLE_ALBUMS + "." + COLUMN_ALBUM_ARTIST + '=' + TABLE_ARTISTS + "." + COLUMN_ALBUM_ID + 
			" WHERE " + TABLE_SONGS + "." + COLUMN_SONG_TITLE + "= \"";
	
	public static final String QUERY_ARTIST_FOR_SONG_SORT = 
			" ORDER BY " + TABLE_ARTISTS + '.' + COLUMN_ARTIST_NAME + ", " + TABLE_ALBUMS + '.' + COLUMN_ALBUM_NAME +
			" COLLATE NOCASE ";
	
	public static final String TABLE_ARTIST_SONG_VIEW = "artist_list";
			
	public static final String CREATE_ARTIST_FOR_SONG_VIEW = "CREATE VIEW IF NOT EXISTS " + TABLE_ARTIST_SONG_VIEW + 
			" AS SELECT " + TABLE_ARTISTS + "." + COLUMN_ARTIST_NAME + ", " +
			TABLE_ALBUMS + "." + COLUMN_ALBUM_NAME + " AS album, " + TABLE_SONGS + "." + COLUMN_SONG_TRACK + ", " +
			TABLE_SONGS + "." + COLUMN_SONG_TITLE + 
			" FROM " + TABLE_SONGS + " INNER JOIN " + TABLE_ALBUMS +
			" ON " + TABLE_SONGS + "." + COLUMN_SONG_ALBUM + " = " + TABLE_ALBUMS + "." + COLUMN_ALBUM_ID + 
			" INNER JOIN " + TABLE_ARTISTS +
			" ON " + TABLE_ALBUMS + "." + COLUMN_ALBUM_ARTIST + " = " + TABLE_ARTISTS + "." + COLUMN_ARTIST_ID +
			" ORDER BY " + TABLE_ARTISTS + "." + COLUMN_ARTIST_NAME + ", " + TABLE_ALBUMS + "." + COLUMN_ALBUM_NAME + ", " +
			TABLE_SONGS + "." + COLUMN_SONG_TRACK + ";";
	
	public static final String QUERY_VIEW_SONG_INFO = 
			"SELECT " + COLUMN_ARTIST_NAME + ", " + COLUMN_SONG_ALBUM + ", " + COLUMN_SONG_TRACK + 
			" FROM " + TABLE_ARTIST_SONG_VIEW + " WHERE " + COLUMN_SONG_TITLE + " = \"";
	
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
	
	
	public List<Artist> querryArtist(int sortOrder){
		
		StringBuilder sb = new StringBuilder("SELECT * FROM ");
		sb.append(TABLE_ARTISTS);
		if (sortOrder != ORDER_BY_NONE) {
			sb.append(" ORDER BY ");
			sb.append(COLUMN_ARTIST_NAME);
			sb.append(" COLLATE NOCASE ");
			if (sortOrder == ORDER_BY_ASC) {
				sb.append("ASC");
			} else {
				sb.append("DESC");
			}
		}
		
		try (Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery(sb.toString())) 
		{
					
			List<Artist> artists = new ArrayList<>();
			while (result.next()) {
				Artist artist = new Artist();
				artist.setId(result.getInt(INDEX_ARTIST_ID));
				artist.setName(result.getString(INDEX_ARTIST_NAME));
				artists.add(artist);
			}
			
			return artists;
		} catch (SQLException e) {
			System.out.println("Query failed " + e.getMessage());
			return null;
		} 
		
	}
		
	
	public List<String> queryAlbumsForArtist(String artistName, int sortOrder){
		
		StringBuilder sb = new StringBuilder(QUERY_ALBUMS_BY_ARTIST_START);
		sb.append(artistName);
		sb.append("\"");
		
		if (sortOrder != ORDER_BY_NONE) {
			sb.append(QUERY_ALBUMS_BY_ARTIST_SORT);

			if (sortOrder == ORDER_BY_ASC) {
				sb.append("ASC");
			} else {
				sb.append("DESC");
			}
		}
				
		try (Statement statement = conn.createStatement();
			 ResultSet result = statement.executeQuery(sb.toString())) {
			
			List<String> retalbums = new ArrayList<String>();
			
			while(result.next()) {
				retalbums.add(result.getString(1));
			}
			return retalbums;
			
		} catch (SQLException e) {
			System.out.println("Can't create album for artist");
			e.printStackTrace();
			return null;
		} 
		
	}
	

	public List<SongArtist> queryArtistForSong (String songName, int sortOrder){
		
		StringBuilder sb = new StringBuilder(QUERY_ARTIST_FOR_SONG_START);
		sb.append(songName);
		sb.append("\"");
		
		if (sortOrder != ORDER_BY_NONE) {
			sb.append(QUERY_ARTIST_FOR_SONG_SORT);
			if (sortOrder == ORDER_BY_ASC) {
				sb.append("ASC;");
			} else {
				sb.append("DESC;");
			}
		}

		try (Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery(sb.toString())){
		
			List<SongArtist> songArtists = new ArrayList<>();
			
			while(result.next()) {
				SongArtist songArtist = new SongArtist();
				songArtist.setArtistName(result.getString(1));
				songArtist.setAlbumName(result.getString(2));
				songArtist.setTrack(result.getInt(3));
				songArtists.add(songArtist);
			}
			
			return songArtists;
			
		} catch (Exception e) {
			System.out.println("Can't create song query" + e.getMessage());
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	public void querySongsMetadata() {
		String sql = "SELECT * FROM " + TABLE_SONGS;
		
		try (Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery(sql)){
			
			ResultSetMetaData meta = result.getMetaData();
			int numColumns = meta.getColumnCount();
			
			for (int i = 1; i <= numColumns; i++) {
				System.out.format("Column %d in the songs is names %s\n", i, meta.getColumnName(i));
			}		
					
		} catch (SQLException e) {
			System.out.println("Can't take metadata: " + e.getMessage());
		}
		
	}

	public int getCount(String table) {
		String sql = "SELECT COUNT(*) AS count FROM " + table;
		
		try (Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery(sql)){
			
			int count = result.getInt("count");
			System.out.format("Count: %d \n", count);
			return count;
			
		} catch (SQLException e) {
			System.out.println("Can't calculate from table: " + e.getMessage());
			return -1;
		}
		
	}
	
	public boolean createViewForSongArtists () {
		
		try (Statement statement = conn.createStatement()){
			
			statement.execute(CREATE_ARTIST_FOR_SONG_VIEW);
			return true;
		}	
		catch (SQLException e) {
			System.out.println("Create View failed: " + e.getMessage());
			return false;
		}
		
	}

	public List<SongArtist> querySongInfoView (String title) {
		
		StringBuilder sb = new StringBuilder(QUERY_VIEW_SONG_INFO);
		sb.append(title);
		sb.append("\";");
		
		try (Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery(sb.toString()) ) {
			
			List<SongArtist> songArtists = new ArrayList<>();
			
			while (result.next()) {
				SongArtist songArtist = new SongArtist();
				songArtist.setAlbumName(result.getString(INDEX_ALBUM_NAME));
				songArtist.setArtistName(result.getString(INDEX_ARTIST_NAME));
				songArtist.setTrack(result.getInt(INDEX_SONG_TRACK));
				songArtists.add(songArtist);
			}
			
			return songArtists;
		} catch (SQLException e) {
			System.out.println(" :" + e.getMessage());
			return null;
		}
		
	}
}











