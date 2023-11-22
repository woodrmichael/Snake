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
    private Timer timer;
    private final Random generator;
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

    /**
     * Adds an apple to the screen.
     * Starts the game.
     * Starts the timer.
     */
    public void startGame() {
        this.newApple();
        this.running = true;
        this.timer = new Timer(DELAY, this);
        this.timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.draw(g);
    }

    /**
     * Draws all components of the game.
     * @param g Graphics object g used to draw everything needed for the game.
     */
    public void draw(Graphics g) {
        if(running) {
            // Draws Lines across x-axis then y-axis to make a grid.
            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT); // vertical lines
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE); // horizontal lines
            }

            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < this.bodyParts; i++) {
                if (i == 0) { // Sets head of snake to a different color.
                    g.setColor(Color.blue);
                    g.fillRect(this.x[i], this.y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(Color.green);
                    g.fillRect(this.x[i], this.y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
        } else {
            this.gameOver(g);
        }
    }

    /**
     * Creates a new Apple in a random location in the grid.
     */
    public void newApple() {
        this.appleX = this.generator.nextInt(SCREEN_WIDTH / UNIT_SIZE) * UNIT_SIZE;
        this.appleY = this.generator.nextInt(SCREEN_HEIGHT / UNIT_SIZE) * UNIT_SIZE;
    }

    /**
     * Moves the snake based on the direction it is facing.
     */
    public void move() {
        for(int i = this.bodyParts; i > 0; i--) {
            this.x[i] = this.x[i - 1];
            this.y[i] = this.y[i - 1];

        }

        switch(this.direction) {
            case 'U':
                this.y[0] = this.y[0] - UNIT_SIZE;
                break;
            case 'D':
                this.y[0] = this.y[0] + UNIT_SIZE;
                break;
            case 'L':
                this.x[0] = this.x[0] - UNIT_SIZE;
                break;
            case 'R':
                this.x[0] = this.x[0] + UNIT_SIZE;
                break;
        }
    }

    /**
     * Checks to see if the snake has hit an apple.
     */
    public void checkApple() {
        if(this.x[0] == this.appleX && this.y[0] == this.appleY) {
            this.bodyParts++;
            this.applesEaten++;
            this.newApple();
        }


    }

    /**
     * Checks to see if the snake has hit itself or hit any of the borders.
     * Stops the game if that has happened.
     */
    public void checkCollisions() {
        // Checks if head of snake collides with body.
        for(int i = this.bodyParts; i > 0; i--) {
            if(this.x[0] == x[i] && this.y[0] == this.y[i]) {
                this.running = false;
                break;
            }
        }
        // Checks if head of snake touches left or right border.
        if(this.x[0] < 0 || this.x[0] > SCREEN_WIDTH) {
            this.running = false;
        }
        // Checks if head of snake touches top or bottom border.
        if(this.y[0] < 0 || this.y[0] > SCREEN_HEIGHT) {
            this.running = false;
        }
        if(!running) {
            this.timer.stop();
        }
    }

    public void gameOver(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font(""))
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(this.running) {
            this.move();
            this.checkApple();
            this.checkCollisions();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if(direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }
        }
    }
}
