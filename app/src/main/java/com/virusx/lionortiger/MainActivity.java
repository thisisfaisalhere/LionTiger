package com.virusx.lionortiger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.parse.ParseInstallation;
import es.dmoral.toasty.Toasty;

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
    //some variables to check the flow of the game
    private boolean[] notTapped =
            {true, true, true,
            true, true, true,
            true, true, true};
    private boolean notGameOver = true;
    private int falseCount = 0;
    private String message;

    //UI components
    private Button resetBtn;
    private GridLayout grid;

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
        ImageView tappedImageView = (ImageView) imageView;

        /*tiTag or textImageTag is a variable to store the
            tag value to each grid. Tag value is starting from 1 and
            ends with 9 so the 1 is subtracted
         */
        int tiTag = Integer.parseInt(tappedImageView.getTag().toString());
        playerChoices[tiTag - 1] = currentPlayer;

        //conditional statements for the game
        if(notGameOver) {                                                                              // is game is over?
            if(notTapped[tiTag - 1]) {                                                                 // is the tapped grid is tapped before?
                if(falseCount != notTapped.length - 1 ) {                                              // is false count is less than 8?
                    if(currentPlayer == Player.ONE) {
                        tappedImageView.setImageResource(R.drawable.tiger);
                        tappedImageView.setTranslationX(-2000);
                        tappedImageView.animate().translationXBy(2000).alpha(1).setDuration(500);
                        currentPlayer = Player.TWO;
                        notTapped[tiTag - 1] = false;
                        falseCount++;
                    } else if(currentPlayer == Player.TWO) {
                        tappedImageView.setImageResource(R.drawable.lion);
                        tappedImageView.setTranslationX(2000);
                        tappedImageView.animate().translationXBy(-2000).alpha(1).setDuration(500);
                        currentPlayer = Player.ONE;
                        notTapped[tiTag - 1] = false;
                        falseCount++;
                    }
                } else {
                    Toasty.info(MainActivity.this, "Its a Draw! Reset to Play Again", Toast.LENGTH_SHORT, true).show();
                    notGameOver = false;
                    resetBtn.setVisibility(View.VISIBLE);
                }
            } else {
                Toasty.warning(MainActivity.this, "Choose another Grid", Toast.LENGTH_SHORT, true).show();
            }

            //iterating through the win cases array to find the winner
            for(int[] checkWinner : winCases) {
                if(playerChoices[checkWinner[0]] == playerChoices[checkWinner[1]]
                        && playerChoices[checkWinner[1]] == playerChoices[checkWinner[2]]
                        && playerChoices[checkWinner[0]] != Player.INPUT) {
                    if(currentPlayer == Player.TWO) {
                        if(notTapped[tiTag - 1]) {
                            tappedImageView.setImageResource(R.drawable.lion);
                            tappedImageView.setTranslationX(-2000);
                            tappedImageView.animate().translationXBy(2000).alpha(1).setDuration(500);
                            message = "Lion is Our Champion";
                            showMessage();
                            break;
                        }
                        message = "Tiger is Our Champion";
                        showMessage();
                    } else if(currentPlayer == Player.ONE) {
                        if(notTapped[tiTag - 1]) {
                            tappedImageView.setImageResource(R.drawable.tiger);
                            tappedImageView.setTranslationX(2000);
                            tappedImageView.animate().translationXBy(-2000).alpha(1).setDuration(500);
                            message = "Tiger is Our Champion";
                            showMessage();
                            break;
                        }
                        message = "Lion is Our Champion";
                        showMessage();
                    }
                }
            }
        } else {
            Toasty.warning(MainActivity.this, "Game Over. Reset to Play Again", Toast.LENGTH_SHORT, true).show();
        }
    }

    //refactored code to show toast message
    private void showMessage() {
        Toasty.success(MainActivity.this, message, Toast.LENGTH_SHORT, true).show();
        notGameOver = false;
        resetBtn.setVisibility(View.VISIBLE);
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
        falseCount = 0;
        resetBtn.setVisibility(View.GONE);
        notGameOver = true;
        currentPlayer = Player.ONE;
    }
}
