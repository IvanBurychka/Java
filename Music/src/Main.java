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
		
		List<SongArtist> songArtists = datasource.queryArtistForSong("111Go Your Own Way", Datasource.ORDER_BY_ASC);
		
		if (songArtists.size() == 0) {
			System.out.println("No song");
		} else {
			for(SongArtist artist: songArtists) {
				System.out.println("Artist name = " + artist.getArtistName() + ";  Album name = " + artist.getAlbumName() +
						";  Song track = " + artist.getTrack());
			} 
		}

		datasource.querySongsMetadata();
		
		int count = datasource.getCount(Datasource.TABLE_SONGS);
		System.out.println("Count: " + count);
		
		datasource.createViewForSongArtists();

		datasource.close();

		
		
	}

}
