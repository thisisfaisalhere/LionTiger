package com.virusX.lionOrTiger;

import java.util.Random;

class AIPlays {

    private Board board;

    AIPlays(Board board) {
        this.board = board;
    }

    int getLocation(int strength, int startedWith, int turnNo) {
        if(strength == 1) {
            Random random = new Random();
            return random.nextInt(9) + 1;
        } else if(strength == 2) {
            ModerateGamePlay moderateGamePlay = new ModerateGamePlay(board, startedWith, turnNo);
            return moderateGamePlay.gamePlay();
        }
        return -1;
    }
}
