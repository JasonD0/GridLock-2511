
public class Level {
	private Puzzle current; // current puzzle
	private Puzzle init; // the starting puzzle can be use for reset or restart
	
	public Level() {
		int axisX = 6; 
		int axisY = 6;
		this.current = new Puzzle(axisX,axisY);
		this.init = new Puzzle(axisX,axisY);
		
	}
	
	public Puzzle getInit() {
		return init;
	}

	public void setInit(Puzzle init) {
		this.init = init;
	}

	public Puzzle getCurrent() {
		return current;
	}

	public void setCurrent(Puzzle current) {
		this.current = current;
	}
	
	public void createBasicState() {
		int[][] gridState = new int[][] {
			{ 1,  1, -1, -1, -1,  8},
			{ 2, -1, -1,  5, -1,  8},
			{ 2,  4,  4,  5, -1,  8},
			{ 2, -1, -1,  5, -1, -1},
			{ 3, -1, -1, -1,  7,  7},
			{ 3, -1,  6,  6,  6, -1}
		};
		init.setGridState(gridState);
		
	}

	
	
	

	
	
}
