package edu.lehman.android.domain;

import java.util.List;

import edu.lehman.android.factory.AnimalType;
import edu.lehman.android.views.GameSurfaceView.Boundaries;

/**
 * Sheep representation
 * 
 * @author marcus.silveira
 *
 */
public class Sheep extends Animal {
	public static final int FOX_AWARENESS_RANGE = 200;

	private boolean isBeingEaten = false;
	private int direction = 0;

	/*
	 * The directions a sheep can move in
	 */
	

	private Fox closestFox;

	/**
	 * Constructs a sheep
	 * 
	 * @param type defines the type of animal
	 * @param x the initial starting x position
	 * @param y the initial starting y position
	 * @param speed how fast the animal can move
	 * @param width width of the bitmap
	 * @param height height of the bitmap
	 * @param b defines the size of the surface view the animal can move in
	 */
	public Sheep(int x, int y, int speed, int width, int height, Boundaries b) {
		super(AnimalType.SHEEP, x, y, speed, width, height, b);
	}

	/**
	 * Determines how the sheep should move based on the location of the dog
	 * and the presence of one or more foxes. Sheep should do so only if it
	 * is not being eaten. It looks for the presence of any foxes first, and
	 * if it senses none it looks for the dog. If neither are close, it just
	 * wanders.
	 * 
	 * @param foxList a list containing attributes of all foxes
	 * @param dog a reference to the player
	 */
	public void move(List<Fox> foxList, Dog dog) {
		if (!isBeingEaten) {
			if (sees(foxList)) {
				evade(closestFox);
			} else if (dog.closeTo(this)) {
				evade(dog);
			} else {
				graze();
			}
		}
	}
	
	/**
	 * A flag to determine if the sheep has been caught by a fox
	 * @return true if the sheep has been caught, false otherwise
	 */
	public boolean isBeingEaten() {
		return isBeingEaten;
	}

	/**
	 * A method to signify if the sheep has changed state from being
	 * eaten to not being eaten (and vice versa)
	 * @param isBeingEaten the new state of the sheep
	 */
	public void setBeingEaten(boolean isBeingEaten) {
		this.isBeingEaten = isBeingEaten;
	}

	/**
	 * Logic the sheep should take to avoid any animal that is not another
	 * sheep.
	 * @param animal the fox or dog entity that the sheep should run away from
	 */
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

	/**
	 * Determines if the sheep is sufficiently close to one of the foxes.
	 * 
	 * @param foxList list of fox entities in the game
	 * @return true if fox is close, false otherwise
	 */
	public boolean sees(List<Fox> foxList) {
		boolean sees = false;

		for (int i = 0; i < foxList.size(); i++) {
			sees = this.closeTo(foxList.get(i));

			if (sees) {
				closestFox = foxList.get(i);
				break;
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
