import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class GameBoard extends JPanel implements ComponentListener, MouseListener, MouseMotionListener, Runnable {
    protected Dimension gameBoardSize = null;
    private static final int BLOCK_SIZE = 10;
    protected ArrayList<Point> point = new ArrayList<Point>(0);

    public GameBoard() {
        // Add resizing listener
        addComponentListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        setBackground(Color.GRAY);
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

    private void addPoint(int x, int y) {
        if (!point.contains(new Point(x,y))) {
            point.add(new Point(x,y));
        }
        repaint();
    }

    private void addPoint(MouseEvent me) {
        int x = me.getPoint().x/BLOCK_SIZE-1;
        int y = me.getPoint().y/BLOCK_SIZE-1;
        if ((x >= 0) && (x < gameBoardSize.width) && (y >= 0) && (y < gameBoardSize.height)) {
            addPoint(x,y);
        }
    }

    private void removePoint(int x, int y) {
        if(point.contains(new Point(x,y))) {
            point.remove(new Point(x, y));
        }
        repaint();
    }
    private void removePoint(MouseEvent me) {
        int x = me.getPoint().x/BLOCK_SIZE-1;
        int y = me.getPoint().y/BLOCK_SIZE-1;
        if ((x >= 0) && (x < gameBoardSize.width) && (y >= 0) && (y < gameBoardSize.height)) {
            removePoint(x,y);
        }
    }


    protected void resetBoard() {
        point.clear();
    }



    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            for (Point newPoint : point) {
                // Draw new point
                g.setColor(Color.YELLOW);
                g.fillRect(BLOCK_SIZE + (BLOCK_SIZE*newPoint.x), BLOCK_SIZE + (BLOCK_SIZE*newPoint.y), BLOCK_SIZE, BLOCK_SIZE);
            }
        } catch (ConcurrentModificationException cme) {}
        // Setup grid
        g.setColor(Color.WHITE);
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
        CellGeneration generate = new CellGeneration();
        ArrayList survivingCells= generate.evolve(gameBoard,point);
        resetBoard();
        point.addAll(survivingCells);
        repaint();
        try {
            Thread.sleep(1000 / 3);
            run();
        } catch (InterruptedException ex) {
        }

    }
}
