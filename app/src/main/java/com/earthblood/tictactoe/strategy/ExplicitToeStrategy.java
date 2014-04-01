package com.earthblood.tictactoe.strategy;

import com.earthblood.tictactoe.util.GameSymbol;

/**
 * @author John Piser developer@earthblood.com
 *         Copyright 2014.
 */
public class ExplicitToeStrategy implements ToeStrategy {

    private Integer boxId;
    private GameSymbol gameSymbol;

    public ExplicitToeStrategy(Integer boxId, GameSymbol gameSymbol) {
        this.boxId = boxId;
        this.gameSymbol = gameSymbol;
    }

    @Override
    public int getBoxId() {
        return this.boxId;
    }

    @Override
    public GameSymbol getSymbol() {
        return this.gameSymbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExplicitToeStrategy strategy = (ExplicitToeStrategy) o;

        if (!boxId.equals(strategy.boxId)) return false;
        if (gameSymbol != strategy.gameSymbol) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = boxId.hashCode();
        result = 31 * result + gameSymbol.hashCode();
        return result;
    }
}
