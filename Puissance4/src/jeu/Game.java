package jeu;

public class Game {
	
	Grid grid;
	Player player1;
	Player player2;
	Boolean isplayer1Turn;
	
	/**
	 * Méthode qui determine si le jeu est fini (par victoire ou égalité)
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
	
	
	public Game(Player player1,Player player2) {
		this.isplayer1Turn=true;
		this.player1=player1;
		this.player2=player2;
		grid=new Grid();


	}
	public boolean isFinished() {
		if(this.checkHori() || this.checkVert() || this.checkSlash() || this.checkAntiSlash()) {
			return true;
		}
		return false;
	}
	
	public void play(Player player,int ligne,int colonne) {
		if(player.equalsPlayer(player1)){
			this.isplayer1Turn=false;
			try {
				grid.setCase(ligne, colonne, player1.getToken());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	
	
	public boolean checkHori()
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
	public boolean checkVert()
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

	public boolean checkSlash()
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
	public boolean checkAntiSlash()
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
