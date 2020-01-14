package com.virusx.lionortiger;


import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.gridlayout.widget.GridLayout;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.parse.ParseInstallation;

import java.util.Random;

import es.dmoral.toasty.Toasty;
import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;


/**
 * A simple {@link Fragment} subclass.
 */
public class EasyGameModeFragment extends Fragment {

    public EasyGameModeFragment() {
        // Required empty public constructor
    }

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
    private ImageView imgOne,imgTwo, imgThree, imgFour, imgFive, imgSix, imgSeven, imgEight, imgNine;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game_layout, container, false);

        //setting up parse server
        ParseInstallation.getCurrentInstallation().saveInBackground();

        //initializing UI components
        resetBtnAndroid = view.findViewById(R.id.resetBtnAndroid);
        gridLayout = view.findViewById(R.id.gridAndroid);

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

        imgOne = view.findViewById(R.id.imgOne);
        imgTwo = view.findViewById(R.id.imgTwo);
        imgThree = view.findViewById(R.id.imgThree);
        imgFour = view.findViewById(R.id.imgFour);
        imgFive = view.findViewById(R.id.imgFive);
        imgSix = view.findViewById(R.id.imgSix);
        imgSeven = view.findViewById(R.id.imgSeven);
        imgEight = view.findViewById(R.id.imgEight);
        imgNine = view.findViewById(R.id.imgNine);

        imgOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tappedImageView = imgOne;
                tappedOnImgViewEasy();
            }
        });

        imgTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tappedImageView = imgTwo;
                tappedOnImgViewEasy();
            }
        });

        imgThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tappedImageView = imgThree;
                tappedOnImgViewEasy();
            }
        });

        imgFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tappedImageView = imgFour;
                tappedOnImgViewEasy();
            }
        });

        imgFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tappedImageView = imgFive;
                tappedOnImgViewEasy();
            }
        });

        imgSix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tappedImageView = imgSix;
                tappedOnImgViewEasy();
            }
        });

        imgSeven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tappedImageView = imgSeven;
                tappedOnImgViewEasy();
            }
        });

        imgEight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tappedImageView = imgEight;
                tappedOnImgViewEasy();
            }
        });

        imgNine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tappedImageView = imgNine;
                tappedOnImgViewEasy();
            }
        });

        return view;
    }

    private void tappedOnImgViewEasy() {
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
                Toasty.info(getContext(), "Choose another Grid",
                        Toasty.LENGTH_SHORT, true).show();
            }
            //iterating through the win cases array to find the winner
            checkWinner();
        } else {
            Toasty.warning(getContext(), "Game Over. Reset to Play Again",
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
                setAndroidImg();
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
        tappedImageView.animate().translationXBy(2000).alpha(1).setDuration(100);
        notTapped[tiTag] = false;
        currentPlayer = Player.TWO;
        falseCount++;
        Log.i("tag2", "androidPlay() called");
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                androidPlays();
            }
        }, 150);
    }

    private void setAndroidImg() {
        icon = R.drawable.android;
        switch (tiTag) {
            case 0:
                gridLayout.removeView(imgOne);
                imgOne.setImageResource(icon);
                gridLayout.setUseDefaultMargins(true);
                imgOne.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.imageBackgroundColor));
                imgOne.setTranslationX(2000);
                imgOne.animate().translationXBy(-2000).alpha(1).setDuration(100);
                Log.i("tags", "i is: " + tiTag);
                gridLayout.addView(imgOne, tiTag);
                break;
            case 1:
                gridLayout.removeView(imgTwo);
                imgTwo.setImageResource(icon);
                gridLayout.setUseDefaultMargins(true);
                imgTwo.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.imageBackgroundColor));
                imgTwo.setTranslationX(2000);
                imgTwo.animate().translationXBy(-2000).alpha(1).setDuration(100);
                Log.i("tags", "i is: " + tiTag);
                gridLayout.addView(imgTwo, tiTag);
                break;
            case 2:
                gridLayout.removeView(imgThree);
                imgThree.setImageResource(icon);
                gridLayout.setUseDefaultMargins(true);
                imgThree.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.imageBackgroundColor));
                imgThree.setTranslationX(2000);
                imgThree.animate().translationXBy(-2000).alpha(1).setDuration(100);
                Log.i("tags", "i is: " + tiTag);
                gridLayout.addView(imgThree, tiTag);
                break;
            case 3:
                gridLayout.removeView(imgFour);
                imgFour.setImageResource(icon);
                gridLayout.setUseDefaultMargins(true);
                imgFour.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.imageBackgroundColor));
                imgFour.setTranslationX(2000);
                imgFour.animate().translationXBy(-2000).alpha(1).setDuration(100);
                Log.i("tags", "i is: " + tiTag);
                gridLayout.addView(imgFour, tiTag);
                break;
            case 4:
                gridLayout.removeView(imgFive);
                imgFive.setImageResource(icon);
                gridLayout.setUseDefaultMargins(true);
                imgFive.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.imageBackgroundColor));
                imgFive.setTranslationX(2000);
                imgFive.animate().translationXBy(-2000).alpha(1).setDuration(100);
                Log.i("tags", "i is: " + tiTag);
                gridLayout.addView(imgFive, tiTag);
                break;
            case 5:
                gridLayout.removeView(imgSix);
                imgSix.setImageResource(icon);
                gridLayout.setUseDefaultMargins(true);
                imgSix.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.imageBackgroundColor));
                imgSix.setTranslationX(2000);
                imgSix.animate().translationXBy(-2000).alpha(1).setDuration(100);
                Log.i("tags", "i is: " + tiTag);
                gridLayout.addView(imgSix, tiTag);
                break;
            case 6:
                gridLayout.removeView(imgSeven);
                imgSeven.setImageResource(icon);
                gridLayout.setUseDefaultMargins(true);
                imgSeven.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.imageBackgroundColor));
                imgSeven.setTranslationX(2000);
                imgSeven.animate().translationXBy(-2000).alpha(1).setDuration(100);
                Log.i("tags", "i is: " + tiTag);
                gridLayout.addView(imgSeven, tiTag);
                break;
            case 7:
                gridLayout.removeView(imgEight);
                imgEight.setImageResource(icon);
                gridLayout.setUseDefaultMargins(true);
                imgEight.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.imageBackgroundColor));
                imgEight.setTranslationX(2000);
                imgEight.animate().translationXBy(-2000).alpha(1).setDuration(500);
                Log.i("tags", "i is: " + tiTag);
                gridLayout.addView(imgEight, tiTag);
                break;
            case 8:
                gridLayout.removeView(imgNine);
                imgNine.setImageResource(icon);
                gridLayout.setUseDefaultMargins(true);
                imgNine.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.imageBackgroundColor));
                imgNine.setTranslationX(2000);
                imgNine.animate().translationXBy(-2000).alpha(1).setDuration(100);
                Log.i("tags", "i is: " + tiTag);
                gridLayout.addView(imgNine, tiTag);
                break;
            default: break;
        }
        notTapped[tiTag] = false;
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
            final PrettyDialog prettyDialog = new PrettyDialog(getContext());
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
            resetBtnAndroid.setVisibility(View.VISIBLE);
            showed = true;
        }
    }

    //for draw dialog
    private void drawDialog() {
        final PrettyDialog prettyDialog = new PrettyDialog(getContext());
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
