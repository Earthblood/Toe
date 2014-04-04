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

public class ToeStrategyVeryHard extends ToeStrategyBase implements ToeStrategy{

    public ToeStrategyVeryHard(int[] selectedXBoxIds, int[] selectedOBoxIds, GameSymbol androidSymbol) {
        this.selectedXBoxIds = selectedXBoxIds;
        this.selectedOBoxIds = selectedOBoxIds;
        this.androidSymbol   = androidSymbol;
    }

    @Override
    public int getBoxId() {

        StrategyItemPickRandomBox   pickRandom     = new StrategyItemPickRandomBox();
        StrategyItemPickMiddleBox   pickMiddle     = new StrategyItemPickMiddleBox(pickRandom);
        StrategyItemPatternDetector pickDefensive  = new StrategyItemPatternDetector(
                                                          pickMiddle,
                                                          totalPatterns(),
                                                          androidOpponent());

        StrategyItemPatternDetector pickOffensive  = new StrategyItemPatternDetector(
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