import java.awt.Point;

public class Car {	
	private int x, y;
	private int height, length, size;
	private String orientation;
	private int id;
	
	public Car(int x, int y, int length, int height, String orientation, int size, int id) {
		this.x = x;
		this.y = y;
		this.height = height;
		this.length = length;
		this.orientation = orientation;
		this.size = size;
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setX(int x) { 
		this.x = x; 
	}
	
	public void setY(int y) { 
		this.y = y; 
	}
	
	public int getX() { 
		return this.x; 
	}
	
	public int getY() { 
		return this.y; 
	}
	
	public int getSize() {
		return this.size;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public int getLength() {
		return this.length;
	}
	
	public String orientation() {
		return this.orientation;
	}
	
	public boolean contains(Point point) {
		if (point.x < this.x || point.y < this.y) return false;
		if (point.x > this.x + length || point.y > this.y + height) return false;
		return true;
	}
}

