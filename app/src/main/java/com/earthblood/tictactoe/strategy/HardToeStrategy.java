package com.earthblood.tictactoe.strategy;

import com.earthblood.tictactoe.util.GameSymbol;

/**
 * @author John Piser developer@earthblood.com
 *         Copyright 2014.
 */
public class HardToeStrategy implements ToeStrategy {

    private final int[] selectedXBoxIds;
    private final int[] selectedOBoxIds;
    private final GameSymbol androidSymbol;

    public HardToeStrategy(int[] selectedXBoxIds, int[] selectedOBoxIds, GameSymbol androidSymbol) {
        this.selectedXBoxIds = selectedXBoxIds;
        this.selectedOBoxIds = selectedOBoxIds;
        this.androidSymbol   = androidSymbol;
    }

    @Override
    public int getBoxId() {
        //TODO: IMPLEMENT THIS STRATEGY

        return 0;
    }

    @Override
    public GameSymbol getSymbol() {
        return androidSymbol;
    }
}
