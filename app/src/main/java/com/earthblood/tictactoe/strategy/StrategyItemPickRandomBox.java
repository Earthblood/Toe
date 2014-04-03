package com.earthblood.tictactoe.strategy;

import android.util.Log;

import com.earthblood.tictactoe.application.Toe;
import com.earthblood.tictactoe.util.GameSymbol;

/**
 * @author John Piser developer@earthblood.com
 *         Copyright 2014.
 *
 *
 *         PICK ANY AVAILABLE BOX AT RANDOM
 */
public class StrategyItemPickRandomBox extends StrategyItem {


    public int getBoxId(int[] selectedXBoxIds, int[] selectedOBoxIds, GameSymbol androidSymbol) {

        int max = availableBoxes.length - 1;
        Log.d(Toe.TAG, " max ="+max);

        int selectedIndex = max > 0 ?  getRandom(0, max): 0;

        Log.d(Toe.TAG, " selectedIndex =" + selectedIndex);

        return availableBoxes[selectedIndex];
    }
}
