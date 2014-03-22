package edu.lehman.android.domain;

import edu.lehman.android.views.GameSurfaceView.Boundaries;

public class Dog extends Animal {
	public static final int CLOSE = 100;
	public static final int FOXCLOSE = 40;

	public Dog(final int x, final int y, final int speed, final int width,
			final int height, Boundaries b) {
		super(AnimalType.DOG, x, y, speed, width, height, b);
	}

	public boolean closeTo(final Fox fox) {
		final Position foxPosition = fox.getPosition();
		return Math.abs(position.getX() - foxPosition.getX()) < FOXCLOSE
				&& Math.abs(position.getY() - foxPosition.getY()) < FOXCLOSE;
	}

	public boolean closeTo(final Sheep sheep) {
		final Position sheepPosition = sheep.getPosition();
		return Math.abs(position.getX() - sheepPosition.getX()) < CLOSE
				&& Math.abs(position.getY() - sheepPosition.getY()) < CLOSE;
	}
}
