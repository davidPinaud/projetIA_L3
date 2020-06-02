package jeu;

public class Game {
	
	Grid grid;
	Player player1;
	Player player2;
	Boolean isplayer1Turn;
	
	/**
	 * Initialisation de la premiere partie,
	 * les joueurs et la grille sont crées
	 */
	public Game() {
		this.isplayer1Turn=true;
		try {
			player1=new Player("blue",0);
			player2=new Player("red",1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		grid=new Grid();
	}
	
	/**
	 * Initialisation pour les parties suivantes
	 * Méthode créee pour que deux joueurs puissent jouer plusieurs parties ensemble.
	 * Les deux joueurs existent déjà mais on crée une nouvelle grille
	 * @param player1 Le joueur 1
	 * @param player2 le joueur 2
	 */
	public Game(Player player1,Player player2) {
		this.isplayer1Turn=true;
		this.player1=player1;
		this.player2=player2;
		grid=new Grid();
	}
	
	/**
	 * Test de terminaison pour le jeu, utilisé lors d'un jeu entre deux joueurs humain,
	 * le test pour les jeux contre une IA est directement dans leur classe.
	 * @return un boolean qui détermine si le jeu est fini
	 */
	public boolean isFinished() {
		if(this.testHorizontal() || this.testVertical() || this.testSlash() || this.testAntiSlash()) {
			return true;
		}
		return false;
	}
	
	
	
	/**
	 * Une des quatre fonctions test, elle permet de regarder si il y a des alignements horizontaux
	 * @param grid La grille a tester
	 * @return un boolean true s'il existe un alignement, false sinon
	 */
	public boolean testHorizontal()
	{
		int nbTokenRed = 0;
		int nbTokenBlue = 0;
		for(String[] i : grid.getGrille())
		{
			for(String i2 : i )
			{
				switch (i2)
				{
					case "blue":
						if(nbTokenBlue > 0)
							nbTokenBlue = 0;
						nbTokenRed++;
						if(nbTokenRed >= 4 || nbTokenBlue >= 4)
						{
							return true;
						}
						break;
					case "red":
						if(nbTokenRed > 0)
							nbTokenRed = 0;
						nbTokenBlue++;
						if(nbTokenRed >= 4 || nbTokenBlue >= 4)
						{
							return true;
						}
						break;
					default:
						nbTokenRed = 0;
						nbTokenBlue = 0;
				}
			}
			nbTokenRed = 0;
			nbTokenBlue = 0;
		}

		return false;
	}
	/**
	 * Une des quatre fonctions test, elle permet de regarder si il y a des alignements verticaux
	 * @param grid La grille a tester
	 * @return un boolean true s'il existe un alignement, false sinon
	 */
	public boolean testVertical()
	{
		int nbTokenRed = 0;
		int nbTokenBlue = 0;
		for(int c = 0; c<= grid.getNB_COLONNE(); c++)
		{
			for(int l = 0; l<= grid.getNB_LIGNE(); l++)
			{
				switch (grid.getCase(l, c))
				{
					case "blue":
						if(nbTokenBlue > 0)
							nbTokenBlue = 0;
						nbTokenRed++;
						if(nbTokenRed >= 4 || nbTokenBlue >= 4)
						{
							return true;
						}
						break;
					case "red":
						if(nbTokenRed > 0)
							nbTokenRed = 0;
						nbTokenBlue++;
						if(nbTokenRed >= 4 || nbTokenBlue >= 4)
						{
							return true;
						}
						break;
					default:
						nbTokenRed = 0;
						nbTokenBlue = 0;
				}
			}
			nbTokenRed = 0;
			nbTokenBlue = 0;
		}
		return false;
	}
	/**
	 * Une des quatre fonctions test, elle permet de regarder si il y a des alignements dans la direction "slash"
	 * @param grid La grille a tester
	 * @return un boolean true s'il existe un alignement, false sinon
	 */
	public boolean testSlash()
	{
		int nbTokenRed = 0;
		int nbTokenBlue = 0;
		int c2,l2;
		for(int l = 3; l <= grid.getNB_LIGNE(); l++)
		{
			for(int c = 0; c <= 3; c++)
			{
				switch (grid.getCase(l, c))
				{
					case "blue":
						nbTokenRed = 0;
						if(nbTokenBlue > 0)
							nbTokenBlue = 0;
						nbTokenRed++;
						c2 = c;
						l2 = l;
						for(int i = 1; i < 4; i++ )
						{
							c2++;
							l2--;
							if(grid.getCase(l2, c2) == "blue")
								nbTokenRed++;
							else if(grid.getCase(l2, c2) != "red")
							{
								nbTokenRed = 0;
								break;
							}
						}
						if(nbTokenRed >= 4 || nbTokenBlue >= 4)
						{
							return true;
						}
						break;
					case "red":
						nbTokenBlue = 0;
						if(nbTokenRed > 0)
							nbTokenRed = 0;
						nbTokenBlue++;
						c2 = c;
						l2 = l;
						for(int i = 1; i < 4; i++ )
						{
							c2++;
							l2--;
							if(grid.getCase(l2, c2) == "red")
								nbTokenBlue++;
							else if(grid.getCase(l2, c2) !=  "blue")
							{
								nbTokenBlue = 0;
								break;
							}
						}
						if(nbTokenRed >= 4 || nbTokenBlue >= 4)
						{
							return true;
						}
						break;
					default:
						nbTokenRed = 0;
						nbTokenBlue = 0;
				}
			}
			nbTokenRed = 0;
			nbTokenBlue = 0;
		}
		return false;
	}
	/**
	 * Une des quatre fonctions test, elle permet de regarder si il y a des alignements dans la direction "anti-slash"
	 * @param grid La grille a tester
	 * @return un boolean true s'il existe un alignement, false sinon
	 */
	public boolean testAntiSlash()
	{
		int nbTokenRed = 0;
		int nbTokenBlue = 0;
		int c2,l2;
		for(int l = 3; l <= grid.getNB_LIGNE(); l++)
		{
			for(int c = 3; c <= grid.getNB_COLONNE(); c++)
			{
				switch (grid.getCase(l, c))
				{
					case "blue":
						nbTokenRed = 0;
						if(nbTokenBlue > 0)
							nbTokenBlue = 0;
						nbTokenRed++;
						c2 = c;
						l2 = l;
						for(int i = 1; i < 4; i++ )
						{
							c2--;
							l2--;
							if(grid.getCase(l2, c2) == "blue")
								nbTokenRed++;
							else if(grid.getCase(l2, c2) != "blue")
							{
								nbTokenRed = 0;
								break;
							}
						}
						if(nbTokenRed >= 4 || nbTokenBlue >= 4)
						{
							return true;
						}
						break;
					case "red":
						nbTokenBlue = 0;
						if(nbTokenRed > 0)
							nbTokenRed = 0;
						nbTokenBlue++;
						c2 = c;
						l2 = l;
						for(int i = 1; i < 4; i++ )
						{
							c2--;
							l2--;
							if(grid.getCase(l2, c2) == "red")
								nbTokenBlue++;
							else if(grid.getCase(l2, c2) != "red")
							{
								nbTokenBlue = 0;
								break;
							}
						}
						if(nbTokenRed >= 4 || nbTokenBlue >= 4)
						{
							return true;
						}
						break;
					default:
						nbTokenRed = 0;
						nbTokenBlue = 0;
				}
			}
			nbTokenRed = 0;
			nbTokenBlue = 0;
		}
		return false;
	}
	
	/**
	 * méthode permettant de commencer une nouvelle partie
	 */
	public void nouvellePartie() {
		this.grid=new Grid();
		this.isplayer1Turn=true;
	}
	
	public boolean getIsPlayer1Turn() {
		return this.isplayer1Turn;
	}

	public Grid getGrid() {
		return grid;
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
	}

	public Player getPlayer1() {
		return player1;
	}

	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}

	public void setIsPlayer1Turn(Boolean player1Turn) {
		this.isplayer1Turn = player1Turn;
	}
	
}
