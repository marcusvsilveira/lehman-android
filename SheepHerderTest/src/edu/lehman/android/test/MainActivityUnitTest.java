package edu.lehman.android.test;

import edu.lehman.android.MainActivity;
import edu.lehman.android.R;
import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.widget.Button;

/**
 * Unit test class to ensure main UI components are rendered and functioning
 * @author marcus.silveira
 *
 */
public class MainActivityUnitTest extends ActivityUnitTestCase<MainActivity> {
	private Button settingsButton, startGameButton, quitButton;
	private MainActivity activity;
	
	public MainActivityUnitTest() {
		super(MainActivity.class);
	}

	public void setUp() throws Exception {
		super.setUp();
		// Starts the MainActivity of the target application
        startActivity(new Intent(getInstrumentation().getTargetContext(), MainActivity.class), null, null);
        // Getting a reference to the MainActivity of the target application
        activity = (MainActivity)getActivity();
        getInstrumentation().callActivityOnStart(activity); //required for these tests in order to have the listeners set up 
        
	    settingsButton = (Button) activity.findViewById(R.id.settingsButton);
		startGameButton = (Button) activity.findViewById(R.id.startButton);
		quitButton = (Button) activity.findViewById(R.id.quitButton);
		
	}
	
	public void testSettings() {
		this.settingsButton.performClick();
		Intent settingsIntent = getStartedActivityIntent();
		assertNotNull(settingsIntent);
		assertEquals(settingsIntent.getComponent().getClassName(), "edu.lehman.android.GameSettingsActivity");
	}
	
	public void testQuit() {
		assertFalse(isFinishCalled());
		this.quitButton.performClick();
		assertTrue(isFinishCalled());
	}
	
	public void testStartGame() {
		this.startGameButton.performClick();
		Intent startGameIntent = getStartedActivityIntent();
		assertNotNull(startGameIntent);
		assertEquals(startGameIntent.getComponent().getClassName(), "edu.lehman.android.SheepHerderActivity");
	}

}
