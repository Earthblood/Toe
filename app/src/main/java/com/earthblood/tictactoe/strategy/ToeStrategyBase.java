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
import com.earthblood.tictactoe.util.GameWinPattern;

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
