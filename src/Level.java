
public class Level {
	private Puzzle current; // current puzzle
	private Puzzle init; // the starting puzzle can be use for reset or restart
	
	
	
	public Level(Difficulty difficulty) {
		int axisX = 6; 
		int axisY = 6;
		this.current = new Puzzle(axisX,axisY);
		
	}
	
	public void buildLevel() {
		int x = 0;
		int y = 0;
		
		
		
	}
	
	public void addObjectstoLevel() {
		
	}
	
	/**
	 * Add truck and car into the level
	 * Before adding floor into the level
	 */
	public void generateLevel() {
		
	}
	

	
	
}
