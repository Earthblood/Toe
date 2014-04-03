package com.earthblood.tictactoe.strategy;

import android.util.Log;

import com.earthblood.tictactoe.application.Toe;
import com.earthblood.tictactoe.util.GameSymbol;

import org.apache.commons.lang3.ArrayUtils;

/**
 * @author John Piser developer@earthblood.com
 *         Copyright 2014.
 *
 *         Chain of Responsibility implementation
 */
public class StrategyItem {

    public static final int[] ALL_BOXES = {1,2,3,4,5,6,7,8,9};
    public static final int[] DEFAULT_ZEROS = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    public static final int NOT_FOUND = -1;
    public static final int MIDDLE_BOX_POSITION = 5;

    private StrategyItem successor;
    protected int[] currentlySelectedBoxes;
    protected int[] availableBoxes;


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
