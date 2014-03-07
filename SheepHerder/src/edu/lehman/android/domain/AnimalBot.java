package edu.lehman.android.domain;

/**
 * Generic class to represent animated animals (bots)
 * @author marcus.silveira
 *
 */
public abstract class AnimalBot extends Animal {

	public AnimalBot(int x, int y, int speed, int width, int height) {
		super(x, y, speed, width, height);
	}

	public abstract Position move();
	
}
