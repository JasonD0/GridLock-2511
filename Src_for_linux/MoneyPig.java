import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 * Implements the target of the mini game 
 * Pig will move up and down at different speeds
 */

public class MoneyPig extends JLabel{
	private Image pigImage;
	private int pigLength, pigX, pigY, velY;
	private Timer timer; 
	
	/**
	 * Constructor for MoneyPig
	 * @param x    x ordinate of the pig
	 */
	public MoneyPig(int x) {
		this.pigX = x;
		this.pigY = 0;
		this.velY = 10;
		this.pigLength = 200;
		initMoneyPig();
	}
	
	/**
	 * Handles the speed and position of the pig
	 */
	private void initMoneyPig() {
		makeImage();
		Random random = new Random();
		timer = new Timer(50, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int x = random.nextInt(60 -20 +1) + 20;	// get random integer between 20 and 60 
				pigY -= velY;
				if (pigY <= 0) {
					//if (x % 2 == 0) velY = -20;
					//else velY = -50;
					velY = -x;
				} else if (pigY >= 420) {
					//if (x % 2 != 0) velY = 20;
					//else velY = 50;
					velY = x;
				}
				repaint();
			}
		});
		timer.start();
		setPreferredSize(new Dimension(pigLength, 420));
	}
	
	/**
	 * Create image for the pig
	 */
	private void makeImage() {
		pigImage = new ImageIcon(("mini_game_pig.png")).getImage();
		pigImage = pigImage.getScaledInstance(pigLength, pigLength, Image.SCALE_SMOOTH);
	}
	
	/**
	 * Draws pig every time repaint() is called
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(pigImage, pigX - 490, pigY, pigLength, pigLength, null);
	}
	
	/**
	 * Returns the boundary/space used up by the pig
	 * @return     rectangle with the same area as the cog
	 */
	public Rectangle getBoundary() {
		return new Rectangle(pigX, pigY, pigLength, pigLength);
	}
	
}
