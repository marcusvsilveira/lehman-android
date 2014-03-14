package edu.lehman.android.domain;

import edu.lehman.android.SheepHerderActivity.Boundaries;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

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
	protected int SCREEN_WIDTH;
	protected int SCREEN_HEIGHT;
	protected Boundaries visible_screen_boundaries;

	public Animal(AnimalType type, int x, int y, int speed, int width, int height, Boundaries b){
		this.position = new Position(x, y);
		this.speed = speed;
		this.width = width;
		this.height = height;
		visible_screen_boundaries = b;
	}
	
	public void moveX(int moveX) {
		if (position.getX() + moveX > 0 && position.getX() + moveX < visible_screen_boundaries.getScreenWidth())
			position.setX(position.getX() + moveX);
		else if (position.getX() + moveX <= 0)
			position.setX(0);
		else
			position.setX(visible_screen_boundaries.getScreenWidth());
			
	}

	public void moveY(int moveY) {
		if (position.getY() + moveY > 0 && position.getY() + moveY < visible_screen_boundaries.getScreenHeight())
			position.setY(position.getY() + moveY);
		else if (position.getY() + moveY <= 0)
			position.setY(0);
		else
			position.setY(visible_screen_boundaries.getScreenHeight());
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
