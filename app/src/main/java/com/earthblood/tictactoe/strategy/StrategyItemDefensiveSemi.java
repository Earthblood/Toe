package com.earthblood.tictactoe.strategy;

import android.util.Log;

import com.earthblood.tictactoe.application.Toe;
import com.earthblood.tictactoe.util.GameSymbol;
import com.earthblood.tictactoe.util.GameWinPattern;

import org.apache.commons.lang3.ArrayUtils;

/**
 * @author John Piser developer@earthblood.com
 *         Copyright 2014.
 *
 *         BLOCK IF OPPONENT HAS TWO IN A ROW WITH THE WINNING BOX AVAILABLE
 *         IGNORE A FEW WINNING PATTERNS HOWEVER SO THE OTHER GUY HAS A CHANCE
 */
public class StrategyItemDefensiveSemi extends StrategyItem{

    public StrategyItemDefensiveSemi(StrategyItem successor) {
        this.setSuccessor(successor);
    }

    public int execute(int[] selectedXBoxIds, int[] selectedOBoxIds, GameSymbol androidSymbol) {
        Log.d(Toe.TAG, "Inside StrategyItemDefensiveSemi");
        int boxId = getBoxId(selectedXBoxIds, selectedOBoxIds, androidSymbol);
        if (ArrayUtils.contains(ALL_BOXES, boxId)) {
            Log.d(Toe.TAG, "Found: " + boxId);
            return boxId;
        } else {
            return super.execute(selectedXBoxIds, selectedOBoxIds, androidSymbol);
        }
    }


    public int getBoxId(int[] selectedXBoxIds, int[] selectedOBoxIds, GameSymbol androidSymbol) {

        int[] currentlySelectedBoxes = ArrayUtils.addAll(selectedXBoxIds, selectedOBoxIds);

        currentlySelectedBoxes = ArrayUtils.removeElements(currentlySelectedBoxes, DEFAULT_ZEROS);
        int[] availableBoxes = ArrayUtils.removeElements(ALL_BOXES, currentlySelectedBoxes);

        int[] selectedBoxesToInspect = androidSymbol == GameSymbol.O ? selectedXBoxIds : selectedOBoxIds;

        for (GameWinPattern gameWinPattern : GameWinPattern.randomBunchOfWinningPatterns()) {
            int count = 0;
            for (int selectedOpponentPosition : selectedBoxesToInspect) {
                if (gameWinPattern.containsBoxId(selectedOpponentPosition)) {
                    count++;
                }
            }
            if (count == 2) {
                int found = gameWinPattern.returnFirstMatch(availableBoxes, NOT_FOUND);
                if (found > NOT_FOUND) {
                    return found;
                }
            }
        }
        return NOT_FOUND;

    }
}
