import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Cog {
	private int cogLength, cogX, cogY;
	private boolean inFrame;
	private Image cogImage;
	
	public Cog(int x, int y) {
		this.cogLength = 36;
		this.cogX = x;
		this.cogY = y;
		this.inFrame = true;
		initCog();
	}
	
	private void initCog() {
		cogImage = new ImageIcon(getClass().getResource("cog.png")).getImage();
		cogImage = cogImage.getScaledInstance(cogLength, cogLength, Image.SCALE_SMOOTH);
	}
	
	public void move() {
		if (cogX + cogLength > 650) {
			inFrame = false;
		}
		cogX += 15;
	}
	
	public int getX() {
		return this.cogX;
	}
	
	public int getY() {
		return this.cogY;
	}
	
	public void tmpRemove() {
		this.cogX = -1000;
		inFrame = false;
	}
	
	public Image getImage() {
		return this.cogImage;
	}
	
	public boolean inFrame() {
		return this.inFrame;
	}
	
	public Rectangle getBoundary(int xMove) {
		return new Rectangle(cogX + xMove, cogY, cogLength, cogLength);
	}
}
