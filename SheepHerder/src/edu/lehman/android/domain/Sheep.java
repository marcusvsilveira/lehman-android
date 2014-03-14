package edu.lehman.android.domain;

import java.util.Random;

import android.util.Log;

import edu.lehman.android.SheepHerderActivity.Boundaries;

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
	
	/*
	 * The directions a sheep can move in
	 */
	private final int UP = 0;
	private final int DOWN = 1;
	private final int LEFT = 2;
	private final int RIGHT = 3;
	  
	public Sheep(int x, int y, int speed, int width, int height, Boundaries b) {
		super(AnimalType.SHEEP, x, y, speed, width, height, b);
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
		int animalX = animal.getPosition().getX();
		int animalY = animal.getPosition().getY();

	    int x = position.getX();
	    int y = position.getY();

	    int dx = Math.abs(animalX - x);
	    int dy = Math.abs(animalY - y);

	    if (dx > dy) {
	    	if (animalX > x) {
	    		moveX(-speed);
	    		direction = LEFT;
	        }
	        if (animalX < x) {
	            moveX(speed);
	            direction = RIGHT;
	        }
	    } else {
	    	if (animalY > y) {
	            moveY(-speed);
	            direction = UP;
	        }
	        if (animalY < y) {
	            moveY(speed);
	            direction = DOWN;
	        }
	    }
	}
	
	public boolean sees (Fox fox) {
		return (Math.abs(position.getX() - fox.getPosition().getX()) < FOX_AWARENESS_RANGE)
				&& (Math.abs(position.getY() - fox.getPosition().getY()) < FOX_AWARENESS_RANGE);
	}
	
	/**
	 * Actions sheep should take when not being pursued by fox or dog.
	 */
	public void graze() {
		final int RANGE = 6;	// range of random numbers to generate
		final int TOTAL_NUM_DIRS = 4;	// total number of directions
		final int MAX_COUNTDOWN_VAL = 200;	// value to reset count down to
		
		if (countdown == 0) {
			countdown = MAX_COUNTDOWN_VAL;

			direction = random.nextInt(RANGE);
		}

		if (countdown % TOTAL_NUM_DIRS == 0) {
			if (direction == UP) moveY(-speed);
			if (direction == DOWN) moveY(speed);
			if (direction == LEFT) moveX(-speed);
			if (direction == RIGHT) moveX(speed);
		}

		countdown--;
	}
	
}
