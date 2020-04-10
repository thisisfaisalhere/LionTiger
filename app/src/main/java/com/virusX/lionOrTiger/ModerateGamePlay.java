package com.virusX.lionOrTiger;

import java.util.Random;

class ModerateGamePlay {
    private Board board;
    private int startedWith, turnNo;

    ModerateGamePlay(Board board, int startedWith, int turnNo) {
        this.board = board;
        this.startedWith = startedWith;
        this.turnNo = turnNo;
    }

    int gamePlay() {
        int finalReturnValue;
        if (startedWith == 1) {
            if (turnNo == 1) {
                finalReturnValue = forTurnNoOne();
            } else {
                finalReturnValue = forTurnNoTwo();
            }
        } else {
            if (turnNo == 0) {
                finalReturnValue = forTurnNoOne();
            } else if (turnNo == 1) {
                finalReturnValue = forTurnNoOne();
            } else {
                finalReturnValue = forTurnNoTwo();
            }
        }
        return finalReturnValue + 1;
    }

    private int forTurnNoOne() {
        int returnValue = -1;
        if (board.getNotTapped(4)) {
            returnValue = 4;
        } else {
            switch (getRandomNo()) {
                case 0:
                    returnValue = 0;
                    break;
                case 1:
                    returnValue = 2;
                    break;
                case 2:
                    returnValue = 6;
                    break;
                case 3:
                    returnValue = 8;
                    break;
                default:
                    break;
            }
        }
        return returnValue;
    }

    private int forTurnNoTwo() {
        int returnValue = -1;
        Board.Player[] playerChoices = board.getPlayerChoices();
        for (int i = 2; i < 9; i = i + 2) {
            if (playerChoices[i] == Board.Player.ONE && playerChoices[0] == Board.Player.ONE) {
                if (i != 4) {
                    if (board.getNotTapped(i / 2)) {
                        return i / 2;
                    } else return throwCheckedRandom();
                } else {
                    if (board.getNotTapped(8)) {
                        return 8;
                    } else return throwCheckedRandom();
                }
            }
        }
        for (int i = 4; i < playerChoices.length; i = i + 2) {
            if (playerChoices[i] == Board.Player.ONE && playerChoices[2] == Board.Player.ONE) {
                if (i == 4) {
                    if (board.getNotTapped(6)) {
                        return 6;
                    } else return throwCheckedRandom();
                } else if (i == 6) {
                    if (board.getNotTapped(4)) {
                        return 4;
                    } else return throwCheckedRandom();
                } else {
                    if (board.getNotTapped(5)) {
                        return 5;
                    } else return throwCheckedRandom();
                }
            }
        }

        int j, k, m;
        k = 0;
        for (int i = 0; i < playerChoices.length - 1; i++) {
            j = i;
            while (k < 2) {
                if (++j < playerChoices.length - 1) {
                    if (playerChoices[i] == Board.Player.ONE && playerChoices[j] == Board.Player.ONE) {
                        if ((float) i % 3 == 0) {
                            if (board.getNotTapped(++j)) return j;
                        } else {
                            m = i;
                            if (board.getNotTapped(--m)) return m;
                        }
                    }
                }
                k++;
            }
            k = 0;
        }
        if (playerChoices[4] == Board.Player.ONE && playerChoices[6] == Board.Player.ONE) {
            if (board.getNotTapped(2)) returnValue = 2;
        } else if (playerChoices[4] == Board.Player.ONE && playerChoices[8] == Board.Player.ONE) {
            if (board.getNotTapped(0)) returnValue = 0;
        } else if (playerChoices[6] == Board.Player.ONE && playerChoices[8] == Board.Player.ONE) {
            if (board.getNotTapped(7)) returnValue = 7;
        } else if (playerChoices[2] == Board.Player.ONE && playerChoices[5] == Board.Player.ONE) {
            if (board.getNotTapped(8)) returnValue = 8;
        } else if (playerChoices[0] == Board.Player.ONE && playerChoices[3] == Board.Player.ONE) {
            if (board.getNotTapped(6)) returnValue = 6;
        } else if (playerChoices[0] == Board.Player.ONE && playerChoices[6] == Board.Player.ONE) {
            if (board.getNotTapped(3)) returnValue = 3;
        } else if (playerChoices[5] == Board.Player.ONE && playerChoices[8] == Board.Player.ONE) {
            if (board.getNotTapped(2)) returnValue = 2;
        } else if (playerChoices[3] == Board.Player.ONE && playerChoices[6] == Board.Player.ONE) {
            if (board.getNotTapped(0)) returnValue = 0;
        }

        if (playerChoices[0] == Board.Player.ONE && playerChoices[7] == Board.Player.ONE) {
            int i = getRandomNo();
            switch (i) {
                case 0:
                    if (board.getNotTapped(2)) {
                        returnValue = 2;
                        break;
                    }
                case 1:
                    if (board.getNotTapped(5)) {
                        returnValue = 5;
                        break;
                    }
                case 2:
                    if (board.getNotTapped(3)){
                        returnValue = 3;
                        break;
                    }
                case 3:
                    if (board.getNotTapped(6)) {
                        returnValue = 6;
                        break;
                    }
            }
        } else if (playerChoices[0] == Board.Player.ONE && playerChoices[5] == Board.Player.ONE) {
            int i = getRandomNo();
            switch (i) {
                case 0:
                    if (board.getNotTapped(2)) {
                        returnValue = 2;
                        break;
                    }
                case 1:
                    if (board.getNotTapped(1)) {
                        returnValue = 1;
                        break;
                    }
                case 2:
                    if (board.getNotTapped(7)) {
                        returnValue = 7;
                        break;
                    }
                case 3:
                    if (board.getNotTapped(6)) {
                        returnValue = 6;
                        break;
                    }
            }
        } else if (playerChoices[0] == Board.Player.ONE && playerChoices[8] == Board.Player.ONE) {
            returnValue = throwCheckedRandom();
        } else if (playerChoices[2] == Board.Player.ONE && playerChoices[7] == Board.Player.ONE) {
            int i = getRandomNo();
            switch (i) {
                case 0:
                    if (board.getNotTapped(0)) {
                        returnValue = 0;
                        break;
                    }
                case 1:
                    if (board.getNotTapped(3)) {
                        returnValue = 3;
                        break;
                    }
                case 2:
                    if (board.getNotTapped(8)) {
                        returnValue = 8;
                        break;
                    }
                case 3:
                    if (board.getNotTapped(5)) {
                        returnValue = 5;
                        break;
                    }
            }
        } else if (playerChoices[2] == Board.Player.ONE && playerChoices[3] == Board.Player.ONE) {
            int i = getRandomNo();
            switch (i) {
                case 0:
                    if (board.getNotTapped(0)) {
                        returnValue = 0;
                        break;
                    }
                case 1:
                    if (board.getNotTapped(1)) {
                        returnValue = 1;
                        break;
                    }
                case 2:
                    if (board.getNotTapped(7)) {
                        returnValue = 7;
                        break;
                    }
                case 3:
                    if (board.getNotTapped(8)) {
                        returnValue = 8;
                        break;
                    }
            }
        } else if (playerChoices[2] == Board.Player.ONE && playerChoices[6] == Board.Player.ONE) {
            returnValue = throwCheckedRandom();
        } else if (playerChoices[6] == Board.Player.ONE && playerChoices[5] == Board.Player.ONE) {
            int i = getRandomNo();
            switch (i) {
                case 0:
                    if (board.getNotTapped(0)) {
                        returnValue = 0;
                        break;
                    }
                case 1:
                    if (board.getNotTapped(1)) {
                        returnValue = 1;
                        break;
                    }
                case 2:
                    if (board.getNotTapped(7)) {
                        returnValue = 7;
                        break;
                    }
                case 3:
                    if (board.getNotTapped(8)) {
                        returnValue = 8;
                        break;
                    }
            }
        } else if (playerChoices[1] == Board.Player.ONE && playerChoices[6] == Board.Player.ONE) {
            int i = getRandomNo();
            switch (i) {
                case 0:
                    if (board.getNotTapped(0)) {
                        returnValue = 0;
                        break;
                    }
                case 1:
                    if (board.getNotTapped(3)) {
                        returnValue = 3;
                        break;
                    }
                case 2:
                    if (board.getNotTapped(5)) {
                        returnValue = 5;
                        break;
                    }
                case 3:
                    if (board.getNotTapped(8)) {
                        returnValue = 8;
                        break;
                    }
            }
        } else if (playerChoices[1] == Board.Player.ONE && playerChoices[8] == Board.Player.ONE) {
            int i = getRandomNo();
            switch (i) {
                case 0:
                    if (board.getNotTapped(2)) {
                        returnValue = 2;
                        break;
                    }
                case 1:
                    if (board.getNotTapped(5)) {
                        returnValue = 5;
                        break;
                    }
                case 2:
                    if (board.getNotTapped(7)) {
                        returnValue = 5;
                        break;
                    }
                case 3:
                    if (board.getNotTapped(6)) {
                        returnValue = 6;
                        break;
                    }
            }
        } else if (playerChoices[3] == Board.Player.ONE && playerChoices[8] == Board.Player.ONE) {
            int i = getRandomNo();
            switch (i) {
                case 0:
                    if (board.getNotTapped(2)) {
                        returnValue = 2;
                        break;
                    }
                case 1:
                    if (board.getNotTapped(1)) {
                        returnValue = 1;
                        break;
                    }
                case 2:
                    if (board.getNotTapped(7)) {
                        returnValue = 7;
                        break;
                    }
                case 3:
                    if (board.getNotTapped(6)) {
                        returnValue = 6;
                        break;
                    }
            }
        }
        if(returnValue == -1 ) returnValue = throwCheckedRandom();
        return returnValue;
    }

    private int throwCheckedRandom() {
        int returnValue = 0;
        Random random = new Random();
        int i = random.nextInt(9);
        if(board.getNotTapped(i))
            returnValue = i;
        else
            throwCheckedRandom();
        return returnValue;
    }

    private int getRandomNo() {
        Random random = new Random();
        return random.nextInt(3);
    }
}