import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GridLockGrid extends JPanel {
	private final int GRID_HEIGHT = 601;
	private final int GRID_LENGTH = 601;
	private final int BORDER_OFFSET = 0;
	private ArrayList<Car> carList;
	private ArrayList<String> colors;
	private Map<Integer, String> carColor;
	private Car selected;
	private Puzzle current;
	private GridLockFrame frame;
	private int movesMade = 0;
	private int oldX = BORDER_OFFSET;
	private int oldY = BORDER_OFFSET;
	private int index = 0;
	int x = 0, y = 0, velX = 0, velY = 0;
	
	public GridLockGrid(Puzzle initial, GridLockFrame frame) {
		this.frame = frame;
		this.current = initial;
		setBackground(new Color(51,51,51));
		movesMade = 0;
	
		// add grid background
		ImageIcon background = new ImageIcon(getClass().getResource("grid3.png"));
		JLabel pane = new JLabel(background);
		pane.setLayout(new GridBagLayout());
		GridBagConstraints gb = new GridBagConstraints();
		gb.anchor = GridBagConstraints.CENTER;
		pane.add(new Grid());
		add(pane);
		setCarColors();
		initial.initGridState();
		carList = initial.getCarList();
	}
	
	// give each car a color
	private void setCarColors() {
		carColor = new HashMap<>();
		colors = new ArrayList<>(Arrays.asList("black", "blue", "dark_blue", "dark_orange", "dark_pink", "dark_yellow", "emerald", "green", "light_blue", "orange", "pink", "purple", "yellow"));
		Collections.shuffle(colors);
		Iterator<String> itr = colors.iterator();
		for (int i = 0; itr.hasNext(); i++) {
			carColor.put(i, itr.next());
			itr.remove();
		}
	}
	
	private class Grid extends JPanel { 		
		private Grid( ) {
			initGrid();
		}
	
		/**
		 * Initialize mouse adapter for the grid
		 */
		public void initGrid() {
			//setBackground(Color.GRAY);
			setOpaque(false);
			setPreferredSize(new Dimension(GRID_LENGTH, GRID_HEIGHT));
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
						
						// move horizontal car
						if (selected.orientation().equals("h")) {
							horizontalTryMove(row, col);
							horizontalUpdateArray();
							
						}
						// move vertical car 
						if (selected.orientation().equals("v")) {
							verticalTryMove(row, col);
							verticalUpdateArray();
							
						}
						// increment moves made if car moved
						if (selected.getX() != oldX || selected.getY() != oldY) {
							movesMade++;
							frame.setMovesMade(movesMade);
						}
						oldX = selected.getX();
						oldY = selected.getY();
					}
					printState();
					repaint();
					checkGoalState();
				}
				
				@Override
				// move selected car to new position (e.getX, e.getY)
				public void mouseDragged(MouseEvent e) {
					frame.startTimer();
					if (selected != null) {
						int leftX = e.getX() - distanceFromTopToClick.x;
						int leftY = e.getY() - distanceFromTopToClick.y;
						// distanceFromTopToClick.y -> value from top to where click,  e.getY -> value from top of panel  similar with x
						if (selected.orientation().equals("h") && selected.contains(e.getPoint())) {
							// prevent going outside grid on left
							if (leftX < BORDER_OFFSET) 
								selected.setX(BORDER_OFFSET);
							// prevent going outside grid on right
							else if (leftX + selected.getLength() > GRID_LENGTH + BORDER_OFFSET)
								selected.setX(GRID_LENGTH + BORDER_OFFSET - selected.getLength());
							else 
								selected.setX(leftX);
						}
						if (selected.orientation().equals("v") && selected.contains(e.getPoint())) {
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
		
		/**
		 * Draws cars onto the grid
		 * @param g
		 */
		private void draw(Graphics g) {
			Graphics2D g2 = (Graphics2D)g.create();
			for (Car car : carList) {
				// draw non-selected cars
				if (car != selected) {
					if (car.isRed() == true && !carColor.get(car.getId()).equals("red"))  carColor.put(car.getId(), "red");
					String carImagePath = "/cars/" + carColor.get(car.getId());
					if (car.getSize() == 3) carImagePath += "_truck_" + car.orientation() + ".png";
					else if (car.getSize() == 2) carImagePath += "_car_" + car.orientation() + ".png";
					Image carImage = new ImageIcon(getClass().getResource(carImagePath)).getImage();
					g2.drawImage(carImage, car.getX(), car.getY(), null);
					// draw the border for non-selected cars
					/*
					g2.setColor(new Color(255,255,255));
					Stroke oldStroke = g2.getStroke();
					g2.setStroke(new BasicStroke((float) 1.0));
					g2.drawRoundRect(car.getX(), car.getY(), car.getLength(), car.getHeight(), 15, 15);
					g2.setStroke(oldStroke);*/
				}
			}
			
			if (selected != null) {
				// draw selected car
				String carImagePath = "/cars/" + carColor.get(selected.getId());
				if (selected.getSize() == 3) carImagePath += "_truck_" + selected.orientation() + ".png";
				else if (selected.getSize() == 2) carImagePath += "_car_" + selected.orientation() + ".png";
				Image carImage = new ImageIcon(getClass().getResource(carImagePath)).getImage();
				g2.drawImage(carImage, selected.getX(), selected.getY(), null);
				
				// draw the border of selected car
			/*	g2.setColor(Color.WHITE);
				Stroke oldStroke = g2.getStroke();
				g2.setStroke(new BasicStroke((float) 3.0));
				//g2.drawRoundRect(selected.getX(), selected.getY(), selected.getLength(), selected.getHeight(), 15, 15);
				g2.setStroke(oldStroke);*/
			}
			g2.dispose();
		}
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			draw(g);
		}

		/**
		 * Rounds value given to the nearest hundred
		 * Ensures edges of car are on the edges of tiles
		 * @param val
		 * @return
		 */
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
		
		/**
		 * Checks if the red car has reached the exit (last column)
		 */
		private void checkGoalState() {
			if (selected != null && selected.isRed() == true) {
				if (selected.getX() == 400 + BORDER_OFFSET) {
					frame.stopTimer();
					System.out.println("Level Complete");
					new Message(frame, movesMade, frame.getTime());
				}
			}
		}
		
		/**
		 * Moves car left/right until car is on top of free tiles
		 * @param row
		 * @param col
		 */
		private void horizontalTryMove(int row, int col) {
			int[][] gridState = current.getGridState();
			int size = selected.getSize();
			int nextFreeSlot = -1;
			int j = 1, k = -1;
			
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
		
		/**
		 * Moves car up/down until the car is on top of free tiles
		 * @param row
		 * @param col
		 */
		private void verticalTryMove(int row, int col) {
			int[][] gridState = current.getGridState();
			int size = selected.getSize();
			int nextFreeSlot = -1;
			int j = 1, k = -1;
			
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
		
		/**
		 * Updates current grid state for horizontal cars
		 */
		private void horizontalUpdateArray() {
			int[][] gridState = current.getGridState();
			int col = (selected.getX() - BORDER_OFFSET)/100;
			int row = (selected.getY() - BORDER_OFFSET)/100;
			for (int i = 0; i < 6; i++) {
				if (gridState[row][i] == selected.getId()) {
					gridState[row][i] = -1;
				}
			}
			for (int i = 0; i < selected.getSize(); i++) {
				gridState[row][col + i] = selected.getId();
			}
		}
		
		/**
		 * Updates current grid state for vertical cars
		 */
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
		
		/**
		 * Test 
		 * Show current grid state
		 */
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
}
