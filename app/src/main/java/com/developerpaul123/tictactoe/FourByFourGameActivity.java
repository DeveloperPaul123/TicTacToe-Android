package com.developerpaul123.tictactoe;

import android.view.View;

import com.developerpaul123.tictactoe.abstracts.AbstractGameActivity;
import com.developerpaul123.tictactoe.abstracts.Board;
import com.developerpaul123.tictactoe.gameobjects.FourByFourBoard;
import com.developerpaul123.tictactoe.gameobjects.MinimaxAI;
import com.developerpaul123.tictactoe.views.TicTacToeView;

/**
 * Created by Paul on 11/29/2015.
 */
public class FourByFourGameActivity extends AbstractGameActivity implements TicTacToeView.TicTacToeListener{

    FourByFourBoard board;
    MinimaxAI computer;
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
        board = new FourByFourBoard();
        ticTacToeView.setBoard(board);
        ticTacToeView.setTicTacToeListener(this);

    }

    @Override
    public void onSquareClicked(int row, int col) {

    }

    @Override
    public void onMoveAdded(Board board) {

    }
}
