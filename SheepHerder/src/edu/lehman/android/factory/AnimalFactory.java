package edu.lehman.android.factory;

import edu.lehman.android.domain.Animal;
import edu.lehman.android.domain.Dog;
import edu.lehman.android.domain.Fox;
import edu.lehman.android.domain.Sheep;
import edu.lehman.android.views.GameSurfaceView.Boundaries;

public class AnimalFactory {

	public static Animal createAnimal(AnimalType t, final int x, final int y, final int speed, final int width,
			final int height, Boundaries b){
		Animal animal = null;
		
		switch (t) {
		case DOG:
			animal = new Dog(x,y,speed,width,height,b);
			break;
		case FOX:
			animal = new Fox(x,y,speed,width,height,b);
			break;
		case SHEEP:
			animal = new Sheep(x,y,speed,width,height,b);
			break;
		default:
			break;
		}
		
		return animal;
	}
}
