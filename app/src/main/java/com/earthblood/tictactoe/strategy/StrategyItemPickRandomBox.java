package com.earthblood.tictactoe.strategy;

import android.util.Log;

import com.earthblood.tictactoe.application.Toe;
import com.earthblood.tictactoe.util.GameSymbol;

import java.util.Random;

/**
 * @author John Piser developer@earthblood.com
 *         Copyright 2014.
 *
 *
 *         PICK ANY AVAILABLE BOX AT RANDOM
 */
public class StrategyItemPickRandomBox extends StrategyItem {

    public int getBoxId(int[] selectedXBoxIds, int[] selectedOBoxIds, GameSymbol androidSymbol) {


        Random random = new Random();
        int max = availableBoxes.length - 1;
        Log.d(Toe.TAG, " max ="+max);

        int selectedIndex = max > 0 ? random.nextInt(max) : 0;

        return availableBoxes[selectedIndex];
    }
}
