import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javafx.scene.control.Label;

/*
 * Handles all time-related calculations of soul 
 * currencies, bonus applications, etc.
 */
public class SoulManager {
	
	float time;
	int souls;
	int soulsPerSecond;
	Label soulsL;
	
	
	//Get some souls every 1s
	public SoulManager(Label soulsL) {
		this.soulsL = soulsL;
		souls = 0;
		soulsPerSecond = 1;
		time = 0;
//		start();		
//		this.t = t;
	}
	
	
	
}
