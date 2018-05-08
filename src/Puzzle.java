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

	public InGameObject getRedCar() {
		return redCar;
	}

	public void setRedCar(InGameObject redCar) {
		this.redCar = redCar;
	}

	public void addObjectToPuzzle(InGameObject g) {
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

	
 
	

	
	
}
