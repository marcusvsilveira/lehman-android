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
				evade(closestFox, dog);
			} else if (dog.closeTo(this, DOG_AWARENESS_RANGE)) {
				evade(closestFox, dog);
			} else {
				graze();
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

	/**
	 * Logic the sheep should take to avoid any animal that is not another
	 * sheep.
	 * 
	 * @param animal
	 *            the fox or dog entity that the sheep should run away from
	 */
	public void evade(Fox fox, Dog dog) {
		int animalX = 0;
		int animalY = 0;

		int dogX = dog.getPosition().getX();
		int dogY = dog.getPosition().getY();

		int x = position.getX();
		int y = position.getY();

		int dxDog = Math.abs(dogX - x);
		int dyDog = Math.abs(dogY - y);

		if (fox != null && fox.isVisible()) {
			int foxX = fox.getPosition().getX();
			int foxY = fox.getPosition().getY();

			int dxFox = Math.abs(foxX - x);
			int dyFox = Math.abs(foxY - y);
			// who's closer?
			if ((dxFox + dyFox) > (dxDog + dyDog)) {
				// dog is closer
				// evade from dog
				animalX = dogX;
				animalY = dogY;
			} else {
				// fox is closer
				// evade from fox
				animalX = foxX;
				animalY = foxY;
			}
		} else {
			// evade from dog
			animalX = dogX;
			animalY = dogY;
		}

		// TODO this could be a little more intelligent to avoid moving into the
		// other direction where the other animal might be
		// also, this could work better if we consider the speed of both (or
		// accelaration)
		evade(x, y, animalX, animalY);
	}

	/*
	 * Specialized evade subroutine to
	 * TODO: What does this method do?
	 */
	private void evade(int x, int y, int animalX, int animalY) {
		if (animalX > animalY) {
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
