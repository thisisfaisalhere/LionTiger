package com.virusX.lionOrTiger;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.virusX.lionOrTiger.databinding.ActivityMainBinding;

import es.dmoral.toasty.Toasty;
import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

class GameMechanics {

    private ActivityMainBinding binding;
    private Context context;
    private Board board;
    private boolean isOnePlayerGame, yourTurn = true;
    private int tag, firstPlayerImg, secondPlayerImg, playerOneScore, playerTwoScore, strength;
    private Board.Player winnerPlayer;
    private final String TAG = "LionTiger";

    GameMechanics(ActivityMainBinding binding, Context context) {
        this.binding = binding;
        this.context = context;
        board = new Board();
        board.setCurrentPlayer(Board.Player.ONE);
        board.notTappedInitializer();
        board.playerChoicesInitializer();
    }

    void run(ImageView tappedImgView, boolean isOnePlayerGame, int strength) {
        this.isOnePlayerGame = isOnePlayerGame;
        this.strength = strength;
        if(!isOnePlayerGame) yourTurn = true;
        if (!board.isGameOver()) {
            if(yourTurn) {
                tag = Integer.parseInt(tappedImgView.getTag().toString());
                if (board.getNotTapped(tag - 1)) {
                    board.setPlayerChoice(board.getCurrentPlayer(), tag - 1);
                    setImage(tappedImgView);
                    Log.d(TAG, tag - 1 + "");
                } else Toasty.info(context, "Choose another Grid", Toasty.LENGTH_SHORT, true).show();
            } else Toasty.warning(context, "Wait for your Turn", Toasty.LENGTH_SHORT, true).show();
        } else Toasty.warning(context, "Game Over. Reset to Play Again", Toasty.LENGTH_SHORT, true).show();
    }



    private void setImage(ImageView tappedImg) {
        board.setNotTapped(tag - 1);
        int translationValue = 0, translationXByValue = 0, img = 0;
        if(board.getCurrentPlayer() == Board.Player.ONE) {
            img = firstPlayerImg;
            board.setCurrentPlayer(Board.Player.TWO);
            translationValue = -2000;
            translationXByValue = 2000;
            binding.firstPlayerImg.setBackgroundColor(ContextCompat
                    .getColor(context, R.color.rootLayoutColor));
            binding.secondPlayerImg.setBackgroundColor(ContextCompat
                    .getColor(context, R.color.imageBackgroundColor));
            Log.d(TAG, "setImage: checking winner in player one");
            checkWinner();
            if(isOnePlayerGame) {
                yourTurn = false;
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        androidPlays();
                    }
                }, 350);
            }
        } else if(board.getCurrentPlayer() == Board.Player.TWO) {
            img = secondPlayerImg;
            board.setCurrentPlayer(Board.Player.ONE);
            translationValue = 2000;
            translationXByValue = -2000;
            binding.secondPlayerImg.setBackgroundColor(ContextCompat
                    .getColor(context, R.color.rootLayoutColor));
            binding.firstPlayerImg.setBackgroundColor(ContextCompat
                    .getColor(context, R.color.imageBackgroundColor));
            Log.d(TAG, "setImage: checking winner in player two");
            checkWinner();
        }
        tappedImg.setImageResource(img);
        tappedImg.setTranslationX(translationValue);
        tappedImg.animate().translationXBy(translationXByValue).alpha(1).setDuration(500);
        if(!board.isSpaceAvailable())
            drawMessage();
    }

    private void checkWinner() {
        winnerPlayer = board.checkWinner();
        int winner;
        String message;
        if(winnerPlayer == Board.Player.ONE) {
            Log.d(TAG, "checkWinner: player one winner");
            winner = secondPlayerImg;
            message = "Lion is our Winner";
            if(isOnePlayerGame) message = "android is our Winner";
        } else {
            Log.d(TAG, "checkWinner: player one winner");
            winner = firstPlayerImg;
            message = "Tiger is our Winner";
        }
        showMessage(winner, message);
    }

    private void androidPlays() {
        if (!board.isGameOver()) {
            if(board.isSpaceAvailable()) {
                AIPlays aiPlays = new AIPlays(board);
                int location = aiPlays.getLocation(strength);
                if (board.getNotTapped(location - 1)) {
                    Log.d(TAG, location - 1 + "");
                    board.setPlayerChoice(board.getCurrentPlayer(), location - 1);
                    setAndroidImage(location - 1);
                } else androidPlays();
            } else board.setGameOver(true);
        }
    }

    private void setAndroidImage(int location) {
        board.setCurrentPlayer(Board.Player.ONE);
        ImageView imageView;
        switch (location) {
            case 0:
                imageView = binding.img1;
                break;
            case 1:
                imageView = binding.img2;
                break;
            case 2:
                imageView = binding.img3;
                break;
            case 3:
                imageView = binding.img4;
                break;
            case 4:
                imageView = binding.img5;
                break;
            case 5:
                imageView = binding.img6;
                break;
            case 6:
                imageView = binding.img7;
                break;
            case 7:
                imageView = binding.img8;
                break;
            case 8:
                imageView = binding.img9;
                break;
            default:
                imageView = null;
        }
        Log.d(TAG, "SetAndroidImg: checking winner ...");
        checkWinner();
        board.setNotTapped(location);
        binding.grid.removeView(imageView);
        assert imageView != null;
        imageView.setImageResource(secondPlayerImg);
        binding.grid.setUseDefaultMargins(true);
        imageView.setBackgroundColor(ContextCompat.getColor(context, R.color.imageBackgroundColor));
        imageView.setTranslationX(2000);
        imageView.animate().translationXBy(-2000).alpha(1).setDuration(300);
        binding.grid.addView(imageView, location);
        binding.firstPlayerImg.setBackgroundColor(ContextCompat.getColor(context, R.color.imageBackgroundColor));
        binding.secondPlayerImg.setBackgroundColor(ContextCompat.getColor(context, R.color.rootLayoutColor));
        yourTurn = true;
        if(!board.isSpaceAvailable())
            drawMessage();
    }

    private void showMessage(int winner, String message) {
        final PrettyDialog prettyDialog = new PrettyDialog(context);
        prettyDialog.setIcon(winner).setTitle(message)
                .addButton("Reset Game",
                        R.color.pdlg_color_white,
                        R.color.pdlg_color_green,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                resetTheGame();
                                prettyDialog.dismiss();
                            }
                        }
                ).show();
    }

    private void drawMessage() {
        final PrettyDialog prettyDialog = new PrettyDialog(context);
        prettyDialog.setIcon(R.drawable.ic_warning).setTitle("It's a Draw. Rest to Play Again")
                .addButton("Reset Game",
                        R.color.pdlg_color_white,
                        R.color.pdlg_color_green,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                resetTheGame();
                                prettyDialog.dismiss();
                            }
                        }
                ).show();
    }

    @SuppressLint("SetTextI18n")
    void resetTheGame() {
        for(int i = 0; i < binding.grid.getChildCount(); i++) {
            ImageView imageView = (ImageView) binding.grid.getChildAt(i);
            imageView.setImageDrawable(null);
            imageView.setAlpha(0.2f);
        }
        board.playerChoicesInitializer();
        board.notTappedInitializer();

        if(board.isGameOver()) {
            board.setGameOver(false);
            if(winnerPlayer == Board.Player.TWO) {
                board.setCurrentPlayer(Board.Player.TWO);
                binding.firstPlayerImg.setBackgroundColor(ContextCompat.getColor(context, R.color.rootLayoutColor));
                binding.secondPlayerImg.setBackgroundColor(ContextCompat.getColor(context, R.color.imageBackgroundColor));
                playerTwoScore++;
                if(isOnePlayerGame) {
                    androidPlays();
                }
            } else {
                board.setCurrentPlayer(Board.Player.ONE);
                binding.secondPlayerImg.setBackgroundColor(ContextCompat.getColor(context, R.color.rootLayoutColor));
                binding.firstPlayerImg.setBackgroundColor(ContextCompat.getColor(context, R.color.imageBackgroundColor));
                playerOneScore++;
            }
            binding.scorePlayerOne.setText(Integer.toString(playerOneScore));
            binding.scorePlayerTwo.setText(Integer.toString(playerTwoScore));
        } else {
            if(board.getCurrentPlayer() == Board.Player.ONE)
                board.setCurrentPlayer(Board.Player.TWO);
            else
                board.setCurrentPlayer(Board.Player.ONE);
            binding.secondPlayerImg.setBackgroundColor(ContextCompat.getColor(context, R.color.rootLayoutColor));
            binding.firstPlayerImg.setBackgroundColor(ContextCompat.getColor(context, R.color.imageBackgroundColor));
        }

        if(!yourTurn) {
            Log.d(TAG, "resetTheGame: " + board.getCurrentPlayer());
            yourTurn = true;
            if(board.getCurrentPlayer() == Board.Player.ONE && isOnePlayerGame) {
                Log.d(TAG, "resetTheGame: ok");
                androidPlays();
            }
        }
    }

    int getPlayerOneScore() {
        return playerOneScore;
    }

    int getPlayerTwoScore() {
        return playerTwoScore;
    }

    void setPlayerOneScore(int playerOneScore) {
        this.playerOneScore = playerOneScore;
    }

    void setPlayerTwoScore(int playerTwoScore) {
        this.playerTwoScore = playerTwoScore;
    }

    void setPlayerImg(int secondPlayerImg) {
        this.firstPlayerImg = R.drawable.tiger;
        this.secondPlayerImg = secondPlayerImg;
        binding.firstPlayerImg.setBackgroundColor(ContextCompat
                .getColor(context, R.color.imageBackgroundColor));
        binding.secondPlayerImg.setBackgroundColor(ContextCompat
                .getColor(context, R.color.rootLayoutColor));
    }
}
