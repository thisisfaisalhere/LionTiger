package com.virusx.lionortiger;

import java.util.Random;

class GetGridLocation {

    private boolean[] notTapped;
    Variables.Player[] playerChoices;
    private boolean blocked = false;
    private Variables variables = new Variables();

    int getLocation(int turnNo, boolean[] notTapped, Variables.Player[] playerChoices, int startedWith) {
        this.notTapped = notTapped;
        this.playerChoices = playerChoices;
        int finalReturnValue = 0;
        if (startedWith == 1) {
            if(turnNo == 1) {
                finalReturnValue = forTurnNoOne();
            } else if(turnNo == 2) {
                finalReturnValue = forTurnNoTwo();
            } else if(turnNo == 3) {
                finalReturnValue = forTurnNoThree();
            } else if(turnNo == 4){
                finalReturnValue = forTurnNoFour();
            } else {
                finalReturnValue = throwCheckedRandom();
            }
        } else {
            if(turnNo == 0) {
                finalReturnValue = forTurnNoOne();
            } else if(turnNo == 1) {
                finalReturnValue = forTurnNoOne();
            } else if(turnNo == 2) {
                finalReturnValue = forTurnNoTwo();
            } else if(turnNo == 3) {
                finalReturnValue = forTurnNoThree();
            } else if(turnNo == 4){
                finalReturnValue = forTurnNoFour();
            } else {
                finalReturnValue = findLastGrid();
            }
        }
        return finalReturnValue;
    }

    private int forTurnNoOne() {
        int returnValue = 0;
        if(notTapped[4]) {
            returnValue = 4;
        } else {
            switch (getRandomNo(1)) {
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
        return check();
    }

    private int forTurnNoThree() {
        return check();
    }

    private int forTurnNoFour() {
        return check();
    }

    private int check() {
        int returnValue = 0;
        for(int i = 2; i < playerChoices.length; i=i+2) {
            if (playerChoices[i] == Variables.Player.ONE && playerChoices[0] == Variables.Player.ONE) {
                if(i != 4) {
                    if (notTapped[i/2]) {
                        return i/2;
                    } else throwCheckedRandom();
                } else {
                    if(notTapped[8]) {
                        return 8;
                    } else throwCheckedRandom();
                }
            }
        }
        for(int i = 4; i < playerChoices.length; i=i+2) {
            if (playerChoices[i] == Variables.Player.ONE && playerChoices[2] == Variables.Player.ONE) {
                if(i == 4) {
                    if(notTapped[6]) {
                        return 6;
                    } else return throwCheckedRandom();
                } else if(i == 6) {
                    if(notTapped[4]) {
                        return 4;
                    } else return throwCheckedRandom();
                } else {
                    if(notTapped[5]) {
                        return 5;
                    } else return throwCheckedRandom();
                }
            }
        }

        int j,k,m;
        k = 0;
        for(int i = 0; i < playerChoices.length - 1; i++) {
            j = i;
            while (k < 2) {
                if (++j < playerChoices.length - 1) {
                    if (playerChoices[i] == Variables.Player.ONE && playerChoices[j] == Variables.Player.ONE) {
                        if ((float)i%3 == 0) {
                            if(notTapped[++j]) return j;
                            else return throwCheckedRandom();
                        } else {
                            m=i;
                            if(notTapped[--m]) return m;
                            else return throwCheckedRandom();
                        }
                    }
                }
                k++;
            } k = 0;
        }
        if( playerChoices[4] == Variables.Player.ONE && playerChoices[6] == Variables.Player.ONE) {
            if(notTapped[2]) returnValue = 2;
            else returnValue = throwCheckedRandom();
        } else if ( playerChoices[4] == Variables.Player.ONE && playerChoices[8] == Variables.Player.ONE ) {
            if(notTapped[0]) returnValue = 0;
            else returnValue = throwCheckedRandom();
        } else if ( playerChoices[6] == Variables.Player.ONE && playerChoices[8] == Variables.Player.ONE ) {
            if(notTapped[7]) returnValue = 7;
            else returnValue = throwCheckedRandom();
        } else if( playerChoices[2] == Variables.Player.ONE && playerChoices[5] == Variables.Player.ONE ) {
            if(notTapped[8]) returnValue = 8;
            else returnValue = throwCheckedRandom();
        } else if( playerChoices[0] == Variables.Player.ONE && playerChoices[3] == Variables.Player.ONE ) {
            if(notTapped[6]) returnValue = 6;
            else returnValue = throwCheckedRandom();
        } else if( playerChoices[0] == Variables.Player.ONE && playerChoices[6] == Variables.Player.ONE ) {
            if(notTapped[3]) returnValue = 3;
            else returnValue = throwCheckedRandom();
        } else if( playerChoices[5] == Variables.Player.ONE && playerChoices[8] == Variables.Player.ONE ) {
            if(notTapped[2]) returnValue = 2;
            else returnValue = throwCheckedRandom();
        } else if( playerChoices[3] == Variables.Player.ONE && playerChoices[6] == Variables.Player.ONE ) {
            if (notTapped[0]) returnValue = 0;
            else returnValue = throwCheckedRandom();
        }

        if(playerChoices[0] == Variables.Player.ONE && playerChoices[7] == Variables.Player.ONE) {
            int i = getRandomNo(1);
            switch (i) {
                case 0:
                    if(notTapped[2]) {
                        returnValue = 2;
                        break;
                    } else return check();
                case 1:
                    if(notTapped[5]) {
                        returnValue = 5;
                        break;
                    } else return check();
                case 2:
                    if(notTapped[3]) {
                        returnValue = 3;
                        break;
                    } else return check();
                case 3:
                    if(notTapped[6]) {
                        returnValue = 6;
                        break;
                    } else return check();
            }
        } else if(playerChoices[0] == Variables.Player.ONE && playerChoices[5] == Variables.Player.ONE) {
            int i = getRandomNo(1);
            switch (i) {
                case 0:
                    if(notTapped[2]) {
                        returnValue = 2;
                        break;
                    } else return check();
                case 1:
                    if(notTapped[1]) {
                        returnValue = 1;
                        break;
                    } else return check();
                case 2:
                    if(notTapped[7]) {
                        returnValue = 7;
                        break;
                    } else return check();
                case 3:
                    if(notTapped[6]) {
                        returnValue = 6;
                        break;
                    } else return check();
            }
        } else if(playerChoices[0] == Variables.Player.ONE && playerChoices[8] == Variables.Player.ONE) {
            returnValue = throwCheckedRandom();
        } else if(playerChoices[2] == Variables.Player.ONE && playerChoices[7] == Variables.Player.ONE) {
            int i = getRandomNo(1);
            switch (i) {
                case 0:
                    if(notTapped[0]) {
                        returnValue = 0;
                        break;
                    } else return check();
                case 1:
                    if(notTapped[3]) {
                        returnValue = 3;
                        break;
                    } else return check();
                case 2:
                    if(notTapped[8]) {
                        returnValue = 8;
                        break;
                    } else return check();
                case 3:
                    if(notTapped[5]) {
                        returnValue = 5;
                        break;
                    } else return check();
            }
        } else if(playerChoices[2] == Variables.Player.ONE && playerChoices[3] == Variables.Player.ONE) {
            int i = getRandomNo(1);
            switch (i) {
                case 0:
                    if(notTapped[0]) {
                        returnValue = 0;
                        break;
                    } else return check();
                case 1:
                    if(notTapped[1]) {
                        returnValue = 1;
                        break;
                    } else return check();
                case 2:
                    if(notTapped[7]) {
                        returnValue = 7;
                        break;
                    } else return check();
                case 3:
                    if(notTapped[8]) {
                        returnValue = 8;
                        break;
                    } else return check();
            }
        } else if(playerChoices[2] == Variables.Player.ONE && playerChoices[6] == Variables.Player.ONE) {
            returnValue = throwCheckedRandom();
        } else if(playerChoices[6] == Variables.Player.ONE && playerChoices[5] == Variables.Player.ONE) {
            int i = getRandomNo(1);
            switch (i) {
                case 0:
                    if(notTapped[0]) {
                        returnValue = 0;
                        break;
                    } else return check();
                case 1:
                    if(notTapped[1]) {
                        returnValue = 1;
                        break;
                    } else return check();
                case 2:
                    if(notTapped[7]) {
                        returnValue = 7;
                        break;
                    } else return check();
                case 3:
                    if(notTapped[8]) {
                        returnValue = 8;
                        break;
                    } else return check();
            }
        } else if(playerChoices[1] == Variables.Player.ONE && playerChoices[6] == Variables.Player.ONE) {
            int i = getRandomNo(1);
            switch (i) {
                case 0:
                    if(notTapped[0]){
                        returnValue = 0;
                        break;
                    } else return check();
                case 1:
                    if(notTapped[3]) {
                        returnValue = 3;
                        break;
                    } else return check();
                case 2:
                    if(notTapped[5]) {
                        returnValue = 5;
                        break;
                    } else return check();
                case 3:
                    if(notTapped[8]) {
                        returnValue = 8;
                        break;
                    } else return check();
            }
        } else if(playerChoices[1] == Variables.Player.ONE && playerChoices[8] == Variables.Player.ONE) {
            int i = getRandomNo(1);
            switch (i) {
                case 0:
                    if(notTapped[2]) {
                        returnValue = 2;
                        break;
                    } else return check();
                case 1:
                    if(notTapped[5]) {
                        returnValue = 5;
                        break;
                    } else return check();
                case 2:
                    if(notTapped[7]) {
                        returnValue = 5;
                        break;
                    } else return check();
                case 3:
                    if(notTapped[6]) {
                        returnValue = 6;
                        break;
                    } else return check();
            }
        } else if(playerChoices[3] == Variables.Player.ONE && playerChoices[8] == Variables.Player.ONE) {
            int i = getRandomNo(1);
            switch (i) {
                case 0:
                    if(notTapped[2]) {
                        returnValue = 2;
                        break;
                    } else return check();
                case 1:
                    if(notTapped[1]) {
                        returnValue = 1;
                        break;
                    } else return check();
                case 2:
                    if(notTapped[7]) {
                        returnValue = 7;
                        break;
                    } else return check();
                case 3:
                    if(notTapped[6]) {
                        returnValue = 6;
                        break;
                    } else return check();
            }
        }

        return returnValue;
    }

    private int findLastGrid() {
        int returnValue = 0;
        for(int i = 0; i < notTapped.length; i++) {
            if(notTapped[i])
                returnValue = i;
        }
        return returnValue;
    }

    private int throwCheckedRandom() {
        int returnValue = 0;
        int i = getRandomNo(2);
        if(notTapped[i])
            returnValue = i;
        else
            throwCheckedRandom();
        return returnValue;
    }

    int getRandomNo(int caseNo) {
        Random random = new Random();
        if(caseNo == 1) return random.nextInt(3);
        else return random.nextInt(9);
    }
}
