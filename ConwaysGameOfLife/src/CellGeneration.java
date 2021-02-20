import java.awt.*;
import java.util.ArrayList;

/**
 * Represents the rules for the evolution of cells.
 * @author Aayush Rawal (arawal2)
 * @author Dennis Polly Pynadath (dpynadat)
 * @author Saurabh Rane (ssrane)
 * @author Dhruv Dilipkumar Patel (dpatel81)
 * @author Sameet Krishnakumar (skris114)
 */
public class CellGeneration {

     ArrayList<Point> evolve(boolean[][] gameBoard, ArrayList<Point> point) {
        for (Point current : point) {
            gameBoard[current.x + 1][current.y + 1] = true;
        }
        ArrayList<Point> survivingCells = new ArrayList<Point>(0);
        for (int i = 1; i < gameBoard.length - 1; i++) {
            for (int j = 1; j < gameBoard[0].length - 1; j++) {
                int neighborCell = 0;
                if (gameBoard[i - 1][j - 1]) {
                    neighborCell++;
                }
                if (gameBoard[i + 1][j - 1]) {
                    neighborCell++;
                }
                if (gameBoard[i - 1][j + 1]) {
                    neighborCell++;
                }
                if (gameBoard[i][j - 1]) {
                    neighborCell++;
                }
                if (gameBoard[i - 1][j]) {
                    neighborCell++;
                }
                if (gameBoard[i][j + 1]) {
                    neighborCell++;
                }
                if (gameBoard[i + 1][j + 1]) {
                    neighborCell++;
                }
                if (gameBoard[i + 1][j]) {
                    neighborCell++;
                }

                if (gameBoard[i][j]) {
                    if ((neighborCell == 2) || (neighborCell == 3)) {
                        survivingCells.add(new Point(i - 1, j - 1));
                    }
                } else {
                    if (neighborCell == 3) {
                        survivingCells.add(new Point(i - 1, j - 1));
                    }
                }
            }
        }
        return survivingCells;
    }
}
