package com.earthblood.tictactoe.util;

/**
 * @author John Piser developer@earthblood.com
 *         Copyright 2014.
 */
public enum GameWinPattern {
    ONE_ACROSS(1,2,3),
    FOUR_ACROSS(4,5,6),
    SEVEN_ACROSS(7,8,9),
    ONE_DOWN(1,4,7),
    TWO_DOWN(2,5,8),
    THREE_DOWN(3,6,9),
    ONE_DIAGONAL(1,5,9),
    THREE_DIAGONAL(3,5,7);

    private int[] boxIds;

    private GameWinPattern(int id1, int id2, int id3){
        this.boxIds = new int[]{id1, id2, id3};
    }

    public int[] getBoxIds() {
        return boxIds;
    }

    public static GameWinPattern checkForWin(int[] boxIdsForGameSymbol){
        for (GameWinPattern gameWinPattern : values()) {
            int count =0;
            for (int boxId : gameWinPattern.boxIds) {
                for (int id : boxIdsForGameSymbol) {
                    count += (id == boxId ? 1 : 0 );
                }
            }
            if(count == 3){
                return gameWinPattern;
            }
        }
        return null;
    }
}
