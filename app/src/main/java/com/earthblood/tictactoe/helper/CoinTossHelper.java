package com.earthblood.tictactoe.helper;

import com.earthblood.tictactoe.util.GameSymbol;

import java.util.Random;

/**
 * @author John Piser developer@earthblood.com
 *         Copyright 2014.
 */
public class CoinTossHelper {

    public int getRandom(int min, int max){
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }
    public GameSymbol coinToss(){
        int random = getRandom(0, 10);
        return random < 5 ? GameSymbol.X : GameSymbol.O;
    }

}
