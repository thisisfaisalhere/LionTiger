package com.virusX.lionOrTiger;

import android.content.Context;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.virusX.lionOrTiger.databinding.ActivityMainBinding;

import es.dmoral.toasty.Toasty;

class GameMechanics {

    private ActivityMainBinding binding;
    private Context context;
    private Board board;
    private boolean gameOver;
    private int tag;

    GameMechanics(ActivityMainBinding binding, Context context) {
        this.binding = binding;
        this.context = context;
        board = new Board();
        board.setCurrentPlayer(Board.Player.ONE);
    }

    void run(ImageView tappedImgView) {
        if (!gameOver) {
            tag = Integer.parseInt(tappedImgView.getTag().toString());
            if (board.getNotTapped(tag - 1)) {
                board.setPlayerChoice(board.getCurrentPlayer(), tag - 1);
                setImage(tappedImgView);
            } else Toasty.info(context, "Choose another Grid", Toasty.LENGTH_SHORT, true).show();
        } else Toasty.warning(context, "Game Over. Reset to Play Again", Toasty.LENGTH_SHORT, true).show();
    }

    private void setImage(ImageView tappedImg) {
        int translationValue = 0, translationXByValue = 0, img = 0;
        if(board.getCurrentPlayer() == Board.Player.ONE) {
            img = R.drawable.tiger;
            board.setCurrentPlayer(Board.Player.TWO);
            translationValue = -2000;
            translationXByValue = 2000;
            binding.firstPlayerImg.setBackgroundColor(ContextCompat
                    .getColor(context, R.color.rootLayoutColor));
            binding.secondPlayerImg.setBackgroundColor(ContextCompat
                    .getColor(context, R.color.imageBackgroundColor));
        } else if(board.getCurrentPlayer() == Board.Player.TWO) {
            img = R.drawable.lion;
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

    void resetTheGame() {
        for(int i = 0; i < binding.grid.getChildCount(); i++) {
            ImageView imageView = (ImageView) binding.grid.getChildAt(i);
            imageView.setImageDrawable(null);
            imageView.setAlpha(0.2f);
        }
        board.playerChoicesInitializer();
        board.notTappedInitializer();
        gameOver = false;
    }
}
