package jeu;

import java.util.List;
import java.util.Vector;

public class Etat {
	private Etat parent;
	private List<Etat> enfants = new Vector<Etat>();
	Game game;
	Grid grid;
	int utilite, slash, antislash, vertical, horizontale;

	public Etat(Game game, Grid grid) {
		this.game = game;
		this.grid = grid;
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

	@Override
	public String toString() {
		return "\nutilité : " + utilite + "\ngrid : \n" + grid.toString() + "\n" + "antislash" + this.getAntislash()
				+ "\nhorizontale" + this.getHorizontale() + "\nvertical" + this.getVertical() + "\nslash"
				+ this.getSlash() + "\n\n";
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
