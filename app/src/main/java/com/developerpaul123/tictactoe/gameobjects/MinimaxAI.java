package com.developerpaul123.tictactoe.gameobjects;

import com.developerpaul123.tictactoe.abstracts.MinimaxTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paul on 11/24/2015.
 */
public class MinimaxAI extends MinimaxTemplate<ComputerMove, Integer, Board > {

    public MinimaxAI() {
        super(PlayerType.COMPUTER_MINIMAX.getValue());
    }

    @Override
    public ComputerMove getBestMove(Board input, Integer type) {
       Board b = input;
        //first check for base cases. We have three:
        // 1: there was a tie.
        // 2: x won
        // 3: o won
        // we may have more later on (may have 3 players total).
        if(b.isATie()) {
            return new ComputerMove(0);
        }
        else if(b.hasOWon()) {
            //we won!
            return new ComputerMove(100);
        }
        else if(b.hasXWon()) {
            return new ComputerMove(-100);
        }

        //now we move on with the algorithm. Create a holder for all the moves we're going to make.
        List<ComputerMove> myMoves = new ArrayList<>();
        //get the list of available places to play.
        List<Point> availables = b.getAvailablePoints();

        //now we're going to go through all available moves.
        for(int i = 0; i < availables.size(); i++) {
            //create a new move.
            ComputerMove move = new ComputerMove();
            //set the point of the current move as the next available one.
            move.setPoint(availables.get(i));

            //add the move to the board.
            b.addAMove(move.point(), PlayerType.getType(type));
            //check for who is playing.
            if(type == PlayerType.USER.getValue()) {
                //the user played so we run this recursively but now the next move is us (i.e. the
                //computer player)
                move.setScore(getBestMove(b, getPlayerType()).score());
            }
            else if(type == PlayerType.COMPUTER_MINIMAX.getValue()) {
                //we just played, so now it's the users turn. Call the recursion with the users
                //play.
                move.setScore(getBestMove(b, PlayerType.USER.getValue()).score());
            }

            //remember the move we made and add it to our list of moves.
            myMoves.add(move);

            //remember to remove the play from the board.
            b.removeAMove(move.point());
        }

        //now look for the best move.
        int bestMove = 0;
        //check for the player type first.
        if(type == PlayerType.COMPUTER_MINIMAX.getValue()) {
            int bestScore = -10000; //make sure this gets changed so make it really negative.
            for(int j = 0; j < myMoves.size(); j++) {
                //check for best score.
                if(myMoves.get(j).score() > bestScore) {
                    bestScore = myMoves.get(j).score();
                    bestMove = j;
                }
            }
        }
        //the user played.
        else if(type == PlayerType.USER.getValue()) {
            //now we flip it. We're looking for the most negative score.
            int bestScore = 10000;
            for(int x = 0; x < myMoves.size(); x++) {
                if(myMoves.get(x).score() < bestScore) {
                    bestScore = myMoves.get(x).score();
                    bestMove = x;
                }
            }
        }

        //return the "best" move.
        return myMoves.get(bestMove);

    }
}
