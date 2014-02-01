package edu.lehman.android.domain;

import java.util.List;

public class Fox extends Animal {

	private boolean isEating = false;
	public final long EATING_TIME = 1000;	// 1 second 
	
	@Override
	public Position move(double moveX, double moveY) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private boolean isFoxSafe(Position dog_position) {
		// TODO: Calculate distance between fox and dog and see if the dog is close
		return false;
	}
	
	private void runAway(Position dog_position){
		
	}
	
	private Position findSheep(List<Sheep> sheeps){
		return null;
	}

	private void eat(Sheep sheep){
		
	}

	public boolean isEating() {
		return isEating;
	}

	public void setEating(boolean isEating) {
		this.isEating = isEating;
	}
}
