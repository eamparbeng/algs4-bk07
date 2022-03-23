import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.StdOut;

public class Board {
    private final int[][] tilesCopy;
    private final int dimension;
    private int blankRowIndex;
    private int blankColIndex;
    private final int manhatanVal;
    private final int hammingVal;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        tilesCopy = Arrays.copyOf(tiles, tiles.length);
        dimension = tilesCopy.length;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (tilesCopy[i][j] == 0) {
                    blankRowIndex = i;
                    blankColIndex = j;
                }
            }
        }
        hammingVal = computeHamming();
        manhatanVal = computeManhatan();
    }

    // string representation of this board
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append(tilesCopy.length + "\n");
        for (int[] rows : tilesCopy) {
            for (int cell : rows) {
                buffer.append(cell + "\t");
            }
            buffer.append("\n");
        }
        return buffer.toString();
    }

    // board dimension n
    public int dimension() {
        return tilesCopy.length;
    }

    // number of tiles out of place
    public int hamming() {
        return hammingVal;
    }

    private int computeHamming() {
        int hamming = 0;
        for (int i = 0; i < dimension; i++)
            for (int j = 0; j < dimension; j++)
                if (tilesCopy[i][j] != 0 && tilesCopy[i][j] != j + i * dimension + 1)
                    hamming++;
        return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return manhatanVal;
    }

    private int computeManhatan() {
        int manhattan = 0;
        for (int i = 0; i < dimension; i++)
            for (int j = 0; j < dimension; j++)
                if (tilesCopy[i][j] != 0 && tilesCopy[i][j] != j + i * dimension + 1)
                    manhattan += manhattanDistance(i, j, tilesCopy[i][j]);
        return manhattan;
    }

    private int manhattanDistance(int i, int j, int square) {
        square--;
        int horizontal = Math.abs(square % dimension - j);
        int vertical = Math.abs(square / dimension - i);
        return horizontal + vertical;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null)
            return false;
        if (!(y.getClass() == Board.class))
            return false;
        Board that = (Board) y;
        return this.dimension() == that.dimension() && Arrays.deepEquals(this.tilesCopy, that.tilesCopy);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        return determineNeighbours();
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] twinTiles = copy(tilesCopy);

        if (twinTiles[0][0] != 0 && twinTiles[0][1] != 0)
            return new Board(swapTiles(0, 0, 0, 1));
        else
            return new Board(swapTiles(1, 0, 1, 1));
    }

    // swap the value of two specified location and returns a copy
    private int[][] swapTiles(int row1, int col1, int row2, int col2) {
        int[][] copyOfTiles = new int[dimension][dimension];
        for (int row = 0; row < dimension; row++)
            copyOfTiles[row] = tilesCopy[row].clone();

        int tmp = tilesCopy[row1][col1];
        copyOfTiles[row1][col1] = tilesCopy[row2][col2];
        copyOfTiles[row2][col2] = tmp;

        return copyOfTiles;
    }

    private int[][] copy(int[][] blocks) {
        int[][] copy = new int[blocks.length][blocks.length];
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                copy[i][j] = blocks[i][j];
            }
        }
        return copy;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] testTiles = {
                { 8, 1, 3 },
                { 4, 0, 2 },
                { 7, 6, 5 }
        };
        Board tBoard = new Board(testTiles);

        StdOut.printf("Incoming Tiles %s\n", tBoard.toString());
        StdOut.printf("Hamming Distance %d\n", tBoard.hamming());
        StdOut.printf("Manhattan Distance %d\n", tBoard.manhattan());

        int[][] tNeighbourTiles = {
                { 1, 0, 3 },
                { 4, 2, 5 },
                { 7, 8, 6 }
        };
        Board nBoard = new Board(tNeighbourTiles);
        StdOut.printf("Initial nBoard Tiles %s\n", nBoard.toString());
        int i = 0;
        Iterable<Board> neighbours = nBoard.neighbors();
        for (Board neighbour : neighbours) {
            StdOut.printf("Neighbour %d\n %s\n", ++i, neighbour.toString());
        }

        StdOut.printf("tBoard twin1 %s\n", tBoard.twin());
        StdOut.printf("tBoard twin2 %s\n", tBoard.twin());
        StdOut.printf("tBoard twin3 %s\n", tBoard.twin());

        StdOut.printf("nBoard twin1 %s\n", nBoard.twin());
        StdOut.printf("nBoard twin2 %s\n", nBoard.twin());
        StdOut.printf("nBoard twin3 %s\n", nBoard.twin());

        StdOut.printf("nBoard == tBoard is %s\n", nBoard.equals(tBoard) ? "true" : "false");
        StdOut.printf("tBoard == tBoard is %s\n", tBoard.equals(tBoard) ? "true" : "false");

        int[][] exactTestTilesCopy = {
                { 8, 1, 3 },
                { 4, 0, 2 },
                { 7, 6, 5 }
        };
        Board altTBoard = new Board(exactTestTilesCopy);
        StdOut.printf("altTBoard == tBoard is %s\n", tBoard.equals(altTBoard) ? "true" : "false");
    }

    private ArrayList<Board> determineNeighbours() {
        ArrayList<Board> neigh = new ArrayList<Board>();

        if (blankRowIndex > 0)
            neigh.add(new Board(swapTiles(blankRowIndex, blankColIndex, blankRowIndex - 1, blankColIndex)));
        if (blankRowIndex < dimension - 1)
            neigh.add(new Board(swapTiles(blankRowIndex, blankColIndex, blankRowIndex + 1, blankColIndex)));
        if (blankColIndex > 0)
            neigh.add(new Board(swapTiles(blankRowIndex, blankColIndex, blankRowIndex, blankColIndex - 1)));
        if (blankColIndex < dimension - 1)
            neigh.add(new Board(swapTiles(blankRowIndex, blankColIndex, blankRowIndex, blankColIndex + 1)));

        return neigh;
    }

}