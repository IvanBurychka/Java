import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class wordCount {

	public static void main(String[] args) {
	
		final int N = Integer.parseInt(args[1]);
		String fileName = args[0];
		
        Map<String, Integer> words = new TreeMap<>();
		
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
			
			for (Entry<String, Integer> value : words.entrySet()) {
				if (value.getValue() > N) {
					System.out.println(value.getKey() + "=" + value.getValue());
				}
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