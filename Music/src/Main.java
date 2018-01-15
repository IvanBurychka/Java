import java.util.List;

import model.*;

public class Main {

	public static void main(String[] args) {

		Datasource datasource = new Datasource();
				
		if(!datasource.open()) {
			System.out.println("Can't open datasource");
			return;
		}
		
		List<Artist> artists = datasource.querryArtist(Datasource.ORDER_BY_ASC);
		if (artists == null) {
			System.out.println("No artists");
			return;
		} 
		
//		for(Artist artist: artists) {
//			System.out.println("ID = " + artist.getId() + " name: " + artist.getName());
//		} 
		
		List<String> albums = datasource.queryAlbumsForArtist("Pink Floyd", Datasource.ORDER_BY_ASC);
		
		if (albums.size() == 0) {
			System.out.println("No album");
		} else {
			for(String album: albums) {
				System.out.println("Album: " + album);
			} 
		}
		
		System.out.println("ORDER BY artists.name, albums.name COLLATE NOCASE ASC;");
		System.out.println(Datasource.QUERY_ARTIST_FOR_SONG_SORT);
		datasource.close();

		
		
	}

}
