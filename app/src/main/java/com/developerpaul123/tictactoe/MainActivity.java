package com.developerpaul123.tictactoe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.developerpaul123.tictactoe.gameobjects.Board;
import com.developerpaul123.tictactoe.views.TicTacToeView;

public class MainActivity extends AppCompatActivity {

    Board board;
    TicTacToeView ticTacToeView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ticTacToeView = (TicTacToeView) findViewById(R.id.tic_tac_toe_view);

        //create a new game board. Just trying 3x3 for now.
        board = new Board(3, 3);
        ticTacToeView.setBoard(board);
    }
}
