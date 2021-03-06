package com.developerpaul123.tictactoe.abstracts;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;

import com.developerpaul123.tictactoe.R;
import com.developerpaul123.tictactoe.views.TicTacToeView;

/**
 * Created by Paul on 11/29/2015.
 */
public abstract class AbstractGameActivity extends AppCompatActivity {

    public TicTacToeView ticTacToeView;
    public FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.abstract_game_activity);

        ticTacToeView = findViewById(R.id.tic_tac_toe_view);
        fab = findViewById(R.id.material_fab);

        init();
    }

    /**
     * Called in onCreate. Instantiate new stuff you need here.
     */
    public abstract void init();
}
