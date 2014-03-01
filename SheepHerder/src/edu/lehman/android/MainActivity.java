package edu.lehman.android;

import interfaces.SettingsInterface;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * MainActivity initializes the main page with options to control settings or
 * start a new game (both new activities)
 * 
 * @author Marcos Davila, Marcus Silveira
 * @revisionhistory 2/11/2014 - Creating main layout and navigation. Setting up
 *                  button listeners (settings, start game, and quit) 2/4/2014 -
 *                  OnClickListener implemented so this activity listens for
 *                  taps 2/1/2014 - Project Created
 * 
 */
public class MainActivity extends Activity implements SettingsInterface,
		Runnable {

	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Thread preferencesThread = new Thread(this);
		preferencesThread.start();
		
		// Sets up the view. All changing of the UI must be done in the
		// UI thread
		Button settings = (Button) findViewById(R.id.settingsButton);
		Button startGame = (Button) findViewById(R.id.startButton);
		Button quitButton = (Button) findViewById(R.id.quitButton);

		// setup listeners
		quitButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});

		settings.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent settingsIntent = new Intent(MainActivity.this,
						GameSettingsActivity.class);
				startActivity(settingsIntent);
			}
		});

		startGame.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent startGameIntent = new Intent(MainActivity.this,
						SheepHerderActivity.class);
				startActivity(startGameIntent);
			}
		});
		
		// Wait for all threads to finish
		try {
			preferencesThread.join();
		} catch (InterruptedException e) {
			// Thread was interrupted; print stack trace
			e.printStackTrace();
		}

	}

	@Override
	public void loadPreferences() {
		// Define a default preferences file
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt(DOG_SPEED_PREFS, DEFAULT_DOG_SPEED);
		editor.putInt(NUM_SHEEP_PREFS, DEFAULT_NUM_SHEEPS);
		editor.putInt(NUM_FOXES_PREFS, DEFAULT_NUM_FOXES);
		editor.putInt(SHEEP_SPEED_PREFS, DEFAULT_SHEEP_SPEED);
		editor.putInt(FOX_SPEED_PREFS, DEFAULT_FOX_SPEED);

		// Commit the edits!
		editor.commit();

	}

	@Override
	public void storePreferences() {
		// Do nothing

	}

	@Override
	public void run() {
		// Sets up the default preferences in a new thread
		loadPreferences();
		Log.i("Main", "Preferences loaded!");
	}

}
