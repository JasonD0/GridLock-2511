import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class GridButtonsPanel extends JPanel{
	private GridLockFrame frame; 
	private static boolean audio = true;
	private JButton musicButton;
	private JButton menuButton;
	private JButton resetButton;
	
	public GridButtonsPanel(GridLockFrame frame) {
		this.frame = frame;
		initButtonPanel();
	}
	
	/**
	 * Creates JPanel with music, menu and reset button
	 */
	private void initButtonPanel() {
		setPreferredSize(new Dimension(0, 0));
		setBackground(new Color(51,51,51));
		Box buttonBox = new Box(BoxLayout.X_AXIS);
		buttonBox.add(newMusicButton());
		buttonBox.add(Box.createRigidArea(new Dimension(100,50))); // add gap
		buttonBox.add(newMenuButton());
		buttonBox.add(Box.createRigidArea(new Dimension(100,50))); // add gap
		buttonBox.add(newResetButton());
		add(buttonBox);
	}
	
	/**
	 * Creates button that switches music on/off 
	 * @return   button 
	 */
	private JButton newMusicButton() {
		musicButton = new JButton();
		musicButton.setFocusable(false);
		musicButton.setBorderPainted(false);
		Image musicIcon = new ImageIcon(getClass().getResource("audio_off1.png")).getImage();
		if (audio) {
			musicIcon = new ImageIcon(getClass().getResource("audio_on1.png")).getImage();
			// music plays
		} else {
			// music stops
		}
		musicIcon = musicIcon.getScaledInstance(35, 35, Image.SCALE_DEFAULT);
		musicButton.setIcon(new ImageIcon(musicIcon));
		//musicButton.setBackground(new Color(255,153,0));
		musicButton.setContentAreaFilled(false);
		musicButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Image musicIcon = new ImageIcon(getClass().getResource("audio_off1.png")).getImage();
				if (audio) {
					audio = false;
					// music stops
				} else {
					audio = true;
					musicIcon = new ImageIcon(getClass().getResource("audio_on1.png")).getImage();
					// music plays
				}
				//musicButton.setBorder(BorderFactory.createEmptyBorder());
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
		menuButton = new JButton();
		menuButton.setFocusable(false);
		menuButton.setBorderPainted(false);
		Image menuIcon = new ImageIcon(getClass().getResource("home1.png")).getImage();
		menuIcon = menuIcon.getScaledInstance(35, 35, Image.SCALE_DEFAULT);
		menuButton.setIcon(new ImageIcon(menuIcon));
		menuButton.setContentAreaFilled(false);
//		menuButton.setBackground(Color.LIGHT_GRAY);

		menuButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.mainMenu();
			}
		});
		return menuButton;
	}
	
	/**
	 * Resets current puzzle state to the original puzzle
	 * @return    button
	 */
	private JButton newResetButton() {
		resetButton = new JButton();
		resetButton.setFocusable(false);
		resetButton.setBorderPainted(false);
		Image resetIcon = new ImageIcon(getClass().getResource("reset1.png")).getImage();
		resetIcon = resetIcon.getScaledInstance(35, 35, Image.SCALE_DEFAULT);
		resetButton.setIcon(new ImageIcon(resetIcon));
		resetButton.setContentAreaFilled(false);
		//resetButton.setBackground(Color.LIGHT_GRAY);
		
		resetButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.newPuzzlePanel();	// current puzzle's initial state inserted as param
				frame.stopTimer();
			}
		});
		return resetButton;
	}
}
