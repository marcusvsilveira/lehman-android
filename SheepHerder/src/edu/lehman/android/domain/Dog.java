package edu.lehman.android.domain;

import android.util.Log;
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

	@Override
	public void moveTo(final int x, final int y) {
		final int PACE = getSpeed();
		int correctedX = x, correctedY = y;
		int posXDir, negXDir, posYDir, negYDir;
		int newXLocation, newYLocation;
		
		/*
		 * Since the (x,y) coordinate of the dog starts from the upper left 
		 * corner, there arises a situation in which a user can tap on the
		 * edge of the screen and the location specified in (x,y) coordinates
		 * do not place the entire dog bitmap on the screen. If that case
		 * arises, the graphic will need to be reset so that the entire
		 * image is visible. The last valid (x,y) pixel which can be tapped
		 * on the screen without causing the image to forcibly reset would
		 * be the width of the screen - width of the bitmap image, and likewise
		 * for height.
		 */
		final int lastValidXPos = visible_screen_boundaries.getScreenWidth() - width;
		final int lastValidYPos = visible_screen_boundaries.getScreenHeight() - height;
		
		correctedX = (correctedX > lastValidXPos) ? lastValidXPos : correctedX;
		correctedY = (correctedY > lastValidYPos) ? lastValidYPos : correctedY;
		
		while (position.getX() != correctedX || position.getY() != correctedY) {
			
			if (position.getX() != correctedX) {
				posXDir = position.getX() + PACE;
				negXDir = position.getX() - PACE;

				// The dog calculates the direction that it needs to move in
				// and starts moving in that direction. The newXLocation is
				// chosen based on where the dog is supposed to end up and
				// corrects
				// for cases where the dog would end up outside of the small
				// clickable range.
				if (position.getX() <= correctedX) {
					newXLocation = (posXDir >= correctedX) ? correctedX
							: posXDir;
				} else {
					newXLocation = (negXDir <= correctedX) ? correctedX
							: negXDir;
				}

				position.setX(newXLocation);
			}
			
			if (position.getY() != correctedY) {
				posYDir = position.getY() + PACE;
				negYDir = position.getY() - PACE;

				// The dog calculates the direction that it needs to move in
				// and starts moving in that direction. The newXLocation is
				// chosen based on where the dog is supposed to end up and
				// corrects
				// for cases where the dog would end up outside of the small
				// clickable range.
				if (position.getY() <= correctedY) {
					newYLocation = (posYDir >= correctedY) ? correctedY
							: posYDir;
				} else {
					newYLocation = (negYDir <= correctedY) ? correctedY
							: negYDir;
				}

				position.setY(newYLocation);
			}

			/*
			 * The dog needs to sleep so that the image appears to slide across
			 * the screen
			 */
			try {
				Thread.sleep(17);
			} catch (InterruptedException ie) {
				Log.v("Animal movement", "Sleeping dog was interrupted.");
			}
		}	
	}
}
