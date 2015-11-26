package com.developerpaul123.tictactoe.gameobjects;

import android.util.Log;

import com.developerpaul123.tictactoe.abstracts.MinimaxTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paul on 11/24/2015.
 */
public class MinimaxAI extends MinimaxTemplate<ComputerMove, Integer, Board > {

    public MinimaxAI() {
        super(PlayerType.COMPUTER_MINIMAX.getValue());
    }

    public Point performMove(Board b) {
        ComputerMove move = getBestMove(b, getPlayerType(), 9);
        Log.i("Computer", "Score: " + move.score());
        return move.point();
    }
    @Override
    public ComputerMove getBestMove(final Board board, Integer type, int depth) {

        // Generate possible next moves in a list.

        List<Point> nextMoves = generateMoves(board);

        // mySeed is maximizing; while oppSeed is minimizing
        int bestScore = (type == getPlayerType()) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int currentScore;
        Point bestPlay = new Point(-1, -1);

        if (nextMoves.isEmpty() || depth == 0) {
            // Gameover or depth reached, evaluate score
            bestScore = evaluate(board);
        } else {
            for (int i = 0; i < nextMoves.size(); i++) {
                Point play = nextMoves.get(i);
                // Try this move for the current "player"
                board.addAMove(play, type);
                if (type.equals(getPlayerType())) {  // mySeed (computer) is maximizing player
                    currentScore = getBestMove(board, PlayerType.USER.getValue(), depth-1).score();
                    if (currentScore > bestScore) {
                        bestScore = currentScore;
                        bestPlay = play;
                    }
                } else {  // oppSeed is minimizing player
                    currentScore = getBestMove(board, getPlayerType(), depth-1).score();
                    if (currentScore < bestScore) {
                        bestScore = currentScore;
                        bestPlay = play;
                    }
                }
                // Undo move
                board.removeAMove(play);
            }
        }
        return new ComputerMove(bestPlay, bestScore);
    }

    /** Find all valid next moves.
     Return List of moves in int[2] of {row, col} or empty list if gameover */
    private List<Point> generateMoves(Board b) {
        List<Point> nextMoves = new ArrayList<Point>(); // allocate List

        // If gameover, i.e., no next move
        if (b.hasOWon() || b.hasXWon()) {
            return nextMoves;   // return empty list
        }
        return b.getAvailablePoints();
    }

    /** The heuristic evaluation function for the current board
     @Return +100, +10, +1 for EACH 3-, 2-, 1-in-a-line for computer.
     -100, -10, -1 for EACH 3-, 2-, 1-in-a-line for opponent.
     0 otherwise   */
    private int evaluate(Board b) {
        int score = 0;
        // Evaluate score for each of the 8 lines (3 rows, 3 columns, 2 diagonals)
        score += evaluateWin(b, 0, 0, 0, 1, 0, 2);  // row 0
        score += evaluateWin(b, 1, 0, 1, 1, 1, 2);  // row 1
        score += evaluateWin(b, 2, 0, 2, 1, 2, 2);  // row 2
        score += evaluateWin(b, 0, 0, 1, 0, 2, 0);  // col 0
        score += evaluateWin(b, 0, 1, 1, 1, 2, 1);  // col 1
        score += evaluateWin(b, 0, 2, 1, 2, 2, 2);  // col 2
        score += evaluateWin(b, 0, 0, 1, 1, 2, 2);  // diagonal
        score += evaluateWin(b, 0, 2, 1, 1, 2, 0);  // alternate diagonal
        score += evaluateBlock(b, 0, 0, 0, 1, 0, 2);  // row 0
        score += evaluateBlock(b, 1, 0, 1, 1, 1, 2);  // row 1
        score += evaluateBlock(b, 2, 0, 2, 1, 2, 2);  // row 2
        score += evaluateBlock(b, 0, 0, 1, 0, 2, 0);  // col 0
        score += evaluateBlock(b, 0, 1, 1, 1, 2, 1);  // col 1
        score += evaluateBlock(b, 0, 2, 1, 2, 2, 2);  // col 2
        score += evaluateBlock(b, 0, 0, 1, 1, 2, 2);  // diagonal
        score += evaluateBlock(b, 0, 2, 1, 1, 2, 0);  // alternate diagonal
        return score;
    }

    /**
     * Heuristic evaluation for winning.
     * @param board the current board.
     * @param row1 row coordinate.
     * @param col1 column coordinate.
     * @param row2 second row coordinate.
     * @param col2 second column coordinate.
     * @param row3 third row coordinate.
     * @param col3 third column coordinate.
     * @return int a score.
     */
    private int evaluateWin(Board board, int row1, int col1, int row2, int col2, int row3, int col3) {
        if(board.hasOWon()) {
            return 150;
        }
        else if(board.hasXWon()) {
            return -150;
        }
        else if(board.isATie()) {
            return 0;
        }
        int[][] b = board.getBoard();
        int[] values = {b[row1][col1], b[row2][col2], b[row3][col3]};

        if(values[0] == values[1] && values[2] != values[0]) {
            if(values[0] == PlayerType.USER.getValue()) {
                return -75;
            }
            else if(values[0] == PlayerType.COMPUTER_MINIMAX.getValue()) {
                return 75;
            }
        }

        if(values[1] == values[2] && values[1] != values[0]) {
            if(values[1] == PlayerType.USER.getValue()) {
                return -75;
            }
            else if(values[1] == PlayerType.COMPUTER_MINIMAX.getValue()) {
                return 75;
            }
        }

        if(values[0] != values[1] && values[0] != values[2]) {
            if(values[0] == PlayerType.USER.getValue() && values[1] == PlayerType.NO_ONE.getValue()) {
                return -30;
            }
            else if(values[0] == PlayerType.COMPUTER_MINIMAX.getValue() && values[1] == PlayerType.NO_ONE.getValue()) {
                return 30;
            }
        }

        if(values[1] != values[0] && values[1] != values[2]) {
            if(values[1] == PlayerType.USER.getValue() && values[0] == PlayerType.NO_ONE.getValue()) {
                return -30;
            }
            else if(values[1] == PlayerType.COMPUTER_MINIMAX.getValue() && values[0] == PlayerType.NO_ONE.getValue()) {
                return 30;
            }
        }

        if(values[2] != values[1] && values[2] != values[0]) {
            if(values[2] == PlayerType.USER.getValue() && values[0] == PlayerType.NO_ONE.getValue()) {
                return -30;
            }
            else if(values[2] == PlayerType.COMPUTER_MINIMAX.getValue() && values[0] == PlayerType.NO_ONE.getValue()) {
                return 30;
            }
        }
        return 0;
    }

    /**
     * Evaluate if the user or the computer has blocked the other player.
     * @param b the current board.
     * @param row1 row coordinate.
     * @param col1 column coordinate.
     * @param row2 second row coordinate.
     * @param col2 second column coordinate.
     * @param row3 third row coordinate.
     * @param col3 third column coordinate.
     * @return int a score.
     */
    private int evaluateBlock(Board b, int row1, int col1, int row2, int col2, int row3, int col3) {
        int[][] board = b.getBoard();
        int[] values  = {board[row1][col1], board[row2][col2], board[row3][col3]};

        //check for blocks.
        if(values[0] == values[1] && values[0] != values[2] && values[2] != PlayerType.NO_ONE.getValue()
                && values[0] != PlayerType.NO_ONE.getValue()) {
            if(values[2] == PlayerType.COMPUTER_MINIMAX.getValue()) {
                return 100;
            }
            else if(values[2] == PlayerType.USER.getValue()) {
                return -100;
            }
        }

        else if(values[1] == values[2] && values[1] != values[0] && values[0] != PlayerType.NO_ONE.getValue()
                && values[1] != PlayerType.NO_ONE.getValue()) {
            if(values[0] == PlayerType.COMPUTER_MINIMAX.getValue()) {
                return 100;
            }
            else if(values[0] == PlayerType.USER.getValue()) {
                return -100;
            }
        }

        else if(values[0] == values[2] && values[0] != values[1] && values[1] != PlayerType.NO_ONE.getValue()
                && values[0] != PlayerType.NO_ONE.getValue()) {
            if(values[1] == PlayerType.COMPUTER_MINIMAX.getValue()) {
                return 100;
            }
            else if(values[1] == PlayerType.USER.getValue()) {
                return -100;
            }
        }

        return 0;
    }
}
