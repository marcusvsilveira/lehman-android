package edu.lehman.android;

import android.os.Bundle;
import android.app.Activity;
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
public class SheepHerderActivity extends Activity {

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
		
		//TODO create fragment with canvas that starts threads responsible for rendering each component
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sheep_herder, menu);
		return true;
	}

}
