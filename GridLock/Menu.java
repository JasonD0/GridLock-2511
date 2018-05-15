import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import javax.swing.*;
import java.awt.Dimension;
import javax.swing.JFrame;

public class Menu extends JPanel {
	JButton start = new JButton();
	JButton help = new JButton();
	JButton quit = new JButton();
	GridLockFrame game = new GridLockFrame();

	public Menu() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(Box.createVerticalStrut(160));
		start.setAlignmentX(CENTER_ALIGNMENT);
		start.setPreferredSize(new Dimension(100, 20));
		ImageIcon startIcon = new ImageIcon(Menu.class.getResource("button_play (1).png"));
		start.setIcon(startIcon);
		start.addActionListener(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				game.runGame();
			}
		});
		add(start);

		add(Box.createVerticalStrut(75));
		help.setAlignmentX(CENTER_ALIGNMENT);
		help.setPreferredSize(new Dimension(100, 20));
		ImageIcon helpIcon = new
		ImageIcon(Menu.class.getResource("button_help.png"));
		help.setIcon(helpIcon);
		help.addActionListener(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				game.helpPage();
			}
		});

		ImageIcon icon = new ImageIcon(Menu.class.getResource("button_exit.png"));
		quit.setIcon(icon);
		add(help);
		add(Box.createVerticalStrut(75));
		quit.setAlignmentX(CENTER_ALIGNMENT);
		start.setPreferredSize(new Dimension(100, 20));
		// ImageIcon quitIcon = new
		// ImageIcon(Menu.class.getResource("button_exit.png"));
		// quit.setIcon(quitIcon);
		quit.addActionListener(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		add(quit);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(new ImageIcon(Menu.class.getResource("MenuComplete.png")).getImage(), 0, 0, 640, 658, this);
	}
}