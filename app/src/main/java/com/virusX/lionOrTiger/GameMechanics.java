package com.virusX.lionOrTiger;

import android.annotation.SuppressLint;
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
    private boolean isGameOver, isOnePlayerGame;
    private int tag, firstPlayerImg, secondPlayerImg, playerOneScore, playerTwoScore, strength;
    private Board.Player winnerPlayer;

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
        if (!isGameOver) {
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
                isGameOver = true;
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
            if(isOnePlayerGame) {
                AIPlays aiPlays = new AIPlays();
                if(board.getNotTapped(aiPlays.getLocation(strength))) {
                    setImage(tappedImg); //todo fix this
                }
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

    @SuppressLint("SetTextI18n")
    void resetTheGame() {
        for(int i = 0; i < binding.grid.getChildCount(); i++) {
            ImageView imageView = (ImageView) binding.grid.getChildAt(i);
            imageView.setImageDrawable(null);
            imageView.setAlpha(0.2f);
        }
        board.playerChoicesInitializer();
        board.notTappedInitializer();
        isGameOver = false;
        if(winnerPlayer == Board.Player.TWO) {
            board.setCurrentPlayer(Board.Player.TWO);
            binding.firstPlayerImg.setBackgroundColor(ContextCompat.getColor(context, R.color.rootLayoutColor));
            binding.secondPlayerImg.setBackgroundColor(ContextCompat.getColor(context, R.color.imageBackgroundColor));
            playerTwoScore++;
        } else {
            board.setCurrentPlayer(Board.Player.ONE);
            binding.secondPlayerImg.setBackgroundColor(ContextCompat.getColor(context, R.color.rootLayoutColor));
            binding.firstPlayerImg.setBackgroundColor(ContextCompat.getColor(context, R.color.imageBackgroundColor));
            playerOneScore++;
        }
        binding.scorePlayerOne.setText(Integer.toString(playerOneScore));
        binding.scorePlayerTwo.setText(Integer.toString(playerTwoScore));
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
