import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;


public class GridLockFrame extends JFrame implements Runnable{
	
	private static final int FRAME_HEIGHT = 875; 
	private static final int FRAME_LENGTH = 650;
	private JLabel movesMadeLabel;
	private Box box;
	public GridLockFrame() {
	}

	@Override
	public void run() {
		setTitle("GridLock");
		//setLayout(new BorderLayout());
		setPreferredSize(new Dimension(FRAME_LENGTH, FRAME_HEIGHT));
		setBackground(Color.DARK_GRAY);
		box = new Box(BoxLayout.Y_AXIS);
		
		
		newJPanel();
		//movesMadeLabel = new JLabel("0");
		//add(box, BorderLayout.CENTER);
		//add(movesMadeLabel, BorderLayout.PAGE_START);
				
		//add(p, BorderLayout.SOUTH);
		
		pack();
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null) ;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	// add array as param ie gridstate 
	// gridLockgrid should keep copy of original -> when reset -> calls this with orig array
	// gridLockFrame gives array from algorithm
	public void newJPanel() {
		GridLockGrid grid = new GridLockGrid(this);
		grid.setPreferredSize(new Dimension(620, 620));
		grid.setMaximumSize(new Dimension(620, 620));
		grid.setMinimumSize(new Dimension(620, 620));
		grid.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		
		box.removeAll();
		
		Box head = new Box(BoxLayout.X_AXIS);
		JPanel movesMade = new JPanel();
		movesMade.setMaximumSize(new Dimension(130, 75));
		movesMade.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		movesMade.setBackground(Color.GRAY);
		//setMovesMade(0);
		String move = "<html> Moves <br/> &nbsp; &nbsp;0</html>";
		movesMadeLabel = new JLabel(move);
		movesMadeLabel.setFont(new Font("Britannic Bold", Font.BOLD, 25));
		movesMadeLabel.setForeground(Color.BLACK);
		movesMade.add(movesMadeLabel);
		head.add(Box.createVerticalGlue());
		head.add(movesMade);
		box.add(head);
		box.add(grid);
		
		box.add(new GridButtonsPanel(this));

		this.setContentPane(box);
		//invalidate();
		//validate();
	}
	
	public void setMovesMade(int movesMade) {
		String move = "<html> Moves <br/> &nbsp; &nbsp;" + String.valueOf(movesMade) + "</html>";
		this.movesMadeLabel.setText(move);
	}
	
	/*
	public JLabel getMovesLabel() {
		return this.movesMadeLabel;
	}*/
}
