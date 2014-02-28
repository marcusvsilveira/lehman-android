package edu.lehman.android;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * MainActivity initializes the main page with options to control settings 
 * or start a new game (both new activities)
 * 
 * @author Marcos Davila, Marcus Silveira
 * @revisionhistory
 * 		2/11/2014 - Creating main layout and navigation. Setting up button listeners (settings, start game, and quit)
 * 		2/4/2014 - OnClickListener implemented so this activity listens
 * 				   for taps
 * 		2/1/2014 - Project Created
 *
 */
public class MainActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//button handling 
		Button settings = (Button) findViewById(R.id.settingsButton);
		Button startGame = (Button) findViewById(R.id.startButton);
		Button quitButton = (Button) findViewById(R.id.quitButton);
		
		//setup listeners
		quitButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		
		settings.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent settingsIntent = new Intent(MainActivity.this, GameSettingsActivity.class);
				startActivity(settingsIntent);
			}
		});
		
		startGame.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent startGameIntent = new Intent(MainActivity.this, SheepHerderActivity.class);
				startActivity(startGameIntent);
			}
		});
	}

}
