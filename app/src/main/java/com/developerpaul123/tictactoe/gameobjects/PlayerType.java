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

    public static PlayerType getType(int t) {
        switch (t) {
            case 0:
                return NO_ONE;
            case 1:
                return USER;
            case 2:
                return COMPUTER_MINIMAX;
            case 3:
                return COMPUTER_MCTS;
            default:
                return NO_ONE;
        }
    }
}
