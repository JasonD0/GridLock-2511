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


	
	
}
