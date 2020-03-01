package jeu;

import javafx.scene.image.ImageView;

public class Case {
	ImageView imageView;
	int x,y;
	boolean isEmpty;
	
	
	public Case(ImageView imageView, int x, int y) {
		super();
		this.imageView = imageView;
		this.x = x;
		this.y = y;
		this.isEmpty=true;
	}
	
	public boolean isEmpty() {
		return isEmpty;
	}

	public void setEmpty(boolean isEmpty) {
		this.isEmpty = isEmpty;
	}

	public ImageView getImageView() {
		return imageView;
	}
	public void setImageView(ImageView imageView) {
		this.imageView = imageView;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	@Override
	public String toString() {
		return "x:"+x+" y:"+y+" empty:"+this.isEmpty+"\n";
	}
	
}
