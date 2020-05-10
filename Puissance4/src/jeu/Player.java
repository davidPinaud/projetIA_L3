package jeu;

public class Player {
	private int score=0;
	private String token;//either red or blue
	private int id;
	
	public Player(String token,int id) throws Exception {
		if(equalsToken(token)) {
			this.token=token;
		}
		else {
			throw new Exception("Mauvais Jeton");
		}
		
		this.id=id;
	}
	
	
	public boolean equalsPlayer(Player player) {
		return this.id==player.getID()?true:false;
	}
	
	public int getID() {
		return this.id;
	}
	
	/**
	 * Méthode qui test si l'attribut token est bien x ou o 
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
