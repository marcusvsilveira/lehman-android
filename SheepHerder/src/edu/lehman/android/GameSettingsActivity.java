package edu.lehman.android;

import com.google.android.gms.ads.AdView;

import interfaces.Settings;
import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

/**
 * A pre-game settings menu where user can alter difficulty and other
 * parameters. These settings are passed into the game via shared preferences.
 * 
 * @author Marcos Davila
 * @author Marcus Silveira
 * @author Prince Oladimeji
 */
public class GameSettingsActivity extends Activity implements Settings {
	private static final String LOG_TAG = "GameSettingsActivity";

	private AdView mAdView;
	private LinearLayout topLevelLayout;
	
	private SeekBar dogSpeedSeekBar, foxSpeedSeekBar, sheepSpeedSeekBar;
	private TextView textView1, textView2, textView3;
	private Button backButton;
	private RadioListener radioListener;

	private RadioGroup foxRadioGroup;
	private RadioGroup sheepRadioGroup;

	private int DOG_SPEED;
	private int NUM_SHEEP;
	private int NUM_FOXES;
	private int SHEEP_SPEED;
	private int FOX_SPEED;

	/**
	 * Associate seekbars, textviews, radio groups and buttons with their IDs
	 */
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

		foxRadioGroup = (RadioGroup) findViewById(R.id.fox_radios);
		sheepRadioGroup = (RadioGroup) findViewById(R.id.sheep_radios);

		topLevelLayout = (LinearLayout) findViewById(R.id.topLevelLayout);
		
		mAdView = (AdView) findViewById(R.id.adView);
        AdsHelper.showAds(mAdView, topLevelLayout);
		
		Log.i(LOG_TAG, "GameSettingsActivity.onCreate()");
	}

	/**
	 * Inflates the menu and adds items to the action bar if any need to be
	 * added
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.game_settings_panel, menu);
		return true;
	}

	/**
	 * Loads in user preferences concerning the number of dogs, foxes and sheep
	 * that are to be in the next game. If there are no shared preferences,
	 * default values are used.
	 */
	@Override
	public void loadPreferences() {
		SharedPreferences settings = getSharedPreferences(PREFS_NAME,
				MODE_PRIVATE);

		if (settings != null) {
			DOG_SPEED = settings.getInt(DOG_SPEED_PREFS, DEFAULT_DOG_SPEED);
			NUM_SHEEP = settings.getInt(NUM_SHEEP_PREFS, DEFAULT_NUM_SHEEPS);
			NUM_FOXES = settings.getInt(NUM_FOXES_PREFS, DEFAULT_NUM_FOXES);
			SHEEP_SPEED = settings.getInt(SHEEP_SPEED_PREFS,
					DEFAULT_SHEEP_SPEED);
			FOX_SPEED = settings.getInt(FOX_SPEED_PREFS, DEFAULT_FOX_SPEED);
		}

		// Sets the defaults for the seekbar
		dogSpeedSeekBar.setProgress(DOG_SPEED);
		foxSpeedSeekBar.setProgress(FOX_SPEED);
		sheepSpeedSeekBar.setProgress(SHEEP_SPEED);

		if (NUM_SHEEP == 10) {
			sheepRadioGroup.check(R.id.ten_sheeps);
		} else if (NUM_SHEEP == 15) {
			sheepRadioGroup.check(R.id.fifteen_sheeps);
		} else {
			sheepRadioGroup.check(R.id.twenty_sheeps);
		}

		if (NUM_FOXES == 5) {
			foxRadioGroup.check(R.id.five_foxes);
		} else if (NUM_FOXES == 8) {
			foxRadioGroup.check(R.id.eight_foxes);
		} else {
			foxRadioGroup.check(R.id.twelve_foxes);
		}

		// Initialize the textview with '0'
		textView1.setText(dogSpeedSeekBar.getProgress() + "/"
				+ dogSpeedSeekBar.getMax());
		textView2.setText(foxSpeedSeekBar.getProgress() + "/"
				+ foxSpeedSeekBar.getMax());
		textView3.setText(sheepSpeedSeekBar.getProgress() + "/"
				+ sheepSpeedSeekBar.getMax());
	}

	/**
	 * Stores any changed values that the user may want so that it can be
	 * reflected in the game
	 */
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

	/**
	 * Load preferences again if the activity is restarted
	 */
	@Override
	protected void onRestart() {
		super.onRestart();
		Log.i(LOG_TAG, "GameSettingsActivity.onRestart()");
	}

	/**
	 * Attaches actionlisteners to the objects in this activity and ties them to
	 * their appropriate actions.
	 */
	@Override
	protected void onStart() {
		super.onStart();
		
		backButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});

		// radio buttons
		radioListener = new RadioListener();
		foxRadioGroup.setOnCheckedChangeListener(radioListener);
		sheepRadioGroup.setOnCheckedChangeListener(radioListener);

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

		Log.i(LOG_TAG, "GameSettingsActivity.onStart()");
	}

	/*
	 * Callbacks that load and store preferences when they are called.
	 */
	@Override
	protected void onResume() {
		super.onResume();
		mAdView.resume();
		loadPreferences();
		Log.i(LOG_TAG, "GameSettingsActivity.onResume()");
	}

	@Override
	protected void onPause() {
		mAdView.pause();
		storePreferences();
		super.onPause();
		Log.i(LOG_TAG, "GameSettingsActivity.onPause()");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.i(LOG_TAG, "GameSettingsActivity.onStop()");
	}

	@Override
	protected void onDestroy() {
		mAdView.destroy();
		super.onDestroy();
		Log.i(LOG_TAG, "GameSettingsActivity.onDestroy()");
	}

	/*
	 * A RadioGroup that allows a user to select the number of foxes and the
	 * number of sheep that are supposed to be in the game
	 */
	private class RadioListener implements RadioGroup.OnCheckedChangeListener {
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			if (checkedId == R.id.five_foxes) {
				NUM_FOXES = 5;
			} else if (checkedId == R.id.eight_foxes) {
				NUM_FOXES = 8;
			} else if (checkedId == R.id.twelve_foxes) {
				NUM_FOXES = 12;
			} else if (checkedId == R.id.ten_sheeps) {
				NUM_SHEEP = 10;
			} else if (checkedId == R.id.fifteen_sheeps) {
				NUM_SHEEP = 15;
			} else if (checkedId == R.id.twenty_sheeps) {
				NUM_SHEEP = 20;
			}
		}
	}
}
