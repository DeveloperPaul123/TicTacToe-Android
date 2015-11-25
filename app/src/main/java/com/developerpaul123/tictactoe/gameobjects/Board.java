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
    private List<Row> boardList;

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
        boardList = new ArrayList<>();
        for(int i = 0; i < rows; i++) {
            //add new rows.
            Row r = new Row(cols);
            boardList.add(r);
            //add available points.
            for(int j = 0; j < columns; j++) {
                Point p = new Point(i, j);
                availablePoints.add(p);
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
        Row row1 = boardList.get(0);
        Row row2 = boardList.get(1);
        Row row3 = boardList.get(2);

        //check rows and columns.
        for(int i = 0; i < columns-2; i++) {
            if(row1.getValue(i) == row1.getValue(i+1) && row1.getValue(i+1) == row1.getValue(i+2)
                    && row1.getValue(0) == PlayerType.USER.getValue()) {
                return true;
            }
            else if(row2.getValue(i) == row2.getValue(i+1) && row2.getValue(i+1) == row2.getValue(i+2)
                    && row2.getValue(0) == PlayerType.USER.getValue()) {
                return true;
            }
            else if(row3.getValue(i) == row3.getValue(i+1) && row3.getValue(i+1) == row3.getValue(i+2)
                    && row3.getValue(0) == PlayerType.USER.getValue()) {
                return true;
            }

            if(row1.getValue(i) == row2.getValue(i) && row2.getValue(i) == row3.getValue(i)
                    && row1.getValue(i) == PlayerType.USER.getValue()) {
                return true;
            }
            else if(row1.getValue(i+1) == row2.getValue(i+1) && row2.getValue(i+1) == row3.getValue(i+1)
                    && row1.getValue(i+1) == PlayerType.USER.getValue()) {
                return true;
            }
            else if(row1.getValue(i+2) == row2.getValue(i+2) && row2.getValue(i+2) == row3.getValue(i+2)
                    && row1.getValue(i+2) == PlayerType.USER.getValue()) {
                return true;
            }
        }

        //check diagonals.
        if(row1.getValue(0) == row2.getValue(1) && row2.getValue(1) == row3.getValue(2)
                    && row1.getValue(0) == PlayerType.USER.getValue()) {
                return true;
        }
        else if (row1.getValue(2) == row2.getValue(1) && row2.getValue(1) == row3.getValue(0)
                && row1.getValue(2) == PlayerType.USER.getValue()) {
            return true;
        }
        return false;
    }

    /**
     * Checks if the o player won.
     * @return true if the O player won, false otherwise.
     */
    public boolean hasOWon() {
        Row row1 = boardList.get(0);
        Row row2 = boardList.get(1);
        Row row3 = boardList.get(2);

        //check rows and columns.
        for(int i = 0; i < columns-2; i++) {
            if(row1.getValue(i) == row1.getValue(i+1) && row1.getValue(i+1) == row1.getValue(i+2)
                    && row1.getValue(0) == PlayerType.COMPUTER_MINIMAX.getValue()) {
                return true;
            }
            if(row2.getValue(i) == row2.getValue(i+1) && row2.getValue(i+1) == row2.getValue(i+2)
                    && row2.getValue(0) == PlayerType.COMPUTER_MINIMAX.getValue()) {
                return true;
            }
            if(row3.getValue(i) == row3.getValue(i+1) && row3.getValue(i+1) == row3.getValue(i+2)
                    && row3.getValue(0) == PlayerType.COMPUTER_MINIMAX.getValue()) {
                return true;
            }

            if(row1.getValue(i) == row2.getValue(i) && row2.getValue(i) == row3.getValue(i)
                    && row1.getValue(i) == PlayerType.COMPUTER_MINIMAX.getValue()) {
                return true;
            }
            if(row1.getValue(i+1) == row2.getValue(i+1) && row2.getValue(i+1) == row3.getValue(i+1)
                    && row1.getValue(i+1) == PlayerType.COMPUTER_MINIMAX.getValue()) {
                return true;
            }
            if(row1.getValue(i+2) == row2.getValue(i+2) && row2.getValue(i+2) == row3.getValue(i+2)
                    && row1.getValue(i+2) == PlayerType.COMPUTER_MINIMAX.getValue()) {
                return true;
            }
        }

        //check diagonals.
        if(row1.getValue(0) == row2.getValue(1) && row2.getValue(1) == row3.getValue(2)
                && row1.getValue(0) == PlayerType.COMPUTER_MINIMAX.getValue()) {
            return true;
        }
        else if (row1.getValue(2) == row2.getValue(1) && row2.getValue(1) == row3.getValue(0)
                && row1.getValue(2) == PlayerType.COMPUTER_MINIMAX.getValue()) {
            return true;
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
    public void addAMove(Point play, PlayerType playerType) {
        for(int i = 0; i < availablePoints.size(); i++) {
            if(availablePoints.get(i).getRow() == play.getRow() &&
                    availablePoints.get(i).getColumn() == play.getColumn()) {
                availablePoints.remove(i);
                playedPoints.add(play);
                Row r = boardList.get(play.getRow());
                r.setValue(play.getColumn(), playerType.getValue());
                boardList.set(play.getRow(), r);
            }
        }

    }

    /**
     * Removes a play from the current board.
     * @param removePlay remove the play.
     */
    public void removeAMove(Point removePlay) {
        for(int i = 0; i < playedPoints.size(); i++) {
            if(playedPoints.get(i).getColumn() == removePlay.getColumn() &&
            playedPoints.get(i).getRow() == removePlay.getRow()) {
                playedPoints.remove(i);
                availablePoints.add(removePlay);
                Row r = boardList.get(removePlay.getRow());
                r.setValue(removePlay.getColumn(), PlayerType.NO_ONE.getValue());
                boardList.set(removePlay.getRow(), r);
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
            Row r = boardList.get(i);
            for (int j = 0; j < columns; j++) {
                r.setValue(j, PlayerType.NO_ONE.getValue());
                availablePoints.add(new Point(i, j));
            }
            boardList.set(i, r);
        }

    }

    public List<Row> getBoardList() {
        return this.boardList;
    }

}
