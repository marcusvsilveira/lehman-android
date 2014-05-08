package edu.lehman.android.timer;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class SheepHerderTimer {

	private Timer timer;
	private final int INTERVAL = 1 * 1000;
	
	public SheepHerderTimer(TimerTask countdownTimer){
		timer = new Timer();
		timer.scheduleAtFixedRate(countdownTimer, new Date(), INTERVAL);
	}
}
