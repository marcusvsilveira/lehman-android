package edu.lehman.android.domain;

import interfaces.Orientable;
import edu.lehman.android.factory.AnimalType;
import edu.lehman.android.views.GameSurfaceView.Boundaries;

/**
 * Generic class to represent characteristics all animal entities share.
 * 
 * @author marcus.silveira
 * @author Marcos Davila
 *
 */
public abstract class Animal implements Orientable {

	protected int speed;
	protected Position position;
	protected int width;
	protected int height;
	protected AnimalType type;
	protected int screenWidth, screenHeight;

	/**
	 * Constructs an animal object
	 * 
	 * @param type defines the type of animal
	 * @param x the initial starting x position
	 * @param y the initial starting y position
	 * @param speed how fast the animal can move
	 * @param width width of the bitmap
	 * @param height height of the bitmap
	 * @param b defines the size of the surface view the animal can move in
	 */
	public Animal(AnimalType type, int x, int y, int speed, int width, int height, Boundaries b){
		this.position = new Position(x, y);
		this.speed = speed;
		this.width = width;
		this.height = height;
		screenWidth = b.getScreenWidth();
		screenHeight = b.getScreenHeight();
	}
	
	/**
	 * Determines if one animal is close to another animal
	 */
	public boolean closeTo(final Animal animal, int RADIUS) {
		return Math.abs(position.x - animal.position.x) < RADIUS
				&& Math.abs(position.y - animal.position.y) < RADIUS;
	}
	
	/**
	 * Moves an animal towards the location on screen specified by x and y.
	 * x and y are determined by the location on the screen that the user
	 * selects
	 * 
	 * @param x the x coordinate to move towards
	 * @param y the y coordinate to move towards
	 */
	public void moveTo(final int x, final int y) {
		final int oldX = position.x;
		final int oldY = position.y;
		int newX = 0, newY = 0;
		final int posXDir = oldX+speed;
		final int negXDir = oldX-speed;
		final int posYDir = oldY+speed;
		final int negYDir = oldY-speed;
			
		// If the dog is not told to move to where it already is, it calculates
		// the direction that it needs to move in and then moves one unit in that 
		// direction
		if (oldX != x){
			if (oldX <= x){
				newX = (posXDir >= x) ? x : posXDir;
			} else if (oldX > x) {
				newX = (negXDir <= x) ? x : negXDir;
			}
					
			if (newX + width >= screenWidth){
				newX = screenWidth - width;
			} else if (newX < 0){
				newX = 0;
			}
			
			position.x = newX;
		}
		
		if (oldY != y || oldY == screenHeight || oldY == 0){
			if (oldY <= y){
				newY = (posYDir >= y) ? y : posYDir;
			} else {
				newY = (negYDir <= y) ? y : negYDir;
			}
			
			if (newY + height >= screenHeight) {
				newY = screenHeight - height;
			} else if (newY < 0){
				newY = 0;
			}
			 
			position.y = newY;
		}
	}
	
	/**
	 * Directs an animal to move in just the x direction
	 * 
	 * @param moveX the speed and direction to move in
	 */
	// Sheeps only care about moving in one direction at a time, so 
	// create these methods to call moveTo methods much more easily
	public void moveX(int moveX){
		moveTo(position.x + moveX, position.y);
	}
	
	/**
	 * Directs an animal to move in just the y direction
	 * 
	 * @param moveY the speed and direction to move in
	 */
	public void moveY(int moveY){
		moveTo(position.x, position.y + moveY);
	}
	
	/**
	 * Checks if the animals are colliding
	 * 
	 * @param anotherAnimal the animal to check collision against
	 * @return true if collision is detected, false otherwise
	 */
	public boolean collidesWith (Animal anotherAnimal) {
		if(anotherAnimal == null) return false;
	     
		int x1min = position.x;
		int y1min = position.x;
		int x1max = position.y + width;
		int y1max = position.y + height;
		
		int x2min = anotherAnimal.position.x;
		int y2min = anotherAnimal.position.y;
		int x2max = anotherAnimal.position.x + anotherAnimal.height;
		int y2max = anotherAnimal.position.x + anotherAnimal.height;

 		return (
		    (x1min <= x2max) &&
		    (x1max >= x2min) &&
		    (y1min <= y2max) &&
		    (y1max >= y2min)
		);
	}
	
	/**
	 * Returns the rate at which the animal moves
	 * @return the speed of the animal
	 */
	public int getSpeed() {
		return speed * 3; //make speed more significant because it will use to be rendered (pixels)
	}

	/**
	 * Returns the x and y coordinates of the animal
	 * @return the location of the animal on the screen
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * Returns the width of the bitmap image of the animal calculated from the
	 * upper left hand corner
	 * 
	 * @return width of bitmap image
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Returns the height of the bitmap image of the animal calculated from the
	 * upper left hand corner
	 * 
	 * @return height of bitmap image
	 */
	public int getHeight() {
		return height;
	}
	
}
