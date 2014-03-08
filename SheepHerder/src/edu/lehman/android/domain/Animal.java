package edu.lehman.android.domain;

/**
 * Generic class to represent an animal
 * @author marcus.silveira
 *
 */
public abstract class Animal {

	protected int speed;
	protected Position position;
	protected int width;
	protected int height;
	protected AnimalType type;

	public Animal(AnimalType type, int x, int y, int speed, int width, int height){
		this.position = new Position(x, y);
		this.speed = speed;
		this.width = width;
		this.height = height;
	}
	
	public void moveX(int moveX) {
		position.setX(position.getX() + moveX);
	}

	public void moveY(int moveY) {
		position.setY(position.getY() + moveY);
	}
	
	/**
	 * Checks if the animals are colliding
	 * @param anotherAnimal
	 * @return
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
	
	public int getSpeed() {
		return speed;
	}

	public Position getPosition() {
		return position;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
}
