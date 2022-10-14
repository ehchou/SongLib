package song;
import java.io.*;

//@author Ethan Chou, ehc60
//@author Alessandro Gonzaga, amg573


import java.util.*;
public class DataHandler {
	private static String path =  System.getProperty("user.dir")+"/src/data/library.txt";
	
	private static String delim = " ;delim; ";
	
	public static String getDelim() {
		return delim;
	}
	public static void deleteSong(Song song) {
		File file = new File(path);
		Scanner scan;
		List<Song> arr = null;
		try {
			scan = new Scanner(file);
		
			arr = new ArrayList<Song>();
			while(scan.hasNextLine()) {
			String[] temp = scan.nextLine().split(delim);
				if(!temp[0].equals(song.getName()) && 
			       !temp[1].equals(song.getArtist())) {
					arr.add(new Song(temp[0], temp[1], temp[2], temp[3]));
				}
					
				
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		file.delete();
		try {
			file.createNewFile();
			writeList(arr, file);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
	}
	
	public static void editSong(Song newSong, Song oldSong) {
		
		File file = new File(path);
		Scanner scan;
		List<Song> arr = null;
		try {
			scan = new Scanner(file);
		
			arr = new ArrayList<Song>();
			while(scan.hasNextLine()) {
				String[] temp = scan.nextLine().split(delim);
				if(temp[0].equals(oldSong.getName()) &&
				   temp[1].equals(oldSong.getArtist())){
					arr.add(newSong);
				}
				else {
					arr.add(new Song(temp[0], temp[1], temp[2], temp[3]));
				}
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		file.delete();
		try {
			file.createNewFile();
			writeList(arr, file);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void addSong(Song song) {
		File file = new File(path);
		FileWriter fr = null;
		try {
			if(!file.exists()) file.createNewFile();
			fr = new FileWriter(file, true);
			BufferedWriter br = new BufferedWriter(fr);
			br.write(songToString(song));
			br.close();
			fr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static List<Song> generateList(){
		File file = new File(path);
		Scanner scan;
		List<Song> arr = null;
		try {
		
			if(!file.exists()) file.createNewFile();
			scan = new Scanner(file);
		
			arr = new ArrayList<Song>();
			while(scan.hasNextLine()) {
				String temp[] = scan.nextLine().split(delim);
				arr.add(new Song(temp[0], temp[1], temp[2], temp[3]));
			}
			
			scan.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arr;
		
	}
	
	private static void writeList(List<Song> Songs, File file) {
		FileWriter fr = null;
		try {
			fr = new FileWriter(file, true);
			BufferedWriter br = new BufferedWriter(fr);
			for(int i = 0; i < Songs.size(); i++) {
				br.write(songToString(Songs.get(i)));
			}
			br.close();
			fr.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
			 
		
	}
	
	private static String songToString(Song song) {
		String out = song.getName() + delim; 
		out = out + song.getArtist() + delim;
		if(song.getYear() == null || song.getYear() == " ") out = out + " " + delim;
		else out = out + song.getYear() + delim;
		if(song.getAlbum() == null || song.getAlbum() == " ") out = out + " " + delim + "\n";
		else out = out + song.getYear() + delim + "\n";
		
		return out;
	}
}