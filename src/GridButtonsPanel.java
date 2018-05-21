import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

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
	
	/**
	 * Constructor for GridButtonsPanel
	 * @param frame
	 * @param grid
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
		Image deleteIcon = new ImageIcon(getClass().getResource("edit.png")).getImage();
		deleteIcon = deleteIcon.getScaledInstance(35, 35, Image.SCALE_DEFAULT);
		deleteButton.setIcon(new ImageIcon(deleteIcon));
		deleteButton.setContentAreaFilled(false);
		
		// set action when button clicked
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (grid.checkHelp() == false) {
					grid.setHelp(true);
					grid.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("delete1.png").getImage(), new Point(0,0), "cursor"));
				} else {
					grid.setHelp(false);
					grid.setCursor(null);
					
				}
			}
		});
		return deleteButton; 
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
		Image musicIcon = new ImageIcon(getClass().getResource("audio_off1.png")).getImage();
		if (audio) {
			musicIcon = new ImageIcon(getClass().getResource("audio_on1.png")).getImage();
			// music plays
		} else {
			// music stops
		}
		musicIcon = musicIcon.getScaledInstance(35, 35, Image.SCALE_DEFAULT);
		musicButton.setIcon(new ImageIcon(musicIcon));
		musicButton.setContentAreaFilled(false);
		
		// set action when button clicked
		musicButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Image musicIcon = new ImageIcon(getClass().getResource("audio_off1.png")).getImage();
				// stop music
				if (audio) {
					audio = false;
					// stop music
				// play music
				} else {
					audio = true;
					// play music
					musicIcon = new ImageIcon(getClass().getResource("audio_on1.png")).getImage();
				}
				musicIcon = musicIcon.getScaledInstance(35, 35, Image.SCALE_DEFAULT);
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
		Image menuIcon = new ImageIcon(getClass().getResource("home1.png")).getImage();
		menuIcon = menuIcon.getScaledInstance(35, 35, Image.SCALE_DEFAULT);
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
		Image resetIcon = new ImageIcon(getClass().getResource("reset1.png")).getImage();
		resetIcon = resetIcon.getScaledInstance(35, 35, Image.SCALE_DEFAULT);
		resetButton.setIcon(new ImageIcon(resetIcon));
		resetButton.setContentAreaFilled(false);
		
		// set action when button clicked
		resetButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.newPuzzlePanel();	
				frame.stopTimer();
			}
		});
		return resetButton;
	}
}
