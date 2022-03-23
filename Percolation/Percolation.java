import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final boolean[][] gridOpenClosedStates;
    private int openSitesCount;
    private final int gridSideLength;
    private final WeightedQuickUnionUF topSite;
    private final int virtualTopSiteIndex;
    private final int virtualBottomSiteIndex;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be an integer larger than 0");
        }
        gridSideLength = n;
        int gridSiteCount = gridSideLength * gridSideLength + 2;
        gridOpenClosedStates = new boolean[gridSideLength][gridSideLength];
        topSite = new WeightedQuickUnionUF(gridSiteCount);
        virtualTopSiteIndex = 0;
        virtualBottomSiteIndex = gridSiteCount - 1;

    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (isOpen(row, col)) {
            return;
        }

        int currentCellIndex = getCellIndex(row, col);

        gridOpenClosedStates[row - 1][col - 1] = true;

        if (row == 1) { // union virtual top
            topSite.union(currentCellIndex, virtualTopSiteIndex);
        }
        if (row == gridSideLength) { // union virtual bottom
            topSite.union(currentCellIndex, virtualBottomSiteIndex);
        }

        if (row > 1 && isOpen(row - 1, col)) {
            int onTopCellIndex = getCellIndex(row - 1, col);
            topSite.union(currentCellIndex, onTopCellIndex);
        }
        if (row < gridSideLength && isOpen(row + 1, col)) {
            int atBelowCellIndex = getCellIndex(row + 1, col);
            topSite.union(currentCellIndex, atBelowCellIndex);
        }
        if (col > 1 && isOpen(row, col - 1)) {
            int leftCellIndex = getCellIndex(row, col - 1);
            topSite.union(currentCellIndex, leftCellIndex);
        }
        if (col < gridSideLength && isOpen(row, col + 1)) {
            int rightCellIndex = getCellIndex(row, col + 1);
            topSite.union(currentCellIndex, rightCellIndex);
        }

        openSitesCount++;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateCellIndices(row, col);
        return gridOpenClosedStates[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validateCellIndices(row, col);
        int currentCellIndex = getCellIndex(row, col);
        return topSite.find(currentCellIndex) == topSite.find(virtualTopSiteIndex);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSitesCount;
    }

    // does the system percolate?
    public boolean percolates() {
        return topSite.find(virtualTopSiteIndex) == topSite.find(virtualBottomSiteIndex);
    }

    private int getCellIndex(int row, int col) {
        return (row - 1) * gridSideLength + col;
    }

    private void validateCellIndices(int row, int col) {
        if (row <= 0 || col <= 0 || row > gridSideLength || col > gridSideLength) {
            throw new IllegalArgumentException("row and col must each have values between 1 and n");
        }
    }

    // test client (optional)
    public static void main(String[] args) {
        int n = 10;

        Percolation percolation = new Percolation(n);
        percolation.open(1, 6);
        StdOut.print(percolation.isOpen(1, 6));
    }
}
