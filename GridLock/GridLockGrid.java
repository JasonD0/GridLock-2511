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

	int x = 0, y = 0, velX = 0, velY = 0;
	
	public GridLockGrid() {
		setLayout(new BorderLayout());
		add(new Grid(), BorderLayout.NORTH);
		//add(new Utilities(), BorderLayout.SOUTH);
		initGridLock();
	}
	
	private void initGridLock() {	
		cars = new ArrayList<>();
		
		gridState = new int[][] {
			{ 1,  1, -1, -1, -1, -1},
			{-1, -1, -1, -1, -1, -1},
			{ 2, -1, -1, -1, -1, -1},
			{ 2, -1, -1, -1, -1, -1},
			{-1, -1, -1, -1, -1, -1},
			{-1, -1, -1, -1, -1, -1}
		};
		
		
		int xTmp = BORDER_OFFSET, yTmp = BORDER_OFFSET;
		/*
		for (int i = 0; i < 3; i++) {
			cars.add(new Car(xTmp, yTmp, CAR_LENGTH, CAR_HEIGHT, "v"));
//			xTmp = 0;
			yTmp += 200;
		}*/
		cars.add(new Car(xTmp, yTmp, 200, 100, "h", 2));
		cars.add(new Car(xTmp, yTmp + 100, 100, 200, "v", 2));
		
	}

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
					System.out.println(e.getX() + " " + e.getY());
					// check if clicked on a car
					currSelected = selected;
					if (selected == null || !selected.contains(e.getPoint())) {
						for (Car car : cars) {
							if (car.contains(e.getPoint())) {
								selected = car;
								x = selected.getX();
								y = selected.getY();
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
					if (selected == currSelected) {
						selected = null;
						repaint();
					}
				}
				
				@Override
				// move selected car to new position (e.getX, e.getY)
				public void mouseDragged(MouseEvent e) {
					if (selected != null) {
						int x = e.getX() - delta.x;
						int y = e.getY() - delta.y;
						if (selected.orientation().equals("h")) {
							// change to use array -> /100 etc to get array position and see if can drag 
							// prevent going outside grid on left
							if (x < BORDER_OFFSET) 
								selected.setX(BORDER_OFFSET);
							// prevent going outside grid on right
							else if (x + selected.getLength() > GRID_LENGTH + BORDER_OFFSET)
								selected.setX(GRID_LENGTH + BORDER_OFFSET - selected.getLength());
							// if array -> grid -> empty -> go there else -> dont go anywhere
							else { 
								//System.out.println(e.getX() + " " + e.getY()); // prints as long as you drag  so can get exact eg 400 x ord -> /100 -> array[][4]
								// but depends on where you click
								// use x  -> position of left (only x) thanks to delta 
									// so x = 300 -> col 3 ie gridState[][3]
									// REMEBER TO DO THINGS RELATIVE TO LEFT OF RECTANGLE  (only x)
								// remember cars have white border
								// array location   point/100    (only looking at top left)
								// car class -> horizontal/vertical     size    
									// -> trymove ->   if selected.getX() getY() (remember left)  -> get array position -> 
										// if eg vertical size 2 ->   array[Y + size-1][X ]  -> gets position in array 
											// y gets which row    + size-1  to get end point (depend move up or down)
									// when try move down -> trymove -> get end of car -> check one below ie second [] -> -1 -> if == -1 -> move down
								// instead of changin x/y to get array 
									// index 0 - 1  -> 0 - 100    so if eg x 50 -> index 0   100 -200 -> 1   200-300 2   300-400  3  400-500 4... 
//								int yOrd = y/100 
	//							int xOrd = 
		//						if ()
								if (x > 300) System.out.println("dsada" + " " + y);   
								selected.setX(x);
								
							}
						}
						
						if (selected.orientation().equals("v")) {
							// prevent going outside grid up
							if (y < BORDER_OFFSET) 
								selected.setY(BORDER_OFFSET);
							// prevent going outside grid down
							else if (y + selected.getHeight() > GRID_HEIGHT + BORDER_OFFSET)
								selected.setY(GRID_HEIGHT + BORDER_OFFSET - selected.getHeight());
							else 
								selected.setY(y);
						}
						repaint();
					}
				}
			};
			addMouseListener(carMouseAdapter);
			addMouseMotionListener(carMouseAdapter);
		}
		
		private void draw(Graphics g) {
			Graphics2D g2 = (Graphics2D)g.create();
			for (Car car : cars) {
				if (car != selected) {
					g2.setColor(Color.BLUE);
					g2.fillRect(car.getX(), car.getY(), car.getLength(), car.getHeight());
					g2.setColor(Color.WHITE);
					
					Stroke oldStroke = g2.getStroke();
					g2.setStroke(new BasicStroke((float) 4.0));
					g2.drawRect(car.getX(), car.getY(), car.getLength(), car.getHeight());
					g2.setStroke(oldStroke);
				}
			}
			if (selected != null) {
				g2.setColor(Color.RED);
				g2.fillRect(selected.getX(), selected.getY(), selected.getLength(), selected.getHeight());
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
}
