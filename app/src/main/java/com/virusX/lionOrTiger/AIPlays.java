package com.virusX.lionOrTiger;

import java.util.Random;

class AIPlays {

    private Board board;

    AIPlays(Board board) {
        this.board = board;
    }

    int getLocation(int strength) {
        Board.Player[][] b = board.boardState();
        if(strength == 1) {
            Random random = new Random();
            return random.nextInt(9) + 1;
        } else if(strength == 2) {
            ModerateGamePlay moderateGamePlay = new ModerateGamePlay(board, b);
            ModerateGamePlay.Move move = moderateGamePlay.findBestMove();
            return ((move.row * 3) + move.col) + 1;
        } else if(strength == 3) {
            ExpertGamePlay expertGamePlay = new ExpertGamePlay(board, b);
            ExpertGamePlay.Move move = expertGamePlay.findBestMove();
            return ((move.row * 3) + move.col) + 1;
        }
        return -1;
    }
}
