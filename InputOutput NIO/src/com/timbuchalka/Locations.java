package com.timbuchalka;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
 
/**
 * Created by timbuchalka on 2/04/2016.
 */
public class Locations implements Map<Integer, Location> {
    private static Map<Integer, Location> locations = new LinkedHashMap<Integer, Location>();

    public static void main(String[] args) throws IOException {
        
    	
    	
    	
    	
//    	Path locPath = FileSystems.getDefault().getPath("locatios_big.txt");
//    	Path dirPath = FileSystems.getDefault().getPath("directions_big.txt");
//    	try (BufferedWriter locFile = Files.newBufferedWriter(locPath);
//    		 BufferedWriter dirFile = Files.newBufferedWriter(dirPath) ){
//    		
//    		for (Location location : locations.values()) {
//    			locFile.write(location.getLocationID() + "," + location.getDescription() + "\n");
//    			for(String direction: location.getExits().keySet()) {
//    				if (!direction.equalsIgnoreCase("Q")) {
//    					dirFile.write(location.getLocationID() + "," + direction + " ," +
//    							location.getExits().get(direction) + "\n");
//    				}
//    			}
//    		}
//    		
//    	} catch (IOException e) {
//    		System.out.println("IOException: " + e.getMessage());
//    	}
    }

    static {

    	Path locPath = FileSystems.getDefault().getPath("locatios_big.txt");
    	Path dirPath = FileSystems.getDefault().getPath("directions_big.txt");
    	
    	try (Scanner scanner = new Scanner(Files.newBufferedReader(locPath))){
    		scanner.useDelimiter(",");
    		while (scanner.hasNextLine()) {
    			int loc = scanner.nextInt();
    			scanner.skip(scanner.delimiter());
    			String description = scanner.nextLine();
    			System.out.println("Importet loc: " + loc + ": " + description);
    			locations.put(loc, new Location(loc, description, null));
    		}  		
    	} catch (IOException e) {
			System.out.println("IOException" + e.getMessage());
			e.printStackTrace();
		}
    	
    	try (BufferedReader dirFile = Files.newBufferedReader(dirPath)){
			String input;
			
			while ((input = dirFile.readLine()) != null) {
				String[] data = input.split(",");
				int loc = Integer.parseInt(data[0]);
				String direction = data[1];
				int destination = Integer.parseInt(data[3]);
				System.out.println(loc + ": "+ direction + ": " + destination);
				Location location = locations.get(loc);
				location.addExit(direction, destination);
			}
    		
    		
    		
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }

    @Override
    public int size() {
        return locations.size();
    }

    @Override
    public boolean isEmpty() {
        return locations.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return locations.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return locations.containsValue(value);
    }

    @Override
    public Location get(Object key) {
        return locations.get(key);
    }

    @Override
    public Location put(Integer key, Location value) {
        return locations.put(key, value);
    }

    @Override
    public Location remove(Object key) {
        return locations.remove(key);
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends Location> m) {

    }

    @Override
    public void clear() {
        locations.clear();

    }

    @Override
    public Set<Integer> keySet() {
        return locations.keySet();
    }

    @Override
    public Collection<Location> values() {
        return locations.values();
    }

    @Override
    public Set<Entry<Integer, Location>> entrySet() {
        return locations.entrySet();
    }
}
