import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

public class GridLockGrid extends JPanel {
	private final int GRID_HEIGHT = 600;
	private final int GRID_LENGTH = 600;
	private final int CAR_HEIGHT = 200;
	private final int CAR_LENGTH = 100;
	private final int BORDER_OFFSET = 10;
	
	private JButton button1;
	private JButton button2;
	
	private List<Car> cars;
	private int[][] gridState;
	private Car selected;

	private int oldX = BORDER_OFFSET;
	private int oldY = BORDER_OFFSET;
	int x = 0, y = 0, velX = 0, velY = 0;
	
	public GridLockGrid() {
		setLayout(new BorderLayout());
		add(new Grid(), BorderLayout.NORTH);
		//add(new Utilities(), BorderLayout.SOUTH);
		initGridLock();
	}
	
	private void initGridLock() {	
		cars = new ArrayList<>();
		
		/* TO DO:
		   	- constructor for gridLockGrid takes in array representing grid state
		   	- add cars based on the array given
		*/ 
		gridState = new int[][] {
			{ 1,  1, -1, -1, -1, -1},
			{-1, -1, -1, -1, -1, -1},
			{-1, -1, -1, -1, -1, -1},
			{-1, -1, -1, -1, -1, -1},
			{ 2, -1, -1, -1, -1, -1},
			{ 2, -1, -1, -1, -1, -1}
		};
		
		
		int xTmp = BORDER_OFFSET, yTmp = BORDER_OFFSET;
		/*
		for (int i = 0; i < 3; i++) {
			cars.add(new Car(xTmp, yTmp, CAR_LENGTH, CAR_HEIGHT, "v"));
//			xTmp = 0;
			yTmp += 200;
		}*/
		cars.add(new Car(xTmp, yTmp, 200, 100, "h", 2));
		cars.add(new Car(xTmp, yTmp + 400, 100, 200, "v", 2));
		
	}

	/* TO DO
	 	- check if red car reached goal i.e. column = 4  (left edge of car of size 2)  
	*/
	private class Grid extends JPanel { 		
		private Grid( ) {
			setFocusable(true);
			setFocusTraversalKeysEnabled(false);
			initGrid();
		}
	
		public void initGrid() {
			setPreferredSize(new Dimension(620, 620));
			setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
			
			MouseAdapter carMouseAdapter = new MouseAdapter() {
				private Car currSelected;
				private Point delta;
				
				@Override
				public void mousePressed(MouseEvent e) {
					// check if clicked on a car
					currSelected = selected;
					if (selected == null || !selected.contains(e.getPoint())) {
						for (Car car : cars) {
							if (car.contains(e.getPoint())) {
								selected = car;
								oldX = x = selected.getX();
								oldY = y = selected.getY();
								delta = new Point(e.getX() - selected.getX(), e.getY() - selected.getY());
								repaint();
								break;
							}
						}
					} else if (selected != null) {
						delta = new Point(e.getX() - selected.getX(), e.getY() - selected.getY());
					}
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					// clicking anything else again deselect
					//repaint();
					if (selected != null && selected == currSelected) {
						selected.setX(roundNearestHundred(selected.getX()));
						selected.setY(roundNearestHundred(selected.getY()));
						selected = null;
						repaint();
					}
				}
				
				@Override
			    public void mouseReleased(MouseEvent e) {
					if (selected != null) {
						// ensures edges of car on edges of tile
						selected.setX(roundNearestHundred(selected.getX()));
						selected.setY(roundNearestHundred(selected.getY()));
						
						int row = (selected.getY() - BORDER_OFFSET)/100;	// top edge of car
						int col = (selected.getX() - BORDER_OFFSET)/100;	// left edge of car
						
						if (selected.orientation().equals("h")) horizontalTryMove(row, col);
						if (selected.orientation().equals("v")) verticalTryMove(row, col);
						
						oldX = selected.getX();
						oldY = selected.getY();
					}
					repaint();
				}
				
				@Override
				// move selected car to new position (e.getX, e.getY)
				public void mouseDragged(MouseEvent e) {
					if (selected != null) {
						int leftX = e.getX() - delta.x;
						int leftY = e.getY() - delta.y;
						// delta.y -> value from top to where click,  e.getY -> value from top of panel  similar with x
						if (selected.orientation().equals("h")) {
							// prevent going outside grid on left
							if (leftX < BORDER_OFFSET) 
								selected.setX(BORDER_OFFSET);
							// prevent going outside grid on right
							else if (leftX + selected.getLength() > GRID_LENGTH + BORDER_OFFSET)
								selected.setX(GRID_LENGTH + BORDER_OFFSET - selected.getLength());
							else 
								selected.setX(leftX);
						}
						if (selected.orientation().equals("v")) {
							// prevent going outside grid up
							if (leftY < BORDER_OFFSET) 
								selected.setY(BORDER_OFFSET);
							// prevent going outside grid down
							else if (leftY + selected.getHeight() > GRID_HEIGHT + BORDER_OFFSET)
								selected.setY(GRID_HEIGHT + BORDER_OFFSET - selected.getHeight());
							else 
								selected.setY(leftY);
						}
						repaint();
					}
				}
			};
			addMouseListener(carMouseAdapter);
			addMouseMotionListener(carMouseAdapter);
		}
		
		private void draw(Graphics g) {
			/* TO DO   
			 	- exit car is red
			 	- different colors for different cars (?)
			*/  
			Graphics2D g2 = (Graphics2D)g.create();
			for (Car car : cars) {
				if (car != selected) {
					// draw non-selected cars
					g2.setColor(Color.BLUE);
					g2.fillRect(car.getX(), car.getY(), car.getLength(), car.getHeight());
					// draw the border for non-selected cars
					// white so it seems like there are gaps between cars
					g2.setColor(Color.WHITE);
					Stroke oldStroke = g2.getStroke();
					g2.setStroke(new BasicStroke((float) 4.0));
					g2.drawRect(car.getX(), car.getY(), car.getLength(), car.getHeight());
					g2.setStroke(oldStroke);
				}
			}
			if (selected != null) {
				// draw selected car
				g2.setColor(Color.RED);
				g2.fillRect(selected.getX(), selected.getY(), selected.getLength(), selected.getHeight());
				// draw the border of selected car
				g2.setColor(Color.BLACK);
				Stroke oldStroke = g2.getStroke();
				g2.setStroke(new BasicStroke((float) 2.0));
				g2.drawRect(selected.getX(), selected.getY(), selected.getLength(), selected.getHeight());
				g2.setStroke(oldStroke);
			}
			g2.dispose();
		}
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			draw(g);
		}

		// ensures edges of car are on the edges of tiles
		public int roundNearestHundred(int val) {
			int value = val;
			if (value < BORDER_OFFSET) return BORDER_OFFSET;
			if (value > GRID_LENGTH) return GRID_LENGTH;
			if (value % 100 < 50)
				for (; value % 100 != BORDER_OFFSET; value -= 1) {}
			else if (value % 100 > 50) 
				for (; value % 100 != BORDER_OFFSET; value += 1) {}
			return value;
		}
		
		public void horizontalTryMove(int row, int col) {
			/* TO DO 
			   - update array when change car position (or do it in mouse released)
			*/
			int size = selected.getSize();
			int nextFreeSlot = -1;
			int j = 1, k = -1;
			
			int oldCol = (roundNearestHundred(oldX) - BORDER_OFFSET)/100;
			// try move right 
			if (oldX < selected.getX()) {
				// handles case where car ignores another car eg  2 2 (curr car)  1  -1 -1    
				for (int i = oldCol + size; i < col; i++) {
					if (gridState[row][i] != -1 && gridState[row][i] != 1/*selected.getID*/) {
						nextFreeSlot = i - size;  // nextFreeslot is the start of rect s.t end is before i 
						break;
					}
				}
				j = -1; k = 0;
				
			// try move left  
			} else if (oldX > selected.getX()) {
				// handles case where car ignores another car eg  -1 -1   1   2 2 (curr car)
				for (int i = oldCol - 1; i >= col; i--) {
					if (gridState[row][i] != -1 && gridState[row][i] != 1/*selected.getID*/) {
						nextFreeSlot = i + 1;  // nextFreeslot is the start of rect s.t end is before i 
						break;
					}
				}
			}
			// moves car until car is in free tiles
			for (int i = size - 1; i >= 0 /*&& nextFreeSlot == -1*/; i--) {
				if (gridState[row][col+i] != -1 && gridState[row][col+i] != 1 /*selected.getID()*/) {
					col += j;		
					i = size + k;
				}
			}
			if (nextFreeSlot != -1) 
				selected.setX(nextFreeSlot*100 + BORDER_OFFSET);
			else
				selected.setX(col*100 + BORDER_OFFSET);
		}
		
		public void verticalTryMove(int row, int col) {
			/* TO DO 
			   - update array when change car position (or do it in mouse released)
			*/
			int size = selected.getSize();
			int nextFreeSlot = -1;
			int j = 1, k = -1;
			
			int oldRow = (roundNearestHundred(oldY) - BORDER_OFFSET)/100;
			// try move down
			if (oldY < selected.getY()) {
				// handles case where car ignores another car eg  2 2 (curr car)  1  -1 -1
				for (int i = oldRow + size; i < row; i++) {
					if (gridState[i][col] != -1 && gridState[i][col] != 2/*selected.getID()*/) {
						nextFreeSlot = i - size;  // nextFreeslot is the start of rect s.t end is before i
						break;
					}
				}
				j = -1; k = 0;
				
			// try move up
			} else if (oldY > selected.getY()) {
				// handles case where car ignores another car eg  -1 -1   1   2 2 (curr car)
				for (int i = oldRow - 1; i > row; i--) {
					if (gridState[i][col] != -1 && gridState[i][col] != 2/*selected.getID()*/) {
						nextFreeSlot = i + 1;  // nextFreeslot is the start of rect s.t end is before i 
						break;
					}
				}
			}
			// moves car until whole car is in free tiles
			for (int i = size - 1; i >= 0; i--) {
				if (gridState[row+i][col] != -1 && gridState[row+i][col] != 2/*selected.getID()*/) {
					row += j;		
					i = size + k;
				}
			}
			if (nextFreeSlot != -1)
				selected.setY(nextFreeSlot*100 + BORDER_OFFSET);
			else
				selected.setY(row*100 + BORDER_OFFSET);
		}
	}
	
	// for later if want to add buttons below (?)
	private class Utilities extends JPanel {
		private Utilities() {
			initUtilities();
		}
		
		public void initUtilities() {
			button1 = new JButton( new AbstractAction("idk") {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
				}
				
			});
			//button2 = new JButton("hhkj");
			setLayout(new BorderLayout());
			add(button1, BorderLayout.EAST);
			//add(button2, BorderLayout.WEST);
		}
	} 

}
