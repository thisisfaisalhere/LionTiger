package com.virusx.lionortiger;

import java.util.Random;

class GetGridLocation {

    private boolean[] notTapped;
    private  Variables.Player[] playerChoices;
    private boolean blocked = false, checkedExtra = false;
    private Variables variables = new Variables();
    private int calledFrom, returnValue = Integer.MAX_VALUE;

    GetGridLocation(int calledFrom) {
        this.calledFrom = calledFrom;
    }

    int getLocation(int turnNo, boolean[] notTapped, Variables.Player[] playerChoices, int startedWith) {
        this.notTapped = notTapped;
        this.playerChoices = playerChoices;
        if (startedWith == 1) {
            if(turnNo == 1) {
                returnValue = forTurnNoOne();
            } else if(turnNo == 2) {
                returnValue = forTurnNoTwo();
            } else if(turnNo == 3 || turnNo == 4) {
                if(calledFrom == 2) {
                    returnValue = forTurnNoThreeAndFourExp();
                }
                returnValue = forTurnNoThreeAndFour();
            } else {
                returnValue = throwCheckedRandom();
            }
        } else {
            if(turnNo == 0) {
                returnValue = forTurnNoOne();
            } else if(turnNo == 1) {
                returnValue = forTurnNoOne();
            } else if(turnNo == 2) {
                returnValue = forTurnNoTwo();
            } else if(turnNo == 3 || turnNo == 4) {
                if(calledFrom == 2) {
                    returnValue = forTurnNoThreeAndFourExp();
                }
                returnValue = forTurnNoThreeAndFour();
            }  else {
                returnValue = findLastGrid();
            }
        }
        return returnValue;
    }

    private int forTurnNoOne() {
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

    private int forTurnNoThreeAndFour() {
        int returnValue = goForWin();
        if (!blocked) returnValue = check();
        return returnValue;
    }

    private int forTurnNoThreeAndFourExp() {
        int returnValue = goForWinExp();
        if (!blocked) returnValue = check();
        return returnValue;
    }

    private int check() {
        for(int i = 2; i < playerChoices.length; i=i+2) {
            if (playerChoices[i] == Variables.Player.ONE && playerChoices[0] == Variables.Player.ONE) {
                if(i != 4) {
                    if (notTapped[i/2]) {
                        return i/2;
                    } else return throwCheckedRandom();
                } else {
                    if(notTapped[8]) {
                        return 8;
                    } else return throwCheckedRandom();
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

    private int goForWin() {
        int[][] winCases = variables.getWinCases();
        Variables.Player[] choicesByPlayer = variables.getPlayerChoices();
        int returnValue = 0;
        for(int[] checkWinner : winCases) {
            if(choicesByPlayer[checkWinner[0]] == choicesByPlayer[checkWinner[1]]) {
                if(choicesByPlayer[checkWinner[0]] == Variables.Player.TWO) {
                    if(notTapped[2]) {
                        blocked = true;
                        return 2;
                    }
                }
            } else if(choicesByPlayer[checkWinner[2]] == choicesByPlayer[checkWinner[1]]) {
                if(choicesByPlayer[checkWinner[1]] == Variables.Player.TWO) {
                    if(notTapped[0]) {
                        blocked = true;
                        return  0;
                    }
                }
            } else if(choicesByPlayer[checkWinner[2]] == choicesByPlayer[checkWinner[0]]) {
                if(choicesByPlayer[checkWinner[0]] == Variables.Player.TWO) {
                    if(notTapped[1]) {
                        blocked = true;
                        return 1;
                    }
                }
            }
        }
        return returnValue;
    }

    private int goForWinExp() {
        if(checkedExtra) {
            return extras();
        }
        for(int i = 2; i < playerChoices.length; i=i+2) {
            if (playerChoices[i] == Variables.Player.TWO && playerChoices[0] == Variables.Player.TWO) {
                if(i != 4) {
                    if (notTapped[i/2]) {
                        return i/2;
                    } else return throwCheckedRandom();
                } else {
                    if(notTapped[8]) {
                        return 8;
                    } else return throwCheckedRandom();
                }
            }
        }
        for(int i = 4; i < playerChoices.length; i=i+2) {
            if (playerChoices[i] == Variables.Player.TWO && playerChoices[2] == Variables.Player.TWO) {
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
                    if (playerChoices[i] == Variables.Player.TWO && playerChoices[j] == Variables.Player.TWO) {
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
        return returnValue;
    }

    private int extras() {
        boolean v1 = true, v2 = true, v3 = true,
                v4 = true, v5 = true, v6 = true,
                v7 = true, v8 = true, v9 = true,
                v10 = true, v11 = true, v12 = true;
        if( playerChoices[4] == Variables.Player.TWO && playerChoices[6] == Variables.Player.TWO && v1) {
            v1 = false;
            if(notTapped[2]) {
                returnValue = 2;
            } else extras();
        } else if ( playerChoices[4] == Variables.Player.TWO && playerChoices[8] == Variables.Player.TWO && v2) {
            v2 = false;
            if(notTapped[0]) {
                returnValue = 0;
            } else extras();
        } else if ( playerChoices[6] == Variables.Player.TWO && playerChoices[8] == Variables.Player.TWO && v3) {
            v3 = false;
            if(notTapped[7]) {
                returnValue = 7;
            } else extras();
        } else if( playerChoices[2] == Variables.Player.TWO && playerChoices[5] == Variables.Player.TWO && v4) {
            v4 = false;
            if(notTapped[8]) {
                returnValue = 8;
            } else extras();
        } else if( playerChoices[0] == Variables.Player.TWO && playerChoices[3] == Variables.Player.TWO && v5 ) {
            v5 = false;
            if(notTapped[6]) {
                returnValue = 6;
            } else extras();
        } else if( playerChoices[0] == Variables.Player.TWO && playerChoices[6] == Variables.Player.TWO && v6 ) {
            v6 = false;
            if(notTapped[3]) {
                returnValue = 3;
            } else extras();
        } else if( playerChoices[5] == Variables.Player.TWO && playerChoices[8] == Variables.Player.TWO && v7) {
            v7 = false;
            if(notTapped[2]) {
                returnValue = 2;
            } else extras();
        } else if( playerChoices[3] == Variables.Player.TWO && playerChoices[6] == Variables.Player.TWO && v8) {
            v8 = false;
            if (notTapped[0]) {
                returnValue = 0;
            } else extras();
        } else if( playerChoices[1] == Variables.Player.TWO && playerChoices[4] == Variables.Player.TWO && v12) {
            v12 = false;
            if (notTapped[7]) {
                returnValue = 7;
            } else extras();
        } else if( playerChoices[7] == Variables.Player.TWO && playerChoices[4] == Variables.Player.TWO && v9) {
            v9 = false;
            if (notTapped[1]) {
                returnValue = 1;
            } else extras();
        } else if( playerChoices[3] == Variables.Player.TWO && playerChoices[4] == Variables.Player.TWO && v10) {
            v10 = false;
            if (notTapped[5]) {
                returnValue = 5;
            } else extras();
        } else if( playerChoices[5] == Variables.Player.TWO && playerChoices[4] == Variables.Player.TWO && v11) {
            v11 = false;
            if (notTapped[3]) {
                returnValue = 5;
            } else extras();
        } else {
            checkedExtra = false;
            goForWinExp();
        }
        return returnValue;
    }

    private int findLastGrid() {
        for(int i = 0; i < notTapped.length; i++) {
            if(notTapped[i])
                returnValue = i;
        }
        return returnValue;
    }

    private int throwCheckedRandom() {
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
