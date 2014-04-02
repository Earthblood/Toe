package com.earthblood.tictactoe.strategy;

import com.earthblood.tictactoe.helper.CoinTossHelper;
import com.earthblood.tictactoe.util.GameSymbol;

import org.apache.commons.lang3.ArrayUtils;

/**
 * @author John Piser developer@earthblood.com
 *         Copyright 2014.
 */
public class EasyToeStrategy implements ToeStrategy {

    private final int[] selectedXBoxIds;
    private final int[] selectedOBoxIds;
    private final GameSymbol androidSymbol;

    public EasyToeStrategy(int[] selectedXBoxIds, int[] selectedOBoxIds, GameSymbol androidSymbol) {
        this.selectedXBoxIds = selectedXBoxIds;
        this.selectedOBoxIds = selectedOBoxIds;
        this.androidSymbol   = androidSymbol;
    }

    @Override
    public int getBoxId() {

        int[] currentlySelectedBoxes = ArrayUtils.addAll(this.selectedXBoxIds, this.selectedOBoxIds);
        currentlySelectedBoxes = ArrayUtils.removeElements(currentlySelectedBoxes, DEFAULT_ZEROS);
        int[] availableBoxes = ArrayUtils.removeElements(ALL_BOXES, currentlySelectedBoxes);

        //** Pick a Random Box
        int selectedIndex = CoinTossHelper.getRandom(0, availableBoxes.length-1);
        return availableBoxes[selectedIndex];
    }

    @Override
    public GameSymbol getSymbol() {
        return androidSymbol;
    }
}
