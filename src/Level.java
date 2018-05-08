
public class Level {
	private Puzzle current; // current puzzle
	private Puzzle init; // the starting puzzle can be use for reset or restart
	
	
	
	public Level() {
		int axisX = 6; 
		int axisY = 6;
		this.current = new Puzzle(axisX,axisY);
		
	}
	
	public void buildLevel() {
		int x = 0;
		int y = 0;
		
		for (y = 0; y < this.current.getPuzzleYLen(); y++) {
			for (x = 0; x < this.current.getPuzzleXLen(); x++) {
				InGameObject floor = new InGameObject(x,y,Type.FLOOR);
				current.addObjectToPuzzle(floor);
			}
		}
		
		
	}
	
	public void addObjectstoLevel(InGameObject g) {
		
	}
	
	
	public void generateLevel() {
		
	}

	public Puzzle getCurrent() {
		return current;
	}

	public void setCurrent(Puzzle current) {
		this.current = current;
	}
	
	public void printPuzzle() {
		int x = 0;
		int y = 0;
		for(y = 0; y < this.current.getPuzzleYLen(); y++) {
			for (x = 0; x < this.current.getPuzzleXLen(); x++) {
				
				if(current.getInGameObject(x, y).getType().toString().equals("FLOOR")) {
					System.out.print("_");
				}
				
			}
			System.out.println();
		}
		System.out.println();
		
	}
	
	
	

	
	
}
