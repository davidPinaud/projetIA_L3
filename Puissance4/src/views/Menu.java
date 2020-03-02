package views;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Menu extends BorderPane{
	
	private Button boutonJouer,boutonQuitter;
	private FlowPane flow=new FlowPane(Orientation.VERTICAL);
	private Text title=new Text("Puissance 4");
	
	
	public Menu(Stage stage) {
		boutonJouer=new Button("Jouer");
		boutonQuitter=new Button("Quitter");
		
		
		
		//code for title placement and font
		title.setFont(Font.font("Verdana",80));
		title.setFill(Color.BEIGE);
		this.setTop(title);
		BorderPane.setAlignment(title, Pos.CENTER);
		BorderPane.setMargin(title,new Insets(30,10,10,10)); //top right bottom left
		
		// code for button Jouer placement, font and listener
		boutonJouer.setOnAction((event)->{
			stage.setScene(new Scene(new GamePane(stage)));
		});
		boutonJouer.setFont(Font.font("Verdana",20));
		boutonJouer.setMinWidth(150);
		flow.getChildren().add(boutonJouer);
		
		//code for button Quitter placement, font and listener
		boutonQuitter.setOnAction((event)->{
			Platform.exit();
		});
		boutonQuitter.setFont(Font.font("Verdana",20));
		boutonQuitter.setMinWidth(150);
		flow.getChildren().add(boutonQuitter);
		FlowPane.setMargin(boutonQuitter, new Insets(20,0,0,0));

		
		//code for background placement
		BackgroundSize bSize = new BackgroundSize(100, 100, true, true, true, true);//double width, double height, boolean widthAsPercentage, boolean heightAsPercentage, boolean contain, boolean cover
		Background backgroundMenu=new Background(new BackgroundImage(new Image("res/backgroundMenu.jpg"),
				BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.CENTER,
				bSize));
		this.setBackground(backgroundMenu);
		
		//code adding FlowPane to BorderPane
		flow.setAlignment(Pos.CENTER);
		this.setCenter(flow);
		BorderPane.setAlignment(flow,Pos.CENTER);
		
	}
}
