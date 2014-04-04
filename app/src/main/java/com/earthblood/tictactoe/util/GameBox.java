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
package com.earthblood.tictactoe.util;

import com.earthblood.tictactoe.R;

public enum GameBox {
    ONE(R.id.box_1),
    TWO(R.id.box_2),
    THREE(R.id.box_3),
    FOUR(R.id.box_4),
    FIVE(R.id.box_5),
    SIX(R.id.box_6),
    SEVEN(R.id.box_7),
    EIGHT(R.id.box_8),
    NINE(R.id.box_9);

    private int layoutBoxId;

    private GameBox(int layoutBoxId){
        this.layoutBoxId = layoutBoxId;
    }

    public int layoutBoxId() {
        return layoutBoxId;
    }

    public int boxPosition(){
        return ordinal() +1;
    }
    public static GameBox byLayoutId(int id) {
        for (GameBox gameBox : GameBox.values()) {
            if(gameBox.ordinal() +1 == id){
                return gameBox;
            }
        }
        return null;
    }
    public static GameBox byBoxPosition(int i){
        for (GameBox gameBox : GameBox.values()) {
            if(gameBox.ordinal() +1 == i){
                return gameBox;
            }
        }
        return null;
    }
}
