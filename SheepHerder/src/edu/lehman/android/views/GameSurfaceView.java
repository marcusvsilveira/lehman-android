package edu.lehman.android.views;

import android.content.Context;
import android.graphics.Bitmap;
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

	private Bitmap dog;
	private Bitmap fox;
	private Bitmap sheep;
	
	private SurfaceHolder surfaceHolder;
	private Thread dogThread;
	private Thread foxThread;
	private Thread sheepThread;
	
	public GameSurfaceView(Context context, Bitmap dog, Bitmap fox, Bitmap sheep) {
		super(context);
		this.dog = dog;
		this.fox = fox;
		this.sheep = sheep;
		
		surfaceHolder = getHolder();
		surfaceHolder.addCallback(this);
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub

	}

}
