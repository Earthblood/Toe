package com.earthblood.tictactoe.strategy;

import com.earthblood.tictactoe.util.GameSymbol;

/**
 * @author John Piser developer@earthblood.com
 *         Copyright 2014.
 */
public class ToeStrategyVeryHard extends ToeStrategyBase implements ToeStrategy{

    public ToeStrategyVeryHard(int[] selectedXBoxIds, int[] selectedOBoxIds, GameSymbol androidSymbol) {
        this.selectedXBoxIds = selectedXBoxIds;
        this.selectedOBoxIds = selectedOBoxIds;
        this.androidSymbol   = androidSymbol;
    }

    @Override
    public int getBoxId() {

        StrategyItemPickRandomBox   pickRandom          = new StrategyItemPickRandomBox();
        StrategyItemPickMiddleBox   pickMiddle          = new StrategyItemPickMiddleBox(pickRandom);
        StrategyItemPatternDetector pickDefensive       = new StrategyItemPatternDetector(
                                                               pickMiddle,
                                                               totalPatterns(),
                                                               androidOpponent());

        StrategyItemPatternDetector pickOffensive       = new StrategyItemPatternDetector(
                                                               pickDefensive,
                                                               totalPatterns(),
                                                               androidSymbol);


        return pickOffensive.execute(selectedXBoxIds, selectedOBoxIds, androidSymbol);
    }

    @Override
    public GameSymbol getSymbol() {
        return androidSymbol;
    }
}
