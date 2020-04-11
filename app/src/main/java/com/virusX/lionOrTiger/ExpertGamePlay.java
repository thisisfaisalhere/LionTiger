package com.virusX.lionOrTiger;

class ExpertGamePlay {

    private Board.Player player = Board.Player.ONE, AI = Board.Player.TWO;
    private Board board;
    private Board.Player[][] b;

    static class Move
    {
        int row, col;
    }

    ExpertGamePlay(Board board, Board.Player[][] b) {
        this.board = board;
        this.b = b;
    }

    private int evaluate()
    {
        for (int row = 0; row < 3; row++)
        {
            if (b[row][0] == b[row][1] &&
                    b[row][1] == b[row][2])
            {
                if (b[row][0] == player)
                    return +10;
                else if (b[row][0] == AI)
                    return -10;
            }
        }
        for (int col = 0; col < 3; col++)
        {
            if (b[0][col] == b[1][col] &&
                    b[1][col] == b[2][col])
            {
                if (b[0][col] == player)
                    return +10;

                else if (b[0][col] == AI)
                    return -10;
            }
        }
        if (b[0][0] == b[1][1] && b[1][1] == b[2][2])
        {
            if (b[0][0] == player)
                return +10;
            else if (b[0][0] == AI)
                return -10;
        }
        if (b[0][2] == b[1][1] && b[1][1] == b[2][0])
        {
            if (b[0][2] == player)
                return +10;
            else if (b[0][2] == AI)
                return -10;
        }
        return 0;
    }

    private int miniMax(int depth, Boolean isMax)
    {
        int score = evaluate();
        if (score == 10)
            return score - depth;
        if (score == -10)
            return score + depth;
        if (board.isSpaceAvailable())
            return 0;
        if (isMax)
        {
            int best = -1000;
            for (int i = 0; i < 3; i++)
            {
                for (int j = 0; j < 3; j++)
                {
                    if (b[i][j]== Board.Player.INPUT)
                    {
                        b[i][j] = player;
                        best = Math.max(best, miniMax(depth + 1, false));
                        b[i][j] = Board.Player.INPUT;
                    }
                }
            }
            return best;
        }
        else
        {
            int best = 1000;
            for (int i = 0; i < 3; i++)
            {
                for (int j = 0; j < 3; j++)
                {
                    if (b[i][j] == Board.Player.INPUT)
                    {
                        b[i][j] = AI;
                        best = Math.min(best, miniMax(depth + 1, true));
                        b[i][j] = Board.Player.INPUT;
                    }
                }
            }
            return best;
        }
    }

    Move findBestMove()
    {
        int bestVal = -1000;
        Move bestMove = new Move();
        bestMove.row = -1;
        bestMove.col = -1;
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                if (b[i][j] == Board.Player.INPUT)
                {
                    b[i][j] = player;
                    int moveVal = miniMax(0, true);
                    b[i][j] = Board.Player.INPUT;
                    if (moveVal > bestVal)
                    {
                        bestMove.row = i;
                        bestMove.col = j;
                        bestVal = moveVal;
                    }
                }
            }
        }
        return bestMove;
    }
}
