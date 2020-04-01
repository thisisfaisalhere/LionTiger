package com.virusX.lionOrTiger;

import android.content.Context;
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
    private boolean gameOver;
    private int tag, firstPlayerImg, secondPlayerImg;
    private Board.Player winnerPlayer;

    GameMechanics(ActivityMainBinding binding, Context context) {
        this.binding = binding;
        this.context = context;
        board = new Board();
        board.setCurrentPlayer(Board.Player.ONE);
        board.notTappedInitializer();
        board.playerChoicesInitializer();
        firstPlayerImg = R.drawable.tiger;
        secondPlayerImg = R.drawable.lion;
        binding.firstPlayerImg.setBackgroundColor(ContextCompat
                .getColor(context, R.color.imageBackgroundColor));
        binding.secondPlayerImg.setBackgroundColor(ContextCompat
                .getColor(context, R.color.rootLayoutColor));
    }

    void run(ImageView tappedImgView) {
        if (!gameOver) {
            tag = Integer.parseInt(tappedImgView.getTag().toString());
            if (board.getNotTapped(tag - 1)) {
                board.setPlayerChoice(board.getCurrentPlayer(), tag - 1);
                setImage(tappedImgView);
                checkWinner();
            } else Toasty.info(context, "Choose another Grid", Toasty.LENGTH_SHORT, true).show();
        } else Toasty.warning(context, "Game Over. Reset to Play Again", Toasty.LENGTH_SHORT, true).show();
    }

    private void checkWinner() {
        int[][] winCases = board.getWinCases();
        Board.Player[] choicesByPlayer = board.getPlayerChoices();
        for(int[] checkWinner : winCases) {
            if(choicesByPlayer[checkWinner[0]] == choicesByPlayer[checkWinner[1]]
                    && choicesByPlayer[checkWinner[1]] == choicesByPlayer[checkWinner[2]]

                    && choicesByPlayer[checkWinner[0]] != Board.Player.INPUT) {
                int winner;
                String message;
                if(board.getCurrentPlayer() == Board.Player.ONE) {
                    winner = secondPlayerImg;
                    winnerPlayer = Board.Player.TWO;
                    message = "Lion is our Winner";
                } else {
                    winner = firstPlayerImg;
                    winnerPlayer = Board.Player.ONE;
                    message = "Tiger is our Winner";
                }
                showMessage(winner, message);
                gameOver = true;
                break;
            }
        }
    }

    private void setImage(ImageView tappedImg) {
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
        } else if(board.getCurrentPlayer() == Board.Player.TWO) {
            img = secondPlayerImg;
            board.setCurrentPlayer(Board.Player.ONE);
            translationValue = 2000;
            translationXByValue = -2000;
            binding.secondPlayerImg.setBackgroundColor(ContextCompat
                    .getColor(context, R.color.rootLayoutColor));
            binding.firstPlayerImg.setBackgroundColor(ContextCompat
                    .getColor(context, R.color.imageBackgroundColor));
        }
        tappedImg.setImageResource(img);
        tappedImg.setTranslationX(translationValue);
        tappedImg.animate().translationXBy(translationXByValue).alpha(1).setDuration(500);
        board.setNotTapped(tag - 1);
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

    void resetTheGame() {
        for(int i = 0; i < binding.grid.getChildCount(); i++) {
            ImageView imageView = (ImageView) binding.grid.getChildAt(i);
            imageView.setImageDrawable(null);
            imageView.setAlpha(0.2f);
        }
        board.playerChoicesInitializer();
        board.notTappedInitializer();
        gameOver = false;
        if(winnerPlayer == Board.Player.TWO) {
            board.setCurrentPlayer(Board.Player.TWO);
            binding.firstPlayerImg.setBackgroundColor(ContextCompat.getColor(context, R.color.rootLayoutColor));
            binding.secondPlayerImg.setBackgroundColor(ContextCompat.getColor(context, R.color.imageBackgroundColor));
        } else {
            board.setCurrentPlayer(Board.Player.ONE);
            binding.secondPlayerImg.setBackgroundColor(ContextCompat.getColor(context, R.color.rootLayoutColor));
            binding.firstPlayerImg.setBackgroundColor(ContextCompat.getColor(context, R.color.imageBackgroundColor));
        }
    }
}
