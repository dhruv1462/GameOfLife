import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class GameOfLife extends JFrame implements ActionListener {
    private static final Dimension MINIMUM_FRAME_SIZE = new Dimension(450, 450);
    private GameBoard gameBoard;
    private Thread game;
    private JButton play_b,stop_b,reset_b,instruction_b;
    private Container cont;
    GridBagConstraints c;

    public static void main(String[] args) {
        JFrame game = new GameOfLife();
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.setTitle("Game of Life");
        game.setSize(700,500); // Default Size
        game.setMinimumSize(MINIMUM_FRAME_SIZE);
        game.setVisible(true);
    }

    public GameOfLife() {
        cont=getContentPane();
        cont.setLayout(new GridBagLayout());
        c= new GridBagConstraints();

        //button
        play_b=new JButton("PLAY");
        play_b.addActionListener(this);
        play_b.setBackground(Color.BLUE);
        play_b.setForeground(Color.WHITE);
        play_b.setFont(new Font("Arial", Font.BOLD, 20));
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0.5;
        cont.add(play_b,c);
        stop_b=new JButton("STOP");
        stop_b.addActionListener(this);
        stop_b.setBackground(Color.BLUE);
        stop_b.setForeground(Color.WHITE);
        stop_b.setFont(new Font("Arial", Font.BOLD, 20));
        c.gridx = 1;
        c.gridy = 1;
        c.weightx = 0.5;
        cont.add(stop_b,c);
        reset_b=new JButton("RESET");
        reset_b.addActionListener(this);
        reset_b.setBackground(Color.BLUE);
        reset_b.setForeground(Color.WHITE);
        reset_b.setFont(new Font("Arial", Font.BOLD, 20));
        c.gridx = 2;
        c.gridy = 1;
        c.weightx = 0.5;
        cont.add(reset_b,c);
        instruction_b=new JButton("INSTRUCTION");
        instruction_b.addActionListener(this);
        instruction_b.setBackground(Color.BLUE);
        instruction_b.setForeground(Color.WHITE);
        instruction_b.setFont(new Font("Arial", Font.BOLD, 20));
        c.gridx = 3;
        c.gridy = 1;
        c.weightx = 0.5;
        cont.add(instruction_b,c);
        //board
        gameBoard = new GameBoard();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0; // fill the rest of the space
        c.weighty = 1.0;
        c.gridwidth=4;
        c.fill = GridBagConstraints.BOTH;
        cont.add(gameBoard,c);
        //set layout



    }

    private void setPlayAndStop(boolean play) {
        if (play) {
            play_b.setEnabled(false);
            stop_b.setEnabled(true);
            game = new Thread(gameBoard);
            game.start();
        } else {
            play_b.setEnabled(true);
            stop_b.setEnabled(false);
            game.interrupt();
        }
    }
    private void instruction() {
        JOptionPane.showMessageDialog(cont,"Any live cell with fewer than two live neighbours dies," +
                        " as if by underpopulation.\n" +
                        "Any live cell with two or three live neighbours lives on to the next generation.\n" +
                        "Any live cell with more than three live neighbours dies, as if by overpopulation.\n" +
                        "Any dead cell with exactly three live neighbours becomes a live cell," +
                        " as if by reproduction.","Let's Go And Play Game.",
                JOptionPane.PLAIN_MESSAGE);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        System.out.println(ae);
        if (ae.getSource().equals(reset_b)) {
            gameBoard.resetBoard();
            gameBoard.repaint();
        } else if (ae.getSource().equals(play_b)) {
            setPlayAndStop(true);
        } else if (ae.getSource().equals(stop_b)) {
            setPlayAndStop(false);
        } else if(ae.getSource().equals(instruction_b)){
            instruction();
        }
    }



}
