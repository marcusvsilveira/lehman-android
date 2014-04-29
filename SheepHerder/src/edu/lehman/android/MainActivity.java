package edu.lehman.android;

import com.google.android.gms.ads.AdView;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * MainActivity initializes the main page with options to control settings or
 * start a new game (both new activities)
 * 
 * @author Marcos Davila, Marcus Silveira
 */
public class MainActivity extends Activity {

	private static final String LOG_TAG = "MainActivity";
	private Button settings, startGame, quitButton;
	private LinearLayout topLevelLayout;
	private AdView mAdView;
	public boolean adsEnabled = true;

	/**
	 * Identify buttons with their ID's when the app is started
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Draw the screen
		settings = (Button) findViewById(R.id.settingsButton);
		startGame = (Button) findViewById(R.id.startButton);
		quitButton = (Button) findViewById(R.id.quitButton);
		topLevelLayout = (LinearLayout) findViewById(R.id.topLevelLayout);
		
		Log.i(LOG_TAG, "MainActivity.onCreate()");
	}

	/**
	 * Register action listeners with the buttons when the app is
	 * started
	 */
	@Override
	protected void onStart() {
		super.onStart();
		
		if(adsEnabled) {
			mAdView = (AdView) findViewById(R.id.adView);
	        AdsHelper.showAds(mAdView, topLevelLayout);
		}
		
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

	/*
	 * Unused callbacks
	 */

	@Override
	protected void onRestart() {
		super.onRestart();
		Log.i(LOG_TAG, "MainActivity.onRestart()");
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if(adsEnabled) {
			mAdView.resume();
		}
		Log.i(LOG_TAG, "MainActivity.onResume()");
	}

	@Override
	protected void onPause() {
		if(adsEnabled) {
			mAdView.pause();
		}
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
		if(adsEnabled) {
			mAdView.destroy();
		}
		super.onDestroy();
		Log.i(LOG_TAG, "MainActivity.onDestroy()");
	}

}
