import javax.swing.SwingUtilities;

public class Game {
	
	private Level level;
	private User user;
	
	public static void main(String [] args) {
		Game gridLockGame = new Game();
		gridLockGame.level = new Level();
		
		GridLockFrame gridLock = new GridLockFrame(gridLockGame);
		SwingUtilities.invokeLater(gridLock);
		
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
