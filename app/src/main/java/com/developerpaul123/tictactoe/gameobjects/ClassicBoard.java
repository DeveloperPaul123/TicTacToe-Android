package com.developerpaul123.tictactoe.gameobjects;

import com.developerpaul123.tictactoe.abstracts.Board;

/**
 * The TicTacToe game board. Holds the current moves and open areas on the board.
 * Created by Paul on 11/23/2015.
 */
public class ClassicBoard extends Board {

    /**
     * Constructor for the game board. Can have variable rows and columns.
     * @param rows the number of rows.
     * @param cols the number of columns.
     */
    public ClassicBoard(int rows, int cols) {
        super(rows, cols);
    }

    @Override
    public boolean isGameOver() {
        return hasOWon() || hasXWon() || isATie();
    }

    @Override
    public boolean isATie() {
        return !hasOWon() && !hasXWon() && availablePoints.size() == 0;
    }

    @Override
    public boolean hasPlayerWon(int playerType) {
        if(playerType == PlayerType.USER.getValue()) {
            return hasXWon();
        }
        else if(playerType == PlayerType.COMPUTER_MINIMAX.getValue()) {
            return hasOWon();
        }
        return false;
    }

    /**
     * Checks if the x player won.
     * @return true if the x player won, false otherwise.
     */
    public boolean hasXWon() {
        int USER = PlayerType.USER.getValue();
        if ((board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] == USER)
                || (board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] == USER)) {
            return true;
        }
        for (int i = 0; i < 3; ++i) {
            if ((board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] == USER)
                    || (board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] == USER)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the o player won.
     * @return true if the O player won, false otherwise.
     */
    public boolean hasOWon() {
        int COMPUTER = PlayerType.COMPUTER_MINIMAX.getValue();
        if ((board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] == COMPUTER)
                || (board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] == COMPUTER)) {
            return true;
        }
        for (int i = 0; i < 3; ++i) {
            if ((board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] == COMPUTER)
                    || (board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] == COMPUTER)) {
                return true;
            }
        }
        return false;
    }

}
