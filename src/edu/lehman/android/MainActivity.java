package edu.lehman.android;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

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

	private Intent startGameIntent;
	private Intent optionsIntent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		startGameIntent = new Intent(this, SheepHerderActivity.class);
		optionsIntent = new Intent(this, GameSettingsActivity.class);
	}
	
	/*
	 * When the start button is clicked, launch the activity associated with
	 * starting the game. The onClicklistener is defined in the xml for this
	 * activity
	 */
	public void startGame(View view) {
		startActivity(startGameIntent);
	}
	
	public void options(View view){
		startActivity(optionsIntent);
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
