package com.earthblood.tictactoe.strategy;

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
        int selectedIndex = max > 0 ?  getRandom(0, max): 0;

        return availableBoxes[selectedIndex];
    }
}
