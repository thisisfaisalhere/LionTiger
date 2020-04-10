package com.virusX.lionOrTiger;

import java.util.Random;

class AIPlays {

    int getLocation(int strength) {
        if(strength == 1) {
            Random random = new Random();
            return (random.nextInt(8) + 1);
        }
        return -1;
    }
}
