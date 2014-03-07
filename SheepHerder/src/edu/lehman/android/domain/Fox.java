package edu.lehman.android.domain;

import java.util.List;

public class Fox extends AnimalBot {
	public final long EATING_TIME = 1000;	// 1 second 

	private boolean isEating = false;
	private boolean isVisible = false;
	
	public Fox(int x, int y, int speed, int width, int height) {
		super(x, y, speed, width, height);
	}
	
	@Override
	public Position move() {
		return position;
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

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
	
}
