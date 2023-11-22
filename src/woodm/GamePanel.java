/*
 * Course: CSC1110 - 111
 * Fall 2023
 * Personal Project
 * Name: Michael Wood
 * Created: 11/22/2023
 */
package woodm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    private static final int SCREEN_WIDTH = 600;
    private static final int SCREEN_HEIGHT = 600;
    private static final int UNIT_SIZE = 25;
    private static final int TOTAL_UNITS = SCREEN_WIDTH * SCREEN_HEIGHT / UNIT_SIZE;
    private static final int DELAY = 75;
    private static final int STARTING_BODY_PARTS = 6;
    Timer timer;
    Random generator;
    private final int[] x;
    private final int[] y;
    private int bodyParts;
    private int applesEaten;
    private int appleX;
    private int appleY;
    private char direction;
    private boolean running;

    public GamePanel() {
        this.generator = new Random();
        this.x = new int[TOTAL_UNITS];
        this.y = new int[TOTAL_UNITS];
        this.bodyParts = STARTING_BODY_PARTS;
        this.direction = 'R';
        this.running = false;

        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        this.startGame();
    }

    public void startGame() {

    }

    @Override
    public void paintComponent(Graphics g) {

    }

    public void draw(Graphics g) {

    }

    public void newApple() {

    }

    public void move() {

    }

    public void checkApple() {

    }

    public void checkCollisions() {

    }

    public void gameOver(Graphics g) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
