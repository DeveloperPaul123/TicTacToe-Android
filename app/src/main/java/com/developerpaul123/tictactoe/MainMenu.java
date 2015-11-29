package com.developerpaul123.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.developerpaul123.tictactoe.gameobjects.MinimaxAI;
import com.developerpaul123.tictactoe.views.TicTacToeView;
import com.devpaul.materiallibrary.views.MaterialFlatButton;

/**
 * Created by Paul on 11/28/2015.
 */
public class MainMenu extends AppCompatActivity {

    MaterialFlatButton playButton;
    MaterialFlatButton settingsButton;
    LinearLayout mainLayout;
    TicTacToeView ticTacToeView;

    MinimaxAI playerOne;
    MinimaxAI playerTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu_activity);

        mainLayout = (LinearLayout) findViewById(R.id.main_menu_linear_layout);
        playButton = (MaterialFlatButton) findViewById(R.id.main_menu_play_button);
        settingsButton = (MaterialFlatButton) findViewById(R.id.main_menu_settings_button);
        ticTacToeView = (TicTacToeView) findViewById(R.id.tic_tac_toe_view);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenu.this, ClassicGameActivity.class);
                startActivity(intent);
            }
        });

        playerOne = new MinimaxAI();
        playerTwo = new MinimaxAI();
    }
}
