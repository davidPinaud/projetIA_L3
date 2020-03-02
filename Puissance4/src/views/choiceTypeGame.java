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

public class choiceTypeGame extends BorderPane {
	private Button boutonHumainContreHumain, boutonHumainContreMinMax, boutonHumainContreElagage, boutonQuitter;
	private FlowPane flow = new FlowPane(Orientation.VERTICAL);
	private Text title = new Text("Veuillez choisir un mode de jeu");

	public choiceTypeGame(Stage stage) {
		stage.setHeight(500);
		stage.setWidth(800);

		title.setFont(Font.font("Verdana", 40));
		title.setFill(Color.BEIGE);
		this.setTop(title);
		BorderPane.setAlignment(title, Pos.CENTER);
		BorderPane.setMargin(title, new Insets(30, 10, 10, 10)); // top right bottom left

		this.boutonHumainContreHumain = new Button("Humain vs Humain");
		this.boutonHumainContreMinMax = new Button("Humain vs MinMax");
		this.boutonHumainContreElagage = new Button("Humain vs Elagage");
		// background code
		BackgroundSize bSize = new BackgroundSize(100, 100, true, true, true, true);// double width, double height,
		// boolean widthAsPercentage,
		// boolean heightAsPercentage,
		// boolean contain, boolean cover
		Background backgroundMenu = new Background(new BackgroundImage(new Image("res/backgroundMenu.jpg"),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, bSize));
		this.setBackground(backgroundMenu);

		// code humain contre humain
		boutonHumainContreHumain.setOnAction((event) -> {
			stage.setScene(new Scene(new GamePane(stage)));
		});
		this.boutonHumainContreHumain.setFont(Font.font("Verdana", 20));
		this.boutonHumainContreHumain.setMinWidth(150);
		flow.getChildren().add(this.boutonHumainContreHumain);
		FlowPane.setMargin(this.boutonHumainContreHumain, new Insets(10, 0, 0, 0));

		// code humain contre minmax
		this.boutonHumainContreMinMax.setOnAction((event) -> {
			// TODO
		});
		this.boutonHumainContreMinMax.setFont(Font.font("Verdana", 20));
		this.boutonHumainContreMinMax.setMinWidth(150);
		flow.getChildren().add(this.boutonHumainContreMinMax);
		FlowPane.setMargin(this.boutonHumainContreMinMax, new Insets(10, 0, 0, 0));

		// code humain contre humain
		this.boutonHumainContreElagage.setOnAction((event) -> {
			// TODO
		});
		this.boutonHumainContreElagage.setFont(Font.font("Verdana", 20));
		this.boutonHumainContreElagage.setMinWidth(150);
		flow.getChildren().add(this.boutonHumainContreElagage);
		FlowPane.setMargin(this.boutonHumainContreElagage, new Insets(10, 0, 0, 0));

		// Bouton quitter
		boutonQuitter = new Button("Quitter");
		boutonQuitter.setOnAction((event) -> {
			Platform.exit();
		});
		boutonQuitter.setFont(Font.font("Verdana", 20));
		boutonQuitter.setMinWidth(150);
		flow.getChildren().add(boutonQuitter);
		FlowPane.setMargin(boutonQuitter, new Insets(50, 0, 0, 37));


		flow.setAlignment(Pos.CENTER);
		this.setCenter(flow);
		BorderPane.setAlignment(flow, Pos.CENTER);

	}
}