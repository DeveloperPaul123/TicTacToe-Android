package com.developerpaul123.tictactoe.gameobjects;

import android.util.Log;

import com.developerpaul123.tictactoe.abstracts.MinimaxTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paul on 11/24/2015.
 * Minimax AI player for Tic Tac Toe.
 */
public class MinimaxAI extends MinimaxTemplate<ComputerMove, Integer, ClassicBoard> {

    /**
     * Default constructor.
     * Pass super call with MINIMAX player type.
     */
    public MinimaxAI() {
        super(PlayerType.COMPUTER_MINIMAX.getValue());
    }

    /**
     * Performs the best possible move for this player on the given board.
     * @param b the current board.
     * @return a Point that corresponds to the AI's move on the board.
     */
    public Point performMove(ClassicBoard b) {
//        ComputerMove move = getBestMove(b, getPlayerType(), 1);
        ComputerMove move = minimax(b, getPlayerType());
        Log.i("Computer", "Score: " + move.score());
        return move.point();
    }

    /**
     * Pure minimax algorithm. This is a perfect AI player.
     * @param classicBoard the current classicBoard.
     * @param type the player type.
     * @return the best ComputerMove.
     */
    public ComputerMove minimax(ClassicBoard classicBoard, int type) {

        List<Point> availables = classicBoard.getAvailablePoints();

        if(classicBoard.isGameOver()) {
            if(classicBoard.hasOWon()) {
                return new ComputerMove(1000);
            }
            else if(classicBoard.hasXWon()) {
                return new ComputerMove(-1000);
            }
            else {
                return new ComputerMove(0);
            }
        }

        List<ComputerMove> mMoves = new ArrayList<>();

        for(int i = 0; i < availables.size(); i++) {
            Point p = availables.get(i);
            classicBoard.addAMove(p, type);
            ComputerMove move = new ComputerMove();
            if(type == PlayerType.USER.getValue()) {
                move = minimax(classicBoard, PlayerType.COMPUTER_MINIMAX.getValue());
            }
            else if(type == PlayerType.COMPUTER_MINIMAX.getValue()) {
                move = minimax(classicBoard, PlayerType.USER.getValue());
            }
            move.setPoint(p);
            mMoves.add(move);
            classicBoard.removeAMove(p);
        }

        //get the best move for the respective player.
        int bestScore = type == PlayerType.COMPUTER_MINIMAX.getValue() ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int bestPos = 0;
        for(int j = 0; j < mMoves.size(); j++) {
            ComputerMove move = mMoves.get(j);
            switch (type) {
                //computer
                case 2:
                    if(move.score() > bestScore) {
                        bestScore = move.score();
                        bestPos = j;
                    }
                    break;
                //user
                case 1:
                    if(move.score() < bestScore) {
                        bestScore = move.score();
                        bestPos = j;
                    }
                    break;
            }
        }

        return mMoves.get(bestPos);
    }

    @Override
    public ComputerMove getBestMove(final ClassicBoard classicBoard, Integer type, int depth) {

        // Generate possible next moves in a list.

        List<Point> nextMoves = generateMoves(classicBoard);

        // mySeed is maximizing; while oppSeed is minimizing
        int bestScore = (type == getPlayerType()) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int currentScore;
        Point bestPlay = new Point(-1, -1);

        if (nextMoves.isEmpty() || depth == 0) {
            // Gameover or depth reached, evaluate score
            bestScore = evaluate(classicBoard);
        } else {
            for (int i = 0; i < nextMoves.size(); i++) {
                Point play = nextMoves.get(i);
                // Try this move for the current "player"
                classicBoard.addAMove(play, type);
                if (type.equals(getPlayerType())) {  // mySeed (computer) is maximizing player
                    currentScore = getBestMove(classicBoard, PlayerType.USER.getValue(), depth-1).score();
                    if (currentScore > bestScore) {
                        bestScore = currentScore;
                        bestPlay = play;
                    }
                } else {  // oppSeed is minimizing player
                    currentScore = getBestMove(classicBoard, getPlayerType(), depth-1).score();
                    if (currentScore < bestScore) {
                        bestScore = currentScore;
                        bestPlay = play;
                    }
                }
                // Undo move
                classicBoard.removeAMove(play);
            }
        }
        return new ComputerMove(bestPlay, bestScore);
    }

    /**
     * Get a list of all possible, valid moves. Returns an empty list if the game is over.
     * @param b the current board.
     * @return a List of Point objects, empty if the game is over.
     */
    private List<Point> generateMoves(ClassicBoard b) {
        List<Point> nextMoves = new ArrayList<Point>(); // allocate List

        // If gameover, i.e., no next move
        if (b.hasOWon() || b.hasXWon()) {
            return nextMoves;   // return empty list
        }
        return b.getAvailablePoints();
    }


    /**
     * Heuristic score for the current board. Wins are heavily favored,
     * then blocks, then forks, then 1 or 2 in a row with an empty space.
     * @param b the current board.
     * @return the total score for a given player.
     */
    private int evaluate(ClassicBoard b) {
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
        score += evaluateFork(b);
        return score;
    }

    /**
     * Heuristic evaluation for winning.
     * @param classicBoard the current classicBoard.
     * @param row1 row coordinate.
     * @param col1 column coordinate.
     * @param row2 second row coordinate.
     * @param col2 second column coordinate.
     * @param row3 third row coordinate.
     * @param col3 third column coordinate.
     * @return int a score.
     */
    private int evaluateWin(ClassicBoard classicBoard, int row1, int col1, int row2, int col2, int row3, int col3) {
        if(classicBoard.hasOWon()) {
            return 1000;
        }
        else if(classicBoard.hasXWon()) {
            return -1000;
        }
        else {
            return 0;
        }
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
    private int evaluateBlock(ClassicBoard b, int row1, int col1, int row2, int col2, int row3, int col3) {
        int[][] board = b.getBoard();
        int[] values  = {board[row1][col1], board[row2][col2], board[row3][col3]};
        int score = 500;
        //check for blocks.

        //positions 0 and 1 are the same, position 2 isn't.
        if(values[0] == values[1] && values[0] != values[2] && values[2] != PlayerType.NO_ONE.getValue()
                && values[0] != PlayerType.NO_ONE.getValue()) {
            if(values[2] == PlayerType.COMPUTER_MINIMAX.getValue()) {
                return score;
            }
            else if(values[2] == PlayerType.USER.getValue()) {
                return -1*score;
            }
        }

        //positions 1 and 2 are the same, position 0 isn't.
        else if(values[1] == values[2] && values[1] != values[0] && values[0] != PlayerType.NO_ONE.getValue()
                && values[1] != PlayerType.NO_ONE.getValue()) {
            if(values[0] == PlayerType.COMPUTER_MINIMAX.getValue()) {
                return score;
            }
            else if(values[0] == PlayerType.USER.getValue()) {
                return -1*score;
            }
        }

        //positions 0 and 2 are the same middle value (position 1) isn't.
        else if(values[0] == values[2] && values[0] != values[1] && values[1] != PlayerType.NO_ONE.getValue()
                && values[0] != PlayerType.NO_ONE.getValue()) {
            if(values[1] == PlayerType.COMPUTER_MINIMAX.getValue()) {
                return score;
            }
            else if(values[1] == PlayerType.USER.getValue()) {
                return -1*score;
            }
        }

        //no blocks so return 0.
        return 0;
    }

    /**
     * Evaluate the board and get a score based on if there is a fork made.
     * @param b the current board.
     * @return
     */
    private int evaluateFork(ClassicBoard b) {
        //check for forks.

        //Split fork with one move in the center.
        int[][] board = b.getBoard();
        if(board[0][0] == board[1][1] && board[0][0] == board[0][2]) {
            if(board[0][0] == PlayerType.USER.getValue()) {
                return -250;
            }
            else if(board[0][0] == PlayerType.COMPUTER_MINIMAX.getValue()) {
                return 250;
            }
        }
        else if(board[1][1] == board[0][2] && board[1][1] == board[2][2]) {
            if(board[1][1] == PlayerType.USER.getValue()) {
                return -250;
            }
            else if(board[1][1] == PlayerType.COMPUTER_MINIMAX.getValue()) {
                return 250;
            }
        }
        else if(board[1][1] == board[0][0] && board[1][1] == board[2][0]) {
            if(board[1][1] == PlayerType.USER.getValue()) {
                return -250;
            }
            else if(board[1][1] == PlayerType.COMPUTER_MINIMAX.getValue()) {
                return 250;
            }
        }
        else if(board[1][1] == board[2][0] && board[1][1] == board[2][2]) {
            if(board[1][1] == PlayerType.USER.getValue()) {
                return -250;
            }
            else if(board[1][1] == PlayerType.COMPUTER_MINIMAX.getValue()) {
                return 250;
            }
        }

        return 0;
    }
}
