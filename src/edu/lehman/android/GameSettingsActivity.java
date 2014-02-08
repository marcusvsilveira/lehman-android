package edu.lehman.android;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

/**
 * A pre-game settings menu where user can alter difficulty and other parameters.
 * 
 * @author Marcos Davila
 * @revisionhistory
 * 		2/6/2014 - Back and start buttons now move to the activities
 * 		2/4/2014 - File created
 */
public class GameSettingsActivity extends Activity {

	// Intents for the buttons to point to the class files of the activities
	// that they should launch
	private Intent backIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_settings_panel);
		
		backIntent = new Intent(this, MainActivity.class);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game_settings_panel, menu);
		return true;
	}
	
	/*
	 * When the back button is clicked, launch the activity associated
	 * with the intent to return to the start menu. The onClick listener
	 * is defined in the xml for this activity
	 */
	public void goBack(View view){
		startActivity(backIntent);
	}
	
	

}
