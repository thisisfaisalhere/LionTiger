package com.virusx.lionortiger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.gridlayout.widget.GridLayout;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import es.dmoral.toasty.Toasty;
import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class PlayerActivity extends AppCompatActivity {

    //declared enum variable
    private boolean GameOver = false;
    private int falseCount = 0;
    private boolean flag = true;
    private boolean showed = false;
    private int tiTag;
    private int icon;
    private String message;
    private Variables variables;
    private boolean isDraw = false;
    private Variables.Player winner;
    private int playerOneWinCount, playerTwoWinCount, drawCount;

    //UI components
    private Button resetBtn;
    private GridLayout grid;
    private ImageView tappedImageView, firstPlayer, secondPlayer;
    private TextView scorePlayerOne, scorePlayerTwo, countDraw;

    private SharedPreferences sharedPreferences;
    private static final String prefName = "scores";
    private static final String scoreOneKey = "playerOneScorePvP";
    private static final String scoreTwoKey = "playerTwoScorePvP";
    private static final String drawCountKey = "drawCountPvP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        //initializing UI components
        resetBtn = findViewById(R.id.resetBtn);
        grid = findViewById(R.id.grid);

        scorePlayerOne = findViewById(R.id.scorePlayerOne);
        scorePlayerTwo = findViewById(R.id.scorePlayerTwo);
        countDraw = findViewById(R.id.countDraw);

        sharedPreferences = getSharedPreferences(prefName, MODE_APPEND);

        playerOneWinCount = sharedPreferences.getInt(scoreOneKey, 0);
        playerTwoWinCount = sharedPreferences.getInt(scoreTwoKey, 0);
        drawCount = sharedPreferences.getInt(drawCountKey, 0);

        scorePlayerOne.setText(playerOneWinCount + "");
        scorePlayerTwo.setText(playerTwoWinCount + "");
        countDraw.setText(drawCount + "");

        firstPlayer = findViewById(R.id.firstPlayer);
        secondPlayer = findViewById(R.id.secondPlayer);
        firstPlayer.setBackgroundColor(ContextCompat.getColor(this, R.color.imageBackgroundColor));

        /*added reset function to the reset button, resetTheGame()
            is a function to reset the game*/
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTheGame();
            }
        });

        variables = new Variables();
        variables.playerChoicesInitializer();
        variables.setCurrentPlayer(Variables.Player.ONE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sharedPreferences = getSharedPreferences("scores", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(scoreOneKey, playerOneWinCount);
        editor.putInt(scoreTwoKey, playerTwoWinCount);
        editor.putInt(drawCountKey, drawCount);

        editor.apply();
    }

    public void tappedOnImgGrid(View imageView) {
        tappedImageView = (ImageView) imageView;

        /*tiTag or textImageTag is a variable to store the
            tag value to each grid. Tag value is starting from 1 and
            ends with 9 so the 1 is subtracted
         */
        //conditional statements for the game
        if(!GameOver) {
            tiTag = Integer.parseInt(tappedImageView.getTag().toString());
            if(variables.getNotTapped(tiTag - 1)) {
                variables.setPlayerChoice(variables.getCurrentPlayer(), tiTag - 1);
                if(falseCount != variables.notTappedLength() - 1 )
                    setImage();
                else if (falseCount == 8) {
                    checkWinner();
                    if (flag) {
                        setImage();
                        drawDialog();
                        GameOver = true;
                        resetBtn.setVisibility(View.VISIBLE);
                    }
                } else {
                    drawDialog();
                    GameOver = true;
                    resetBtn.setVisibility(View.VISIBLE);
                }
            } else {
                Toasty.info(PlayerActivity.this, "Choose another Grid",
                        Toasty.LENGTH_SHORT, true).show();
            }
            //iterating through the win cases array to find the winner
            checkWinner();
        } else {
            Toasty.warning(PlayerActivity.this, "Game Over. Reset to Play Again",
                    Toasty.LENGTH_SHORT, true).show();
        }
    }

    //this is a function to set image
    private void setImage() {
        int translationValue = 0;
        int translationXByValue = 0;
        if(variables.getCurrentPlayer() == Variables.Player.ONE) {
            icon = R.drawable.tiger;
            variables.setCurrentPlayer(Variables.Player.TWO);
            translationValue = -2000;
            translationXByValue = 2000;
            firstPlayer.setBackgroundColor(ContextCompat
                    .getColor(this, R.color.rootLayoutColor));
            secondPlayer.setBackgroundColor(ContextCompat
                    .getColor(this, R.color.imageBackgroundColor));
        } else if(variables.getCurrentPlayer() == Variables.Player.TWO) {
            icon = R.drawable.lion;
            variables.setCurrentPlayer(Variables.Player.ONE);
            translationValue = 2000;
            translationXByValue = -2000;
            secondPlayer.setBackgroundColor(ContextCompat
                    .getColor(this, R.color.rootLayoutColor));
            firstPlayer.setBackgroundColor(ContextCompat
                    .getColor(this, R.color.imageBackgroundColor));
        }
        tappedImageView.setImageResource(icon);
        tappedImageView.setTranslationX(translationValue);
        tappedImageView.animate().translationXBy(translationXByValue).alpha(1).setDuration(500);
        variables.setNotTapped(tiTag - 1);
        falseCount++;
    }

    //this is a function to check winner
    private void checkWinner() {
        int[][] winCases = variables.getWinCases();
        Variables.Player[] choicesByPlayer = variables.getPlayerChoices();
        for(int[] checkWinner : winCases) {
            if(choicesByPlayer[checkWinner[0]] == choicesByPlayer[checkWinner[1]]
                    && choicesByPlayer[checkWinner[1]] == choicesByPlayer[checkWinner[2]]
                    && choicesByPlayer[checkWinner[2]] == choicesByPlayer[checkWinner[0]]
                    && choicesByPlayer[checkWinner[0]] != Variables.Player.INPUT) {
                if(variables.getCurrentPlayer() == Variables.Player.TWO) {
                    if(variables.getNotTapped(tiTag - 1)) {
                        setImage();
                        flag = false;
                        message = "Lion";
                        winner = Variables.Player.TWO;
                        showMessage();
                        break;
                    }
                    flag = false;
                    message = "Tiger";
                    winner = Variables.Player.ONE;
                } else {
                    if(variables.getNotTapped(tiTag - 1)) {
                        setImage();
                        flag = false;
                        message = "Tiger";
                        winner = Variables.Player.ONE;
                        showMessage();
                        break;
                    }
                    flag = false;
                    message = "Lion";
                    winner = Variables.Player.TWO;
                }
                showMessage();
                break;
            }
        }
    }

    //refactored code to show toast message
    private void showMessage() {
        if(!showed) {
            final PrettyDialog prettyDialog = new PrettyDialog(PlayerActivity.this);
            prettyDialog.setIcon(icon).setTitle(message + " is our Winner")
                    .addButton("Reset Game",
                            R.color.pdlg_color_white,  // button text color
                            R.color.pdlg_color_green,  // button background color
                            new PrettyDialogCallback() {  // button OnClick listener
                                @Override
                                public void onClick() {
                                    resetTheGame();
                                    prettyDialog.dismiss();
                                }
                            }
                    ).show();
            GameOver = true;
            resetBtn.setVisibility(View.VISIBLE);
            showed = true;
        }
    }

    //for draw dialog
    private void drawDialog() {
        final PrettyDialog prettyDialog = new PrettyDialog(PlayerActivity.this);
        prettyDialog.setIcon(R.drawable.warning).setTitle("It's a Draw. Rest to Play Again")
                .addButton("Reset Game",
                R.color.pdlg_color_white,  // button text color
                R.color.pdlg_color_green,  // button background color
                new PrettyDialogCallback() {  // button OnClick listener
                    @Override
                    public void onClick() {
                        resetTheGame();
                        prettyDialog.dismiss();
                    }
                }
        ).show();
        isDraw = true;
    }

    // reset game function to reset all the variables
    private void resetTheGame() {
        for(int i = 0; i < grid.getChildCount(); i++) {
            ImageView imageView = (ImageView) grid.getChildAt(i);
            imageView.setImageDrawable(null);
            imageView.setAlpha(0.2f);
        }
        variables.playerChoicesInitializer();
        variables.notTappedInitializer();
        flag = true;
        falseCount = 0;
        resetBtn.setVisibility(View.GONE);
        GameOver = false;
        showed = false;

        if(isDraw) {
            if(variables.getCurrentPlayer() == Variables.Player.ONE){
                variables.setCurrentPlayer(Variables.Player.ONE);
                secondPlayer.setBackgroundColor(ContextCompat.getColor(this, R.color.rootLayoutColor));
                firstPlayer.setBackgroundColor(ContextCompat.getColor(this, R.color.imageBackgroundColor));
            }
            else{
                variables.setCurrentPlayer(Variables.Player.TWO);
                firstPlayer.setBackgroundColor(ContextCompat.getColor(this, R.color.rootLayoutColor));
                secondPlayer.setBackgroundColor(ContextCompat.getColor(this, R.color.imageBackgroundColor));
            }
            drawCount++;
            countDraw.setText(drawCount + "");
            isDraw = false;
        } else {
            if(winner == Variables.Player.TWO) {
                variables.setCurrentPlayer(Variables.Player.TWO);
                firstPlayer.setBackgroundColor(ContextCompat.getColor(this, R.color.rootLayoutColor));
                secondPlayer.setBackgroundColor(ContextCompat.getColor(this, R.color.imageBackgroundColor));
                playerTwoWinCount++;
                scorePlayerTwo.setText(playerTwoWinCount + "");
            } else {
                variables.setCurrentPlayer(Variables.Player.ONE);
                secondPlayer.setBackgroundColor(ContextCompat.getColor(this, R.color.rootLayoutColor));
                firstPlayer.setBackgroundColor(ContextCompat.getColor(this, R.color.imageBackgroundColor));
                playerOneWinCount++;
                scorePlayerOne.setText(playerOneWinCount + "");
            }
        }
    }
}