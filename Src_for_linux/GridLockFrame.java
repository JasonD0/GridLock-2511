import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Implements the game frame and handles the view of different screens
 *
 */

public class GridLockFrame extends JFrame implements Runnable {
	private static final int FRAME_HEIGHT = 820;
	private static final int FRAME_LENGTH = 700;
	private static final int MENU_HEIGHT = 697;
	private static final int MENU_LENGTH = 696;
	private JLabel movesMadeLabel;
	private JLabel timeLabel;
	private Game g;
	private Timer time;
	private int counter;
	private Box box;
	//private User user;
	private String difficulty;

	/*
	 * public Puzzle getPuzzle() { return this.g.getLevel().getInit(); }
	 */

	/**
	 * Constructor for GridLockFrame
	 * 
	 * @param g    back-end and front-end connector
	 */
	public GridLockFrame(Game g) {
		this.g = g;
		//this.user = new User(); // put to backend
	}

	/**
	 * Returns the user class holding a user's information
	 * 
	 * @return    user details
	 */
	public User getUser() {
		return g.getUser();//this.user;
	}

	/**
	 * Returns gold reward for current level difficulty
	 * 
	 * @return    gold reward
	 */
	public int goldReward() {
		switch (difficulty) {
		case "easy":
			return 10; 
		case "intermediate":
			return 20; 
		case "hard":
			return 40;
		}
		return 0; // retry
	}

	/**
	 * Returns difficulty of the current puzzle
	 * 
	 * @return    difficulty string 
	 */
	public String getDifficulty() {
		return this.difficulty;
	}

	// needed for message -> next level -> frame.getDifficulty() 
	/**
	 * Sets difficulty of the puzzle
	 * 
	 * @param difficulty    difficulty of the puzzle
	 */
	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	/**
	 * Returns initial state of the current puzzle
	 * 
	 * @return    initial state of puzzle
	 */
	public Puzzle getInitialState() {
		return this.g.getLevel().getInit().clonePuzzle();
	}

	/**
	 * Returns another puzzle of given difficulty
	 * @param difficulty    difficulty of next puzzle
	 * @return              puzzle of given difficulty
	 */
	public Puzzle getNextLevel(String difficulty) {
		// getLevel -> takes in param -> easy/intermediate/hard (?)
		return this.g.getLevel().getInit().clonePuzzle();
	}

	/**
	 * Changes current frame to show the puzzle
	 */
	public void newPuzzlePanel(String levelDifficulty) { 
		setPreferredSize(new Dimension(FRAME_LENGTH, FRAME_HEIGHT));
		setMaximumSize(new Dimension(FRAME_LENGTH, FRAME_HEIGHT));
		setMinimumSize(new Dimension(FRAME_LENGTH, FRAME_HEIGHT));
		setSize(new Dimension(FRAME_LENGTH, FRAME_HEIGHT));
		Box head = new Box(BoxLayout.X_AXIS);

		// adds time spent, moves made for the puzzle
		JPanel header = new JPanel();
		header.setMaximumSize(new Dimension(500, 70));
		header.setPreferredSize(new Dimension(500, 70));
		header.setMinimumSize(new Dimension(500, 70));
		header.setOpaque(false);
		header.add(initMovesLabel());
		header.add(initTimeLabel());

		head.add(header);

		// creates the interface for the puzzle of given difficulty
		GridLockGrid grid = null;
		switch (levelDifficulty) {
		case "reset":	
			grid = new GridLockGrid(getInitialState(), this);
			levelDifficulty = getDifficulty();
			break;
		case "easy": 
			grid = new GridLockGrid(getNextLevel(levelDifficulty), this);
			break;
		case "intermediate": 
			grid = new GridLockGrid(getNextLevel(levelDifficulty), this);
			break;
		case "hard": 
			grid = new GridLockGrid(getNextLevel(levelDifficulty), this);
			break;
		default: // retry
			grid = new GridLockGrid(getInitialState(), this);
		}
		setDifficulty(levelDifficulty);

		grid.setPreferredSize(new Dimension(638, 642));
		grid.setMaximumSize(new Dimension(638, 642));
		grid.setMinimumSize(new Dimension(638, 642));

		box.removeAll();
		box.add(head);
		box.add(grid);
		box.add(new GridButtonsPanel(this, grid));

		setLocationRelativeTo(null);
		setContentPane(box);
	}

	/**
	 * Creates game window
	 */
	@Override
	public void run() {
		setTitle("GridLock");
		setBackground(new Color(51, 51, 51));
		box = new Box(BoxLayout.Y_AXIS);

		// setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new
		// ImageIcon("home1.png").getImage(), new Point(0,0), "cursor"));

		setPreferredSize(new Dimension(MENU_LENGTH, MENU_HEIGHT));
		setMaximumSize(new Dimension(MENU_LENGTH, MENU_HEIGHT));
		setMinimumSize(new Dimension(MENU_LENGTH, MENU_HEIGHT));
		setSize(new Dimension(MENU_LENGTH, MENU_HEIGHT));
		setFocusable(true);
		mainMenu();
		// miniGame();

		pack();
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * Changes current frame to show mini-game
	 */
	public void miniGame() {
		box.removeAll();
		setFocusable(true);
		setPreferredSize(new Dimension(MENU_LENGTH, MENU_HEIGHT));
		setMaximumSize(new Dimension(MENU_LENGTH, MENU_HEIGHT));
		setMinimumSize(new Dimension(MENU_LENGTH, MENU_HEIGHT));
		setSize(new Dimension(MENU_LENGTH, MENU_HEIGHT));
		MiniGame miniGame = new MiniGame(this);
		box.add(miniGame);
		setLocationRelativeTo(null);
		setContentPane(box);
	}

	/**
	 * Changes current frame to show the main menu
	 */
	public void mainMenu() {
		box.removeAll();
		setPreferredSize(new Dimension(MENU_LENGTH, MENU_HEIGHT));
		setMaximumSize(new Dimension(MENU_LENGTH, MENU_HEIGHT));
		setMinimumSize(new Dimension(MENU_LENGTH, MENU_HEIGHT));
		setSize(new Dimension(MENU_LENGTH, MENU_HEIGHT));
		Menu menu = new Menu(this);
		box.add(menu);
		setLocationRelativeTo(null);
		setContentPane(box);
	}

	/**
	 * Changes current frame to show levels/difficulty choosing page
	 */
	public void levelPage() {
		box.removeAll();
		setPreferredSize(new Dimension(MENU_LENGTH, MENU_HEIGHT));
		setMaximumSize(new Dimension(MENU_LENGTH, MENU_HEIGHT));
		setMinimumSize(new Dimension(MENU_LENGTH, MENU_HEIGHT));
		setSize(new Dimension(MENU_LENGTH, MENU_HEIGHT));
		LevelManager levels = new LevelManager(this);
		box.add(levels);
		setLocationRelativeTo(null);
		setContentPane(box);
	}

	/**
	 * Changes current frame to show instructions
	 */
	public void helpPage() {
		box.removeAll();
		setPreferredSize(new Dimension(MENU_LENGTH, MENU_HEIGHT));
		setMaximumSize(new Dimension(MENU_LENGTH, MENU_HEIGHT));
		setMinimumSize(new Dimension(MENU_LENGTH, MENU_HEIGHT));
		setSize(new Dimension(MENU_LENGTH, MENU_HEIGHT));
		setTitle("GridLock");
		Help help = new Help(this);
		box.add(help);
		setLocationRelativeTo(null);
		setContentPane(box);

	}

	/**
	 * Creates timer for the current puzzle
	 * 
	 * @return JLabel    showing time spend on the current puzzle
	 */
	private JLabel initTimeLabel() {
		if (time != null)
			time.stop();
		counter = 0;
		String timer = "Time  " + counter;
		timeLabel = new JLabel(timer);
		timeLabel.setFont(new Font("Britannic Bold", Font.BOLD, 25));
		time = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				counter++;
				String timer = "Time  " + counter;
				timeLabel.setText(timer);
			}
		});
		timeLabel.setForeground(new Color(255, 153, 0));
		return timeLabel;
	}

	/**
	 * Creates JLabel to indicate number of moves made for the current puzzle
	 * 
	 * @return JLabel    showing number of moves made
	 */
	private JLabel initMovesLabel() {
		String move = "Moves  0";
		movesMadeLabel = new JLabel(move);
		movesMadeLabel.setFont(new Font("Britannic Bold", Font.BOLD, 25));
		movesMadeLabel.setForeground(new Color(255, 153, 0));
		movesMadeLabel.setPreferredSize(new Dimension(300, 70));
		return movesMadeLabel;
	}

	/**
	 * Updates number of moves made for current puzzle
	 * 
	 * @param movesMade    number of moves made by the user of the current puzzle
	 */
	public void setMovesMade(int movesMade) {
		String move = "Moves  " + String.valueOf(movesMade);
		this.movesMadeLabel.setText(move);
	}

	/**
	 * Starts timer for the current puzzle
	 */
	public void startTimer() {
		if (time != null)
			time.start();
	}

	/**
	 * Stops timer for the current puzzle
	 */
	public void stopTimer() {
		time.stop();
	}

	/**
	 * Returns time taken to complete the puzzle
	 * 
	 * @return     time taken
	 */
	public int getTime() {
		return this.counter;
	}

	/**
	 * Increases time taken for current puzzle
	 */
	public void increaseTime() {
		this.counter += 100;
		String timer = "Time  " + counter;
		timeLabel.setText(timer);
	}
}
