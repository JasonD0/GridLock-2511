import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 * Implements image animation of an image
 * Images will resize then bounce
 */

public class AnimalAnimation extends JLabel{
	private Image animal;
	private int imageLength = 75;
	private int imageHeight = 75;
	private int imageX = 17;
	private int imageY = 45;
	private int imageSizeChange = 10;
	private int pointChange = 3;
	private int pointChangeY = 0;
	private Timer timer;

	/**
	 * Constructor for AnimalAnimation
	 * @param animalReward    animal name of the reward
	 */
	public AnimalAnimation(String animalReward) {
		ImageIcon animalIcon = new ImageIcon(getClass().getResource("/animals/" + animalReward + ".png"));
		animal = animalIcon.getImage();
		
		timer = new Timer(50, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// change size and position of image
				imageLength += imageSizeChange;
				imageHeight += imageSizeChange;
				imageX -= pointChange;
				imageY -= pointChange + pointChangeY;

				// start decreasing size of image 
				if (imageHeight == 135) {
					imageSizeChange = -imageSizeChange;
					pointChange = -3;
				}

				// stops resizing of image
				if (imageHeight == 75 && pointChange < 0) {
					//timer.stop();
					imageSizeChange = 0;
					pointChange = 0;
				}

				// starts image bouncing
				if (imageSizeChange == 0 && pointChange == 0) {
					if (imageY == 63) {
						pointChangeY = 3;
					} else if (imageY == 45){
						pointChangeY = -3;
					}
				}
				repaint();
			}
		});
		timer.start();
		setPreferredSize(new Dimension(130, 140));
	}
	
	/**
	 * Draws the animal every time repaint() is called
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(animal, imageX, imageY, imageLength, imageHeight, null);
	}
}
