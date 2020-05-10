package jeu;

import java.io.File;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import views.Menu;

public class Main extends Application{
	
	//l
	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Puissance 4");
		
		
		stage.setScene(new Scene(new Menu(stage)));
		stage.setHeight(500);
		stage.setWidth(500);
		stage.show();
		
	}
	
	public static void main(String[] args) {
		launch(args);

	
		}

}
