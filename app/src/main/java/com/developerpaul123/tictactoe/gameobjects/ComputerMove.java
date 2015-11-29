package com.developerpaul123.tictactoe.gameobjects;

/**
 * Created by Paul on 11/24/2015.
 * Holds a computer move with a given point and a score for the point.
 * Used in the minimax algorithm.
 */
public class ComputerMove {

    /**
     * The point for this computer move.
     */
    private Point p;

    /**
     * The score for this computer move.
     */
    private int score;

    /**
     * Default constructor. Create a computer move with a point and a score.
     * @param play the play point.
     * @param score the score to set this move at.
     */
    public ComputerMove(Point play, int score) {
        this.p = play;
        this.score =score;
    }

    /**
     * Secondary constructor with just a score.
     * @param score the score to set this move with.
     */
    public ComputerMove(int score) {
        this.score = score;
    }

    /**
     * Empty constructor.
     */
    public ComputerMove() {

    }

    /**
     * Set the score of this computer move.
     * @param score the score to set for this move.
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Set the point of this move.
     * @param play the play of this move.
     */
    public void setPoint(Point play) {
        this.p = play;
    }

    /**
     * The current score of this move.
     * @return int the current score.
     */
    public int score() {
        return this.score;
    }

    /**
     * The current point of this move.
     * @return Point the current point.
     */
    public Point point() {
        return this.p;
    }
}
