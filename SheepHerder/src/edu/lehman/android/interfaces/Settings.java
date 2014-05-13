package edu.lehman.android.interfaces;

public interface Settings {
	public static final String PREFS_NAME = "MyPrefsFile";

	public static final String DOG_SPEED_PREFS = "dogSpeed";
	public static final String NUM_SHEEP_PREFS = "numSheep";
	public static final String NUM_FOXES_PREFS = "numFoxes";
	public static final String SHEEP_SPEED_PREFS = "sheepSpeed";
	public static final String FOX_SPEED_PREFS = "foxSpeed";
	public static final String HIGHEST_SCORE_EVER = "highestScore";
	
	public static final int DEFAULT_DOG_SPEED = 5;
	public static final int DEFAULT_NUM_SHEEPS = 10;
	public static final int DEFAULT_NUM_FOXES = 1;
	public static final int DEFAULT_FOX_SPEED = 7;
	public static final int DEFAULT_SHEEP_SPEED = 3;
	public static final int DEFAULT_SCORE = 0;
	
	public static final boolean DEBUG_MODE = false;
	
	public void loadPreferences();
}
