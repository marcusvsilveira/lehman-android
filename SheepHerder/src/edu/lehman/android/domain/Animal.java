package edu.lehman.android.domain;

public abstract class Animal {

	private int speed;
	private Position position;

	public Animal(int x, int y){
		setup(x, y);
	}
	
	public Animal(){
		setup(0, 0);
	}
	
	private void setup(int x, int y){
		
	}
	
	public abstract Position move(double moveX, double moveY);
	
}
