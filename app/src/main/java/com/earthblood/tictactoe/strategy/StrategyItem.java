package com.earthblood.tictactoe.strategy;

import android.util.Log;

import com.earthblood.tictactoe.application.Toe;
import com.earthblood.tictactoe.util.GameSymbol;

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

    public int execute(int[] selectedXBoxIds, int[] selectedOBoxIds, GameSymbol androidSymbol){
        if(getSuccessor() != null){
           return getSuccessor().execute(selectedXBoxIds, selectedOBoxIds, androidSymbol);
        }
        else{
            Log.d(Toe.TAG," ! Unable to find a boxId ! ");
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
