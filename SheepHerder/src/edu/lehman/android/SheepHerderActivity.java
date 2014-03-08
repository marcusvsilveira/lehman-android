package edu.lehman.android;

import edu.lehman.android.views.GameSurfaceView;
import interfaces.SettingsInterface;
import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

/**
 * 
 * This is the Game screen
 * 
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

		Button backButton = (Button) findViewById(R.id.backButton);
		backButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish(); // finishes game and free up resources
			}
		});

		RelativeLayout surfaceLayout = (RelativeLayout) findViewById(R.id.gamelayout);
		final GameSurfaceView surfaceView = new GameSurfaceView(
				getApplicationContext(), BitmapFactory.decodeResource(
						getResources(), R.drawable.gamedog),
				BitmapFactory
						.decodeResource(getResources(), R.drawable.gamefox),
				BitmapFactory.decodeResource(getResources(),
						R.drawable.gamesheep));
		surfaceLayout.addView(surfaceView);

		Log.i(LOG_TAG, "SheepHerderActivity.onCreate()");
		// TODO create fragment with canvas that starts threads responsible for
		// rendering each component
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

		Log.i(LOG_TAG, "Dog speed: " + DOG_SPEED);
		Log.i(LOG_TAG, "# Sheep: " + NUM_SHEEP);
		Log.i(LOG_TAG, "# Foxes: " + NUM_FOXES);
		Log.i(LOG_TAG, "Sheep speed: " + SHEEP_SPEED);
		Log.i(LOG_TAG, "Fox speed: " + FOX_SPEED);
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Log.i(LOG_TAG, "SheepHerderActivity.onRestart()");
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.i(LOG_TAG, "SheepHerderActivity.onStart()");
	}

	@Override
	protected void onResume() {
		super.onResume();
		loadPreferences();
		Log.i(LOG_TAG, "SheepHerderActivity.onResume()");
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.i(LOG_TAG, "SheepHerderActivity.onPause()");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.i(LOG_TAG, "SheepHerderActivity.onStop()");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.i(LOG_TAG, "SheepHerderActivity.onDestroy()");
	}
}
