package com.developerpaul123.tictactoe;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.developerpaul123.tictactoe.gameobjects.Board;
import com.developerpaul123.tictactoe.gameobjects.ComputerMove;
import com.developerpaul123.tictactoe.gameobjects.MinimaxAI;
import com.developerpaul123.tictactoe.gameobjects.PlayerType;
import com.developerpaul123.tictactoe.gameobjects.Point;
import com.developerpaul123.tictactoe.views.TicTacToeView;
import com.devpaul.materiallibrary.views.MaterialFloatingActionButton;

public class MainActivity extends AppCompatActivity implements TicTacToeView.TicTacToeListener{

    Board board;
    TicTacToeView ticTacToeView;
    MaterialFloatingActionButton fab;

    MinimaxAI computerPlayer;
    boolean gameOver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ticTacToeView = (TicTacToeView) findViewById(R.id.tic_tac_toe_view);
        fab = (MaterialFloatingActionButton) findViewById(R.id.material_fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                board.clearBoard();
                ticTacToeView.setBoard(board);
                gameOver = false;
            }
        });

        //create a new game board. Just trying 3x3 for now.
        board = new Board(3, 3);
        ticTacToeView.setBoard(board);
        ticTacToeView.setTicTacToeListener(this);
        computerPlayer = new MinimaxAI();
        gameOver = false;
    }

    @Override
    public void onSquareClicked(int row, int col) {
        //For now just play something.
        if(!gameOver) {
            board.addAMove(new Point(row, col), PlayerType.USER);
        }
        checkForGameOver();
        if(!gameOver) {
            ComputerMove move = computerPlayer.getBestMove(board,
                    computerPlayer.getPlayerType());
            board.addAMove(move.point(), PlayerType.COMPUTER_MINIMAX);
            checkForGameOver();
        }
        ticTacToeView.setBoard(board);

//        ticTacToeView.addAMove(new Point(row, col), PlayerType.USER);
//        new GetComputerMoveTask().execute(board);
    }

    /**
     * Check to see if the game is over.
     */
    public void checkForGameOver() {

        if(board.hasXWon()) {
            gameOver = true;
            showDialog("You won!");
        }
        else if(board.hasOWon()) {
            gameOver = true;
            showDialog("You lost!");
        }
        if(board.isATie()) {
            gameOver = true;
            showDialog("Cat's game!");
        }
    }

    public void showDialog(String message) {
       new MaterialDialog.Builder(this)
               .title("Game Over")
               .content(message)
               .positiveText("Restart")
               .show();
    }
    @Override
    public void onMoveAdded(Board b) {
        new GetComputerMoveTask().execute(b);
    }

    private class GetComputerMoveTask extends AsyncTask<Board, Void, ComputerMove> {

        @Override
        protected ComputerMove doInBackground(Board... boards) {
            final Board b = boards[0];
            return computerPlayer.getBestMove(b, computerPlayer.getPlayerType());
        }

        @Override
        protected void onPostExecute(ComputerMove computerMove) {
            board.addAMove(computerMove.point(), PlayerType.getType(computerPlayer.getPlayerType()));
//            ticTacToeView.addAMove(computerMove.point(), PlayerType.getType(computerPlayer.getPlayerType()));
            ticTacToeView.setBoard(board);
        }
    }
}
