
public class InGameObject {
	private int x;
	private int y;
	private int size;
	private Type type;
	private Direction direction;
	
	InGameObject(int x, int y, Type type) {
		this.x = x;
		this.y = y;
		this.type = type;
	}
	
	InGameObject(int x, int y, Type type, Direction direction) {
		this.x = x;
		this.y = y;
		this.type = type;
		this.direction = direction;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
	
	
	
}
