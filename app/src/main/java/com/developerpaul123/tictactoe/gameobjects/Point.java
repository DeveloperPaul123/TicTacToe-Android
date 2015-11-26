package com.developerpaul123.tictactoe.gameobjects;

/**
 * Created by Paul on 11/23/2015.
 */
public class Point {

    private int row;
    private int column;

    /**
     * Simple class that holds a coordinate for the board.
     * @param row the "x" coordinate.
     * @param column the "y" coordinate.
     */
    public Point(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Get the row for this point.
     * @return the row.
     */
    public int getRow() {
        return this.row;
    }

    /**
     * Get the column for this point.+
     * @return the column.
     */
    public int getColumn() {
        return this.column;
    }

    @Override
    public String toString() {
        return "[" + getRow() + ", " + getColumn() + "]";
    }
}
