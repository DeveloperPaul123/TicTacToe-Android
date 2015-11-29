package com.developerpaul123.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.MaterialDialog;
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
                new MaterialDialog.Builder(MainMenu.this)
                        .title("Play Type")
                        .items(R.array.play_type_values)
                        .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                /**
                                 * If you use alwaysCallSingleChoiceCallback(), which is discussed below,
                                 * returning false here won't allow the newly selected radio button to actually be selected.
                                 **/
                                switch (which) {
                                    case 0:
                                        Intent intent = new Intent(MainMenu.this, ClassicGameActivity.class);
                                        dialog.dismiss();
                                        startActivity(intent);
                                        break;
                                    case 1:
                                        Intent intent1 = new Intent(MainMenu.this, FourByFourGameActivity.class);
                                        dialog.dismiss();
                                        startActivity(intent1);
                                        break;
                                }
                                return true;
                            }
                        })
                        .positiveText("Ok")
                        .show();
            }
        });

        playerOne = new MinimaxAI();
        playerTwo = new MinimaxAI();
    }
}
