
public class Level {
	private Puzzle current; // current puzzle
	private Puzzle init; // the starting puzzle can be use for reset or restart
	
	
	/**
	 * Constructor for the level
	 */
	public Level() {
		int axisX = 6; 
		int axisY = 6;
		this.current = new Puzzle(axisX,axisY);
		this.init = new Puzzle(axisX,axisY);
		
	}
	/**
	 * @pre a Puzzle exist
	 * @post got the initial Puzzle
	 * @return Puzzle
	 */
	
	public Puzzle getInit() {
		return init;
	}

	/**
	 * @pre a proper level must have been created 
	 * @post new Puzzle is set for initial
	 * @param init
	 */
	public void setInit(Puzzle init) {
		this.init = init;
	}
	/**
	 * @pre a Puzzle exist
	 * @post got the current Puzzle
	 * @return Puzzle
	 */
	public Puzzle getCurrent() {
		return current;
	}
	/**
	 * @pre a proper level must have been created
	 * @post a new Puzzle is set for current
	 * @param current
	 */
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
