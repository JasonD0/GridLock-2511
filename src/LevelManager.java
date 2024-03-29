import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import javax.swing.*;
import java.awt.Dimension;
import javax.swing.JFrame;

public class LevelManager extends JPanel {
    JButton Easy = new JButton();
    JButton Intermediate = new JButton();
    JButton Hard = new JButton();
    JButton Return = new JButton();

    GridLockFrame game;
    /**
     * Constructer for LevelManager class
     * @param game
     */
    public LevelManager(GridLockFrame game) {
        this.game = game;
        setLayout(null);

        add(Box.createVerticalStrut(50));
        Easy.setAlignmentX(CENTER_ALIGNMENT);
        Easy.setPreferredSize(new Dimension(10, 10));
        ImageIcon startIcon = new ImageIcon(Menu.class.getResource("/buttons/button_easy.png"));
        Easy.setIcon(startIcon);

        Easy.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	game.newPuzzlePanel("easy");
            }
        });
        Easy.setBounds(280,150,85,40);
        add(Easy);

        add(Box.createVerticalStrut(50));
        Intermediate.setAlignmentX(CENTER_ALIGNMENT);
        Intermediate.setPreferredSize(new Dimension(100, 20));
        ImageIcon icon = new ImageIcon(Menu.class.getResource("/buttons/button_intermediate.png"));
        Intermediate.setIcon(icon);

        Intermediate.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	game.newPuzzlePanel("intermediate");
            }
        });
        Intermediate.setBounds(250,250,150,42);
        add(Intermediate);

        Hard.setAlignmentX(CENTER_ALIGNMENT);
        Hard.setPreferredSize(new Dimension(100, 20));
        ImageIcon Hardicon = new ImageIcon(Menu.class.getResource("/buttons/button_hard.png"));
        Hard.setIcon(Hardicon);
        Hard.setBounds(280,350,86,42);

        Hard.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	game.newPuzzlePanel("hard");	
            }		
        });
        add(Hard);

        Return.setAlignmentX(CENTER_ALIGNMENT);
        Return.setPreferredSize(new Dimension(100, 20));
        ImageIcon Returnicon = new ImageIcon(Menu.class.getResource("/buttons/button_exit.png"));
        Return.setIcon(Returnicon);
        Return.setBounds(280,450,86,42);

        Return.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	game.mainMenu();
            }
        });
        add(Return);
    }

    /**
     * Drawing the level management menu on the screen
     * @pre the proper graphics is there to be painted
     * @post the level manager menu shows up
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(new ImageIcon(Menu.class.getResource("/Backgrounds/LvlManagerbackground.png")).getImage(), 0, 0, 680, 658, this);
    }
}