package edu.lehman.android.views;

import java.util.List;

import edu.lehman.android.domain.Dog;
import edu.lehman.android.domain.Fox;
import edu.lehman.android.domain.Sheep;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

/**
 * Game Surface View responsible for drawing animals on screen as fast as possible smoothly
 * @author marcus.silveira
 *
 */
public class GameSurfaceView extends SurfaceView implements Callback {

	private Bitmap dogBitmap;
	private Bitmap foxBitmap;
	private Bitmap sheepBitmap;
	
	private SurfaceHolder surfaceHolder;
	private Thread dogThread;
	private Thread foxThread;
	private Thread sheepThread;
	
	private Dog dog;
	private Fox fox;
	private List<Sheep> sheep;
	
	
	public GameSurfaceView(Context context, Bitmap dog, Bitmap fox, Bitmap sheep) {
		super(context);
		this.dogBitmap = dog;
		this.foxBitmap = fox;
		this.sheepBitmap = sheep;
		
		//TODO
		//initialize dog, fox, and sheep objects and get some info from the sharedPreferences (possibly to be passed by parameter here)
		
		surfaceHolder = getHolder();
		surfaceHolder.addCallback(this);
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		dogThread = new Thread(new Runnable() {
			public void run() {
				Canvas canvas = null;
				while(!Thread.currentThread().isInterrupted() /* && condition to stop game */) {
					canvas = surfaceHolder.lockCanvas();
					if( canvas != null) {
						//TODO
						//MAKE dog MOVE HERE
						canvas.drawBitmap(dogBitmap, 5, 5, null);
						surfaceHolder.unlockCanvasAndPost(canvas);
					}
				}
				try{
					Thread.sleep(1000);
				} catch(InterruptedException e) {
					//ignore it
				}
				
			}
		});

		foxThread = new Thread(new Runnable() {
			public void run() {
				Canvas canvas = null;
				
				while(!Thread.currentThread().isInterrupted() /* && condition to stop game */) {
					canvas = surfaceHolder.lockCanvas();
					if( canvas != null) {
						//TODO
						//MAKE fox MOVE HERE
						canvas.drawBitmap(foxBitmap, 100, 5, null);
						surfaceHolder.unlockCanvasAndPost(canvas);
					}
					try{
						Thread.sleep(1000);
					} catch(InterruptedException e) {
						//ignore it
					}
				}
			}
		});
		
		sheepThread = new Thread(new Runnable() {
			public void run() {
				Canvas canvas = null;
				while(!Thread.currentThread().isInterrupted() /* && condition to stop game */) {
					canvas = surfaceHolder.lockCanvas();
					if( canvas != null) {
						//TODO
						//MAKE sheep MOVE HERE
						canvas.drawBitmap(sheepBitmap, 200, 5, null);
						surfaceHolder.unlockCanvasAndPost(canvas);
					}
				}
				try{
					Thread.sleep(1000);
				} catch(InterruptedException e) {
					//ignore it
				}
			}
		});
		
		dogThread.start();
		foxThread.start();
		sheepThread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		if( foxThread != null) {
			foxThread.interrupt();
		}
		if( dogThread != null) {
			dogThread.interrupt();
		}
		if( sheepThread != null) {
			sheepThread.interrupt();
		}
		
	}
}
