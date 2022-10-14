package song;

import javafx.beans.property.SimpleStringProperty;
//@author Ethan Chou, ehc60
//@author Alessandro Gonzaga, amg573

public class Song {
	private SimpleStringProperty name;
	private SimpleStringProperty artist;
	private SimpleStringProperty year;
	private SimpleStringProperty album;
	
	public Song(String name, String artist, String year, String album) {
		this.name = new SimpleStringProperty(name.strip());
		this.artist = new SimpleStringProperty(artist.strip());
		if(year != null) this.year = new SimpleStringProperty(year.strip());
		else this.year = new SimpleStringProperty(null);
		if(album != null) this.album = new SimpleStringProperty(album.strip());
		else this.album = new SimpleStringProperty(null);
	}
	
	public String getName() {
		return this.name.get();
	}
	public void setName(String name) {
		this.name.set(name);
	}
	
	public String getArtist() {
		return this.artist.get();
	}
	public void setArtist(String artist) {
		this.artist.set(artist);
	}
	
	public String getYear() {
		String here = this.year.get();
		if(here == null || here == "") return " ";
		return here;
	}
	public void setYear(String year) {
		this.year.set(year);
	}
	
	public String getAlbum() {
		String here = this.album.get();
		if(here == null || here == "") return " ";
		return here;
	}
	
	public void setAlbum(String album) {
		this.album.set(album);
	}
	
	public static int alphOrder(Song a, Song b) {
		if(a.getName().compareToIgnoreCase(b.getName()) != 0) return a.getName().compareToIgnoreCase(b.getName());
		return a.getArtist().compareToIgnoreCase(b.getArtist());
	}
	
	public String useName(Song a) {
		return a.getName() + "\t\t\t" +  a.getArtist();
	}
}
