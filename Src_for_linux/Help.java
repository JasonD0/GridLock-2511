import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;

import javax.swing.*;

/**
 * Implements the help page of the game 
 * Shows instructions on how to play the game
 */

public class Help extends JPanel {
    private GridLockFrame game;
    private JTextPane helpText = new JTextPane();
    private JButton Return = new JButton();

    /**
     * Constructor for help
     * Creates the help page containing the instructions
     * @param game    link to the main menu
     */
    public Help(GridLockFrame game) {
        this.game = game;
        setLayout(null);

        // create the exit button to return to the main menu
        add(Box.createVerticalStrut(160));
        ImageIcon startIcon = new ImageIcon(Menu.class.getResource("/buttons/button_exit.png"/*return*/));
        Return.setIcon(startIcon);
        Return.setBounds(295,500, 88,43);
        Return.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.mainMenu();
            }
        });
        add(Return, BorderLayout.SOUTH);

        add(Box.createVerticalStrut(160));
        
        // create instructions 
        String s =
                "The objective of the game is to get the red car out of the game\n" +
                        "Vertical cars only move vertically!\n" +
                        "Horizontal cars only move horizontally!\n" +
                        "Drag these cars around the grid to give way for the red car to reach the exit\n" +
                        "Delete cars if you are stuck but you will receive a penalty and no rewards\n" +
                        "You can purchase deletes and you will not get the penalty\n" +
                        "Completing a level will earn you an animal\n" + 
                        "Collecting all the animals will unlock one run of the mini game where you earn gold\n" +
                        "Getting a money pig will unlock a run of the mini game\n" +
                        "Getting a rubber duck will give you free deletes\n";
        helpText.setText(s);
        helpText.setFont(new Font("Serif", Font.BOLD, 20));
        helpText.setForeground(Color.WHITE);
        helpText.setBounds(10,40,500,500);
        helpText.setOpaque(false);
        helpText.setBorder(BorderFactory.createEmptyBorder());
        helpText.setBackground(new Color(0,0,0,0));
        helpText.setLocation(105,80);
        add(helpText);
        setOpaque(false);
    }

    /**
     * draws the background
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(new ImageIcon(Menu.class.getResource("/Backgrounds/helpBackground.png")).getImage(), 0, 0, 697, 696, this);
    }
}
