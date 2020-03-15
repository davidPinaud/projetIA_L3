package jeu;

import java.util.List;
import java.util.Vector;

public class Etat {
	private Etat parent;
	private List<Etat> enfants=new Vector<Etat>();
	Game game;
	Grid grid;
	int utilite;
	
	public Etat(Game game,Grid grid) {
		this.game=game;
		this.grid=grid;
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


	
	
	
}
