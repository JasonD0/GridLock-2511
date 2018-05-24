import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import javax.swing.*;
import java.awt.Dimension;
import javax.swing.JFrame;

public class LevelManager extends JPanel {
    JButton Easy = new JButton();
    JButton Intermeidate = new JButton();
    JButton Hard = new JButton();
    JButton Return = new JButton();

    GridLockFrame game;

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
            	game.newPuzzlePanel();
            }
        });
        Easy.setBounds(320,150,85,40);
        add(Easy);

        add(Box.createVerticalStrut(50));
        Intermeidate.setAlignmentX(CENTER_ALIGNMENT);
        Intermeidate.setPreferredSize(new Dimension(100, 20));
        ImageIcon icon = new ImageIcon(Menu.class.getResource("/buttons/button_intermediate.png"));
        Intermeidate.setIcon(icon);

        Intermeidate.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	game.newPuzzlePanel();
            }
        });
        Intermeidate.setBounds(285,250,150,42);
        add(Intermeidate);

        Hard.setAlignmentX(CENTER_ALIGNMENT);
        Hard.setPreferredSize(new Dimension(100, 20));
        ImageIcon Hardicon = new ImageIcon(Menu.class.getResource("/buttons/button_hard.png"));
        Hard.setIcon(Hardicon);
        Hard.setBounds(320,350,86,42);

        Hard.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	game.newPuzzlePanel();	
            }		
        });
        add(Hard);

        Return.setAlignmentX(CENTER_ALIGNMENT);
        Return.setPreferredSize(new Dimension(100, 20));
        ImageIcon Returnicon = new ImageIcon(Menu.class.getResource("/buttons/button_exit.png"));
        Return.setIcon(Returnicon);
        Return.setBounds(320,450,86,42);

        Return.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	game.mainMenu();
            }
        });
        add(Return);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(new ImageIcon(Menu.class.getResource("/Backgrounds/LvlManagerbackground.png")).getImage(), 0, 0, 697, 696, this);
    }
}