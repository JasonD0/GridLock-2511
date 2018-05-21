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
import javax.swing.Timer;

/**
 * Implements the game frame and handles the view of different screens
 *
 */

public class GridLockFrame extends JFrame implements Runnable{
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

	/**
	 * Constructor for GridLockFrame
	 * @param g
	 */
	public GridLockFrame(Game g) {
		this.g = g;
	}

	/*	public Puzzle getPuzzle() {	
		return this.g.getLevel().getInit();
	}*/

	/**
	 * Returns initial state of the current puzzle
	 * @return    Puzzle 
	 */
	public Puzzle getInitialState() {	
		return this.g.getLevel().getInit().clonePuzzle();
	}

	/**
	 * Creates game window
	 */
	@Override
	public void run() {
		setTitle("GridLock");
		setBackground(new Color(51,51,51));
		box = new Box(BoxLayout.Y_AXIS);

		//setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("home1.png").getImage(), new Point(0,0), "cursor"));

		mainMenu();

		pack();
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null) ;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		this.setContentPane(box);
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
		this.setContentPane(box);
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
		this.setContentPane(box);

	}

	/**
	 * Changes current frame to show the puzzle
	 */
	public void newPuzzlePanel() {	// (?) takes in string  reset/easy/intermediate/hard    gets level 
		setPreferredSize(new Dimension(FRAME_LENGTH, FRAME_HEIGHT));
		setMaximumSize(new Dimension(FRAME_LENGTH, FRAME_HEIGHT));
		setMinimumSize(new Dimension(FRAME_LENGTH, FRAME_HEIGHT));
		setSize(new Dimension(FRAME_LENGTH, FRAME_HEIGHT));
		Box head = new Box(BoxLayout.X_AXIS);

		// adds time spent and moves made for the puzzle 
		head.add(initMovesLabel());
		head.add(Box.createRigidArea(new Dimension(200,70))); // add gap
		head.add(initTimeLabel());
		head.add(Box.createRigidArea(new Dimension(100,70))); // add gap
		head.add(autoFinish());

		// creates the interface for the puzzle
		GridLockGrid grid = new GridLockGrid(getInitialState(), this);
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

	private JButton autoFinish() {
		JButton autoFinish = new JButton();
		autoFinish.setFocusable(false);
		autoFinish.setBorderPainted(false);
		ImageIcon next = new ImageIcon(getClass().getResource("autoNext_on.png"));
		// next = new ImageIcon(getClass().getResource("autoNext_off.png"));
		Image nextImage = next.getImage();
		nextImage = nextImage.getScaledInstance(45, 45, Image.SCALE_SMOOTH);
		next = new ImageIcon(nextImage);
		autoFinish.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// if have dont have free skip -> give option to buy 
			}
		});
		autoFinish.setIcon(next);
		autoFinish.setBackground(new Color(51,51,51));
		autoFinish.setOpaque(false);
		return autoFinish;
	}

	/**
	 * Creates timer for the current puzzle
	 * @return    time JPanel
	 */
	private JLabel initTimeLabel() {
		if (time != null) time.stop();
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
		timeLabel.setForeground(new Color(255,153,0));
		return timeLabel;
	}

	/**
	 * Creates JPanel to indicate number of moves made for the current puzzle
	 * @return    moves JPanel
	 */
	private JLabel initMovesLabel() {
		String move = "Moves  0";
		movesMadeLabel = new JLabel(move);
		movesMadeLabel.setFont(new Font("Britannic Bold", Font.BOLD, 25));
		movesMadeLabel.setForeground(new Color(255,153,0));
		return movesMadeLabel;
	}

	/**
	 * Updates number of moves made for current puzzle
	 * @param movesMade
	 */
	public void setMovesMade(int movesMade) {
		String move = "Moves  " + String.valueOf(movesMade);
		this.movesMadeLabel.setText(move);
	}

	/**
	 * Starts timer for the current puzzle
	 */
	public void startTimer() {
		if (time != null) time.start();
	}

	/**
	 * Stops timer for the current puzzle
	 */
	public void stopTimer() {
		time.stop();
	}

	/**
	 * Returns time taken to complete the puzzle
	 * @return    time taken
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
