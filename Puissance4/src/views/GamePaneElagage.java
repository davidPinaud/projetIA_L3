package views;

import java.util.Comparator;
import java.util.Optional;
import java.util.Random;
import java.util.Vector;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
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
import javafx.stage.Stage;
import jeu.Case;
import jeu.Elagage;
import jeu.Etat;
import jeu.Game;
import jeu.Grid;


public class GamePaneElagage extends HBox {
	// attributs
	private GridPane grid = new GridPane();
	private FlowPane flow = new FlowPane(Orientation.VERTICAL);
	private Game game = new Game();
	private Button boutonQuitter = new Button("Quitter");
	private Button relancerPartie = new Button("Relancer la partie");
	private Button retourAuMenuButton = new Button("Retour au Menu");

	private Vector<Case> cases = new Vector<Case>();
	private Label scorePlayer1 = new Label("");
	private Label scorePlayer2 = new Label("");
	private Label quiAGagne = new Label("");
	private String pseudo1, pseudo2;
	int profondeur;

	private static final int TAILLEFENETRELARGEUR = 1375;
	private static final int TAILLEFENETRELONGUEUR = 925;

	public GamePaneElagage(Stage stage, String pseudo1, String pseudo2,String difficulte) {
		profondeur=difficulte=="plutôt facile"?1:5;
		this.pseudo1 = pseudo1;
		this.pseudo2 = pseudo2;
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

		// code for label
		scorePlayer1.setText(pseudo1 + " : " + game.getPlayer1().getScore());
		scorePlayer2.setText(pseudo2 + " : " + game.getPlayer2().getScore());
		scorePlayer1.setFont(Font.font("Verdana", 30));
		scorePlayer2.setFont(Font.font("Verdana", 30));
		flow.getChildren().add(this.scorePlayer1);
		flow.getChildren().add(this.scorePlayer2);
		flow.getChildren().add(this.quiAGagne);
		FlowPane.setMargin(quiAGagne, new Insets(100, 0, 0, 0));
		FlowPane.setMargin(this.scorePlayer1, new Insets(10, 0, 0, 0));
		FlowPane.setMargin(this.scorePlayer2, new Insets(30, 0, 0, 0));

		// code for relancerPartie
		relancerPartie.setOnAction((event) -> {
			game = new Game(game.getPlayer1(), game.getPlayer2());
			cases = new Vector<Case>();
			gridSetup();
			for (Case i : cases) {
				grid.getChildren().add(i.getImageView());
			}

		});
		relancerPartie.setFont(Font.font("Verdana", 20));
		relancerPartie.setMinWidth(150);
		flow.getChildren().add(relancerPartie);
		FlowPane.setMargin(this.relancerPartie, new Insets(500, 0, 0, 0));
		retourAuMenuButton.setOnAction((event) -> {
			stage.setScene(new Scene(new Menu(stage)));
		});
		flow.getChildren().add(retourAuMenuButton);
		boutonQuitter.setFont(Font.font("Verdana", 20));
		retourAuMenuButton.setMinWidth(150);
		FlowPane.setMargin(retourAuMenuButton,new Insets(10,0,0,0));
		// code for BoutonQuitter
		boutonQuitter.setOnAction((event) -> {
			Platform.exit();
		});
		boutonQuitter.setFont(Font.font("Verdana", 20));
		boutonQuitter.setMinWidth(150);
		flow.getChildren().add(boutonQuitter);
		FlowPane.setMargin(this.boutonQuitter, new Insets(10, 0, 0, 0));

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
			if (game.getIsPlayer1Turn()) { // si c'est le tour du joueur 1
				if (game.getPlayer1().getToken().contentEquals("blue")) { // si son token est blue
					if (this.isThereMoreSpaceInColumn(colonne)) { // s'il y a de l'espace dans la colonne
						ligne = this.whichLinetoPutToken(colonne);
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

						if (game.isFinished()) {
							quiAGagne.setText(pseudo1 + " a gagné");
							quiAGagne.setFont(Font.font("Verdana", 30));
							game.getPlayer1().incrementScore(1);
							scorePlayer1.setText(pseudo1 + " : " + game.getPlayer1().getScore());
							scorePlayer2.setText(pseudo2 + " : " + game.getPlayer2().getScore());
							Alert alert = new Alert(AlertType.CONFIRMATION);
							alert.setTitle("Partie terminée");
							alert.setHeaderText(pseudo1 + " a gagné");
							alert.setContentText(pseudo1 + " : " + game.getPlayer1().getScore() + "\n" + pseudo2 + " : "
									+ game.getPlayer2().getScore());
							ButtonType buttonTypeRelancerButtonType = new ButtonType("Relancer une partie");
							ButtonType buttonTypeQuitterLeJeuButtonType = new ButtonType("Quitter le jeu");
							alert.getButtonTypes().setAll(buttonTypeRelancerButtonType,
									buttonTypeQuitterLeJeuButtonType);
							Optional<ButtonType> result = alert.showAndWait();
							if (result.get() == buttonTypeQuitterLeJeuButtonType) {
								Platform.exit();
							} else if (result.get() == buttonTypeRelancerButtonType) {
								game = new Game(game.getPlayer1(), game.getPlayer2());
								cases = new Vector<Case>();
								gridSetup();
								for (Case i : cases) {
									grid.getChildren().add(i.getImageView());
								}
							}

						}

						// L'IA joue
						Elagage minimax = new Elagage(GamePaneElagage.this.game, profondeur); // calcul des états possible puis
																					// les utilités de ces états
						Etat etatParent = minimax.getEtatParent();
						System.out.println("etat Parent " + etatParent);
						Etat etatChoisi = null;
						Boolean notFound=true;
						//Vector<Etat> etatEnfantsConvenablesEtats=new Vector<>();
						
						for(Etat etatEnfant : etatParent.getEnfants()) {
							if(etatEnfant.getUtilite()==2147483647) {
								etatChoisi =etatEnfant;
								notFound=false;
								break;
							}
							/*else if(etatEnfant.getUtilite()==etatParent.getUtilite()){
								etatEnfantsConvenablesEtats.add(etatEnfant);
							}*/
						}
						
						/*if(notFound&!etatEnfantsConvenablesEtats.isEmpty()) {
							Random random=new Random();
							int index=random.nextInt(etatEnfantsConvenablesEtats.size());
							//choix d'un enfant convenable au hasard
							etatChoisi=etatEnfantsConvenablesEtats.get(index);
						}else {
							Vector<Etat> etatEnfantsVector=new Vector<Etat>();
							for(Etat etatEnfant : etatParent.getEnfants()) {
								etatEnfantsVector.add(etatEnfant);
							}
							etatEnfantsVector.sort(new Comparator<Etat>() {
								@Override
								public int compare(Etat etat1, Etat etat2) {
									return etat1.getUtilite()-etat2.getUtilite();
								}
							});
							
							Random random=new Random();
							int index=etatEnfantsVector.size()-(random.nextInt(2)+1);
							//prends au hasard un des 2 meilleurs états.
							etatChoisi=etatEnfantsVector.get(index);
						}*/
						
						if(notFound) {
							Vector<Etat> etatEnfantsVector=new Vector<Etat>();
							for(Etat etatEnfant : etatParent.getEnfants()) {
								etatEnfantsVector.add(etatEnfant);
							}
							etatEnfantsVector.sort(new Comparator<Etat>() {
								@Override
								public int compare(Etat etat1, Etat etat2) {
									return etat1.getUtilite()-etat2.getUtilite();
								}
							});
							
							Random random=new Random();
							int index=etatEnfantsVector.size()-(random.nextInt(2)+1);
							//prends au hasard un des 2 meilleurs états.
							etatChoisi=etatEnfantsVector.get(index);
						}
						
						
						// on regarde les différences entre les grilles du jeu et celui de l'état
						// suivant
						System.out.println("etat choisi " + etatChoisi + "\n------------------");
						int ligneIA = 0, colonneIA = 0;
						Grid gridEnfant = etatChoisi.getGrid();
						for (int lig = 0; lig <= gridEnfant.getNB_LIGNE(); lig++) {
							for (int col = 0; col <= gridEnfant.getNB_COLONNE(); col++) {
								if (!gridEnfant.getCase(lig, col).contentEquals(game.getGrid().getCase(lig, col))) {
									ligneIA = lig;
									colonneIA = col;
									break;
								}
							}
						}
						// On met le choix de l'ia dans l'interface et dans le game
						this.caseFromVectorWithCoordinates(ligneIA, colonneIA).getImageView()
								.setImage(new Image("res/redx.png"));
						this.caseFromVectorWithCoordinates(ligneIA, colonneIA).setEmpty(false);
						try {
							game.getGrid().setCase(ligneIA, colonneIA, "red");
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if (game.isFinished()) {
							quiAGagne.setText(pseudo2 + " a gagné");
							quiAGagne.setFont(Font.font("Verdana", 30));
							game.getPlayer2().incrementScore(1);
							Alert alert = new Alert(AlertType.CONFIRMATION);
							alert.setTitle("Partie terminée");
							alert.setHeaderText(pseudo2 + " a gagné");
							alert.setContentText(pseudo1 + " : " + game.getPlayer1().getScore() + "\n" + pseudo2 + " : "
									+ game.getPlayer2().getScore());
							ButtonType buttonTypeRelancerButtonType = new ButtonType("Relancer une partie");
							ButtonType buttonTypeQuitterLeJeuButtonType = new ButtonType("Quitter le jeu");
							alert.getButtonTypes().setAll(buttonTypeRelancerButtonType,
									buttonTypeQuitterLeJeuButtonType);
							Optional<ButtonType> result = alert.showAndWait();
							if (result.get() == buttonTypeQuitterLeJeuButtonType) {
								Platform.exit();
							} else if (result.get() == buttonTypeRelancerButtonType) {
								game = new Game(game.getPlayer1(), game.getPlayer2());
								cases = new Vector<Case>();
								gridSetup();
								for (Case i : cases) {
									grid.getChildren().add(i.getImageView());
								}
							}

						}
						game.setIsPlayer1Turn(true);

					}
				}

			}

		}
		/**
		 * Méthode qui permet de savoir s'il y a encore un espace dans une certaine colonne donnée
		 * @param colonne un entier qui est le numéro de la colonne a tester
		 * @return un boolean qui indique si il reste une ligne non occupé dans la colonne donnée en paramètre
		 */
		public boolean isThereMoreSpaceInColumn(int colonne) {
			int howManyTokens = 0;
			for (Case c : cases) {
				if (c.getY() == colonne) {
					if (!c.isEmpty()) {
						howManyTokens++;
					}
				}
			}

			return howManyTokens < 6;
		}
		/**
		 * Méthode qui permet de determiner selon la colonne donnée, quelle est la premiere colonne de libre afin d'y inserer un jeton.
		 * Elle doit etre appelée après la méthode isThereMoreSpaceInColumn
		 * @param colonne La colonne a tester
		 * @return un entier qui est la ligne de la premiere case vide dans la colonne donnée
		 */
		public int whichLinetoPutToken(int colonne) {
			Vector<Case> cases2 = new Vector<>();
			for (Case c : cases) {
				if (c.getY() == colonne) {
					cases2.add(c);
				}
			}

			sortFromLine(cases2);
			for (int i = cases2.size() - 1; i >= 0; i--) {
				if (cases2.get(i).isEmpty()) {

					return i;
				}
			}
			return -1;
		}
		/**
		 * Méthode qui sert à ordonner un vecteur de Case selon leur ligne (Tri par selection complexité en O(1764)=O(42^2)=O(n^2))
		 * @param cases2 Le Vector à ordonner
		 */
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
		/**
		 * Méthode qui sert à trouver l'index de la plus petite case d'un vecteur de case en commencant à l'indice i
		 * @param i L'indice ou commencer la recherche
		 * @param cases2
		 * @return
		 */
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
		/**
		 * Méthode qui sert à trouver un objet case du vecteur de case selon ses coordonnées
		 * @param x Ligne de la case
		 * @param y Colonne de la case
		 * @return la case
		 */
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