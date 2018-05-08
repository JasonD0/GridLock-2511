
public class Game {
	
	private Level level;
	private User user;
	
	public static void main(String [] args) {
		Game gridLockGame = new Game();
		gridLockGame.level = new Level();
		gridLockGame.level.buildLevel();
		gridLockGame.level.printPuzzle();
	}
}
