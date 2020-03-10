package views;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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

public class NameView extends BorderPane{
	private FlowPane flow = new FlowPane(Orientation.VERTICAL);
	private Text title = new Text("Veuillez rentrer vos pseudos");
	private Button boutonConfirm=new Button("OK");
	private Button boutonQuitter=new Button("Quitter");
	private TextField pseudo1,pseudo2;
	
	public NameView(Stage stage) {
		stage.setHeight(500);
		stage.setWidth(800);
		
		pseudo1=new TextField();
		pseudo2=new TextField();
		pseudo1.setPromptText("pseudo du joueur 1");
		pseudo2.setPromptText("pseudo du joueur 2");
		pseudo1.setMinWidth(150);
		pseudo1.setMaxWidth(150);
		pseudo2.setMinWidth(150);
		pseudo2.setMaxWidth(150);
		flow.getChildren().addAll(pseudo1,pseudo2);
		FlowPane.setMargin(pseudo2, new Insets(10,0,10,0));
		
		
		boutonConfirm.setOnAction((event)->{
			stage.setScene(new Scene(new GamePane(stage,pseudo1.getText(),pseudo2.getText())));
		});
		boutonConfirm.setFont(Font.font("Verdana", 20));
		boutonConfirm.setMinWidth(150);
		flow.getChildren().add(boutonConfirm);
		FlowPane.setMargin(boutonConfirm, new Insets(10, 0, 0, 0));
		
		boutonQuitter.setOnAction((event) -> {
			Platform.exit();
		});
		boutonQuitter.setFont(Font.font("Verdana", 20));
		boutonQuitter.setMinWidth(150);
		flow.getChildren().add(boutonQuitter);
		FlowPane.setMargin(boutonQuitter, new Insets(50, 0, 0, 0));
		

		title.setFont(Font.font("Verdana", 40));
		title.setFill(Color.BEIGE);
		this.setTop(title);
		BorderPane.setAlignment(title, Pos.CENTER);
		BorderPane.setMargin(title, new Insets(30, 10, 10, 10)); // top right bottom left
		
		BackgroundSize bSize = new BackgroundSize(100, 100, true, true, true, true);// double width, double height,
		// boolean widthAsPercentage,
		// boolean heightAsPercentage,
		// boolean contain, boolean cover
		Background backgroundMenu = new Background(new BackgroundImage(new Image("res/backgroundMenu.jpg"),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, bSize));
		this.setBackground(backgroundMenu);
		
		flow.setAlignment(Pos.CENTER);
		this.setCenter(flow);
		BorderPane.setAlignment(flow, Pos.CENTER);
		
	}
	
	
}
