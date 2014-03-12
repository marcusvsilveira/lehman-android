package edu.lehman.android.domain;

public class Dog extends Animal {
	public static final int CLOSE = 100;
	public static final int FOXCLOSE = 40;

	public Dog(final int x, final int y, final int speed, final int width,
			final int height) {
		super(AnimalType.DOG, x, y, speed, width, height);
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

	public void moveTo(final int x, final int y) {
		int oldX = this.getPosition().getX();
		int oldY = this.getPosition().getY();
		
		// There is an inverse relationship between the pause rate
		// of the move function and the dog's speed. The higher the
		// speed, the less time the thread should be paused.
		final int APPARENT_DOG_SPEED = 15 - getSpeed();

		while (true) {
			if (oldX == x && oldY == y) {
				break;
			}

			if (oldX != x) {
				if (oldX < x) {
					position.setX(oldX++);
				} else {
					position.setX(oldX--);
				}
			}

			if (oldY != y) {
				if (oldY < y) {
					position.setY(oldY++);
				} else {
					position.setY(oldY--);
				}
			}

			try {
				Thread.sleep(APPARENT_DOG_SPEED);
			} catch (Exception e) {

			}
		}

	}
}
