package com.virusx.lionortiger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.gridlayout.widget.GridLayout;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.parse.ParseInstallation;
import java.util.Random;

import es.dmoral.toasty.Toasty;
import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class AndroidActivity extends AppCompatActivity {

    //declared enum variable
    private enum Player {
        ONE, TWO, INPUT
    }
    private Player currentPlayer = Player.ONE;
    private Player[] playerChoices = new Player[9];
    //declared win cases
    private int[][] winCases =
            {{0, 1, 2}, {3, 4, 5}, {6, 7, 8},
                    {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
                    {0, 4, 8}, {2, 4, 6}};

    //some variables to check the flow of the game
    private boolean[] notTapped =
            {true, true, true,
                    true, true, true,
                    true, true, true};
    private boolean GameOver = false;
    private int falseCount = 0;
    private boolean flag = true;
    private boolean showed = false;
    private int tiTag;
    private int icon;
    private String message;

    //UI components
    private Button resetBtnAndroid;
    private GridLayout gridLayout;
    private ImageView tappedImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android);

        //setting up parse server
        ParseInstallation.getCurrentInstallation().saveInBackground();

        setTitle("Tiger or Android");

        //initializing UI components
        resetBtnAndroid = findViewById(R.id.resetBtnAndroid);
        gridLayout = findViewById(R.id.gridAndroid);

        /*added reset function to the reset button, resetTheGame()
            is a function to reset the game*/
        resetBtnAndroid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTheGame();
            }
        });

        //initializing each gridLayout with no input variable
        for(int i = 0; i<playerChoices.length; i++) {
            playerChoices[i] = Player.INPUT;
        }
    }

    public void clickedOnImgView(View imageView) {
        tappedImageView = (ImageView) imageView;
        //conditional statements for the game
        if(!GameOver) {
            tiTag = Integer.parseInt(tappedImageView.getTag().toString());
            if(notTapped[tiTag]) {
                playerChoices[tiTag] = currentPlayer;
                if(falseCount != notTapped.length - 1 )
                    setImage();
                else if (falseCount == 8) {
                    checkWinner();
                    if (flag) {
                        setImage();
                        drawDialog();
                        GameOver = true;
                        resetBtnAndroid.setVisibility(View.VISIBLE);
                    }
                } else {
                    drawDialog();
                    GameOver = true;
                    resetBtnAndroid.setVisibility(View.VISIBLE);
                }
            } else {
                Toasty.info(AndroidActivity.this, "Choose another Grid",
                        Toasty.LENGTH_SHORT, true).show();
            }
            //iterating through the win cases array to find the winner
            checkWinner();
        } else {
            Toasty.warning(AndroidActivity.this, "Game Over. Reset to Play Again",
                    Toasty.LENGTH_SHORT, true).show();
        }
    }

    private void androidPlays() {
        if(!GameOver) {
            int i = getRandomNo();
            Log.i("tag3", "getRandomNo() called");
            if(notTapped[i]) {
                tiTag = i;
                Log.i("tag", "randomNo generated is: " + tiTag);
                playerChoices[tiTag] = currentPlayer;
                setAndroidImg(i);
            } else androidPlays();
            //iterating through the win cases array to find the winner
            checkWinner();
        }
    }

    private int getRandomNo() {
        Random random = new Random();
        return random.nextInt(9);
    }

    //this is a function to set image
    private void setImage() {
        icon = R.drawable.tiger;
        tappedImageView.setImageResource(icon);
        tappedImageView.setTranslationX(-2000);
        tappedImageView.animate().translationXBy(2000).alpha(1).setDuration(500);
        notTapped[tiTag] = false;
        currentPlayer = Player.TWO;
        Log.i("tag2", "androidPlay() called");
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                androidPlays();
            }
        }, 1000);
        falseCount++;
    }

    private void setAndroidImg(int i) {
        Log.i("tag4", "setImage() called for android");
        icon = R.drawable.android;
        ImageView setAndroidImage = new ImageView(AndroidActivity.this);
        setAndroidImage.setImageResource(icon);
        gridLayout.setUseDefaultMargins(true);
        setAndroidImage.setBackgroundColor(ContextCompat.getColor(this, R.color.imageBackgroundColor));
        setAndroidImage.setTranslationX(2000);
        setAndroidImage.animate().translationXBy(-2000).alpha(1).setDuration(500);
        gridLayout.addView(setAndroidImage, i);
        notTapped[i] = false;
        currentPlayer = Player.ONE;
        falseCount++;
    }

    //this is a function to check winner
    private void checkWinner() {
        for(int[] checkWinner : winCases) {
            if(playerChoices[checkWinner[0]] == playerChoices[checkWinner[1]]
                    && playerChoices[checkWinner[1]] == playerChoices[checkWinner[2]]
                    && playerChoices[checkWinner[2]] == playerChoices[checkWinner[0]]
                    && playerChoices[checkWinner[0]] != Player.INPUT) {
                if(currentPlayer == Player.TWO) {
                    if(notTapped[tiTag]) {
                        setImage();
                        flag = false;
                        message = "Android";
                        showMessage();
                        break;
                    }
                    flag = false;
                    message = "Tiger";
                } else {
                    if(notTapped[tiTag]) {
                        setImage();
                        flag = false;
                        message = "Tiger";
                        showMessage();
                        break;
                    }
                    flag = false;
                    message = "android";
                }
                showMessage();
                break;
            }
        }
    }

    //refactored code to show toast message
    private void showMessage() {
        if(!showed) {
            final PrettyDialog prettyDialog = new PrettyDialog(AndroidActivity.this);
            prettyDialog.setIcon(icon).setTitle(message + " is our Winner")
                    .addButton("Rest Game",
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
            resetBtnAndroid.setVisibility(View.VISIBLE);
            showed = true;
        }
    }

    //for draw dialog
    private void drawDialog() {
        final PrettyDialog prettyDialog = new PrettyDialog(AndroidActivity.this);
        prettyDialog.setIcon(R.drawable.warning).setTitle("It's a Draw. Rest to Play Again")
                .addButton("Rest Game",
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
    }

    // reset game function to reset all the variables
    private void resetTheGame() {
        for(int i = 0; i < gridLayout.getChildCount(); i++) {
            ImageView imageView = (ImageView) gridLayout.getChildAt(i);
            imageView.setImageDrawable(null);
            imageView.setAlpha(0.2f);
        }
        for(int i = 0; i < playerChoices.length; i++) {
            playerChoices[i] = Player.INPUT;
            notTapped[i] = true;
        }
        flag = true;
        falseCount = 0;
        resetBtnAndroid.setVisibility(View.GONE);
        GameOver = false;
        showed = false;
        currentPlayer = Player.ONE;
    }
}