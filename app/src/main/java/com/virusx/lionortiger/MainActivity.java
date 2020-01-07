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

public class MainActivity extends AppCompatActivity {

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
    private Player icon;

    //some variables to check the flow of the game
    private boolean[] notTapped =
            {true, true, true,
            true, true, true,
            true, true, true};
    private boolean notGameOver = true;
    private int falseCount = 0;
    private boolean flag = true;
    private int tiTag;

    //UI components
    private Button resetBtn;
    private GridLayout grid;
    private ImageView tappedImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        tiTag = Integer.parseInt(tappedImageView.getTag().toString());
        //conditional statements for the game
        if(notGameOver) {
            if(notTapped[tiTag - 1]) {
                playerChoices[tiTag - 1] = currentPlayer;
                if(falseCount != notTapped.length - 1 ) {
                    setImage();
                } else if (falseCount == 8) {
                    checkWinner();
                    if (flag) {
                        setImage();
                        drawDialog();
                        notGameOver = false;
                        resetBtn.setVisibility(View.VISIBLE);
                    }
                }
                else {
                    drawDialog();
                    notGameOver = false;
                    resetBtn.setVisibility(View.VISIBLE);
                }
            } else {
                Toasty.info(MainActivity.this, "Choose another Grid",
                        Toasty.LENGTH_SHORT, true).show();
            }

            //iterating through the win cases array to find the winner
            checkWinner();
        } else {
            Toasty.warning(MainActivity.this, "Game Over. Reset to Play Again",
                    Toasty.LENGTH_SHORT, true).show();
        }
    }

    //this is a function to set image
    private void setImage() {
        if(currentPlayer == Player.ONE) {
            tappedImageView.setImageResource(R.drawable.tiger);
            tappedImageView.setTranslationX(-2000);
            tappedImageView.animate().translationXBy(2000).alpha(1).setDuration(500);
            currentPlayer = Player.TWO;
        } else if(currentPlayer == Player.TWO) {
            tappedImageView.setImageResource(R.drawable.lion);
            tappedImageView.setTranslationX(2000);
            tappedImageView.animate().translationXBy(-2000).alpha(1).setDuration(500);
            currentPlayer = Player.ONE;
        }
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
                        icon = Player.TWO;
                        showMessage();
                        flag = false;
                        break;
                    }
                    icon = Player.TWO;
                    showMessage();
                } else if(currentPlayer == Player.ONE) {
                    if(notTapped[tiTag - 1]) {
                        setImage();
                        icon = Player.ONE;
                        showMessage();
                        flag = false;
                        break;
                    }
                    icon = Player.ONE;
                    showMessage();
                    flag = false;
                }
            }
        }
    }

    //refactored code to show toast message
    private void showMessage() {
        final PrettyDialog prettyDialog = new PrettyDialog(MainActivity.this);
        if (icon == Player.ONE) {
            prettyDialog.setIcon(R.drawable.lion).setTitle("Lion is our Champion").addButton(
                    "Rest Game",
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
        } else if (icon == Player.TWO) {
            prettyDialog.setIcon(R.drawable.tiger).setTitle("Tiger is our Champion").addButton(
                    "Rest Game",
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
        notGameOver = false;
        resetBtn.setVisibility(View.VISIBLE);
    }

    //for draw dialog
    private void drawDialog() {
        final PrettyDialog prettyDialog = new PrettyDialog(MainActivity.this);
        prettyDialog.setIcon(R.drawable.warning).setTitle("It's a Draw. Rest to Play Again").addButton(
                "Rest Game",
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
        notGameOver = true;
        currentPlayer = Player.ONE;
    }
}