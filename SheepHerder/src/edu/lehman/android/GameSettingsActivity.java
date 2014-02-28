package edu.lehman.android;

import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

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
	public SeekBar seekBar,seekBar1, seekBar2, seekBar3;
	public TextView textView1,textView2,textView3;

	
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
	    
	     seekBar1 = (SeekBar) findViewById(R.id.dog);
		 seekBar2 = (SeekBar) findViewById(R.id.fox);
		 seekBar3 = (SeekBar) findViewById(R.id.sheep);
		 textView1 = (TextView) findViewById(R.id.textV05);
		 textView2 = (TextView) findViewById(R.id.textV06);
		 textView3 = (TextView) findViewById(R.id.textV07);
		  // Initialize the textview with '0'
		  
	  textView1.setText(seekBar1.getProgress() + "/" + seekBar1.getMax());
      textView2.setText(seekBar2.getProgress() + "/" + seekBar2.getMax());
      textView3.setText(seekBar3.getProgress() + "/" + seekBar3.getMax());    

	
		Button backButton = (Button) findViewById(R.id.backButton);
		backButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish(); //finishes settings view and free up resources
			}
		});
		
		// TODO implement actionlisteners and tie actionlisteners to the
		// variables that hold the preferences
		
		seekBar1.setOnSeekBarChangeListener(new OnSeekBarChangeListener() 
		{
		
			 int progress = 0;
		 
			 
	      @Override
		  public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) 
		    {
	    	       progress = progressValue;
	    	  
                
                
		    }

		      public void onStartTrackingTouch(SeekBar seekBar) {
		        // Do something here,                        //if you want to do anything at the start of
		        // touching the seekbar
		      }

		      @Override
		      public void onStopTrackingTouch(SeekBar seekBar) {
		        // Display the value in textview
		        
		        textView1.setText(progress + "/" + seekBar1.getMax());
		        
		      }
		  });
		
		seekBar2.setOnSeekBarChangeListener(new OnSeekBarChangeListener() 
		{
		
			 int progress = 0;
		        
	      @Override
		  public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) 
		    {
	    	       progress = progressValue;
	    	      
		    }

		      @Override
		      public void onStartTrackingTouch(SeekBar seekBar) {
		        // Do something here,                        //if you want to do anything at the start of
		        // touching the seekbar
		      }

		      public void onStopTrackingTouch(SeekBar seekBar) {
		        // Display the value in textview
		       
		        textView2.setText(progress + "/" + seekBar2.getMax());
		        
		      }
		  });
		
		seekBar3.setOnSeekBarChangeListener(new OnSeekBarChangeListener() 
		{
		
			 int progress = 0;
		        
	      public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) 
		    {
	    	       progress = progressValue;
	    	  
                
                
		    }

		      public void onStartTrackingTouch(SeekBar seekBar) {
		        // Do something here,                        //if you want to do anything at the start of
		        // touching the seekbar
		      }

		      public void onStopTrackingTouch(SeekBar seekBar) {
		        // Display the value in textview
		        textView3.setText(progress + "/" + seekBar3.getMax());
		        
		        
		      }
		  });
	}
      
	protected void onStop(Bundle savedInstanceState){
		super.onStop();

	    // We need an Editor object to make preference changes.
	    // All objects are from android.context.Context
	    SharedPreferences settings1 = getSharedPreferences(PREFS_NAME, 0);
	    SharedPreferences.Editor editor = settings1.edit();
	    editor.putInt(DOG_SPEED_PREFS, DOG_SPEED);
	    editor.putInt(NUM_SHEEP_PREFS, NUM_SHEEP);
	    editor.putInt(NUM_FOXES_PREFS, NUM_FOXES);

	    // Commit the edits!
	    editor.commit();
	
	}
	 @Override
	 public boolean onCreateOptionsMenu(Menu menu) {
	  // Inflate the menu;                  //this adds items to the action bar if it is present.
	  getMenuInflater().inflate(R.menu.game_settings_panel, menu);
	  return true;
	 }

	

	
}
	


	