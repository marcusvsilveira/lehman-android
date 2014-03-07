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
	private List<Sheep> sheep;
	
	
	public GameSurfaceView(Context context, Bitmap dogBitmap, Bitmap foxBitmap, Bitmap sheepBitmap) {
		super(context);
		this.dogBitmap = dogBitmap;
		this.foxBitmap = foxBitmap;
		this.sheepBitmap = sheepBitmap;
		
		//TODO
		//initialize dog, fox, and sheep objects and get some info from the sharedPreferences (possibly to be passed by parameter here)
		// we need to make sure it gets created 
		dog = new Dog(0, 0, 5, dogBitmap.getWidth(), dogBitmap.getHeight());
		fox = new Fox(300, 200, 5, foxBitmap.getWidth(), foxBitmap.getHeight());
		sheep = new ArrayList<Sheep>(5);
		sheep.add(new Sheep(300, 400, 5, sheepBitmap.getWidth(), sheepBitmap.getHeight()));
		sheep.add(new Sheep(360, 580, 5, sheepBitmap.getWidth(), sheepBitmap.getHeight()));
		sheep.add(new Sheep(400, 280, 5, sheepBitmap.getWidth(), sheepBitmap.getHeight()));
		sheep.add(new Sheep(400, 400, 5, sheepBitmap.getWidth(), sheepBitmap.getHeight()));
		sheep.add(new Sheep(200, 140, 5, sheepBitmap.getWidth(), sheepBitmap.getHeight()));
		
		
		surfaceHolder = getHolder();
		surfaceHolder.addCallback(this);
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		gameThread = new Thread(new Runnable() {
			public void run() {
				Canvas canvas = null;
				Position foxPosition = null;
				Position dogPosition = null;
				Position sheepPosition = null;
				while(!Thread.currentThread().isInterrupted() /* && condition to stop game */) {
					canvas = surfaceHolder.lockCanvas();
					if( canvas != null) {
						//TODO
						//MAKE game elements MOVE HERE
		
						//dog position changes according to the touch listener
						dogPosition = dog.getPosition();
						canvas.drawBitmap(dogBitmap, dogPosition.getX(), dogPosition.getY(), null);
						
						if( sheep != null) {
							for(Sheep oneSheep : sheep) {
								sheepPosition = oneSheep.move();
								canvas.drawBitmap(sheepBitmap, sheepPosition.getX(), sheepPosition.getY(), null);
							}
						}
						
						foxPosition = fox.move();
						canvas.drawBitmap(foxBitmap, foxPosition.getX(), foxPosition.getY(), null);
						
						surfaceHolder.unlockCanvasAndPost(canvas);
						
						//TODO we might want to update a clock on the top here
					}
					try{
						Thread.sleep(1000);
						
					} catch(InterruptedException e) {
						//ignore it
					}
				}
				
			}
		});
		
		gameThread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		if( gameThread != null) {
			gameThread.interrupt();
		}
	}
}
