package com.developerpaul123.tictactoe.abstracts;

/**
 * Created by Paul on 11/23/2015.
 */
public abstract class MinimaxTemplate<T1, T2, T3> {

    private final T2 playerType;

    public MinimaxTemplate(T2 param) {
        playerType = param;
    }

    public abstract T1 getBestMove(T3 input, T2 type, int depth);

    public T2 getPlayerType() {
        return playerType;
    }
}
