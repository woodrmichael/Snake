/*
 * Course: CSC1110 - 111
 * Fall 2023
 * Personal Project
 * Name: Michael Wood
 * Created: 11/22/2023
 * Updated: 11/22/2023
 */
package woodm;

import javax.swing.JFrame;

/**
 * A GameFrame which extends the JFrame Class
 * @author Michael Wood
 */
public class GameFrame extends JFrame {

    /**
     * Creates a visible GameFrame with a title of Snake.
     */
    public GameFrame() {
        this.add(new GamePanel());
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
