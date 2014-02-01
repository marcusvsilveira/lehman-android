package edu.lehman.android.domain;

public class Sheep extends Animal {

	private boolean isBeingEaten = false;
	
	public Sheep(){
		
	}
	
	@Override
	public Position move(double moveX, double moveY) {
		// TODO Auto-generated method stub
		
		if (isBeingEaten){
			
		}
		
		return null;
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
