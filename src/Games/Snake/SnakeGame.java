package Games.Snake;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {
    int boardWidth, boardHeight;
    int tileSize = 25;
    Tile snakeHead;
    ArrayList<Tile> snakeBody;
    Tile food;
    Random rand;


    Timer gameLoop;
    int velocityX;
    int velocityY;

    boolean gameOver = false;

    private static class Tile {
        int x;
        int y;

        public Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public SnakeGame(int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);
        snakeHead = new Tile(boardWidth / (2 * tileSize), boardHeight / (2 * tileSize));


        food = new Tile(10, 10);
        rand = new Random();
        snakeBody = new ArrayList<>();
        placeFood();

        gameLoop = new Timer(100, this);
        gameLoop.start();

        velocityX = 0;
        velocityY = 0;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!gameOver) {
            draw(g);
        } else {
            drawGameOver(g);
        }
    }

    private void draw(Graphics g) {
        // Dibujar comida
        g.setColor(Color.white);
        g.fillRect(food.x * tileSize, food.y * tileSize, tileSize -10, tileSize -10);

        // Dibujar cabeza de la serpiente
        g.setColor(Color.green);
        g.fillRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize);

        // Dibujar cuerpo de la serpiente

        for (Tile snakePart : snakeBody) {
            g.fillRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize);
        }
    }

    private void drawGameOver(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font("Arial", Font.BOLD, 36));
        String message = "Game Over!";
        FontMetrics metrics = g.getFontMetrics();
        int x = (boardWidth - metrics.stringWidth(message)) / 2;
        int y = boardHeight / 2;
        g.drawString(message, x, y);
        g.setFont(new Font("Arial", Font.BOLD, 17));
        g.drawString("Press Enter to restart.", x + 17, y + metrics.getHeight());
    }

    private void placeFood() {
        food.x = rand.nextInt(boardWidth / tileSize);
        food.y = rand.nextInt(boardHeight / tileSize);
    }

    private void move() {
        Tile previousHeadPosition = new Tile(snakeHead.x, snakeHead.y);



        // Mover la cabeza
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;

        // Colisión con comida
        if (collision(snakeHead, food)) {
            snakeBody.add(0, previousHeadPosition);
            placeFood();
            SoundManager.play("eat");
        } else if (!snakeBody.isEmpty()) {
            // Mover el cuerpo
            snakeBody.add(0, previousHeadPosition);
            snakeBody.remove(snakeBody.size() - 1);
        }

        // Game Over por colisión con el cuerpo
        for (Tile snakePart : snakeBody) {
            if (collision(snakeHead, snakePart)) {
                gameOver = true;
                return;
            }
        }

        // Game Over por salir del tablero
        if (snakeHead.x < 0 || snakeHead.x >= boardWidth / tileSize ||
                snakeHead.y < 0 || snakeHead.y >= boardHeight / tileSize) {
            gameOver = true;
        }
    }

    private boolean collision(Tile tile1, Tile tile2) {
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            move();
            repaint();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if ((e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) && velocityY != 1) {
            velocityX = 0;
            velocityY = -1;
        } else if ((e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) && velocityY != -1) {
            velocityX = 0;
            velocityY = 1;
        } else if ((e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) && velocityX != 1) {
            velocityX = -1;
            velocityY = 0;
        } else if ((e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) && velocityX != -1) {
            velocityX = 1;
            velocityY = 0;
        }
        if (gameOver && KeyEvent.VK_ENTER == e.getKeyCode()) {
            resetGame();
        }
    }

    private void resetGame() {
        gameOver = false;
        snakeHead = new Tile(boardWidth / (2 * tileSize), boardHeight / (2 * tileSize));
        snakeBody.clear(); // Limpia el cuerpo de la serpiente
        placeFood(); // Reubica la comida
        velocityX = 0; // Detiene el movimiento inicial
        velocityY = 0;
        gameOver = false; // Restablece el estado del juego
        gameLoop.start(); // Reinicia el temporizador del juego
        repaint(); // Redibuja el panel
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }


}
