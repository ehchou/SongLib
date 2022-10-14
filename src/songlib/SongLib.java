package songlib;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import songstage.SongController;

//@author Ethan Chou, ehc60
//@author Alessandro Gonzaga, amg573


public class SongLib extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception{
    	primaryStage.setTitle("Song Library");
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource("/songstage/songstage.fxml"));
    	AnchorPane root = (AnchorPane)loader.load();
    	
    	SongController sc = loader.getController();
    	sc.start();
    	
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
    	primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}

