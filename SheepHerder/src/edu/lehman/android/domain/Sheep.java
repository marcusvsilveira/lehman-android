package edu.lehman.android.domain;

public class Sheep extends AnimalBot {

	private boolean isBeingEaten = false;
	public Sheep(int x, int y, int speed, int width, int height) {
		super(x, y, speed, width, height);
	}
	
	@Override
	public Position move() {
		// TODO Auto-generated method stub
		
		if (isBeingEaten){
			
		}
		
		return position;
	}
	
	// move away from dog
	public boolean isSafeFromDog(Position dog_pos){
		return false;
	}
	
	// move away from fox
	public boolean isSafeFromFox(Position fox_pos){
		return false;
	}

	public boolean isBeingEaten() {
		return isBeingEaten;
	}

	public void setBeingEaten(boolean isBeingEaten) {
		this.isBeingEaten = isBeingEaten;
	}
	
}
