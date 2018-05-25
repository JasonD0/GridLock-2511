import java.util.ArrayList;
import java.util.List;

public class Puzzle {
	private int[][] gridState;
	private ArrayList<Car> carList;
	//private InGameObject redCar;
	
	public Puzzle(int sizeX, int sizeY) {
		this.gridState = new int[sizeY][sizeX];
		this.carList = new ArrayList<Car>();
		
	}
	
	public int[][] getGridState() {
		return gridState;
	}
	public void setGridState(int[][] gridState) {
		this.gridState = gridState;
	}
	public ArrayList<Car> getCarList() {
		return carList;
	}
	public void setCarList(ArrayList<Car> carList) {
		this.carList = carList;
	}
	
	public void addCars(Car newCar) {
		if(!carList.contains(newCar)) {
			carList.add(newCar);
		}
		
	}
	
	public ArrayList<Car> cloningCar() {
		ArrayList<Car> clone = new ArrayList<Car>(carList);
		return clone;
	}
	
	public Puzzle clonePuzzle() {
		Puzzle p = new Puzzle(6,6);
		p.carList = new ArrayList<Car>(carList);
		if (difficulty.equals("easy")) {
			p.gridState = new int[][] {
				{ 1,  1, -1, -1, -1,  8},
				{ 2, -1, -1,  5, -1,  8},
				{ 2,  4,  4,  5, -1,  8},
				{ 2, -1, -1,  5, -1, -1},
				{ 3, -1, -1, -1,  7,  7},
				{ 3, -1,  6,  6,  6, -1}
			};
		}
		if (difficulty.equals("intermediate")) {
			p.gridState = new int[][] {
				{ 1, -1, -1, 10, 10, 10},
				{ 1, -1, -1, 11, -1,  9},
				{ 2,  2, -1, 11,  8,  9},
				{ 3,  3,  3,  5,  8,  9},
				{-1, -1,  5, -1,  7,  7},
				{ 4,  4,  5,  6,  6, -1}
			};
		}
		if (difficulty.equals("hard")) {
			p.gridState = new int[][] {
				{-1, -1, -1, -1, -1, -1},
				{-1, -1, -1, -1, -1, -1},
				{-1,  3,  3,  5, -1, -1},
				{-1,  2,  2,  5, -1,  4},
				{-1,  1, -1,  5, -1,  4},
				{-1,  1,  6,  6, -1,  4}
			};
			

		}
	
		return p;
	}
	public void initGridState() {
		if (difficulty.equals("easy")) {
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
		}
		if (difficulty.equals("intermediate")) {
			gridState = new int[][] {
				{ 1, -1, -1, 10, 10, 10},
				{ 1, -1, -1, 11, -1,  9},
				{ 2,  2, -1, 11,  8,  9},
				{ 3,  3,  3,  5,  8,  9},
				{-1, -1,  5, -1,  7,  7},
				{ 4,  4,  5,  6,  6, -1}
			};
			
			// test
			Car newOne = new Car(0, 0, 100, 200, "v", 2, 1, false);
			carList.add(newOne);     
			carList.add(new Car(0, 0 + 200, 200, 100, "h", 2, 2, true));
			carList.add(new Car(0, 0 + 300, 300, 100, "h", 3, 3, false));
			carList.add(new Car(0, 0 + 500, 200, 100, "h", 2, 4, false));
			carList.add(new Car(0 + 200, 0 + 400, 100, 200, "v", 2, 5, false));
			carList.add(new Car(0 + 300, 0 + 500, 200, 100, "h", 2, 6, false));
			carList.add(new Car(0 + 400, 0 + 400, 200, 100, "h", 2, 7, false));
			carList.add(new Car(0 + 400, 0 + 200, 100, 300, "v", 2, 8, false));
			carList.add(new Car(0 + 100, 0 + 500, 100, 300, "v", 3, 9, false));
			carList.add(new Car(0, 0 + 300, 100, 300, "h", 3, 10, false));
			carList.add(new Car(0 + 100, 0 + 300, 100, 300, "v", 2, 11, false));
		}
		if (difficulty.equals("hard")) {
			gridState = new int[][] {
				{-1, -1, -1, -1, -1, -1},
				{-1, -1, -1, -1, -1, -1},
				{-1,  3,  3,  5, -1, -1},
				{-1,  2,  2,  5, -1,  4},
				{-1,  1, -1,  5, -1,  4},
				{-1,  1,  6,  6, -1,  4}
			};
			
			// test
			Car newOne = new Car(0 + 400, 0 + 100, 100, 200, "v", 2, 1, false);
			carList.add(newOne);     
			carList.add(new Car(0 + 300, 0 + 100, 200, 100, "h", 2, 2, false));
			carList.add(new Car(0 + 100, 0 + 200, 200, 100, "h", 2, 3, true));
			carList.add(new Car(0 + 500, 0 + 300, 200, 100, "v", 3, 4, false));
			carList.add(new Car(0 + 300, 0 + 200, 100, 300, "v", 3, 5, false));
			carList.add(new Car(0 + 200, 0 + 500, 200, 100, "h", 2, 6, false));

		}
	
	}
	
}
