import java.util.ArrayList;

public class Puzzle {
	private InGameObject[][] objectArray;
	private InGameObject exit;
	private InGameObject redCar;
	
	public Puzzle(int sizeX, int sizeY) {
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
		if(t.getDirection().equals(Direction.DOWN)) {
			objectArray[t.getY()][t.getX()] = t;
			objectArray[t.getY()+1][t.getX()] = t;
			objectArray[t.getY()+2][t.getX()] = t;
		}
		if(t.getDirection().equals(Direction.UP)) {
			objectArray[t.getY()][t.getX()] = t;
			objectArray[t.getY()-1][t.getX()] = t;
			objectArray[t.getY()-2][t.getX()] = t;
		}
		if(t.getDirection().equals(Direction.RIGHT)) {
			objectArray[t.getY()][t.getX()] = t;
			objectArray[t.getY()][t.getX()-1] = t;
			objectArray[t.getY()][t.getX()-2] = t;
		}
		if(t.getDirection().equals(Direction.LEFT)) {
			objectArray[t.getY()][t.getX()] = t;
			objectArray[t.getY()][t.getX()+1] = t;
			objectArray[t.getY()][t.getX()+2] = t;
		}

	}
	
	public void addCar(InGameObject c) {
		if(c.getDirection().equals(Direction.DOWN)) {
			objectArray[c.getY()][c.getX()] = c;
			objectArray[c.getY()+1][c.getX()] = c;
	
		}
		if(c.getDirection().equals(Direction.UP)) {
			objectArray[c.getY()][c.getX()] = c;
			objectArray[c.getY()-1][c.getX()] = c;
			
		}
		if(c.getDirection().equals(Direction.RIGHT)) {
			objectArray[c.getY()][c.getX()] = c;
			objectArray[c.getY()][c.getX()-1] = c;
		
		}
		if(c.getDirection().equals(Direction.LEFT)) {
			objectArray[c.getY()][c.getX()] = c;
			objectArray[c.getY()][c.getX()+1] = c;
		
		}

	}
	

	
 
	

	
	
}
