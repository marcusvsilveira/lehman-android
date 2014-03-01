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
 * @revisionhistory 3/01/2014 - Adding logs to each lifecycle state for later use on integration tests
 * 					2/11/2014 - Creating main layout and navigation. Setting up
 *                  button listeners (settings, start game, and quit) 2/4/2014 -
 *                  OnClickListener implemented so this activity listens for
 *                  taps 
 *                  2/1/2014 - Project Created
 * 
 */
public class MainActivity extends Activity implements SettingsInterface,
		Runnable {

	private static final String LOG_TAG = "MainActivity";

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

		Log.i(LOG_TAG, "onCreate()");
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
		
		Log.i(LOG_TAG, "loadPreferences()");
	}

	@Override
	public void storePreferences() {
		// Do nothing
		Log.i(LOG_TAG, "storePreferences()");
	}

	@Override
	public void run() {
		// Sets up the default preferences in a new thread
		loadPreferences();
		
		Log.i(LOG_TAG, "run()");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Log.i(LOG_TAG, "onRestart()");
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.i(LOG_TAG, "onStart()");
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		Log.i(LOG_TAG, "onResume()");
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.i(LOG_TAG, "onPause()");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.i(LOG_TAG, "onStop()");
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.i(LOG_TAG, "onDestroy()");
	}

}
