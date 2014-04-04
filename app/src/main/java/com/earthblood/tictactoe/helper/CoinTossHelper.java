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
package com.earthblood.tictactoe.helper;

import com.earthblood.tictactoe.util.GameSymbol;

import java.util.Random;

public class CoinTossHelper {

    private Random random;

    public CoinTossHelper() {
        this.random = new Random();
    }

    public int getRandom(int min, int max){
        return random.nextInt(max - min + 1) + min;
    }

    public GameSymbol coinToss(){
        int random = getRandom(0, 10);
        return random < 5 ? GameSymbol.X : GameSymbol.O;
    }

}
