package com.developerpaul123.tictactoe.gameobjects;

/**
 * Created by Paul on 11/23/2015.
 */
public class Row {

    int columns;
    int colValues[];

    /**
     * Row class. Holds a given number of columns.
     * @param size the number of columns.
     */
    public Row(int size) {
        columns = size;
        colValues = new int[columns];
    }

    public int getSize() {
        return columns;
    }

    /**
     * Set a value at a specified column.
     * @param column the column.
     * @param value the value to set.
     */
    public void setValue(int column, int value) {
        colValues[column] = value;
    }

    /**
     * Get a value at a specified column.
     * @param column the column to get the value for.
     * @return the value at the given column.
     */
    public int getValue(int column) {
        return colValues[column];
    }

    /**
     * Returns all column values for this row.
     * @return int array of all column values.
     */
    public int[] getValues() {
        return colValues;
    }
}
