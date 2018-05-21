import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;

import javax.swing.*;

public class Help extends JPanel {
    GridLockFrame game;
    JTextPane helpText = new JTextPane();
    JButton Return = new JButton();

    public Help(GridLockFrame game) {
        this.game = game;
        setLayout(null);

        add(Box.createVerticalStrut(160));
        //Return.setPreferredSize(new Dimension(100, 70));
        ImageIcon startIcon = new ImageIcon(Menu.class.getResource("button_exit.png"/*return*/));
        Return.setIcon(startIcon);
        Return.setBounds(275,500, 88,43);

        Return.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.mainMenu();
            }
        });
        add(Return, BorderLayout.SOUTH);

        add(Box.createVerticalStrut(160));
        String s =
                "\nThe objective of the game is to get the red car out of the game\n" +
                        "Vertical cars only move vertically!\n" +
                        "Horizontal cars only move horizontally!\n" +
                        "Drag these cars around the grid to give way for the red car to reach the exit\n" +
                        "Completing a level will earn you an animal\n" + 
                        "Collecting all the animals will ... \n" +
				        "Delete cars if you are stuck but you will receive a penalty and no rewards";
        helpText.setText(s);
        helpText.setFont(new Font("Serif", Font.HANGING_BASELINE, 19 ));
        helpText.setBounds(10,40,500,500);
        helpText.setOpaque(false);
        helpText.setBorder(BorderFactory.createEmptyBorder());
        helpText.setBackground(new Color(0,0,0,0));
        helpText.setLocation(105,80);
        add(helpText);
        setOpaque(false);
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(new ImageIcon(Menu.class.getResource("helpBackground.png")).getImage(), 0, 0, 680, 658, this);
    }
}
