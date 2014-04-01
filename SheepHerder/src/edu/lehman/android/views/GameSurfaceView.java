package edu.lehman.android.views;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.lehman.android.domain.Dog;
import edu.lehman.android.domain.Fox;
import edu.lehman.android.domain.Position;
import edu.lehman.android.domain.Sheep;
import edu.lehman.android.factory.AnimalFactory;
import edu.lehman.android.factory.AnimalType;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

/**
 * Game Surface View responsible for drawing animals on screen as fast as
 * possible smoothly
 * 
 * @author marcus.silveira
 *
 */

@SuppressLint("ViewConstructor")
public class GameSurfaceView extends SurfaceView implements Callback {

	/**
	 * A class to hold the width and height of the phone
	 * 
	 * @author Marcos Davila
	 *
	 */
	public class Boundaries {
		private int WIDTH, HEIGHT;

		// Sets width and height to predetermined values w and h
		public Boundaries(int w, int h) {
			WIDTH = w;
			HEIGHT = h;
		}

		public int getScreenWidth() {
			return WIDTH;
		}

		public int getScreenHeight() {
			return HEIGHT;
		}

		public void printBoundaries() {
			Log.i("Boundaries: ", WIDTH + "x" + HEIGHT);
		}
	}

	private Bitmap dogBitmap;
	private Bitmap foxBitmap;
	private Bitmap sheepBitmap;

	private SurfaceHolder surfaceHolder;
	private Thread gameThread;

	private Dog dog;
	private List<Fox> foxList;
	private List<Sheep> sheepList;
	private Canvas canvas;
	private boolean running = true;
	private int NUM_FOX, NUM_SHEEP, DOG_SPEED, FOX_SPEED, SHEEP_SPEED;
	private Boundaries surfaceBoundaries;
	private Random locationGenerator = new Random();
	
	private boolean dogMovementLock = false;
	private boolean isLongPressing = false;
	private int dogDirectionY;
	private int dogDirectionX;
	
	// Approximates 1/60 of a second. The game runs at 60 FPS
	private final int GAME_SPEED = 17;
	private final String LOG_TAG = "GameSurfaceView";


	public GameSurfaceView(Context context,
			final Bitmap dogBitmap, final Bitmap foxBitmap,
			final Bitmap sheepBitmap, final int NUM_FOX, final int NUM_SHEEP,
			final int DOG_SPEED, final int FOX_SPEED, final int SHEEP_SPEED) {
		super(context);
		this.dogBitmap = dogBitmap;
		this.foxBitmap = foxBitmap;
		this.sheepBitmap = sheepBitmap;
		this.NUM_FOX = NUM_FOX;
		this.NUM_SHEEP = NUM_SHEEP;
		this.DOG_SPEED = DOG_SPEED;
		this.FOX_SPEED = FOX_SPEED;
		this.SHEEP_SPEED = SHEEP_SPEED;

		surfaceHolder = getHolder();
		surfaceHolder.addCallback(this);
	}

	/*
	 * This onSizeChanged() method gets called automatically BEFORE the view
	 * gets laid out or drawn for the first time. It also gets called when the
	 * view's orientation changes or gets resized etc. Records the width and
	 * height of the view into a Boundaries object.
	 */
	@Override
	protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld) {
		super.onSizeChanged(xNew, yNew, xOld, yOld);
		surfaceBoundaries = new Boundaries(xNew, yNew);
		surfaceBoundaries.printBoundaries();
	}

	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}
	
	private synchronized void locksDog(boolean value) {
		this.dogMovementLock = value;
	}
	private synchronized boolean isDogLocked() {
		return this.dogMovementLock;
	}
	private synchronized void stopLongPress() {
		this.isLongPressing = false;
	}
	private synchronized void longPress(int x, int y) {
		this.isLongPressing = true;
		this.dogDirectionX = x;
		this.dogDirectionY = y;
	}
	private synchronized boolean isLongPressing() {
		return this.isLongPressing;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent me) {
		int action = me.getAction();
		// Get the action event that happened and the location that was
		// pressed on the screen, then move the dog there
		if (action == MotionEvent.ACTION_DOWN) {
			if(! isDogLocked() ) {
				this.locksDog(true);
				this.longPress((int) me.getX(), (int) me.getY());
			}
		} else if (action == MotionEvent.ACTION_UP
				|| action == MotionEvent.ACTION_CANCEL) {
			this.stopLongPress();
		}
		return true;
		
	}

	/*
	 * Initializes the game thread and the surface properties when the surface
	 * is created
	 */
	public void surfaceCreated(SurfaceHolder arg0) {

		Thread initializeObjects = new Thread(new Runnable() {

			@Override
			public void run() {
				
				int loc_x, loc_y;

				// TODO
				// initialize dog, fox, and sheep objects and get some info from
				// the sharedPreferences (possibly to be passed by parameter
				// here)
				// we need to make sure it gets created
				dog = (Dog) AnimalFactory.createAnimal(AnimalType.DOG, 0, 0,
						DOG_SPEED, dogBitmap.getWidth(), dogBitmap.getHeight(),
						surfaceBoundaries);

				sheepList = new ArrayList<Sheep>(NUM_SHEEP);
				for (int i = 0; i < NUM_SHEEP; i++) {
					loc_x = locationGenerator.nextInt(surfaceBoundaries
							.getScreenWidth());
					loc_y = locationGenerator.nextInt(surfaceBoundaries
							.getScreenHeight());

					sheepList.add((Sheep) AnimalFactory.createAnimal(
							AnimalType.SHEEP, loc_x, loc_y, SHEEP_SPEED,
							sheepBitmap.getWidth(), sheepBitmap.getHeight(),
							surfaceBoundaries));
				}

				foxList = new ArrayList<Fox>(NUM_FOX);
				Position foxPos;
				for (int i = 0; i < NUM_FOX; i++) {
					
					// TODO create logic to start fox anywhere outside
					// range of screen
					foxPos = spawnFox(locationGenerator, Fox.NUM_EDGES,
							Fox.OFF_SCREEN_FOX_RANGE, Fox.FOX_STARTING_POINT);
					
					foxList.add((Fox) AnimalFactory.createAnimal(
							AnimalType.FOX, foxPos.getX(), foxPos.getY(), FOX_SPEED,
							foxBitmap.getWidth(), foxBitmap.getHeight(),
							surfaceBoundaries));
				}

			}

			
		});

		initializeObjects.start();

		// Background should only be drawn green once
		canvas = surfaceHolder.lockCanvas();
		canvas.drawColor(Color.GREEN);
		surfaceHolder.unlockCanvasAndPost(canvas);



		// Starts the game thread
		gameThread = new Thread(new Runnable() {
			public void run() {

				while (running) {

					canvas = surfaceHolder.lockCanvas();
					// reset canvas so that objects can be redrawn. Otherwise,
					// they get duplicated when they move
					canvas.drawColor(Color.GREEN);

					// TODO: check screen boundaries. Sheep and dog shouldn't be
					// allowed to go out of screen
					moveDog();
					moveSheep();
					moveFox();
					surfaceHolder.unlockCanvasAndPost(canvas);

					// Control the frame rate of the game
					// TODO we might want to update a clock on the top here
					try {
						Thread.sleep(GAME_SPEED);
					} catch (InterruptedException e) {
						Log.e(LOG_TAG, "Game loop was interrupted");
					}
				}

				gameThread.interrupt();
			}

			private void moveDog() {
				if(isDogLocked() ) {
					locksDog(false);
//				} else { 
					//no new movement was recorded - nothing to be changed unless it's long pressing 
//				}
				}
				
				if(isLongPressing()) {
					dog.moveTo(dogDirectionX, dogDirectionY);
				}
				Position dogPosition = dog.getPosition();
				canvas.drawBitmap(dogBitmap, dogPosition.getX(),
						dogPosition.getY(), null);
			}

			private void moveFox() {
				Position foxPosition;
				boolean wasEating = false;
				for (Fox fox : foxList) {
					if( fox.isVisible() ) {
						wasEating = fox.isEating();
						fox.move(dog, sheepList);
						
						if( fox.isVisible()) { //still visible, still in the game
							foxPosition = fox.getPosition();
							canvas.drawBitmap(foxBitmap, foxPosition.getX(), foxPosition.getY(), null);
							if(!wasEating && fox.isEating()) {
								//TODO take points out
								Log.e(LOG_TAG, "Fox finished eating");
							}
						} else {
							//TODO possibly count points here. You got the fox!
							Log.e(LOG_TAG, "You got the fox");
						}
					} else {
//						Log.e(LOG_TAG, "Fox is not visible, check if it can respawn");
						if(fox.canRespawn()) {
							Log.e(LOG_TAG, "Fox is ready to respawn");
							Position foxNewPos = spawnFox(locationGenerator, Fox.NUM_EDGES,
									Fox.OFF_SCREEN_FOX_RANGE, Fox.FOX_STARTING_POINT);
							fox.spawn(foxNewPos);
							canvas.drawBitmap(foxBitmap, foxNewPos.getX(), foxNewPos.getY(), null);
							Log.e(LOG_TAG, "Fox is now visible, position: "+ foxNewPos);
						} else {
							Log.e(LOG_TAG, "Fox is not visible and not ready to respawn");
						}
					}
				}
			}

			private void moveSheep() {
				Position sheepPosition;
				if (sheepList != null) {
					if( sheepList.isEmpty()) {
						//GAME IS OVER! 
						//TODO create interstitial screen with GAME OVER message, with option to restart
						//TODO show score
						running = false;
					} else {
						for (Sheep sheep : sheepList) {
							if (!sheep.isBeingEaten()) {
								sheep.move(foxList, dog);
								sheepPosition = sheep.getPosition();
								canvas.drawBitmap(sheepBitmap,
										sheepPosition.getX(), sheepPosition.getY(),
										null);
							}
						}
					}
				}
			}
		});
		
		try {
			initializeObjects.join(); //ready to start when initializatino is done
		} catch (Exception e) {
			// TODO : Handle if there is an error loading all materials.
			Log.e(LOG_TAG, "Uncaught exception in loading all materials.");
		}
		gameThread.start();
	}

	private Position spawnFox(Random locationGenerator,
			final int NUM_EDGES, int OFF_SCREEN_FOX_RANGE,
			final int FOX_STARTING_POINT) {
		int loc_x;
		int loc_y;
		int location = locationGenerator.nextInt(NUM_EDGES);

		if (location == 0) {
			// constrained by x, y can be negative
			loc_x = locationGenerator.nextInt(surfaceBoundaries
					.getScreenWidth());
			loc_y = FOX_STARTING_POINT;
		} else if (location == 1) {
			// constrained by x, y can be larger than screen
			// height
			loc_x = locationGenerator.nextInt(surfaceBoundaries
					.getScreenWidth());
			loc_y = surfaceBoundaries.getScreenHeight()
					+ locationGenerator
							.nextInt(OFF_SCREEN_FOX_RANGE);
		} else if (location == 2) {
			// constrained by y, x can be negative
			loc_x = FOX_STARTING_POINT;
			loc_y = locationGenerator.nextInt(surfaceBoundaries
					.getScreenHeight());
		} else {
			// constrained by y, x can be greater than
			// screen height
			loc_x = surfaceBoundaries.getScreenWidth()
					+ locationGenerator
							.nextInt(OFF_SCREEN_FOX_RANGE);
			loc_y = locationGenerator.nextInt(surfaceBoundaries
					.getScreenHeight());
		}

		return new Position(loc_x, loc_y);
	}
	
	/*
	 * Stop all threads and remove all handlers when the surface is destroyed
	 * 
	 * @see android.view.SurfaceHolder.Callback#surfaceDestroyed(android.view.
	 * SurfaceHolder)
	 */
	public void surfaceDestroyed(SurfaceHolder arg0) {
		if (gameThread != null) {
			gameThread.interrupt();
		}
	}

	public boolean getRunning() {
		return running;
	}

	/**
	 * Tells the game thread to stop
	 */
	public void stop() {
		running = false;
	}

	/**
	 * Tells the game thread to start again
	 */
	public void restart() {
		running = true;
	}
}