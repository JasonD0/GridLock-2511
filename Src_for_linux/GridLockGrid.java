import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Implements the interface for the puzzle  
 *
 */

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
	private boolean help = false;
	private boolean reward = true;
	int x = 0, y = 0, velX = 0, velY = 0;

	/**
	 * Constructor for GridLockGrid
	 * @param initial    the initial state of the puzzle 
	 * @param frame		 link back to the main menu and puzzle's time and moves updater
	 */
	public GridLockGrid(Puzzle initial, GridLockFrame frame) {
		this.frame = frame;
		this.current = initial;
		setBackground(new Color(51,51,51));
		movesMade = 0;

		// add grid background
		ImageIcon background = new ImageIcon("grid3.png");
		JLabel pane = new JLabel(background);
		pane.setLayout(new GridBagLayout());
		GridBagConstraints gb = new GridBagConstraints();
		gb.anchor = GridBagConstraints.CENTER;

		// create interface for puzzle
		pane.add(new Grid());
		add(pane);
		setCarColors();
		initial.initGridState();
		carList = initial.getCarList();
	}

	/**
	 * Assigns a unique color to each car
	 */
	private void setCarColors() {
		carColor = new HashMap<>();
		colors = new ArrayList<>(Arrays.asList("black", "blue", "dark_blue", "dark_orange", "dark_pink", "dark_yellow", "emerald", "green", "light_blue", "orange", "pink", "purple", "yellow"));
		Collections.shuffle(colors);
		Iterator<String> colorsItr = colors.iterator();
		// i -> id    
		for (int i = 0; colorsItr.hasNext(); i++) {
			carColor.put(i, colorsItr.next());
			colorsItr.remove();
		}
	}

	/**
	 * Sets help boolean to indicate whether user wants to delete a car
	 * @param bool    indicates if user is deleting a car 
	 */
	public void setHelp(boolean bool) {
		this.help = bool;
	}

	/**
	 * Checks if user clicked the help (delete car) button
	 * @return
	 */
	public boolean checkHelp() {
		return this.help;
	}

	/**
	 * Implements the interface for the cars
	 *
	 */
	private class Grid extends JPanel { 	
		/**
		 * Constructor for Grid 
		 */
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

				/**
				 * Saves information of the car pressed and co-ordinates of the mouse click
				 * @param e    details of mouse click
				 */
				@Override
				public void mousePressed(MouseEvent e) {
					// removes the car selected
					if (help == true) {
						removeCar(e);	
						frame.startTimer();
					}
					
					// save co-ordinates of click of new selected car
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
					// save co ordinates of click of already selected car
					} else if (selected != null) {
						distanceFromTopToClick = new Point(e.getX() - selected.getX(), e.getY() - selected.getY());
					}
				}

				/**
				 * Reset information of the current car 
				 * @param e    details of mouse click
				 */
				@Override
				public void mouseClicked(MouseEvent e) {
					if (selected != null && selected == currSelected) {
						selected.setX(roundNearestHundred(selected.getX()));
						selected.setY(roundNearestHundred(selected.getY()));
						selected = null;
						repaint();
					}
				}

				/**
				 * Collision detection of the cars
				 * Moves cars back towards it's old position until the car sits on free tiles
				 * @param e    details of mouse release
				 */
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

						// save old position of the current car
						oldX = selected.getX();
						oldY = selected.getY();
					}
					printState();
					repaint();
					checkGoalState();
				}

				/**
				 * Moves selected car following the mouse drag
				 * @param e    details of mouse drag movement
				 */
				@Override
				public void mouseDragged(MouseEvent e) {
					frame.startTimer();
					if (selected != null) {
						// gets current car's top left corner co-ordinates
						// distanceFromTopToClick.y is the value from top to the click, e.getY is the value from top of panel  
						int leftX = e.getX() - distanceFromTopToClick.x;
						int leftY = e.getY() - distanceFromTopToClick.y;

						// move horizontal cars
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
						// move vertical cars
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
			// draw all non-selected cars
			for (Car car : carList) {
				if (car != selected) {
					// initialize red car
					if (car.isRed() == true && !carColor.get(car.getId()).equals("red"))  carColor.put(car.getId(), "red");

					// sets car image 
					String carImagePath = carColor.get(car.getId());				
					if (car.getSize() == 3) carImagePath += "_truck_" + car.orientation() + ".png";
					else if (car.getSize() == 2) carImagePath += "_car_" + car.orientation() + ".png";
					Image carImage = new ImageIcon(carImagePath).getImage();
					g2.drawImage(carImage, car.getX(), car.getY(), null);
				}
			}

			// draw selected car
			if (selected != null) {
				String carImagePath = carColor.get(selected.getId());
				if (selected.getSize() == 3) carImagePath += "_truck_" + selected.orientation() + ".png";
				else if (selected.getSize() == 2) carImagePath += "_car_" + selected.orientation() + ".png";
				Image carImage = new ImageIcon((carImagePath)).getImage();
				g2.drawImage(carImage, selected.getX(), selected.getY(), null);			
			}
			g2.dispose();
		}

		/**
		 * Calls draw method to redraw grid state each time repaint() is called
		 */
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			draw(g);
		}

		/**
		 * Rounds value given to the nearest hundred
		 * Ensures edges of car are on the edges of tiles
		 * @param val    value to be rounded
		 * @return	     rounded value 
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
					new Message(frame, movesMade, frame.getTime(), reward);
				}
			}
		}

		/**
		 * Moves car left/right until car is on top of free tiles
		 * @param row    row of the car in the current grid state
		 * @param col    column of the car in the current grid state
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
			// sets car x ordinate for when car ignores blocking car 
			if (nextFreeSlot != -1)
				selected.setX(nextFreeSlot*100 + BORDER_OFFSET);
			else
				selected.setX(col*100 + BORDER_OFFSET);
		}

		/**
		 * Moves car up/down until the car is on top of free tiles
		 * @param row    row of the car in the current grid state
		 * @param col    column of the car in the current grid state
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
			// sets y ordinate for when the car ignores a blocking car
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

	/**
	 * Remove car selected from the puzzle and set penalty (increase time taken and moves made)
	 * @param e    details of mouse click
	 */
	private void removeCar(MouseEvent e) {
		List<Car> toRemove = new ArrayList<>();
		// find car clicked and removes from grid
		for (Car car : carList) {
			if (car.contains(e.getPoint()) && car.isRed() == false) {
				toRemove.add(car);
				int[][] gridState = current.getGridState();
				for (int i = 0; i < 6; i++) {
					for (int j = 0; j < 6; j++) {
						if (gridState[i][j] == car.getId()) {
							gridState[i][j] = -1;
						}
					}
				}
				// add penalty if user hasn't bought or won free deletes
				if (frame.getUser().checkFreeDeleteUsed() == false) {
					reward = false;
					movesMade += 50;
					frame.increaseTime();
				} else {
					frame.getUser().setFreeDelete(-1);
				}
				frame.setMovesMade(movesMade);
				break;
			}
		}
		selected = null;
		help = false;
		setCursor(null);
		carList.removeAll(toRemove);
	}
}
