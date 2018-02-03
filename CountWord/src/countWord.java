import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class countWord {

	public static void main(String[] args) {
	
		String fileName = args[0];
		final int N = Integer.parseInt(args[1]);
				
//		int N = 5;
//		String fileName = "Lyrics.txt";
		
        Map<String, Integer> words = new HashMap<String, Integer>();
		
		try ( BufferedReader br = new BufferedReader(new FileReader(fileName)) ){
			String line;
		    Pattern p = Pattern.compile("[a-zA-z]+");		    
			while ( (line = br.readLine()) != null) {
				Matcher m = p.matcher(line);
				while (m.find()) {
					String word = line.substring(m.start(), m.end()).toLowerCase();
					if (words.containsKey(word)) {
						Integer old = words.get(word);
						words.replace(word, old + 1);
					} else {
						words.put(word, 1);
					}
					
				}
			}
			
	        List<Entry<String, Integer>> sortedWords = new ArrayList<Entry<String, Integer>>(words.entrySet());
	        Collections.sort( sortedWords, new Comparator<Map.Entry<String, Integer>>()
	        {
	            public int compare( Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2 )
	            {
	                if (o2.getValue() - o1.getValue() != 0) {
	                	return o2.getValue() - o1.getValue();
	                } else {
	                	return o1.getKey().compareTo(o2.getKey());
	                }
	            	
	            }
	        } );
			
			
	        for (int i = 0; i < N; i++) {
	        	System.out.println(sortedWords.get(i));
	        }
	        
			
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOException");
			e.printStackTrace();
		}

	}

}








