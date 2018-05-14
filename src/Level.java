
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
				current.addFloorToPuzzle(floor);
			}
		}
		
		InGameObject exit = new InGameObject(5,2,Type.EXIT);
		current.setExit(exit);
		current.addExitToPuzzle(exit);
		InGameObject redCar = new InGameObject(1,2,Type.REDCAR, Direction.RIGHT);
		current.setRedCar(redCar);
		current.addRedCartoPuzzle(redCar);
		InGameObject truck1 = new InGameObject(3,2,Type.TRUCK, Direction.UP);
		current.addTruck(truck1);
		InGameObject truck2 = new InGameObject(2,3,Type.TRUCK, Direction.DOWN);
		current.addTruck(truck2);
		InGameObject car1 = new InGameObject(3,3,Type.CAR, Direction.LEFT);
		current.addCar(car1);
		InGameObject car2 = new InGameObject(2,1,Type.CAR, Direction.RIGHT);
		current.addCar(car2);
		
		
		
		
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
				if(current.getInGameObject(x, y).getType().toString().equals("EXIT")) {
					System.out.print("E");
				}
				if(current.getInGameObject(x, y).getType().toString().equals("REDCAR")) {
					System.out.print("R");
				}
				if(current.getInGameObject(x, y).getType().toString().equals("CAR")) {
					System.out.print("C");
				}
				if(current.getInGameObject(x, y).getType().toString().equals("TRUCK")) {
					System.out.print("T");
				}
				
				
				
			}
			System.out.println();
		}
		System.out.println();
		
	}
	
	
	

	
	
}
