import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

/**
 * Implements the projectile in the mini game 
 *
 */

public class Cog {
	private int cogLength, cogX, cogY;
	private boolean inFrame;
	private Image cogImage;
	
	/**
	 * Constructor for Cog
	 * @param x    initial x ordinate of the cog
	 * @param y    initial y ordinate of the cog
	 */
	public Cog(int x, int y) {
		this.cogLength = 36;
		this.cogX = x;
		this.cogY = y;
		this.inFrame = true;
		initCog();
	}
	
	/**
	 * Creates image for the cog
	 */
	private void initCog() {
		cogImage = new ImageIcon(getClass().getResource("/icons/cog.png")).getImage();
		cogImage = cogImage.getScaledInstance(cogLength, cogLength, Image.SCALE_SMOOTH);
	}
	
	/**
	 * Move cog forward
	 */
	public void move() {
		// indicate cog that is out of the game window
		if (cogX + cogLength > 650) {
			inFrame = false;
		}
		cogX += 15;
	}
	
	/**
	 * Returns x ordinate of the cog
	 * @return    x ordinate on the current frame
	 */
	public int getX() {
		return this.cogX;
	}
	
	/**
	 * Returns y ordinate of the cog
	 * @return    y ordinate on the current frame
	 */
	public int getY() {
		return this.cogY;
	}
	
	/**
	 * Moves cogs outside game window and waits for deletion
	 */
	public void tmpRemove() {
		this.cogX = -1000;
		inFrame = false;
	}
	
	/**
	 * Returns cog image
	 * @return    image of a cog
	 */
	public Image getImage() {
		return this.cogImage;
	}
	
	/**
	 * Checks if cog is in the game window
	 * @return    true if cog is in frame
	 *            false otherwise
	 */
	public boolean inFrame() {
		return this.inFrame;
	}
	
	/**
	 * Returns the boundary/space used up by the cog
	 * @param xMove    offset to prevent gold double counting    
	 * @return         rectangle with the same area as the cog
	 */
	public Rectangle getBoundary(int xMove) {
		return new Rectangle(cogX + xMove, cogY, cogLength, cogLength);
	}
}
