package edu.lehman.android.domain;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.util.Log;
import edu.lehman.android.factory.AnimalType;
import edu.lehman.android.views.GameSurfaceView.Boundaries;

public class Fox extends Animal {
	public static final String LOG_TAG = "FOX";
	public static final int EATING_TIME = 100; // 1 second
	public static final int DELAY = 10;
	public static final int OUT_OF_RANGE_RADIUS = 100000;
	public static final int RANGE_OF_WAITING_TIME = 30; // time for the fox to wait in
													// seconds
	public static final int NUM_EDGES = 4;
	public static final int OFF_SCREEN_FOX_RANGE = 20;
	public static final int FOX_STARTING_POINT = 100;
	
	private Random foxRandomAppearance = new Random();
	private int foxAppearanceRate;
	private int eatingClock = Fox.EATING_TIME;
	private boolean isEating = false;
	private boolean isVisible = true;

	/**
	 * Constructs a fox object and sets the rate at which the fox should appear
	 * on screen
	 * 
	 * @param type defines the type of animal
	 * @param x the initial starting x position
	 * @param y the initial starting y position
	 * @param speed how fast the animal can move
	 * @param width width of the bitmap
	 * @param height height of the bitmap
	 * @param b defines the size of the surface view the animal can move in
	 */
	public Fox(int x, int y, int speed, int width, int height, Boundaries b) {
		super(AnimalType.FOX, x, y, speed, width, height, b);
		foxAppearanceRate = foxRandomAppearance.nextInt(RANGE_OF_WAITING_TIME)
				* DELAY + 1;
	}

	public void move(Dog dog, List<Sheep> sheepList) {
		if(isEating) {
			if(eatingClock > 0) {
				Log.i(LOG_TAG, "FOX IS EATING");
				eatingClock--;
				return;
			} else {
				isEating = false;
				eatingClock = Fox.EATING_TIME;
				//ready to move again
				//TODO check which sheep was eaten and remove from the list
				Sheep sheep = null;
				for(Iterator<Sheep> it = sheepList.iterator(); it.hasNext(); ) {
					sheep = it.next();
					if(sheep.isBeingEaten() ) {
						it.remove();
					}
				}
			}
		}
		
		if (dog.collidesWith(this)) {
			Log.i(LOG_TAG, "FOX WAS CAUGHT");
			caught();
		} else if (dog.closeTo(this)) {
			Log.i(LOG_TAG, "FOX EVADE");
			evade(dog);
		} else {
			Log.i(LOG_TAG, "FOX CHASING");
			chaseClosest(sheepList);
		} 
		
	}
	
	public void caught(){
		isVisible = false;
		isEating = false;
	}
	
	public boolean canRespawn() {
		if( this.foxAppearanceRate > 0) {
			this.foxAppearanceRate--;
			return false;
		} else {
			//respawn and resets the appearance rate
			foxAppearanceRate = foxRandomAppearance.nextInt(RANGE_OF_WAITING_TIME)
					* DELAY + 1;
			return true;
		}
	}
	
	public void spawn(Position newPosition) {
		this.position.setX(newPosition.getX());
		this.position.setY(newPosition.getY());
		this.isVisible = true;
	}
	
	public void chaseClosest(List<Sheep> sheepList) {
		Sheep closest = findClosest(sheepList);

		float sheepx = closest.getPosition().getX();
		float sheepy = closest.getPosition().getY();

		float x = position.getX();
		float y = position.getY();

		float dx = Math.abs(sheepx - x);
		float dy = Math.abs(sheepy - y);

		// TODO: check if dx or dy is less than speed, and mark sheep as being
		// eaten, + move fox up to that position,
		// /and set it to is eating state for 1sec (or amount from above)

		if (dx > dy) {
			if (sheepx > x)
				moveX(speed);
			if (sheepx < x)
				moveX(-speed);
		} else {
			if (sheepy > y)
				moveY(speed);
			if (sheepy < y)
				moveY(-speed);
		}
		
		//TODO enhance this detection since it doesn't consider the distance of the movement, just the final position
		if(closest.collidesWith(this)) {
			eat(closest);
		}

	}

	private Sheep findClosest(List<Sheep> sheepList) {
		float distance = OUT_OF_RANGE_RADIUS; // out of range
		Sheep closest = null;

		for (Sheep sheep : sheepList) {
			float temp = findSheep(sheep);
			
			if (temp < distance) {
				distance = temp;
				closest = sheep;
				break;
			}
		}

		return closest;
	}

	public void evade(Dog dog) {

		Position dog_position = dog.getPosition();
		int fox_x = getPosition().getX();
		int fox_y = getPosition().getY();

		while (closeTo(dog)) {
			float dx = Math.abs(dog_position.getX() - fox_x);
			float dy = Math.abs(dog_position.getY() - fox_y);

			if (dx > dy) {
				if (dog_position.getX() > fox_x)
					moveX(-speed);
				if (dog_position.getX() < fox_x)
					moveX(speed);
			} else {
				if (dog_position.getY() > fox_y)
					moveY(-speed);
				if (dog_position.getY() < fox_y)
					moveY(speed);
			}
		}
	}

	@Override
	public void moveX(int moveX) {
		int foxPos = getPosition().getX() + moveX;
		
		(getPosition()).setX(foxPos);
		
		if (foxPos < 0 || foxPos > visible_screen_boundaries.getScreenWidth());
				setVisible(false); // Fox gets away
	}

	@Override
	public void moveY(int moveY) {
		int foxPos = getPosition().getY();

		(getPosition()).setY(foxPos);

		if (foxPos < 0 || foxPos > visible_screen_boundaries.getScreenHeight())
			setVisible(false); // Fox gets away
	}

	/**
	 * Returns the position between this fox and a sheep
	 * @param sheeps the sheep to determine distance from
	 * @return distance between fox and sheep
	 */
	private int findSheep(Sheep sheeps) {
		return Math.abs(sheeps.getPosition().getX() - position.getX())
				+ Math.abs(sheeps.getPosition().getY() - position.getY());
	}

	private void eat(Sheep sheep) {
		Log.i(LOG_TAG, "FOX CAUGHT A SHEEP");
		//fox is eating now
		isEating = true;
		sheep.setBeingEaten(true);
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
