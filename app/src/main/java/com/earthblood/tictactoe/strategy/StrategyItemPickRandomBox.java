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

/**
 * PICK ANY AVAILABLE BOX AT RANDOM
 */
package com.earthblood.tictactoe.strategy;

import com.earthblood.tictactoe.util.GameSymbol;

public class StrategyItemPickRandomBox extends StrategyItem {

    public int getBoxId(int[] selectedXBoxIds, int[] selectedOBoxIds, GameSymbol androidSymbol) {

        int max = availableBoxes.length - 1;
        int selectedIndex = max > 0 ?  getRandom(0, max): 0;

        return availableBoxes[selectedIndex];
    }
}
