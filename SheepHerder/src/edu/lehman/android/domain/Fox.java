package edu.lehman.android.domain;

import java.util.List;
import java.util.Random;

public class Fox extends Animal {
	public final long EATING_TIME = 1000;	// 1 second 
	public final int OUT_OF_RANGE_RADIUS = 100000;
	
	private static Random random = new Random();
	
	private boolean isEating = false;
	private boolean isVisible = false;
	
	public Fox(int x, int y, int speed, int width, int height) {
		super(AnimalType.FOX, x, y, speed, width, height);
	}
	
	public void move(Dog dog, List<Sheep> sheepList) {
		//TODO check when fox should show up // random logic
	  
	  // if fox goes off screen get time break
		if (dog.collidesWith(this)) {
			setVisible(false);
		} else if (dog.closeTo(this)) {
			evade(dog);
		} else {
			chaseClosest(sheepList);
		}
	}
	
	public void chaseClosest(List<Sheep> sheepList) {
		Sheep closest = findClosest(sheepList);

		float sheepx = closest.getPosition().getX();
		float sheepy = closest.getPosition().getY();

		float x = position.getX();
		float y = position.getY();

		float dx = Math.abs(sheepx - x);
		float dy = Math.abs(sheepy - y);

		//TODO: check if dx or dy is less than speed, and mark sheep as being eaten, + move fox up to that position, 
		///and set it to is eating state for 1sec (or amount from above)
		
		if (dx > dy) {
			if (sheepx > x)  moveX(speed);
			if (sheepx < x)  moveX(-speed);
		} else {
			if (sheepy > y)  moveY(speed);
			if (sheepy < y)  moveY(-speed);
		}
		
		
	}
	   
	private Sheep findClosest(List<Sheep> sheepList) {
		float distance = OUT_OF_RANGE_RADIUS; // out of range
		Sheep closest = null;

		for (Sheep sheep : sheepList) {
			float temp = Math.abs(sheep.getPosition().getX() - position.getX())
					+ Math.abs(sheep.getPosition().getY() - position.getY());
			if (temp < distance) {
				distance = temp;
				closest  = sheep;
			}
		}
     
		return closest;
	}
	   
	public void evade(Dog dog) {
		float dogx = dog.getPosition().getX();
		float dogy = dog.getPosition().getY();

		float x = position.getX();
		float y = position.getY();

		float dx = Math.abs(dogx - x);
		float dy = Math.abs(dogy - y);

	    if (dx > dy) {
	    	if (dogx > x)  moveX(-speed);
	        if (dogx < x)  moveX(speed);
	    } else {
	    	if (dogy > y) moveY(-speed);
	    	if (dogy < y) moveY(speed);
	    }
	}
	
	private boolean isFoxSafe(Position dog_position) {
		// TODO: Calculate distance between fox and dog and see if the dog is close
		return false;
	}
	
	private void runAway(Position dog_position){
		
	}
	
	private Position findSheep(List<Sheep> sheeps){
		return null;
	}

	private void eat(Sheep sheep){
		
	}

	public boolean isEating() {
		return isEating;
	}

	public void setEating(boolean isEating) {
		this.isEating = isEating;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
	
}
