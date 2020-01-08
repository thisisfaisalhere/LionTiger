package com.virusx.lionortiger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.parse.ParseInstallation;
import es.dmoral.toasty.Toasty;
import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class PlayerActivity extends AppCompatActivity {

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
    private Button resetBtn;
    private GridLayout grid;
    private ImageView tappedImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        //setting up parse server
        ParseInstallation.getCurrentInstallation().saveInBackground();

        //initializing UI components
        resetBtn = findViewById(R.id.resetBtn);
        grid = findViewById(R.id.grid);

        /*added reset function to the reset button, resetTheGame()
            is a function to reset the game*/
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTheGame();
            }
        });

        //initializing each grid with no input variable
        for(int i = 0; i<playerChoices.length; i++) {
            playerChoices[i] = Player.INPUT;
        }
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
            if(notTapped[tiTag - 1]) {
                playerChoices[tiTag - 1] = currentPlayer;
                if(falseCount != notTapped.length - 1 )
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
        if(currentPlayer == Player.ONE) {
            icon = R.drawable.tiger;
            currentPlayer = Player.TWO;
            translationValue = 2000;
        } else if(currentPlayer == Player.TWO) {
            icon = R.drawable.lion;
            currentPlayer = Player.ONE;
            translationValue = -2000;
        }
        tappedImageView.setImageResource(icon);
        tappedImageView.setTranslationX(translationValue);
        tappedImageView.animate().translationXBy(2000).alpha(1).setDuration(500);
        notTapped[tiTag - 1] = false;
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
                    if(notTapped[tiTag - 1]) {
                        setImage();
                        flag = false;
                        message = "Lion";
                        showMessage();
                        break;
                    }
                    flag = false;
                    message = "Tiger";
                } else {
                    if(notTapped[tiTag - 1]) {
                        setImage();
                        flag = false;
                        message = "Tiger";
                        showMessage();
                        break;
                    }
                    flag = false;
                    message = "Lion";
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
            resetBtn.setVisibility(View.VISIBLE);
            showed = true;
        }
    }

    //for draw dialog
    private void drawDialog() {
        final PrettyDialog prettyDialog = new PrettyDialog(PlayerActivity.this);
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
        for(int i = 0; i < grid.getChildCount(); i++) {
            ImageView imageView = (ImageView) grid.getChildAt(i);
            imageView.setImageDrawable(null);
            imageView.setAlpha(0.2f);
            playerChoices[i] = Player.INPUT;
            notTapped[i] = true;
        }
        flag = true;
        falseCount = 0;
        resetBtn.setVisibility(View.GONE);
        GameOver = false;
        showed = false;
        currentPlayer = Player.ONE;
    }
}