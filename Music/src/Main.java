import java.util.List;

import model.Artist;
import model.Datasource;

public class Main {

	public static void main(String[] args) {

		Datasource datasource = new Datasource();
		if(!datasource.open()) {
			System.out.println("Can't open datasource");
			return;
		}
		
		List<Artist> artists = datasource.querryArtist();
		if (artists == null) {
			System.out.println("No artists");
			return;
		} 
		
		for(Artist artist: artists) {
			System.out.println("ID = " + artist.getId() + " name: " + artist.getName());
		} 
		
		datasource.close();

		
		
	}

}
