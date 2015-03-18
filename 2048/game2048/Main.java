package game2048;

import ucb.util.CommandArgs;

import game2048.gui.Game;
import static game2048.Main.Side.*;

/** The main class for the 2048 game.
 *  @author Yadel Abraham
 */
public class Main {

    /** Size of the board: number of rows and of columns. */
    static final int SIZE = 4;
    /** Number of squares on the board. */
    static final int SQUARES = SIZE * SIZE;
    /** this makes sure that "2048" remains unchained throughout the game. **/
    static final int WINNING = 2048;

    /** Symbolic names for the four sides of a board. */
    static enum Side { NORTH, EAST, SOUTH, WEST };

    /** The main program.  ARGS may contain the options --seed=NUM,
     *  (random seed); --log (record moves and random tiles
     *  selected.); --testing (take random tiles and moves from
     *  standard input); and --no-display. */
    public static void main(String... args) {
        CommandArgs options =
            new CommandArgs("--seed=(\\d+) --log --testing --no-display",
                            args);
        if (!options.ok()) {
            System.err.println("Usage: java game2048.Main [ --seed=NUM ] "
                               + "[ --log ] [ --testing ] [ --no-display ]");
            System.exit(1);
        }

        Main game = new Main(options);

        while (game.play()) {
            /* No action */
        }
        System.exit(0);
    }

    /** A new Main object using OPTIONS as options (as for main). */
    Main(CommandArgs options) {
        boolean log = options.contains("--log"),
            display = !options.contains("--no-display");
        long seed = !options.contains("--seed") ? 0 : options.getLong("--seed");
        _testing = options.contains("--testing");
        _game = new Game("2048", SIZE, seed, log, display, _testing);
    }

    /** Reset the score for the current game to 0 and clear the board. */
    void clear() {
        _score = 0;
        _count = 0;
        _game.clear();
        _game.setScore(_score, _maxScore);
        for (int r = 0; r < SIZE; r += 1) {
            for (int c = 0; c < SIZE; c += 1) {
                _board[r][c] = 0;
            }
        }
    }

    /** Play one game of 2048, updating the maximum score. Return true
     *  iff play should continue with another game, or false to exit. */
    boolean play() {
        clear();
        setRandomPiece();
        while (true) {
            setRandomPiece();
            if (gameOver()) {
                if (_score > _maxScore) {
                    _maxScore = _score;
                    _game.setScore(_score, _maxScore);

                }
                _game.endGame();
            }

        GetMove:
            while (true) {
                String key = _game.readKey();
                switch (key) {
                case "Up": case "Down": case "Left": case "Right":
                    if (!gameOver() && tiltBoard(keyToSide(key))) {
                        break GetMove;
                    }
                    break;
                case "New Game":
                    clear();
                    return true;
                case "Quit":
                    return false;
                default:
                    break;
                }
            }
        }
    }

    /** Return true iff the current game is over (no more moves
     *  possible). */
    boolean gameOver() {
        for (int r = 0; r < SIZE; r += 1) {
            for (int c = 0; c < SIZE; c += 1) {
                if (_board[r][c] == 0) {
                    return false;
                }
                if (_board[r][c] == WINNING) {
                    return true;
                }
                if (c < SIZE - 1 && _board[r][c] == _board[r][c + 1]) {
                    return false;
                }
                if (r < SIZE - 1 && _board[r][c] == _board[r + 1][c]) {
                    return false;
                }
                if (c > 0 && _board[r][c] == _board[r][c - 1]) {
                    return false;
                }
                if (r > 0 && _board[r][c] == _board[r - 1][c]) {
                    return false;
                }
            }
        }
        return true;
    }

    /** Add a tile to a random, empty position, choosing a value (2 or
     *  4) at random.  Has no effect if the board is currently full. */
    void setRandomPiece() {
        if (_count == SQUARES) {
            return;
        }
        int[] myTile = _game.getRandomTile();
        int val = myTile[0];
        int total = myTile[1];
        int total2 = myTile[2];
        if (_board[total][total2] == 0) {
            _game.addTile(val, total, total2);
            _board[total][total2] = val;
            _count += 1;
        } else {
            setRandomPiece();
        }
    }

    /** Perform the result of tilting the board toward SIDE.
     *  Returns true iff the tilt changes the board. **/
    boolean tiltBoard(Side side) {
        /* As a suggestion (see the project text), you might try copying
         * the board to a local array, turning it so that edge SIDE faces
         * north.  That way, you can re-use the same logic for all
         * directions.  (As usual, you don't have to). */
        int[][] board = new int[SIZE][SIZE];
        boolean changed = false;
        for (int r = 0; r < SIZE; r += 1) {
            for (int c = 0; c < SIZE; c += 1) {
                board[r][c] =
                    _board[tiltRow(side, r, c)][tiltCol(side, r, c)];
            }
        }
        for (int c = 0; c < SIZE; c += 1) {
            for (int r = 1; r < SIZE; r += 1) {
                int pointer = r;
                if (board[r][c] != 0) {
                    while (pointer > 0 && board[pointer - 1][c] == 0) {
                        pointer -= 1;
                    }
                    if (pointer > 0 && board[pointer - 1][c] == board[r][c]) {
                        int val   = 2 * board[r][c];
                        int row1a = tiltRow(side, r, c);
                        int col1a = tiltCol(side, r, c);
                        int row2a = tiltRow(side, pointer - 1, c);
                        int col2a = tiltCol(side, pointer - 1, c);
                        _game.mergeTile(board[r][c], val, row1a, col1a,
                                        row2a, col2a);
                        changed = true;
                        _score += val;
                        board[pointer - 1][c] = val + 1;
                        board[r][c] = 0;
                        _count -= 1;
                    } else if (pointer != r) {
                        int row1 = tiltRow(side, r, c);
                        int col1 = tiltCol(side, r, c);
                        int row2 = tiltRow(side, pointer, c);
                        int col2 = tiltCol(side, pointer, c);
                        _game.moveTile(board[r][c], row1, col1, row2  , col2);
                        board[pointer][c] = board[r][c];
                        board[r][c] = 0;
                        changed = true;
                    }
                }
            }
        }
        for (int r = 0; r < SIZE; r += 1) {
            for (int c = 0; c < SIZE; c += 1) {
                if (board[r][c] % 2 != 0) {
                    board[r][c] -= 1;
                }
                _board[tiltRow(side, r, c)][tiltCol(side, r, c)]
                    = board[r][c];
            }
        }
        _game.setScore(_score, _maxScore);
        _game.displayMoves();
        return changed;
    }


    /** Return the row number on a playing board that corresponds to row R
     *  and column C of a board turned so that row 0 is in direction SIDE (as
     *  specified by the definitions of NORTH, EAST, etc.).  So, if SIDE
     *  is NORTH, then tiltRow simply returns R (since in that case, the
     *  board is not turned).  If SIDE is WEST, then column 0 of the tilted
     *  board corresponds to row SIZE - 1 of the untilted board, and
     *  tiltRow returns SIZE - 1 - C. */
    int tiltRow(Side side, int r, int c) {
        switch (side) {
        case NORTH:
            return r;
        case EAST:
            return c;
        case SOUTH:
            return SIZE - 1 - r;
        case WEST:
            return SIZE - 1 - c;
        default:
            throw new IllegalArgumentException("Unknown direction");
        }
    }

    /** Return the column number on a playing board that corresponds to row
     *  R and column C of a board turned so that row 0 is in direction SIDE
     *  (as specified by the definitions of NORTH, EAST, etc.). So, if SIDE
     *  is NORTH, then tiltCol simply returns C (since in that case, the
     *  board is not turned).  If SIDE is WEST, then row 0 of the tilted
     *  board corresponds to column 0 of the untilted board, and tiltCol
     *  returns R. */
    int tiltCol(Side side, int r, int c) {
        switch (side) {
        case NORTH:
            return c;
        case EAST:
            return SIZE - 1 - r;
        case SOUTH:
            return SIZE - 1 - c;
        case WEST:
            return r;
        default:
            throw new IllegalArgumentException("Unknown direction");
        }
    }

    /** Return the side indicated by KEY ("Up", "Down", "Left",
     *  or "Right"). */
    Side keyToSide(String key) {
        switch (key) {
        case "Up":
            return NORTH;
        case "Down":
            return SOUTH;
        case "Left":
            return WEST;
        case "Right":
            return EAST;
        default:
            throw new IllegalArgumentException("unknown key designation");
        }
    }

    /** Represents the board: _board[r][c] is the tile value at row R,
     *  column C, or 0 if there is no tile there. */
    private final int[][] _board = new int[SIZE][SIZE];

    /** True iff --testing option selected. */
    private boolean _testing;
    /** THe current input source and output sink. */
    private Game _game;
    /** The score of the current game, and the maximum final score
     *  over all games in this session. */
    private int _score, _maxScore;
    /** Number of tiles on the board. */
    private int _count;
}
