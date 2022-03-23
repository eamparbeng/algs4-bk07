import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {
    private final boolean solvable;
    private SearchNode currentNode;
    private final Stack<Board> solutionBoards;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null)
            throw new IllegalArgumentException("Initial board is null");

        solutionBoards = new Stack<>();
        MinPQ<SearchNode> boardPq = new MinPQ<SearchNode>();
        MinPQ<SearchNode> altPq = new MinPQ<SearchNode>();

        boardPq.insert(new SearchNode(initial));
        altPq.insert(new SearchNode(initial.twin()));

        SearchNode altCurrentNode = altPq.min();
        while (!boardPq.min().currentBoard.isGoal() && !altPq.min().currentBoard.isGoal()) {
            currentNode = boardPq.delMin();
            altCurrentNode = altPq.delMin();
            for (Board neighbour : currentNode.currentBoard.neighbors()) {
                if (currentNode.previousNode == null || (currentNode.previousNode != null
                        && !neighbour.equals(currentNode.previousNode.currentBoard))) {
                    boardPq.insert(new SearchNode(neighbour, currentNode));
                }
            }

            for (Board neighbour : altCurrentNode.currentBoard.neighbors()) {
                if (altCurrentNode.previousNode == null || (altCurrentNode.previousNode != null
                        && !neighbour.equals(altCurrentNode.previousNode.currentBoard))) {
                    altPq.insert(new SearchNode(neighbour, altCurrentNode));
                }
            }
        }

        solvable = boardPq.min().currentBoard.isGoal();
        if (solvable) {
            SearchNode current = boardPq.min();
            while (current.previousNode != null) {
                solutionBoards.push(current.currentBoard);
                current = current.previousNode;
            }
            solutionBoards.push(current.currentBoard);
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return isSolvable() ? solutionBoards.size() - 1 : -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable())
            return null;

        // need a stack to output the solution in a reversed order
        Stack<Board> solution = new Stack<Board>();
        SearchNode current = currentNode;
        while (current.previousNode != null) {
            solution.push(current.currentBoard);
            current = current.previousNode;
        }
        solution.push(current.currentBoard);
        return solution;
    }

    private class SearchNode implements Comparable<SearchNode> {
        private final Board currentBoard;
        private final int moves;
        private final SearchNode previousNode;
        private final int priority;

        private SearchNode(Board currentBoard) {
            this.currentBoard = currentBoard;
            this.moves = 0;
            this.previousNode = null;
            this.priority = moves + currentBoard.manhattan();
        }

        private SearchNode(Board currentBoard, SearchNode previous) {
            this.currentBoard = currentBoard;
            this.moves = previous.moves + 1;
            this.previousNode = previous;
            this.priority = moves + currentBoard.manhattan();
        }

        @Override
        public int compareTo(SearchNode that) {
            return this.priority - that.priority;
        }
    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        String argsFile = "puzzle04.txt";
        if (args.length > 0)
            argsFile = args[0];
        In in = new In(argsFile);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}