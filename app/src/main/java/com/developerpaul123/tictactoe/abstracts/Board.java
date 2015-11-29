package com.developerpaul123.tictactoe.abstracts;

import com.developerpaul123.tictactoe.gameobjects.PlayerType;
import com.developerpaul123.tictactoe.gameobjects.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paul on 11/28/2015.
 *
 * Abstract board class for all different game boards.
 * Classic, 4x4, 5x5, 7x7, 9x9.
 * Holds the board itself, available points and the size of the board.
 */
public abstract class Board {

    public  int rows, columns;
    public int[][] board;
    public List<Point> availablePoints;
    public List<Point> playedPoints;

    public abstract boolean isGameOver();
    public abstract boolean isATie();
    public abstract boolean hasPlayerWon(int playerType);

    /**
     * Constructor with the number of rows and columns.
     * @param rows
     * @param columns
     */
    public Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        availablePoints = new ArrayList<>();
        playedPoints = new ArrayList<>();
        board = new int[rows][columns];
        for(int i = 0; i < this.rows; i++) {
            for(int j = 0; j < this.columns; j++) {
                board[i][j] = PlayerType.NO_ONE.getValue();
                Point point = new Point(i, j);
                availablePoints.add(point);
            }
        }
    }

    /**
     * Returns a list of all the unplayed points on the board.
     * @return List of Point objects that are empty.
     */
    public List<Point> getAvailablePoints() {
        List<Point> points = new ArrayList<>();
        for(int i = 0; i < rows; i++) {
            for(int j =0; j < columns; j++) {
                if(board[i][j] == PlayerType.NO_ONE.getValue()) {
                    Point p = new Point(i, j);
                    points.add(p);
                }
            }
        }
        return points;
    }

    /**
     * Get the current number of rows.
     * @return the number of rows.
     */
    public int getRows() {
        return rows;
    }

    /**
     * Get the curent number of columns.
     * @return the number of columns.
     */
    public int getColumns() {
        return columns;
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

    /**
     * Get the current board 2D array representation.
     * @return 2D array of current board plays.
     */
    public int[][] getBoard() {
        return this.board;
    }

}
