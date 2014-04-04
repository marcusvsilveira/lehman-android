package edu.lehman.android.domain;

/**
 * Coordinates to represent a position. 
 * This starts on top left corner (0,0)
 * @author marcus.silveira
 *
 */
public class Position {

	int x;
	int y;
	
	public Position(int x, int y){
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public void setXY(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public String toString(){
		return "Position: [" + x + "," + y + "]"; 
	}
	
}
