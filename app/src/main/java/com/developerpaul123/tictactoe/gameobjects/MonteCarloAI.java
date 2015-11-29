package com.developerpaul123.tictactoe.gameobjects;

import com.developerpaul123.tictactoe.abstracts.MCTSTemplate;

import java.util.List;

/**
 * Created by Paul on 11/28/2015.
 */
public class MonteCarloAI extends MCTSTemplate<Point, ClassicBoard, Integer> {

    public MonteCarloAI() {
        super(PlayerType.COMPUTER_MCTS.getValue());
    }

    @Override
    public Point getBestMove(ClassicBoard classicBoard, Integer playerType) {

        Node node = new Node(null, playerType);

        for(int i = 0; i < 1000; i++) {
            Node current = selection(node, classicBoard);
            int value = rollout(current, classicBoard, getPlayerType());
            current.update(value, 1);
        }
        return null;
    }

    @Override
    public Node selection(Node current, ClassicBoard classicBoard) {
        while(!classicBoard.isGameOver()) {
            List<Point> availableMoves = classicBoard.getAvailablePoints();
            if(availableMoves.size() > current.getChildren().size()) {
                return expansion(current, classicBoard);
            }
            else {
                current = getBestUCB(current, 1.44);
            }
        }
        return current;
    }

    /**
     * Return the best child node using upper cofidence bound method/formula.
     * @param current the current node.
     * @param c constant in the UCB formula.
     * @return the best node.
     */
    public Node getBestUCB(Node current, double c) {
        Node bestChild = null;
        double best = Double.MIN_VALUE;
        List<Node> children = current.getChildren();
        for(int i = 0; i < children.size(); i++) {
            Node child = children.get(i);
            double ucb1 = (((double) child.getWins()) / (double) child.getVisits()) + c
                    * Math.sqrt((2.0 * Math.log((double) child.getWins()/ (double) child.getVisits())));
            if(ucb1 > best) {
                best = ucb1;
                bestChild = child;
            }
        }

        return bestChild;
    }

    @Override
    public Node expansion(Node current, ClassicBoard classicBoard) {
        List<Point> availablePoints = classicBoard.getAvailablePoints();
        for(int i = 0; i < availablePoints.size(); i++) {
            Point play = availablePoints.get(i);
            //TODO
        }
        return null;
    }

    @Override
    public int rollout(Node current, ClassicBoard classicBoard, Integer playerType) {
        return 0;
    }
}
