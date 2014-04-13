package edu.lehman.android.test;

import com.robotium.solo.Solo;

import edu.lehman.android.GameSettingsActivity;
import edu.lehman.android.MainActivity;
import edu.lehman.android.R;
import edu.lehman.android.SheepHerderActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ActivityUnitTestCase;
import android.widget.Button;
import android.widget.SeekBar;

/**
 * This class is responsible for integration tests on the whole system. 
 * It simulates user interaction using Robotium, validates log messages, and app behavior.
 * @author marcus.silveira
 *
 */
public class GameIntegrationTest extends ActivityInstrumentationTestCase2<MainActivity> {
	private Solo solo;
	private MainActivity activity;
	
	public GameIntegrationTest() {
		super(MainActivity.class);
	}

	public void setUp() throws Exception {
		super.setUp();
		//sets up Robotium
		solo = new Solo(getInstrumentation());
		
        // Getting a reference to the MainActivity of the target application
        activity = (MainActivity)getActivity();
     }
	
	public void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}
	public void testRun() throws Exception {
		// Wait for activity: 'edu.lehman.android.MainActivity'
		assertTrue("edu.lehman.android.MainActivity is not found!", solo.waitForActivity(MainActivity.class));
	
		// Check for log messages
		assertTrue("Log message for MainActivity.onCreate()' was not found in the Log",
				solo.waitForLogMessage("MainActivity.onCreate()", 5));
		assertTrue("Log message for MainActivity.onStart()'  was not found in the Log",
				solo.waitForLogMessage("MainActivity.onStart()", 5));
		assertTrue("Log message for MainActivity.onResume()' was not found in the Log",
				solo.waitForLogMessage("MainActivity.onResume()", 5));
				
		// Clear the log
		solo.clearLog();
		
		// Click on Settings button
		solo.clickOnView(solo.getView(R.id.settingsButton));
		
		//validates activity started
		assertTrue("edu.lehman.android.GameSettingsActivity is not found!", solo.waitForActivity(GameSettingsActivity.class));
		
		//reset shared preferences in case the app is already installed and someone changed the values
		final SharedPreferences preferences = activity.getSharedPreferences(GameSettingsActivity.PREFS_NAME, GameSettingsActivity.MODE_PRIVATE);
		assertNotNull("Preferences are null", preferences);
		
		//validate shared preferences
		final SharedPreferences.Editor preferencesEditor = preferences.edit();
		preferencesEditor.clear();
		preferencesEditor.commit();
		
		assertEquals("Num sheep was not the default", preferences.getInt(GameSettingsActivity.NUM_SHEEP_PREFS,GameSettingsActivity.DEFAULT_NUM_SHEEPS), GameSettingsActivity.DEFAULT_NUM_SHEEPS);
		assertEquals("Num foxes was not the default", preferences.getInt(GameSettingsActivity.NUM_FOXES_PREFS,GameSettingsActivity.DEFAULT_NUM_FOXES), GameSettingsActivity.DEFAULT_NUM_FOXES);
		assertEquals("Dog Speed was not the default", preferences.getInt(GameSettingsActivity.DOG_SPEED_PREFS,GameSettingsActivity.DEFAULT_DOG_SPEED), GameSettingsActivity.DEFAULT_DOG_SPEED);
		assertEquals("Fox Speed was not the default", preferences.getInt(GameSettingsActivity.FOX_SPEED_PREFS,GameSettingsActivity.DEFAULT_FOX_SPEED), GameSettingsActivity.DEFAULT_FOX_SPEED);
		assertEquals("Sheep Speed was not the default", preferences.getInt(GameSettingsActivity.SHEEP_SPEED_PREFS,GameSettingsActivity.DEFAULT_SHEEP_SPEED), GameSettingsActivity.DEFAULT_SHEEP_SPEED);
		
		//change things around
		SeekBar dogSpeed = (SeekBar)solo.getCurrentActivity().findViewById(R.id.dogSpeed);
		solo.waitForView(dogSpeed.getId());
		solo.setProgressBar(dogSpeed, 1);
		assertEquals("Dog Speed", dogSpeed.getProgress(), 1);
		
		SeekBar foxSpeed = (SeekBar)solo.getCurrentActivity().findViewById(R.id.foxSpeed);
		solo.waitForView(foxSpeed.getId());
		solo.setProgressBar(foxSpeed, 1);
		assertEquals("Fox Speed", foxSpeed.getProgress(), 1);
		
		SeekBar sheepSpeed = (SeekBar)solo.getCurrentActivity().findViewById(R.id.sheepSpeed);
		solo.waitForView(sheepSpeed.getId());
		solo.setProgressBar(sheepSpeed, 10);
		assertEquals("Sheep Speed", sheepSpeed.getProgress(), 10);
		
		solo.waitForView(R.id.backButton);
		solo.clickOnView(solo.getView(R.id.backButton));
		
		assertTrue("edu.lehman.android.MainActivity is not found!", solo.waitForActivity(MainActivity.class));
		
		final SharedPreferences preferences2 = activity.getSharedPreferences(GameSettingsActivity.PREFS_NAME, GameSettingsActivity.MODE_PRIVATE);
		assertEquals("Dog Speed was not the new value", preferences2.getInt(GameSettingsActivity.DOG_SPEED_PREFS,GameSettingsActivity.DEFAULT_DOG_SPEED), 1);
		assertEquals("Fox Speed was not the new value", preferences2.getInt(GameSettingsActivity.FOX_SPEED_PREFS,GameSettingsActivity.DEFAULT_FOX_SPEED), 1);
		assertEquals("Sheep Speed was not the new value", preferences2.getInt(GameSettingsActivity.SHEEP_SPEED_PREFS,GameSettingsActivity.DEFAULT_SHEEP_SPEED), 10);
	
		// Click on Start Game
		solo.waitForView(R.id.startButton);
		solo.clickOnView(solo.getView(R.id.startButton));
		
		//validates Game started
		assertTrue("edu.lehman.android.SheepHerderActivity is not found!", solo.waitForActivity(SheepHerderActivity.class));
	
		//go back
		solo.waitForView(R.id.backButton);
		solo.clickOnView(solo.getView(R.id.backButton));
		
		assertTrue("edu.lehman.android.MainActivity is not found!", solo.waitForActivity(MainActivity.class));
		//validates no activity is still on the stack
		
		// Click on Quit Activity
		solo.waitForView(R.id.quitButton);
		solo.clickOnView(solo.getView(R.id.quitButton));
	}

}
