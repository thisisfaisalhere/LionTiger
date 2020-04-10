package com.virusX.lionOrTiger;

import android.util.Log;

import java.util.Arrays;

class Board {

    enum Player {
        ONE, TWO, INPUT
    }
    private Player currentPlayer;
    private Player[] playerChoices = new Player[9];

    private int[][] winCases =
            {{0, 1, 2}, {3, 4, 5}, {6, 7, 8},
                    {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
                    {0, 4, 8}, {2, 4, 6}};

    private boolean[] notTapped =
            {true, true, true,
                    true, true, true,
                    true, true, true};

    void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    void setPlayerChoice(Player currentPlayer, int position) {
        playerChoices[position] = currentPlayer;
    }

    void setNotTapped(int position) {
        notTapped[position] = false;
    }

    Player getCurrentPlayer() {
        return currentPlayer;
    }

    Player[] getPlayerChoices() {
        return playerChoices;
    }

    int[][] getWinCases() {
        return winCases;
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
        Log.i("LionOrTiger" ,"isSpaceAvailable() called");
        for (boolean b : notTapped) {
            if (b) return true;
        }
        return false;
    }
}
