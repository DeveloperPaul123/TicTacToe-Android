package com.developerpaul123.tictactoe.abstracts;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paul on 11/25/2015.
 */
public abstract class MCTSTemplate<T1, T2, T3> {

    public class Node {
        private int visits;
        private int wins;
        private Node parent;
        List<Node> children;

        public Node(Node parent, int playerType) {
            this.children = new ArrayList<>();
        }

        public void update(int updateWins, int updateVisits) {
            this.visits+= updateVisits;
            this.wins+= updateWins;
        }

        public Node getParent() {
            return parent;
        }

        public void addChild(Node child) {
            this.children.add(child);
        }

        public List<Node> getChildren() {
            return children;
        }

        public int getVisits() {
            return visits;
        }

        public int getWins() {
            return wins;
        }
    }

    private T3 playerType;

    public MCTSTemplate(T3 type) {
        this.playerType = type;
    }

    public T3 getPlayerType() {
        return playerType;
    }

    public abstract T1 getBestMove(T2 board, T3 playerType);

    public abstract Node selection(Node current, T2 board);

    public abstract Node expansion(Node current, T2 board);

    public abstract int rollout(Node current, T2 board, T3 playerType);
}
