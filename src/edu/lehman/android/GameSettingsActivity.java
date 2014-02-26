package edu.lehman.android;

import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * A pre-game settings menu where user can alter difficulty and other parameters.
 * 
 * @author Marcos Davila
 * @author Marcus Silveira
 * @revisionhistory
 * 		2/23/2014 - Shared preferences for controlling game parameters created
 *	    2/11/2014 - Refactoring and fixing end of activity (shouldn't start a new one to go back)
 * 		2/6/2014 - Back and start buttons now move to the activities
 * 		2/4/2014 - File created
 */
public class GameSettingsActivity extends Activity {
	
	public static final String PREFS_NAME = "MyPrefsFile";

	private final String DOG_SPEED_PREFS = "dogSpeed";
	private final String NUM_SHEEP_PREFS = "numSheep";
	private final String NUM_FOXES_PREFS = "numFoxes";
	
	private int DOG_SPEED;
	private int NUM_SHEEP;
	private int NUM_FOXES;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_settings_panel);
		
		// TODO: Restore preferences and set default values if no such
		// preference exists
	    SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
	    DOG_SPEED = settings.getInt(DOG_SPEED_PREFS, 3);
	    NUM_SHEEP = settings.getInt(NUM_SHEEP_PREFS, 10);
	    NUM_FOXES = settings.getInt(NUM_FOXES_PREFS, 1);
		
		Button backButton = (Button) findViewById(R.id.backButton);
		backButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish(); //finishes settings view and free up resources
			}
		});
		
		// TODO implement actionlisteners and tie actionlisteners to the
		// variables that hold the preferences
	}
	
	protected void onStop(Bundle savedInstanceState){
		super.onStop();

	    // We need an Editor object to make preference changes.
	    // All objects are from android.context.Context
	    SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
	    SharedPreferences.Editor editor = settings.edit();
	    editor.putInt(DOG_SPEED_PREFS, DOG_SPEED);
	    editor.putInt(NUM_SHEEP_PREFS, NUM_SHEEP);
	    editor.putInt(NUM_FOXES_PREFS, NUM_FOXES);

	    // Commit the edits!
	    editor.commit();

	}
	
}
