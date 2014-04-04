package edu.lehman.android;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * MainActivity initializes the main page with options to control settings or
 * start a new game (both new activities)
 * 
 * @author Marcos Davila, Marcus Silveira
 * @revisionhistory 3/01/2014 - Adding logs to each lifecycle state for later
 *                  use on integration tests 2/11/2014 - Creating main layout
 *                  and navigation. Setting up button listeners (settings, start
 *                  game, and quit) 2/4/2014 - OnClickListener implemented so
 *                  this activity listens for taps 2/1/2014 - Project Created
 * 
 */
public class MainActivity extends Activity {

	private static final String LOG_TAG = "MainActivity";
	private Button settings, startGame, quitButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Draw the screen
		settings = (Button) findViewById(R.id.settingsButton);
		startGame = (Button) findViewById(R.id.startButton);
		quitButton = (Button) findViewById(R.id.quitButton);

		Log.i(LOG_TAG, "MainActivity.onCreate()");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Log.i(LOG_TAG, "MainActivity.onRestart()");
	}

	@Override
	protected void onStart() {
		super.onStart();
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
		Log.i(LOG_TAG, "MainActivity.onStart()");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.i(LOG_TAG, "MainActivity.onResume()");
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.i(LOG_TAG, "MainActivity.onPause()");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.i(LOG_TAG, "MainActivity.onStop()");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.i(LOG_TAG, "MainActivity.onDestroy()");
	}

}
