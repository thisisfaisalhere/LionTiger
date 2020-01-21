package com.virusx.lionortiger;

import java.util.Random;

class GetGridLocation {

//    private boolean[] notTapped;
//
//    int run() {
//        int finalReturnValue = 0;
//        if(turnNo == 1) {
//            if(notTapped[4]) {
//                return 4;
//            } else {
//                switch (getRandomNo(1)) {
//                    case 0:
//                        if(notTapped[0]) return 0;
//                    case 1:
//                        if(notTapped[2]) return 2;
//                    case 2:
//                        if(notTapped[6]) return 6;
//                    case 3:
//                        if(notTapped[8]) return 8;
//                }
//            }
//        } else if(turnNo == 2) {
//            for(int i = 2; i < playerChoices.length; i=i+2) {
//                if (playerChoices[i] == Player.ONE && playerChoices[0] == Player.ONE) {
//                    if(i != 4) {
//                        if (notTapped[i/2]) {
//                            finalReturnValue = i/2;
//                            return finalReturnValue;
//                        }
//                        else break;
//                    } else {
//                        if(notTapped[8]) {
//                            finalReturnValue = 8;
//                            return finalReturnValue;
//                        }
//                        else break;
//                    }
//                }
//            }
//            if( playerChoices[4] == Player.ONE && playerChoices[2] == Player.ONE) {
//                finalReturnValue = 6;
//            } else if( playerChoices[4] == Player.ONE && playerChoices[6] == Player.ONE) {
//                finalReturnValue = 2;
//            } else if ( playerChoices[4] == Player.ONE && playerChoices[8] == Player.ONE ) {
//                finalReturnValue = 0;
//            } else if ( playerChoices[6] == Player.ONE && playerChoices[8] == Player.ONE ) {
//                finalReturnValue = 7;
//            } else if ( playerChoices[0] == Player.ONE && playerChoices[3] == Player.ONE ) {
//                finalReturnValue = 6;
//            } else if ( playerChoices[2] == Player.ONE && playerChoices[5] == Player.ONE ) {
//                finalReturnValue = 8;
//            } else if ( playerChoices[0] == Player.ONE && playerChoices[1] == Player.ONE) {
//                return 2;
//            } else if ( playerChoices[6] == Player.ONE && playerChoices[7] == Player.ONE) {
//                return 8;
//            }
//            else  finalReturnValue = getRandomNo(2);
//            return finalReturnValue;
//        } else if( turnNo == 3) {
//            if(helper != 0) {
//                if (helper == 1) return 7;
//                else if (helper == 3) return 5;
//                else if (helper == 5) return 3;
//                else return 1;
//            } else if(helper == 0) {
//                return function();
//            } else {
//                for(int i = 0; i < notTapped.length; i++) {
//                    if(notTapped[i])
//                        finalReturnValue = i;
//                }
//            }
//        } else {
//            for(int i = 0; i < notTapped.length; i++) {
//                if(notTapped[i])
//                    finalReturnValue = i;
//            }
//        }
//        return finalReturnValue;
//    }
//
//    private int function() {
//        int j,k,l,m,finalReturnValue = 0;
//        k = 0;
//        for(int i = 0; i < playerChoices.length; i++) {
//            j = i;
//            if (k < 2) {
//                if (++j < playerChoices.length - 2) {
//                    if (playerChoices[i] == Player.ONE && playerChoices[j] == Player.ONE) {
//                        if (i == 0 || i == 3 || i == 6) {
//                            if(notTapped[++j]) return j;
//                            else continue;
//                        } else {
//                            m=i;
//                            if(notTapped[--m]) return m;
//                            else continue;
//                        }
//                    }
//                }
//                k++;
//            } else k = 0;
//        } for(int i = 0; i < playerChoices.length; i++) {
//            l = i;
//            m = i;
//            l = l + 3;
//            if(i < 3) {
//                while(k < 2) {
//                    if(playerChoices[m] == Player.ONE && playerChoices[l] == Player.ONE){
//                        if(k == 0){
//                            l=l+3;
//                            if(notTapped[l]) return l;
//                            else continue;
//                        } else{
//                            if(notTapped[i]) return i;
//                            else continue;
//                        }
//                    } m = m + 6; k++;
//                }
//            }
//        }
//        for(int i = 0; i < notTapped.length; i++) {
//            if(notTapped[i])
//                finalReturnValue = i;
//        }
//        return finalReturnValue;
//    }
//
    int getRandomNo(int caseNo) {
        Random random = new Random();
        if(caseNo == 1) return random.nextInt(3);
        else return random.nextInt(9);
    }
}
