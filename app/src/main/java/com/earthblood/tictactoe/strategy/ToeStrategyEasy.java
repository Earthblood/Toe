package com.earthblood.tictactoe.strategy;

import com.earthblood.tictactoe.util.GameSymbol;

/**
 * @author John Piser developer@earthblood.com
 *         Copyright 2014.
 */
public class ToeStrategyEasy extends ToeStrategyBase implements ToeStrategy {

    public ToeStrategyEasy(int[] selectedXBoxIds, int[] selectedOBoxIds, GameSymbol androidSymbol) {
        this.selectedXBoxIds = selectedXBoxIds;
        this.selectedOBoxIds = selectedOBoxIds;
        this.androidSymbol   = androidSymbol;
    }

    @Override
    public int getBoxId() {

        StrategyItemPickRandomBox pickRandom = new StrategyItemPickRandomBox();

        return pickRandom.execute(selectedXBoxIds, selectedOBoxIds, androidSymbol);

    }

    @Override
    public GameSymbol getSymbol() {
        return androidSymbol;
    }
}
