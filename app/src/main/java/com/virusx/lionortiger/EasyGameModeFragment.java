package com.virusx.lionortiger;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.gridlayout.widget.GridLayout;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import es.dmoral.toasty.Toasty;
import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class EasyGameModeFragment extends Fragment {

    public EasyGameModeFragment() {
        // Required empty public constructor
    }

    private boolean GameOver = false;
    private int falseCount = 0;
    private boolean flag = true;
    private boolean showed = false;
    private boolean yourTurn = true;
    private int tiTag;
    private int icon;
    private String message;
    private Variables variables;
    private GetGridLocation getGridLocation;
    private boolean isDraw = false;
    private Variables.Player winner;
    private int playerOneWinCount, playerTwoWinCount, drawCount;

    //UI components
    private Button resetBtnAndroid;
    private GridLayout gridLayout;
    private ImageView tappedImageView, imgOne, imgTwo, imgThree,
            imgFour, imgFive, imgSix, imgSeven, imgEight, imgNine,
            firstPlayerImg, secondPlayerImg, reference;
    private TextView scorePlayerOne, scorePlayerTwo, countDraw;

    private SharedPreferences sharedPreferences;
    private static final String prefName = "scores";
    private static final String scoreOneKey = "playerOneScorePvA";
    private static final String scoreTwoKey = "playerTwoScorePvA";
    private static final String drawCountKey = "drawCountPvA";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game_layout, container, false);

        //initializing UI components
        resetBtnAndroid = view.findViewById(R.id.resetBtnAndroid);
        gridLayout = view.findViewById(R.id.gridAndroid);

        scorePlayerOne = view.findViewById(R.id.playerOneScore);
        scorePlayerTwo = view.findViewById(R.id.playerTwoScore);
        countDraw = view.findViewById(R.id.drawCountTxt);

        sharedPreferences = getContext().getSharedPreferences(prefName, Context.MODE_PRIVATE);

        playerOneWinCount = sharedPreferences.getInt(scoreOneKey, 0);
        playerTwoWinCount = sharedPreferences.getInt(scoreTwoKey, 0);
        drawCount = sharedPreferences.getInt(drawCountKey, 0);

        scorePlayerOne.setText(playerOneWinCount + "");
        scorePlayerTwo.setText(playerTwoWinCount + "");
        countDraw.setText(drawCount + "");

        /*added reset function to the reset button, resetTheGame()
            is a function to reset the game*/
        resetBtnAndroid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTheGame();
            }
        });

        variables = new Variables();
        variables.playerChoicesInitializer();
        variables.setCurrentPlayer(Variables.Player.ONE);

        imgOne = view.findViewById(R.id.imgOne);
        imgTwo = view.findViewById(R.id.imgTwo);
        imgThree = view.findViewById(R.id.imgThree);
        imgFour = view.findViewById(R.id.imgFour);
        imgFive = view.findViewById(R.id.imgFive);
        imgSix = view.findViewById(R.id.imgSix);
        imgSeven = view.findViewById(R.id.imgSeven);
        imgEight = view.findViewById(R.id.imgEight);
        imgNine = view.findViewById(R.id.imgNine);

        firstPlayerImg = view.findViewById(R.id.gameLayoutFirst);
        secondPlayerImg = view.findViewById(R.id.gameLayoutSecond);

        if(variables.getCurrentPlayer() == Variables.Player.ONE) {
            firstPlayerImg.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.imageBackgroundColor));
            secondPlayerImg.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.rootLayoutColor));
            yourTurn = true;
        } else {
            firstPlayerImg.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.imageBackgroundColor));
            secondPlayerImg.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.rootLayoutColor));
            yourTurn = false;
        }

        imgOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tappedImageView = imgOne;
                tappedOnImgView();
            }
        });

        imgTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tappedImageView = imgTwo;
                tappedOnImgView();
            }
        });

        imgThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tappedImageView = imgThree;
                tappedOnImgView();
            }
        });

        imgFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tappedImageView = imgFour;
                tappedOnImgView();
            }
        });

        imgFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tappedImageView = imgFive;
                tappedOnImgView();
            }
        });

        imgSix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tappedImageView = imgSix;
                tappedOnImgView();
            }
        });

        imgSeven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tappedImageView = imgSeven;
                tappedOnImgView();
            }
        });

        imgEight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tappedImageView = imgEight;
                tappedOnImgView();
            }
        });

        imgNine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tappedImageView = imgNine;
                tappedOnImgView();
            }
        });

        if(variables.getCurrentPlayer() == Variables.Player.TWO) {
            androidPlays();
            yourTurn = true;
        }

        getGridLocation = new GetGridLocation(0);

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        sharedPreferences = getContext().getSharedPreferences("scores", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(scoreOneKey, playerOneWinCount);
        editor.putInt(scoreTwoKey, playerTwoWinCount);
        editor.putInt(drawCountKey, drawCount);

        editor.apply();
    }

    private void tappedOnImgView() {
        if (!GameOver) {
            if (yourTurn) {
                tiTag = Integer.parseInt(tappedImageView.getTag().toString());
                if (variables.getNotTapped(tiTag)) {
                    variables.setPlayerChoice(variables.getCurrentPlayer(), tiTag);
                    if (falseCount != variables.notTappedLength() - 1)
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
                Toasty.warning(getContext(), "Slow Down Bud.",
                        Toasty.LENGTH_SHORT, true).show();
            }
        } else{
            Toasty.warning(getContext(), "Game Over. Reset to Play Again",
                    Toasty.LENGTH_SHORT, true).show();
        }
    }

    private void androidPlays() {
        if (!GameOver) {
            tiTag = getGridLocation.getRandomNo(2);
            if (variables.getNotTapped(tiTag)) {
                variables.setPlayerChoice(variables.getCurrentPlayer(), tiTag);
                if (falseCount != variables.notTappedLength() - 1)
                    setAndroidImg();
                else if (falseCount == 8) {
                    checkWinner();
                    if (flag) {
                        setAndroidImg();
                        drawDialog();
                        GameOver = true;
                        resetBtnAndroid.setVisibility(View.VISIBLE);
                    }
                } else {
                    drawDialog();
                    GameOver = true;
                    resetBtnAndroid.setVisibility(View.VISIBLE);
                }
                //iterating through the win cases array to find the winner
                checkWinner();
            } else androidPlays();
        }
    }

    //this is a function to set image
    private void setImage() {
        icon = R.drawable.tiger;
        tappedImageView.setImageResource(icon);
        tappedImageView.setTranslationX(-2000);
        tappedImageView.animate().translationXBy(2000).alpha(1).setDuration(300);
        variables.setNotTapped(tiTag);
        variables.setCurrentPlayer(Variables.Player.TWO);
        falseCount++;
        yourTurn = false;
        firstPlayerImg.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.rootLayoutColor));
        secondPlayerImg.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.imageBackgroundColor));
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                androidPlays();
            }
        }, 350);
    }

    private void setAndroidImg() {
        icon = R.drawable.android;
        switch (tiTag) {
            case 0:
                reference = imgOne;
                break;
            case 1:
                reference = imgTwo;
                break;
            case 2:
                reference = imgThree;
                break;
            case 3:
                reference = imgFour;
                break;
            case 4:
                reference = imgFive;
                break;
            case 5:
                reference = imgSix;
                break;
            case 6:
                reference = imgSeven;
                break;
            case 7:
                reference = imgEight;
                break;
            case 8:
                reference = imgNine;
                break;
            default: break;
        }
        gridLayout.removeView(reference);
        reference.setImageResource(icon);
        gridLayout.setUseDefaultMargins(true);
        reference.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.imageBackgroundColor));
        reference.setTranslationX(2000);
        reference.animate().translationXBy(-2000).alpha(1).setDuration(300);
        gridLayout.addView(reference, tiTag);
        firstPlayerImg.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.imageBackgroundColor));
        secondPlayerImg.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.rootLayoutColor));
        variables.setNotTapped(tiTag);
        yourTurn = true;
        variables.setCurrentPlayer(Variables.Player.ONE);
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
                    if(variables.getNotTapped(tiTag)) {
                        setAndroidImg();
                        flag = false;
                        message = "android";
                        winner = Variables.Player.TWO;
                        showMessage();
                        break;
                    }
                    flag = false;
                    message = "Tiger";
                    winner = Variables.Player.ONE;
                } else {
                    if(variables.getNotTapped(tiTag)) {
                        setImage();
                        flag = false;
                        message = "Tiger";
                        winner = Variables.Player.ONE;
                        showMessage();
                        break;
                    }
                    flag = false;
                    message = "android";
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
        isDraw = true;
    }

    // reset game function to reset all the variables
    private void resetTheGame() {
        for(int i = 0; i < gridLayout.getChildCount(); i++) {
            ImageView imageView = (ImageView) gridLayout.getChildAt(i);
            imageView.setImageDrawable(null);
            imageView.setAlpha(0.2f);
        }
        variables.playerChoicesInitializer();
        variables.notTappedInitializer();
        flag = true;
        falseCount = 0;
        resetBtnAndroid.setVisibility(View.GONE);
        GameOver = false;
        showed = false;

        if(isDraw) {
            if(variables.getCurrentPlayer() == Variables.Player.ONE){
                variables.setCurrentPlayer(Variables.Player.ONE);
                secondPlayerImg.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.rootLayoutColor));
                firstPlayerImg.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.imageBackgroundColor));
                yourTurn = true;
                isDraw = false;
                drawCount++;
                countDraw.setText(drawCount + "");
            }
            else{
                variables.setCurrentPlayer(Variables.Player.TWO);
                firstPlayerImg.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.rootLayoutColor));
                secondPlayerImg.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.imageBackgroundColor));
                isDraw = false;
                drawCount++;
                countDraw.setText(drawCount + "");
                yourTurn = true;
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        androidPlays();
                    }
                }, 200);
            }
        } else {
            if(winner == Variables.Player.TWO) {
                variables.setCurrentPlayer(Variables.Player.TWO);
                firstPlayerImg.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.rootLayoutColor));
                secondPlayerImg.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.imageBackgroundColor));
                Handler handler = new Handler();
                playerTwoWinCount++;
                scorePlayerTwo.setText(playerTwoWinCount + "");
                yourTurn = true;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        androidPlays();
                    }
                }, 200);
            } else {
                variables.setCurrentPlayer(Variables.Player.ONE);
                secondPlayerImg.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.rootLayoutColor));
                firstPlayerImg.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.imageBackgroundColor));
                yourTurn = true;
                playerOneWinCount++;
                scorePlayerOne.setText(playerOneWinCount + "");
            }
        }
    }
}