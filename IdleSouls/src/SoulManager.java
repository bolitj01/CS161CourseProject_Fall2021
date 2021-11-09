import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.scene.control.Label;

/*
 * Handles all time-related calculations of soul 
 * currencies, bonus applications, etc.
 */
public class SoulManager {
	
	//determines how many times the label updates per second.
	final int LIMIT_OF_UPDATES_PER_SECOND = 60;
	//the period for the addSouls TimerTask (time between executions)
	long addSoulsPeriod;
	//the period for the updateLabel TimerTask (time between executions)
	long updateLabelPeriod;
	//souls changed to double for larger size and for storing decimals (which is important for the souls to
	//add correctly
	double souls;
	int soulsPerSecond;
	//stores the number of souls that is added to souls each time the TimerTask executes
	double soulsAddend;
	//Label for storing souls
	Label soulsL;
	Timer addSoulsTimer;
	Timer updateLabelTimer;
	TimerTask addSouls;
	TimerTask updateLabel;
	
	public SoulManager(Label soulsL) {
		this.soulsL = soulsL;
		souls = 0;
		soulsPerSecond = 0;
		soulsAddend = 0;
		addSoulsPeriod = 1000l;
		//period of update label is always 1 second divided by the limit of updates per second.
		updateLabelPeriod = 1000l / LIMIT_OF_UPDATES_PER_SECOND;
		//Initializes timers, but does not start them yet.
		createAddSouls();
		createUpdateLabel();
	}
	
	public void createAddSouls() {
		addSouls = new TimerTask() {
			
			public void run() {
				
				souls += soulsAddend;
			}
		};
	}
	
	public void createUpdateLabel() {
		updateLabel = new TimerTask() {
			
			public void run() {
				
				Runnable updater = new Runnable() {
					
					public void run() {
						
						soulsL.setText(String.format("%.0f", souls));
					}
				};
				//when updating JavaFX controls in TimerTasks, they need to use the JavaFX thread
				Platform.runLater(updater);
			}
		};
	}
	
	//instantiate Timers and TimerTasks, and start the Timers at a fixed rate.
	public void startAddSoulsTimer() {
		addSoulsTimer = new Timer();
		createAddSouls();
		addSoulsTimer.scheduleAtFixedRate(addSouls, 0, addSoulsPeriod);
	}
	
	public void startUpdateLabelTimer() {
		updateLabelTimer = new Timer();
		createUpdateLabel();
		updateLabelTimer.scheduleAtFixedRate(updateLabel, 0, updateLabelPeriod);
	}
	
	//cancel the Timers
	public void cancelAddSoulsTimer() {
		addSoulsTimer.cancel();
	}
	
	public void cancelUpdateLabelTimer() {
		updateLabelTimer.cancel();
	}
	
	//combine canceling and starting of Timers into one method
	public void restartAddSoulsTimer() {
		cancelAddSoulsTimer();
		startAddSoulsTimer();
	}
	
	public void restartUpdateLabelTimer() {
		cancelUpdateLabelTimer();
		startUpdateLabelTimer();
	}
	
	/**
	 * updateFrequency handles the algorithm for how often the addSouls calculation should be done, and how many
	 * souls should be added. For example, if we have 5 souls per second, we don't want the addSouls task to
	 * increase souls by 5 every 1 second; rather, we want the task to increase souls by 1 every 0.2 seconds. So,
	 * until our soulsPerSecond is greater than the limit of updates per second (determined by the constant
	 * LIMIT_OF_UPDATES_PER_SECOND), we want to add 1 soul at a rate of 1 second divided by soulsPerSecond
	 * (e.g. if soulsPerSecond is 8, then we want to add 1 soul every 1/8 of a second). However, if soulsPerSecond
	 * is greater than the limit of updates per second, then we want to add 1 soul plus a little more every 1 second
	 * divided by the limit of updates per second (e.g. if our limit of updates per second is 100, and we have 120
	 * soulsPerSecond, then we want to add 1.2 souls every 0.01 seconds). This is the algorithm that updateFrequency
	 * implements.
	 */
	public void updateFrequency() {
		if (soulsPerSecond > LIMIT_OF_UPDATES_PER_SECOND) {
			soulsAddend = Double.valueOf(soulsPerSecond) / Double.valueOf(LIMIT_OF_UPDATES_PER_SECOND);
			addSoulsPeriod = updateLabelPeriod;
		} else {
			soulsAddend = 1;
			addSoulsPeriod = 1000l / soulsPerSecond;
		}
		//System.out.println(soulsPerSecond + "\n" + soulsAddend);
	}
}