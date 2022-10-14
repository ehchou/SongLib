package songstage;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import song.Song;
import song.DataHandler;

//@author Ethan Chou, ehc60
//@author Alessandro Gonzaga, amg573

public class SongController {
	@FXML AnchorPane total;
	@FXML Button display;
	@FXML Button detail;
	@FXML Button ade;
	@FXML ListView<Song> songtable;
	@FXML Button close;
	@FXML GridPane inputFields;
	@FXML TextField nameIn;
	@FXML TextField artistIn;
	@FXML TextField yearIn;
	@FXML TextField albumIn;
	@FXML Button add;
	@FXML Button edit;
	@FXML Button delete;
	@FXML Button cancel;
	@FXML Button ok;
	
	private ObservableList<Song> songlist;
	private Song selectedSong;
	
	public void start() {
		songtable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		songtable.setCellFactory(param -> new ListCell<Song>() {
			protected void updateItem(Song song, boolean empty) {
				super.updateItem(song, empty);
				if(song == null || empty) {
					setText(null);
				}
				else {
					setText(song.getName() + "   -   " + song.getArtist());
				}
			}
		});
		inputFields.add(new Text("Name:"), 0, 0);
		inputFields.add(new Text("Artist:"), 0, 1);
		inputFields.add(new Text("Year:"), 0, 2);
		inputFields.add(new Text("Album:"), 0, 3);
		//FXCollections.observableArrayList(List<T>), can input List<Song>....
		
		display.setTooltip(new Tooltip("Display your song library."));
		detail.setTooltip(new Tooltip("Display the details of the song you've selected."));
		ade.setTooltip(new Tooltip("If no song is selected, ADD a new song to your library." + System.lineSeparator() + 
				"If a song is selected, EDIT or DELETE that song in your library."));
		songlist = FXCollections.observableArrayList(
				DataHandler.generateList()
				);
		songtable.setItems(songlist);
		
	}
	
	public void deselect(MouseEvent e) {
		songtable.getSelectionModel().clearSelection();
	}
	
	public void useDisplay() {
		songtable.setVisible(!songtable.isVisible());
		songlist.sort((a,b) -> Song.alphOrder(a,b));
		songtable.getSelectionModel().select(0);
	}
	
	public void detailSong() {
		if(songtable.getSelectionModel().isEmpty()) {
			displayNoSelection();
			return;
		}
		
		selectedSong = songtable.getSelectionModel().getSelectedItem();
		songtable.setVisible(false);
		inputFields.setVisible(true);
		nameIn.setText(selectedSong.getName());
		artistIn.setText(selectedSong.getArtist());
		yearIn.setText(selectedSong.getYear());
		albumIn.setText(selectedSong.getAlbum());
		nameIn.setEditable(false);
		artistIn.setEditable(false);
		yearIn.setEditable(false);
		albumIn.setEditable(false);
		ok.setVisible(true);
		detail.setDisable(true);
		ade.setDisable(true);
		display.setDisable(true);
		
	}
	
	public void ok() {
		inputFields.setVisible(false);
		songtable.setVisible(true);
		nameIn.clear();
		artistIn.clear();
		yearIn.clear();
		albumIn.clear();
		nameIn.setEditable(true);
		artistIn.setEditable(true);
		yearIn.setEditable(true);
		albumIn.setEditable(true);
		ok.setVisible(false);
		ade.setDisable(false);
		display.setDisable(false);
		detail.setDisable(false);
		
	}
	
	public void changeSong() {
		songtable.setVisible(false);
		inputFields.setVisible(true);
		display.setDisable(true);
		detail.setDisable(true);
		ade.setDisable(true);
		cancel.setVisible(!cancel.isVisible());
		close.setVisible(!close.isVisible());
		if(songtable.getSelectionModel().getSelectedItem() == null) {
			add.setVisible(!add.isVisible());
		}
		else {
			selectedSong = songtable.getSelectionModel().getSelectedItem();
			nameIn.setText(selectedSong.getName());
			artistIn.setText(selectedSong.getArtist());
			yearIn.setText(selectedSong.getYear());
			if(selectedSong.getYear().compareTo(" ") == 0) yearIn.setText(null);
			albumIn.setText(selectedSong.getAlbum());
			if(selectedSong.getAlbum().compareTo(" ") == 0) albumIn.setText(null);
			edit.setVisible(!edit.isVisible());
			delete.setVisible(!delete.isVisible());
		}
	}
	
	public boolean existsInList(Song newSong) {
		for(Song exist: songlist) {
			if(newSong.getName().compareToIgnoreCase(exist.getName()) == 0 && newSong.getArtist().compareToIgnoreCase(exist.getArtist()) == 0) {
				Alert here = new Alert(AlertType.ERROR, "This song already exists in your library.", ButtonType.OK);
				here.showAndWait();
				return true;
			}
		}
		return false;
	}
	
	public boolean validSong(Song tester) {
		if(tester.getArtist().compareTo(" ") == 0 || tester.getName().compareTo(" ") == 0){
			Alert invalid = new Alert(AlertType.ERROR, "Please enter at least a song name and artist.", ButtonType.OK);
			invalid.showAndWait();
			return false;
		}
		
		try {
			if(!tester.getYear().equals(" ") && Integer.parseInt(tester.getYear()) < 0) { 
				Alert invalid = new Alert(AlertType.ERROR, "Year must be a positive integer.", ButtonType.OK);
				invalid.showAndWait();
				return false;
			}
			
		} catch(NumberFormatException nfe) {
			Alert invalid = new Alert(AlertType.ERROR, "Year must be a positive integer.", ButtonType.OK);
			invalid.showAndWait();
			return false;
		}
		
		
		if(tester.getName().contains("|") || tester.getArtist().contains("|") ||
				   tester.getYear().contains("|") || tester.getAlbum().contains("|")) {
					Alert invalid = new Alert(AlertType.ERROR, " | is a restricted character.", ButtonType.OK);
					invalid.showAndWait();
					return false;
				}
		if(tester.getName().contains(DataHandler.getDelim()) || tester.getArtist().contains(DataHandler.getDelim()) ||
		   tester.getYear().contains(DataHandler.getDelim()) || tester.getAlbum().contains(DataHandler.getDelim())) {
			Alert invalid = new Alert(AlertType.ERROR, DataHandler.getDelim() + " Is a restricted sequence of characters.", ButtonType.OK);
			invalid.showAndWait();
			return false;
		}
		return true;
	}
	
	public void addSong() {
		Song newSong = new Song(nameIn.getText(), artistIn.getText(), yearIn.getText(), albumIn.getText());
		if(yearIn.getText() == null || yearIn.getText().equals(" ")) newSong.setYear(null);
		if(albumIn.getText() == null || albumIn.getText().equals(" ")) newSong.setAlbum(null);
		if (displayConfirmation(newSong, "ADD")) {
			if(validSong(newSong) && !existsInList(newSong)) {
				songlist.add(newSong);
				DataHandler.addSong(newSong);
				songtable.getSelectionModel().select(newSong);
				songlist.sort((a,b) -> Song.alphOrder(a,b));
				cancel();
			}
		}
	}
	
	public void editSong() {
		Song edited = new Song(nameIn.getText(), artistIn.getText(), yearIn.getText(), albumIn.getText());
		if(yearIn.getText() == null || yearIn.getText().equals("")) edited.setYear(null);
		if(albumIn.getText() == null || albumIn.getText().equals("")) edited.setAlbum(null);
		if(displayConfirmation(edited, "EDIT")) {
			if (validSong(edited)) {
				if (edited.getName().compareToIgnoreCase(selectedSong.getName()) == 0 && edited.getArtist().compareToIgnoreCase(selectedSong.getArtist()) == 0) {
					songlist.add(edited);
					DataHandler.editSong(edited, selectedSong);
					songlist.remove(selectedSong);
					songtable.getSelectionModel().select(edited);
					songlist.sort((a,b) -> Song.alphOrder(a,b));
					cancel();
				}
				else if(!existsInList(edited)) {
					songlist.add(edited);
					DataHandler.editSong(edited, selectedSong);
					songlist.remove(selectedSong);
					songtable.getSelectionModel().select(edited);
					songlist.sort((a,b) -> Song.alphOrder(a,b));
					cancel();
				}
			}
		}
	}
	
	
	public void deleteSong() {
		if(displayConfirmation(selectedSong, "DELETE")) {
		
			DataHandler.deleteSong(selectedSong);
			songlist.remove(selectedSong);
			songlist.sort((a,b) -> Song.alphOrder(a,b));
			cancel();
		}
	}
	
	public void cancel() {
		inputFields.setVisible(false);
		add.setVisible(false);
		edit.setVisible(false);
		delete.setVisible(false);
		cancel.setVisible(false);
		nameIn.setText(null);
		artistIn.setText(null);
		yearIn.setText(null);
		albumIn.setText(null);
		songtable.setVisible(true);
		close.setVisible(true);
		ade.setDisable(false);
		display.setDisable(false);
		detail.setDisable(false);
		songlist.sort((a,b) -> Song.alphOrder(a,b));
	}
	
	public void exit(){
		Platform.exit();
		System.exit(0);
	}
	
	public void displayNoSelection() {
		Alert alert = new Alert(AlertType.ERROR, "No song has been selected.", ButtonType.OK);
		alert.showAndWait();
	}
	
	public boolean displayConfirmation(Song selectedSong, String action) {
		Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to " + action + " this song?" + System.lineSeparator() + System.lineSeparator() + 
				"Name:\t" + selectedSong.getName() + System.lineSeparator() +  
				"Artist:\t" + selectedSong.getArtist() + System.lineSeparator() + 
				"Year:\t\t" + selectedSong.getYear() + System.lineSeparator() + 
				"Album:\t" + selectedSong.getAlbum(), ButtonType.YES, ButtonType.NO);
		alert.showAndWait();
		return alert.getResult() == ButtonType.YES;
	}
}