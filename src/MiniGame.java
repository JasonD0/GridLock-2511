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
	}
	
	private void initMiniGame() {
		this.cogs = new ArrayList<>();
		setLayout(new BorderLayout());
		headerPanel = new JPanel();
		headerPanel.setOpaque(false);
		
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
		actionTimer.start();
		shootTimer = new Timer(50, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Cog cog = new Cog(x + 50, y - 3);
				cogs.add(cog);
				shootTimer.stop();
			}
		});
	}

	private void shoot() {
		shootTimer.start();
	}
	
	private void endMiniGame() {
		frame.getUser().addMoney(gold);
		UIManager.put("Panel.background", new Color(51,51,51));
		UIManager.put("OptionPane.background", new Color(51,51,51));

		// Create level completion message window
		JOptionPane pane = new JOptionPane();	
		//pane.setPreferredSize(new Dimension(200, 80));
		JPanel panel = new JPanel(new BorderLayout());
		JDialog dialog = pane.createDialog("Mini Game Completed");

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
		Image homeImage = new ImageIcon(getClass().getResource("home1.png")).getImage();
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
		
		// adds message to the message window
		panel.add(message, BorderLayout.CENTER);
		panel.setBackground(new Color(51,51,51));
		panel.setOpaque(false);
		pane.setMessage(panel);
		pane.setOptions(options);

		// create window for the message
		dialog.setVisible(true);
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
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
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (timeCounter == 60) requestFocusInWindow();
		if (y < 70) {
			velY = 6;
			y = 70;
		}
		if (y > 610) {
			velY = -6;
			y = 610;
		}
		y += velY;
		Iterator<Cog> cogsItr = cogs.iterator();
		while (cogsItr.hasNext()) {
			Cog cog = cogsItr.next();
			if (cog.inFrame() == false) {
				cogsItr.remove();
			} else {
				if (cog.getBoundary(15).intersects(pig.getBoundary())) {
					cog.tmpRemove();
					this.gold += 2;
					goldMade.setText("Gold Made: " + gold);
				} else {
					cog.move();					
				}
			}
		}
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(new Color(255,153,0));
		g.fillRect(x, y, 50, 30);
		Graphics2D g2 = (Graphics2D)g.create();
		for (int i = 0; i < cogs.size(); i++) {
			Cog cog = cogs.get(i);
			Image cogImage = cog.getImage();
			g2.drawImage(cogImage, cog.getX(), cog.getY(), null);			
		}
	}

	@Override
	public void keyPressed(KeyEvent e) { if (timeCounter != 0) gameLimit.start(); }

	@Override
	public void keyTyped(KeyEvent e) {}
}
