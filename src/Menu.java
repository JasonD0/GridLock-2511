import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Menu extends JPanel {
	JButton start = new JButton();
	JButton help = new JButton();
	JButton quit = new JButton();
	JButton miniGame = new JButton();
	private String miniGamePng;
	GridLockFrame game;

	public Menu(GridLockFrame game) {
		this.game = game;
		Box box = new Box(BoxLayout.Y_AXIS);

		box.add(Box.createRigidArea(new Dimension(0,197))); // add gap

		start.setAlignmentX(CENTER_ALIGNMENT);
		start.setBorderPainted(false);
		//start.setMargin(new Insets(-2,-2,-3,-2));
		start.setFocusable(false);
		Image menuIcon = new ImageIcon(getClass().getResource("button_play (1).png")).getImage();
		menuIcon = menuIcon.getScaledInstance(110, 61, Image.SCALE_SMOOTH);
		start.setIcon(new ImageIcon(menuIcon));
		start.setContentAreaFilled(false);
		start.addActionListener(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				game.levelPage();
			}
		});
		box.add(start);
		
		box.add(Box.createRigidArea(new Dimension(0,3))); // add gap
		
		miniGame.setAlignmentX(CENTER_ALIGNMENT);
		miniGame.setBorderPainted(false);
		miniGame.setFocusable(false);
		miniGamePng = "miniGame_";
		
		// check mini game accessible
		if (game.getUser().checkMiniGameAccess() == 1 || game.getUser().checkAllCollectibles() == true) {
			miniGamePng += "unlocked.png";
		} else {
			miniGamePng += "locked.png";
		}
		
		Image miniGameIcon = new ImageIcon(getClass().getResource(miniGamePng)).getImage();
		miniGameIcon = miniGameIcon.getScaledInstance(114, 67, Image.SCALE_SMOOTH);
		miniGame.setIcon(new ImageIcon(miniGameIcon));
		miniGame.setContentAreaFilled(false);
		miniGame.addActionListener(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (miniGamePng.equals("miniGame_unlocked.png")) game.miniGame();
			}
		});
		box.add(miniGame);

		box.add(Box.createRigidArea(new Dimension(0,3))); // add gap

		help.setAlignmentX(CENTER_ALIGNMENT);
		help.setBorderPainted(false);
		help.setFocusable(false);
		Image helpIcon = new ImageIcon(getClass().getResource("button_help.png")).getImage();
		helpIcon = helpIcon.getScaledInstance(110, 60, Image.SCALE_SMOOTH);
		help.setIcon(new ImageIcon(helpIcon));
		help.setContentAreaFilled(false);
		help.addActionListener(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				game.helpPage();
			}
		});
		box.add(help);

		box.add(Box.createRigidArea(new Dimension(0,6))); // add gap

		quit.setAlignmentX(CENTER_ALIGNMENT);
		quit.setBorderPainted(false);
		quit.setFocusable(false);
		Image quitIcon = new ImageIcon(getClass().getResource("button_exit.png")).getImage();
		quitIcon = quitIcon.getScaledInstance(110, 60, Image.SCALE_SMOOTH);
		quit.setIcon(new ImageIcon(quitIcon));
		quit.setContentAreaFilled(false);
		quit.addActionListener(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		box.add(quit);

		add(box);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}

	public void draw(Graphics g) {
		g.drawImage(new ImageIcon(Menu.class.getResource("menugif1.gif")).getImage(), 0, 0, 680, 658, this);
	}
}