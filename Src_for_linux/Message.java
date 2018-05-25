import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

/**
 * Implements the JOptionPane message after the level completed
 * 
 */

public class Message {
	private int movesMade;
	private int time;
	private ArrayList<String> animals;
	private GridLockFrame frame;
	private String animalReward;
	private boolean reward;
	private JButton home;
	private JButton retry;
	private JButton next;
	private JButton exit;

	/**
	 * Constructor for Message 
	 * @param frame         link back to the main menu
	 * @param movesMade     number of moves made to complete the puzzle
	 * @param time          time taken to complete the puzzle
	 * @param reward        collectible given to the user 
	 */
	public Message(GridLockFrame frame, int movesMade, int time, boolean reward) {
		this.frame = frame;
		this.movesMade = movesMade;
		this.time = time;		
		this.animals = new ArrayList<>();
		this.reward = reward;
		initMessage();
	}

	/**
	 * Create message after level completion
	 */
	private void initMessage() {
		UIManager.put("Panel.background", new Color(51,51,51));
		UIManager.put("OptionPane.background", new Color(51,51,51));

		// Create level completion message window
		JOptionPane pane = new JOptionPane();	
		JPanel panel = new JPanel(new BorderLayout());

		JLabel message = messageText();
		initButtons(pane);
		Object[] options = {home, retry, next, exit};

		// adds message and reward animation to the message window
		panel.add(message, BorderLayout.WEST);
		panel.add(new AnimalAnimation(animalReward), BorderLayout.EAST);
		panel.setBackground(new Color(51,51,51));
		panel.setOpaque(false);

		pane.setMessage(panel);
		pane.setOptions(options);

		// create window for the message
		JDialog dialog = pane.createDialog("Level Completed");
		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		dialog.setVisible(true);
		dialog.dispose();
	}

	/**
	 * Creates text for the level completion message
	 * @return    JLabel containing level completion message
	 */
	private JLabel messageText() {
		// Create ribbon image
		ImageIcon ribbon = new ImageIcon(("ribbon3.png"));
		Image ribbonImage = ribbon.getImage();
		ribbonImage = ribbonImage.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
		ribbon = new ImageIcon(ribbonImage);

		int gold = frame.goldReward();
		// Create level completion message
		if (reward == true) {
			animalReward = getAnimal();
			frame.getUser().saveCollectible(animalReward);
			frame.getUser().addMoney(gold);
		} else {
			animalReward = "penalty";
			gold = 0;
		}
		String second = (time == 1) ? " second" : " seconds"; 
		String text = "<html><body width='" + 200 + "'><h1>Congratulations</h1> "
						+ "<p>You got the " + animalReward + "! <p/> "
						+ "<p> You earned " + gold + "!</p><br/> "
						+ "<p>Number of Moves : " + movesMade + "</p><br />" 
						+ "<p>Time : " + time + second + "</p></html>";
		JLabel message = new JLabel(text, ribbon, SwingConstants.CENTER);

		message.setHorizontalTextPosition(JLabel.RIGHT);
		message.setVerticalTextPosition(JLabel.CENTER);
		message.setForeground(Color.WHITE);

		return message;
	}

	/**
	 * Creates buttons for the level completion message
	 * @param pane    pop-up window to contain the buttons
	 */
	private void initButtons(JOptionPane pane) {
		// Create home button
		ImageIcon homeIcon = new ImageIcon(("home1.png"));
		home = setOptionPaneButton(pane, "Home  ", homeIcon, 0);

		// Create retry button
		ImageIcon retryIcon = new ImageIcon(("retry1.png"));
		retry = setOptionPaneButton(pane, "Retry  ", retryIcon, 1);

		// create next button
		ImageIcon nextIcon = new ImageIcon(("next1.png"));
		next = setOptionPaneButton(pane, "Next  ", nextIcon, 2);

		// create exit button
		ImageIcon exitIcon = new ImageIcon(("exit1.png"));
		exit = setOptionPaneButton(pane, "Exit  ", exitIcon, 3);
	}

	/**
	 * Create level completion panel buttons functionality
	 * 	including returning to home page, resetting current level, generate next level and exiting game
	 * @param pane      pop-up window that holds the button
	 * @param text      text of the button
	 * @param icon      image for the button
	 * @param option    value determining functionality of the button
	 * @return          button
	 */
	private JButton setOptionPaneButton(JOptionPane pane, String text, ImageIcon icon, int option) {
		JButton button = new JButton(text);
		button.setFocusable(false);
		button.setBorderPainted(false);

		// add functionality to button
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pane.setValue(option);
				switch (option) {
				case 0: frame.mainMenu(); break;
				case 1: frame.newPuzzlePanel("retry"); break;
				case 2: frame.newPuzzlePanel(frame.getNextLevelDifficulty()); break;
				case 3: frame.dispose(); break;
				}
			}
		});

		// set image for button
		Image iconImage = icon.getImage();
		iconImage = iconImage.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		button.setIcon(new ImageIcon(iconImage));
		button.setForeground(Color.WHITE);
		button.setBackground(new Color(51,51,51));
		return button;
	}

	/**
	 * Get random animal for the reward
	 * @return    animal name
	 */
	private String getAnimal() {
		Random random = new Random();
		int randNum = random.nextInt(1000 -1 + 1) + 1;	// gets number from 1 to 1000    max-min+1  + min
		// int difficultyNumBonus = random ...   -> put to 1st two if -> increase chance and reduce number for others (hard)     intermediate -> reduce number for others
		
		animals.clear();
		if (randNum == 233) {
			animals.addAll(Arrays.asList("money_pig"));			
		} else if (randNum == 972) {
			animals.addAll(Arrays.asList("rubber_duck"));
		} else if (randNum >= 1 && randNum <= 500) {
			animals.addAll(Arrays.asList("cat", "dog", "pig", "rabbit", "rat", "shark"));
		} else if (randNum > 500  && randNum <= 850 ) {
			animals.addAll(Arrays.asList("beetle", "cow", "owl", "sheep", "snake", "turtle"));
		} else if (randNum > 850  && randNum <= 950 ) {
			animals.addAll(Arrays.asList("crab", "hen", "duck", "rooster"));
		} else if (randNum > 950  && randNum <= 1000) {
			animals.addAll(Arrays.asList("goat", "horse"));
		}
		Collections.shuffle(animals);
		return animals.get(0);
	}
}
