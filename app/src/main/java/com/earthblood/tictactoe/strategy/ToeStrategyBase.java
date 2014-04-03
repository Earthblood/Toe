package com.earthblood.tictactoe.strategy;

import com.earthblood.tictactoe.util.GameSymbol;
import com.earthblood.tictactoe.util.GameWinPattern;

/**
 * @author John Piser developer@earthblood.com
 *         Copyright 2014.
 *
 *         BASE CLASS FOR TOE STRATEGIES
 */
public abstract class ToeStrategyBase {

    protected int[] selectedXBoxIds;
    protected int[] selectedOBoxIds;
    protected GameSymbol androidSymbol;


    public GameSymbol androidOpponent(){
        return androidSymbol == GameSymbol.O ? GameSymbol.X : GameSymbol.O;
    }
    public int totalPatterns(){
        return GameWinPattern.values().length;
    }
}
