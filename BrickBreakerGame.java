package com.Task_4;

import javax.swing.*;
import java.awt.event.KeyListener;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;


public class BrickBreakerGame extends JPanel implements KeyListener, Runnable {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int BRICK_WIDTH = 50;
    private static final int BRICK_HEIGHT = 20;
    private static final int PADDLE_WIDTH = 80;
    private static final int PADDLE_HEIGHT = 10;
    private static final int BALL_SIZE = 20;

    private boolean gameRunning = true;
    private int score = 0;
    private int lives = 3;
    private int level = 1;
    private int powerUpType = 0; // 0 = none, 1 = expand paddle, 2 = shrink paddle

    private Rectangle paddle;
    private Rectangle ball;
    private Rectangle[][] bricks;

    private int ballDX = -3;
    private int ballDY = -3;

    private Random random;

    public BrickBreakerGame() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        addKeyListener(this);
        setFocusable(true);

        paddle = new Rectangle(WIDTH / 2 - PADDLE_WIDTH / 2, HEIGHT - PADDLE_HEIGHT - 10, PADDLE_WIDTH, PADDLE_HEIGHT);
        ball = new Rectangle(WIDTH / 2 - BALL_SIZE / 2, HEIGHT / 2 - BALL_SIZE / 2, BALL_SIZE, BALL_SIZE);

        random = new Random();
        initializeBricks();

        new Thread(this).start();
    }

    private void initializeBricks() {
        bricks = new Rectangle[level + 3][level * 2 + 1];
        int x = 50;
        int y = 50;
        for (int i = 0; i < bricks.length; i++) {
            for (int j = 0; j < bricks[i].length; j++) {
                bricks[i][j] = new Rectangle(x, y, BRICK_WIDTH, BRICK_HEIGHT);
                x += BRICK_WIDTH + 10;
            }
            x = 50;
            y += BRICK_HEIGHT + 10;
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.setColor(Color.WHITE);
        g.fillRect(paddle.x, paddle.y, paddle.width, paddle.height);
        g.fillOval(ball.x, ball.y, ball.width, ball.height);

        for (Rectangle[] row : bricks) {
            for (Rectangle brick : row) {
                if (brick != null) {
                    g.fillRect(brick.x, brick.y, brick.width, brick.height);
                }
            }
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Score: " + score, 10, 20);
        g.drawString("Lives: " + lives, 10, 40);
        g.drawString("Level: " + level, 10, 60);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_LEFT && paddle.x > 0) {
            paddle.x -= 20;
        } else if (keyCode == KeyEvent.VK_RIGHT && paddle.x + paddle.width < WIDTH) {
            paddle.x += 20;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Not needed for this example
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not needed for this example
    }

    @Override
    public void run() {
        while (gameRunning) {
            moveBall();
            checkCollisions();
            checkGameState();

            repaint();

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void moveBall() {
        ball.x += ballDX;
        ball.y += ballDY;

        // Check for wall collisions
        if (ball.x < 0 || ball.x + ball.width > WIDTH) {
            ballDX = -ballDX;
        }
        if (ball.y < 0) {
            ballDY = -ballDY;
        }
    }

    private void checkCollisions() {
        // Check for paddle collision
        if (ball.intersects(paddle)) {
            ballDY = -ballDY;
        }

        // Check for brick collisions
        for (int i = 0; i < bricks.length; i++) {
            for (int j = 0; j < bricks[i].length; j++) {
                if (bricks[i][j] != null && ball.intersects(bricks[i][j])) {
                    bricks[i][j] = null;
                    score += 10;
                    ballDY = -ballDY;

                    // Spawn power-up
                    if (random.nextInt(10) == 0) {
                        powerUpType = random.nextInt(2) + 1;
                    }
                }
            }
        }
    }

    private void checkGameState() {
        // Check for game over
        if (ball.y + ball.height > HEIGHT) {
            lives--;
            if (lives == 0) {
                gameRunning = false;
                JOptionPane.showMessageDialog(this, "Game Over! Your score: " + score);
                System.exit(0);
            } else {
                resetBallPosition();
            }
        }

        // Check for level completion
        boolean levelCompleted = true;
        for (Rectangle[] row : bricks) {
            for (Rectangle brick : row) {
                if (brick != null) {
                    levelCompleted = false;
                    break;
                }
            }
            if (!levelCompleted) {
                break;
            }
        }

        if (levelCompleted) {
            level++;
            initializeBricks();
            resetBallPosition();
        }

        // Apply power-up
        if (powerUpType == 1) {
            paddle.width = PADDLE_WIDTH * 2;
        } else if (powerUpType == 2) {
            paddle.width = PADDLE_WIDTH / 2;
        }
    }

    private void resetBallPosition() {
        ball.x = WIDTH / 2 - BALL_SIZE / 2;
        ball.y = HEIGHT / 2 - BALL_SIZE / 2;
        ballDX = -3;
        ballDY = -3;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Brick Breaker Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(new BrickBreakerGame());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

