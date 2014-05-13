package edu.lehman.android;

import com.google.android.gms.ads.AdView;

import edu.lehman.android.interfaces.Settings;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * MainActivity initializes the main page with options to control settings or
 * start a new game (both new activities)
 * 
 * @author Marcos Davila, Marcus Silveira
 */
public class MainActivity extends Activity {

	private static final String LOG_TAG = "MainActivity";
	private Button settings, startGame, quitButton;
	private ViewGroup topLevelLayout;
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
		topLevelLayout = (ViewGroup) findViewById(R.id.topLevelLayout);
		if(Settings.DEBUG_MODE) {
			Log.i(LOG_TAG, "MainActivity.onCreate()");
		}
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
		if(Settings.DEBUG_MODE) {
			Log.i(LOG_TAG, "MainActivity.onStart()");
		}
	}

	/*
	 * Unused callbacks
	 */

	@Override
	protected void onRestart() {
		super.onRestart();
		if(Settings.DEBUG_MODE) {
			Log.i(LOG_TAG, "MainActivity.onRestart()");
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if(adsEnabled) {
			mAdView.resume();
		}
		if(Settings.DEBUG_MODE) {
			Log.i(LOG_TAG, "MainActivity.onResume()");
		}
	}

	@Override
	protected void onPause() {
		if(adsEnabled) {
			mAdView.pause();
		}
		super.onPause();
		if(Settings.DEBUG_MODE) {
			Log.i(LOG_TAG, "MainActivity.onPause()");
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		if(Settings.DEBUG_MODE) {
			Log.i(LOG_TAG, "MainActivity.onStop()");
		}
	}

	@Override
	protected void onDestroy() {
		if(adsEnabled) {
			mAdView.destroy();
		}
		super.onDestroy();
		if(Settings.DEBUG_MODE) {
			Log.i(LOG_TAG, "MainActivity.onDestroy()");
		}
	}

}
