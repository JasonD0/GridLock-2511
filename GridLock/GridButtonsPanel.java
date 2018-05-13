import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
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
	
	private void initButtonPanel() {
		setPreferredSize(new Dimension(0, 27));
		setBackground(Color.DARK_GRAY);
		Box buttonBox = new Box(BoxLayout.X_AXIS);
		
		musicButton = new JButton();
		musicButton.setFocusable(false);
		musicButton.setBorderPainted(false);
		Image musicIcon = new ImageIcon("audio_off.png").getImage();
		if (audio) {
			musicIcon = new ImageIcon("audio_on.png").getImage();
			// music plays
		}
		musicIcon = musicIcon.getScaledInstance(35, 35, Image.SCALE_DEFAULT);
		musicButton.setIcon(new ImageIcon(musicIcon));
		musicButton.setBackground(Color.LIGHT_GRAY);
		buttonBox.add(musicButton);
		
		buttonBox.add(Box.createRigidArea(new Dimension(100,75))); // add gap
		
		menuButton = new JButton();
		menuButton.setFocusable(false);
		menuButton.setBorderPainted(false);
		Image menuIcon = new ImageIcon("home.png").getImage();
		menuIcon = menuIcon.getScaledInstance(35, 35, Image.SCALE_DEFAULT);
		menuButton.setIcon(new ImageIcon(menuIcon));
		menuButton.setBackground(Color.LIGHT_GRAY);
		buttonBox.add(menuButton);
		
		buttonBox.add(Box.createRigidArea(new Dimension(100,75))); // add gap
		
		resetButton = new JButton();
		resetButton.setFocusable(false);
		resetButton.setBorderPainted(false);
		Image resetIcon = new ImageIcon("reset.png").getImage();
		resetIcon = resetIcon.getScaledInstance(35, 35, Image.SCALE_DEFAULT);
		resetButton.setIcon(new ImageIcon(resetIcon));
		resetButton.setBackground(Color.LIGHT_GRAY);
		buttonBox.add(resetButton);
		
		add(buttonBox);
		
		musicButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Image musicIcon = new ImageIcon("audio_off.png").getImage();
				if (audio) {
					audio = false;
				} else {
					audio = true;
					musicIcon = new ImageIcon("audio_on.png").getImage();
					// music plays
				}
				//musicButton.setBorder(BorderFactory.createEmptyBorder());
				musicIcon = musicIcon.getScaledInstance(35, 35, Image.SCALE_DEFAULT);
				musicButton.setIcon(new ImageIcon(musicIcon));
				musicButton.setBackground(Color.LIGHT_GRAY);
				
			}
		});
		
		menuButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// frame.menu()
			}
		});
		
		resetButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.newJPanel();	// current puzzle's initial state inserted as param
				
			}
		});
	}
}
