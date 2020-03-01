package views;

import java.util.Vector;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jeu.Case;
import jeu.Game;

public class GamePane extends HBox {

	// attributs
	private GridPane grid = new GridPane();
	private FlowPane flow = new FlowPane(Orientation.VERTICAL);
	private Game game = new Game();
	private Button boutonQuitter = new Button("Quitter");
	private Button relancerPartie= new Button("Relancer la partie");
	private Vector<Case> cases = new Vector<Case>();
	private Label scorePlayer1=new Label("");
	private Label scorePlayer2=new Label("");

	private static final int TAILLEFENETRELARGEUR = 1300;
	private static final int TAILLEFENETRELONGUEUR = 950;

	public GamePane(Stage stage) {
		stage.setHeight(TAILLEFENETRELONGUEUR);
		stage.setWidth(TAILLEFENETRELARGEUR);
		// Background image
		BackgroundSize bSize = new BackgroundSize(100, 100, true, true, true, true);// double width, double height,
																					// boolean widthAsPercentage,
																					// boolean heightAsPercentage,
																					// boolean contain, boolean cover
		Background backgroundMenu = new Background(new BackgroundImage(new Image("res/backgroundMenu.jpg"),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, bSize));
		this.setBackground(backgroundMenu);

		// permet d'initialiser la grille de depart (vide)
		gridSetup();

		
		// code for relancerPartie
		relancerPartie.setOnAction((event) -> {
			game=new Game(game.getPlayer1(),game.getPlayer2());
			cases=new Vector<Case>();
			gridSetup();
			for (Case i : cases) {
				grid.getChildren().add(i.getImageView());
			}
			
		});
		relancerPartie.setFont(Font.font("Verdana", 20));
		relancerPartie.setMinWidth(150);
		flow.getChildren().add(relancerPartie);
		
		
		// code for BoutonQuitter
		boutonQuitter.setOnAction((event) -> {
			Platform.exit();
		});
		boutonQuitter.setFont(Font.font("Verdana", 20));
		boutonQuitter.setMinWidth(150);
		flow.getChildren().add(boutonQuitter);
		
		
		//code for label
		scorePlayer1.setText("Score joueur 1 : "+game.getPlayer1().getScore());
		scorePlayer2.setText("Score joueur 2 : "+game.getPlayer2().getScore());
		scorePlayer1.setFont(Font.font("Verdana", 20));
		scorePlayer2.setFont(Font.font("Verdana", 20));
		flow.getChildren().add(this.scorePlayer1);
		flow.getChildren().add(this.scorePlayer2);

		// code for grid
		grid.setGridLinesVisible(true);
		for (Case i : cases) {
			grid.getChildren().add(i.getImageView());
		}

		// setting grid to the left and flow to the right
		this.getChildren().addAll(grid, flow);

	}

	public void gridSetup() {
		// case 0,0
		int m = 0;

		for (int l = 0; l < 6; l++) {
			for (int c = 0; c < 7; c++) {

				cases.add(new Case(new ImageView("res/blankImage.png"), l, c));

				cases.get(m).getImageView().setFitHeight(150);
				cases.get(m).getImageView().setFitWidth(150);
				cases.get(m).getImageView().setOnMouseClicked(new handler(m));
				GridPane.setConstraints(cases.get(m).getImageView(), c, l);
				m++;
			}
		}

	}

	class handler implements EventHandler<MouseEvent> {
		int m;

		public handler(int m) {
			this.m = m;
		}

		@Override
		public void handle(MouseEvent arg0) {

			Case caseClicked = cases.get(m);
			int ligne;
			int colonne = caseClicked.getY();
//			System.out.println(0);
			if (game.getIsPlayer1Turn()) { // si c'est le tour du joueur 1

//				System.out.println(1);

				if (game.getPlayer1().getToken().contentEquals("blue")) { // si son token est blue
//					System.out.println(2);
					if (this.isThereMoreSpaceInColumn(colonne)) { // s'il y a de l'espace dans la colonne
//						System.out.println(3);
						// System.out.println(this.whichLinetoPutToken(colonne)+" "+colonne);
						ligne=this.whichLinetoPutToken(colonne);
						this.caseFromVectorWithCoordinates(ligne, colonne).getImageView()
								.setImage(new Image("res/blueo.png"));
						this.caseFromVectorWithCoordinates(ligne, colonne).setEmpty(false);
						// System.out.println(cases);
						game.setIsPlayer1Turn(false);
						
						try {
							game.getGrid().setCase(ligne, colonne, "blue");
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println(game.isFinished());
//						System.out.println(game.getGrid().getCase(5, 6));
//						System.out.println(game.getGrid().getCase(4, 6));
//						System.out.println(game.getGrid().getCase(3, 6));
//						System.out.println(game.getGrid().getCase(2, 6));
//						System.out.println(game.getGrid().getCase(1, 6));
//						System.out.println(game.getGrid().getCase(0, 6));
						if(game.isFinished()) {
							
							game.getPlayer1().incrementScore(1);
							scorePlayer1.setText("Score joueur 1 : "+game.getPlayer1().getScore());
							scorePlayer2.setText("Score joueur 2 : "+game.getPlayer2().getScore());
							game=new Game(game.getPlayer1(),game.getPlayer2());
							cases=new Vector<Case>();
							gridSetup();
							for (Case i : cases) {
								grid.getChildren().add(i.getImageView());
							}
							
							
							
						}
						

					}
				}

				else {
//					System.out.println(3.5);

					if (this.isThereMoreSpaceInColumn(colonne)) {
						ligne=this.whichLinetoPutToken(colonne);
//						System.out.println(4);
						this.caseFromVectorWithCoordinates(ligne, colonne).getImageView()
								.setImage(new Image("res/redx.png"));
						this.caseFromVectorWithCoordinates(ligne, colonne).setEmpty(false);
						game.setIsPlayer1Turn(false);
						
						try {
//							System.out.println("hello3");
							game.getGrid().setCase(ligne, colonne, "red");
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println(game.isFinished());
						if(game.isFinished()) {
							game.getPlayer1().incrementScore(1);
							game=new Game(game.getPlayer1(),game.getPlayer2());
							cases=new Vector<Case>();
							gridSetup();
							for (Case i : cases) {
								grid.getChildren().add(i.getImageView());
							}
							
							scorePlayer1.setText("Score joueur 1 : "+game.getPlayer1().getScore());
							scorePlayer2.setText("Score joueur 2 : "+game.getPlayer2().getScore());
							
						}
						

						
					}

				}

			}

			else {
//				System.out.println(5);
				if (game.getPlayer2().getToken().contentEquals("blue")) {
//					System.out.println(6);
					if (this.isThereMoreSpaceInColumn(colonne)) {
						ligne=this.whichLinetoPutToken(colonne);
						System.out.println(7);
						this.caseFromVectorWithCoordinates(ligne, colonne).getImageView()
								.setImage(new Image("res/blueo.png"));
						this.caseFromVectorWithCoordinates(ligne, colonne).setEmpty(false);
						game.setIsPlayer1Turn(true);
						
						try {
							game.getGrid().setCase(ligne, colonne, "blue");
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println(game.isFinished());
						if(game.isFinished()) {
							game.getPlayer2().incrementScore(1);
							game=new Game(game.getPlayer1(),game.getPlayer2());
							cases=new Vector<Case>();
							gridSetup();
							for (Case i : cases) {
								grid.getChildren().add(i.getImageView());
							}
							
							scorePlayer1.setText("Score joueur 1 : "+game.getPlayer1().getScore());
							scorePlayer2.setText("Score joueur 2 : "+game.getPlayer2().getScore());
							
						}
						

					}
				}

				else {
//					System.out.println(8);
					if (this.isThereMoreSpaceInColumn(colonne)) {
						ligne=this.whichLinetoPutToken(colonne);
//						System.out.println(9);
						this.caseFromVectorWithCoordinates(ligne, colonne).getImageView()
								.setImage(new Image("res/redx.png"));
						this.caseFromVectorWithCoordinates(ligne, colonne).setEmpty(false);
						game.setIsPlayer1Turn(true);
						
						try {
							game.getGrid().setCase(ligne, colonne, "red");
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println(game.isFinished());
						
						if(game.isFinished()) {
							game.getPlayer2().incrementScore(1);
							game=new Game(game.getPlayer1(),game.getPlayer2());
							cases=new Vector<Case>();
							gridSetup();
							for (Case i : cases) {
								grid.getChildren().add(i.getImageView());
							}
							
							scorePlayer1.setText("Score joueur 1 : "+game.getPlayer1().getScore());
							scorePlayer2.setText("Score joueur 2 : "+game.getPlayer2().getScore());
							
						}
						
						
//						System.out.println(game.getGrid().getCase(5, 6));
//						System.out.println(game.getGrid().getCase(4, 6));
//						System.out.println(game.getGrid().getCase(3, 6));
//						System.out.println(game.getGrid().getCase(2, 6));
//						System.out.println(game.getGrid().getCase(1, 6));
//						System.out.println(game.getGrid().getCase(0, 6));


					}
				}

			}

		}

		public boolean isThereMoreSpaceInColumn(int colonne) {
			int howManyTokens = 0;
			for (Case c : cases) {
				if (c.getY() == colonne) {
					if (!c.isEmpty()) {
						howManyTokens++;
					}
				}
			}

			return true;
		}

		public int whichLinetoPutToken(int colonne) {
			Vector<Case> cases2 = new Vector<>();
			for (Case c : cases) {
				if (c.getY() == colonne) {
					cases2.add(c);
				}
			}

			sortFromLine(cases2);
			// System.out.println(cases2);
			for (int i = cases2.size() - 1; i >= 0; i--) {
				// System.out.println(cases2.get(i));
				if (cases2.get(i).isEmpty()) {

					return i;
				}
			}
			System.out.println("no line found");
			return -1;
		}

		public void sortFromLine(Vector<Case> cases2) {
			Case minCase, temp;
			int indexOfSmallest;
			for (int i = 0; i < cases2.size() - 1; i++) {
				indexOfSmallest = indexOfMinList(i + 1, cases2);
				minCase = cases2.get(indexOfSmallest);
				if (minCase.getX() < cases2.get(i).getX()) {
					temp = cases2.get(i);
					cases2.set(i, minCase);
					cases2.set(indexOfSmallest, temp);
				}
			}
		}

		public int indexOfMinList(int i, Vector<Case> cases2) {
			Case min = cases2.get(i);
			int indexMin = i;
			int k = i;
			for (k = i; k < cases2.size(); k++) {
				if (min.getX() > cases2.get(k).getX()) {
					min = cases2.get(k);
					indexMin = k;
				}
			}
			return indexMin;
		}

		public Case caseFromVectorWithCoordinates(int x, int y) {
			for (Case c : cases) {
				if ((c.getX() == x) && (c.getY() == y)) {
					return c;
				}
			}
			System.out.println("rien trouvé");
			return null;
		}

	}

}
