package com.earthblood.tictactoe.strategy;

import com.earthblood.tictactoe.util.GameSymbol;
import com.earthblood.tictactoe.util.GameWinPattern;

/**
 * @author John Piser developer@earthblood.com
 *         Copyright 2014.
 *
 *         TRY TO DETECT IF YOU OR YOUR OPPONENT HAS TWO BOXES OF A WINNING PATTERN,
 *         IF THE THIRD WINNING BOX OF THAT PATTERN IS AVAILABLE, SELECT IT EITHER TO BLOCK OR WIN
 *
 *         THIS STRATEGY CAN BE USED OFFENSIVELY OR DEFENSIVELY DEPENDING ON WHICH SYMBOL YOU ARE TARGETING:
 *
 *         int numberOfPatternsToDetect: The number of game winning patterns to target. The higher the number, the greater the degree
 *                                       of offensive or defensive difficulty. Cannot be higher than total number of patterns
 *
 *         GameSymbol symbolToDetect:    The symbol whose selected boxes will be inspected for pattern matching.
 *                                       When initialized with the Android's symbol, this strategy will be offensive, else defensive
 *
 *
 */

public class StrategyItemPatternDetector extends StrategyItem {

    protected int numberOfPatternsToDetect;
    protected GameSymbol symbolToDetect;

    public StrategyItemPatternDetector(StrategyItem successor, int numberOfPatternsToDetect, GameSymbol symbolToDetect) {
        this.setSuccessor(successor);
        this.numberOfPatternsToDetect = numberOfPatternsToDetect;
        this.symbolToDetect = symbolToDetect;
    }

    public int getBoxId(int[] selectedXBoxIds, int[] selectedOBoxIds, GameSymbol androidSymbol) {

        int[] selectedBoxesToInspect = symbolToDetect == GameSymbol.X ? selectedXBoxIds : selectedOBoxIds;

        //Guard against selecting more patterns than exist.
        numberOfPatternsToDetect = numberOfPatternsToDetect > GameWinPattern.values().length ? GameWinPattern.values().length : numberOfPatternsToDetect;

        GameWinPattern[] gameWinPatternsSubSet = GameWinPattern.randomBunchOfWinningPatterns(numberOfPatternsToDetect, getRandomObject());

        for (GameWinPattern gameWinPattern : gameWinPatternsSubSet) {
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
