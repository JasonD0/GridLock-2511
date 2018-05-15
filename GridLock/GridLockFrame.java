import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GridLockFrame extends JFrame implements Runnable{
	
	private static final int FRAME_HEIGHT = 875; 
	private static final int FRAME_LENGTH = 700;
	private static final int MENU_HEIGHT = 697;
	private static final int MENU_LENGTH = 696;
	private JLabel movesMadeLabel;
	private Game g;
	private Timer time;
	private int counter;
	private Box box;
	
	public GridLockFrame(Game g) {
		this.g = g;
	}
	
	/**
	 * Sets new game 
	 * @param g
	 */
	public void setGame(Game g) {	// set new game each time new puzzle chosen 
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
		setBackground(Color.DARK_GRAY);
		box = new Box(BoxLayout.Y_AXIS);
	
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
	public void newPuzzlePanel() {
		setPreferredSize(new Dimension(FRAME_LENGTH, FRAME_HEIGHT));
		setMaximumSize(new Dimension(FRAME_LENGTH, FRAME_HEIGHT));
		setMinimumSize(new Dimension(FRAME_LENGTH, FRAME_HEIGHT));
		setSize(new Dimension(FRAME_LENGTH, FRAME_HEIGHT));
		
		Box head = new Box(BoxLayout.X_AXIS);
		head.add(Box.createVerticalGlue());
		head.add(initMovesPanel());
		head.add(Box.createRigidArea(new Dimension(200,0))); // add gap
		head.add(initTimePanel());
		
		GridLockGrid grid = new GridLockGrid(getInitialState(), this);
		grid.setPreferredSize(new Dimension(638, 642));
		grid.setMaximumSize(new Dimension(638, 642));
		grid.setMinimumSize(new Dimension(638, 642));
		grid.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		
		box.removeAll();
		box.add(head);
		box.add(grid);
		box.add(new GridButtonsPanel(this));

		setLocationRelativeTo(null);
		setContentPane(box);
	}
	
	/**
	 * Creates timer for the current puzzle
	 * @return    time JPanel
	 */
	private JPanel initTimePanel() {
		if (time != null) time.stop();
		counter = 0;
		JPanel timePanel = new JPanel();
		timePanel.setMaximumSize(new Dimension(130, 75));
		timePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		timePanel.setBackground(Color.GRAY);
		String timer = "<html> Time <br/>" + counter + "</html>";
		JLabel timeLabel = new JLabel(timer);
		timeLabel.setFont(new Font("Britannic Bold", Font.BOLD, 25));
		time = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				counter++;
				String timer = "<html> Time <br/>" + counter + "</html>";
				timeLabel.setText(timer);
			}
		});
		//time.start();
		timePanel.add(timeLabel);
		return timePanel;
	}
	
	/**
	 * Creates JPanel to indicate number of moves made for the current puzzle
	 * @return    moves JPanel
	 */
	private JPanel initMovesPanel() {
		JPanel movesMade = new JPanel();
		movesMade.setMaximumSize(new Dimension(130, 75));
		movesMade.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		movesMade.setBackground(Color.GRAY);
		String move = "<html> Moves <br/> 0</html>";
		movesMadeLabel = new JLabel(move);
		movesMadeLabel.setFont(new Font("Britannic Bold", Font.BOLD, 25));
		movesMadeLabel.setForeground(Color.BLACK);
		movesMade.add(movesMadeLabel);
		return movesMade;
	}
	
	/**
	 * Updates number of moves made for current puzzle
	 * @param movesMade
	 */
	public void setMovesMade(int movesMade) {
		String move = "<html> Moves <br/>" + String.valueOf(movesMade) + "</html>";
		this.movesMadeLabel.setText(move);
	}
	
	/**
	 * Starts timer for the current puzzle
	 */
	public void startTimer() {
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
	 * @return    time taken
	 */
	public int getTime() {
		return this.counter;
	}
}
