import java.awt.*;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseListener;
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
import javax.imageio.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;


public class Menu extends JPanel {
    public Rectangle playButton = new Rectangle(235, 150, 150, 50);
    public Rectangle exitButton = new Rectangle(235, 250, 150, 50);
    public Rectangle helpButton = new Rectangle(235, 350, 150, 50);



    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        JLabel backgroundImage;
        ImageIcon menuIcon = new ImageIcon(this.getClass().getResource("Menu Background.png"));
        backgroundImage = new JLabel("",menuIcon,JLabel.CENTER);
        backgroundImage.setBounds(0,0,600,600);
        add(backgroundImage);
        //g.drawImage(menuIcon, 0, 0, getWidth(), getHeight(), this); // draw the image
        //this.setBackground(Color.BLACK);
        render(g);
    }

    public void render(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        Font fnt0 = new Font("arial", Font.BOLD,50);
        g.setFont(fnt0);
        g.setColor(Color.white);
        g.drawString("GridLock", 200,100);

        Font fnt1 = new Font("Arial", Font.BOLD, 30);
        g.setFont(fnt1);
        g.drawString("Play", 280, 185);
        g.drawString("Exit", 280, 285);
        g.drawString("Help", 280, 385);
        g2d.draw(playButton);
        g2d.draw(exitButton);
        g2d.draw(helpButton);
    }

    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        if(x>235 && x<385){
            if(y>150 && y<200){
                //Click on playButton
                GridLockFrame.State = GridLockFrame.State.GAME;
            }
            if(y>150 && y<200){
                //Click on exitButton
            }
            if(y>150 && y<200){
                //Click on helpButton
            }
        }
    }

}
