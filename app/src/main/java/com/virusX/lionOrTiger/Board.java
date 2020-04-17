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

    private Player[] getPlayerChoices() {
        return playerChoices;
    }

    Player checkWinner() {
        Player winnerPlayer = null;
        for(int[] winCase : winCases) {
            if(playerChoices[winCase[0]] == playerChoices[winCase[1]]
                    && playerChoices[winCase[1]] == playerChoices[winCase[2]]
                    && playerChoices[winCase[0]] != Board.Player.INPUT) {
                if(getCurrentPlayer() == Board.Player.ONE) {
                    winnerPlayer = Board.Player.TWO;
                } else {
                    winnerPlayer = Board.Player.ONE;
                }
                isGameOver = true;
                break;
            }
        }
        return winnerPlayer;
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