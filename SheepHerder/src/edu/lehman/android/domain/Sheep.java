package edu.lehman.android.domain;

import java.util.List;

import edu.lehman.android.views.GameSurfaceView.Boundaries;

/**
 * Sheep representation
 * 
 * @author marcus.silveira
 *
 */
public class Sheep extends Animal {
	public static final float FOX_AWARENESS_RANGE = 200;

	private boolean isBeingEaten = false;
	private int direction = 0;

	/*
	 * The directions a sheep can move in
	 */
	private final int UP = 0;
	private final int DOWN = 1;
	private final int LEFT = 2;
	private final int RIGHT = 3;

	private Fox closestFox;

	public Sheep(int x, int y, int speed, int width, int height, Boundaries b) {
		super(AnimalType.SHEEP, x, y, speed, width, height, b);
	}

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

	// move away from dog
	public boolean isSafeFromDog(Position dog_pos) {
		return false;
	}

	// move away from fox
	public boolean isSafeFromFox(Position fox_pos) {
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

	public boolean sees(List<Fox> foxList) {
		boolean sees = false;

		for (int i = 0; i < foxList.size(); i++) {
			sees = (Math.abs(position.getX()
					- foxList.get(i).getPosition().getX()) < FOX_AWARENESS_RANGE)
					&& (Math.abs(position.getY()
							- foxList.get(i).getPosition().getY()) < FOX_AWARENESS_RANGE);

			if (sees) {
				closestFox = foxList.get(i);
				break;
			}
		}

		return sees;
	}

	/**
	 * Defines sheep behavior when a sheep is not being pursued by a fox or dog.
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
