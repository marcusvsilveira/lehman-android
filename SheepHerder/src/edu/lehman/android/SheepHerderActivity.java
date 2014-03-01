package edu.lehman.android;

import interfaces.SettingsInterface;
import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 
 * This is the Game screen
 * @author Marcos Davila, Marcus Silveira
 *
 */
public class SheepHerderActivity extends Activity implements SettingsInterface {
	private static final String LOG_TAG = "SheepHerderActivity";
	
	private int DOG_SPEED;
	private int NUM_SHEEP;
	private int NUM_FOXES;
	private int SHEEP_SPEED;
	private int FOX_SPEED;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sheep_herder);

		//setup back button listener
		Button backButton = (Button) findViewById(R.id.backButton);
		backButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish(); //finishes game and free up resources
			}
		});
		
		loadPreferences();
		
		Log.i(LOG_TAG, "Dog speed: " + DOG_SPEED);
		Log.i(LOG_TAG, "# Sheep: " + NUM_SHEEP);
		Log.i(LOG_TAG, "# Foxes: " + NUM_FOXES);
		Log.i(LOG_TAG, "Sheep speed: " + SHEEP_SPEED);
		Log.i(LOG_TAG, "Fox speed: " + FOX_SPEED);
		
		//TODO create fragment with canvas that starts threads responsible for rendering each component
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sheep_herder, menu);
		return true;
	}

	@Override
	public void loadPreferences() {
		// TODO: Restore preferences and set default values if no such
		// preference exists
		SharedPreferences settings = getSharedPreferences(PREFS_NAME,
				MODE_PRIVATE);
		
		DOG_SPEED = settings.getInt(DOG_SPEED_PREFS, DEFAULT_DOG_SPEED);
		NUM_SHEEP = settings.getInt(NUM_SHEEP_PREFS, DEFAULT_NUM_SHEEPS);
		NUM_FOXES = settings.getInt(NUM_FOXES_PREFS, DEFAULT_NUM_FOXES);
		SHEEP_SPEED = settings.getInt(SHEEP_SPEED_PREFS, DEFAULT_SHEEP_SPEED);
		FOX_SPEED = settings.getInt(FOX_SPEED_PREFS, DEFAULT_FOX_SPEED);
		
	}

	@Override
	public void storePreferences() {
		// We need an Editor object to make preference changes.
		// All objects are from android.context.Context
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt(DOG_SPEED_PREFS, DOG_SPEED);
		editor.putInt(NUM_SHEEP_PREFS, NUM_SHEEP);
		editor.putInt(NUM_FOXES_PREFS, NUM_FOXES);
		editor.putInt(SHEEP_SPEED_PREFS, SHEEP_SPEED);
		editor.putInt(FOX_SPEED_PREFS, FOX_SPEED);

		// Commit the edits!
		editor.commit();
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
