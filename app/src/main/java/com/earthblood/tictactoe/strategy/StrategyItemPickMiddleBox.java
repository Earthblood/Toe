package com.earthblood.tictactoe.strategy;

import com.earthblood.tictactoe.util.GameSymbol;

import org.apache.commons.lang3.ArrayUtils;

/**
 * @author John Piser developer@earthblood.com
 *         Copyright 2014.
 *
 *
 *         PICK THE MIDDLE BOX IF AVAILABLE
 */
public class StrategyItemPickMiddleBox extends StrategyItem {


    public StrategyItemPickMiddleBox(StrategyItem successor) {
        this.setSuccessor(successor);
    }

    public int getBoxId(int[] selectedXBoxIds, int[] selectedOBoxIds, GameSymbol androidSymbol) {

        if(ArrayUtils.contains(availableBoxes, MIDDLE_BOX_POSITION)){
            return MIDDLE_BOX_POSITION;
        }
        else{
            return NOT_FOUND;
        }
    }
}
