package jeu;

import java.util.List;
import java.util.Vector;
/**
 * classe permettant de calculer le coup suivant d'une IA basée sur l'algorithme d'élagage alpha-beta
 * @author davidpinaud
 *
 */
public class Elagage { //alpha beta
	private Game game;
	private Etat etatParent;
	private int[][] grilleHeuristique = { { 3, 4, 5, 7, 5, 4, 3 }, { 4, 6, 8, 10, 8, 6, 4 }, { 5, 8, 11, 13, 11, 8, 5 },
			{ 5, 8, 11, 13, 11, 8, 5 }, { 4, 6, 8, 10, 8, 6, 4 }, { 3, 4, 5, 7, 5, 4, 3 }, };
	private int profondeur;
	int alpha=Integer.MIN_VALUE;
	int beta=Integer.MAX_VALUE;
	
	public Elagage(Game game, int profondeur) {
		super();
		this.game = game;
		etatParent = new Etat(game, game.getGrid());
		this.profondeur = profondeur;
		this.initialiserEtatEnfants(etatParent, this.profondeur, true); // initialisation des enfants d'etatParent
		etatParent.setUtilite(this.elagageAlphaBeta(this.etatParent, this.profondeur, true,alpha,beta));
		System.out.println(etatParent.getEnfants() + "===========================");
	}
		
	
	/**
	 * Cette fonction permet de generer la liste des enfants possible pour l'état
	 * Parent de ce minimax
	 * 
	 * @param etat       : etat pour lequel on calcule les enfants
	 * @param profondeur : profondeur de la génération des enfants
	 * @param isMax      : boolean qui indique pour qui on calcule les enfants
	 */
	private void initialiserEtatEnfants(Etat etat, int profondeur, boolean isMax) {
		List<Integer> colonneJouable = new Vector<>();// liste des colonnes ou on peut mettre une pièce
		Grid grid = etat.getGrid();
		for (int i = 0; i <= grid.getNB_COLONNE(); i++) {
			if (grid.getCase(0, i).equals("")) {// on regarde le premier élément de la colonne
				colonneJouable.add(i);
			}
		}
		List<List<Integer>> listeDePositionJouable = new Vector<>(); // liste des coordonnées de position jouable dans
																		// la grille donné
		// cette liste est une liste de liste tel que :
		// [[ligne,colonne],[ligne,colonne],[ligne,colonne]]
		// cette liste va determiner le nombre d'enfants de l'etat

		for (int c = 0; c < colonneJouable.size(); c++) { // pour toutes les colonnes jouables
			listeDePositionJouable.add(new Vector<Integer>());
			for (int k = 0; k <= grid.getNB_LIGNE(); k++) {
				if (grid.getCase(k, colonneJouable.get(c)) != "") {// on trouve la premiere ligne ou il y a un jeton
					listeDePositionJouable.get(c).add(k - 1); // on ajoute le jeton sur la ligne d'avant
					listeDePositionJouable.get(c).add(colonneJouable.get(c)); // ajout de la colonne associé dans la
																				// meme liste
					break;
				} else if (k == grid.getNB_LIGNE()) {// si il y a rien dans la colonne
					listeDePositionJouable.get(c).add(k);// on ajoute le jeton à la derniere colonne
					listeDePositionJouable.get(c).add(colonneJouable.get(c));// ajout de la colonne associé dans la meme
																				// liste
				}
			}

		}
		// System.out.println("profondeur : "+profondeur+"\nlisteDePositionJouable :
		// "+listeDePositionJouable);

		Grid gridBuffer;
		for (List<Integer> position : listeDePositionJouable) {
			gridBuffer = new Grid();
			grid.copyGrid(gridBuffer);// on copie grid dans GridBuffer
			try {
				gridBuffer.setCase(position.get(0), position.get(1), isMax ? "red" : "blue");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			etat.getEnfants().add(new Etat(this.game, gridBuffer));
		}

		if (profondeur != 0) {
			for (Etat etatEnfant : etat.getEnfants()) {
				initialiserEtatEnfants(etatEnfant, profondeur - 1, !isMax);
			}
		}
	}

	/**
	 * Retourne la valeur d'utilité que doit choisir max i.e. le chemin à prendre
	 * dans l'arbre. Cette fonction permettra à l'IA de choisir le prochain jeton à
	 * jouer
	 * 
	 * @param grid       :l'état du jeu
	 * @param profondeur : la profondeur de recherche souhaité
	 * @param isMax      : boolean qui indique si
	 * @return un tableau d'entiers de taille 2 qui sont les coordonnées du jeton
	 *         joué
	 */
	public int elagageAlphaBeta(Etat etat, int profondeur, boolean isMax,int alpha,int beta) {
		if (profondeur == -1 || this.isFinished(etat.grid)) {
			return valeurDutilite(etat, isMax);
		}
		if (isMax) {
			int v = Integer.MIN_VALUE; // plus petit entier représentable (-2^31)
			for (Etat etatFils : etatParent.getEnfants()) {
				v = Math.max(v,elagageAlphaBeta(etatFils, profondeur - 1, false,alpha,beta));
				alpha=Math.max(alpha, v);
				if(alpha>=beta) {
					break;
				}
			}
			return v;
		} else {
			int v = Integer.MAX_VALUE; // plus grand entier représentable (2^31-1)
			for (Etat etatFils : etatParent.getEnfants()) {
				v = Math.min(v,elagageAlphaBeta(etatFils, profondeur - 1, true,alpha,beta));
				beta=Math.min(beta,v);
				if(alpha>=beta) {
					break;
				}
			}
			return v;
		}
	}
	
	/**
	 * Méthode qui permet de donner une valeur d'utilité à l'état analysé en
	 * appellant les deux heurisitique implémentées
	 * @param etat L'état a analyser
	 * @param isMax Boolean pour savoir si l'appelant est max
	 * @return Un entier correspondant à la valeur d'utilité de l'état
	 */
	public int valeurDutilite(Etat etat, boolean isMax) {
		if (this.isFinished(etat.getGrid())) {
			etat.setUtilite(isMax ? -2147483648 : 2147483647);
			return isMax ? 2147483647 : -2147483648;
		}

		int slash, hori, verti, antiSlash, align;
		antiSlash = this.checkAntiSlashPourValeurUtilite(etat.getGrid(), isMax);
		hori = this.checkHoriPourValeurUtilite(etat.getGrid(), isMax);
		verti = this.checkVertPourValeurUtilite(etat.getGrid(), isMax);
		slash = this.checkSlashPourValeurUtilite(etat.getGrid(), isMax);
		align = this.heuristique2AlignementsGagnants(etat.getGrid(), isMax);

		int valeurUtilite = slash + antiSlash + verti + hori + align;

		etat.setAntislash(antiSlash);
		etat.setSlash(slash);
		etat.setHorizontale(hori);
		etat.setVertical(verti);
		etat.setHeuristique2Alignement(align);
		
		etat.setUtilite(valeurUtilite);

		return valeurUtilite;
	}

	//Constante déterminant la premiere heuristique
	final static int POSITIVE_3=200;
	final static int NEGATIVE_3=-350;
	final static int NEGATIVE_3_BIS=-600;
	final static int POSITIVE_2=10;
	final static int NEGATIVE_2=-50;
	final static int NEGATIVE_1=-5;
	
	/**
	 * Méthode qui permet de calculer la valeur d'utilité d'une grille en comptant les alignements horizontaux 
	 * des jetons
	 * @param grid Grille a analyser
	 * @param isMax Boolean pour savoir si l'appelant est max ou pas
	 * @return Une valeur entiere correspondant a la valeur d'utilité horizontale pour l'appelant
	 */
	public int checkHoriPourValeurUtilite(Grid grid, boolean isMax) {
		int valeur = 0;
		int countTokenBlue = 0;
		int countTokenRed = 0;
		int ligneNb = 0, colonneNb = 0;
		for (String[] ligne : grid.getGrille()) {
			for (String colonne : ligne) {
				switch (colonne) {
				case "blue": {
					countTokenBlue++;
					if (countTokenRed > 0) {
						countTokenBlue = 0;
					}
					if (countTokenBlue == 3 && !isMax) {//Si il y a trois jetons alignés horizontalement
						if (colonneNb + 1 <= 6) { //Si la prochaine case existe
							if (grid.getCase(ligneNb, colonneNb + 1) == "") { //si la prochaine case est vide
								//System.out.println("hey4");
								valeur += POSITIVE_3;
							}
						}
						if (colonneNb - 3 >= 0) {//si la case d'avant les jetons existe
							if (grid.getCase(ligneNb, colonneNb - 3) == "") {//si elle est vide
								//System.out.println("hey5");

								valeur += POSITIVE_3;
							}
						}
						if (colonneNb + 1 <= 6) {//si les deux existent et sont vides
							if (grid.getCase(ligneNb, colonneNb + 1) == "") {
								if (colonneNb - 3 >= 0) {
									if (grid.getCase(ligneNb, colonneNb - 3) == "") {
									//	System.out.println("hey5");

										valeur += POSITIVE_3;
									}
								}
							}
						}
					} else if (countTokenBlue == 2 && !isMax) {
						valeur += POSITIVE_2;
					}
					if (countTokenBlue == 3 && isMax) {
						if (colonneNb + 1 <= 6) {
							if (grid.getCase(ligneNb, colonneNb + 1) == "") {
								//System.out.println("hey1");
								valeur += NEGATIVE_3;
							}
						}
						if (colonneNb - 3 >= 0) {
							if (grid.getCase(ligneNb, colonneNb - 3) == "") {
								//System.out.println("hey2");
								valeur += NEGATIVE_3;
							}
						}
						if (colonneNb + 1 <= 6) { // si bleu est en passe de gagner
							if (grid.getCase(ligneNb, colonneNb + 1) == "") {
								if (colonneNb - 3 >= 0) {
									if (grid.getCase(ligneNb, colonneNb - 3) == "") {
										//System.out.println("hey3");
										valeur += NEGATIVE_3_BIS;
									}
								}
							}
						}
					} else if (countTokenBlue == 2 && isMax) {
						valeur += NEGATIVE_2;
					} else if (countTokenBlue == 1 && isMax) {
						valeur += NEGATIVE_1;
					}
					break;
				}
				case "red": {
					countTokenRed++;
					if (countTokenBlue > 0) {
						countTokenRed = 0;
					}
					if (countTokenRed == 3 && isMax) {
						if (colonneNb + 1 <= 6) {
							if (grid.getCase(ligneNb, colonneNb + 1) == "") {
								valeur += POSITIVE_3;
							}
						}
						if (colonneNb - 3 >= 0) {
							if (grid.getCase(ligneNb, colonneNb - 3) == "") {
								valeur += POSITIVE_3;
							}
						}
						if (colonneNb + 1 <= 6) {
							if (grid.getCase(ligneNb, colonneNb + 1) == "") {
								if (colonneNb - 3 >= 0) {
									if (grid.getCase(ligneNb, colonneNb - 3) == "") {
										valeur += POSITIVE_3;
									}
								}
							}
						}
					} else if (countTokenRed == 2 && isMax) {
						valeur += POSITIVE_2;
					}
					if (countTokenRed == 3 && !isMax) {
						if (colonneNb + 1 <= 6) {
							if (grid.getCase(ligneNb, colonneNb + 1) == "") {
								valeur += NEGATIVE_3;
							}
						} 
						if (colonneNb - 3 >= 0) {
							if (grid.getCase(ligneNb, colonneNb - 3) == "") {
								valeur += NEGATIVE_3;
							}
						}
						if (colonneNb + 1 <= 6) {
							if (grid.getCase(ligneNb, colonneNb + 1) == "") {
								if (colonneNb - 3 >= 0) {
									if (grid.getCase(ligneNb, colonneNb - 3) == "") {
										valeur += NEGATIVE_3_BIS;
									}
								}
							}
						}
					} else if (countTokenRed == 2 && !isMax) {
						valeur += NEGATIVE_2;
					} else if (countTokenRed == 1 && !isMax) {
						valeur += NEGATIVE_1;
					}
					break;
				}
				default: {
					countTokenBlue = 0;
					countTokenRed = 0;
					break;
				}
				}
				colonneNb++;
			}
			ligneNb++;
			colonneNb = 0;
		}
		return valeur;
	}
	/**
	 * Méthode qui sert de test de terminaison en appelant quatre méthodes correspondant aux quatres façon de 
	 * gagner le jeu
	 * @param grid La grille a tester
	 * @return un boolean true si le jeu est fini, false sinon
	 */
	public boolean isFinished(Grid grid) {
		if (this.checkHori(grid) || this.checkVert(grid) || this.checkSlash(grid) || this.checkAntiSlash(grid)) {
			return true;
		}
		return false;
	}
	/**
	 * Une des quatre fonctions test, elle permet de regarder si il y a des alignements horizontaux
	 * @param grid La grille a tester
	 * @return un boolean true s'il existe un alignement, false sinon
	 */
	public boolean checkHori(Grid grid) {
		int countToken1 = 0;
		int countToken2 = 0;
		for (String[] i : grid.getGrille()) {
			for (String i2 : i) {
				switch (i2) {
				case "blue":
					if (countToken2 > 0)
						countToken2 = 0;
					countToken1++;
					if (countToken1 >= 4 || countToken2 >= 4) {
						return true;
					}
					break;
				case "red":
					if (countToken1 > 0)
						countToken1 = 0;
					countToken2++;
					if (countToken1 >= 4 || countToken2 >= 4) {
						return true;
					}
					break;
				default:
					countToken1 = 0;
					countToken2 = 0;
				}
			}
			countToken1 = 0;
			countToken2 = 0;
		}

		return false;
	}
	/**
	 * Méthode qui permet de calculer la valeur d'utilité d'une grille en comptant les alignements verticaux 
	 * des jetons
	 * @param grid Grille a analyser
	 * @param isMax Boolean pour savoir si l'appelant est max ou pas
	 * @return Une valeur entiere correspondant a la valeur d'utilité verticale pour l'appelant
	 */
	public int checkVertPourValeurUtilite(Grid grid, boolean isMax) {
		int valeur = 0;
		int countTokenBlue = 0;
		int countTokenRed = 0;
		for (int colonne = 0; colonne <= grid.getNB_COLONNE(); colonne++) {
			for (int ligne = 0; ligne <= grid.getNB_LIGNE(); ligne++) {
				switch (grid.getCase(ligne, colonne)) {
				case "blue": {
					countTokenBlue++;
					if (countTokenRed > 0) {
						countTokenBlue = 0;
					}
					if (countTokenBlue == 3 && !isMax) {
						if (ligne - 3 >= 0) {
							if (grid.getCase(ligne - 3, colonne) == "") {
								valeur += POSITIVE_3;
							}
						} else {
							valeur -= 0;
						}
					} else if (countTokenBlue == 2 && !isMax) {
						valeur += 50;
					}
					if (countTokenBlue == 3 && isMax) {
						if (ligne - 3 >= 0) {
							if (grid.getCase(ligne - 3, colonne) == "") {
								valeur += NEGATIVE_3_BIS;
							}
						} else {
							valeur -= 0;
						}
					} else if (countTokenBlue == 2 && isMax) {
						valeur += NEGATIVE_2;
					} else if (countTokenBlue == 1 && isMax) {
						valeur += NEGATIVE_1;
					}
					break;
				}
				case "red": {
					countTokenRed++;
					if (countTokenBlue > 0) {
						countTokenRed = 0;
					}
					if (countTokenRed == 3 && isMax) {
						if (ligne - 3 >= 0) {
							if (grid.getCase(ligne - 3, colonne) == "") {
								valeur += POSITIVE_3;
							}
						} else {
							valeur -= 0;
						}
						;
					} else if (countTokenRed == 2 && isMax) {
						valeur += 50;
					}
					if (countTokenRed == 3 && !isMax) {
						if (ligne - 3 >= 0) {
							if (grid.getCase(ligne - 3, colonne) == "") {
								valeur += NEGATIVE_3_BIS;
							}
						} else {
							valeur -= 0;
						}
					} else if (countTokenRed == 2 && !isMax) {
						valeur += NEGATIVE_2;
					} else if (countTokenRed == 1 && !isMax) {
						valeur += NEGATIVE_1;
					}
					break;
				}
				default: {
					countTokenBlue = 0;
					countTokenRed = 0;
					break;
				}
				}
			}
		}
		return valeur;
	}
	/**
	 * Une des quatre fonctions test, elle permet de regarder si il y a des alignements verticaux
	 * @param grid La grille a tester
	 * @return un boolean true s'il existe un alignement, false sinon
	 */
	public boolean checkVert(Grid grid) {
		int countToken1 = 0;
		int countToken2 = 0;
		for (int c = 0; c <= grid.getNB_COLONNE(); c++) {
			for (int l = 0; l <= grid.getNB_LIGNE(); l++) {
				switch (grid.getCase(l, c)) {
				case "blue":
					if (countToken2 > 0)
						countToken2 = 0;
					countToken1++;
					if (countToken1 >= 4 || countToken2 >= 4) {
						return true;
					}
					break;
				case "red":
					if (countToken1 > 0)
						countToken1 = 0;
					countToken2++;
					if (countToken1 >= 4 || countToken2 >= 4) {
						return true;
					}
					break;
				default:
					countToken1 = 0;
					countToken2 = 0;
				}
			}
			countToken1 = 0;
			countToken2 = 0;
		}
		return false;
	}
	/**
	 * Une des quatre fonctions test, elle permet de regarder si il y a des alignements dans la direction "slash"
	 * @param grid La grille a tester
	 * @return un boolean true s'il existe un alignement, false sinon
	 */
	public boolean checkSlash(Grid grid) {
		int countToken1 = 0;
		int countToken2 = 0;
		int c2, l2;
		for (int l = 3; l <= grid.getNB_LIGNE(); l++) {
			for (int c = 0; c <= 3; c++) {
				switch (grid.getCase(l, c)) {
				case "blue":
					countToken1 = 0;
					if (countToken2 > 0)
						countToken2 = 0;
					countToken1++;
					c2 = c;
					l2 = l;
					for (int i = 1; i < 4; i++) {
						c2++;
						l2--;
						if (grid.getCase(l2, c2) == "blue")
							countToken1++;
						else if (grid.getCase(l2, c2) != "red") {
							countToken1 = 0;
							break;
						}
					}
					if (countToken1 >= 4 || countToken2 >= 4) {
						return true;
					}
					break;
				case "red":
					countToken2 = 0;
					if (countToken1 > 0)
						countToken1 = 0;
					countToken2++;
					c2 = c;
					l2 = l;
					for (int i = 1; i < 4; i++) {
						c2++;
						l2--;
						if (grid.getCase(l2, c2) == "red")
							countToken2++;
						else if (grid.getCase(l2, c2) != "blue") {
							countToken2 = 0;
							break;
						}
					}
					if (countToken1 >= 4 || countToken2 >= 4) {
						return true;
					}
					break;
				default:
					countToken1 = 0;
					countToken2 = 0;
				}
			}
			countToken1 = 0;
			countToken2 = 0;
		}
		return false;
	}
	/**
	 * Méthode qui permet de calculer la valeur d'utilité d'une grille en comptant les alignements dans la
	 * direction "slash" des jetons
	 * @param grid Grille a analyser
	 * @param isMax Boolean pour savoir si l'appelant est max ou pas
	 * @return Une valeur entiere correspondant a la valeur d'utilité des alignements
	 * en "slash" pour l'appelant
	 */
	public int checkSlashPourValeurUtilite(Grid grid, boolean isMax) {
		int valeur = 0;
		int countTokenBlue = 0;
		int countTokenRed = 0;
		int ligne2, colonne2;
		for (int ligne = 3; ligne <= grid.getNB_LIGNE(); ligne++) {
			for (int colonne = 0; colonne <= 3; colonne++) {
				switch (grid.getCase(ligne, colonne)) {
				case "blue": {
					countTokenBlue = 1;
					if (countTokenRed > 0) {
						countTokenRed = 0;
					}
					ligne2 = ligne;
					colonne2 = colonne;
					for (int k = 1; k < 4; k++) {
						ligne2--;
						colonne2++;
						if (grid.getCase(ligne2, colonne2) == "blue") {
							countTokenBlue++;
							if (countTokenBlue == 3 && !isMax) {
								valeur += POSITIVE_3;
							} else if (countTokenBlue == 2 && !isMax) {
								valeur += 50;
							}
							if (countTokenBlue == 3 && isMax) {
								valeur += NEGATIVE_3_BIS;
							} else if (countTokenBlue == 2 && isMax) {
								valeur += NEGATIVE_2;
							} else if (countTokenBlue == 1 && isMax) {
								valeur += NEGATIVE_1;
							}
						} else if (grid.getCase(ligne2, colonne2) == "red") {
							countTokenBlue = 0;
						}
					}
					break;
				}
				case "red": {
					countTokenRed = 1;
					if (countTokenBlue > 0) {
						countTokenBlue = 0;
					}
					ligne2 = ligne;
					colonne2 = colonne;
					for (int k = 1; k < 4; k++) {
						ligne2--;
						colonne2++;
						if (grid.getCase(ligne2, colonne2) == "blue") {
							countTokenRed++;
							if (countTokenRed == 3 && isMax) {
								valeur += POSITIVE_3;
							} else if (countTokenRed == 2 && isMax) {
								valeur += 50;
							}
							if (countTokenRed == 3 && !isMax) {
								valeur += NEGATIVE_3_BIS;
							} else if (countTokenRed == 2 && !isMax) {
								valeur += NEGATIVE_2;
							} else if (countTokenRed == 1 && !isMax) {
								valeur += NEGATIVE_1;
							}
						} else if (grid.getCase(ligne2, colonne2) == "red") {
							countTokenBlue = 0;
						}
					}
					break;
				}
				default: {
					countTokenBlue = 0;
					countTokenRed = 0;
				}
				}

			}
			countTokenBlue = 0;
			countTokenRed = 0;
		}
		return valeur;
	}
	/**
	 * Méthode qui permet de calculer la valeur d'utilité d'une grille en comptant les alignements dans la
	 * direction "anti-slash" des jetons
	 * @param grid Grille a analyser
	 * @param isMax Boolean pour savoir si l'appelant est max ou pas
	 * @return Une valeur entiere correspondant a la valeur d'utilité des alignements
	 * en "anti-slash" pour l'appelant
	 */
	public int checkAntiSlashPourValeurUtilite(Grid grid, boolean isMax) {
		int valeur = 0;
		int countTokenBlue = 0;
		int countTokenRed = 0;
		int ligne2, colonne2;
		for (int ligne = 3; ligne <= grid.getNB_LIGNE(); ligne++) {
			for (int colonne = 3; colonne <= grid.getNB_COLONNE(); colonne++) {
				switch (grid.getCase(ligne, colonne)) {
				case "blue": {
					countTokenBlue = 1;
					if (countTokenRed > 0) {
						countTokenRed = 0;
					}
					ligne2 = ligne;
					colonne2 = colonne;
					for (int k = 1; k < 4; k++) {
						ligne2--;
						colonne2--;
						if (grid.getCase(ligne2, colonne2) == "blue") {
							countTokenBlue++;
							if (countTokenRed == 3 && isMax) {
								valeur += POSITIVE_3;
							} else if (countTokenRed == 2 && isMax) {
								valeur += 50;
							}
							if (countTokenRed == 3 && !isMax) {
								valeur += NEGATIVE_3_BIS;
							} else if (countTokenRed == 2 && !isMax) {
								valeur += NEGATIVE_2;
							} else if (countTokenRed == 1 && !isMax) {
								valeur += NEGATIVE_1;
							}
						} else if (grid.getCase(ligne2, colonne2) == "red") {
							countTokenBlue = 0;
						}
					}
					break;
				}
				case "red": {
					countTokenRed = 1;
					if (countTokenBlue > 0) {
						countTokenBlue = 0;
					}
					ligne2 = ligne;
					colonne2 = colonne;
					for (int k = 1; k < 4; k++) {
						ligne2--;
						colonne2--;
						if (grid.getCase(ligne2, colonne2) == "blue") {
							countTokenRed++;
							if (countTokenRed == 3 && isMax) {
								valeur += POSITIVE_3;
							} else if (countTokenRed == 2 && isMax) {
								valeur += 50;
							}
							if (countTokenRed == 3 && !isMax) {
								valeur += NEGATIVE_3_BIS;
							} else if (countTokenRed == 2 && !isMax) {
								valeur += NEGATIVE_2;
							} else if (countTokenRed == 1 && !isMax) {
								valeur += NEGATIVE_1;
							}
						} else if (grid.getCase(ligne2, colonne2) == "red") {
							countTokenBlue = 0;
						}
					}
					break;
				}
				default: {
					countTokenBlue = 0;
					countTokenRed = 0;
				}
				}

			}
			countTokenBlue = 0;
			countTokenRed = 0;
		}
		return valeur;
	}
	
	/**
	 * Une des quatre fonctions test, elle permet de regarder si il y a des alignements dans la direction "anti-slash"
	 * @param grid La grille a tester
	 * @return un boolean true s'il existe un alignement, false sinon
	 */
	public boolean checkAntiSlash(Grid grid) {
		int countToken1 = 0;
		int countToken2 = 0;
		int c2, l2;
		for (int l = 3; l <= grid.getNB_LIGNE(); l++) {
			for (int c = 3; c <= grid.getNB_COLONNE(); c++) {
				switch (grid.getCase(l, c)) {
				case "blue":
					countToken1 = 0;
					if (countToken2 > 0)
						countToken2 = 0;
					countToken1++;
					c2 = c;
					l2 = l;
					for (int i = 1; i < 4; i++) {
						c2--;
						l2--;
						if (grid.getCase(l2, c2) == "blue")
							countToken1++;
						else if (grid.getCase(l2, c2) != "blue") {
							countToken1 = 0;
							break;
						}
					}
					if (countToken1 >= 4 || countToken2 >= 4) {
						return true;
					}
					break;
				case "red":
					countToken2 = 0;
					if (countToken1 > 0)
						countToken1 = 0;
					countToken2++;
					c2 = c;
					l2 = l;
					for (int i = 1; i < 4; i++) {
						c2--;
						l2--;
						if (grid.getCase(l2, c2) == "red")
							countToken2++;
						else if (grid.getCase(l2, c2) != "red") {
							countToken2 = 0;
							break;
						}
					}
					if (countToken1 >= 4 || countToken2 >= 4) {
						return true;
					}
					break;
				default:
					countToken1 = 0;
					countToken2 = 0;
				}
			}
			countToken1 = 0;
			countToken2 = 0;
		}
		return false;
	}

	/**
	 * Méthode qui retourne une heuristique basée sur les positions des jetons
	 * @param grid Grille sur laquelle la méthode va calculer l'heuristique
	 * @param isMax Boolean qui permet de savoir si c'est le tour de max ou non
	 * @return Une heuristique basée sur le placement des jetons du joueur. Les points sont basés sur le nombre d'
	 * alignement possible a ce point. La valeur de cet heuristique diminue au fur et a mesure qu'on avance
	 * dans le jeu.
	 */
	public int heuristique2AlignementsGagnants(Grid grid, boolean isMax) {
		int heurs = 0;
		int nombreDeJetonsDansTableau=grid.nombreJetonDansTableau();
		int facteur=(nombreDeJetonsDansTableau==0?20:20/nombreDeJetonsDansTableau);
		for (int l = 0; l < 6; l++) {
			for (int c = 0; c < 7; c++) {
				if (grid.getCase(l, c).equals("blue") && isMax) {
					heurs -=  facteur*this.grilleHeuristique[l][c];
				} else if (grid.getCase(l, c).equals("red") && isMax) {
					heurs += facteur * this.grilleHeuristique[l][c];
				} else if (grid.getCase(l, c).equals("blue") && !isMax) {
					heurs += facteur * this.grilleHeuristique[l][c];
				} else if (grid.getCase(l, c).equals("red") && !isMax) {
					heurs -= facteur * this.grilleHeuristique[l][c];
				}
			}
		}
		return heurs;
	}
	/**
	 * 
	 * @return L'état parent du tour en cours
	 */
	public Etat getEtatParent() {
		return this.etatParent;
	}
}

