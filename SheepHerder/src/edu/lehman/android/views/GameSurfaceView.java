package edu.lehman.android.views;

import interfaces.Orientable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.lehman.android.R;
import edu.lehman.android.SheepHerderActivity;
import edu.lehman.android.domain.Dog;
import edu.lehman.android.domain.Fox;
import edu.lehman.android.domain.Position;
import edu.lehman.android.domain.Sheep;
import edu.lehman.android.factory.AnimalFactory;
import edu.lehman.android.factory.AnimalType;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
public class GameSurfaceView extends SurfaceView implements Callback, Runnable,
		Orientable {
	public static final int POINTS_LOSE_SHEEP = 10;
	public static final int POINTS_START_LOSING_SHEEP = 5;
	public static final int POINTS_FOX_RUN_AWAY = 5;
	public static final int POINTS_CATCH_FOX = 10;
	// starting with a higher number so that if you don't play and fox starts
	// eating sheep, the score doesn't go negative
	public static final int POINTS_AT_START = 500;

	// Approximates 1/60 of a second. The game runs at 60 FPS
	private static int GAME_SPEED = 17;
	private static final String LOG_TAG = "GameSurfaceView";

	private Bitmap dogBitmap;
	private Bitmap foxBitmap;
	private Bitmap sheepBitmap;
	private Bitmap sheepBeingEatenBitmap;

	private SurfaceHolder surfaceHolder;
	private Thread gameThread;

	private int NUM_FOX, NUM_SHEEP, DOG_SPEED, FOX_SPEED, SHEEP_SPEED;

	private Dog dog;
	private List<Fox> foxList;
	private List<Sheep> sheepList;
	private Canvas canvas;
	private static boolean running = true;
	private Boundaries surfaceBoundaries;
	private static final Random locationGenerator = new Random();

	private boolean dogMovementLock = false;
	private boolean isLongPressing = false;
	private int dogDirectionY;
	private int dogDirectionX;

	/**
	 * Sets the number of foxes, dogs, and sheeps and also initializes holders
	 * and callbacks for the surface
	 * 
	 * @param context
	 *            application context
	 * @param NUM_FOX
	 *            number of foxes to be created
	 * @param NUM_SHEEP
	 *            number of sheeps to be created
	 * @param DOG_SPEED
	 *            how fast the dog should move
	 * @param FOX_SPEED
	 *            how fast foxes should move
	 * @param SHEEP_SPEED
	 *            how fast sheep should move
	 */
	public GameSurfaceView(Context context, final int NUM_FOX,
			final int NUM_SHEEP, final int DOG_SPEED, final int FOX_SPEED,
			final int SHEEP_SPEED) {
		super(context);
		this.NUM_FOX = NUM_FOX;
		this.NUM_SHEEP = NUM_SHEEP;
		this.DOG_SPEED = DOG_SPEED;
		this.FOX_SPEED = FOX_SPEED;
		this.SHEEP_SPEED = SHEEP_SPEED;

		SheepHerderActivity.score = POINTS_AT_START;
		surfaceHolder = getHolder();
		surfaceHolder.addCallback(this);
	}

	/**
	 * Starts the game thread. The game thread runs continuously in a loop until
	 * the game is over or until it is interrupted. The game thread continuously
	 * updates the position of all the animals on the screen every 1/60th of a
	 * second.
	 */
	@Override
	public void run() {
		while (running) {
			canvas = surfaceHolder.lockCanvas();
			// reset canvas so that objects can be redrawn. Otherwise,
			// they get duplicated when they move
			canvas.drawColor(Color.GREEN);

			moveDog();
			moveSheep();
			moveFox();
			surfaceHolder.unlockCanvasAndPost(canvas);

			// Control the frame rate of the game
			try {
				Thread.sleep(GAME_SPEED);
			} catch (InterruptedException e) {
				Log.e(LOG_TAG, "Game loop was interrupted");
			}
		}
	}

	/*
	 * Helper method that details how a dog should be moved according to a long
	 * press. The dog is locked so that only one press event is recorded at a
	 * time.
	 */
	private void moveDog() {
		if (isDogLocked()) {
			locksDog(false);
		}

		if (isLongPressing()) {
			dog.moveTo(dogDirectionX, dogDirectionY);
		}

		Position dogPosition = dog.getPosition();
		// removes half of width and height because the user touched where the
		// center of the dog should be
		canvas.drawBitmap(dogBitmap, dogPosition.getX() - dogBitmap.getWidth()
				/ 2, dogPosition.getY() - dogBitmap.getHeight() / 2, null);
	}

	/*
	 * Helper method that details how a fox should be moved according to logic.
	 * A fox can be in two states: visible or not visible. An invisible fox
	 * cannot be interacted with. If the fox is visible, it continuously checks
	 * to see if the dog is nearby and if sheep are nearby. The fox runs if the
	 * dog is too close and the fox eats a sheep if it can catch one.
	 * 
	 * If the fox successfully eats a sheep, he continues to try to eat another
	 * one. The player loses a point if the fox succeeds at his goal. If the fox
	 * is caught, it disappears and the player gets a point.
	 */
	private void moveFox() {
		Position foxPosition;
		boolean wasEating = false;
		for (Fox fox : foxList) {
			if (fox.isVisible()) {
				wasEating = fox.isEating();
				fox.move(dog, sheepList);

				if (fox.isVisible()) { // still visible, still in the
										// game
					foxPosition = fox.getPosition();
					canvas.drawBitmap(foxBitmap,
							foxPosition.getX() - foxBitmap.getWidth() / 2,
							foxPosition.getY() - foxBitmap.getHeight() / 2,
							null);
					if (!wasEating && fox.isEating()) {
						Log.e(LOG_TAG, "Fox started to eat");
						SheepHerderActivity.score -= POINTS_START_LOSING_SHEEP;
					} else if (wasEating && !fox.isEating()) {
						Log.e(LOG_TAG, "Fox finished eating");
						SheepHerderActivity.score -= POINTS_LOSE_SHEEP;
					}
				} else {
					if (fox.hasRunAway()) {
						// TODO -> possibly give move time for the game as a
						// bonus!!
						// increase fox speed (more difficult) or add more foxes
						// - just don't go over the speed limit :-)
						Log.e(LOG_TAG, "You scared the fox away");
						SheepHerderActivity.score += POINTS_FOX_RUN_AWAY;
					} else {
						// TODO -> possibly give move time for the game as a
						// bonus!!
						// increase fox speed (more difficult) or add more foxes
						// - just don't go over the speed limit :-)
						Log.e(LOG_TAG, "You got the fox");
						SheepHerderActivity.score += POINTS_CATCH_FOX;
					}
				}
			} else {
				// Log.e(LOG_TAG,
				// "Fox is not visible, check if it can respawn");
				if (fox.canRespawn()) {
					Log.e(LOG_TAG, "Fox is ready to respawn");
					Position foxNewPos = spawnFox(locationGenerator,
							Fox.NUM_EDGES, Fox.OFF_SCREEN_FOX_RANGE,
							Fox.FOX_STARTING_POINT);
					fox.spawn(foxNewPos);
					canvas.drawBitmap(foxBitmap,
							foxNewPos.getX() - foxBitmap.getWidth() / 2,
							foxNewPos.getY() - foxBitmap.getHeight() / 2, null);
					Log.e(LOG_TAG, "Fox is now visible, position: " + foxNewPos);
				} else {
					Log.e(LOG_TAG,
							"Fox is not visible and not ready to respawn");
				}
			}
		}
	}

	/*
	 * Helper method that details how a sheep should move according to logic.
	 * Sheeps are fine with grazing until a dog or a fox gets too close to them,
	 * in which case they run.
	 */
	private void moveSheep() {
		Position sheepPosition;
		if (sheepList != null) {
			if (sheepList.isEmpty()) {
				// GAME IS OVER!
				Log.i(LOG_TAG, "No more sheep available - all gone!");
				running = false;
			} else {
				Bitmap currentSheepBitmap;
				for (Sheep sheep : sheepList) {
					if (!sheep.isBeingEaten()) {
						sheep.move(foxList, dog);
						currentSheepBitmap = sheepBitmap;
					} else {
						currentSheepBitmap = sheepBeingEatenBitmap;
					}
					sheepPosition = sheep.getPosition();
					canvas.drawBitmap(
							currentSheepBitmap,
							sheepPosition.getX()
									- currentSheepBitmap.getWidth() / 2,
							sheepPosition.getY()
									- currentSheepBitmap.getHeight() / 2, null);
				}
			}
		}
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
		// surfaceBoundaries.printBoundaries();
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

	/*
	 * Locks access to the dog instance so that only one touch event corresponds
	 * to movement of the dog to a specific location on the screen.
	 */
	private synchronized void locksDog(boolean value) {
		this.dogMovementLock = value;
	}

	/*
	 * Check to see if the dog attribute is locked
	 */
	private synchronized boolean isDogLocked() {
		return this.dogMovementLock;
	}

	/*
	 * Stops the view from recognizing a long press touch event
	 */
	private synchronized void stopLongPress() {
		this.isLongPressing = false;
	}

	/*
	 * Allows the view to recognize a long press event at a given (x,y)
	 * coordinate on the screen
	 */
	private synchronized void longPress(int x, int y) {
		this.isLongPressing = true;
		this.dogDirectionX = x;
		this.dogDirectionY = y;
	}

	/*
	 * Returns whether the view is currently processing a long press touch event
	 */
	private synchronized boolean isLongPressing() {
		return this.isLongPressing;
	}

	/**
	 * Processes touch events on the screen
	 */
	@Override
	public boolean onTouchEvent(MotionEvent me) {
		int action = me.getAction();
		// Get the action event that happened and the location that was
		// pressed on the screen, then move the dog there
		if (action == MotionEvent.ACTION_DOWN) {
			if (!isDogLocked()) {
				this.locksDog(true);
				this.longPress((int) me.getX(), (int) me.getY());
			}
		} else if (action == MotionEvent.ACTION_UP
				|| action == MotionEvent.ACTION_CANCEL) {
			this.stopLongPress();
		}

		return true;
	}

	/**
	 * Initializes the game thread and the surface properties
	 */
	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		// Run this thread in the background
		dogBitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.gamedog);
		foxBitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.gamefox);
		sheepBitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.gamesheep);
		sheepBeingEatenBitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.gamesheepeaten);

		int loc_x, loc_y;

		// Initialize dog, fox, and sheep objects and create as many of
		// these objects as is specified in the shared preferences
		dog = (Dog) AnimalFactory.createAnimal(AnimalType.DOG,
				dogBitmap.getWidth(), dogBitmap.getHeight(), DOG_SPEED,
				dogBitmap.getWidth(), dogBitmap.getHeight(), surfaceBoundaries);

		sheepList = new ArrayList<Sheep>(NUM_SHEEP);

		for (int i = 0; i < NUM_SHEEP; i++) {
			loc_x = locationGenerator.nextInt(surfaceBoundaries.WIDTH
					- sheepBitmap.getWidth());
			loc_y = locationGenerator.nextInt(surfaceBoundaries.HEIGHT
					- sheepBitmap.getHeight());

			sheepList.add((Sheep) AnimalFactory.createAnimal(AnimalType.SHEEP,
					loc_x, loc_y, SHEEP_SPEED, sheepBitmap.getWidth(),
					sheepBitmap.getHeight(), surfaceBoundaries));
		}

		foxList = new ArrayList<Fox>(NUM_FOX);
		Position foxPos;
		for (int i = 0; i < NUM_FOX; i++) {

			foxPos = spawnFox(locationGenerator, Fox.NUM_EDGES,
					Fox.OFF_SCREEN_FOX_RANGE, Fox.FOX_STARTING_POINT);

			foxList.add((Fox) AnimalFactory.createAnimal(AnimalType.FOX,
					foxPos.getX(), foxPos.getY(), FOX_SPEED,
					foxBitmap.getWidth(), foxBitmap.getHeight(),
					surfaceBoundaries));
		}
	}

	/*
	 * Sets all foxes starting locations to one of the four outside boundaries
	 * of the screen.
	 */
	private Position spawnFox(Random locationGenerator, final int NUM_EDGES,
			int OFF_SCREEN_FOX_RANGE, final int FOX_STARTING_POINT) {
		int loc_x;
		int loc_y;
		int location = locationGenerator.nextInt(NUM_EDGES);

		if (location == UP) {
			// constrained by x, y can be negative
			loc_x = locationGenerator.nextInt(surfaceBoundaries.WIDTH);
			loc_y = FOX_STARTING_POINT;
		} else if (location == DOWN) {
			// constrained by x, y can be larger than screen
			// height
			loc_x = locationGenerator.nextInt(surfaceBoundaries.WIDTH);
			loc_y = surfaceBoundaries.HEIGHT
					+ locationGenerator.nextInt(OFF_SCREEN_FOX_RANGE);
		} else if (location == LEFT) {
			// constrained by y, x can be negative
			loc_x = FOX_STARTING_POINT;
			loc_y = locationGenerator.nextInt(surfaceBoundaries.HEIGHT);
		} else {
			// constrained by y, x can be greater than
			// screen height
			loc_x = surfaceBoundaries.WIDTH
					+ locationGenerator.nextInt(OFF_SCREEN_FOX_RANGE);
			loc_y = locationGenerator.nextInt(surfaceBoundaries.HEIGHT);
		}

		return new Position(loc_x, loc_y);
	}

	public boolean isRunning() {
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

	/**
	 * Kill the game loop and join all running threads before the surface is
	 * destroyed.
	 */
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		running = false;

		try {
			// Wait for the game thread to finish up on resources
			if (gameThread != null) {
				gameThread.join();
			}
		} catch (InterruptedException e) {
			// ignore because the game is ending
		}
	}

	public void start() {
		running = true;
		gameThread = new Thread(this);
		gameThread.start();
	}

	/**
	 * A class to hold the width and height of the phone's touchscreen. This is
	 * used in determining the location of the animals on the screen so the
	 * animals know not to move to a location where they are not completely
	 * visible on the screen. An instance of this class is passed into all
	 * animal objects when they are created.
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
}
