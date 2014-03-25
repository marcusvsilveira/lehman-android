package edu.lehman.android.domain;

import edu.lehman.android.views.GameSurfaceView.Boundaries;

/**
 * Generic class to represent characteristics all animal entities share.
 * 
 * @author marcus.silveira
 * @author Marcos Davila
 *
 */
public abstract class Animal {

	protected int speed;
	protected Position position;
	protected int width;
	protected int height;
	protected AnimalType type;
	protected Boundaries visible_screen_boundaries;

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
		visible_screen_boundaries = b;
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
		final int PACE = 15 + getSpeed();
		Position p = getPosition();
		int oldX = p.getX();
		int oldY = p.getY();
		int newX, newY;

		// If the dog is not told to move to where it already is, it calculates
		// the direction that it needs to move in and then moves one unit in that 
		// direction
		if (oldX != x){
			newX = (oldX < x) ? oldX+PACE : oldX-PACE;
			
			if (newX + width >= visible_screen_boundaries.getScreenWidth()){
				newX = visible_screen_boundaries.getScreenWidth() - width;
			} else if (newX < 0){
				newX = 0;
			}
			
			position.setX(newX);
		}
		
		if (oldY != y){
			newY = (oldY < y) ? oldY+PACE : oldY-PACE;
			
			if (newY + height >= visible_screen_boundaries
					.getScreenHeight()) {
				newY = visible_screen_boundaries.getScreenHeight() - height;
			} else if (newY < 0){
				newY = 0;
			}
			 
			position.setY(newY);
		}

		// Pause the thread for 1/1000 of a second
		final int THREAD_SLEEP = 15;

		try {
			Thread.sleep(THREAD_SLEEP);
		} catch (Exception e) {
			// Do nothing
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
		Position p = getPosition();
		moveTo(p.getX() + moveX, p.getY());
	}
	
	/**
	 * Directs an animal to move in just the y direction
	 * 
	 * @param moveY the speed and direction to move in
	 */
	public void moveY(int moveY){
		Position p = getPosition();
		moveTo(p.getX(), p.getY() + moveY);
	}
	
	/**
	 * Checks if the animals are colliding
	 * 
	 * @param anotherAnimal the animal to check collision against
	 * @return true if collision is detected, false otherwise
	 */
	public boolean collidesWith (Animal anotherAnimal) {
		if(anotherAnimal == null) return false;
	     
		float x1min = position.getX();
		float y1min = position.getY();
		float x1max = position.getX() + width;
		float y1max = position.getY() + height;
		
		float x2min = anotherAnimal.getPosition().getX();
		float y2min = anotherAnimal.getPosition().getY();
		float x2max = anotherAnimal.position.getX() + anotherAnimal.getWidth();
		float y2max = anotherAnimal.position.getX() + anotherAnimal.getHeight();

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
		return speed;
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
