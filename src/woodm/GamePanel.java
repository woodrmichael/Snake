/*
 * Course: CSC1110 - 111
 * Fall 2023
 * Personal Project
 * Name: Michael Wood
 * Created: 11/22/2023
 * Updated: 11/22/2023
 */
package woodm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

/**
 * A GamePanel that extends the JPanel and implements an ActionListener.
 * Displays the actual game.
 * @author Michael Wood
 */
public class GamePanel extends JPanel implements ActionListener {
    private static final int SCREEN_WIDTH = 600;
    private static final int SCREEN_HEIGHT = 600;
    private static final int UNIT_SIZE = 25;
    private static final int TOTAL_UNITS = (SCREEN_WIDTH / UNIT_SIZE) * (SCREEN_HEIGHT / UNIT_SIZE);
    private static final int DELAY = 80;
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

    /**
     * Creates a new GamePanel and starts the game.
     */
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
        this.drawGrid(g);
        // Draws Apple
        g.setColor(Color.red);
        g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

        this.drawSnake(g);
        this.displayScore(g);
        if(!running) {
            this.gameOver(g);
        }
    }

    /**
     * Draws a grid with squares that are UNIT SIZE x UNIT SIZE in area.
     * @param g Graphics object g used to draw the lines.
     */
    public void drawGrid(Graphics g) {
        final int red = 67;
        final int green = 64;
        final int blue = 71;
        g.setColor(new Color(red, green, blue));
        for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
            g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT); // vertical lines
            g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE); // horizontal lines
        }
    }

    /**
     * Draws the Snake.
     * @param g Graphics object g used to draw the snake.
     */
    public void drawSnake(Graphics g) {
        final int red = 16;
        final int green = 148;
        final int blue = 51;
        for (int i = 0; i < this.bodyParts; i++) {
            if (i == 0) { // Sets head of snake to a different color.
                g.setColor(new Color(red, green, blue));
                g.fillRect(this.x[i], this.y[i], UNIT_SIZE, UNIT_SIZE);
            } else {
                g.setColor(Color.green);
                g.fillRect(this.x[i], this.y[i], UNIT_SIZE, UNIT_SIZE);
            }
        }
    }

    /**
     * Creates a new Apple in a random location in the grid.
     * The apple cannot spawn inside the snake.
     */
    public void newApple() {
        boolean flag;
        do {
            flag = false;
            this.appleX = this.generator.nextInt(SCREEN_WIDTH / UNIT_SIZE) * UNIT_SIZE;
            this.appleY = this.generator.nextInt(SCREEN_HEIGHT / UNIT_SIZE) * UNIT_SIZE;
            for(int i = 0; i < this.x.length; i++) {
                if(this.appleX == this.x[i] && i < this.y.length && this.appleY == this.y[i]) {
                    flag = true;
                    break;
                }
            }
            if(!flag) {
                for(int j = 0; j < this.y.length; j++) {
                    if(this.appleY == this.y[j] && j < this.x.length && this.appleX == this.x[j]) {
                        flag = true;
                        break;
                    }
                }
            }
        } while(flag);
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

    /**
     * Displays the score at the top of the screen.
     * @param g Graphics object g used to draw the text for the game over message.
     */
    public void displayScore(Graphics g) {
        final int fontSize = 40;
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, fontSize));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Score: " + this.applesEaten, (SCREEN_WIDTH -
                metrics.stringWidth("Score: " + this.applesEaten)) / 2, g.getFont().getSize());
    }

    /**
     * Prints out a message when you lose the game.
     * @param g Graphics object g used to draw the text for the game over message.
     */
    public void gameOver(Graphics g) {
        // Removes the apple from the screen once you lose.
        g.setColor(Color.black);
        g.fillRect(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

        this.drawGrid(g);
        this.drawSnake(g);
        this.displayScore(g);

        // Displays the Game over message to your screen.
        final int fontSize = 75;
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, fontSize));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over")) / 2,
                SCREEN_HEIGHT / 2);
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

    /**
     * MyKeyAdapter extends the KeyAdapter class and is used to get key inputs from user.
     */
    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_A:
                    if(direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_D:
                    if(direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                case KeyEvent.VK_W:
                    if(direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_S:
                    if(direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }
        }
    }
}
