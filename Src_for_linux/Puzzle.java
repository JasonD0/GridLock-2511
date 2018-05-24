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
	
/*	public Puzzle(int sizeX, int sizeY) {
		this.objectArray = new InGameObject[sizeY][sizeX];
		
	}

	public InGameObject[][] getObjectArray() {
		return objectArray;
	}

	public void setObjectArray(InGameObject[][] objectArray) {
		this.objectArray = objectArray;
	}
	
	public int getPuzzleYLen() {
		return objectArray.length;
	}
	
	public int getPuzzleXLen() {
		return objectArray.length;
	}

	public InGameObject getExit() {
		return exit;
	}

	public void setExit(InGameObject exit) {
		this.exit = exit;
	}
	
	public void addExitToPuzzle(InGameObject e) {
		objectArray[e.getY()][e.getX()] = e;
		objectArray[e.getY()][e.getX()-1] = e;
	}
	

	public InGameObject getRedCar() {
		return redCar;
	}

	public void setRedCar(InGameObject redCar) {
		this.redCar = redCar;
	}
	
	// the head of the car is being added first
	// the body follows
	public void addRedCartoPuzzle(InGameObject redCar) {
		objectArray[redCar.getY()][redCar.getX()] = redCar;
		objectArray[redCar.getY()][redCar.getX()-1] = redCar;
	}

	public void addFloorToPuzzle(InGameObject g) {
		objectArray[g.getY()][g.getX()] = g;
	}
	
	public InGameObject getInGameObject(int x ,int y) {
		return objectArray[y][x];
	}
	
	public boolean checkAvailability(int x, int y) {
		Type t = objectArray[y][x].getType();
		if (t.equals(Type.FLOOR) || t.equals(Type.EXIT)) {
			return true;
		} else {
			return false; 
		}
	}
	
	public void addTruck(InGameObject t) {

		if(t.getDirection().equals(Direction.VERTICAL)) {
			objectArray[t.getY()][t.getX()] = t;
			objectArray[t.getY()-1][t.getX()] = t;
			objectArray[t.getY()-2][t.getX()] = t;
		}
		if(t.getDirection().equals(Direction.HORIZONTAL)) {
			objectArray[t.getY()][t.getX()] = t;
			objectArray[t.getY()][t.getX()-1] = t;
			objectArray[t.getY()][t.getX()-2] = t;
		}

	}
	
	public void addCar(InGameObject c) {

		if(c.getDirection().equals(Direction.VERTICAL)) {
			objectArray[c.getY()][c.getX()] = c;
			objectArray[c.getY()-1][c.getX()] = c;
			
		}
		if(c.getDirection().equals(Direction.HORIZONTAL)) {
			objectArray[c.getY()][c.getX()] = c;
			objectArray[c.getY()][c.getX()-1] = c;
		
		}


	} */
	
	
	

	
 
	

	
	
}
