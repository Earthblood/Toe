package com.earthblood.tictactoe.strategy;

import com.earthblood.tictactoe.util.GameSymbol;

/**
 * @author John Piser developer@earthblood.com
 *         Copyright 2014.
 */
public class ToeStrategyNormal implements ToeStrategy {

    private final int[] selectedXBoxIds;
    private final int[] selectedOBoxIds;
    private final GameSymbol androidSymbol;

    public ToeStrategyNormal(int[] selectedXBoxIds, int[] selectedOBoxIds, GameSymbol androidSymbol) {
        this.selectedXBoxIds = selectedXBoxIds;
        this.selectedOBoxIds = selectedOBoxIds;
        this.androidSymbol   = androidSymbol;
    }

    @Override
    public int getBoxId() {

        StrategyItemPickRandomBox pickRandom    = new StrategyItemPickRandomBox();
        StrategyItemPickMiddleBox pickMiddle    = new StrategyItemPickMiddleBox(pickRandom);
        StrategyItemDefensiveSemi semiDefensive = new StrategyItemDefensiveSemi(pickMiddle);

        return semiDefensive.execute(selectedXBoxIds, selectedOBoxIds, androidSymbol);

    }

    @Override
    public GameSymbol getSymbol() {
        return androidSymbol;
    }
}
