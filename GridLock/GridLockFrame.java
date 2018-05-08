import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import java.awt.EventQueue;
import javax.swing.JFrame;

// DO LIKE TETRIS -> NO GRID(?) 

public class GridLockFrame extends JFrame implements Runnable{
	private JFrame frame;
	private static final int FRAME_HEIGHT = 658; 
	private static final int FRAME_LENGTH = 640;
	
	public GridLockFrame() {
	}

	@Override
	public void run() {
		setPreferredSize(new Dimension(FRAME_LENGTH, FRAME_HEIGHT));
		setTitle("GridLock");
		GridLockGrid grid = new GridLockGrid();
		add(grid);

		
		pack();
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null) ;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
