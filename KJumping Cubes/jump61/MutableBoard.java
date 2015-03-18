package jump61;
import java.util.Stack;
import static jump61.Side.*;
import static jump61.Square.square;

/** A Jump61 board state that may be modified.
 *  @author Yadel Abraham.
 */
class MutableBoard extends Board {

    /** An N x N board in initial configuration. */
    MutableBoard(int N) {
        _size = N;
        listOfColors = new int[3];
        listOfColors[WHITE.ordinal()] = N * N;
        myboard = new Square[N][N];
        for (int r = 1; r <= _size; r++) {
            for (int c = 1; c <= _size; c++) {
                myboard[r - 1][c - 1] = Square.INITIAL;
            }
        }
    }


    /** A board whose initial contents are copied from BOARD0, but whose
     *  undo history is clear. */
    MutableBoard(Board board0) {
        myboard = new Square[board0.size()][board0.size()];
        for (int r = 1; r <= board0.size(); r++) {
            for (int c = 1; c <= board0.size(); c++) {
                myboard[r - 1][c - 1] = Square.INITIAL;
            }
        }

        copy(board0);
    }
    @Override
    void clear(int N) {
        _size = N;
        myboard = new Square[N][N];
        for (int r = 1; r <= _size; r++) {
            for (int c = 1; c <= _size; c++) {
                myboard[r - 1][c - 1] = Square.INITIAL;
            }
        }
        history = new Stack<Square[][]>();
        announce();
    }

    @Override
    void copy(Board board) {
        for (int r = 1; r <= _size; r++) {
            for (int c = 1; c <= _size; c++) {
                myboard[r - 1][c - 1] = board.get(r + 1, c + 1);
            }
        }
        history = new Stack<Square[][]>();

    }

    /** Copy the contents of BOARD into me, without modifying my undo
     *  history.  Assumes BOARD and I have the same size. */
    private void internalCopy(MutableBoard board) {
        for (int r = 1; r <= _size; r++) {
            for (int c = 1; c <= _size; c++) {
                myboard[r - 1][c - 1] = board.get(r + 1, c + 1);
            }
        }
    }
    @Override
    int size() {
        return _size;
    }

    @Override
    Square get(int n) {
        int rl = row(n);
        int cl = col(n);
        return myboard[rl - 1][cl - 1];
    }

    @Override
    int numOfSide(Side side) {
        int counter = 0;
        for (int r = 1; r <= _size; r++) {
            for (int c = 1; c <= _size; c++) {
                Side tempSide = myboard[r - 1][c - 1].getSide();
                if (side.equals(tempSide)) {
                    counter++;
                }
            }
        }
        return counter;
    }

    @Override
    int numPieces() {
        int spt = 0;
        for (int r = 1; r <= _size; r++) {
            for (int c = 1; c <= _size; c++) {
                spt += myboard[r - 1][c - 1].getSpots();
            }
        }
        return spt;
    }
    @Override
    /** Add a spot from PLAYER at row R, column C.  Assumes
     *  isLegal(PLAYER, R, C). */
    void addSpot(Side player, int r, int c) {
        markUndo();

        if (exists(r, c)) {
            int numNeighbors = neighbors(r, c);
            Square res = get(r, c);
            int store = res.getSpots();
            if (store + 1 > numNeighbors) {
                set(r, c, 1, player);
                if (getWinner() == null) {

                    check(player, r, c);
                    return;
                }
            }
            if ((store + 1) <= numNeighbors) {

                set(r, c, store + 1, player);
            }
            announce();
        }
    }
    /**
    * @param player is the same player.
    * @param r is the row at that square.
    * @param c is the column at that square.
    * this is and alternative to add spot avoids calling MarkUndo(). */
    void addSpotTwo(Side player, int r, int c) {
        if (exists(r, c)) {
            int numNeighbors = neighbors(r, c);
            Square res = get(r, c);
            int store = res.getSpots();
            if (store + 1 > numNeighbors) {
                set(r, c, 1, player);
                if (getWinner() == null) {

                    check(player, r, c);
                    return;
                }
            }
            if ((store + 1) <= numNeighbors) {
                set(r, c, store + 1, player);
            }
            announce();
        }
    }
    @Override
    void addSpot(Side player, int n) {
        int rw = row(n);
        int cl = col(n);
        addSpot(player, rw, cl);
        announce();
    }

    @Override
    void set(int r, int c, int num, Side player) {
        internalSet(sqNum(r, c), square(player, num));
    }

    @Override
    void set(int n, int num, Side player) {
        internalSet(n, square(player, num));
        announce();
    }

    @Override
    void undo() {
        if (!history.empty()) {
            Square[][]diff = history.pop();
            for (int r = 1; r < diff.length; r++) {
                for (int c = 1; c < diff.length; c++) {
                    myboard[r - 1][c - 1] = diff[r - 1][c - 1];
                }
            }
        }
    }

    /** Record the beginning of a move in the undo history. */
    private void markUndo() {
        Square[][] copiedItems = new Square[_size][_size];
        for (int r = 1; r < _size; r++) {
            for (int c = 1; c < _size; c++) {
                Square t = myboard[r - 1][c - 1];
                Side sayd = t.getSide();
                int bs = t.getSpots();
                copiedItems[r - 1][c - 1] = Square.square(sayd, bs);
            }
        }
        history.push(copiedItems);

    }

    /** Set the contents of the square with index IND to SQ. Update counts
     *  of numbers of squares of each color.  */
    private void internalSet(int ind, Square sq) {
        myboard[row(ind) - 1][col(ind) - 1] = sq;
    }

    /** Notify all Observers of a change. */
    private void announce() {
        setChanged();
        notifyObservers();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MutableBoard)) {
            return obj.equals(this);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return 0;
    }
    /**  @param player is the player.
    *    @param r is the row.
    *    @param c is the column.
    *    this is my helper method for addspot.*/
    private void check(Side player, int r, int c) {

        if (exists(r, c - 1)) {
            addSpotTwo(player, r, c - 1);
        }
        if (exists(r, c + 1)) {
            addSpotTwo(player, r, c + 1);
        }
        if (exists(r - 1, c)) {
            addSpotTwo(player, r - 1, c);
        }
        if (exists(r + 1, c)) {
            addSpotTwo(player, r + 1, c);
        }
    }
    /** a stack to represent my undo history.*/
    private Stack<Square[][]> history = new Stack<Square[][]>();
    /** its just size.*/
    private int _size;
    /** its my board.*/
    private Square[][] myboard;
    /** A list keeping track of what list has been aded and not.*/
    private int[] listOfColors;

}
