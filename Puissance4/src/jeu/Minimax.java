package jeu;

import java.util.List;
import java.util.Vector;

public class Minimax {
	private Game game;
	private Etat etatParent;
	private int profondeur;
	public Minimax(Game game,int profondeur) {
		super();
		this.game = game;
		etatParent=new Etat(game,game.getGrid());
		this.profondeur=profondeur;
		this.initialiserEtatEnfants(etatParent,this.profondeur); //initialisation des enfants d'etatParent
	}
	
	/**
	 * Cette fonction permet de generer la liste des enfants possible pour l'état Parent de ce minimax
	 * @param etat : etat pour lequel on calcule les enfants
	 * @param profondeur : profondeur de la génération des enfants
	 */
	private void initialiserEtatEnfants(Etat etat,int profondeur) {
		List<Integer> colonneJouable=new Vector<>();//liste des colonnes ou on peut mettre une pièce
		Grid grid=etat.getGrid();
		for(int i=0;i<grid.getNB_COLONNE();i++) {
			if(grid.getCase(0, i).equals("")) {
				colonneJouable.add(i);
			}
		}
		
		List<List<Integer>> listeDePositionJouable=new Vector<>(); // liste des coordonnées de position jouable dans la grille donné
		
		for(Integer i:colonneJouable) {
			
		}
	}

	/**
	 * Retourne la valeur d'utilité que doit choisir max i.e. le chemin à prendre dans l'arbre.
	 * Cette fonction permettra à l'IA de choisir le prochain jeton à jouer
	 * @param grid :l'état du jeu
	 * @param profondeur : la profondeur de recherche souhaité
	 * @param isMax : boolean qui indique si 
	 * @return un tableau d'entiers de taille 2 qui sont les coordonnées du jeton
	 *         joué
	 */
	public int minimax(Etat etat,int profondeur,boolean isMax) {
		
		if(profondeur==0||this.isFinished(etat.grid)) {
			return valeurDutilite(etat);
		}
		if(isMax) {
			int maxEval=-2147483648; //plus petit entier représentable (-2^31
			int eval;
			for(Etat etatFils :etatParent.getEnfants()) {
				eval=minimax(etatFils,profondeur-1,false);
				maxEval=maxEval>eval?maxEval:eval;
			}
			return maxEval;
		}
		else {
			int minEval=2147483647; // plus grand entier représentable (2^31-1)
			int eval;
			for(Etat etatFils :etatParent.getEnfants()) {
				eval=minimax(etatFils,profondeur-1,false);
				minEval=minEval<eval?minEval:eval;
			}
			return minEval;
		}
	}
	
	public int valeurDutilite(Etat etat) {
		return 0;
	}

	public boolean isFinished(Grid grid) {
		if(this.checkHori(grid) || this.checkVert(grid) || this.checkSlash(grid) || this.checkAntiSlash(grid)) {
			return true;
		}
		return false;
	}
	
	public boolean checkHori(Grid grid)
	{
		int countToken1 = 0;
		int countToken2 = 0;
		for(String[] i : grid.getGrille())
		{
			for(String i2 : i )
			{
				switch (i2)
				{
					case "blue":
						if(countToken2 > 0)
							countToken2 = 0;
						countToken1++;
						if(countToken1 >= 4 || countToken2 >= 4)
						{
							return true;
						}
						break;
					case "red":
						if(countToken1 > 0)
							countToken1 = 0;
						countToken2++;
						if(countToken1 >= 4 || countToken2 >= 4)
						{
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
	public boolean checkVert(Grid grid)
	{
		int countToken1 = 0;
		int countToken2 = 0;
		for(int c = 0; c<= grid.getNB_COLONNE(); c++)
		{
			for(int l = 0; l<= grid.getNB_LIGNE(); l++)
			{
				switch (grid.getCase(l, c))
				{
					case "blue":
						if(countToken2 > 0)
							countToken2 = 0;
						countToken1++;
						if(countToken1 >= 4 || countToken2 >= 4)
						{
							return true;
						}
						break;
					case "red":
						if(countToken1 > 0)
							countToken1 = 0;
						countToken2++;
						if(countToken1 >= 4 || countToken2 >= 4)
						{
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

	public boolean checkSlash(Grid grid)
	{
		int countToken1 = 0;
		int countToken2 = 0;
		int c2,l2;
		for(int l = 3; l <= grid.getNB_LIGNE(); l++)
		{
			for(int c = 0; c <= 3; c++)
			{
				switch (grid.getCase(l, c))
				{
					case "blue":
						countToken1 = 0;
						if(countToken2 > 0)
							countToken2 = 0;
						countToken1++;
						c2 = c;
						l2 = l;
						for(int i = 1; i < 4; i++ )
						{
							c2++;
							l2--;
							if(grid.getCase(l2, c2) == "blue")
								countToken1++;
							else if(grid.getCase(l2, c2) != "red")
							{
								countToken1 = 0;
								break;
							}
						}
						if(countToken1 >= 4 || countToken2 >= 4)
						{
							return true;
						}
						break;
					case "red":
						countToken2 = 0;
						if(countToken1 > 0)
							countToken1 = 0;
						countToken2++;
						c2 = c;
						l2 = l;
						for(int i = 1; i < 4; i++ )
						{
							c2++;
							l2--;
							if(grid.getCase(l2, c2) == "red")
								countToken2++;
							else if(grid.getCase(l2, c2) !=  "blue")
							{
								countToken2 = 0;
								break;
							}
						}
						if(countToken1 >= 4 || countToken2 >= 4)
						{
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
	public boolean checkAntiSlash(Grid grid)
	{
		int countToken1 = 0;
		int countToken2 = 0;
		int c2,l2;
		for(int l = 3; l <= grid.getNB_LIGNE(); l++)
		{
			for(int c = 3; c <= grid.getNB_COLONNE(); c++)
			{
				switch (grid.getCase(l, c))
				{
					case "blue":
						countToken1 = 0;
						if(countToken2 > 0)
							countToken2 = 0;
						countToken1++;
						c2 = c;
						l2 = l;
						for(int i = 1; i < 4; i++ )
						{
							c2--;
							l2--;
							if(grid.getCase(l2, c2) == "blue")
								countToken1++;
							else if(grid.getCase(l2, c2) != "blue")
							{
								countToken1 = 0;
								break;
							}
						}
						if(countToken1 >= 4 || countToken2 >= 4)
						{
							return true;
						}
						break;
					case "red":
						countToken2 = 0;
						if(countToken1 > 0)
							countToken1 = 0;
						countToken2++;
						c2 = c;
						l2 = l;
						for(int i = 1; i < 4; i++ )
						{
							c2--;
							l2--;
							if(grid.getCase(l2, c2) == "red")
								countToken2++;
							else if(grid.getCase(l2, c2) != "red")
							{
								countToken2 = 0;
								break;
							}
						}
						if(countToken1 >= 4 || countToken2 >= 4)
						{
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
}
