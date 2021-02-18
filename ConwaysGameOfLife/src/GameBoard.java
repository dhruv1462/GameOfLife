import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class GameBoard extends JPanel implements ComponentListener, MouseListener, MouseMotionListener, Runnable {
    private Dimension gameBoardSize = null;
    private static final int BLOCK_SIZE = 10;
    private ArrayList<Point> point = new ArrayList<Point>(0);

    public GameBoard() {
        // Add resizing listener
        addComponentListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    private void updateGrid() {
        ArrayList<Point> removeList = new ArrayList<Point>(0);
        for (Point current : point) {
            if ((current.x > gameBoardSize.width-1) || (current.y > gameBoardSize.height-1)) {

                removeList.add(current);
            }
        }
        point.removeAll(removeList);
        repaint();
    }

    public void addPoint(int x, int y) {
        if (!point.contains(new Point(x,y))) {
            point.add(new Point(x,y));
        }
        repaint();
    }

    public void addPoint(MouseEvent me) {
        int x = me.getPoint().x/BLOCK_SIZE-1;
        int y = me.getPoint().y/BLOCK_SIZE-1;
        if ((x >= 0) && (x < gameBoardSize.width) && (y >= 0) && (y < gameBoardSize.height)) {
            addPoint(x,y);
        }
    }

    public void removePoint(int x, int y) {
        if(point.contains(new Point(x,y))) {
            point.remove(new Point(x, y));
        }
        repaint();
    }
    public void removePoint(MouseEvent me) {
        int x = me.getPoint().x/BLOCK_SIZE-1;
        int y = me.getPoint().y/BLOCK_SIZE-1;
        if ((x >= 0) && (x < gameBoardSize.width) && (y >= 0) && (y < gameBoardSize.height)) {
            removePoint(x,y);
        }
    }


    public void resetBoard() {
        point.clear();
    }



    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            for (Point newPoint : point) {
                // Draw new point
                g.setColor(Color.blue);
                g.fillRect(BLOCK_SIZE + (BLOCK_SIZE*newPoint.x), BLOCK_SIZE + (BLOCK_SIZE*newPoint.y), BLOCK_SIZE, BLOCK_SIZE);
            }
        } catch (ConcurrentModificationException cme) {}
        // Setup grid
        g.setColor(Color.BLACK);
        for (int i=0; i<=gameBoardSize.width; i++) {
            g.drawLine(((i*BLOCK_SIZE)+BLOCK_SIZE), BLOCK_SIZE, (i*BLOCK_SIZE)+BLOCK_SIZE, BLOCK_SIZE + (BLOCK_SIZE*gameBoardSize.height));
        }
        for (int i=0; i<=gameBoardSize.height; i++) {
            g.drawLine(BLOCK_SIZE, ((i*BLOCK_SIZE)+BLOCK_SIZE), BLOCK_SIZE*(gameBoardSize.width+1), ((i*BLOCK_SIZE)+BLOCK_SIZE));
        }
    }

    @Override
    public void componentResized(ComponentEvent e) {
        // Setup the game board size with proper boundries
        gameBoardSize = new Dimension(getWidth()/BLOCK_SIZE-2, getHeight()/BLOCK_SIZE-2);
        updateGrid();
    }
    @Override
    public void componentMoved(ComponentEvent e) {}
    @Override
    public void componentShown(ComponentEvent e) {}
    @Override
    public void componentHidden(ComponentEvent e) {}
    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {
        if(SwingUtilities.isLeftMouseButton(e))
        {
            addPoint(e);
        }
        else if(SwingUtilities.isRightMouseButton(e))
            removePoint(e);
        }
    @Override
    public void mouseReleased(MouseEvent e) {
        // Mouse was released (user clicked)

    }
    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {
        // Mouse is being dragged, user wants multiple selections
        if(SwingUtilities.isLeftMouseButton(e))
        {
            addPoint(e);
        }
        else if(SwingUtilities.isRightMouseButton(e))
            removePoint(e);

    }
    @Override
    public void mouseMoved(MouseEvent e) {}

    @Override
    public void run() {
        boolean[][] gameBoard = new boolean[gameBoardSize.width+2][gameBoardSize.height+2];
        for (Point current : point) {
            gameBoard[current.x+1][current.y+1] = true;
        }
        ArrayList<Point> survivingCells = new ArrayList<Point>(0);
        // Iterate through the array, follow game of life rules
        for (int i=1; i<gameBoard.length-1; i++) {
            for (int j=1; j<gameBoard[0].length-1; j++) {
                int surrounding = 0;
                if (gameBoard[i-1][j-1]) { surrounding++; }
                if (gameBoard[i+1][j-1]) { surrounding++; }
                if (gameBoard[i-1][j+1]) { surrounding++; }
                if (gameBoard[i][j-1])   { surrounding++; }
                if (gameBoard[i-1][j])   { surrounding++; }
                if (gameBoard[i][j+1])   { surrounding++; }
                if (gameBoard[i+1][j+1]) { surrounding++; }
                if (gameBoard[i+1][j])   { surrounding++; }

                if (gameBoard[i][j]) {
                    // Cell is alive, Can the cell live? (2-3)
                    if ((surrounding == 2) || (surrounding == 3)) {
                        survivingCells.add(new Point(i-1,j-1));
                    }
                } else {
                    // Cell is dead, will the cell be given birth? (3)
                    if (surrounding == 3) {
                        survivingCells.add(new Point(i-1,j-1));
                    }
                }
            }
        }
        resetBoard();
        point.addAll(survivingCells);
        repaint();
        try {
            Thread.sleep(1000/3);
            run();
        } catch (InterruptedException ex) {}
    }
}
