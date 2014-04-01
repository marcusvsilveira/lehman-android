package edu.lehman.android.domain;

import edu.lehman.android.factory.AnimalType;
import edu.lehman.android.views.GameSurfaceView.Boundaries;

public class Dog extends Animal {

	/**
	 * Constructs a single dog entity
	 * 
	 * @param x
	 *            starting x coordinate
	 * @param y
	 *            starting y coordinate
	 * @param speed
	 *            how fast the dog should move
	 * @param width
	 *            width of the bitmap
	 * @param height
	 *            height of the bitmap
	 * @param b
	 *            Boundaries object to communicate size of touch screen
	 */
	public Dog(final int x, final int y, final int speed, final int width,
			final int height, Boundaries b) {
		super(AnimalType.DOG, x, y, speed, width, height, b);
	}

}
