import java.awt.*;
import javax.swing.*;


public class GameOfLife extends JFrame {
    private static final Dimension MINIMUM_FRAME_SIZE = new Dimension(450, 450);

    public static void main(String[] args) {
        JFrame game = new GameLayout();
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.setTitle("Game of Life");
        game.setSize(700, 500); // Default Size
        game.setMinimumSize(MINIMUM_FRAME_SIZE);
        game.setVisible(true);
    }

}
