package edu.lehman.android.views;

import java.util.ArrayList;
import java.util.List;

import edu.lehman.android.domain.Dog;
import edu.lehman.android.domain.Fox;
import edu.lehman.android.domain.Position;
import edu.lehman.android.domain.Sheep;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

/**
 * Game Surface View responsible for drawing animals on screen as fast as possible smoothly
 * @author marcus.silveira
 *
 */
@SuppressLint("ViewConstructor")
public class GameSurfaceView extends SurfaceView implements Callback {

	private Bitmap dogBitmap;
	private Bitmap foxBitmap;
	private Bitmap sheepBitmap;
	
	private SurfaceHolder surfaceHolder;
	private Thread gameThread;
	
	private Dog dog;
	private Fox fox;
	private List<Sheep> sheepList;
	private Canvas canvas;
	private boolean running = true;
	
	// Approximates 1/60 of a second. The game runs at 60 FPS
	private final int GAME_SPEED = 17;
	
	public GameSurfaceView(Context context, final Bitmap dogBitmap, final Bitmap foxBitmap, final Bitmap sheepBitmap) {
		super(context);
		this.dogBitmap = dogBitmap;
		this.foxBitmap = foxBitmap;
		this.sheepBitmap = sheepBitmap;
		
		Thread initializeObjects = new Thread(new Runnable(){

			@Override
			public void run() {
				//TODO
				// initialize dog, fox, and sheep objects and get some info from the sharedPreferences (possibly to be passed by parameter here)
				// we need to make sure it gets created 
				dog = new Dog(0, 0, 5, dogBitmap.getWidth(), dogBitmap.getHeight());
				fox = new Fox(300, 200, 5, foxBitmap.getWidth(), foxBitmap.getHeight());
				sheepList = new ArrayList<Sheep>(5);
				sheepList.add(new Sheep(300, 400, 5, sheepBitmap.getWidth(), sheepBitmap.getHeight()));
				sheepList.add(new Sheep(360, 580, 5, sheepBitmap.getWidth(), sheepBitmap.getHeight()));
				sheepList.add(new Sheep(400, 280, 5, sheepBitmap.getWidth(), sheepBitmap.getHeight()));
				sheepList.add(new Sheep(400, 400, 5, sheepBitmap.getWidth(), sheepBitmap.getHeight()));
				sheepList.add(new Sheep(200, 140, 5, sheepBitmap.getWidth(), sheepBitmap.getHeight()));
				
				
				surfaceHolder = getHolder();
			}
			
		});
		
		initializeObjects.start();
		
		try {
			initializeObjects.join();
		} catch (Exception e){
			// TODO : Handle if there is an error loading all materials. 
		}
		
		surfaceHolder.addCallback(this);
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public boolean onTouchEvent(MotionEvent me) {		
		int action = me.getAction();

		// Get the action event that happened and the location that was
		// pressed on the screen, then move the dog there
		if (action == MotionEvent.ACTION_DOWN
				|| action == MotionEvent.ACTION_UP 
				|| action == MotionEvent.ACTION_MOVE){
			
			dog.moveTo( (int) me.getX(), (int) me.getY() );
			
			return true;
		} else {
			// There was no touch event
			return false;
		}
	}

	/*
	 * Initializes the game thread and the surface properties when
	 * the surface is created
	 */
	@Override
	public void surfaceCreated(SurfaceHolder arg0) {

		// Background should only be drawn green once
		canvas = surfaceHolder.lockCanvas();
		canvas.drawColor(Color.GREEN);
		surfaceHolder.unlockCanvasAndPost(canvas);
		
		// Starts the game thread
		gameThread = new Thread(new Runnable() {
			public void run() {
				while( running ) {
					canvas = surfaceHolder.lockCanvas();
					// reset canvas so that objects can be redrawn. Otherwise,
					// they get duplicated when they move
					canvas.drawColor(Color.GREEN);

					// TODO: check screen boundaries. Sheep and dog shouldn't be
					// allowed to go out of screen
					moveDog(canvas);
					moveSheep(canvas);
					moveFox(canvas);
					surfaceHolder.unlockCanvasAndPost(canvas);

					// Control the frame rate of the game
					// TODO we might want to update a clock on the top here
					try{
						Thread.sleep(GAME_SPEED);				
					} catch(InterruptedException e) {
						//ignore it
					}
				}
				
				gameThread.interrupt();
			}
			

			private void moveDog(Canvas canvas) {
				Position dogPosition;
				//dog position changes according to the touch listener
				dogPosition = dog.getPosition();
				canvas.drawBitmap(dogBitmap, dogPosition.getX(), dogPosition.getY(), null);
			}

			private void moveFox(Canvas canvas) {
				Position foxPosition;
				fox.move(dog, sheepList);
				foxPosition = fox.getPosition();
				canvas.drawBitmap(foxBitmap, foxPosition.getX(), foxPosition.getY(), null);
			}

			private void moveSheep(Canvas canvas) {
				Position sheepPosition;
				if( sheepList != null) {
					for(Sheep sheep : sheepList) {
						if( !sheep.isBeingEaten()) {
							sheep.move(fox, dog);
							sheepPosition = sheep.getPosition();
							canvas.drawBitmap(sheepBitmap, sheepPosition.getX(), sheepPosition.getY(), null);	
						}
					}
				}
			}
		});
		
		gameThread.start();
	}
   

//	   public List<Sheep> createSheep(int count) {
//		   List<Sheep> sheepList = new ArrayList<Sheep>(count);
//
//	      int columns = (int)Math.ceil(Math.sqrt(count));
//
//	      for(int i = 0; i < count; i++) {
//	         sheepList.add(new Sheep(500 - columns*6 + 12*(i%columns), 350 - 6*columns + 12*(i/columns)));
//	      }
//
//	      return sheepList;
//	   }
	
	/*
	 * Stop all threads and remove all handlers when the surface is destroyed
	 * 
	 * @see android.view.SurfaceHolder.Callback#surfaceDestroyed(android.view.SurfaceHolder)
	 */
	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		if( gameThread != null) {
			gameThread.interrupt();
		}
	}
	
	public boolean getRunning(){
		return running;
	}
	
	/**
	 * Tells the game thread to stop
	 */
	public void stop(){ running = false; }
	
	/**
	 * Tells the game thread to start again 
	 */
	public void restart() { running = true; }
}
