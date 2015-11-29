package com.developerpaul123.tictactoe.gameobjects;

import com.developerpaul123.tictactoe.abstracts.Board;

/**
 * Created by Paul on 11/29/2015.
 */
public class FourByFourBoard extends Board {

    public FourByFourBoard() {
        super(4, 4);
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
     * Check if the x player has won (the human player).
     * @return true if they won. False otherwise.
     */
    public boolean hasXWon() {
        int USER = PlayerType.USER.getValue();
        if(board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] == board[3][3] && board[0][0] == USER
            || board[0][3] == board[1][2] && board[0][3] == board[2][1] && board[0][3] == board[3][0] && board[0][3] == USER){
            return true;
        }
        for(int i = 0; i < rows; i++) {
            if ((board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] == board[i][3] && board[i][0] == USER)
                    || (board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] == board[3][i] && board[0][i] == USER)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check to see if the o player has won (i.e. the computer player).
     * @return true if they have won, false otherwise.
     */
    public boolean hasOWon() {

        int COMPUTER = PlayerType.COMPUTER_MINIMAX.getValue();
        if(board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] == board[3][3] && board[0][0] == COMPUTER
                || board[0][3] == board[1][2] && board[0][3] == board[2][1] && board[0][3] == board[3][0] && board[0][3] == COMPUTER){
            return true;
        }
        for(int i = 0; i < rows; i++) {
            if ((board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] == board[i][3] && board[i][0] == COMPUTER)
                    || (board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] == board[3][i] && board[0][i] == COMPUTER)) {
                return true;
            }
        }
        return false;
    }
}
