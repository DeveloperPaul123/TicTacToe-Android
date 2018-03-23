package com.developerpaul123.tictactoe;

import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.developerpaul123.tictactoe.abstracts.AbstractGameActivity;
import com.developerpaul123.tictactoe.abstracts.Board;
import com.developerpaul123.tictactoe.gameobjects.ClassicBoard;
import com.developerpaul123.tictactoe.gameobjects.MinimaxAi;
import com.developerpaul123.tictactoe.gameobjects.PlayerType;
import com.developerpaul123.tictactoe.gameobjects.Point;
import com.developerpaul123.tictactoe.views.TicTacToeView;

/**
 * Class tic tac toe activity. 
 */
public class ClassicGameActivity extends AbstractGameActivity implements TicTacToeView.TicTacToeListener{

    ClassicBoard classicBoard;
    MinimaxAi computerPlayer;
    boolean gameOver;

    @Override
    public void init() {

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                classicBoard.clearBoard();
                ticTacToeView.setBoard(classicBoard);
                gameOver = false;
            }
        });

        //create a new game classicBoard. Just trying 3x3 for now.
        classicBoard = new ClassicBoard();
        ticTacToeView.setBoard(classicBoard);
        ticTacToeView.setTicTacToeListener(this);
        computerPlayer = new MinimaxAi();
        gameOver = false;
    }

    @Override
    public void onSquareClicked(int row, int col) {
        //For now just play something.
        if(!gameOver) {
            classicBoard.addAMove(new Point(row, col), PlayerType.USER.getValue());
        }
        checkForGameOver();
        if(!gameOver) {
            Point play = computerPlayer.performMove(classicBoard);
            classicBoard.addAMove(play, PlayerType.COMPUTER_MINIMAX.getValue());
            checkForGameOver();
        }
        ticTacToeView.setBoard(classicBoard);
    }

    @Override
    public void onMoveAdded(Board b) {

    }

    /**
     * Check to see if the game is over.
     */
    public void checkForGameOver() {

        if(classicBoard.hasXWon()) {
            gameOver = true;
            showDialog("You won!");
        }
        else if(classicBoard.hasOWon()) {
            gameOver = true;
            showDialog("You lost!");
        }
        if(classicBoard.isATie()) {
            gameOver = true;
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
