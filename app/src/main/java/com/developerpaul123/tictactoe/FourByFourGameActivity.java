package com.developerpaul123.tictactoe;

import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.developerpaul123.tictactoe.abstracts.AbstractGameActivity;
import com.developerpaul123.tictactoe.abstracts.Board;
import com.developerpaul123.tictactoe.gameobjects.FourByFourBoard;
import com.developerpaul123.tictactoe.gameobjects.MinimaxAI;
import com.developerpaul123.tictactoe.gameobjects.PlayerType;
import com.developerpaul123.tictactoe.gameobjects.Point;
import com.developerpaul123.tictactoe.views.TicTacToeView;

/**
 * Created by Paul on 11/29/2015.
 */
public class FourByFourGameActivity extends AbstractGameActivity implements TicTacToeView.TicTacToeListener{

    /**
     * Current board.
     */
    FourByFourBoard board;

    /**
     * The computer player for this activity.
     */
    MinimaxAI computer;

    /**
     * Holder for boolean indicating if the game is over.
     */
    boolean isGameOver;

    @Override
    public void init() {

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                board.clearBoard();
                ticTacToeView.setBoard(board);
                isGameOver = false;
            }
        });

        computer = new MinimaxAI();
        computer.setMaxDepth(4);
        board = new FourByFourBoard();
        ticTacToeView.setBoard(board);
        ticTacToeView.setTicTacToeListener(this);

    }

    @Override
    public void onSquareClicked(int row, int col) {
        //For now just play something.
        if(!isGameOver) {
            board.addAMove(new Point(row, col), PlayerType.USER.getValue());
        }
        checkForGameOver();
        if(!isGameOver) {
            Point play = computer.performMove(board);
            board.addAMove(play, PlayerType.COMPUTER_MINIMAX.getValue());
            checkForGameOver();
        }
        ticTacToeView.setBoard(board);
    }

    @Override
    public void onMoveAdded(Board board) {

    }

    /**
     * Check to see if the game is over.
     */
    public void checkForGameOver() {

        if(board.hasXWon()) {
            isGameOver = true;
            showDialog("You won!");
        }
        else if(board.hasOWon()) {
            isGameOver = true;
            showDialog("You lost!");
        }
        if(board.isATie()) {
            isGameOver = true;
            showDialog("Cat's game!");
        }
    }

    /**
     * Shows a dialog with a given message.
     * @param message the message to show.
     */
    private void showDialog(String message) {
        new MaterialDialog.Builder(this)
                .title("Game Over")
                .content(message)
                .positiveText("Restart")
                .show();
    }
}
