import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;

public class Message {
	private int movesMade;
	private int time;
	private GridLockFrame frame;
	
	public Message(GridLockFrame frame, int movesMade, int time) {
		this.frame = frame;
		this.movesMade = movesMade;
		this.time = time;
		initMessage();
	}
	
	private void initMessage() {

        UIManager.put("Panel.background", new Color(51,51,51));
		// Create level completion panel 
	    JOptionPane pane = new JOptionPane();	
	    ImageIcon ribbon = new ImageIcon(getClass().getResource("ribbon3.png"));
	    Image ribbonImage = ribbon.getImage();
	    ribbonImage = ribbonImage.getScaledInstance(150, 150, Image.SCALE_DEFAULT);
	    ribbon = new ImageIcon(ribbonImage);
	    
	    // Create level completion message
	    String second = (time == 1) ? " second" : " seconds"; 
	    String text = "<html><body width='" + 200 + "'><h1>Congratulations</h1> <p>Number of Moves : " + movesMade + "</p><br />" + "<p>Time : " + time + second + "</p></html>";
	    JLabel message = new JLabel(text, ribbon, SwingConstants.CENTER);
	    message.setHorizontalTextPosition(JLabel.RIGHT);
	    message.setVerticalTextPosition(JLabel.CENTER);
	    message.setForeground(Color.WHITE);
	    pane.setMessage(message);
	    pane.setMessageType(JOptionPane.PLAIN_MESSAGE);
	    
	    // Create home, retry, next puzzle, exit buttons
	    ImageIcon homeIcon = new ImageIcon(getClass().getResource("home1.png"));
		JButton home = setOptionPaneButton(pane, "Home  ", homeIcon, 0);
		home.setForeground(Color.WHITE);
		home.setBackground(new Color(51,51,51));
		
		ImageIcon retryIcon = new ImageIcon(getClass().getResource("retry1.png"));
		JButton retry = setOptionPaneButton(pane, "Retry  ", retryIcon, 1);
		retry.setForeground(Color.WHITE);
		retry.setBackground(new Color(51,51,51));
		
		ImageIcon nextIcon = new ImageIcon(getClass().getResource("next1.png"));
		JButton next = setOptionPaneButton(pane, "Next  ", nextIcon, 2);
		next.setForeground(Color.WHITE);
		next.setBackground(new Color(51,51,51));
		
		ImageIcon exitIcon = new ImageIcon(getClass().getResource("exit1.png"));
		JButton exit = setOptionPaneButton(pane, "Exit  ", exitIcon, 3);
		exit.setForeground(Color.WHITE);
		exit.setBackground(new Color(51,51,51));
		
	    Object[] options = {home, retry, next, exit};
	    pane.setOptions(options);
	    pane.setBackground(new Color(51,51,51));
	    JDialog dialog = pane.createDialog("Level Completed");
	    dialog.setBackground(new Color(51,51,51));
	    
	    
	    dialog.setVisible(true);
	}

	/**
	* Create level completion panel buttons
	* @param pane
	* @param text
	* @param icon
	* @param option
	* @return
	*/
	private JButton setOptionPaneButton(JOptionPane pane, String text, ImageIcon icon, int option) {
		JButton button = new JButton(text);
		button.setFocusable(false);
		button.setBorderPainted(false);
		button.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			pane.setValue(option);
			switch (option) {
				case 0: frame.mainMenu(); break;
				case 1: frame.newPuzzlePanel(); break;
				case 2: /*generate next level (?)*/break;
				case 3: frame.dispose(); break;
			}
		}
		});
		Image iconImage = icon.getImage();
		iconImage = iconImage.getScaledInstance(30, 30, Image.SCALE_DEFAULT);
		button.setIcon(new ImageIcon(iconImage));
		button.setBackground(new Color(238, 238, 238));
		//button.setOpaque(false);
		//button.setContentAreaFilled(false);
		return button;
	}
}
