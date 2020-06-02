package jeu;

public class Player {
	private int score=0;
	private String token;//either red or blue
	private int id;
	
	/**
	 * Initialisation d'un joueur
	 * @param token Le jeton qu'il va utiliser, soit un String "blue", soit un String "red"
	 * @param id
	 * @throws Exception
	 */
	public Player(String token,int id) throws Exception {
		if(equalsToken(token)) {
			this.token=token;
		}
		else {
			throw new Exception("Mauvais Jeton");
		}
		
		this.id=id;
	}
	
	/**
	 * Test pour savoir si le joueur donné en parametre en est fait le joueur courant
	 * @param player Le joueur a tester
	 * @return un boolean
	 */
	public boolean equalsPlayer(Player player) {
		return this.id==player.getID()?true:false;
	}
	
	public int getID() {
		return this.id;
	}
	
	/**
	 * Méthode qui test si l'attribut token est bien blue ou red
	 * @param token
	 * @return boolan
	 */
	public boolean equalsToken(String token) {
		if((token=="blue")||(token=="red")) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void incrementScore(int increment) {
		this.score+=increment;
	}
	public int getScore() {
		return score;
	}
	public String getToken() {
		return this.token;
	}
	
	

}
