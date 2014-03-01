package interfaces;

public interface SettingsInterface {
	public static final String PREFS_NAME = "MyPrefsFile";

	public static final String DOG_SPEED_PREFS = "dogSpeed";
	public static final String NUM_SHEEP_PREFS = "numSheep";
	public static final String NUM_FOXES_PREFS = "numFoxes";
	public static final String SHEEP_SPEED_PREFS = "sheepSpeed";
	public static String FOX_SPEED_PREFS = "foxSpeed";
	
	public int DEFAULT_DOG_SPEED = 5;
	public int DEFAULT_NUM_SHEEPS = 10;
	public int DEFAULT_NUM_FOXES = 1;
	public int DEFAULT_FOX_SPEED = 7;
	public int DEFAULT_SHEEP_SPEED = 3;
	
	public void loadPreferences();
	public void storePreferences();
}
