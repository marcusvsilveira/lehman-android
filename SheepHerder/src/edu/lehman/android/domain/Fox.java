package edu.lehman.android.domain;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import android.util.Log;
import edu.lehman.android.factory.AnimalType;
import edu.lehman.android.views.GameSurfaceView.Boundaries;

public class Fox extends Animal {
	public static final String LOG_TAG = "FOX";
	public static final int EATING_TIME = 50; 
	public static final int DELAY = 10;
	public static final int OUT_OF_RANGE_RADIUS = 100000;
	public static final int RANGE_OF_WAITING_TIME = 30; // time for the fox to wait in
													// seconds
	public static final int DOG_AWARENESS_RANGE = 120;
	
	public static final int NUM_EDGES = 4;
	public static final int OFF_SCREEN_FOX_RANGE = 20;
	public static final int FOX_STARTING_POINT = 100;
	
	private Random foxRandomAppearance = new Random();
	private int foxAppearanceRate;
	private int eatingClock = Fox.EATING_TIME;
	private boolean isEating = false;
	private boolean isVisible = false;
	private boolean ranAway = false;

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
		if (dog.collidesWith(this)) {
			Log.i(LOG_TAG, "FOX WAS CAUGHT");
			caught(sheepList);
		} else {
			if(isEating) {
				if(eatingClock > 0) {
					Log.i(LOG_TAG, "FOX IS EATING");
					eatingClock--;
					return; //can't move
				} else {
					isEating = false;
					eatingClock = Fox.EATING_TIME;
					//ready to move again
					
					Sheep sheep = null;
					for(Iterator<Sheep> it = sheepList.iterator(); it.hasNext(); ) {
						sheep = it.next();
						if(sheep.isBeingEaten() ) {
							it.remove(); 
						}
					}
				}
			}
			
			if (dog.closeTo(this, DOG_AWARENESS_RANGE)) {
				Log.i(LOG_TAG, "FOX EVADE");
				evade(dog);
			} else {
				Log.i(LOG_TAG, "FOX CHASING");
				boolean chasing = chaseClosest(sheepList);
				if(!chasing) {
					//TODO move around randomly
				}
			} 
		}	
	}
	
	public void caught(List<Sheep> sheepList){
		isVisible = false;
		ranAway = false;
		isEating = false;
		Sheep sheep = null;
		for(Iterator<Sheep> it = sheepList.iterator(); it.hasNext(); ) {
			sheep = it.next();
			if(sheep.isBeingEaten() ) {
				sheep.setBeingEaten(false); // sheep was SAVED!
			}
			
		}
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
		this.ranAway = false;
		this.isEating = false;
	}
	
	public boolean chaseClosest(List<Sheep> sheepList) {
		Sheep closest = findClosest(sheepList);

		if( closest == null) {
			return false; //no sheep in range to chase
		}
		
		int sheepx = closest.getPosition().getX();
		int sheepy = closest.getPosition().getY();

		int x = position.getX();
		int y = position.getY();

		int dx = Math.abs(sheepx - x);
		int dy = Math.abs(sheepy - y);

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
		return true;
	}

	private Sheep findClosest(List<Sheep> sheepList) {
		int distance = OUT_OF_RANGE_RADIUS; // out of range
		Sheep closest = null;
	

		for (Sheep sheep : sheepList) {
			int temp = findSheep(sheep);
			
			if (temp < distance) {
				distance = temp;
				closest = sheep;
			}
		}

		return closest;
	}

	public void evade(Dog dog) {
		Position dog_position = dog.getPosition();
		int fox_x = position.getX();
		int fox_y = position.getY();

		if (dog.closeTo(this, DOG_AWARENESS_RANGE)) {
			this.surpassBoundaries = true; //enables going off the screen when moving
			int dx = Math.abs(dog_position.getX() - fox_x);
			int dy = Math.abs(dog_position.getY() - fox_y);

			if (dx > dy) {
				if (dog_position.getX() > fox_x) {
					moveX(-speed);
				} else if (dog_position.getX() < fox_x) {
					moveX(speed);
				}
			} else {
				if (dog_position.getY() > fox_y) {
					moveY(-speed);
				} else if (dog_position.getY() < fox_y) {
					moveY(speed);
				}
			}
			this.surpassBoundaries = false; //resets for the next movement
		}
	}

	/**
	 * Moves the fox only along the x-axis. Checks to see if the fox leaves
	 * the boundaries of the screen.
	 */
	@Override
	public void moveX(int moveX) {
		super.moveX(moveX);
		
		if (position.x < 0 || position.x > screenWidth) { 
			isVisible = false; //fox gets away (evade)
			ranAway = true;
			isEating = false;
		}
	}

	/**
	 * Moves the fox only along the y-axis. Checks to see if the fox leaves
	 * the boundaries of the screen.
	 */
	@Override
	public void moveY(int moveY) {
		super.moveY(moveY);

		if (position.y < 0 || position.y > screenHeight) {
			isVisible = false; //fox gets away (evade)
			ranAway = true;
			isEating = false;
		}
	}

	/**
	 * Returns the position between this fox and a sheep
	 * @param sheeps the sheep to determine distance from
	 * @return distance between fox and sheep
	 */
	private int findSheep(Sheep sheeps) {
		return Math.abs(sheeps.position.x - position.x)
				+ Math.abs(sheeps.position.y - position.y);
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

	public boolean hasRunAway() {
		return ranAway;
	}
	
}
