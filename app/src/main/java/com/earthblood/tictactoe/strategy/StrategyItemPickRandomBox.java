package com.earthblood.tictactoe.strategy;

import android.util.Log;

import com.earthblood.tictactoe.application.Toe;
import com.earthblood.tictactoe.helper.CoinTossHelper;
import com.earthblood.tictactoe.util.GameSymbol;

import org.apache.commons.lang3.ArrayUtils;

/**
 * @author John Piser developer@earthblood.com
 *         Copyright 2014.
 *
 *
 *         PICK AN AVAILABLE BOX AT RANDOM
 */
public class StrategyItemPickRandomBox extends StrategyItem {


    public int execute(int[] selectedXBoxIds, int[] selectedOBoxIds, GameSymbol androidSymbol){
        Log.d(Toe.TAG, "Inside StrategyItemPickRandomBox");
        int boxId = getBoxId(selectedXBoxIds, selectedOBoxIds, androidSymbol);
        if(ArrayUtils.contains(ALL_BOXES, boxId)){
            Log.d(Toe.TAG, "Found: " + boxId);
            return boxId;
        }
        else{
            return super.execute(selectedXBoxIds, selectedOBoxIds, androidSymbol);
        }
    }


    public int getBoxId(int[] selectedXBoxIds, int[] selectedOBoxIds, GameSymbol androidSymbol) {

        int[] currentlySelectedBoxes = ArrayUtils.addAll(selectedXBoxIds, selectedOBoxIds);
        currentlySelectedBoxes = ArrayUtils.removeElements(currentlySelectedBoxes, DEFAULT_ZEROS);
        int[] availableBoxes = ArrayUtils.removeElements(ALL_BOXES, currentlySelectedBoxes);

        //** Pick a Random Box
        int selectedIndex = CoinTossHelper.getRandom(0, availableBoxes.length - 1);
        return availableBoxes[selectedIndex];
    }
}
