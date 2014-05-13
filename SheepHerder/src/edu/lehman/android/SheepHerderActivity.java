package edu.lehman.android;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.google.android.gms.ads.AdView;

import edu.lehman.android.interfaces.Settings;
import edu.lehman.android.views.GameSurfaceView;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 
 * Hosts the surface view where the game takes place. The sheep herder activity
 * is responsible for managing a place for this view to be active, as well as
 * keeping track of the remaining time and the score of the player. It also
 * maintains a back button if the user wishes to quit the game early.
 * 
 * @author Marcos Davila, Marcus Silveira, Prince Oladimeji
 */
public class SheepHerderActivity extends Activity implements Settings {
	private static final String LOG_TAG = "SheepHerderActivity";

	private int DOG_SPEED;
	private int NUM_SHEEP;
	private int NUM_FOXES;
	private int SHEEP_SPEED;
	private int FOX_SPEED;
	private int HIGHEST_SCORE;
	// starting with a higher number so that if you don't play and fox starts
	// eating sheep, the score doesn't go negative
	public static final int POINTS_AT_START = 500;
	public static int score = POINTS_AT_START;
	private Button backButton;
	public TextView timerView;
	public TextView scoreView;
	private GameSurfaceView surfaceView;
	private RelativeLayout surfaceLayout;
	private ViewGroup topLevelLayout;
	private AdView mAdView;
	private static AlertDialog.Builder alert;

	// The timer placed at the top of the activity, plus some instance
	// variables to represent the total amount of time that can be played
	// and the interval this timer counts down in. Currently, the user has
	// 2 minutes of playtime measured in seconds
	private final int SECONDS_IN_FUTURE = 2 * 60;
	private final int INTERVAL = 1 * 1000;
	private TimerTask countdownTimer;

	/*
	 * Saves the score of the user if the user has a new high score
	 */
	private void storePreferences() {
		if (score > HIGHEST_SCORE && !surfaceView.isRunning()) { // if it's
																	// still
																	// running,
																	// we can't
																	// save the
																	// score,
																	// because
																	// the game
																	// is not
																	// finished
			// We need an Editor object to make preference changes.
			SharedPreferences settings = getSharedPreferences(PREFS_NAME,
					MODE_PRIVATE);
			SharedPreferences.Editor editor = settings.edit();
			editor.putInt(HIGHEST_SCORE_EVER, score);

			// Commit the edits!
			editor.commit();
		}
	}

	/*
	 * On creation, buttons and views are associated with their IDs, preferences
	 * are loaded in from shared preferences and those values are recorded into
	 * field instances, and the surface view is created.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_sheep_herder);
		
		surfaceLayout = (RelativeLayout) findViewById(R.id.gamelayout);
		topLevelLayout = (ViewGroup) findViewById(R.id.topLevelLayout);
		timerView = (TextView) findViewById(R.id.timer);
		scoreView = (TextView) findViewById(R.id.score);

		mAdView = (AdView) findViewById(R.id.adView);
		if(Settings.DEBUG_MODE) {
			Log.i(LOG_TAG, "SheepHerderActivity.onCreate()");
		}
	}

	private void showInstructionsModal() {
		// Display a modal screen on how to play the game and wait for
		// user to tap the screen before starting the game thread
		alert.setTitle(getResources().getString(R.string.instructions));
		alert.setMessage(getResources().getString(R.string.message));

		alert.setPositiveButton(getResources().getString(R.string.start), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				surfaceView.start();

				startTimer();
			}
		});

		alert.setCancelable(false);
		alert.show();
	}

	private void startTimer() {
		if (countdownTimer != null) {
			countdownTimer.cancel();
		}

		countdownTimer = new TimerTask() {
			private boolean prematureFinish;
			private int totalTime = SECONDS_IN_FUTURE;

			@Override
			public void run() {

				updateText(String.format("%02d", totalTime / 60) + ":"
						+ String.format("%02d", totalTime % 60),
						getResources().getString(R.string.score) + " = " + score);

				totalTime--;

				if (totalTime <= 0) {
					this.cancel();

					end();
				} else if (!surfaceView.isRunning()) {
					prematureFinish = true;
					this.cancel();
					end();
				}
			}

			private void updateText(final String timerText,
					final String scoreText) {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						timerView.setText(timerText);
						scoreView.setText(scoreText);

					}
				});
			}

			// Show the user their final score and then stop the timer.
			public void end() {
				String finalTimerText;
				if (prematureFinish) {
					surfaceView.stop();
					finalTimerText = getResources().getString(R.string.game_over);
				} else {
					surfaceView.stop();
					finalTimerText = getResources().getString(R.string.times_up);
				}

				updateText(finalTimerText, getResources().getString(R.string.score) + " = " + score);

				showGameOverModal();
			}

			private void showGameOverModal() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						int hse = HIGHEST_SCORE;
						alert.setTitle(getResources().getString(R.string.game_over));

						if (score > hse) {
							hse = score;
						}

						alert.setMessage(getResources().getString(R.string.your_score) + score
								+ "\n" + getResources().getString(R.string.highest_score) + hse);

						alert.setPositiveButton(getResources().getString(R.string.play_again),
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {
										totalTime = SECONDS_IN_FUTURE;

										// Store highest score if it's the case
										storePreferences();

										score = POINTS_AT_START;
										
										updateText(String.format("%02d", totalTime / 60) + ":"
												+ String.format("%02d", totalTime % 60),
												getResources().getString(R.string.score) + " = " + score);
										
										prematureFinish = false;
										restartSurface();
									}
								});

						alert.setNegativeButton(R.string.main_menu_prompt,
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										finish(); // exit
									}
								});

						alert.show();

					}
				});

			}
		};
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(countdownTimer, new Date(), INTERVAL); // start
																			// a
																			// timer
																			// right
																			// now
																			// to
																			// run
																			// every
																			// second
																			// until
																			// it
																			// reaches
																			// SECONDS_IN_FUTURE
																			// or
																			// is
																			// cancelled
	}

	private void createSurface() {
		surfaceView = new GameSurfaceView(getApplicationContext(), NUM_FOXES,
				NUM_SHEEP, DOG_SPEED, FOX_SPEED, SHEEP_SPEED);
		surfaceLayout.addView(surfaceView);
	}

	private void restartSurface() {
		surfaceView.restart();
		startTimer();
	}

	/**
	 * Inflates the menu and adds items to the action bar if it is present
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.sheep_herder, menu);
		return true;
	}

	/**
	 * Reads values from shared preferences pertaining to the number and speed
	 * of dogs, foxes, and sheeps on the screen. If there are no such values,
	 * default values are used instead.
	 */
	@Override
	public void loadPreferences() {
		SharedPreferences settings = getSharedPreferences(PREFS_NAME,
				MODE_PRIVATE);

		DOG_SPEED = settings.getInt(DOG_SPEED_PREFS, DEFAULT_DOG_SPEED);
		NUM_SHEEP = settings.getInt(NUM_SHEEP_PREFS, DEFAULT_NUM_SHEEPS);
		NUM_FOXES = settings.getInt(NUM_FOXES_PREFS, DEFAULT_NUM_FOXES);
		SHEEP_SPEED = settings.getInt(SHEEP_SPEED_PREFS, DEFAULT_SHEEP_SPEED);
		FOX_SPEED = settings.getInt(FOX_SPEED_PREFS, DEFAULT_FOX_SPEED);
		HIGHEST_SCORE = settings.getInt(HIGHEST_SCORE_EVER, DEFAULT_SCORE);
	}

	/*
	 * On restart, restart the game thread in the surface view
	 */
	@Override
	protected void onRestart() {
		super.onRestart();
		if(Settings.DEBUG_MODE) {
			Log.i(LOG_TAG, "SheepHerderActivity.onRestart()");
		}
	}

	/*
	 * Start the timer when the game begins
	 */
	@Override
	protected void onStart() {
		super.onStart();

		AdsHelper.showAds(mAdView, topLevelLayout);

		// Creates the UI on the UI thread and the handler
		// creates the surface on another
		backButton = (Button) findViewById(R.id.backButton);
		backButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		score = POINTS_AT_START;
		loadPreferences();

		createSurface();

		alert = new AlertDialog.Builder(this);

		showInstructionsModal();
		if(Settings.DEBUG_MODE) {
			Log.i(LOG_TAG, "SheepHerderActivity.onStart()");
		}
	}

	/*
	 * Start the timer when the game resumes
	 */
	@Override
	protected void onResume() {
		super.onResume();
		// Resume the AdView.
		mAdView.resume();
		if(Settings.DEBUG_MODE) {
			Log.i(LOG_TAG, "SheepHerderActivity.onResume()");
		}
	}

	/*
	 * Pause the game thread running in the surface view as well as the timer
	 */
	@Override
	protected void onPause() {
		// Pause the AdView.
		if (mAdView != null) {
			mAdView.pause();
		}

		super.onPause();

		this.storePreferences(); // store highest score if they went to the main
									// menu screen

		if (countdownTimer != null) {
			countdownTimer.cancel();
		}

		if (this.surfaceView != null) {
			this.surfaceView.stop();
		}
		if(Settings.DEBUG_MODE) {
			Log.i(LOG_TAG, "SheepHerderActivity.onPause()");
		}
	}

	/*
	 * Pause the game thread running in the surface view as well as the timer.
	 */
	@Override
	protected void onStop() {
		super.onStop();
		if(Settings.DEBUG_MODE) {
			Log.i(LOG_TAG, "SheepHerderActivity.onStop()");
		}
	}

	/*
	 * Pause the game thread running in the surface view as well as the timer.
	 */
	@Override
	protected void onDestroy() {
		// Destroy the AdView.
		mAdView.destroy();

		super.onDestroy();
		if(Settings.DEBUG_MODE) {
			Log.i(LOG_TAG, "SheepHerderActivity.onDestroy()");
		}
	}
}
