import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

/**
 * Represents the grid for the game and contains the methods to support user interaction.
 * @author Aayush Rawal (arawal2)
 * @author Dennis Polly Pynadath (dpynadat)
 * @author Saurabh Rane (ssrane)
 * @author Dhruv Dilipkumar Patel (dpatel81)
 * @author Sameet Krishnakumar (skris114)
 */
public class GameBoard extends JPanel implements ComponentListener, MouseListener, MouseMotionListener, Runnable {
    protected Dimension gridSize = null;
    private static final int CELL_SIZE = 10;
    protected ArrayList<Point> cell = new ArrayList<Point>(0);

    protected GameBoard() {

        addComponentListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        setBackground(Color.GRAY);
    }

    private void updateGrid() {
        ArrayList<Point> removeList = new ArrayList<Point>(0);
        for (Point current : cell) {
            if ((current.x > gridSize.width-1) || (current.y > gridSize.height-1)) {

                removeList.add(current);
            }
        }
        cell.removeAll(removeList);
        repaint();
    }

    private void addCell(int x, int y) {
        if (!cell.contains(new Point(x,y))) {
            cell.add(new Point(x,y));
        }
        repaint();
    }

    private void addCell(MouseEvent me) {
        int x = me.getPoint().x/CELL_SIZE-1;
        int y = me.getPoint().y/CELL_SIZE-1;
        if ((x >= 0) && (x < gridSize.width) && (y >= 0) && (y < gridSize.height)) {
            addCell(x,y);
        }
    }

    private void removeCell(int x, int y) {
        if(cell.contains(new Point(x,y))) {
            cell.remove(new Point(x, y));
        }
        repaint();
    }
    private void removeCell(MouseEvent me) {
        int x = me.getPoint().x/CELL_SIZE-1;
        int y = me.getPoint().y/CELL_SIZE-1;
        if ((x >= 0) && (x < gridSize.width) && (y >= 0) && (y < gridSize.height)) {
            removeCell(x,y);
        }
    }


    protected void resetBoard() {
        cell.clear();
    }



    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        try {
            for (Point newPoint : cell) {
                int xPos= CELL_SIZE + (CELL_SIZE*newPoint.x), yPos=CELL_SIZE + (CELL_SIZE*newPoint.y);
                graphics.setColor(Color.YELLOW);
                graphics.fillRect(xPos, yPos, CELL_SIZE, CELL_SIZE);
            }
        } catch (ConcurrentModificationException cme) {}

        graphics.setColor(Color.WHITE);
        for (int i=0; i<=gridSize.width; i++) {
            int xPos=(i*CELL_SIZE)+CELL_SIZE;
            graphics.drawLine(xPos, CELL_SIZE, xPos, CELL_SIZE + (CELL_SIZE*gridSize.height));
        }
        for (int i=0; i<=gridSize.height; i++) {
            int yPos=(i*CELL_SIZE)+CELL_SIZE;
            graphics.drawLine(CELL_SIZE, yPos, CELL_SIZE*(gridSize.width+1), yPos);
        }
    }

    @Override
    public void componentResized(ComponentEvent e) {

        gridSize = new Dimension(getWidth()/CELL_SIZE-2, getHeight()/CELL_SIZE-2);
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
            addCell(e);
        }
        else if(SwingUtilities.isRightMouseButton(e))
            removeCell(e);
    }
    @Override
    public void mouseReleased(MouseEvent e) {


    }
    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {

        if(SwingUtilities.isLeftMouseButton(e))
        {
            addCell(e);
        }
        else if(SwingUtilities.isRightMouseButton(e))
            removeCell(e);

    }
    @Override
    public void mouseMoved(MouseEvent e) {}

    @Override
    public void run() {
        boolean[][] gameBoard = new boolean[gridSize.width+2][gridSize.height+2];
        CellGeneration generate = new CellGeneration();
        ArrayList survivingCells= generate.evolve(gameBoard,cell);
        resetBoard();
        cell.addAll(survivingCells);
        repaint();
        try {
            Thread.sleep(1000 / 3);
            run();
        } catch (InterruptedException ex) {
        }

    }
}
