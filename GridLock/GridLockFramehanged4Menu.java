import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import java.awt.EventQueue;
import javax.swing.JFrame;

public class GridLockFrame extends JFrame implements Runnable {
	private JFrame frame;
	private static final int FRAME_HEIGHT = 658;
	private static final int FRAME_LENGTH = 640;

	public GridLockFrame() {
	}

	@Override
	public void run() {
		setPreferredSize(new Dimension(FRAME_LENGTH, FRAME_HEIGHT));
		setTitle("GridLock");
		// GridLockGrid grid = new GridLockGrid();
		Menu menu = new Menu();
		add(menu);

		pack();
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void runGame() {
		GridLockGrid grid = new GridLockGrid();
		add(grid);

		pack();
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void helpPage() {
		setPreferredSize(new Dimension(FRAME_LENGTH, FRAME_HEIGHT));
		setTitle("GridLock");
		Help help = new Help();
		add(help);

		pack();
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void mainMenu() {
		setPreferredSize(new Dimension(FRAME_LENGTH, FRAME_HEIGHT));
		setTitle("GridLock");
		Help help = new Help();
		add(help);

		pack();
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void levelManager() {
		setPreferredSize(new Dimension(FRAME_LENGTH, FRAME_HEIGHT));
		setTitle("GridLock");
		Help help = new Help();
		add(help);

		pack();
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}