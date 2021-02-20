import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Represents GUI layout on window.
 * @author Aayush Rawal (arawal2)
 * @author Dennis Polly Pynadath (dpynadat)
 * @author Saurabh Rane (ssrane)
 * @author Dhruv Dilipkumar Patel (dpatel81)
 * @author Sameet Krishnakumar (skris114)
 */

public class GameLayout extends JFrame implements ActionListener {
    private GameBoard gameBoard;
    private Thread timer;
    private JButton playButton,stopButton,resetButton,instructionButton;
    private Container container;
    private GridBagConstraints grid;


    protected GameLayout() {

        container = getContentPane();
        container.setLayout(new GridBagLayout());
        
        grid = new GridBagConstraints();
        
        playButton=new JButton("PLAY");
        playButton.addActionListener(this);
        playButton.setBackground(Color.BLUE);
        playButton.setForeground(Color.WHITE);
        playButton.setFont(new Font("Arial", Font.BOLD, 20));
        grid.gridx = 0;
        grid.gridy = 1;
        grid.weightx = 0.5;
        container.add(playButton,grid);
        
        stopButton=new JButton("STOP");
        stopButton.addActionListener(this);
        stopButton.setBackground(Color.BLUE);
        stopButton.setForeground(Color.WHITE);
        stopButton.setFont(new Font("Arial", Font.BOLD, 20));
        grid.gridx = 1;
        grid.gridy = 1;
        grid.weightx = 0.5;
        container.add(stopButton,grid);
        
        resetButton=new JButton("RESET");
        resetButton.addActionListener(this);
        resetButton.setBackground(Color.BLUE);
        resetButton.setForeground(Color.WHITE);
        resetButton.setFont(new Font("Arial", Font.BOLD, 20));
        grid.gridx = 2;
        grid.gridy = 1;
        grid.weightx = 0.5;
        container.add(resetButton,grid);
        
        instructionButton=new JButton("INSTRUCTION");
        instructionButton.addActionListener(this);
        instructionButton.setBackground(Color.BLUE);
        instructionButton.setForeground(Color.WHITE);
        instructionButton.setFont(new Font("Arial", Font.BOLD, 20));
        grid.gridx = 3;
        grid.gridy = 1;
        grid.weightx = 0.5;
        container.add(instructionButton,grid);
        
        gameBoard = new GameBoard();
        grid.gridx = 0;
        grid.gridy = 0;
        grid.weightx = 1.0;
        grid.weighty = 1.0;
        grid.gridwidth=4;
        grid.fill = GridBagConstraints.BOTH;
        container.add(gameBoard,grid);


    }
    private void setPlayAndStop(boolean play) {
        if (play) {
            playButton.setEnabled(false);
            stopButton.setEnabled(true);
            timer = new Thread(gameBoard);
            timer.start();
        } else {
            playButton.setEnabled(true);
            stopButton.setEnabled(false);
            timer.interrupt();
        }
    }
    private void instruction() {
        JOptionPane.showMessageDialog(container,"Any live cell with fewer than two live neighbours dies," +
                        " as if by underpopulation.\n" +
                        "Any live cell with two or three live neighbours lives on to the next generation.\n" +
                        "Any live cell with more than three live neighbours dies, as if by overpopulation.\n" +
                        "Any dead cell with exactly three live neighbours becomes a live cell," +
                        " as if by reproduction.","Let's Go And Play timer.",
                JOptionPane.PLAIN_MESSAGE);
    }


    @Override
    public void actionPerformed(ActionEvent ae) {
        System.out.println(ae);
        if (ae.getSource().equals(resetButton)) {
            gameBoard.resetBoard();
            gameBoard.repaint();
        } else if (ae.getSource().equals(playButton)) {
            setPlayAndStop(true);
        } else if (ae.getSource().equals(stopButton)) {
            setPlayAndStop(false);
        } else if(ae.getSource().equals(instructionButton)){
            instruction();
        }
    }



}

