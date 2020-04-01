package com.virusX.lionOrTiger;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.virusX.lionOrTiger.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private int strength = 1;
    private GameMechanics gameMechanics;
    private SharedPreferences sharedPreferences;
    private static final String prefName = "scores";
    private static final String PLAYER_ONE_SCORE_PVP = "playerOneScorePvP";
    private static final String PLAYER_TWO_SCORE_PVP = "playerTwoScorePvP";
    private static final String PLAYER_ONE_SCORE_PVA = "playerOneScorePvA";
    private static final String PLAYER_TWO_SCORE_PVA = "playerTwoScorePvA";
    private int playerOneScorePvP, playerTwoScorePvP, playerOneScorePvA, playerTwoScorePvA;
    private boolean isOnePlayerGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        sharedPreferences = getSharedPreferences(prefName, MODE_PRIVATE);

        binding.gameTypeRGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.onePlayerRBtn:
                        binding.gameStrengthRGrp.setVisibility(View.VISIBLE);
                        initOnePlayerGame();
                        break;
                    case R.id.twoPlayerRBtn:
                        binding.gameStrengthRGrp.setVisibility(View.GONE);
                        initTwoPlayerGame();
                        break;
                    default:
                        break;
                }
            }
        });

        binding.gameStrengthRGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.easyRBtn:
                        strength = 1;
                        break;
                    case R.id.modRBtn:
                        strength = 2;
                        break;
                    case R.id.expRBtn:
                        strength = 3;
                        break;
                    default:
                        break;
                }
            }
        });

        binding.resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameMechanics.resetTheGame();
            }
        });

        gameMechanics = new GameMechanics(binding, this);

        if(binding.twoPlayerRBtn.isChecked()) {
            initTwoPlayerGame();
        }
    }

    public void tappedOnGrid(View tappedView) {
        ImageView tappedImgView = (ImageView) tappedView;
        gameMechanics.run(tappedImgView, isOnePlayerGame, strength);
    }

    @SuppressLint("SetTextI18n")
    private void initOnePlayerGame() {
        binding.firstPlayerImg.setImageResource(R.drawable.tiger);
        binding.playerOneImg.setImageResource(R.drawable.tiger);
        binding.secondPlayerImg.setImageResource(R.drawable.android);
        binding.playerTwoImg.setImageResource(R.drawable.android);

        playerOneScorePvA = sharedPreferences.getInt(PLAYER_ONE_SCORE_PVA, 0);
        playerTwoScorePvA = sharedPreferences.getInt(PLAYER_TWO_SCORE_PVA, 0);

        binding.scorePlayerOne.setText(Integer.toString(playerOneScorePvA));
        binding.scorePlayerTwo.setText(Integer.toString(playerTwoScorePvA));

        gameMechanics.setPlayerOneScore(playerOneScorePvA);
        gameMechanics.setPlayerTwoScore(playerTwoScorePvA);

        gameMechanics.setPlayerImg(R.drawable.android);

        isOnePlayerGame = true;
    }

    @SuppressLint("SetTextI18n")
    private void initTwoPlayerGame() {
        binding.firstPlayerImg.setImageResource(R.drawable.tiger);
        binding.playerOneImg.setImageResource(R.drawable.tiger);
        binding.secondPlayerImg.setImageResource(R.drawable.lion);
        binding.playerTwoImg.setImageResource(R.drawable.lion);

        playerOneScorePvP = sharedPreferences.getInt(PLAYER_ONE_SCORE_PVP, 0);
        playerTwoScorePvP = sharedPreferences.getInt(PLAYER_TWO_SCORE_PVP, 0);

        binding.scorePlayerOne.setText(Integer.toString(playerOneScorePvP));
        binding.scorePlayerTwo.setText(Integer.toString(playerTwoScorePvP));

        gameMechanics.setPlayerOneScore(playerOneScorePvP);
        gameMechanics.setPlayerTwoScore(playerTwoScorePvP);

        gameMechanics.setPlayerImg(R.drawable.lion);

        isOnePlayerGame = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        sharedPreferences = getSharedPreferences(prefName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        playerOneScorePvP = gameMechanics.getPlayerOneScore();
        playerTwoScorePvP = gameMechanics.getPlayerTwoScore();

        editor.putInt(PLAYER_ONE_SCORE_PVP, playerOneScorePvP);
        editor.putInt(PLAYER_TWO_SCORE_PVP, playerTwoScorePvP);

        editor.putInt(PLAYER_ONE_SCORE_PVA, playerOneScorePvA);
        editor.putInt(PLAYER_TWO_SCORE_PVA, playerTwoScorePvA);

        editor.apply();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }
}
