package edu.lehman.android.domain;

import java.util.List;

import edu.lehman.android.factory.AnimalType;
import edu.lehman.android.views.GameSurfaceView.Boundaries;

/**
 * A specialized subclass to represent instances of sheep. It has behavior
 * specific to sheeps such as evading, getting eaten, and grazing.
 * 
 * @author marcus.silveira
 * @author Marcos Davila
 *
 */
public class Sheep extends Animal {

	public static final int FOX_AWARENESS_RANGE = 100;
	public static final int DOG_AWARENESS_RANGE = 80;

	private boolean isBeingEaten = false;
	private int direction = 0;

	private Fox closestFox;

	/**
	 * Constructs a sheep
	 * 
	 * @param type
	 *            defines the type of animal
	 * @param x
	 *            the initial starting x position
	 * @param y
	 *            the initial starting y position
	 * @param speed
	 *            how fast the animal can move
	 * @param width
	 *            width of the bitmap
	 * @param height
	 *            height of the bitmap
	 * @param b
	 *            defines the size of the surface view the animal can move in
	 */
	public Sheep(int x, int y, int speed, int width, int height, Boundaries b) {
		super(AnimalType.SHEEP, x, y, speed, width, height, b);
	}

	/**
	 * Determines how the sheep should move based on the location of the dog and
	 * the presence of one or more foxes. Sheep should do so only if it is not
	 * being eaten. It looks for the presence of any foxes first, and if it
	 * senses none it looks for the dog. If neither are close, it just wanders.
	 * 
	 * @param foxList
	 *            a list containing attributes of all foxes
	 * @param dog
	 *            a reference to the player
	 */
	public void move(List<Fox> foxList, Dog dog) {
		if (!isBeingEaten) {
			if (sees(foxList)) {
				// Evade the closest fox
				evade(position.x, position.y, closestFox.position.x,
						closestFox.position.y);
			} else if (dog.closeTo(this, DOG_AWARENESS_RANGE)) {
				// Run away from the dog
				evade(position.x, position.y, dog.position.x, dog.position.y);
			} else {
				graze( );
			}
		}
	}

	/**
	 * A flag to determine if the sheep has been caught by a fox
	 * 
	 * @return true if the sheep has been caught, false otherwise
	 */
	public boolean isBeingEaten() {
		return isBeingEaten;
	}
	
	/**
	 * A method to signify if the sheep has changed state from being eaten to
	 * not being eaten (and vice versa)
	 * 
	 * @param isBeingEaten
	 *            the new state of the sheep
	 */
	public void setBeingEaten(boolean isBeingEaten) {
		this.isBeingEaten = isBeingEaten;
	}

	/*
	 * Specialized evade subroutine that takes this animal's
	 * coordinates and the animal to avoid's coordinates and
	 * then moves away from the animal accordingly.
	 */
	private void evade(int x, int y, int animalX, int animalY) {
		int dx = Math.abs(animalX - x);
		int dy = Math.abs(animalY - y);
		
		if (dx > dy){
			if (animalX > x) {
				moveX(-speed);
				direction = LEFT;
			}
			if (animalX <= x) {
				moveX(speed);
				direction = RIGHT;
			}
		} else {
			if (animalY > y) {
				moveY(-speed);
				direction = UP;
			}
			if (animalY <= y) {
				moveY(speed);
				direction = DOWN;
			}
		}
	}

	/**
	 * Determines if the sheep is sufficiently close to one of the foxes.
	 * 
	 * @param foxList
	 *            list of fox entities in the game
	 * @return true if fox is close, false otherwise
	 */
	public boolean sees(List<Fox> foxList) {
		boolean sees = false;

		Fox fox;
		for (int i = 0; i < foxList.size(); i++) {
			fox = foxList.get(i);
			if (fox.isVisible()) {
				sees = this.closeTo(fox, FOX_AWARENESS_RANGE);

				if (sees) {
					closestFox = foxList.get(i);
					break;
				}
			}
		}

		return sees;
	}

	/**
	 * Sheep behavior when not being pursued by a fox or dog.
	 */
	public void graze() {
		final int RANGE = 4; // range of random numbers to generate

		double temp = Math.random() * RANGE;
		direction = (int) temp;

		if (direction == UP)
			moveY(-speed);
		if (direction == DOWN)
			moveY(speed);
		if (direction == LEFT)
			moveX(-speed);
		if (direction == RIGHT)
			moveX(speed);
	}
}
