import java.awt.Point;

public class Car {	
	private int x, y;
	private int height, length, size;
	private String orientation;
	private int id;
	private String color;
	private boolean red;
	
	
	/**
	 * Constructor for car
	 * @param x
	 * @param y
	 * @param length
	 * @param height
	 * @param orientation
	 * @param size
	 * @param id
	 * @param red
	 */
	public Car(int x, int y, int length, int height, String orientation, int size, int id, boolean red) {
		this.x = x;
		this.y = y;
		this.height = height;
		this.length = length;
		this.orientation = orientation;
		this.size = size;
		this.id = id;
		this.red = red;
	}
	
	/**
	 * 
	 * @return color of the car
	 */
	public String getColor() {
		return this.color;
	}
	
	/**
	 * check whether the car is red or not
	 * @return 
	 */
	
	public boolean isRed() {
		return this.red;
	}
	
	/**
	 * @return get the ID of the car
	 */
	public int getId() {
		return this.id;
	}
	
	/**
	 * changing the x coordinate
	 * @param x
	 */
	public void setX(int x) { 
		this.x = x; 
	}
	
	/**
	 * changing the y coordinate
	 * @param y
	 */
	public void setY(int y) { 
		this.y = y; 
	}

	/**
	 * @return get x coordinate of the car
	 */
	public int getX() { 
		return this.x; 
	}
	/**
	 * @return get y coordinate of the car
	 */
	public int getY() { 
		return this.y; 
	}
	/**
	 * @return get the size of the car
	 */
	public int getSize() {
		return this.size;
	}
	/**
	 * @return get the height of the car
	 */
	public int getHeight() {
		return this.height;
	}
	/**
	 * @return get the length of the car
	 */
	public int getLength() {
		return this.length;
	}
	/**
	 * 
	 * @return string telling the direction of where the vehicle is facing
	 */
	public String orientation() {
		return this.orientation;
	}
	
	/**
	 * @param point
	 * @return boolean
	 */
	
	public boolean contains(Point point) {
		if (point.x < this.x || point.y < this.y) return false;
		if (point.x > this.x + length || point.y > this.y + height) return false;
		return true;
	}
}

