package com.developerpaul123.tictactoe.gameobjects;

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

    @Override
    public ComputerMove getBestMove(Board input, Integer type) {

        List<Point> availablePoints = getMoves(input);

        int bestScore = type == PlayerType.COMPUTER_MINIMAX.getValue() ? Integer.MIN_VALUE: Integer.MAX_VALUE;
        int currentScore;
        Point bestMove = new Point(-1, -1);

        if(availablePoints.size() == 0) {
            bestScore = evaluate(input);
        } else {
            for(int i = 0; i < availablePoints.size(); i++) {
                //try the move with the current player.
                Point move = availablePoints.get(i);
                input.addAMove(move, PlayerType.getType(type));

                if(type == PlayerType.COMPUTER_MINIMAX.getValue()) {
                    currentScore = getBestMove(input, PlayerType.USER.getValue()).score();
                    if(currentScore > bestScore) {
                        bestScore = currentScore;
                        bestMove = move;
                    }
                }
                else {
                    currentScore = getBestMove(input, PlayerType.COMPUTER_MINIMAX.getValue()).score();
                    if(currentScore < bestScore) {
                        bestScore = currentScore;
                        bestMove = move;
                    }
                }

                input.removeAMove(move);
            }

        }

        return new ComputerMove(bestMove, bestScore);
    }

    public List<Point> getMoves(Board b) {
        if(b.hasOWon() || b.hasXWon()) {
            return new ArrayList<Point>();
        }
        else {
            return b.getAvailablePoints();
        }
    }

    /** The heuristic evaluation function for the current board
     @Return +100, +10, +1 for EACH 3-, 2-, 1-in-a-line for computer.
     -100, -10, -1 for EACH 3-, 2-, 1-in-a-line for opponent.
     0 otherwise   */
    private int evaluate(Board b) {
        int score = 0;
        // Evaluate score for each of the 8 lines (3 rows, 3 columns, 2 diagonals)
        score += evaluateLine(b, 0, 0, 0, 1, 0, 2);  // row 0
        score += evaluateLine(b, 1, 0, 1, 1, 1, 2);  // row 1
        score += evaluateLine(b, 2, 0, 2, 1, 2, 2);  // row 2
        score += evaluateLine(b, 0, 0, 1, 0, 2, 0);  // col 0
        score += evaluateLine(b, 0, 1, 1, 1, 2, 1);  // col 1
        score += evaluateLine(b, 0, 2, 1, 2, 2, 2);  // col 2
        score += evaluateLine(b, 0, 0, 1, 1, 2, 2);  // diagonal
        score += evaluateLine(b, 0, 2, 1, 1, 2, 0);  // alternate diagonal
        return score;
    }

    /** The heuristic evaluation function for the given line of 3 cells
     @Return +100, +10, +1 for 3-, 2-, 1-in-a-line for computer.
     -100, -10, -1 for 3-, 2-, 1-in-a-line for opponent.
     0 otherwise */
    private int evaluateLine(Board board, int row1, int col1, int row2, int col2, int row3, int col3) {
        int score = 0;

        // First cell
        int[][] b = board.getBoard();
        if (b[row1][col1] == PlayerType.COMPUTER_MINIMAX.getValue()) {
            score = 1;
        } else if (b[row1][col1] == PlayerType.USER.getValue()) {
            score = -1;
        }

        // Second cell
        if (b[row2][col2] == PlayerType.COMPUTER_MINIMAX.getValue()) {
            if (score == 1) {   // cell1 is mySeed
                score = 10;
            } else if (score == -1) {  // cell1 is oppSeed
                return 0;
            } else {  // cell1 is empty
                score = 1;
            }
        } else if (b[row2][col2] == PlayerType.USER.getValue()) {
            if (score == -1) { // cell1 is oppSeed
                score = -10;
            } else if (score == 1) { // cell1 is mySeed
                return 0;
            } else {  // cell1 is empty
                score = -1;
            }
        }

        // Third cell
        if (b[row3][col3] == PlayerType.COMPUTER_MINIMAX.getValue()) {
            if (score > 0) {  // cell1 and/or cell2 is mySeed
                score *= 10;
            } else if (score < 0) {  // cell1 and/or cell2 is oppSeed
                return 0;
            } else {  // cell1 and cell2 are empty
                score = 1;
            }
        } else if (b[row3][col3] == PlayerType.USER.getValue()) {
            if (score < 0) {  // cell1 and/or cell2 is oppSeed
                score *= 10;
            } else if (score > 1) {  // cell1 and/or cell2 is mySeed
                return 0;
            } else {  // cell1 and cell2 are empty
                score = -1;
            }
        }
        return score;
    }
}
