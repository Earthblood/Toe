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

import android.util.Log;

import com.earthblood.tictactoe.application.Toe;
import com.earthblood.tictactoe.util.GameSymbol;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Random;

public class StrategyItem {

    public static final int[] ALL_BOXES = {1,2,3,4,5,6,7,8,9};
    public static final int[] DEFAULT_ZEROS = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    public static final int NOT_FOUND = -1;
    public static final int MIDDLE_BOX_POSITION = 5;

    private StrategyItem successor;
    protected int[] currentlySelectedBoxes;
    protected int[] availableBoxes;

    protected Random random = new Random();

    public int getRandom(int min, int max){
        return random.nextInt(max - min + 1) + min;
    }
    public Random getRandomObject(){
        return this.random;
    }

    protected int getBoxId(int[] selectedXBoxIds, int[] selectedOBoxIds, GameSymbol androidSymbol){
        return NOT_FOUND;
    }

    public int execute(int[] selectedXBoxIds, int[] selectedOBoxIds, GameSymbol androidSymbol){
        Log.i(Toe.TAG, "Inside: " + getClass().getName());

        currentlySelectedBoxes = ArrayUtils.addAll(selectedXBoxIds, selectedOBoxIds);
        currentlySelectedBoxes = ArrayUtils.removeElements(currentlySelectedBoxes, DEFAULT_ZEROS);
        availableBoxes = ArrayUtils.removeElements(ALL_BOXES, currentlySelectedBoxes);


        int boxId = getBoxId(selectedXBoxIds, selectedOBoxIds, androidSymbol);
        if(ArrayUtils.contains(ALL_BOXES, boxId)){
            Log.i(Toe.TAG, "Found: " + boxId);
            return boxId;
        }
        else if(successor != null){
            return successor.execute(selectedXBoxIds, selectedOBoxIds, androidSymbol);
        }
        else{
            return NOT_FOUND;
        }
    }


    protected StrategyItem getSuccessor() {
        return successor;
    }

    protected void setSuccessor(StrategyItem successor) {
        this.successor = successor;
    }
}
