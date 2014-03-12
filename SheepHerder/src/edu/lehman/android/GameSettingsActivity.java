package edu.lehman.android;

import interfaces.SettingsInterface;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

/**
 * A pre-game settings menu where user can alter difficulty and other
 * parameters.
 * 
 * @author Marcos Davila
 * @author Marcus Silveira
 * @author Prince Oladimeji
 * @revisionhistory 3/01/2014 - Adding logs to each lifecycle state for later use on integration tests
 * 					3/1/2014 - A new interface, SettingsInterface, is implemented
 * 					so that this class can work with the default preferences file
 * 					and their own user-created preferences file. The settings from
 * 					the preferences file are reflected in the initial positions of
 * 					the seekbar, and default values for these seekbars are set through
 * 					the shared preferences.
 * 
 *                  2/23/2014 - Shared preferences for controlling game
 *                  parameters created
 * 
 *                  2/11/2014 - Refactoring and fixing end of activity
 *                  (shouldn't start a new one to go back)
 * 
 *                  2/6/2014 - Back and start buttons now move to the activities
 * 
 *                  2/4/2014 - File created
 */
public class GameSettingsActivity extends Activity implements SettingsInterface {
	private static final String LOG_TAG = "GameSettingsActivity";
	
	private SeekBar dogSpeedSeekBar, foxSpeedSeekBar, sheepSpeedSeekBar;
	private TextView textView1, textView2, textView3;
	private Button backButton;
	
	// Runnable objects that the handler executes
	private Runnable setListeners = new Runnable(){

		@Override
		public void run() {
			// Implement actionlisteners and tie actionlisteners to the
			// variables that hold the preferences in the background thread
			backButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					finish();
				}
			});

			dogSpeedSeekBar
					.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

						@Override
						public void onProgressChanged(SeekBar seekBar,
								int progressValue, boolean fromUser) {
							DOG_SPEED = progressValue;

						}

						public void onStartTrackingTouch(SeekBar seekBar) {
							// Do something here if you want to do anything at
							// the start of touching the seekbar
						}

						@Override
						public void onStopTrackingTouch(SeekBar seekBar) {
							// Display the value in textview

							textView1.setText(DOG_SPEED + "/"
									+ dogSpeedSeekBar.getMax());

						}
					});

			foxSpeedSeekBar
					.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

						@Override
						public void onProgressChanged(SeekBar seekBar,
								int progressValue, boolean fromUser) {
							FOX_SPEED = progressValue;

						}

						@Override
						public void onStartTrackingTouch(SeekBar seekBar) {
							// Do something here if you want to do anything at
							// the start
							// of touching the seekbar
						}

						public void onStopTrackingTouch(SeekBar seekBar) {
							// Display the value in textview

							textView2.setText(FOX_SPEED + "/"
									+ foxSpeedSeekBar.getMax());

						}
					});

			sheepSpeedSeekBar
					.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

						public void onProgressChanged(SeekBar seekBar,
								int progressValue, boolean fromUser) {
							SHEEP_SPEED = progressValue;

						}

						public void onStartTrackingTouch(SeekBar seekBar) {
							// Do something here if you want to do anything at
							// the start
							// of touching the seekbar
						}

						public void onStopTrackingTouch(SeekBar seekBar) {
							// Display the value in textview
							textView3.setText(SHEEP_SPEED + "/"
									+ sheepSpeedSeekBar.getMax());

						}
					});
		}
		
	};
	
	private int DOG_SPEED;
	private int NUM_SHEEP;
	private int NUM_FOXES;
	private int SHEEP_SPEED;
	private int FOX_SPEED;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_settings_panel);

		// No matter what, all values are defined before the
		// threads begin
		dogSpeedSeekBar = (SeekBar) findViewById(R.id.dogSpeed);
		foxSpeedSeekBar = (SeekBar) findViewById(R.id.foxSpeed);
		sheepSpeedSeekBar = (SeekBar) findViewById(R.id.sheepSpeed);
		
		textView1 = (TextView) findViewById(R.id.textV05);
		textView2 = (TextView) findViewById(R.id.textV06);
		textView3 = (TextView) findViewById(R.id.textV07);
		backButton = (Button) findViewById(R.id.backButton);
		
		// Set the action listeners for the seekbars
		new Thread(setListeners).start();
		
		Log.i(LOG_TAG, "GameSettingsActivity.onCreate()");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; //this adds items to the action bar if it is
		// present.
		getMenuInflater().inflate(R.menu.game_settings_panel, menu);
		return true;
	}

	@Override
	public void loadPreferences() {
		// TODO: Restore preferences and set default values if no such
		// preference exists
		SharedPreferences settings = getSharedPreferences(PREFS_NAME,
				MODE_PRIVATE);
		
		if(settings != null) {
			DOG_SPEED = settings.getInt(DOG_SPEED_PREFS, DEFAULT_DOG_SPEED);
			NUM_SHEEP = settings.getInt(NUM_SHEEP_PREFS, DEFAULT_NUM_SHEEPS);
			NUM_FOXES = settings.getInt(NUM_FOXES_PREFS, DEFAULT_NUM_FOXES);
			SHEEP_SPEED = settings.getInt(SHEEP_SPEED_PREFS, DEFAULT_SHEEP_SPEED);
			FOX_SPEED = settings.getInt(FOX_SPEED_PREFS, DEFAULT_FOX_SPEED);
		}
		
		// Sets the defaults for the seekbar
		dogSpeedSeekBar.setProgress(DOG_SPEED);
		foxSpeedSeekBar.setProgress(FOX_SPEED);
		sheepSpeedSeekBar.setProgress(SHEEP_SPEED);
		
		// Initialize the textview with '0'
		textView1.setText(dogSpeedSeekBar.getProgress() + "/" + dogSpeedSeekBar.getMax());
		textView2.setText(foxSpeedSeekBar.getProgress() + "/" + foxSpeedSeekBar.getMax());
		textView3.setText(sheepSpeedSeekBar.getProgress() + "/"
				+ sheepSpeedSeekBar.getMax());
	}

	public void storePreferences() {
		// We need an Editor object to make preference changes.
		SharedPreferences settings = getSharedPreferences(PREFS_NAME,
				MODE_PRIVATE);
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
		loadPreferences();
		Log.i(LOG_TAG, "GameSettingsActivity.onRestart()");
	}

	@Override
	protected void onStart() {
		super.onStart();
		loadPreferences();
		Log.i(LOG_TAG, "GameSettingsActivity.onStart()");
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		loadPreferences();
		Log.i(LOG_TAG, "GameSettingsActivity.onResume()");
	}

	@Override
	protected void onPause() {
		super.onPause();
		storePreferences();
		Log.i(LOG_TAG, "GameSettingsActivity.onPause()");
	}

	@Override
	protected void onStop() {
		super.onStop();
		storePreferences();
		Log.i(LOG_TAG, "GameSettingsActivity.onStop()");
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		storePreferences();
		Log.i(LOG_TAG, "GameSettingsActivity.onDestroy()");
	}

}
