import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Grid extends JPanel implements ActionListener{
	private final int GRID_HEIGHT = 6;
	private final int GRID_LENGTH = 6;
	
	Timer t = new Timer(5, this);
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}