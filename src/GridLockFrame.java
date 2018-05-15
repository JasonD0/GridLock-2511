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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;


public class GridLockFrame extends JFrame implements Runnable{
	
	private static final int FRAME_HEIGHT = 900; 
	private static final int FRAME_LENGTH = 650;
	private JLabel movesMadeLabel;
	private Game g;
	private Box box;
	public GridLockFrame(Game g) {
		this.g = g;
	}

	@Override
	public void run() {
		setTitle("GridLock");
		setLayout(new BorderLayout());
		box = new Box(BoxLayout.Y_AXIS);
		newJPanel(this.g.getLevel().getInit());
		//movesMadeLabel = new JLabel("0");
		//add(box, BorderLayout.CENTER);
		setPreferredSize(new Dimension(FRAME_LENGTH, FRAME_HEIGHT));
		//add(movesMadeLabel, BorderLayout.PAGE_START);
				
		//add(p, BorderLayout.SOUTH);
		
		pack();
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null) ;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void newJPanel(Puzzle initial) {
		Puzzle copy = initial.clonePuzzle();
		GridLockGrid grid = new GridLockGrid(initial,this);
		grid.setPreferredSize(new Dimension(620, 620));
		grid.setMaximumSize(new Dimension(620, 620));
		grid.setMinimumSize(new Dimension(620, 620));
		box.removeAll();
		box.add(Box.createVerticalGlue());
		box.add(grid);
		//box.add(Box.createVerticalGlue());
		grid.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
		JPanel p = new JPanel();		
		JButton b = new JButton("Reset");
		p.add(b);
		box.add(p);
		b.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				newJPanel(copy);
				
			}
			
		});

		this.setContentPane(box);
		//invalidate();
		//validate();
	}
	
	/*
	public JLabel getMovesLabel() {
		return this.movesMadeLabel;
	}*/
}
