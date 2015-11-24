package com.developerpaul123.tictactoe;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

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
            }
        });

        //create a new game board. Just trying 3x3 for now.
        board = new Board(3, 3);
        ticTacToeView.setBoard(board);
        ticTacToeView.setTicTacToeListener(this);
        computerPlayer = new MinimaxAI();
    }

    @Override
    public void onSquareClicked(int row, int col) {
        //For now just play something.
        board.addAMove(new Point(row, col), PlayerType.USER);
        if(board.isATie()) {

        }
        else if(board.hasXWon()) {


        }
        else if(board.hasOWon()) {

        }
        else {
            board.addAMove(computerPlayer.getBestMove(board, computerPlayer.getPlayerType()).point(), PlayerType.COMPUTER_MINIMAX);
            ticTacToeView.setBoard(board);
        }
//        ticTacToeView.addAMove(new Point(row, col), PlayerType.USER);

//        new GetComputerMoveTask().execute(board);
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
