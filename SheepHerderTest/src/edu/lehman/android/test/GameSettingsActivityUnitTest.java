package edu.lehman.android.test;

import edu.lehman.android.GameSettingsActivity;
import edu.lehman.android.R;
import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

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
        
        seekBarDogSpeed = (SeekBar) activity.findViewById(R.id.dogSpeed);
        seekBarFoxSpeed = (SeekBar) activity.findViewById(R.id.foxSpeed);
        seekBarSheepSpeed = (SeekBar) activity.findViewById(R.id.sheepSpeed);
        
        textViewDogSpeed = (TextView) activity.findViewById(R.id.textV05);
        textViewFoxSpeed = (TextView) activity.findViewById(R.id.textV06);
        textViewSheepSpeed = (TextView) activity.findViewById(R.id.textV07);
        
		backButton = (Button) activity.findViewById(R.id.backButton);
	}
	
	public void testSeekBarsAndTextViews() throws Throwable {
		//verify presence
		assertNotNull(this.seekBarDogSpeed);
		assertNotNull(this.seekBarFoxSpeed);
		assertNotNull(this.seekBarSheepSpeed);
		assertNotNull(this.textViewDogSpeed);
		assertNotNull(this.textViewFoxSpeed);
		assertNotNull(this.textViewSheepSpeed);
		
		assertTrue(this.seekBarDogSpeed.isEnabled());
		assertTrue(this.seekBarFoxSpeed.isEnabled());
		assertTrue(this.seekBarSheepSpeed.isEnabled());
		assertTrue(this.textViewDogSpeed.isEnabled());
		assertTrue(this.textViewFoxSpeed.isEnabled());
		assertTrue(this.textViewSheepSpeed.isEnabled());
		
		//verify default values
		assertEquals(this.seekBarDogSpeed.getProgress(), 5);
		assertEquals(this.seekBarSheepSpeed.getProgress(), 3);
		assertEquals(this.seekBarFoxSpeed.getProgress(), 7);
		
		assertEquals(this.textViewSheepSpeed.getText(), "3/10");
		assertEquals(this.textViewDogSpeed.getText(), "5/10");
		assertEquals(this.textViewFoxSpeed.getText(), "7/10");
	
	}
	
	public void testBack() {
		assertFalse(isFinishCalled());
		this.backButton.performClick();
		assertTrue(isFinishCalled());
	}

}
