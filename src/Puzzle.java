import java.util.ArrayList;
import java.util.List;

public class Puzzle {
	private int[][] gridState;
	private ArrayList<Car> carList;
	//private InGameObject redCar;
	
	/**
	 * Constructor for the puzzle
	 * @param sizeX
	 * @param sizeY
	 */
	public Puzzle(int sizeX, int sizeY) {
		this.gridState = new int[sizeY][sizeX];
		this.carList = new ArrayList<Car>();
		
	}
	
	/**
	 * @pre an existing 2D array 
	 * @post retrieve 2D array of int
	 * @return int[][]
	 */
	public int[][] getGridState() {
		return gridState;
	}
	/**
	 * @pre a value or null can be there
	 * @post new 2D array have been set
	 */
	public void setGridState(int[][] gridState) {
		this.gridState = gridState;
	}
	/**
	 * @pre an existing array list of car 
	 * @post retrieve the arrayList
	 * @return ArrayList<Car>
	 */
	public ArrayList<Car> getCarList() {
		return carList;
	}
	/**
	 * @pre a value or null can be there
	 * @post new arrayList of car have been set
	 */
	public void setCarList(ArrayList<Car> carList) {
		this.carList = carList;
	}
	/**
	 * @pre there must be an existing arrayList of car
	 * @post a car is added into the car List
	 */
	public void addCars(Car newCar) {
		if(!carList.contains(newCar)) {
			carList.add(newCar);
		}
		
	}
	/**
	 * @pre array list of car existed
	 * @post a new array list of car 
	 * @return
	 */
	public ArrayList<Car> cloningCar() {
		ArrayList<Car> clone = new ArrayList<Car>(carList);
		return clone;
	}
	/**
	 * @pre an existing and proper Puzzle is calling this function
	 * @post a new Puzzle with same details as the old one
	 * @return a new Puzzle 
	 */
	public Puzzle clonePuzzle() {
		Puzzle p = new Puzzle(6,6);
		p.carList = new ArrayList<Car>(carList);
		p.gridState = new int[][] {
			{ 1,  1, -1, -1, -1,  8},
			{ 2, -1, -1,  5, -1,  8},
			{ 2,  4,  4,  5, -1,  8},
			{ 2, -1, -1,  5, -1, -1},
			{ 3, -1, -1, -1,  7,  7},
			{ 3, -1,  6,  6,  6, -1}
		};
		return p;
	}
	
	/**
	 * @pre a Puzzle must exist
	 * @post Puzzle with a 2D array information
	 */
	public void initGridState() {
		gridState = new int[][] {
			{ 1,  1, -1, -1, -1,  8},
			{ 2, -1, -1,  5, -1,  8},
			{ 2,  4,  4,  5, -1,  8},
			{ 2, -1, -1,  5, -1, -1},
			{ 3, -1, -1, -1,  7,  7},
			{ 3, -1,  6,  6,  6, -1}
		};
		
		// test
		Car newOne = new Car(0, 0, 200, 100, "h", 2, 1, false);
		carList.add(newOne);     
		carList.add(new Car(0, 0 + 100, 100, 300, "v", 3, 2, false));
		carList.add(new Car(0, 0 + 400, 100, 200, "v", 2, 3, false));
		carList.add(new Car(0 + 100, 0 + 200, 200, 100, "h", 2, 4, true));
		carList.add(new Car(0 + 300, 0 + 100, 100, 300, "v", 3, 5, false));
		carList.add(new Car(0 + 200, 0 + 500, 300, 100, "h", 3, 6, false));
		carList.add(new Car(0 + 400, 0 + 400, 200, 100, "h", 2, 7, false));
		carList.add(new Car(0 + 500, 0, 100, 300, "v", 3, 8, false));
		//carList.add(new Car(0 + 300, 0, 300, 100, "h", 3, 9, false));
	
	}

	
	
}
