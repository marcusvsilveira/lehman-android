package edu.lehman.android;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * A pre-game settings menu where user can alter difficulty and other parameters.
 * 
 * @author Marcos Davila
 * @author Marcus Silveira
 * @revisionhistory
 *	    2/11/2014 - Refactoring and fixing end of activity (shouldn't start a new one to go back)
 * 		2/6/2014 - Back and start buttons now move to the activities
 * 		2/4/2014 - File created
 */
public class GameSettingsActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_settings_panel);
		
		Button backButton = (Button) findViewById(R.id.backButton);
		backButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish(); //finishes settings view and free up resources
			}
		});
		
		//TODO implement new items and save preferences
	}
	
}
