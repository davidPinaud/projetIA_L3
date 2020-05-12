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
		if ((token == "red") || (token == "blue")||(token=="")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns Strings of Grid to display on console
	 */

	public String toString() {
		StringBuilder stb=new StringBuilder();
		for(String [] ligne:this.getGrille()) {
			for(String colonne:ligne) {
				stb.append((colonne==""?"*":(colonne=="blue"?"b":"r"))+" ");
			}
			stb.append("\n");
		}
		return stb.toString();
	}
	
	public int nombreJetonDansTableau() {
		int nbJeton=0;
		for(int ligne=0;ligne<=this.getNB_LIGNE();ligne++) {
			for(int colonne=0;colonne<=this.getNB_COLONNE();colonne++) {
				if(this.getCase(ligne, colonne)=="blue"||this.getCase(ligne, colonne)=="red") {
					nbJeton++;
				}
			}
		}
		return nbJeton;
	}
	
	public void copyGrid(Grid gridColler) {
		for(int ligne=0;ligne<=this.getNB_LIGNE();ligne++) {
			for(int colonne=0;colonne<=this.getNB_COLONNE();colonne++) {
				try {
					gridColler.setCase(ligne, colonne, this.getCase(ligne, colonne));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
