package com.developerpaul123.tictactoe.gameobjects;

/**
 * Created by Paul on 11/23/2015.
 */
public enum PlayerType {
    NO_ONE(0),
    USER(1),
    COMPUTER_MINIMAX(2),
    COMPUTER_MCTS(2);

    private final int value;
    PlayerType(int value) {
        this.value = value;
    }
    public int getValue() {
        return this.value;
    }
}
