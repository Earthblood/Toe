/**
 * @author John Piser developer@earthblood.com
 *
 * Copyright (C) 2014 EARTHBLOOD, LLC
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.earthblood.tictactoe.strategy;

import com.earthblood.tictactoe.util.GameSymbol;

public class ToeStrategyExplicit implements ToeStrategy {

    private Integer boxId;
    private GameSymbol gameSymbol;

    public ToeStrategyExplicit(Integer boxId, GameSymbol gameSymbol) {
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

        ToeStrategyExplicit strategy = (ToeStrategyExplicit) o;

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
