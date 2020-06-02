package views;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
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
/**
 * Classe permettant d'afficher l'interface graphique pour le choix des pseudos
 * pour un jeu entre un humain et MinMax ou entre un humain et Elagage
 * @author davidpinaud
 *
 */
public class NameViewMiniMax extends BorderPane {
	private FlowPane flow = new FlowPane(Orientation.VERTICAL);
	private Text title = new Text("Veuillez rentrer votre pseudo");
	private Button boutonConfirm = new Button("OK");
	private Button boutonQuitter = new Button("Quitter");
	private Button retourButton=new Button("< Retour");
	private BorderPane entetenBorderPane = new BorderPane();
	private TextField pseudo1;
	private ChoiceBox<String> difficulteChoixBox = new ChoiceBox<String>();

	public NameViewMiniMax(Stage stage, int i) {
		stage.setHeight(500);
		stage.setWidth(800);
		
		

		pseudo1 = new TextField();
		pseudo1.setPromptText("pseudo du joueur 1");
		pseudo1.setMinWidth(150);
		pseudo1.setMaxWidth(150);
		flow.getChildren().addAll(pseudo1);

		difficulteChoixBox.getItems().addAll("plutôt facile", "moins facile");
		flow.getChildren().add(difficulteChoixBox);
		FlowPane.setMargin(difficulteChoixBox, new Insets(10, 0, 0, 20));
		difficulteChoixBox.setValue("plutôt facile");

		boutonConfirm.setOnAction((event) -> {
			if (i == 1) {
				stage.setScene(new Scene(
						new GamePaneMinMax(stage, pseudo1.getText(), "MinMax", difficulteChoixBox.getValue())));
			} else {
				stage.setScene(new Scene(new GamePaneElagage(stage, pseudo1.getText(), "Elagage", difficulteChoixBox.getValue())));
			}
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

		retourButton.setOnAction((event)->{
			stage.setScene(new Scene(new choiceTypeGame(stage)));
		});
		BorderPane.setAlignment(retourButton, Pos.CENTER);
		entetenBorderPane.setLeft(retourButton);
		
		
		title.setFont(Font.font("Verdana", 40));
		title.setFill(Color.BEIGE);
		BorderPane.setAlignment(title, Pos.CENTER);
		entetenBorderPane.setCenter(title);
		this.setTop(entetenBorderPane);

		
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
