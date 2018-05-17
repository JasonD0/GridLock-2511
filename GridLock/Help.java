import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import javax.swing.*;
import java.awt.Dimension;

public class Help extends JPanel {
    GridLockFrame game;
    JTextPane helpText = new JTextPane();
    JButton Return = new JButton();

    public Help(GridLockFrame game) {
        this.game = game;
        setLayout(null);

        add(Box.createVerticalStrut(160));
        Return.setPreferredSize(new Dimension(100, 20));
        ImageIcon startIcon = new ImageIcon(Menu.class.getResource("button_return.png"));
        Return.setIcon(startIcon);
        Return.setBounds(275,500, 108,43);

        Return.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //LINK TO MAIN MENU
            }
        });
        add(Return, BorderLayout.SOUTH);

        add(Box.createVerticalStrut(160));
        String s =
                "The objective of the game is to get the red car out of the game                                   " +
                        "Vertical cars only move vertically!  " +
                        "Horizontal cars only move horizontally!  " +
                        "Drag these cars around the grid to give way for the red car to reach the exit";
        helpText.setText(s);
        helpText.setFont(new Font("Serif", Font.HANGING_BASELINE, 28 ));
        helpText.setBounds(10,40,500,500);
        helpText.setOpaque(false);
        helpText.setBorder(BorderFactory.createEmptyBorder());
        helpText.setBackground(new Color(0,0,0,0));
        helpText.setLocation(80,80);
        add(helpText);
        setOpaque(false);
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(new ImageIcon(Menu.class.getResource("helpBackground.png")).getImage(), 0, 0, 640, 658, this);
    }
}
