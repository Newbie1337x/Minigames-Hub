import javax.swing.*;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
    int winWidth = 800;
    int winHeight = 600;

    JFrame frame = new JFrame("Snake-Game");
    frame.setVisible(true);
    frame.setSize(winWidth, winHeight);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null);
    frame.setResizable(false);

    SnakeGame snakeGame = new SnakeGame(winWidth, winHeight);

    frame.add(snakeGame);
    frame.pack();
    snakeGame.requestFocus();
    }
}