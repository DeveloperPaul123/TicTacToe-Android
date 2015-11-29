package com.developerpaul123.tictactoe.gameobjects;

/**
 * Created by Paul on 11/23/2015.
 */
public abstract class Player {

    PlayerType playerType;

    /**
     * Abstract player class.
     * @param type the player type.
     */
    public Player(PlayerType type) {
        this.playerType = type;
    }

    /**
     * Should be overridden to get the next move.
     * @param curClassicBoard the current board.
     * @return Object can be a Point or generic move.
     */
    public abstract Object getNextMove(ClassicBoard curClassicBoard);

    /**
     * Return this players playertype.
     * @return This players playertype.
     */
    public PlayerType getPlayerType() {
        return playerType;
    }
}
