package com.virusX.lionOrTiger;

import android.util.Log;

import java.util.Arrays;

class Board {

    enum Player {
        ONE, TWO, INPUT
    }

    private Player currentPlayer;
    private Player[] playerChoices = new Player[9];

    private boolean[] notTapped =
            {true, true, true,
                    true, true, true,
                    true, true, true};
    private boolean isGameOver;

    void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    void setPlayerChoice(Player currentPlayer, int position) {
        playerChoices[position] = currentPlayer;
    }

    void setNotTapped(int position) {
        notTapped[position] = false;
    }

    boolean isGameOver() {
        return isGameOver;
    }

    void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }

    Player getCurrentPlayer() {
        return currentPlayer;
    }

    Player[] getPlayerChoices() {
        return playerChoices;
    }

    boolean getNotTapped(int position) {
        return notTapped[position];
    }

    void playerChoicesInitializer() {
        Arrays.fill(playerChoices, Player.INPUT);
    }

    void notTappedInitializer() {
        Arrays.fill(notTapped, true);
    }

    boolean isSpaceAvailable() {
        Log.i("LionOrTiger", "isSpaceAvailable() called");
        for (boolean b : notTapped) {
            if (b) return true;
        }
        return false;
    }

    Player[][] boardState() {
        Player[] choices = getPlayerChoices();
        Player[][] b = new Player[3][3];
        for (int i = 0; i < 3; i++) {
            int j = 0;
            while (j < 3) {
                b[i][j] = choices[((i * 3) + j)];
                j++;
            }
        }
        return b;
    }
}