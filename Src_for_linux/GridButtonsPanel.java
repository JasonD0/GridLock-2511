import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

/**
 * Implements the puzzle buttons  
 *
 */

public class GridButtonsPanel extends JPanel{
	private GridLockFrame frame; 
	private GridLockGrid grid;
	private static boolean audio = true;
	private JButton musicButton;
	private JButton menuButton;
	private JButton resetButton;
	private JButton deleteButton;
	private String buyingCost;

	/**
	 * Constructor for GridButtonsPanel
	 * @param frame    link back to the main menu and puzzle's time and moves updater
	 * @param grid     the window of the puzzle gui
	 */
	public GridButtonsPanel(GridLockFrame frame, GridLockGrid grid) {
		this.frame = frame;
		this.grid = grid;
		initButtonPanel();
	}

	/**
	 * Creates JPanel with music, menu, reset and delete button
	 */
	private void initButtonPanel() {
		setPreferredSize(new Dimension(0, 0));
		setBackground(new Color(51,51,51));
		Box buttonBox = new Box(BoxLayout.X_AXIS);

		// add the buttons to the puzzle frame
		buttonBox.add(newMusicButton());
		buttonBox.add(Box.createRigidArea(new Dimension(100,50))); // add gap
		buttonBox.add(newMenuButton());
		buttonBox.add(Box.createRigidArea(new Dimension(100,50))); // add gap
		buttonBox.add(newResetButton());
		buttonBox.add(Box.createRigidArea(new Dimension(100,50))); // add gap
		buttonBox.add(newDeleteButton());
		add(buttonBox);
	}

	/**
	 * Creates button that deletes one car chosen
	 * @return	  button
	 */
	private JButton newDeleteButton() {
		// create button
		deleteButton = new JButton();
		deleteButton.setFocusable(false);
		deleteButton.setBorderPainted(false);
		deleteButton.setToolTipText("Delete Car");

		// set button image
		Image deleteIcon = new ImageIcon(("edit.png")).getImage();
		deleteIcon = deleteIcon.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
		deleteButton.setIcon(new ImageIcon(deleteIcon));
		deleteButton.setContentAreaFilled(false);

		// set action when button clicked
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				buyOptionPane();
			}
		});
		return deleteButton; 
	}
	
	/**
	 * Creates pop-up shop allowing user to buy a free delete
	 */
	private void buyOptionPane() {
		UIManager.put("Panel.background", new Color(51,51,51));
		UIManager.put("OptionPane.background", new Color(51,51,51));
		UIManager.put("OptionPane.minimumSize", new Dimension(300, 90));
		// Create shop window
		JOptionPane pane = new JOptionPane();	
		JPanel panel = new JPanel(new BorderLayout());
		JDialog dialog = pane.createDialog("Buy Penalty-Free Delete Car");

		// Tells user amount of gold they have
		JLabel message = new JLabel("You have " + frame.getUser().getMoney() + " gold.", SwingConstants.CENTER);
		message.setFont(new Font(null, Font.BOLD, 20));
		message.setForeground(Color.WHITE);

		// create buy button
		buyingCost = "Penalty";
		JButton buy = new JButton();
		if (frame.getUser().getFreeDeletes() > 0) buyingCost = "$0";
		else if (frame.getUser().getMoney() >= 150) buyingCost = "$150"; 
		buy.setText("Cost " + buyingCost);
		

		buy.setFocusable(false);
		buy.setBorderPainted(false);
		buy.setOpaque(false);
		buy.setBackground(new Color(51,51,51));
		buy.setForeground(Color.WHITE);
		
		Image buyImage = new ImageIcon(("buy.png")).getImage();
		if (frame.getUser().getMoney() < 150 && frame.getUser().getFreeDeletes() <= 0) {
			buyImage = new ImageIcon(("buyFalse.png")).getImage();
			buy.setContentAreaFilled(false);
		}
		buyImage = buyImage.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
		buy.setIcon(new ImageIcon(buyImage));
		
		buy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// allow user to buy if they have sufficient funds/have free deletes
				if (frame.getUser().getMoney() >= 150 || frame.getUser().getFreeDeletes() > 0) {
					if (buyingCost.equals("$150")) {
						frame.getUser().setFreeDelete(1);
						frame.getUser().withdrawMoney(150);
					}
					frame.getUser().setFreeDeleteUsed(true);
				} else {
					frame.getUser().setFreeDeleteUsed(false);
				}
				// sets cursor image 
				if (grid.checkHelp() == false) {
					grid.setHelp(true);
					Image cursorImage = new ImageIcon(("delete1.png")).getImage();
					cursorImage = cursorImage.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
					grid.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(cursorImage, new Point(0,0), "cursor"));
				} else {
					grid.setHelp(false);
					grid.setCursor(null);
				}
				JOptionPane.getRootFrame().dispose();
				dialog.dispose();
			}
		});
	
		// Create go back button 
		JButton goBack = new JButton("Go Back  "); 
		goBack.setFocusable(false);
		goBack.setBorderPainted(false);
		goBack.setOpaque(false);
		goBack.setBackground(new Color(51,51,51));
		goBack.setForeground(Color.WHITE);
		
		Image goBackImage = new ImageIcon(("return.png")).getImage();
		goBackImage = goBackImage.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
		goBack.setIcon(new ImageIcon(goBackImage));
		
		// redirects user back to the current puzzle
		goBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.getRootFrame().dispose();
				dialog.dispose();
			}
		});
		
		Object options[] = {buy, goBack};
		
		// adds message to the message window
		panel.add(message, BorderLayout.CENTER);
		panel.setBackground(new Color(51,51,51));
		panel.setOpaque(false);
		pane.setMessage(panel);
		pane.setOptions(options);

		dialog.setVisible(true);
	}

	/**
	 * Creates button that switches music on/off 
	 * @return   button
	 */
	private JButton newMusicButton() {
		// create button
		musicButton = new JButton();
		musicButton.setFocusable(false);
		musicButton.setBorderPainted(false);
		musicButton.setToolTipText("Audio On/Off");

		// set button image
		Image musicIcon = new ImageIcon(("audio_off1.png")).getImage();
		if (audio) {
			musicIcon = new ImageIcon(("audio_on1.png")).getImage();
			// music plays
		} else {
			// music stops
		}
		musicIcon = musicIcon.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
		musicButton.setIcon(new ImageIcon(musicIcon));
		musicButton.setContentAreaFilled(false);

		// set action when button clicked
		musicButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Image musicIcon = new ImageIcon(("audio_off1.png")).getImage();
				// stop music
				if (audio) {
					audio = false;
					// stop music
					// play music
				} else {
					audio = true;
					// play music
					musicIcon = new ImageIcon(("audio_on1.png")).getImage();
				}
				musicIcon = musicIcon.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
				musicButton.setIcon(new ImageIcon(musicIcon));
				musicButton.setBackground(Color.LIGHT_GRAY);

			}
		});
		return musicButton;
	}

	/**
	 * Creates button that returns user to main menu
	 * @return    button
	 */
	private JButton newMenuButton() {
		// create button
		menuButton = new JButton();
		menuButton.setFocusable(false);
		menuButton.setBorderPainted(false);
		menuButton.setToolTipText("Return Home");

		// set button image
		Image menuIcon = new ImageIcon(("home1.png")).getImage();
		menuIcon = menuIcon.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
		menuButton.setIcon(new ImageIcon(menuIcon));
		menuButton.setContentAreaFilled(false);

		// set action when button clicked
		menuButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.mainMenu();
			}
		});
		return menuButton;
	}

	/**
	 * Creates button that resets current puzzle state to the original puzzle
	 * @return    button
	 */
	private JButton newResetButton() {
		// create button
		resetButton = new JButton();
		resetButton.setFocusable(false);
		resetButton.setBorderPainted(false);
		resetButton.setToolTipText("Restart Puzzle");

		// set button image
		Image resetIcon = new ImageIcon(("reset1.png")).getImage();
		resetIcon = resetIcon.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
		resetButton.setIcon(new ImageIcon(resetIcon));
		resetButton.setContentAreaFilled(false);

		// set action when button clicked
		resetButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!frame.getDifficulty().equals("retry")) frame.getUser().setFreeDeleteUsed(true);
				frame.newPuzzlePanel("reset");	
				frame.stopTimer();
			}
		});
		return resetButton;
	}
}
