package edu.lehman.android.test;

import edu.lehman.android.GameSettingsActivity;
import edu.lehman.android.R;
import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Unit test class to ensure main UI components are rendered and properly set
 * @author marcus.silveira
 *
 */
public class GameSettingsActivityUnitTest extends ActivityUnitTestCase<GameSettingsActivity> {
	private Button backButton;
	public SeekBar seekBarDogSpeed, seekBarFoxSpeed, seekBarSheepSpeed;
	public TextView textViewDogSpeed,textViewFoxSpeed,textViewSheepSpeed;
	
	private GameSettingsActivity activity;
	
	public GameSettingsActivityUnitTest() {
		super(GameSettingsActivity.class);
	}

	public void setUp() throws Exception {
		super.setUp();
		
		// Starts the MainActivity of the target application
        startActivity(new Intent(getInstrumentation().getTargetContext(), GameSettingsActivity.class), null, null);
        // Getting a reference to the MainActivity of the target application
        activity = (GameSettingsActivity)getActivity();
        getInstrumentation().callActivityOnStart(activity); //required for these tests in order to have the listeners set up 
        
        seekBarDogSpeed = (SeekBar) activity.findViewById(R.id.dogSpeed);
        seekBarFoxSpeed = (SeekBar) activity.findViewById(R.id.foxSpeed);
        seekBarSheepSpeed = (SeekBar) activity.findViewById(R.id.sheepSpeed);
        
        textViewDogSpeed = (TextView) activity.findViewById(R.id.textV05);
        textViewFoxSpeed = (TextView) activity.findViewById(R.id.textV06);
        textViewSheepSpeed = (TextView) activity.findViewById(R.id.textV07);
        
		backButton = (Button) activity.findViewById(R.id.backButton);
	}
	
	public void testSeekBarsAndTextViews() {
		//verify presence
		assertNotNull(this.seekBarDogSpeed);
		assertNotNull(this.seekBarFoxSpeed);
		assertNotNull(this.seekBarSheepSpeed);
		assertNotNull(this.textViewDogSpeed);
		assertNotNull(this.textViewFoxSpeed);
		assertNotNull(this.textViewSheepSpeed);
		
		//check if all are enabled
		assertTrue(this.seekBarDogSpeed.isEnabled());
		assertTrue(this.seekBarFoxSpeed.isEnabled());
		assertTrue(this.seekBarSheepSpeed.isEnabled());
		assertTrue(this.textViewDogSpeed.isEnabled());
		assertTrue(this.textViewFoxSpeed.isEnabled());
		assertTrue(this.textViewSheepSpeed.isEnabled());
		
		//verify default values
		assertTrue(this.seekBarDogSpeed.getProgress() >= 0 && this.seekBarDogSpeed.getProgress() <= 10);
		assertTrue(this.seekBarSheepSpeed.getProgress() >= 0 && this.seekBarSheepSpeed.getProgress() <= 10);
		assertTrue(this.seekBarFoxSpeed.getProgress() >= 0 && this.seekBarFoxSpeed.getProgress() <= 10);
		
		assertNotNull(this.textViewSheepSpeed.getText());
		assertNotNull(this.textViewDogSpeed.getText());
		assertNotNull(this.textViewFoxSpeed.getText());	
	}
	
	public void testBack() {
		//ensures back button works as expected
		assertFalse(isFinishCalled());
		this.backButton.performClick();
		assertTrue(isFinishCalled());
	}

}
