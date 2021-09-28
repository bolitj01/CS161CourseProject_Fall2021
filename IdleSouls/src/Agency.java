
public class Agency {

	int internCount;
	int assistantManagerCount;
	int managerCount;
	int janitorCount;
	
	Reaper reaper;
	
	String name;
	
	public Agency(String name) {
		internCount = 0;
		assistantManagerCount = 0;
		managerCount = 0;
		janitorCount = 0;
		
		this.name = name;
	}
	
	public void addCultFollower(String type) {
		if (type.equals("Unpaid Intern")) {
			internCount++;
		}
	}
	
	public void hireReaper(Reaper r) {
		reaper = r;
	}
}
