import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.UIManager;

/**
 * Implements the main part of the mini game  
 * Handles user movement and shooting
 */

public class MiniGame extends JPanel implements KeyListener, ActionListener {
	private Timer actionTimer = new Timer(10, this);	// activates action performed
	private Timer shootTimer;	
	private Timer gameLimit;
	private int x, y, velY;
	private ArrayList<Cog> cogs;
	private MoneyPig pig;
	private int gold;
	private JPanel headerPanel;
	private JLabel goldMade;
	private JLabel time;
	private int timeCounter;
	private GridLockFrame frame;
	private Image car;
	
	/**
	 * Constructor for MiniGame
	 * @param frame    link back to the main menu
	 */
	public MiniGame(GridLockFrame frame) {
		this.x = 20;
		this.y = 60;
		this.velY = 0;
		this.gold = 0;
		this.timeCounter = 60;
		this.frame = frame;
		this.pig = new MoneyPig(490);
		setFocusTraversalKeysEnabled(false);
		setFocusable(true);
		setVisible(true);
		requestFocus();
		addKeyListener(this);
		initMiniGame();
		showInstructions();
	}
	
	/**
	 * Shows instructions for the mini game
	 */
	public void showInstructions() {
		UIManager.put("Panel.background", new Color(51,51,51));
		UIManager.put("OptionPane.background", new Color(51,51,51));

		String instructions = "<html><p>Use up/down arrow keys to move your car</p>  <p>Press space to shoot projectile</p>  <p>Each projectile hit gives 2 Gold</p></html>";
		
		JOptionPane pane = new JOptionPane();	
		pane.setPreferredSize(new Dimension(450, 150));
		JDialog dialog = pane.createDialog("Mini Game Instructions");
		JLabel message = new JLabel(instructions, SwingConstants.CENTER);
		message.setFont(new Font(null, Font.BOLD, 20));
		message.setForeground(Color.WHITE);
		pane.setMessage(message);
		dialog.setVisible(true);
	}
	
	/**
	 * Sets up time countdown, gold earned and the shooting of cogs
	 */
	private void initMiniGame() {
		car = new ImageIcon(("miniGame_car.png")).getImage();
		car = car.getScaledInstance(50, 30, Image.SCALE_SMOOTH);
		
		this.cogs = new ArrayList<>();
		setLayout(new BorderLayout());
		headerPanel = new JPanel();
		headerPanel.setOpaque(false);
		
		// create time countdown for the mini game
		time = new JLabel("Time Remaining: " + timeCounter);
		time.setFont(new Font("Britannic Bold", Font.BOLD, 30));
		time.setForeground(new Color(255,153,0));
		gameLimit = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				timeCounter--;
				time.setText("Time Remaining: " + timeCounter);
				if (timeCounter == 0) {
					gameLimit.stop();
					endMiniGame();
				}
			}
		});
		
		// create gold counter for the mini game
		goldMade = new JLabel(" Gold Made: " + gold);
		goldMade.setPreferredSize(new Dimension(300, 40));
		goldMade.setFont(new Font("Britannic Bold", Font.BOLD, 30));
		goldMade.setForeground(new Color(255,153,0));

		headerPanel.add(goldMade);
		headerPanel.add(time);
		headerPanel.add(Box.createRigidArea(new Dimension(100, 0)));	// add gap
		add(headerPanel, BorderLayout.NORTH);
		setBackground(new Color(51,51,51));
		add(pig, BorderLayout.EAST);
		
		// activator for repainting 
		actionTimer.start();
		
		// create delay after each shot 
		shootTimer = new Timer(50, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Cog cog = new Cog(x + 50, y - 3);
				cogs.add(cog);
				shootTimer.stop();
			}
		});
	}

	/**
	 * Start shooting
	 */
	private void shoot() {
		shootTimer.start();
	}
	
	/**
	 * Create pop-up message indicating end of mini game
	 * Shows gold earned 
	 */
	private void endMiniGame() {
		frame.getUser().addMoney(gold);
		UIManager.put("Panel.background", new Color(51,51,51));
		UIManager.put("OptionPane.background", new Color(51,51,51));

		// Create level completion message window
		JOptionPane pane = new JOptionPane();	
		JPanel panel = new JPanel(new BorderLayout());
		JDialog dialog = pane.createDialog("Mini Game Completed");

		// Create text for the window
		JLabel message = new JLabel("You won " + gold + " gold!", SwingConstants.CENTER);
		message.setFont(new Font(null, Font.BOLD, 20));
		message.setForeground(Color.WHITE);

		// create home button 
		JButton home = new JButton("Home  "); 
		home.setFocusable(false);
		home.setBorderPainted(false);
		home.setOpaque(false);
		home.setBackground(new Color(51,51,51));
		home.setForeground(Color.WHITE);
		Image homeImage = new ImageIcon(("home1.png")).getImage();
		homeImage = homeImage.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
		home.setIcon(new ImageIcon(homeImage));
		home.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.getRootFrame().dispose();
				dialog.dispose();
				frame.mainMenu();
			}
		});
		
		Object options[] = {home};
		
		// adds message and home button to the message window
		panel.add(message, BorderLayout.CENTER);
		panel.setBackground(new Color(51,51,51));
		panel.setOpaque(false);
		pane.setMessage(panel);
		pane.setOptions(options);

		dialog.setVisible(true);
	}
	
	/**
	 * Handles user input
	 * @param e    user keyboard input
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		// remove the mini game ticket from user 
		if (frame.getUser().checkAllCollectibles() == true) frame.getUser().resetCollectibles(); 
		else frame.getUser().setMiniGame(0);
		
		// shoot
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			shoot();
		} 
		// move up
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			velY = -6;
		}
		// move down
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			velY = 6;
		}
	}
	
	/**
	 * Manages the state of the mini game 
	 * @param e     details of action performed
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// focuses window so user can use keys
		if (timeCounter == 60) requestFocusInWindow();
		
		// prevent user from moving above the game window
		if (y < 70) {
			velY = 6;
			y = 70;
		}
		// prevent user from moving below the game window
		if (y > 610) {
			velY = -6;
			y = 610;
		}
		y += velY;
		
		// remove cogs not in frame and add gold for each cog hitting the pig
		Iterator<Cog> cogsItr = cogs.iterator();
		while (cogsItr.hasNext()) {
			Cog cog = cogsItr.next();
			if (cog.inFrame() == false) {
				cogsItr.remove();
			} else {
				// give user gold if cog hits the pig
				if (cog.getBoundary(15).intersects(pig.getBoundary())) {
					cog.tmpRemove();
					if (timeCounter != 0) this.gold += 2;
					goldMade.setText("Gold Made: " + gold);
				} else {
					cog.move();					
				}
			}
		}
		repaint();
	}
	
	/**
	 * Draws all components of the mini game after every repaint() call
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g.create();
		g2.drawImage(car, x, y, null);
		for (int i = 0; i < cogs.size(); i++) {
			Cog cog = cogs.get(i);
			Image cogImage = cog.getImage();
			g2.drawImage(cogImage, cog.getX(), cog.getY(), null);			
		}
	}

	/**
	 * Starts game when any key pressed
	 */
	@Override
	public void keyPressed(KeyEvent e) { 
		if (timeCounter != 0) gameLimit.start(); 
	}

	@Override
	public void keyTyped(KeyEvent e) {}
}
