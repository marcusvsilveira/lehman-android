package edu.lehman.android.domain;

public class Dog extends Animal {
   public static final int CLOSE    = 100;
   public static final int FOXCLOSE = 40;
	   
	public Dog(int x, int y, int speed, int width, int height) {
		super(AnimalType.DOG, x, y, speed, width, height);
	}
	
	public boolean closeTo (Sheep sheep) {
		Position sheepPosition = sheep.getPosition();
	    return (Math.abs(position.getX() - sheepPosition.getX()) < CLOSE)
	          && (Math.abs(position.getY() - sheepPosition.getY()) < CLOSE);
	}

	public boolean closeTo (Fox fox) {
		Position foxPosition = fox.getPosition();
	      return (Math.abs(position.getX() - foxPosition.getX()) < FOXCLOSE)
	          && (Math.abs(position.getY() - foxPosition.getY()) < FOXCLOSE);
	}
}
