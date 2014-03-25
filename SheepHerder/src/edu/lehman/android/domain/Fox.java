package edu.lehman.android.domain;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import edu.lehman.android.views.GameSurfaceView.Boundaries;

public class Fox extends Animal {
	public final long EATING_TIME = 1000; // 1 second
	public final int DELAY = 1000;
	public final int OUT_OF_RANGE_RADIUS = 100000;
	public final int RANGE_OF_WAITING_TIME = 30; // time for the fox to wait in
													// seconds

	public Random foxRandomAppearance = new Random();
	public int foxAppearanceRate;
	private boolean isEating = false;
	private boolean isVisible = false;

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

	}

	private Sheep findClosest(List<Sheep> sheepList) {
		float distance = OUT_OF_RANGE_RADIUS; // out of range
		Sheep closest = null;

		for (Sheep sheep : sheepList) {
			float temp = Math.abs(sheep.getPosition().getX() - position.getX())
					+ Math.abs(sheep.getPosition().getY() - position.getY());
			if (temp < distance) {
				distance = temp;
				closest = sheep;
			}
		}

		return closest;
	}

	public void evade(Dog dog) {

		Position dog_position = dog.getPosition();
		int fox_x = getPosition().getX();
		int fox_y = getPosition().getY();

		while (!isFoxSafe(dog_position)) {
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
		int foxPos = getPosition().getX();
		final int FOX_STARTING_POINT = -20;

		// Calculate distance between fox and edge of screen
		if (moveX < 0) {

			while (foxPos >= 0)
				(getPosition()).setX(foxPos + moveX);

			// Reset fox off the screen
			(getPosition()).setX(FOX_STARTING_POINT);

		} else {

			while (foxPos <= visible_screen_boundaries.getScreenWidth())
				(getPosition()).setX(foxPos + moveX);

			// Reset fox off the screen
			(getPosition()).setX(visible_screen_boundaries.getScreenWidth()
					- FOX_STARTING_POINT);

		}
	}

	@Override
	public void moveY(int moveY) {
		int foxPos = getPosition().getY();
		final int FOX_STARTING_POINT = -20;

		// Calculate distance between fox and edge of screen
		if (moveY < 0) {

			while (foxPos >= 0)
				(getPosition()).setY(foxPos + moveY);

			// Reset fox off the screen
			(getPosition()).setY(FOX_STARTING_POINT);

		} else {

			while (foxPos <= visible_screen_boundaries.getScreenHeight())
				(getPosition()).setY(foxPos + moveY);

			// Reset fox off the screen
			(getPosition()).setY(visible_screen_boundaries.getScreenHeight()
					- FOX_STARTING_POINT);

		}
	}

	private boolean isFoxSafe(Position dog_position) {
		// Calculate distance between fox and dog and see if the dog is close
		final int RADIUS = 100;
		float dogx = dog_position.getX();
		float dogy = dog_position.getY();

		float x = position.getX();
		float y = position.getY();

		float dx = Math.abs(dogx - x);
		float dy = Math.abs(dogy - y);

		if (dx * dx - dy * dy < RADIUS) {
			return true;
		} else {
			return false;
		}
	}

	private Position findSheep(List<Sheep> sheeps) {
		return null;
	}

	private void eat(Sheep sheep) {

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
