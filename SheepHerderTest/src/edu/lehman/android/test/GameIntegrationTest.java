package edu.lehman.android.test;

import com.robotium.solo.Solo;

import edu.lehman.android.MainActivity;
import edu.lehman.android.R;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ActivityUnitTestCase;
import android.widget.Button;

/**
 * This class is responsible for integration tests on the whole system. 
 * It simulates user interaction using Robotium, validates log messages, and app behavior.
 * @author marcus.silveira
 *
 */
public class GameIntegrationTest extends ActivityInstrumentationTestCase2<MainActivity> {
	private Solo solo;
	private Button settings, startGame, quitButton;
	private MainActivity activity;
	
	public GameIntegrationTest() {
		super(MainActivity.class);
	}

	public void setUp() throws Exception {
		super.setUp();
		//sets up Robotium
		solo = new Solo(getInstrumentation());
		//creates activity using instrumentation
		getActivity();
		
        // Getting a reference to the MainActivity of the target application
        activity = (MainActivity)getActivity();
        
	    settings = (Button) activity.findViewById(R.id.settingsButton);
		startGame = (Button) activity.findViewById(R.id.startButton);
		quitButton = (Button) activity.findViewById(R.id.quitButton);
		
	}
	
	public void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}
	public void testRun() {
		// Wait for activity: 'edu.lehman.android.MainActivity'
		assertTrue("edu.lehman.android.MainActivity is not found!", solo.waitForActivity(MainActivity.class));
//		solo.waitForEmptyActivityStack(5);
//		
//		// Check for log messages
//		assertTrue("Log message for 'onPause()' was not found in the Log",
//				solo.waitForLogMessage("onPause", 5));
		assertTrue("Log message for onCreate()' was not found in the Log",
				solo.waitForLogMessage("onCreate", 5));
		assertTrue("Log message for onStart()'  was not found in the Log",
				solo.waitForLogMessage("onStart", 5));
		assertTrue("Log message for onResume()' was not found in the Log",
				solo.waitForLogMessage("onResume", 5));

		
		//other activities were finished
		solo.waitForEmptyActivityStack(5);
		
		// Clear the log
		solo.clearLog();
		
		// Click on Quit Activity
		solo.clickOnView(solo.getView(R.id.quitButton));
		
//		// Wait for activity: 'course.labs.activitylab.ActivityOne'
//		assertTrue("course.labs.activitylab.ActivityOne is not found!", solo.waitForActivity(course.labs.activitylab.ActivityOne.class));
		
		//TODO -> test if shared preferences are saved
		//TODO test if going back and forth also works

	}

}
