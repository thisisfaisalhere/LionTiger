package com.virusx.lionortiger;

class Variables {

    enum Player {
        ONE, TWO, INPUT
    }
    private Player currentPlayer;
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

    void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    void setPlayerChoice(Player currentPlayer, int position) {
        playerChoices[position] = currentPlayer;
    }

    void setNotTapped(int position) {
        notTapped[position] = false;
    }

    int notTappedLength() {
        return notTapped.length;
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
        for(int i = 0; i<playerChoices.length; i++) {
            playerChoices[i] = Player.INPUT;
        }
    }

    void notTappedInitializer() {
        for (int i = 0; i < notTapped.length; i++) {
            notTapped[i] = true;
        }
    }
}
