package edu.lehman.android.domain;

import java.util.Random;

/**
 * Sheep representation
 * @author marcus.silveira
 *
 */
public class Sheep extends Animal {
	public static final float FOX_AWARENESS_RANGE = 200;
	private static Random random = new Random();
	
	private boolean isBeingEaten = false;
	private int direction = 0;
	private int countdown;
	  
	public Sheep(int x, int y, int speed, int width, int height) {
		super(AnimalType.SHEEP, x, y, speed, width, height);
	}
	
	
	public void move(Fox fox, Dog dog) {
		if (!isBeingEaten){
           if (sees(fox)) {
              evade(fox);
           } else if (dog.closeTo(this)) {
              evade(dog);
           } else {
              graze();
           }
        }
	}
	
	// move away from dog
	public boolean isSafeFromDog(Position dog_pos){
		return false;
	}
	
	// move away from fox
	public boolean isSafeFromFox(Position fox_pos){
		return false;
	}

	public boolean isBeingEaten() {
		return isBeingEaten;
	}

	public void setBeingEaten(boolean isBeingEaten) {
		this.isBeingEaten = isBeingEaten;
	}
	
	public void evade(Animal animal) {
		float animalX = animal.getPosition().getX();
		float animalY = animal.getPosition().getY();

	    float x = position.getX();
	    float y = position.getY();

	    float dx = Math.abs(animalX - x);
	    float dy = Math.abs(animalY - y);

	    if (dx > dy) {
	    	if (animalX > x) {
	    		moveX(-speed);
	            direction = 3;
	        }
	        if (animalX < x) {
	            moveX(speed);
	            direction = 4;
	        }
	    } else {
	    	if (animalY > y) {
	            moveY(-speed);
	            direction = 1;
	        }
	        if (animalY < y) {
	            moveY(speed);
	            direction = 2;
	        }
	    }
	}
	
	public boolean sees (Fox fox) {
		return (Math.abs(position.getX() - fox.getPosition().getX()) < FOX_AWARENESS_RANGE)
				&& (Math.abs(position.getY() - fox.getPosition().getY()) < FOX_AWARENESS_RANGE);
	}
	public void graze() {
		if (countdown == 0) {
			countdown = 200;

			direction = random.nextInt(6);
		}

		if (countdown % 4 == 0) {
			if (direction == 1) moveY(-speed);
			if (direction == 2) moveY(speed);
			if (direction == 3) moveX(-speed);
			if (direction == 4) moveX(speed);
		}

		countdown--;
	}
	
}
