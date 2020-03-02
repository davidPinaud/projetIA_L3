package jeu;

public class Grid {

	private String[][] grille = { 
			{ "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "" } };

	private final int NB_LIGNE = 5;
	private final int NB_COLONNE = 6;

	public Grid() {

	}

	public int getNB_LIGNE() {
		return NB_LIGNE;
	}

	public int getNB_COLONNE() {
		return NB_COLONNE;
	}

	public String[][] getGrille() {
		return grille;
	}

	public void setGrille(String[][] grille) {
		this.grille = grille;
	}

	public String getCase(int ligne, int colonne) {
		return this.grille[ligne][colonne];
	}

	public void setCase(int ligne, int colonne, String token) throws Exception {
		if (equalsToken(token)) {
//		System.out.println("hello1");
			this.grille[ligne][colonne] = token;

		} else {
			throw new Exception("Mauvais Jeton");
		}
	}

	/**
	 * Méthode qui test si l'attribut token est bien x ou o
	 * 
	 * @param token
	 * @return boolan
	 */

	public boolean equalsToken(String token) {
		if ((token == "red") || (token == "blue")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns Strings of Grid to display on console
	 */

	public String toString() {
		StringBuffer stb = new StringBuffer();

		for (int l = 0; l < this.NB_LIGNE; l++) {
			stb.append("|");
			for (int c = 0; c < this.NB_COLONNE; c++) {

				stb.append(this.getCase(l, c) + "|");
			}
			stb.append("\n");
		}
		return stb.toString();
	}

}
