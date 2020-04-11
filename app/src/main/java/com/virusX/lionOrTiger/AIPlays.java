package com.virusX.lionOrTiger;

import java.util.Random;

class AIPlays {

    private Board board;

    AIPlays(Board board) {
        this.board = board;
    }

    int getLocation(int strength) {
        Board.Player[] playerChoices = board.getPlayerChoices();
        Board.Player[][] b = new Board.Player[3][3];
        for (int i = 0; i < 3; i++) {
            int j = 0;
            while (j < 3) {
                b[i][j] = playerChoices[((i * 3) + j)];
                j++;
            }
        }
        if(strength == 1) {
            Random random = new Random();
            return random.nextInt(9) + 1;
        } else if(strength == 2) {
            ModerateGamePlay moderateGamePlay = new ModerateGamePlay(board, b);
            ModerateGamePlay.Move move = moderateGamePlay.findBestMove();
            int location = (move.row * 3) + move.col;
            return location + 1;
        } else if(strength == 3) {
            ExpertGamePlay expertGamePlay = new ExpertGamePlay(board, b);
            ExpertGamePlay.Move move = expertGamePlay.findBestMove();
            int location = (move.row * 3) + move.col;
            return location + 1;
        }
        return -1;
    }
}
