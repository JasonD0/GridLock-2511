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
import javax.swing.Timer;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JFrame;


public class GridLockFrame extends JFrame implements Runnable{
	
	private static final int FRAME_HEIGHT = 875; 
	private static final int FRAME_LENGTH = 650;
	private JLabel movesMadeLabel;
	private JLabel timeLabel;
	private Timer time;
	private int counter;
	private Box box;
	
	public GridLockFrame() {
	}

	@Override
	public void run() {
		setTitle("GridLock");
		setPreferredSize(new Dimension(FRAME_LENGTH, FRAME_HEIGHT));
		setBackground(Color.DARK_GRAY);
		box = new Box(BoxLayout.Y_AXIS);
		
		newJPanel();
		
		pack();
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null) ;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
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
		String move = "<html> Moves <br/> 0</html>";
		movesMadeLabel = new JLabel(move);
		movesMadeLabel.setFont(new Font("Britannic Bold", Font.BOLD, 25));
		movesMadeLabel.setForeground(Color.BLACK);
		movesMade.add(movesMadeLabel);
		head.add(Box.createVerticalGlue());
		head.add(movesMade);
		
		if (time != null) time.stop();
		counter = 0;
		JPanel timePanel = new JPanel();
		timePanel.setMaximumSize(new Dimension(130, 75));
		timePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		timePanel.setBackground(Color.GRAY);
		String timer = "<html> Time <br/>" + counter + "</html>";
		timeLabel = new JLabel(timer);
		timeLabel.setFont(new Font("Britannic Bold", Font.BOLD, 25));
		time = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				counter++;
				String timer = "<html> Time <br/>" + counter + "</html>";
				timeLabel.setText(timer);
			}
		});
		//time.start();
		head.add(Box.createRigidArea(new Dimension(200,0))); // add gap
		timePanel.add(timeLabel);
		head.add(timePanel);
		
		box.add(head);
		box.add(grid);
		
		box.add(new GridButtonsPanel(this));

		this.setContentPane(box);
		//invalidate();
		//validate();
	}
	
	public void setMovesMade(int movesMade) {
		String move = "<html> Moves <br/>" + String.valueOf(movesMade) + "</html>";
		this.movesMadeLabel.setText(move);
	}
	
	public void startTimer() {
		time.start();
	}
	
	public void stopTimer() {
		time.stop();
	}
	
	public int getTime() {
		return this.counter;
	}
}
