package jeu;

import java.util.List;
import java.util.Vector;

/**
 * Classe permettant de représenter un état du jeu
 * @author davidpinaud
 *
 */
public class Etat {
	private Etat parent=null; //Son état parent s'il en a un
	private List<Etat> enfants = new Vector<Etat>(); // ses enfants s'il en a
	Game game; // Le jeu auquel cet Etat peut appartenir
	Grid grid; // La grille associé a cet etat
	//L'utilité de cet état, initialisé à la valeur minimale d'un entier
	//Il y a aussi les composantes de cette utilité
	int utilite=Integer.MIN_VALUE, slash, antislash, vertical, horizontale,heuristique2Alignement;

	public Etat(Game game, Grid grid) {
		this.game = game;
		this.grid = grid;
	}
	

	public int getHeuristique2Alignement() {
		return heuristique2Alignement;
	}


	public void setHeuristique2Alignement(int heuristique2Alignement) {
		this.heuristique2Alignement = heuristique2Alignement;
	}


	public Etat getParent() {
		return parent;
	}

	public void setParent(Etat parent) {
		this.parent = parent;
	}

	public List<Etat> getEnfants() {
		return enfants;
	}

	public void setEnfants(List<Etat> enfants) {
		this.enfants = enfants;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public Grid getGrid() {
		return grid;
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
	}

	public int getUtilite() {
		return utilite;
	}

	public void setUtilite(int utilite) {
		this.utilite = utilite;
	}

	/**
	 * Permet d'afficher la grille et les valeurs d'utilité
	 */
	@Override
	public String toString() {
		if(this.parent==null) {
			return "\ngrid : \n" + grid.toString()+ "\n\n";
		}else {
			
		return "\n\nutilité : " + utilite + "\ngrid : \n" + grid.toString() + "\n" + "antislash : " + this.getAntislash()
				+ "\nhorizontale : " + this.getHorizontale() + "\nvertical : " + this.getVertical() + "\nslash : "
				+ this.getSlash() + "\nalignement : "+this.getHeuristique2Alignement()+ "\n\n";
		}
	}

	public int getSlash() {
		return slash;
	}

	public void setSlash(int slash) {
		this.slash = slash;
	}

	public int getAntislash() {
		return antislash;
	}

	public void setAntislash(int antislash) {
		this.antislash = antislash;
	}

	public int getVertical() {
		return vertical;
	}

	public void setVertical(int vertical) {
		this.vertical = vertical;
	}

	public int getHorizontale() {
		return horizontale;
	}

	public void setHorizontale(int horizontale) {
		this.horizontale = horizontale;
	}

}
