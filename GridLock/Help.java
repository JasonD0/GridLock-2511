import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Help extends JPanel {
	GridLockFrame game;
	JButton Return = new JButton();
	
	public Help(GridLockFrame game) {
		this.game = game;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		add(Box.createVerticalStrut(160));
		Return.setAlignmentX(CENTER_ALIGNMENT);
		Return.setPreferredSize(new Dimension(100, 20));
		ImageIcon startIcon = new ImageIcon(Menu.class.getResource("button_play (1).png"));
		Return.setIcon(startIcon);
		
		Return.addActionListener(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				game.mainMenu();
			}
		});
		add(Return);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
        setBackground(Color.blue);
	}
}