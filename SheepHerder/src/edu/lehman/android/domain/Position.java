package edu.lehman.android.domain;

/**
 * Coordinates to represent a position. 
 * This starts on top left corner (0,0)
 * @author marcus.silveira
 *
 */
public class Position {

	private float x, y;
	
	public Position(float x, float y){
		this.x = x;
		this.y = y;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}
	
	public void setXY(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
}
