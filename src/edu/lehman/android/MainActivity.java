package edu.lehman.android;

import edu.lehman.android.views.BubbleSurfaceView1stSample;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

/**
 * MainActivity initializes a simple activity with a background image
 * that serves as the introduction to the game.
 * 
 * @author Marcos Davila, Marcus Silveira
 * @revisionhistory
 * 		2/4/2014 - OnClickListener implemented so this activity listens
 * 				   for taps
 * 		2/1/2014 - Project Created
 *
 */
public class MainActivity extends Activity {

	// For the actionListener to be able to react to taps
	private MainActivity mainReference = this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		/*
		 * The image initialized by activity_main.xml as the main
		 * screen image is given an anonymous onClickListener, which is an
		 * actionListener that listens for taps on the screen and
		 * then redirects to a new activity.
		 * 
		 * TODO: Change the activity from the BubbleSurfaceView example
		 * that Marcus wrote to the proper screen either to set parameters
		 * before starting the game (or more simply for now, the game itself)
		 * 
		 * TODO: Get a better starting image! 
		 */
		ImageView view = (ImageView) findViewById(R.id.mainImage);
		OnClickListener viewTapped = new OnClickListener() {
		       @Override
		       public void onClick(View v) {
		         // TODO Auto-generated method stub
		    	   setContentView(new BubbleSurfaceView1stSample(mainReference));
		       }
		     };
		     
		view.setOnClickListener(viewTapped);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
