package com.developerpaul123.tictactoe.gameobjects;

import java.util.ArrayList;
import java.util.List;

/**
 * The TicTacToe game board. Holds the current moves and open areas on the board.
 * Created by Paul on 11/23/2015.
 */
public class Board {

    private int rows;
    private int columns;
    private List<Point> availablePoints;
    private List<Point> playedPoints;
    int board[][];

    /**
     * Constructor for the game board. Can have variable rows and columns.
     * @param rows the number of rows.
     * @param cols the number of columns.
     */
    public Board(int rows, int cols) {
        this.rows = rows;
        this.columns = cols;
        availablePoints = new ArrayList<>();
        playedPoints = new ArrayList<>();
        board = new int[rows][cols];
        for(int i = 0; i < rows; i++) {
            //add available points.
            for(int j = 0; j < columns; j++) {
                Point p = new Point(i, j);
                availablePoints.add(p);
                board[i][j] = PlayerType.NO_ONE.getValue();
            }
        }
    }

    /**
     * Get the current number of rows.
     * @return number of rows.
     */
    public int getRows() {
        return rows;
    }

    /**
     * Get the current number of columns.
     * @return the number of columns.
     */
    public int getColumns() {
        return columns;
    }

    public boolean isGameOver() {
        return hasOWon() || hasXWon() || isATie();
    }
    /**
     * Check if there was a tie.
     * @return true if there was a tie, false otherwise.
     */
    public boolean isATie() {
        return !hasOWon() && !hasXWon() && availablePoints.size() == 0;
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

    /**
     * Gets a list of all the available {@link Point} objects.
     * @return {@code List} of available, playable points.
     */
    public List<Point> getAvailablePoints() {
        return availablePoints;
    }

    /**
     * Gets a list of all the already played points.
     * @return
     */
    public List<Point> getPlayedPoints() {
        return playedPoints;
    }

    /**
     * Adds a move to the current board.
     * @param play the play to add.
     * @param playerType who made the play.
     */
    public void addAMove(Point play, int playerType) {
        for(int i = 0; i < availablePoints.size(); i++) {
            if(availablePoints.get(i).getRow() == play.getRow() &&
                    availablePoints.get(i).getColumn() == play.getColumn()) {
                availablePoints.remove(i);
                playedPoints.add(play);
                board[play.getRow()][play.getColumn()] = playerType;
                break;
            }
        }

    }

    /**
     * Removes a play from the current board.
     * @param removePlay remove the play.
     */
    public void removeAMove(Point removePlay) {
        availablePoints.add(removePlay);
        board[removePlay.getRow()][removePlay.getColumn()] = PlayerType.NO_ONE.getValue();
        for(int i = 0; i < playedPoints.size(); i++) {
            if(playedPoints.get(i).getColumn() == removePlay.getColumn() &&
            playedPoints.get(i).getRow() == removePlay.getRow()) {
                playedPoints.remove(i);
                break;
            }
        }
    }

    /**
     * Clears the board of all plays and resets it for a new game.
     */
    public void clearBoard() {
        availablePoints.clear();
        playedPoints.clear();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                board[i][j] = PlayerType.NO_ONE.getValue();
                availablePoints.add(new Point(i, j));
            }
        }

    }

    public int[][] getBoard() {
        return this.board;
    }

}
