import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

public class GridLockGrid extends JPanel {
	private final int GRID_HEIGHT = 600;
	private final int GRID_LENGTH = 600;
	private final int BORDER_OFFSET = 0;
	
	private JButton button1;
	private JButton button2;
	
	private ArrayList<Car> carList;
	//private int[][] gridState;
	private Puzzle initial;
	private Puzzle current;
	private Car selected;
	private GridLockFrame frame;
	private JLabel moves;
	private int movesMade = 0;
	private int oldX = BORDER_OFFSET;
	private int oldY = BORDER_OFFSET;
	int x = 0, y = 0, velX = 0, velY = 0;
	
	// Note : can pass in frame here -> can use -> reset, new panel (?) etc
	public GridLockGrid(Puzzle initial, GridLockFrame frame) {
		this.initial = initial;
		this.current = initial;
		this.frame = frame;
		//setLayout(new BorderLayout());
		add(new Grid()/*, BorderLayout.CENTER*/);
		JPanel header = new JPanel(); // different java file
		//add(header, BorderLayout.NORTH);
		//moves = frame.getMovesLabel();
		//add(new Utilities(), BorderLayout.SOUTH);
		initial.initGridState();
		carList = initial.getCarList();
	}
	
/*	private void initGridLock() {	
		cars = new ArrayList<>();

		gridState = new int[][] {
			{ 1,  1, -1, -1, -1,  8},
			{ 2, -1, -1,  5, -1,  8},
			{ 2,  4,  4,  5, -1,  8},
			{ 2, -1, -1,  5, -1, -1},
			{ 3, -1, -1, -1,  7,  7},
			{ 3, -1,  6,  6,  6, -1}
		};
		
		
		int xTmp = BORDER_OFFSET, yTmp = BORDER_OFFSET;
		
		// test
		cars.add(new Car(xTmp, yTmp, 200, 100, "h", 2, 1));     
		cars.add(new Car(xTmp, yTmp + 100, 100, 300, "v", 3, 2));
		cars.add(new Car(xTmp, yTmp + 400arrayindex *100, 100, 200, "v", 2, 3));
		cars.add(new Car(xTmp + 100, yTmp + 200, 200, 100, "h", 2, 4));
		cars.add(new Car(xTmp + 300, yTmp + 100, 100, 300, "v", 3, 5));
		cars.add(new Car(xTmp + 200, yTmp + 500, 300, 100, "h", 3, 6));
		cars.add(new Car(xTmp + 400, yTmp + 400, 200, 100, "h", 2, 7));
		cars.add(new Car(xTmp + 500, yTmp, 100, 300, "v", 3, 8));
		//cars.add(new Car(xTmp + 300, yTmp, 300, 100, "h", 3));
	} */

	private class Grid extends JPanel { 		
		private Grid( ) {
			setFocusable(true);
			setFocusTraversalKeysEnabled(false);
			setPreferredSize(new Dimension(600, 600));
			initGrid();
		}
	
		public void initGrid() {
			//setPreferredSize(new Dimension(590, 590));
			//setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
			
			MouseAdapter carMouseAdapter = new MouseAdapter() {
				private Car currSelected;
				private Point distanceFromTopToClick;
				
				@Override
				public void mousePressed(MouseEvent e) {
					// check if clicked on a car
					currSelected = selected;
					if (selected == null || !selected.contains(e.getPoint())) {
						for (Car car : carList) {
							if (car.contains(e.getPoint())) {
								selected = car;
								oldX = x = selected.getX();
								oldY = y = selected.getY();
								distanceFromTopToClick = new Point(e.getX() - selected.getX(), e.getY() - selected.getY());
								repaint();
								break;
							}
						}
					} else if (selected != null) {
						distanceFromTopToClick = new Point(e.getX() - selected.getX(), e.getY() - selected.getY());
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
						
						if (selected.orientation().equals("h")) {
							horizontalTryMove(row, col);
							horizontalUpdateArray();
							
						}
						if (selected.orientation().equals("v")) {
							verticalTryMove(row, col);
							verticalUpdateArray();
							
						}
						oldX = selected.getX();
						oldY = selected.getY();
					}
					printState();
					repaint();
					
					// goal state test 
					if (selected != null && selected.getId() == 4) {
						if (selected.getX() == 400 + BORDER_OFFSET) {
							System.out.println("Level Complete");
							//Object[] buttonText = ;
							//JOptionPane.showMessageDialog(null, "GZ", "Level Complete", JOptionPane.PLAIN_MESSAGE);

			                Object[] options = {"prev", "home", "next", "idk", "DS"};	// result returns 0 1 2 etc

			                int result = JOptionPane.showOptionDialog(null, "GZ", "Level Completed", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
			                //System.out.println(result);
			                if (result == JOptionPane.NO_OPTION) {
			                	frame.newJPanel(current);
			                }
			                
			                //frame.newJPanel();
						}
					}
				}
				
				@Override
				// move selected car to new position (e.getX, e.getY)
				public void mouseDragged(MouseEvent e) {
					if (selected != null) {
						int leftX = e.getX() - distanceFromTopToClick.x;
						int leftY = e.getY() - distanceFromTopToClick.y;
						// distanceFromTopToClick.y -> value from top to where click,  e.getY -> value from top of panel  similar with x
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
						//movesMade++;
						//moves.setText(String.valueOf(movesMade));
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
			//ArrayList<Car> carList = current.getCarList();
			Graphics2D g2 = (Graphics2D)g.create();
			for (Car car : carList) {
				if (car != selected) {
					// draw non-selected cars
					g2.setColor(Color.BLUE);
					g2.fillRoundRect(car.getX(), car.getY(), car.getLength(), car.getHeight(), 15, 15);
					// draw the border for non-selected cars
					// white so it seems like there are gaps between cars
					g2.setColor(Color.WHITE);
					Stroke oldStroke = g2.getStroke();
					g2.setStroke(new BasicStroke((float) 4.0));
					g2.drawRoundRect(car.getX(), car.getY(), car.getLength(), car.getHeight(), 15, 15);
					g2.setStroke(oldStroke);
				}
			}
			if (selected != null) {
				// draw selected car
				g2.setColor(Color.RED);
				g2.fillRoundRect(selected.getX(), selected.getY(), selected.getLength(), selected.getHeight(), 15, 15);
				// draw the border of selected car
				g2.setColor(Color.BLACK);
				Stroke oldStroke = g2.getStroke();
				g2.setStroke(new BasicStroke((float) 2.0));
				g2.drawRoundRect(selected.getX(), selected.getY(), selected.getLength(), selected.getHeight(), 15, 15);
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
		
		private void horizontalTryMove(int row, int col) {
			int size = selected.getSize();
			int nextFreeSlot = -1;
			int j = 1, k = -1;
			int[][] gridState = current.getGridState();
			
			int oldCol = (roundNearestHundred(oldX) - BORDER_OFFSET)/100;
			// try move right 
			if (oldX < selected.getX()) {
				// handles case where car ignores another car eg  2 2 (curr car)  1  -1 -1    
				for (int i = oldCol + size; i < col; i++) {
					if (gridState[row][i] != -1 && gridState[row][i] != selected.getId()) {
						nextFreeSlot = i - size;  // nextFreeslot is the start of rect s.t end is before i 
						break;
					}
				}
				j = -1; k = 0;
				
			// try move left  
			} else if (oldX > selected.getX()) {
				// handles case where car ignores another car eg  -1 -1   1   2 2 (curr car)
				for (int i = oldCol - 1; i >= col; i--) {
					if (gridState[row][i] != -1 && gridState[row][i] != selected.getId()) {
						nextFreeSlot = i + 1;  // nextFreeslot is the start of rect s.t end is before i 
						break;
					}
				}
			}
			// moves car until car is in free tiles
			for (int i = size - 1; i >= 0 /*&& nextFreeSlot == -1*/; i--) {
				if (gridState[row][col+i] != -1 && gridState[row][col+i] != selected.getId()) {
					col += j;		
					i = size + k;
				}
			}
			if (nextFreeSlot != -1) 
				selected.setX(nextFreeSlot*100 + BORDER_OFFSET);
			else
				selected.setX(col*100 + BORDER_OFFSET);
		}
		
		private void verticalTryMove(int row, int col) {
			int size = selected.getSize();
			int nextFreeSlot = -1;
			int j = 1, k = -1;
			int[][] gridState = current.getGridState();
			
			int oldRow = (roundNearestHundred(oldY) - BORDER_OFFSET)/100;
			// try move down
			if (oldY < selected.getY()) {
				// handles case where car ignores another car eg  2 2 (curr car)  1  -1 -1
				for (int i = oldRow + size; i < row; i++) {
					if (gridState[i][col] != -1 && gridState[i][col] != selected.getId()) {
						nextFreeSlot = i - size;  // nextFreeslot is the start of rect s.t end is before i
						break;
					}
				}
				j = -1; k = 0;
				
			// try move up
			} else if (oldY > selected.getY()) {
				// handles case where car ignores another car eg  -1 -1   1   2 2 (curr car)
				for (int i = oldRow - 1; i > row; i--) {
					if (gridState[i][col] != -1 && gridState[i][col] != selected.getId()) {
						nextFreeSlot = i + 1;  // nextFreeslot is the start of rect s.t end is before i 
						break;
					}
				}
			}
			// moves car until whole car is in free tiles
			for (int i = size - 1; i >= 0; i--) {
				if (gridState[row+i][col] != -1 && gridState[row+i][col] != selected.getId()) {
					row += j;		
					i = size + k;
				}
			}
			if (nextFreeSlot != -1)
				selected.setY(nextFreeSlot*100 + BORDER_OFFSET);
			else
				selected.setY(row*100 + BORDER_OFFSET);
		}
		
		private void horizontalUpdateArray() {
			int col = (selected.getX() - BORDER_OFFSET)/100;
			int row = (selected.getY() - BORDER_OFFSET)/100;
			int[][] gridState = current.getGridState();
			for (int i = 0; i < 6; i++) {
				if (gridState[row][i] == selected.getId()) {
					gridState[row][i] = -1;
				}
			}
			for (int i = 0; i < selected.getSize(); i++) {
				gridState[row][col + i] = selected.getId();
			}
		}
		
		private void verticalUpdateArray() {
			int[][] gridState = current.getGridState();
			int row = (selected.getY() - BORDER_OFFSET)/100;
			int col = (selected.getX() - BORDER_OFFSET)/100;
			for (int i = 0; i < 6; i++) {
				if (gridState[i][col] == selected.getId()) {
					gridState[i][col] = -1;
				}
			}
			for (int i = 0; i < selected.getSize(); i++) {
				gridState[row+i][col] = selected.getId();
			}
		}
		
		public void printState() {
			int[][] gridState = current.getGridState();
			for (int i = 0; i < 6; i++) {
				for (int j = 0; j < 6; j++) {
					if (gridState[i][j] == -1) 
						System.out.print(gridState[i][j] + " ");
					else 
						System.out.print(" " + gridState[i][j] + " ");
				}
				System.out.println();
			}
			System.out.println();
		}
	}
	
	// for later if want to add buttons below (?)
	// different file 
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
