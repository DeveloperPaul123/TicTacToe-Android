package com.developerpaul123.tictactoe.gameobjects;

/**
 * Created by Paul on 11/24/2015.
 */
public class ComputerMove {

    private Point p;
    private int score;

    public ComputerMove(Point play, int score) {
        this.p = play;
        this.score =score;
    }

    public ComputerMove(int score) {
        this.score = score;
    }

    public ComputerMove() {

    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setPoint(Point play) {
        this.p = play;
    }

    public int score() {
        return this.score;
    }

    public Point point() {
        return this.p;
    }
}
