package com.developerpaul123.tictactoe.abstracts;

/**
 * Created by Paul on 11/23/2015.
 * Template class for the minimax algorithm.
 */
public abstract class MinimaxTemplate<T1, T2, T3> {

    /**
     * The player type of this AI player.
     */
    private final T2 playerType;

    /**
     * Constructor for Minimax. Pass in the playertype.
     * @param param a parameter that describes the player type.
     */
    public MinimaxTemplate(T2 param) {
        playerType = param;
    }

    /**
     * Returns the best move for this player.
     * @param input the board/game input.
     * @param type the player type.
     * @param depth how deep to look.
     * @return the "best move" of type T1.
     */
    public abstract T1 getBestMove(T3 input, T2 type, int depth);

    /**
     * Returns the player type.
     * @return the player type of type T2.
     */
    public T2 getPlayerType() {
        return playerType;
    }
}
