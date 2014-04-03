package com.earthblood.tictactoe.strategy;

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
        int selectedIndex = random.nextInt(availableBoxes.length - 1);

        return availableBoxes[selectedIndex];
    }
}
