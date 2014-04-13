package edu.lehman.android;

import edu.lehman.android.views.GameSurfaceView;
import interfaces.Settings;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 
 * Hosts the surface view where the game takes place. The sheep herder
 * activity is responsible for managing a place for this view to be
 * active, as well as keeping track of the remaining time and the
 * score of the player. It also maintains a back button if the user
 * wishes to quit the game early.
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
	public static int score=0;
	private Button backButton;
	public TextView timerView;
	public TextView scoreView;
	private GameSurfaceView surfaceView;
	private RelativeLayout surfaceLayout;
	
	// The timer placed at the top of the activity, plus some instance
	// variables to represent the total amount of time that can be played
	// and the interval this timer counts down in. Currently, the user has
	// 15 minutes of playtime measured in seconds
	// 15 minutes of playtime measured in seconds
	final int MILLIS_IN_FUTURE = 5 * 20000;
	final int INTERVAL = 1*1000;
	private CountDownTimer countDownTimer = new CountDownTimer(MILLIS_IN_FUTURE, INTERVAL) {
		
		private int totalTime = (MILLIS_IN_FUTURE / INTERVAL);
        private boolean prematureFinish;
        
		// Update the time left every tick
		public void onTick(long millisUntilFinished) {
		    timerView.setText(String.format("%02d", totalTime/60) + ":" + String.format("%02d", totalTime%60));  
		    scoreView.setText("Score = " + score);
		    totalTime--;
		    
		    if (totalTime <=0) {
		    	cancel();
			} else if(!surfaceView.isRunning()) {
				prematureFinish = true;
				cancel();
			}
		}

		// Show the user their final score and then stop the timer.
		public void onFinish() {
			if(prematureFinish) {
				timerView.setText("Game Over!");
			} else {
				surfaceView.stop();
				timerView.setText("Time's Up!");
			}
			scoreView.setText("Score = " + score);
		}

	};
	
	/*
	 * On creation, buttons and views are associated with their
	 * IDs, preferences are loaded in from shared preferences
	 * and those values are recorded into field instances,
	 * and the surface view is created. 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sheep_herder);
		score=0;
		surfaceLayout = (RelativeLayout) findViewById(R.id.gamelayout);
		timerView = (TextView) findViewById(R.id.timer);
		scoreView = (TextView) findViewById(R.id.score);
		
		// Creates the UI on the UI thread and the handler
		// creates the surface on another
		backButton = (Button) findViewById(R.id.backButton);
		backButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		
		loadPreferences();
		
		this.surfaceView = new GameSurfaceView(getApplicationContext(), 
				NUM_FOXES, NUM_SHEEP, DOG_SPEED, FOX_SPEED, SHEEP_SPEED );
		this.surfaceLayout.addView(this.surfaceView);
				
		Log.i(LOG_TAG, "SheepHerderActivity.onCreate()");
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
	 * Reads values from shared preferences pertaining to the number
	 * and speed of dogs, foxes, and sheeps on the screen. If there
	 * are no such values, default values are used instead.
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
	}

	/*
	 * On restart, restart the game thread in the surface view
	 */
	@Override
	protected void onRestart() {
		super.onRestart();
		surfaceView.restart();

		Log.i(LOG_TAG, "SheepHerderActivity.onRestart()");
	}

	/*
	 * Start the timer when the game begins
	 */
	@Override
	protected void onStart() {
		super.onStart();
		Log.i(LOG_TAG, "SheepHerderActivity.onStart()");
	}

	/*
	 * Start the timer when the game resumes
	 */
	@Override
	protected void onResume() {
		super.onResume();
		countDownTimer.start();
		this.surfaceView.restart();
		
		Log.i(LOG_TAG, "SheepHerderActivity.onResume()");
	}

	/*
	 * Pause the game thread running in the surface view as well
	 * as the timer
	 */
	@Override
	protected void onPause() {
		super.onPause();
		countDownTimer.cancel();
		this.surfaceView.stop();
		
		Log.i(LOG_TAG, "SheepHerderActivity.onPause()");
	}

	/*
	 * Pause the game thread running in the surface view as well as the timer.
	 */
	@Override
	protected void onStop() {
		super.onStop();
		Log.i(LOG_TAG, "SheepHerderActivity.onStop()");
	}

	/*
	 * Pause the game thread running in the surface view as well as the timer.
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();

		Log.i(LOG_TAG, "SheepHerderActivity.onDestroy()");
	}
}
