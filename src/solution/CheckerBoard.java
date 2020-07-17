package solution;

import battleship.BattleShip;
import java.awt.Point;
import java.util.LinkedList;
import java.util.Queue;

/**
 * A Sample checkerboard shooter Hits a checkerboard until find a hit After hit,
 * switches to finding other nearby ship elements till a ship has been sunk with
 * a hit
 *
 */
public class CheckerBoard {

    // Gamesize to get the board size
    private final int gameSize;
    private final BattleShip battleShip;

    // An array of previously taken shots
    private int[][] previousShots;

    // Queue to keep track of what to hit next
    Queue<Integer> xCoordinatesToShoot = new LinkedList<>();
    Queue<Integer> yCoordinatesToShoot = new LinkedList<>();

    // CurrentShot variables to be used across functions
    int xCurrentShot = 5;
    int yCurrentShot = 5;

    // Sank to keep track of number of ships sank
    int howManySank = 0;

    // Checkerboard Co-ordinates
    Queue<Integer> xCheckerBoard = new LinkedList<>();
    Queue<Integer> yCheckerBoard = new LinkedList<>();

    /**
     * Constructor keeps a copy of the BattleShip instance
     *
     * @param b previously created battleship instance - should be a new game
     */
    public CheckerBoard(BattleShip b) {
        this.previousShots = new int[10][10];
        battleShip = b;
        gameSize = b.BOARDSIZE;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                previousShots[i][j] = 0;
            }
        }

        // Fill the checkerboard options to the queue
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (i % 2 == 0 && j % 2 == 0) {
                    xCheckerBoard.add(i);
                    yCheckerBoard.add(j);
                }
                if (i % 2 == 1 && j % 2 == 1) {
                    xCheckerBoard.add(i);
                    yCheckerBoard.add(j);
                }
            }
        }
    }

    /**
     * Checks if we have already made a shot to given x and y coordinates
     * @param x
     * @param y
     * @return
     */
    public boolean alreadyShot(int x, int y) {
        if (previousShots[x][y] == 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Create a random shot and calls the battleship shoot method
     *
     * @return true if a Ship is hit, false otherwise
     */
    public boolean fireShot() {

        // Check if we have dont coordinate to shoot in the queue
        if (xCoordinatesToShoot.isEmpty()) {
            // If we do not, get a random hit point from checkerboard
            getRandomShotPoint();
        } else {
            // If not, get the next coordinate in the queue
            while (alreadyShot(xCurrentShot, yCurrentShot)) {
                if (xCoordinatesToShoot.isEmpty()) {
                    getRandomShotPoint();
                } else {
                    xCurrentShot = xCoordinatesToShoot.remove();
                    yCurrentShot = yCoordinatesToShoot.remove();
                }
            }
        }

        // Set that we are shooting the current coordinates
        previousShots[xCurrentShot][yCurrentShot] = 1;

        // Initialize the point object to make a shot
        Point shot = new Point(xCurrentShot, yCurrentShot);

        // Take a shot and store if we got a hit
        boolean hit = battleShip.shoot(shot);
        if (hit) {
            // If we just sank a ship, do not add surrounding coordinates
            if (howManySank < battleShip.numberOfShipsSunk()) {
                howManySank = battleShip.numberOfShipsSunk();
            } else {
                // Else add surrounding coordinates
                if (yCurrentShot < 9) {
                    xCoordinatesToShoot.add(xCurrentShot);
                    yCoordinatesToShoot.add(yCurrentShot + 1);
                }
                if (yCurrentShot > 0) {
                    xCoordinatesToShoot.add(xCurrentShot);
                    yCoordinatesToShoot.add(yCurrentShot - 1);
                }
                if (xCurrentShot < 9) {
                    xCoordinatesToShoot.add(xCurrentShot + 1);
                    yCoordinatesToShoot.add(yCurrentShot);
                }
                if (xCurrentShot > 0) {
                    xCoordinatesToShoot.add(xCurrentShot - 1);
                    yCoordinatesToShoot.add(yCurrentShot);
                }
            }
        }
        return hit;
    }

    /**
     * Function to set the Random Shot Point from the checkerboard queue
     */
    public void getRandomShotPoint() {
        while (alreadyShot(xCurrentShot, yCurrentShot)) {
            if (xCheckerBoard.isEmpty()) {
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        System.out.print("[" + i + "," + j + "]" + previousShots[i][j] + " ");
                    }
                    System.out.println("");
                }
            }
            xCurrentShot = xCheckerBoard.remove();
            yCurrentShot = yCheckerBoard.remove();
        }
    }
}
