package com.virusx.lionortiger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private enum Player {
        ONE, TWO, INPUT
    }

    private Player currentPlayer = Player.ONE;
    private Player[] playerChoices = new Player[9];

    private int[][] winCases = {{0,1,2}, {3,4,5}, {6,7,8},
            {0,3,6}, {1,4,7},{2,5,8},
            {0,4,8},{2,4,6}};

    private boolean[] notTapped = {true, true, true,
            true, true, true,
            true, true, true};

    private boolean notGameOver = true;

    private Button resetBtn;
    private GridLayout grid;
    private int falseCount = 0;
    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resetBtn = findViewById(R.id.resetBtn);
        grid = findViewById(R.id.grid);

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTheGame();
            }
        });

        for(int i = 0; i<playerChoices.length; i++) {
            playerChoices[i] = Player.INPUT;
        }
    }

    public void tappedOnImgGrid(View imageView) {
        ImageView tappedImageView = (ImageView) imageView;

        int tiTag = Integer.parseInt(tappedImageView.getTag().toString());
        playerChoices[tiTag - 1] = currentPlayer;

        if(notGameOver) {
            if(notTapped[tiTag - 1]) {
                if(falseCount != notTapped.length - 1 ) {
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
                    message = "Its a Draw! Reset to Play Again";
                    showMessage();
                }
            } else {
                Toast.makeText(MainActivity.this, "Choose another Grid", Toast.LENGTH_SHORT).show();
            }

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
            Toast.makeText(MainActivity.this, "Game Over. Reset to Play Again", Toast.LENGTH_SHORT).show();
        }
    }

    private void showMessage() {
        Toast.makeText(MainActivity.this, message , Toast.LENGTH_SHORT).show();
        notGameOver = false;
        resetBtn.setVisibility(View.VISIBLE);
    }

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
