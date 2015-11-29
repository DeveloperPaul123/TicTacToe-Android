package com.developerpaul123.tictactoe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.developerpaul123.tictactoe.abstracts.Board;
import com.developerpaul123.tictactoe.gameobjects.ClassicBoard;
import com.developerpaul123.tictactoe.gameobjects.MinimaxAI;
import com.developerpaul123.tictactoe.gameobjects.PlayerType;
import com.developerpaul123.tictactoe.gameobjects.Point;
import com.developerpaul123.tictactoe.views.TicTacToeView;
import com.devpaul.materiallibrary.views.MaterialFloatingActionButton;

/**
 * Class tic tac toe activity. 
 */
public class ClassicGameActivity extends AppCompatActivity implements TicTacToeView.TicTacToeListener{

    ClassicBoard classicBoard;
    TicTacToeView ticTacToeView;
    MaterialFloatingActionButton fab;
    MinimaxAI computerPlayer;
    boolean gameOver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.classic_game_activity);
        ticTacToeView = (TicTacToeView) findViewById(R.id.tic_tac_toe_view);
        fab = (MaterialFloatingActionButton) findViewById(R.id.material_fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                classicBoard.clearBoard();
                ticTacToeView.setBoard(classicBoard);
                gameOver = false;
            }
        });

        //create a new game classicBoard. Just trying 3x3 for now.
        classicBoard = new ClassicBoard(3, 3);
        ticTacToeView.setBoard(classicBoard);
        ticTacToeView.setTicTacToeListener(this);
        computerPlayer = new MinimaxAI();
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
